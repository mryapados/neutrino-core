package org.neutrinocms.core.taglib;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.neutrinocms.core.constant.AttributeConst;
import org.neutrinocms.core.model.Authority;
import org.neutrinocms.core.model.AuthorityName;
import org.neutrinocms.core.model.independant.Folder;
import org.neutrinocms.core.model.independant.User;
import org.neutrinocms.core.util.CommonUtil;
import org.neutrinocms.core.util.CommonUtil.TypeBase;


public class HeadTag extends TagSupport {
	
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(HeadTag.class);
	
	private static final String FOLDER = "folder";
	private static final String SURFER = "surfer";

	public int doStartTag() {
		logger.debug("Enter in doStartTag()");
		JspWriter out = pageContext.getOut();
		try {
			
			out.println("<head>");
			
		} catch (IOException e) {
			try {
				out.println("<p class=\"bg-danger\">" + e.getMessage() + "</p>");
			} catch (IOException ex) {
				logger.error("Erreur Block " + ex.getMessage());
				ex.printStackTrace();
			}
		}
		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() {
		logger.debug("Enter in doEndTag()");
		JspWriter out = pageContext.getOut();
		CommonUtil commonUtil = (CommonUtil) pageContext.getAttribute(AttributeConst.COMMON_UTIL_BEAN, PageContext.APPLICATION_SCOPE);
		try {
			Folder folder = (Folder) pageContext.getAttribute(FOLDER, PageContext.REQUEST_SCOPE);
			pageContext.include(commonUtil.getBasePath(true, folder, TypeBase.COMMON) + "components/css.jsp");
			User surfer = (User) pageContext.getAttribute(SURFER, PageContext.REQUEST_SCOPE);
			
			boolean isAdmin = false;
			List<Authority> authorities = surfer.getAuthorities();
			for (Authority authority : authorities) {
				if (authority.getName().equals(AuthorityName.ROLE_ADMIN)) {
					isAdmin = true;
				}
			}
			
			if (isAdmin){
				out.println("<link href=\"" + commonUtil.getContextPath() + "/style/app.css\" rel=\"stylesheet\">");
			} 
			out.println("</head>");
		} catch (IOException | ServletException e) {
			try {
				out.println("<p class=\"bg-danger\">" + e.getMessage() + "</p>");
			} catch (IOException ex) {
				logger.error("Erreur Block " + ex.getMessage());
				ex.printStackTrace();
			}
		}
		return EVAL_BODY_INCLUDE;
	}
	

	
}