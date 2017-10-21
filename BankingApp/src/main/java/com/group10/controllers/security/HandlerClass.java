package com.group10.controllers.security;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HandlerClass extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@RequestMapping("/exception")
		public String Exception(RedirectAttributes redir, HttpServletRequest request, HttpServletResponse response){
			 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			    if (auth != null){    
			        new SecurityContextLogoutHandler().logout(request, response, auth);
			    }
		        redir.addFlashAttribute("exception_message","Oops! Something wrong, login again");
			    return "redirect:/login";
		
	}
}
