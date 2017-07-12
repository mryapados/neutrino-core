package org.neutrinocms.core.controller;

import org.neutrinocms.core.exception.ServiceException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/@action")
public class ActionController extends AbstractController {

	@Secured({ "ROLE_WEBMASTER", "ROLE_ADMIN", "ROLE_USER" })
	@RequestMapping(value = "/setpreview", method = RequestMethod.POST)
	public ModelAndView setPreview() throws ServiceException {
		return new ModelAndView("admin/action/setPreview");
    }
	

}
