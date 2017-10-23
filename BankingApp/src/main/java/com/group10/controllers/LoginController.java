package com.group10.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.group10.dbmodels.UserDetails;


@Controller
public class LoginController {

	String role;
	int userID;
	String username;
	
	
	public void setGlobals(HttpServletRequest request){
		role = (String) request.getSession().getAttribute("role");
		userID = 1;
		username = (String) request.getSession().getAttribute("username");		
	}
	
	@RequestMapping("/Login")
	public  ModelAndView Login(){
		return new ModelAndView("Login");
	}
	
	@RequestMapping(value = "/Login", method = RequestMethod.POST)
	public ModelAndView loginForm(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("roleSelection") String role, HttpServletRequest request) throws IOException {
		try
		{	
			ModelAndView model = new ModelAndView();
			UserDetails user = new UserDetails();
			user.setUsername("user");
			user.setPassword("pass");
			user.setRole("customer");
			boolean validateUser = false;
			boolean validatePass = false;
			boolean validateRole = false;
			
			if(username.compareTo(user.getUsername()) == 0)
			{
				validateUser = true;
			}
			if(password.compareTo(user.getPassword()) == 0)
			{
				validatePass = true;
			}
			if(role.compareTo(user.getRole()) == 0)
			{
				validateRole = true;
			}
			
			if(validateUser && validatePass && validateRole)
			{
				if(role.compareTo("customer") == 0)
				{
					model.setViewName("CustomerDashboard");
				}
				else if(role.compareTo("merchant") == 0)
				{
					model.setViewName("merchant_Dashboard");
				}
				else if(role.compareTo("employee") == 0)
				{
					model.setViewName("EmployeeDashboard");
				}
				else if(role.compareTo("manager") == 0)
				{
					model.setViewName("Internal_users_dashboard");
				}
				else if(role.compareTo("admin") == 0)
				{
					model.setViewName("Admin_User_accounts_management");
				}
				else
				{
					model.setViewName("Login");
					//return "redirect:/exception"; //or whatever error we want to throw
				}
			}
			else
			{
				model.setViewName("Login");
			}
			return model;
		}
		catch(Exception e){
			System.out.println(e);
			//TODO: Redirect
			return null;
		}
	}
}
