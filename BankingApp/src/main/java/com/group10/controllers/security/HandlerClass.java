package com.group10.controllers.security;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HandlerClass extends RuntimeException {
	
	@RequestMapping("/exception")
		public ModelAndView Exception(RedirectAttributes redir, HttpServletRequest request, HttpServletResponse response){
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		    if (auth != null){    
		        new SecurityContextLogoutHandler().logout(request, response, auth);
		    }
		    return new ModelAndView("redirect:/error");
	}
	
	@RequestMapping("/error")
	public ModelAndView loadErrorPage() {
		return new ModelAndView("Extra/error");
	}
}
