package com.group10.controllers.employee;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import com.group10.dao.employee.Validator;
import com.group10.dao.logs.LogsDaoImpl;
import com.group10.dbmodels.DbLogs;
import com.group10.dbmodels.InternalUser;

@Controller
public class Admin {
	
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
	

	@RequestMapping("/employee/RegistrationInternalEmployee")
	public  ModelAndView InternalRegisterform(){
		return new ModelAndView("/employee/RegistrationInternalEmployee");
	}
	
	@RequestMapping("/employee/AdminDashboard")
	public ModelAndView adminDashboardPage(){
		return new ModelAndView("/employee/AdminDashboard");
	}
	
	@RequestMapping("/employee/AdminPendingRequest")
	public ModelAndView AdminPendingRequest(){
		return new ModelAndView("/employee/AdminPendingRequest");
	}
	@RequestMapping("/employee/SystemLogs")
	public ModelAndView SystemLogs(){
		return new ModelAndView("/employee/SystemLogs");
	}
	@RequestMapping("/employee/AdminSearchUser")
	public ModelAndView AdminSearchUser(){
		return new ModelAndView("/employee/AdminSearchUser");
	}
	@RequestMapping("/employee/AdminUserDetails")
	public ModelAndView AdminUserDetails(){
		return new ModelAndView("/employee/AdminUserDetails");
	}
	
