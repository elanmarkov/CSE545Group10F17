package com.group10.controllers.employee;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;


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
import com.group10.dao.employee.UserRegistrationDaoImpl;
import com.group10.dao.logs.LogsDaoImpl;
import com.group10.dbmodels.DbLogs;
import com.group10.dbmodels.User;
import com.group10.dbmodels.PII;
import com.group10.dbmodels.PendingInternalRequests;


@Controller
public class Tier1 {


	String role;
	int userID;
	String username;

	public void setGlobals(HttpServletRequest request){
		role = (String) request.getSession().getAttribute("role");
		userID = (Integer) request.getSession().getAttribute("userID");
		username = (String) request.getSession().getAttribute("username");
	}

	@ExceptionHandler(HandlerClass.class)
	public String handleResourceNotFoundException() {
		return "redirect:/exception";
	}


	@RequestMapping("/employee/Tier1Dashboard")
	public ModelAndView Tier1DashboardPage(){
		return new ModelAndView("/employee/Tier1Dashboard");
	}

	@RequestMapping("/employee/Tier1PendingRequest")
	public ModelAndView tier1PendingRequest(){

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		EmpFunctionsDaoImpl edao = ctx.getBean("empFunctionsDaoImpl",EmpFunctionsDaoImpl.class);
		ModelAndView model = new ModelAndView();
		List<PendingInternalRequests> pending_list = edao.getAdminPendingRequests();
		model.addObject("pending_list", pending_list);
		model.setViewName("/employee/Tier1PendingRequest");
		ctx.close();
		return model;
	}

	@RequestMapping("/employee/Tier1SearchUser")
	public ModelAndView Tier1SearchUser(){
		return new ModelAndView("/employee/Tier1SearchUser");
	}

	@RequestMapping("/employee/Tier1UserDetails")
	public ModelAndView Tier1UserDetails(){
		return new ModelAndView("/employee/Tier1UserDetails");
	}


	@RequestMapping(value = "/tier1/searchExternalUser", method =RequestMethod.POST)
	public ModelAndView searchExternalUser(HttpServletRequest request, @RequestParam("customerID") String customerEmail, RedirectAttributes redir){
		try{

		ModelAndView model =new ModelAndView();

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		EmpFunctionsDaoImpl fdao = ctx.getBean("empFunctionsDaoImpl",EmpFunctionsDaoImpl.class);
		LogsDaoImpl ldao = ctx.getBean("logsDaoImpl", LogsDaoImpl.class);
		if(fdao.existExternalUser(customerEmail))
		{
			int customerID = fdao.getUserIdByName(customerEmail);
			ldao.saveLogs("searched for external user", ""+customerID, userID, "external");
			User customerObj = fdao.getExternalUser(customerID);
			model.addObject("customerObj",customerObj);
			redir.addFlashAttribute("error_msg","Employee Found");
		}
		else{
			redir.addFlashAttribute("error_msg","Employee Not Found");
		}
		model.setViewName("/employee/Tier1SearchUser");
		ctx.close();
		return model;

		}catch(Exception e){
			throw new HandlerClass();
		}

	}


	@RequestMapping(value = "/tier1/showExternalAccount", method =RequestMethod.POST)
	public ModelAndView showExternalAccount(HttpServletRequest request, @RequestParam("customerID") int customerID, RedirectAttributes redir){

		try{
			ModelAndView model = new ModelAndView();
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
			EmpFunctionsDaoImpl fdao = ctx.getBean("empFunctionsDaoImpl",EmpFunctionsDaoImpl.class);
			LogsDaoImpl ldao = ctx.getBean("logsDaoImpl", LogsDaoImpl.class);

			ldao.saveLogs("Accessed details of customer", ""+customerID, userID, "external");
			User user = fdao.getExternalUser(customerID);
			model.addObject("user",user);

			model.setViewName("/employee/Tier1UserDetails");
			ctx.close();
			return model;
		}catch(Exception e){
			throw new HandlerClass();
		}
	}

	@RequestMapping(value = "/tier1/modifyAccount", method =RequestMethod.POST)
	public ModelAndView internalModify(HttpServletRequest request, @RequestParam("address") String address, @RequestParam("state") String state,  @RequestParam("city") String city ,
									   @RequestParam("zipcode") String zipcode, @RequestParam("country") String country, @RequestParam("phone") String phone,
									   @RequestParam("id") int userId,RedirectAttributes redir){
		try{
			ModelAndView model = new ModelAndView();

			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
			EmpFunctionsDaoImpl fdao = ctx.getBean("empFunctionsDaoImpl",EmpFunctionsDaoImpl.class);
			LogsDaoImpl ldao = ctx.getBean("logsDaoImpl", LogsDaoImpl.class);

			fdao.modify(address, city, state, country, zipcode, phone, userId);
			redir.addFlashAttribute("error_msg","Modified the address for "+userId);
			User user = fdao.getUser(userId);
			model.addObject("user",user);
			model.setViewName("/employee/Tier1UserDetails");
			ldao.saveLogs("Modified external account", "address change", userID, "external");

			ctx.close();
			return model;

		}catch(Exception e){
			throw new HandlerClass();
		}
	}



	@RequestMapping("/employee/Tier1Profile")
	public ModelAndView Tier1ProfilePage(HttpServletRequest request){
	try{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		EmpFunctionsDaoImpl fdao = ctx.getBean("empFunctionsDaoImpl",EmpFunctionsDaoImpl.class);
		LogsDaoImpl ldao = ctx.getBean("logsDaoImpl", LogsDaoImpl.class);
		ModelAndView model = new ModelAndView();
		userID = (Integer)request.getSession().getAttribute("userID");
		ldao.saveLogs("modified for profile user", ""+userID, userID, "internal");
		User user = fdao.getUser(userID);
		model.addObject("user",user);
		PII pii = fdao.getUserPII(userID);
		model.addObject("pii", pii);
		//redir.addFlashAttribute("error_msg","Employee Found");
		model.setViewName("/employee/Tier1Profile");
		return model;
	}catch(Exception e){
		throw new HandlerClass();
	}
	}

	@RequestMapping(value = "/employee/tier1Modify", method =RequestMethod.POST)
	public ModelAndView pendingRequests(HttpServletRequest request, @RequestParam("address") String address, @RequestParam("state") String state,  @RequestParam("city") String city ,
										@RequestParam("zipcode") String zipcode, @RequestParam("country") String country, @RequestParam("phone") String phone,
										@RequestParam("id") int userId,RedirectAttributes redir){
		try{
			ModelAndView model = new ModelAndView();

			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
			EmpFunctionsDaoImpl fdao = ctx.getBean("empFunctionsDaoImpl",EmpFunctionsDaoImpl.class);
			LogsDaoImpl ldao = ctx.getBean("logsDaoImpl", LogsDaoImpl.class);
			userID = (Integer)request.getSession().getAttribute("userID");
	
			fdao.generateInternalRequest(address, city, state, zipcode, country, phone, userID);
			redir.addFlashAttribute("error_msg","Generated request for account modification for "+userID);
			User user = fdao.getUser(userID);
			model.addObject("user",user);
			PII pii = fdao.getUserPII(userID);
			model.addObject("pii",pii);
			model.setViewName("/employee/Tier1Profile");
			ldao.saveLogs("Modified internal account", "for"+userID, userID, "internal");

			ctx.close();
			return model;

		}catch(Exception e){
			throw new HandlerClass();
		}
	}

	@RequestMapping("/employee/Tier1CreateUserAccounts")
	public ModelAndView createAccount() {
		return new ModelAndView("/employee/Tier1CreateUserAccounts");
	}



}