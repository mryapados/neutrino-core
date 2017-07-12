package org.neutrinocms.core.taglib;

import javax.servlet.jsp.JspException;

public interface IIncludeJSP {
	
	@Deprecated
	public enum Attributes {
		ACTIVEBLOCK("activeBlock"),
		ACTIVEOBJECT("activeObject"),
		BLOCKPREVIEW("blockPreview"),
		FOLDER("folder"),
		ACTIVEPAGE("activePage"),
		PARENTPAGEBLOCK("parentPageBlock"),
		SURFER("surfer")
		;
	    private final String attribute;
	    private Attributes(final String attribute) {
	        this.attribute = attribute;
	    }
	    @Override
	    public String toString() {
	        return attribute;
	    }
	}
	
	public void getJsp() throws JspException;
	
}
