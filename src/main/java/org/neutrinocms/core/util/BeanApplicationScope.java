package org.neutrinocms.core.util;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.neutrinocms.core.conf.NeutrinoCoreProperties;
import org.neutrinocms.core.constant.AttributeConst;
import org.neutrinocms.core.service.CacheService;
import org.neutrinocms.core.service.PageService;
import org.neutrinocms.core.service.PositionService;
import org.neutrinocms.core.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeanApplicationScope {	
	private Logger logger = Logger.getLogger(BeanApplicationScope.class);
	
	@Autowired
	ServletContext servletContext;
	@Autowired
	private NeutrinoCoreProperties neutrinoCoreProperties;
	@Autowired
	private CommonUtil commonUtil;
	@Autowired
	private PageService pageService;
	@Autowired
	private TemplateService templateService;
	@Autowired
	private CacheService cacheService;
	@Autowired
	private PositionService positionService;
	@Autowired
	private JspTagUtil jspTagUtil;
	@Autowired
	private IdProviderUtil idProviderUtil;
	
	@PostConstruct
	public void addBeanToApplicationScope(){
		logger.debug("Enter in addBeanToApplicationScope");
		servletContext.setAttribute(AttributeConst.APPLICATION_PROPERTIES_BEAN, neutrinoCoreProperties);
		servletContext.setAttribute(AttributeConst.COMMON_UTIL_BEAN, commonUtil);
		servletContext.setAttribute(AttributeConst.CACHE_SERVICE_BEAN, cacheService);
		servletContext.setAttribute(AttributeConst.PAGE_SERVICE_BEAN, pageService);
		servletContext.setAttribute(AttributeConst.TEMPLATE_SERVICE_BEAN, templateService);
		servletContext.setAttribute(AttributeConst.CACHE_SERVICE_BEAN, cacheService);
		servletContext.setAttribute(AttributeConst.POSITION_SERVICE_BEAN, positionService);
		servletContext.setAttribute(AttributeConst.JSP_TAG_UTIL_BEAN, jspTagUtil);
		servletContext.setAttribute(AttributeConst.ID_PROVIDER_UTIL_BEAN, idProviderUtil);
		
	
		
	}

}
