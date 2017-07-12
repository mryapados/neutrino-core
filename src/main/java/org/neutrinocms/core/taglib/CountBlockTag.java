package org.neutrinocms.core.taglib;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.tag.common.core.Util;
import org.neutrinocms.core.constant.AttributeConst;
import org.neutrinocms.core.exception.ServiceException;
import org.neutrinocms.core.model.independant.Folder;
import org.neutrinocms.core.model.translation.Page;
import org.neutrinocms.core.model.translation.Template;
import org.neutrinocms.core.model.translation.Translation;
import org.neutrinocms.core.service.PositionService;

public class CountBlockTag extends TagSupport {
	private static final long serialVersionUID = 1L;

	private String var; // 'var' attribute
	private int scope; // processed 'scope' attribute
	private String position = null;
	
	public CountBlockTag() {
		super();
		scope = PageContext.PAGE_SCOPE;
	}


	public int doStartTag() throws JspException {	
		try {
			PositionService positionService = (PositionService) pageContext.getAttribute(AttributeConst.POSITION_SERVICE_BEAN, PageContext.APPLICATION_SCOPE);
			
			Boolean blockPreview = (Boolean) pageContext.getAttribute(AttributeConst.BLOCKPREVIEW, PageContext.REQUEST_SCOPE);
			Folder folder = (Folder) pageContext.getAttribute(AttributeConst.FOLDER, PageContext.REQUEST_SCOPE);
			List<Translation> models = new ArrayList<>();
			Translation model = (Template) pageContext.getAttribute(AttributeConst.PARENTPAGEBLOCK, PageContext.REQUEST_SCOPE);
			Page page = (Page) pageContext.getAttribute(AttributeConst.ACTIVEPAGE, PageContext.REQUEST_SCOPE);
			if (model == null) model = page.getModel();
			models.add(model);
			
			Translation activeObject = (Translation) pageContext.getAttribute(AttributeConst.ACTIVEOBJECT, PageContext.REQUEST_SCOPE);
			if (activeObject != null){
				models.add(activeObject);
			}
			
			Integer count = positionService.countByNameForModelsWithMaps(models, position);
	
			if (var != null) pageContext.setAttribute(var, count, scope);
			if (count == 0 && !blockPreview) return SKIP_BODY;
			
			return EVAL_BODY_AGAIN;
		
		} catch (ServiceException e) {
			throw new JspTagException(e);
		}
	}
	

	public void setVar(String var) {
		this.var = var;
	}
	public void setScope(String scope) {
		this.scope = Util.getScope(scope);
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
}