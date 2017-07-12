package org.neutrinocms.core.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.neutrinocms.core.exception.ControllerException;
import org.neutrinocms.core.exception.ResourceNotFoundException;
import org.neutrinocms.core.exception.UtilException;
import org.neutrinocms.core.model.independant.Folder;
import org.neutrinocms.core.util.CommonUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController extends AbstractController {
	public static final String LOGINPAGE = "login";
	public static final String BACKFOLDER = "back";
	
    @RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView view(Folder folder) throws ControllerException, ResourceNotFoundException  {
		ModelAndView modelAndView = null;
		try {
			try {
				modelAndView = baseView(LOGINPAGE, null, folder);
			} catch (ResourceNotFoundException e1) {
				folder = common.getFolder(CommonUtil.BACK);
				modelAndView = baseView(LOGINPAGE, null, folder);
			}
			return modelAndView;
		} catch (UtilException e) {
			throw new ControllerException(e);
		}
		
	}
    
    
    
    
    
    
    
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }
	
	
	

}
