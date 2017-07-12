package org.neutrinocms.core.controller;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.neutrinocms.core.exception.ControllerException;
import org.neutrinocms.core.exception.JSPNotFoundException;
import org.neutrinocms.core.exception.ResourceNotFoundException;
import org.neutrinocms.core.exception.ServiceException;
import org.neutrinocms.core.exception.UtilException;
import org.neutrinocms.core.model.independant.Folder;
import org.neutrinocms.core.model.independant.User;
import org.neutrinocms.core.model.translation.Page;
import org.neutrinocms.core.model.translation.Template;
import org.neutrinocms.core.model.translation.Translation;
import org.neutrinocms.core.service.TemplateService;
import org.neutrinocms.core.service.UserService;
import org.neutrinocms.core.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javassist.compiler.ast.Member;

@Controller
@SessionAttributes( value = AbstractController.ATTR_BLOCKPREVIEW, types={Boolean.class} )
public abstract class AbstractController {
	
	private Logger logger = Logger.getLogger(AbstractController.class);
	
	protected static final String ATTR_BLOCKPREVIEW = "blockPreview";
	
	protected static final String ATTR_ACTIVELANG = "activeLang";
	protected static final String ATTR_ACTIVEPAGE = "activePage";
	protected static final String ATTR_ACTIVEOBJECT = "activeObject";
	protected static final String ATTR_TEMPLATE = "template";
	protected static final String ATTR_INITIALIZED = "initialized";
	protected static final String ATTR_FOLDER = "folder";
	protected static final String ATTR_LANGUAGE = "language";

	@Autowired
	private UserService userService;
	
	@Autowired
	private TemplateService templateService;
	
	@Autowired
	protected CommonUtil common;
	
	@ModelAttribute("surfer")
	public User addUserToScope() throws ServiceException {
		logger.debug("Enter in addUserToScope");
		User user = null;
		if (isAuthenticated()){
			logger.debug("user is authenticated");
			org.springframework.security.core.userdetails.User userDetail = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			logger.debug("user is null ? " + (user == null));
			if (user == null || !user.getLogin().equals(userDetail.getUsername())){
				user = userService.findByLogin(userDetail.getUsername());
				logger.debug("user updated");
			}
		} else {
			user = new User();
			user.setEnabled(true);
			user.setRole(User.ROLE_PUBLIC);
		}
		return user;
	}
	private boolean isAuthenticated(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
	}
	@ModelAttribute(ATTR_BLOCKPREVIEW)
	public Boolean addBlockPreviewToSessionScope() throws ServiceException {
		logger.debug("Enter in addBlockPreviewToSessionScope");
		return false;
	}

	public ModelAndView baseView(String pageName, Folder folder) throws ControllerException, ResourceNotFoundException  {
		return baseView(pageName, null, folder);
	}
	public ModelAndView baseView(String pageName, Translation activeObject, Folder folder) throws ControllerException, ResourceNotFoundException {
		logger.debug("Enter in baseView : pageName = " + pageName + "; activeObject = " + activeObject + " folder = " + folder);
		try {
			Locale locale = LocaleContextHolder.getLocale();
			return baseView(common.getPage(folder, pageName, common.getLang(locale.getLanguage())), activeObject, folder);
		} catch (UtilException e) {
			throw new ControllerException(e);
		}
	}
	
	public ModelAndView baseView(Page page, Translation activeObject, Folder folder) throws ControllerException, ResourceNotFoundException {
		Template model = page.getModel();
		ModelAndView modelAndView = baseView(page, model, activeObject, folder);
		return modelAndView;
	}
	
	public ModelAndView baseView(Page page, Template template, Translation activeObject, Folder folder) throws ControllerException, ResourceNotFoundException {
		logger.debug("Enter in baseView : page = " + page + "; template = " + template + "; activeObject = " + activeObject + " folder = " + folder);
		try {
			String pathModelAndView = templateService.getPathJSP(true, folder, page.getContext(), template);
			ModelAndView modelAndView = new ModelAndView(pathModelAndView);
			modelAndView.addObject(ATTR_ACTIVEPAGE, page);
			modelAndView.addObject(ATTR_ACTIVEOBJECT, activeObject);
			modelAndView.addObject(ATTR_TEMPLATE, template);
			modelAndView.addObject(ATTR_INITIALIZED, false);
			modelAndView.addObject(ATTR_FOLDER, folder);
			Locale locale = LocaleContextHolder.getLocale();
			modelAndView.addObject(ATTR_LANGUAGE, locale.getLanguage());
			modelAndView.addObject(ATTR_ACTIVELANG, common.getLang(locale.getLanguage()));
			return modelAndView;
		} catch (JSPNotFoundException e) {
			throw new ResourceNotFoundException(e);
		} catch (ServiceException | UtilException e) {
			throw new ControllerException(e);
		}
	}
	
}
