package org.neutrinocms.core.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;
import org.neutrinocms.core.constant.AttributeConst;
import org.neutrinocms.core.exception.ResourceNotFoundException;
import org.neutrinocms.core.exception.ServiceException;
import org.neutrinocms.core.exception.UtilException;
import org.neutrinocms.core.model.independant.Folder;
import org.neutrinocms.core.model.independant.MapTemplate;
import org.neutrinocms.core.model.independant.Position;
import org.neutrinocms.core.model.translation.Lang;
import org.neutrinocms.core.model.translation.Page;
import org.neutrinocms.core.model.translation.Template;
import org.neutrinocms.core.model.translation.Template.TemplateKind;
import org.neutrinocms.core.model.translation.Translation;
import org.neutrinocms.core.service.NDataService;
import org.neutrinocms.core.service.PageService;
import org.neutrinocms.core.service.PositionService;
import org.neutrinocms.core.service.TObjectService;
import org.neutrinocms.core.service.TemplateControllerExecutor;
import org.neutrinocms.core.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;



@Component
public class JspTagUtil {
	
	private Logger logger = Logger.getLogger(JspTagUtil.class);
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	private PageService pageService;
	
	@Autowired
	private TObjectService tObjectService;
	
	@Autowired
	private PositionService positionService;
	
	@Autowired
	private NDataService nDataService;
	
	@Autowired
	private TemplateService templateService;
	
	@Autowired
	private TemplateControllerExecutor templateControllerExecutor;

	@Autowired
	private IdProviderUtil idProviderUtil;
	
	private Folder getFolder(PageContext pageContext){
		//Get folder from pageContext
		return (Folder) pageContext.getAttribute(AttributeConst.FOLDER, PageContext.REQUEST_SCOPE);
	}
	private Lang getLang(PageContext pageContext){
		//Get lang from page
		return (Lang) pageContext.getAttribute(AttributeConst.ACTIVELANG, PageContext.REQUEST_SCOPE);
	}
	private Page getPage(PageContext pageContext, Folder folder, Lang lang, int pageId, String pageName) throws ServiceException, UtilException, ResourceNotFoundException{
		//Get page from pageContext
		Page page = (Page) pageContext.getAttribute(AttributeConst.ACTIVEPAGE, PageContext.REQUEST_SCOPE);
		if (pageId != 0) page = pageService.findOne(pageId); //If pageId provided -> page is replace
		else if (pageName != null) page = commonUtil.getPage(folder, pageName, lang); //If pageName provided -> page is replace
		return page;
	}
//	private Template getParentModel(PageContext pageContext){
//		//Get model from pageContext if PARENTPAGEBLOCK != null
//		return (Template) pageContext.getAttribute(AttributeConst.PARENTPAGEBLOCK, PageContext.REQUEST_SCOPE);
//	}
//	private Translation getModel(PageContext pageContext, Page page){
//		return page.getModel(); 
//	}
	private Translation getModel(PageContext pageContext, Page page){
		//Get model from pageContext if PARENTPAGEBLOCK != null
		Translation model = (Template) pageContext.getAttribute(AttributeConst.PARENTPAGEBLOCK, PageContext.REQUEST_SCOPE);
		//If model == null (if not PARENTPAGEBLOCK) -> model become page model
		if (model == null) model = page.getModel(); 
		return model;
	}
	private Translation getActiveObject(PageContext pageContext, Folder folder, Lang lang, int activeObjectId, String activeObjectName) throws ServiceException{
		Translation activeObject = (Translation) pageContext.getAttribute(AttributeConst.ACTIVEOBJECT, PageContext.REQUEST_SCOPE);
		if (activeObjectId != 0) {
			activeObject = tObjectService.findOne(activeObjectId); //If activeObjectId provided -> activeObject is replace
		}
		else if (activeObject != null) {
			activeObject = tObjectService.identify(folder, activeObjectName, lang); //If activeObjectName provided -> activeObject is replace
		}
		return activeObject;
	}
	
	
	private void getJsp(PageContext pageContext, String activeTemplateName, MapTemplate mapTemplate, Template activeTemplate, Folder folder, Lang lang, Page page, Translation model, Translation activeObject, Map<String, String> params) throws ServiceException, ServletException, IOException{
		logger.debug("Enter in getJsp : pageContext = " + pageContext + "; activeTemplateName = " + activeTemplateName + "; mapTemplate = " + mapTemplate + "; activeTemplate = " + activeTemplate + "; folder = " + folder + "; lang = " + lang + "; page = " + page + "; model = " + model + "; activeObject = " + activeObject + "; params = " + params);
		ModelMap modelMap = templateControllerExecutor.execute(activeTemplate.getController(), page, model, activeObject, activeTemplate, folder, lang, params, pageContext);
		if (modelMap != null){
			for (Map.Entry<String, Object> entry : modelMap.entrySet()) {
				pageContext.setAttribute(entry.getKey(), entry.getValue(), PageContext.REQUEST_SCOPE);
			}
		}
		
		Map<String, Object> nDatas;
		if (mapTemplate == null) nDatas = nDataService.getNDatas(activeTemplate);
		else nDatas = nDataService.getNDatas(mapTemplate);
		pageContext.setAttribute(AttributeConst.NDATAS, nDatas, PageContext.REQUEST_SCOPE);

		String path = templateService.getPathJSP(false, folder, page.getContext(), activeTemplate);

		pageContext.setAttribute(activeTemplateName, activeTemplate, PageContext.REQUEST_SCOPE);
		
		if (activeTemplate.getKind() == TemplateKind.PAGEBLOCK){
			pageContext.setAttribute(AttributeConst.PARENTPAGEBLOCK, activeTemplate, PageContext.REQUEST_SCOPE);
			pageContext.include(path.toString());
			pageContext.removeAttribute(AttributeConst.PARENTPAGEBLOCK);
		} else {
			pageContext.include(path.toString());
		}
		
		//remove ndata attributes
		pageContext.removeAttribute(AttributeConst.NDATAS, PageContext.REQUEST_SCOPE);
	}
	
