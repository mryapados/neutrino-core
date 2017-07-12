package org.neutrinocms.core.service;

import java.io.File;
import java.util.List;

import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.neutrinocms.core.bo.annotation.BOService;
import org.neutrinocms.core.dao.TemplateDao;
import org.neutrinocms.core.exception.JSPNotFoundException;
import org.neutrinocms.core.exception.ServiceException;
import org.neutrinocms.core.exception.UtilException;
import org.neutrinocms.core.model.independant.Folder;
import org.neutrinocms.core.model.translation.Lang;
import org.neutrinocms.core.model.translation.Template;
import org.neutrinocms.core.model.translation.Template.TemplateKind;
import org.neutrinocms.core.model.translation.TranslationProvider;
import org.neutrinocms.core.util.CommonUtil;
import org.neutrinocms.core.util.CommonUtil.TypeBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@BOService
@Service
@Scope(value = "singleton")
public class TemplateService extends TranslationService<Template>{

	private Logger logger = Logger.getLogger(TemplateService.class);

	@Autowired
	private TemplateDao templateDao;
	
	@Autowired
	private CommonUtil common;
	
	@Autowired
	private CacheService cacheService;
	

	public Template identifyWithAllExceptData(Folder folder, String name, Lang lang) throws ServiceException {
		try {
			if (folder == null) {
				return templateDao.identifyWithAllExceptData(name, lang);
			}
			else {
				return templateDao.identifyWithAllExceptData(folder, name, lang);
			}
		} catch (PersistenceException e) {
			throw new ServiceException("erreur findByNameWithAllExceptData Template", e);
		}
	}	
	
//	public List<Template> findAll() throws ServiceException {
//		try {
//			return (List<Template>) templateDao.findAll();
//		} catch (PersistenceException e) {
//			throw new ServiceException("erreur findAll Template", e);
//		}
//	}
	
	public List<Template> findAllBlockNotAffected() throws ServiceException {
		try {
			return templateDao.findAllBlockNotAffected();
		} catch (PersistenceException e) {
			throw new ServiceException("erreur findAllNotAffected Template", e);
		}
	}
	
	public List<Template> findAllBlockAndPageBlock() throws ServiceException {
		try {
			return templateDao.findAllBlockAndPageBlock();
		} catch (PersistenceException e) {
			throw new ServiceException("erreur findAllNotAffected Template", e);
		}
	}
	
	public List<Template> findAllWithModels() throws ServiceException {
		try {
			return templateDao.findAllWithModels();
		} catch (PersistenceException e) {
			throw new ServiceException("erreur findAllWithModels Template", e);
		}
	}
	
	public String pathType(Template template) throws ServiceException{
		if (template.getKind() == TemplateKind.BLOCK) return "blocks";
		else if (template.getKind() == TemplateKind.PAGE) return "pages";
		else if (template.getKind() == TemplateKind.PAGEBLOCK) return "pageblocks";
		else if (template.getKind() == TemplateKind.ELEMENT) return "elements";
		else throw new ServiceException("erreur pathType Template");
	}

	public String pathJSP(String pathContext, Template template) throws ServiceException{	
		String pathType = pathType(template) + "/" + template.getPath();
		StringBuilder path = new StringBuilder();
		path.append(pathContext);
		path.append("templates/");
		path.append(pathType);
		return path.toString();
	}
	public String convertPath(String path, Boolean forController) throws ServiceException{	
		if (forController) return path;
		else return CommonUtil.BASE_WEBINF + path + ".jsp";
	}
	
	@Cacheable(value="templateService")
	public String getPathJSP(Boolean forController, Folder folder, String context, Template template) throws ServiceException{
		try {
			String pathContext;
			String pathJSP;

			pathContext = common.getBasePath(false, folder, TypeBase.VIEWS) + context + "/";		
			pathJSP = pathJSP(pathContext, template);
			if (common.jspExist(pathJSP)) return convertPath(pathJSP, forController);
			
			pathContext = common.getBasePath(false, folder, TypeBase.COMMON);	
			pathJSP = pathJSP(pathContext, template);
			if (common.jspExist(pathJSP)) return convertPath(pathJSP, forController);
			
			pathContext = common.getBasePath(false, null, TypeBase.COMMON);
			pathJSP = pathJSP(pathContext, template);
			if (common.jspExist(pathJSP)) return convertPath(pathJSP, forController);
			
			throw new JSPNotFoundException("JSP not found for template : " + template.getName() + "(" + template.getPath() + "), context : " + context);
		} catch (UtilException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public Template translate(Template base, Lang lang) throws ServiceException {
		
		boolean fromSomething = base.getId() != null;
		if (fromSomething) base = templateDao.findOne(base.getId()); //Refresh object
		
		base.setId(null);

		TranslationProvider translation = base.getTranslation();
		if (translation == null){
			translation = new TranslationProvider();
		}
		base.setLang(lang);
		base.setTranslation(translation);
		base.setName(base.getName());
		base.setPath(base.getPath());
		base.setKind(base.getKind());
		base.setSchema(base.getSchema());
		
		
//		for (MapTemplate models : baseObject.getModels()) {
//			// TODO
//			System.out.println(models.getId());
//		}
//		
//		for (MapTemplate blocks : baseObject.getBlocks()) {
//			// TODO
//			System.out.println(blocks.getId());
//		}
		return base;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}


}
