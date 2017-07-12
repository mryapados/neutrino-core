package org.neutrinocms.core.taglib;

import javax.servlet.jsp.JspException;

import org.apache.taglibs.standard.tag.common.core.OutSupport;
import org.jsoup.Jsoup;

public class StripTagTag extends OutSupport {

	private static final long serialVersionUID = 1L;

	public int doStartTag() throws JspException {
    	if (value instanceof String) value = Jsoup.parse((String) value).text(); 
    	return super.doStartTag();
    }

    public void setValue(Object value) {
        this.value = value;
    }
    public void setDefault(String def) {
        this.def = def;
    }
    public void setEscapeXml(boolean escapeXml) {
        this.escapeXml = escapeXml;
    }

}