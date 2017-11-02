package com.group10.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.group10.controllers.security.HandlerClass;
import com.group10.dao.employee.EmpFunctionsDaoImpl;


@Controller
public class LoginController {

	public void setSession(HttpServletRequest request, String role, int userID, String username){
		request.getSession().setAttribute("role", role);
		request.getSession().setAttribute("userID", userID);
		request.getSession().setAttribute("username", username);		
	}
	
	@ExceptionHandler(HandlerClass.class)
    public String handleResourceNotFoundException() {
        return "redirect:/exception";
    }
	/*
	@RequestMapping("/login")
	public  ModelAndView Login(){
		return new ModelAndView("/login/Login");
	}
	*/
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, HttpServletRequest request) throws IOException {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		
		model.setViewName("/login/Login");

		return model;

	}
	private String getErrorMessage(HttpServletRequest request, String key) {
		Exception exception = (Exception) request.getSession().getAttribute(key);
		String error = "";
		if (exception instanceof BadCredentialsException) {
			error = "Invalid username and password!";
		} else if (exception instanceof LockedException) {
			error = exception.getMessage();
		} else {
			error = "Invalid username and password!";
		}
		return error;
	}
	

	
	
	@RequestMapping(value = "/login/Logout", method =RequestMethod.POST)
	public  ModelAndView Logout(HttpServletRequest request){
		return new ModelAndView("/login/Logout");
	}
}