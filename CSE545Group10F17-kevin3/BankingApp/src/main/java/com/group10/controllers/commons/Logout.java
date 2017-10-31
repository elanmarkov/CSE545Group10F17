package com.group10.controllers.commons;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Component
public class Logout {
	
	@RequestMapping(value ="/logout", method = RequestMethod.POST)
	public ModelAndView logoutpage(HttpServletRequest request,
			@RequestParam("type") String type){
		if(type.equals("Yes")) {
			HttpSession httpSession = request.getSession();
	        httpSession.invalidate();
			return new ModelAndView("login/logout");
		}
		return new ModelAndView("redirect:/login");
	}
}
