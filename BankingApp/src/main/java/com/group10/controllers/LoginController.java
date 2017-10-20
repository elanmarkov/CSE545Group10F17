package com.group10.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.group10.dbmodels.CheckingAccount;
import com.group10.dbmodels.SavingsAccount;

@Controller
public class LoginController {

	@RequestMapping("/login")
	public ModelAndView customerHome(HttpServletRequest request)
	{
		try {
			
			ModelAndView model = new ModelAndView("login");
			return model;
		} catch(Exception e){
			System.out.println(e);
			//TODO: Redirect
			return null;
		}
	}
}
