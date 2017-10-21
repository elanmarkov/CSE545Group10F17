package com.group10.controllers.employee;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.group10.controllers.security.HandlerClass;

@Controller
public class AccountDetails {
	String role;
	int userID;
	String username;
	
	public void setGlobals(HttpServletRequest request){
		role = (String) request.getSession().getAttribute("role");
		//userID = (int) request.getSession().getAttribute("userID");
		userID = 1;
		username = (String) request.getSession().getAttribute("username");	
	}
	
	@ExceptionHandler(HandlerClass.class)
    public String handleResourceNotFoundException() {
        return "redirect:/exception";
    }
	
	
	
	
	
	
	
}