	@RequestMapping("/employee/internalreg")
	public ModelAndView InternalRegister(@ModelAttribute("user") InternalUser newUser, RedirectAttributes redir){
		try{
				ModelAndView model = new ModelAndView();
		
		String name = newUser.getName();
		String email = newUser.getEmail();
		String designation = newUser.getDesignation();
		String address = newUser.getAddress();
		String city = newUser.getCity();
		String state = newUser.getState();
		String country = newUser.getCountry();
		String pincode = newUser.getPincode();
		String number = newUser.getPhone();
		String dob = newUser.getDob();
		String ssn = newUser.getSsn();
		String username = newUser.getUsername();
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		
		
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
			dblogs.setUserid(userID);
			logsDao.saveLogs(dblogs, "internal");
        	redir.addFlashAttribute("error_msg","Registration successful. Password sent to " + newUser.getEmail());
            model.setViewName("/employee/dashboard");

		}
		else{
			LogsDaoImpl logsDao= ctx.getBean("logsDaoImpl",LogsDaoImpl.class);
			DbLogs dblogs = new DbLogs();
			dblogs.setActivity("Internal User creation");
			dblogs.setDetails("Error occured due to existing email/phone/username");
			dblogs.setUserid(userID);
			logsDao.saveLogs(dblogs, "internal");
            model.setViewName("/employee/RegistrationInternalEmployee");

		}
		ctx.close();
		
		return model;
		
		}catch(Exception e){
			throw new HandlerClass();
		}
	}


	@RequestMapping(value = "/employee/adminreq", method =RequestMethod.POST)
	public ModelAndView pendingRequests(HttpServletRequest request, @RequestParam("requestID") int requestId, @RequestParam("requestDecision") String reqDecision,
			@RequestParam("userId") int userId,RedirectAttributes redir){
			
		try{
			ModelAndView model = new ModelAndView();
			
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
			EmpFunctionsDaoImpl fdao = ctx.getBean("empFunctionsDaoImpl",EmpFunctionsDaoImpl.class);
			LogsDaoImpl ldao = ctx.getBean("logsDaoImpl", LogsDaoImpl.class);

			model.addObject("pending_list",fdao.getAdminPendingRequests());
			/*  write the dao code for admin approval
	         * 
	        */ 
			if(reqDecision.equals("approve"))
				fdao.approveAdminRequest(requestId);
			else
				fdao.deletePendingRequest(requestId);
			redir.addFlashAttribute("error_msg","Request"+reqDecision);
			model.setViewName("/employee/AdminPendingRequests");
			ldao.saveLogs("internal request"+reqDecision, "for"+userId, userID, "internal");
			ctx.close();
			return model;

		}catch(Exception e){
			throw new HandlerClass();
		}
	}
	
	
	@RequestMapping(value = "/employee/adminModify", method =RequestMethod.POST)
	public ModelAndView pendingRequests(HttpServletRequest request, @RequestParam("requestID") String requestId, @RequestParam("requestDecision") String reqDecision,
			@RequestParam("address") String address, @RequestParam("state") String state,  @RequestParam("city") String city ,
			 @RequestParam("zipcode") String zipcode, @RequestParam("country") String country, @RequestParam("phone") String phone,
			 @RequestParam("userId") int userId,RedirectAttributes redir){
		try{
			ModelAndView model = new ModelAndView();
			
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
			EmpFunctionsDaoImpl fdao = ctx.getBean("empFunctionsDaoImpl",EmpFunctionsDaoImpl.class);
			LogsDaoImpl ldao = ctx.getBean("logsDaoImpl", LogsDaoImpl.class);

			/*  write the dao code for admin modify
	         * 
	        */ 
			if(fdao.adminModify(address, city, state, zipcode, country, phone, userId)){	
				redir.addFlashAttribute("error_msg","Request Approved");
				model.setViewName("/employee/AdminPendingRequests");
				ldao.saveLogs("internal request approved", "for"+userId, userID, "internal");
			}
			else{
				redir.addFlashAttribute("error_msg","Request Not Approved");
				ldao.saveLogs("internal request approved", "for"+userId, userID, "internal");
				model.setViewName("/employee/AdminPendingRequests");
			}
			ctx.close();
			return model;

		}catch(Exception e){
			throw new HandlerClass();
		}
	}

	

	@RequestMapping(value = "/employee/SystemLogs", method =RequestMethod.POST)
	public ModelAndView searchLogs(HttpServletRequest request, @RequestParam("requestID") String requestId, @RequestParam("requestDecision") String reqDecision, RedirectAttributes redir){
		try{
        
			ModelAndView model =new ModelAndView();
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
			EmpFunctionsDaoImpl fdao = ctx.getBean("empFunctionsDaoImpl",EmpFunctionsDaoImpl.class);
					
			model.addObject("logs",fdao.getLogs());
			model.setViewName("/employee/SystemLogs");
			ctx.close();
			return model;
		}catch(Exception e){
			throw new HandlerClass();
		}
		
	}

	@RequestMapping(value = "/admin/searchInternalUser", method =RequestMethod.POST)
	public ModelAndView searchInternalUser(HttpServletRequest request, @RequestParam("employeeName") String employeeName, RedirectAttributes redir){
		try{
			
			ModelAndView model =new ModelAndView();

			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
			EmpFunctionsDaoImpl fdao = ctx.getBean("empFunctionsDaoImpl",EmpFunctionsDaoImpl.class);
			LogsDaoImpl ldao = ctx.getBean("logsDaoImpl", LogsDaoImpl.class);
				
        /*
         * write the dao code for admin approval
         */
			if(fdao.existInternalUser(employeeName))
			{	
				ldao.saveLogs("searched for internal user", ""+employeeName, userID, "internal");
				model.addObject("employeeObj",fdao.getInternalUser(employeeName));
				redir.addFlashAttribute("error_msg","Employee Found");
				model.setViewName("/admin/AdminSearchUser");
			}
			else{
				redir.addFlashAttribute("error_msg","Employee Not Found");
				model.setViewName("/admin/AdminSearchUser");
			}
			ctx.close();
			return model;	

		}catch(Exception e){
			throw new HandlerClass();
		}

	}
	
	
	@RequestMapping(value = "/admin/showAccountDetails", method =RequestMethod.POST)
	public ModelAndView showAccountDetails(HttpServletRequest request, @RequestParam("employeeID") String employeeID, RedirectAttributes redir){
		
		try{
			ModelAndView model = new ModelAndView();
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
			EmpFunctionsDaoImpl fdao = ctx.getBean("empFunctionsDaoImpl",EmpFunctionsDaoImpl.class);
			LogsDaoImpl ldao = ctx.getBean("logsDaoImpl", LogsDaoImpl.class);

			ldao.saveLogs("Accessed details of employee", ""+employeeID, userID, "internal");
			model.addObject("employeeObj",fdao.getInternalUser(employeeID));
			model.addObject("employeePII", fdao.getUserPII(employeeID));
			model.setViewName("/employee/AdminUserDetails");
			ctx.close();
			return model;
		}catch(Exception e){
			throw new HandlerClass();
		}
	}
	
	@RequestMapping(value = "/admin/deleteInternalUser", method =RequestMethod.POST)
	public ModelAndView deleteInternalUser(HttpServletRequest request, @RequestParam("employeeID") String employeeID, RedirectAttributes redir){
		try{
			ModelAndView model = new ModelAndView();
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
			EmpFunctionsDaoImpl fdao = ctx.getBean("empFunctionsDaoImpl",EmpFunctionsDaoImpl.class);
			LogsDaoImpl ldao = ctx.getBean("logsDaoImpl", LogsDaoImpl.class);
			fdao.deleteInternalUser(employeeID);
			ldao.saveLogs("deleted internal user", ""+employeeID, userID, "internal");
			model.setViewName("/employee/AdminUserDetails");
			ctx.close();
			return model;

		}catch(Exception e){
			throw new HandlerClass();
		}

	}
	

}
