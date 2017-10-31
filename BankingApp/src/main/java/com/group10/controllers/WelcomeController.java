package com.group10.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WelcomeController {

	@RequestMapping("/welcome")
	public ModelAndView mymethod(){
		return new ModelAndView("welcomepage");
	}

	@RequestMapping("/")
	public ModelAndView loginRedirect(){
		return new ModelAndView("redirect:/login");
	}

}
