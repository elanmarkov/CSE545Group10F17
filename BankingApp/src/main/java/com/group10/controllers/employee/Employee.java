package com.group10.controllers.employee;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.group10.controllers.security.HandlerClass;
import com.group10.dbmodels.DbLogs;

@Controller
public class Employee {

	String role;
	int userID;
	String username;

	public void setGlobals(HttpServletRequest request) {
		role = (String) request.getSession().getAttribute("role");
		userID = (Integer) request.getSession().getAttribute("userID");
		username = (String) request.getSession().getAttribute("username");
	}

	@ExceptionHandler(HandlerClass.class)
	public String handleResourceNotFoundException() {
		return "redirect:/exception";
	}
	
	
	
	
	@RequestMapping(value = "/employee/home", method = RequestMethod.GET)
	public ModelAndView Dashboard(HttpServletRequest request){
		setGlobals(request);
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");

		try{
			DbLogs ldao = new DbLogs();
			if(role.equals("ROLE_REGULAR")){
				return new ModelAndView("login");
 
			}
			
			
		}catch(Exception e){
			return new ModelAndView("login");
		}
		return null;
	}
	
	
	
}
