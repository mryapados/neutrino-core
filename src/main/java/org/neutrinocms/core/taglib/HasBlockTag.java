package org.neutrinocms.core.taglib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.neutrinocms.core.exception.ServiceException;
import org.neutrinocms.core.model.independant.Folder;
import org.neutrinocms.core.model.translation.Page;
import org.neutrinocms.core.model.translation.Template;
import org.neutrinocms.core.model.translation.Translation;
import org.neutrinocms.core.service.PositionService;
import org.neutrinocms.core.taglib.IIncludeJSP.Attributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Deprecated
@Component
@Scope(value = "singleton")
public class HasBlockTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(HasBlockTag.class);

	private String position = null;
	private String var = null;
	private String scope = null;
	
	private static PositionService positionService;
	@Autowired
	public void PositionService(PositionService positionService) {
		HasBlockTag.positionService = positionService;
	}
	
	public int doStartTag() {
		logger.debug("Enter in doStartTag()");
		JspWriter out = pageContext.getOut();
		try {
			Integer contextScope = PageContext.PAGE_SCOPE;
			if (scope != null){
				switch (scope.toUpperCase()) {
					case "SESSION":
						contextScope = PageContext.SESSION_SCOPE;
						break;
					case "REQUEST":
						contextScope = PageContext.REQUEST_SCOPE;
						break;
					case "APPLICATION":
						contextScope = PageContext.APPLICATION_SCOPE;
						break;
					default:
						contextScope = PageContext.PAGE_SCOPE;
						break;
					}
			}
			
			
			Folder folder = (Folder) pageContext.getAttribute(Attributes.FOLDER.toString(), PageContext.REQUEST_SCOPE);
			List<Translation> models = new ArrayList<>();
			Translation model = (Template) pageContext.getAttribute(Attributes.PARENTPAGEBLOCK.toString(), PageContext.REQUEST_SCOPE);
			Page page = (Page) pageContext.getAttribute(Attributes.ACTIVEPAGE.toString(), PageContext.REQUEST_SCOPE);
			if (model == null) model = page.getModel();
			models.add(model);
			
			Translation activeObject = (Translation) pageContext.getAttribute(Attributes.ACTIVEOBJECT.toString(), PageContext.REQUEST_SCOPE);
			if (activeObject != null){
				models.add(activeObject);
			}
			
			System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGGG");
			System.out.println(position);
			System.out.println(model.getId());
			Integer count = positionService.countByNameForModelsWithMaps(models, position);
			
			Integer result = 0;
			if (count > 0) result = 1;
			
			pageContext.setAttribute(var, result, contextScope);

		} catch (ServiceException e) {
			try {
				out.println("<p class=\"bg-danger\">" + e.getMessage() + "</p>");
			} catch (IOException ex) {
				logger.error("Erreur Block " + ex.getMessage());
				ex.printStackTrace();
			}
		} 
		
		return SKIP_BODY;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}

}