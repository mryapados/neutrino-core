package org.neutrinocms.core.taglib;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

public class DiscriminTag extends TagSupport {
	
	/* Le code JSTL contenu entre le tag <discrimin>
	 * n'est affich� qu'une fois en fonction de son
	 * attribut name et scope.
	 */

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(DiscriminTag.class);

	private String name = null;
	private String scope = null;
	
	public int doStartTag() {
		logger.debug("Enter in doStartTag()");
		Integer contextScope = PageContext.REQUEST_SCOPE;
		if (scope !=null){
			switch (scope.toUpperCase()) {
				case "SESSION":
					contextScope = PageContext.SESSION_SCOPE;
					break;
				case "PAGE":
					contextScope = PageContext.PAGE_SCOPE;
					break;
				case "APPLICATION":
					contextScope = PageContext.APPLICATION_SCOPE;
					break;
				default:
					contextScope = PageContext.REQUEST_SCOPE;
					break;
				}
		}
		List<String> discriminators = (ArrayList<String>) pageContext.getAttribute("NEngineDiscriminator", contextScope);
		if (discriminators != null && discriminators.contains(name)){
			return SKIP_BODY;
		}
		else {
			if (discriminators == null){
				discriminators = new ArrayList<>();
			}
			discriminators.add(name);
			pageContext.setAttribute("NEngineDiscriminator", discriminators, contextScope);
			return EVAL_BODY_AGAIN;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}



}