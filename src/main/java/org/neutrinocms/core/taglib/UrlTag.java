package org.neutrinocms.core.taglib;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;

import org.apache.taglibs.standard.tag.common.core.UrlSupport;
import org.neutrinocms.core.constant.AttributeConst;
import org.neutrinocms.core.exception.UtilException;
import org.neutrinocms.core.model.IdProvider;
import org.neutrinocms.core.model.independant.Folder;
import org.neutrinocms.core.util.IdProviderUtil;

public class UrlTag extends UrlSupport {
	private static final long serialVersionUID = 1L;

	private IdProvider bean;
	private IdProviderUtil idProviderUtil;
	

	private String getFieldResult(IdProvider object, String expression) throws JspException{
		try {
			String[] fields = expression.split("\\.");
			IdProvider result = object;
			for (int i = 0; i <= fields.length - 1; i++) {
				Object obj = idProviderUtil.getIdProviderFieldValue(result, fields[i], false);
				if (i == fields.length - 1){
					if (obj instanceof IdProvider){
						return ((IdProvider) result).getId().toString();
					} else {
						return obj.toString();
					}
				} else {
					result = (IdProvider) obj;
				}
			}
		} catch (UtilException e) {
			throw new JspTagException(e);
		}
		throw new JspTagException("can't get field value for '" + expression + "'");
	}
	
	/* Parse expression with IdProvider fields value
	 * Return String
	 * Throw JspException if UtilException from IdProviderUtil
	 */
	private void parseUrl() throws JspException {
		idProviderUtil = (IdProviderUtil) pageContext.getAttribute(AttributeConst.ID_PROVIDER_UTIL_BEAN, PageContext.APPLICATION_SCOPE);
		Pattern pattern = java.util.regex.Pattern.compile("\\{(.*?)\\}");
		while (true) {
			Matcher matcher = pattern.matcher(value);
			if (matcher.find()) {
				value = matcher.replaceFirst(getFieldResult(bean, matcher.group(1)));
			} else break;
		}
	}

	@Override
	public int doStartTag() throws JspException {
		if (bean != null){
			parseUrl();
		}
		//Set servername to url if is not provided in current url;
		int doStartTag = super.doStartTag();
		Folder folder = (Folder) pageContext.getAttribute(AttributeConst.FOLDER, PageContext.REQUEST_SCOPE);
		if (folder.isServerNameForced()) addParameter(AttributeConst.SERVERNAME, folder.getName());
		return doStartTag;
	}

	public void setValue(String value) throws JspTagException {
		this.value = value;
	}

	// for tag attribute
	public void setContext(String context) throws JspTagException {
		this.context = context;
	}

	public void setBean(Object bean) {
		this.bean = (IdProvider) bean;
	}

}