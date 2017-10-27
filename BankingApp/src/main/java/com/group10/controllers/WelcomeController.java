package com.group10.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WelcomeController {

	@RequestMapping("/welcomepage")
	public ModelAndView mymethod(HttpServletRequest request){
		ModelAndView model =new ModelAndView();
		String buf = "hello world";
		model.addObject("message", buf);
		model.setViewName("welcomepage");
		return model;
	}
}
