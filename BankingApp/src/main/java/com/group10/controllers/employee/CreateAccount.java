package com.group10.controllers.employee;

import org.springframework.stereotype.Controller;

@Controller
public class CreateAccount {
	@RequestMapping("/employee/createSavingsAccount")
	public ModelAndView createSavingsAccount(){
		return new ModelAndView("redirect:/employee/DashBoard");
	}
	
	
}
