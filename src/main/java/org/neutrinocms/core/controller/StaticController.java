package org.neutrinocms.core.controller;

import org.neutrinocms.core.exception.ControllerException;
import org.neutrinocms.core.exception.ResourceNotFoundException;
import org.neutrinocms.core.model.independant.Folder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("prototype")
@RequestMapping(value = "/static")
public class StaticController extends AbstractController {

	@RequestMapping(value = "/static", method = RequestMethod.GET)
	public ModelAndView view(@ModelAttribute("p") String page, Folder folder) throws ResourceNotFoundException, ControllerException {
		return baseView(page, null, folder);
	}

}
