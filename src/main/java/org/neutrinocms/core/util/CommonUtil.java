package org.neutrinocms.core.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.neutrinocms.core.conf.NeutrinoCoreProperties;
import org.neutrinocms.core.exception.ResourceNotFoundException;
import org.neutrinocms.core.exception.ServiceException;
import org.neutrinocms.core.exception.UtilException;
import org.neutrinocms.core.model.independant.Folder;
import org.neutrinocms.core.model.translation.Lang;
import org.neutrinocms.core.model.translation.Page;
import org.neutrinocms.core.service.FolderService;
import org.neutrinocms.core.service.LangService;
import org.neutrinocms.core.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "singleton")
public class CommonUtil {	
	private Logger logger = Logger.getLogger(CommonUtil.class);
	
	private Map<String, Page> pages;
	
	private Map<String, Folder> foldersByName;
	private Map<String, Folder> foldersByServerName;
	
	private Map<String, Lang> langs;
		
	private String contextPath;
	private String webInfFolder;
	
	@Autowired
	ServletContext servletContext;
	
	@Autowired
	private FolderService folderService;
	
	@Autowired
	private PageService pageService;

	@Autowired
	protected LangService langService;
	
	@Autowired
	private NeutrinoCoreProperties neutrinoCoreProperties;	

	public enum TypeBase {
		VIEWS("views/"),
		COMMON("common/"),
		ADMIN("admin/")
	    ;
	    private final String text;
	    private TypeBase(final String text) {
	        this.text = text;
	    }
	    @Override
	    public String toString() {
	        return text;
	    }
	}
	
	public static final Boolean DEBUG = false;
	
	public static final String BACK = "back";
	public static final String FRONT = "front";
	
	public static final String BASE_WEBINF = "/WEB-INF/";
	
	public static final String BO_URL = "@bo/";
	public static final String BO_FILE_URL = "file/";
	
	
	public void init(){
		logger.debug("Enter in init");
		foldersByName = new HashMap<>();
		foldersByServerName = new HashMap<>();
		pages = new HashMap<>();
		langs = new HashMap<>();
	}
	
	public boolean jspExist(String path) throws UtilException {
		try {
			URL resource = servletContext.getResource(BASE_WEBINF + path + ".jsp");
			if (resource == null) return false;
		} catch (MalformedURLException e) {
			throw new UtilException(e);
		}
		return true;
	}
	
	@Deprecated
	public String getWebInfFolder() {
		if (webInfFolder == null) {
			webInfFolder = servletContext.getRealPath(BASE_WEBINF);
		}
		return webInfFolder;
	}
	
	private void addFolder(String serverName) throws UtilException {
		Folder folder = folderService.findByServerName(serverName);
		String folderName = folder.getName();
		if (foldersByName.containsKey(folderName)) {
			folder = foldersByName.get(folderName);
		} else {
			foldersByName.put(folderName, folder);
		}
		foldersByServerName.put(serverName, folder);
	}

	public Folder getFolder(String serverName) throws UtilException, ResourceNotFoundException {
		logger.debug("Enter in getFolder : serverName = " + serverName);
		if (foldersByName == null) {
			foldersByName = new HashMap<>();
			foldersByServerName = new HashMap<>();
		}
		if (!foldersByServerName.containsKey(serverName)) {
			addFolder(serverName);
		}
		Folder folder = foldersByServerName.get(serverName);
		if (folder == null) throw new ResourceNotFoundException("Folder not found from servername " + serverName + " !");
		return folder;
	}

	public Lang getLang(String langCode) throws UtilException, ResourceNotFoundException {
		logger.debug("Enter in getLang : langCode = " + langCode);
		try {
			if (langs == null) {
				langs = new HashMap<>();
			}
			if (!langs.containsKey(langCode)) {
				Lang lang = langService.findByCode(langCode);
				if (lang != null)
					langs.put(langCode, lang);
			}
			Lang lang = langs.get(langCode);
			if (lang == null) throw new ResourceNotFoundException("lang not found from code " + langCode + " !");
			return lang;
		} catch (ServiceException e) {
			throw new UtilException(e);
		}
	}

	public Page getPage(Folder folder, String pageName, Lang lang) throws UtilException, ResourceNotFoundException {
		logger.debug("Enter in getPage : folder = " + folder + "; pageName = " + pageName + "; lang = " + lang);
		try {
			String pageNameLong = (folder.getName() + "_" + pageName + "_" + lang.getCode()).toUpperCase();
			if (pages == null) {
				pages = new HashMap<>();
			}
			if (!pages.containsKey(pageNameLong)) {
				Page page = pageService.identify(folder, pageName, lang);
				if (page != null)
					pages.put(pageNameLong, page);
			}
			Page page = pages.get(pageNameLong);
			if (page == null) {
				String folderString = "";
				if (folder != null) folderString = " folder = " + folder.getName();

				String langString = "";
				if (lang != null) langString = " lang = " + lang.getName();
				throw new ResourceNotFoundException("Page " + pageName + " not found ! " + folderString + " " + langString);
			}
			return page;
		} catch (ServiceException e) {
			throw new UtilException(e);
		}
	}
	
	public String getBasePath(Boolean webInf, Folder folder, TypeBase typeBase){
		return (webInf ? BASE_WEBINF : "") + 
			   (folder != null ? folder.getPath() : "") + 
			   (typeBase.toString());
	}
	

	public String getContextPath() {
		if (contextPath == null) contextPath = servletContext.getContextPath();
		return contextPath;
	}


}
