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
	public ModelAndView searchExternalUser(HttpServletRequest request, @RequestParam("employeeID") int customerID/*, RedirectAttributes redir*/){
//		try{
			
			ModelAndView model =new ModelAndView();

			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
			EmpFunctionsDaoImpl fdao = ctx.getBean("empFunctionsDaoImpl",EmpFunctionsDaoImpl.class);
			LogsDaoImpl ldao = ctx.getBean("logsDaoImpl", LogsDaoImpl.class);

			if(fdao.existUser(customerID))
			{	
				ldao.saveLogs("searched for external user", ""+customerID, userID, "external");
				User customerObj = fdao.getExternalUser(customerID);
				model.addObject("customerObj",customerObj);
				//redir.addFlashAttribute("error_msg","Employee Found");
			}
			else{
				//redir.addFlashAttribute("error_msg","Employee Not Found");
			}
			model.setViewName("/employee/Tier1SearchUser");
			ctx.close();
			return model;	
/*
		}catch(Exception e){
			throw new HandlerClass();
		}
*/
	}
	

	@RequestMapping(value = "/tier1/showExternalAccount", method =RequestMethod.POST)
	public ModelAndView showExternalAccount(HttpServletRequest request, @RequestParam("customerID") int customerID, RedirectAttributes redir){
		
		try{
			ModelAndView model = new ModelAndView();
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
			EmpFunctionsDaoImpl fdao = ctx.getBean("empFunctionsDaoImpl",EmpFunctionsDaoImpl.class);
			LogsDaoImpl ldao = ctx.getBean("logsDaoImpl", LogsDaoImpl.class);

			ldao.saveLogs("Accessed details of employee", ""+customerID, userID, "external");
			User user = fdao.getExternalUser(customerID);
			model.addObject("user",user);

			model.setViewName("/employee/Tier1UserDetails");
			ctx.close();
			return model;
		}catch(Exception e){
			throw new HandlerClass();
		}
	}
	
	
}