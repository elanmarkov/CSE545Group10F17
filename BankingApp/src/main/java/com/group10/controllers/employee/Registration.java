package com.group10.controllers.employee;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.group10.dao.employee.UserRegistrationDaoImpl;
import com.group10.dao.employee.Validator;
import com.group10.dao.logs.LogsDaoImpl;
import com.group10.dbmodels.DbLogs;
import com.group10.dbmodels.ExternalUser;
import com.group10.dbmodels.InternalUser;

@Controller
public class Registration {
	
	@RequestMapping("/Employee/RegistrationInternalEmployer")
	//public ModelAndView InternalRegister(@ModelAttribute("user1") InternalUser newUser, RedirectAttributes redir){
		/*
		String name = newUser.getName();
		String email = newUser.getEmail();
		String designation = newUser.getDesignation();
		String address = newUser.getAddress();
		String city = newUser.getCity();
		String state = newUser.getState();
		String country = newUser.getCountry();
		int pincode = newUser.getPincode();
		int number = newUser.getPhone();
		String dob = newUser.getDob();
		String ssn = newUser.getSsn();
		String username = newUser.getUserid();
		ApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		
		/*
		//Validation of form fields
		Validator validator = new Validator();
		Boolean isValidated = true;
		String str[] = {"ssn", "number", "email", "name"};
		for(String word : str)
		{
			if(validator.validateName(word) == false) {
				isValidated = false;
	            redir.addFlashAttribute("error_message",word+" Not Valid");
			}
		}
		
		//check if the username and phone number are unique
		UserRegistrationDaoImpl udao = ctx.getBean("userRegistrationDaoImpl", UserRegistrationDaoImpl.class);
		if(udao.isUnique(username, number, email, "internal_users") == false)
		{	isValidated = false;
			redir.addFlashAttribute("error_message","email/number/username already exists. Select new ones");
		}
		
		if(isValidated){
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			
			Random rand = new Random();
			String rawPassword = Long.toString((long) (rand.nextInt(999999 - 100000) + 100000));
			String password = encoder.encode(rawPassword); 
			dob = encoder.encode(dob);
			ssn = encoder.encode(ssn);
			udao.setInternalUser(name, designation, address, city, state, country, pincode, number, email, dob, ssn, username); 
			udao.setUserDetails(username, password, designation);
			
			LogsDaoImpl logsDao= ctx.getBean("logsDaoImpl",LogsDaoImpl.class);
			DbLogs dblogs = new DbLogs();
			dblogs.setActivity("Internal User creation");
			dblogs.setDetails("Successful");
			//dblogs.setUserid(userid);
			logsDao.saveLogs(dblogs, "internal");
        	redir.addFlashAttribute("error_msg","Registration successful. Password sent to " + newUser.getEmail());

		}
		else{
			LogsDaoImpl logsDao= ctx.getBean("logsDaoImpl",LogsDaoImpl.class);
			DbLogs dblogs = new DbLogs();
			dblogs.setActivity("Internal User creation");
			dblogs.setDetails("Error occured due to existing email/phone/username");
			//dblogs.setUserid(userid);
			logsDao.saveLogs(dblogs, "internal");
		}
		
		ModelAndView model = new ModelAndView();
		model.addObject(newUser);
		
		model.setViewName("redirect:/employee/management");
		return model;
	*/
	public ModelAndView InternalRegister(){
		ModelAndView model = new ModelAndView("dashboard");
	   // model.addObject("InternalUser", newUser);	
	    return model;
	}
	
	
	/*
	

	@RequestMapping(value = "/employee/externalreg", method =RequestMethod.POST)
	public ModelAndView InternalRegister(@ModelAttribute("user1") ExternalUser newUser, RedirectAttributes redir){
		
		String name = newUser.getName();
		String email = newUser.getEmail();
		String address = newUser.getAddress();
		String city = newUser.getCity();
		String state = newUser.getState();
		String country = newUser.getCountry();
		int pincode = newUser.getPincode();
		int number = newUser.getPhone();
		String dob = newUser.getDob();
		String ssn = newUser.getSsn();
		String username = newUser.getUserid();
		ApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		
		//Validation of form fields
		Validator validator = new Validator();
		Boolean isValidated = true;
		String str[] = {"ssn", "number", "email", "name"};
		for(String word : str)
		{
			if(validator.validateName(word) == false) {
				isValidated = false;
	            redir.addFlashAttribute("error_message",word+" Not Valid");
			}
		}
		
		//check if the username and phone number are unique
		UserRegistrationDaoImpl udao = ctx.getBean("userRegistrationDaoImpl", UserRegistrationDaoImpl.class);
		if(udao.isUnique(username, number, email, "external_users") == false)
		{	isValidated = false;
			redir.addFlashAttribute("error_message","email/number/username already exists. Select new ones");
		}
		
		if(isValidated){
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			
			Random rand = new Random();
			String rawPassword = Long.toString((long) (rand.nextInt(999999 - 100000) + 100000));
			String password = encoder.encode(rawPassword); 
			dob = encoder.encode(dob);
			ssn = encoder.encode(ssn);
			udao.setExternalUser(name, address, city, state, country, pincode, number, email, dob, ssn, username); 
			udao.setUserDetails(username, password,"ext");
			
			LogsDaoImpl logsDao= ctx.getBean("logsDaoImpl",LogsDaoImpl.class);
			DbLogs dblogs = new DbLogs();
			dblogs.setActivity("External User creation");
			dblogs.setDetails("Successful");
			//dblogs.setUserid(userid);
			logsDao.saveLogs(dblogs, "external");
        	redir.addFlashAttribute("error_msg","Registration successful. Password sent to " + newUser.getEmail());

		}
		else{
			LogsDaoImpl logsDao= ctx.getBean("logsDaoImpl",LogsDaoImpl.class);
			DbLogs dblogs = new DbLogs();
			dblogs.setActivity("External User creation");
			dblogs.setDetails("Error occured due to existing email/phone/username");
			//dblogs.setUserid(userid);
			logsDao.saveLogs(dblogs, "external");
		}
		ModelAndView model = new ModelAndView();
		model.setViewName("redirect:/employee/management");
		return model;
	}
	
	*/
}
