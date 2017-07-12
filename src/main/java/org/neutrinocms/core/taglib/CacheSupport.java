package org.neutrinocms.core.taglib;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;
import org.neutrinocms.core.constant.AttributeConst;
import org.neutrinocms.core.model.independant.Folder;
import org.neutrinocms.core.model.translation.Lang;
import org.neutrinocms.core.model.translation.Page;
import org.neutrinocms.core.model.translation.Translation;
import org.neutrinocms.core.service.CacheService;

public class CacheSupport extends BodyTagSupport implements Serializable {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(CacheSupport.class);

	protected String key = null;

	private CacheService cacheService;

	private int hashCode = 0;
	private String content = null;
	private boolean done = false;
	private boolean blockPreview = false;
	
	private void init() {
		done = false;
		content = null;
		blockPreview = (Boolean) pageContext.getAttribute(AttributeConst.BLOCKPREVIEW, PageContext.REQUEST_SCOPE);
		if (blockPreview) return;
		cacheService = (CacheService) pageContext.getAttribute(AttributeConst.CACHE_SERVICE_BEAN, PageContext.APPLICATION_SCOPE);

		mkHashCode();
	}

	@Override
	public void doInitBody() throws JspException {

	}

	@Override
	public int doStartTag() throws JspException {
		logger.debug("Enter in doStartTag()");
		try {
			init();
			if (blockPreview) return EVAL_BODY_BUFFERED;
			
			contentFromCache();
			if (content != null) {
				pageContext.getOut().print(content);
				done = true;
				return SKIP_BODY;
			}
			return EVAL_BODY_BUFFERED;
		} catch (IOException e) {
			throw new JspTagException(e);
		}
	}

	@Override
	public int doEndTag() throws JspException {
		logger.debug("Enter in doEndTag()");
		try {
			if (!done) {
				contentAndCache();
				pageContext.getOut().print(content);
			}
			return super.doEndTag();
		} catch (IOException e) {
			throw new JspTagException(e);
		}

	}

	private void mkHashCode() {
		if (key != null) {
			hashCode = key.hashCode();
		} else {
			String pageClass = pageContext.getPage().getClass().toString();
		
			Folder folder = (Folder) pageContext.getAttribute(AttributeConst.FOLDER, PageContext.REQUEST_SCOPE);
			Lang lang = (Lang) pageContext.getAttribute(AttributeConst.ACTIVELANG, PageContext.REQUEST_SCOPE);
			Page page = (Page) pageContext.getAttribute(AttributeConst.ACTIVEPAGE, PageContext.REQUEST_SCOPE);
			Translation activeObject = (Translation) pageContext.getAttribute(AttributeConst.ACTIVEOBJECT, PageContext.REQUEST_SCOPE);

			final int prime = 31;
			hashCode = 1;
			hashCode = prime * hashCode + ((pageClass == null) ? 0 : pageClass.hashCode());
			hashCode = prime * hashCode + ((folder == null) ? 0 : folder.hashCode());
			hashCode = prime * hashCode + ((lang == null) ? 0 : lang.hashCode());
			hashCode = prime * hashCode + ((page == null) ? 0 : page.hashCode());
			hashCode = prime * hashCode + ((activeObject == null) ? 0 : activeObject.hashCode());
		}
	}

	private void contentFromCache() throws JspException {
		logger.debug("Enter in contentFromCache");
		try {
			content = cacheService.getContentFromCache(hashCode);
		} catch (IOException e) {
			throw new JspTagException(e);
		}
	}

	private void contentAndCache() throws JspException {
		content = getBodyContent().getString();
		if (!blockPreview) mkCachedFile();
	}

	private void mkCachedFile() throws JspException {
		logger.debug("Enter in contentFromBodyTag");
		try {
			cacheService.mkCachedFile(hashCode, content);
		} catch (IOException e) {
			throw new JspTagException(e);
		}
	}

	public void setKey(String key) {
		this.key = key;
	}
}