package org.neutrinocms.core.taglib;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.apache.taglibs.standard.tag.common.core.Util;
import org.neutrinocms.core.constant.AttributeConst;
import org.neutrinocms.core.exception.ServiceException;
import org.neutrinocms.core.model.translation.Translation;
import org.neutrinocms.core.service.TObjectService;

public class TranslationsTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(TranslationsTag.class);
	
	private String var;                 
	private int scope;
	private String type;
	private int beanId;
	private boolean active;
	
	public TranslationsTag() {
		super();
		init();
	}

	private void init() {
		var = null;
		active = true;
	    scope = PageContext.PAGE_SCOPE;
	}

	public int doStartTag() throws JspException {
		logger.debug("Enter in doStartTag()");
		try {
	    	TObjectService tObjectService = (TObjectService) pageContext.getAttribute(AttributeConst.TOBJECT_SERVICE_BEAN, PageContext.APPLICATION_SCOPE);

			List<Translation> result = tObjectService.getTranslations(beanId, active);

			if (var != null) pageContext.setAttribute(var, result, scope);
			else pageContext.getOut().print(result);

		} catch (IOException | ServiceException e) {
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
	
	public void setBeanId(int beanId) {
		this.beanId = beanId;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
}