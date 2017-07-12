package org.neutrinocms.core.taglib;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.neutrinocms.core.constant.AttributeConst;
import org.neutrinocms.core.model.independant.Folder;
import org.neutrinocms.core.util.CommonUtil;
import org.neutrinocms.core.util.CommonUtil.TypeBase;

public class InitTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(InitTag.class);
	
	private static final String FOLDER = "folder";
	

	private Boolean test = null;
	
	public int doStartTag() {
		logger.debug("Enter in doStartTag()");
		if (test){
			JspWriter out = pageContext.getOut();
			try {
				CommonUtil commonUtil = (CommonUtil) pageContext.getAttribute(AttributeConst.COMMON_UTIL_BEAN, PageContext.APPLICATION_SCOPE);
				
				pageContext.include(commonUtil.getBasePath(true, null, TypeBase.ADMIN) + "components/init.jsp");
				
				Folder folder = (Folder) pageContext.getAttribute(FOLDER, PageContext.REQUEST_SCOPE);
				pageContext.include(commonUtil.getBasePath(true, folder, TypeBase.COMMON) + "components/init.jsp");
				
				
				pageContext.setAttribute("initialized", true, PageContext.REQUEST_SCOPE);
			} catch (IOException | ServletException e) {
				try {
					out.println("<p class=\"bg-danger\">" + e.getMessage() + "</p>");
				} catch (IOException ex) {
					logger.error("Erreur Block " + ex.getMessage());
					ex.printStackTrace();
				}
			}
		}
		return EVAL_BODY_INCLUDE;
	}

	public Boolean getTest() {
		return test;
	}

	public void setTest(Boolean test) {
		this.test = test;
	}
	
}