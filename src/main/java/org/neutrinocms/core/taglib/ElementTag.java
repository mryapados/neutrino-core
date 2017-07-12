package org.neutrinocms.core.taglib;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.apache.taglibs.standard.tag.common.core.ParamParent;
import org.neutrinocms.core.constant.AttributeConst;
import org.neutrinocms.core.service.PositionService;
import org.neutrinocms.core.util.JspTagUtil;

public class ElementTag extends TagSupport implements IIncludeJSP, ParamParent {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(ElementTag.class);

	private PositionService positionService;
	private JspTagUtil jspTagUtil;
	
	private String template;
	private String page;
	private String activeObject;
	private int pageId;
	private int activeObjectId;
	private Map<String, String> params;	 // added parameters
	
	public ElementTag() {
		super();
		init();
	}
	
    private void init() {
    	template = null;
    	page  = null;
    	activeObject = null;
    	pageId = 0;
    	activeObjectId = 0;
    	params = null;
    }
    
    @Override
    public void addParameter(String name, String value) {
    	params.put(name, value);
    }

	public int doStartTag() throws JspException {
		logger.debug("Enter in doStartTag()");
		
    	positionService = (PositionService) pageContext.getAttribute(AttributeConst.POSITION_SERVICE_BEAN, PageContext.APPLICATION_SCOPE);
    	jspTagUtil = (JspTagUtil) pageContext.getAttribute(AttributeConst.JSP_TAG_UTIL_BEAN, PageContext.APPLICATION_SCOPE);
		
		params = new HashMap<>();
		getJsp();
		return EVAL_BODY_INCLUDE;
	}
	
	public void getJsp() throws JspException{
		logger.debug("Enter in getJsp()");
		jspTagUtil.getJspElement(pageContext, template, page, activeObject, pageId, activeObjectId, params);
	}
	
	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getActiveObject() {
		return activeObject;
	}

	public void setActiveObject(String activeObject) {
		this.activeObject = activeObject;
	}

	public int getPageId() {
		return pageId;
	}

	public void setPageId(int pageId) {
		this.pageId = pageId;
	}

	public int getActiveObjectId() {
		return activeObjectId;
	}

	public void setActiveObjectId(int activeObjectId) {
		this.activeObjectId = activeObjectId;
	}


	
	

}