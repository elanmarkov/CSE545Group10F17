package com.group10.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.support.ClassPathXmlApplicationContext;
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
	
	@RequestMapping("/login")
	public  ModelAndView Login(){
		return new ModelAndView("/login/Login");
	}
	
	@RequestMapping("/login/ForgotPassword")
	public  ModelAndView ForgotPass(){
		return new ModelAndView("/login/ForgotPassword");
	}
	
}