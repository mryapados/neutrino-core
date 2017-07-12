package org.neutrinocms.core.service;

import org.apache.log4j.Logger;
import org.neutrinocms.core.bo.annotation.BOService;
import org.neutrinocms.core.dao.PageDao;
import org.neutrinocms.core.model.translation.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@BOService
@Service
@Scope(value = "singleton")
public class PageService extends TranslationService<Page>{

	private Logger logger = Logger.getLogger(PageService.class);

	@Autowired
	private PageDao pageDao;
	
//	@Transactional
//	public Page translate(Page page, Lang lang) throws ServiceException {
//		try {
//
//			if (page.getId() != null) page = pageDao.findOne(page.getId()); //Refresh object
//			
//			Page translated = page.getClass().newInstance();
//			TranslationProvider translation = page.getTranslation();
//			if (translation == null){
//				translation = translationProviderDao.save(new TranslationProvider());
//			}
//			translated.setLang(lang);
//			translated.setTranslation(translation);
//			translated.setName(page.getName());
//			translated.setContext(page.getContext());
//			
//			Template pageModel = page.getModel();
//			if (pageModel != null){
//				TranslationProvider pageModelTranslation = pageModel.getTranslation();
//				if (pageModelTranslation != null){
//					Map<Lang, Translation> translations = pageModelTranslation.getTranslations();
//					if (translations != null){
//						translated.setModel((Template) translations.get(lang));
//					}
//				}
//			}		
//	
//			return translated;
//		
//		} catch (InstantiationException | IllegalAccessException e) {
//			throw new ServiceException(e);
//		}
//		
//	}
	
	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}




}
