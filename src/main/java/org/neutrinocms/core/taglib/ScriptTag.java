package org.neutrinocms.core.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import org.apache.log4j.Logger;

public class ScriptTag extends BodyTagSupport  {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(ScriptTag.class);
		
	private String src = null;
	
	public int doStartTag() throws JspException {
		logger.debug("Enter in doStartTag()");
		
		// Un tag ScriptTag ne doit pas �tre dans un tag CacheTag car sinon, il ne sera pas pr�sent lorsque le contenu du tag CacheTag est issu du cache
		Tag findAncestorWithClass = findAncestorWithClass(this, CacheTag.class);
		if (findAncestorWithClass != null) throw new JspTagException(ScriptTag.class + " can't be in a " + CacheTag.class);

		if (src != null){
			String engineScript = (String) pageContext.getAttribute("NEngineScript", PageContext.REQUEST_SCOPE);
			if (engineScript == null) engineScript = "";
			String newLine = System.getProperty("line.separator");
			engineScript += newLine + "<script src=\"" + src + "\"></script>";
			pageContext.setAttribute("NEngineScript", engineScript, PageContext.REQUEST_SCOPE);
			return SKIP_BODY;
		}
		return EVAL_BODY_BUFFERED;
	}
	
	public int doAfterBody() {
		logger.debug("Enter in doAfterBody()");
		String engineScript = (String) pageContext.getAttribute("NEngineScript", PageContext.REQUEST_SCOPE);
		if (engineScript == null) engineScript = "";
		String newLine = System.getProperty("line.separator");
		BodyContent bodyContent = getBodyContent();
		engineScript += newLine + "<script type=\"text/javascript\">" + bodyContent.getString() + "</script>";
		pageContext.setAttribute("NEngineScript", engineScript, PageContext.REQUEST_SCOPE);
		return SKIP_BODY;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	
	
	
	

}