	/* Get blocks from container parentPageBlock if not null + activeObject if not null + page */
	public void getJspBlock(PageContext pageContext, String position, String pageName, String activeObjectName, int pageId, int activeObjectId, Map<String, String> params) throws JspException{
		logger.debug("Enter in getJspBlock : pageContext = " + pageContext + "; position = " + position + "; pageName = " + pageName + "; activeObjectName = " + activeObjectName + "; pageId = " + pageId + "; activeObjectId = " + activeObjectId + "; params = " + params);
		JspWriter out = pageContext.getOut();
		try {
			if (CommonUtil.DEBUG) out.print("<p class=\"debug\">" + "Enter in getJSP()" + "</p>");
			
			Folder folder = getFolder(pageContext);
			Lang lang = getLang(pageContext);
			Page page = getPage(pageContext, folder, lang, pageId, pageName);
			Translation model = getModel(pageContext, page);
			Translation activeObject = getActiveObject(pageContext, folder, lang, activeObjectId, activeObjectName);
			
			List<Translation> models = new ArrayList<>();
			models.add(model);
			models.add(page);
			
			if (activeObject != null){
				models.add(activeObject);
			}
					
			Position pos = positionService.findByNameForModelsWithMaps(models, position);

			if (CommonUtil.DEBUG) out.print("<p class=\"debug\">" + "position : " + position + "</p>");
			if (pos != null){
				List<MapTemplate> mapTemplates;
				mapTemplates = pos.getMapTemplates();
				for (MapTemplate mapTemplate : mapTemplates) {
					Template activeBlock = mapTemplate.getBlock();

					getJsp(pageContext, AttributeConst.ACTIVEBLOCK, mapTemplate, activeBlock, folder, lang, page, model, activeObject, params);

				}
			}

			if (CommonUtil.DEBUG) out.print("<p class=\"debug\">" + "Exit getJSP()" + "</p>");
			
		} catch (ServiceException | IOException | ServletException | UtilException | ResourceNotFoundException e) {
			logger.error("Erreur Block " + e.getMessage());
			e.printStackTrace();
			try {
				out.println("<p class=\"bg-danger\">" + e.getMessage() + "</p>");
			} catch (IOException ex) {
				logger.error("Erreur Block " + ex.getMessage());
				ex.printStackTrace();
			}
		}

	}
	
	/* Get blocks from container parentPageBlock if not null + activeObject if not null + page */ 
	public void getJspElement(PageContext pageContext, String templateName, String pageName, String activeObjectName, int pageId, int activeObjectId, Map<String, String> params) throws JspException{
		logger.debug("Enter in getJspBlock : pageContext = " + pageContext + "; templateName = " + templateName + "; pageName = " + pageName + "; activeObjectName = " + activeObjectName + "; pageId = " + pageId + "; activeObjectId = " + activeObjectId + "; params = " + params);
		JspWriter out = pageContext.getOut();
		try {
			if (CommonUtil.DEBUG) out.print("<p class=\"debug\">" + "Enter in getJSP()" + "</p>");

			Folder folder = getFolder(pageContext);
			Lang lang = getLang(pageContext);
			Page page = getPage(pageContext, folder, lang, pageId, pageName);
			Translation model = getModel(pageContext, page);
			Translation activeObject = getActiveObject(pageContext, folder, lang, activeObjectId, activeObjectName);

			Template activeElement = templateService.identify(folder, templateName, lang);
			getJsp(pageContext, AttributeConst.ACTIVEELEMENT, null, activeElement, folder, lang, page, model, activeObject, params);

			if (CommonUtil.DEBUG) out.print("<p class=\"debug\">" + "Exit getJSP()" + "</p>");
			
		} catch (ServiceException | IOException | UtilException | ResourceNotFoundException | ServletException e) {
			logger.error("Erreur Block " + e.getMessage());
			e.printStackTrace();
			try {
				out.println("<p class=\"bg-danger\">" + e.getMessage() + "</p>");
			} catch (IOException ex) {
				logger.error("Erreur Block " + ex.getMessage());
				ex.printStackTrace();
			}
		}

	}

	
	
	
}
