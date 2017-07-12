package org.neutrinocms.core.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.apache.taglibs.standard.tag.common.core.Util;
import org.neutrinocms.core.constant.AttributeConst;
import org.neutrinocms.core.exception.UtilException;
import org.neutrinocms.core.util.IdProviderUtil;

public class BindTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(BindTag.class);
	
	private String var;                 
	private int scope;
	private String type;
	private int beanId;
	private String field;
	private boolean cache;
	private boolean escapeXml;		// tag attribute
	
	public BindTag() {
		super();
		init();
	}

	private void init() {
		var = null;
		cache = true;
	    scope = PageContext.PAGE_SCOPE;
	    escapeXml = true;
	}

	public int doStartTag() throws JspException {
		logger.debug("Enter in doStartTag()");
		try {
	    	IdProviderUtil idProviderUtil = (IdProviderUtil) pageContext.getAttribute(AttributeConst.ID_PROVIDER_UTIL_BEAN, PageContext.APPLICATION_SCOPE);

			Object result = idProviderUtil.getIdProviderFieldValue(type, beanId, field, cache);
			if (escapeXml && result instanceof String) result = (String) Util.escapeXml((String) result);

			if (var != null) pageContext.setAttribute(var, result, scope);
			else pageContext.getOut().print(result);

		} catch (IOException | UtilException e) {
			throw new JspTagException(e);
		}
		return SKIP_BODY;
	}
	
	public void setVar(String var) {
		this.var = var;
	}

	public void setScope(String scope) {
		this.scope = Util.getScope(scope);
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setBeanId(int beanId) {
		this.beanId = beanId;
	}

	public void setField(String field) {
		this.field = field;
	}

	public void setCache(boolean cache) {
		this.cache = cache;
	}
	
    public void setEscapeXml(boolean escapeXml) {
        this.escapeXml = escapeXml;
    }

	
	
	
}