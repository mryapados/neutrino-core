package org.neutrinocms.core.taglib;

import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.neutrinocms.core.util.CommonUtil;

public class DebugTag extends TagSupport  {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(DebugTag.class);
	
	public int doStartTag() {
		if (CommonUtil.DEBUG){
			return EVAL_BODY_AGAIN;
		} else {
			return SKIP_BODY;
		}
	}
	

}