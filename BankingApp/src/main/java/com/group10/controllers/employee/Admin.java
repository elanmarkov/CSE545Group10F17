package com.group10.controllers.employee;

import java.util.List;
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
import com.group10.dbmodels.PII;
import com.group10.dbmodels.User;
import com.group10.dbmodels.PendingInternalRequests;
import com.group10.dao.otp.OneTimePasswordDao;

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

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		EmpFunctionsDaoImpl edao = ctx.getBean("empFunctionsDaoImpl",EmpFunctionsDaoImpl.class);
		ModelAndView model = new ModelAndView();
		List<PendingInternalRequests> pending_list = edao.getAdminPendingRequests();
		model.addObject("pending_list", pending_list);
		model.setViewName("/employee/AdminPendingRequest");
		ctx.close();
		return model;
	}
	@RequestMapping("/employee/SystemLogs")
	public ModelAndView SystemLogs(){
		ModelAndView model = new ModelAndView();
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		LogsDaoImpl ldao= ctx.getBean("logsDaoImpl",LogsDaoImpl.class);
		List<DbLogs> loglist = ldao.getAllLogs();
		model.addObject("loglist", loglist);
		model.setViewName("/employee/SystemLogs");
		return model;
	}

	@RequestMapping("/employee/AdminSearchUser")
	public ModelAndView AdminSearchUser(){
		return new ModelAndView("/employee/AdminSearchUser");
	}
/*
	@RequestMapping("/employee/AdminUserDetails")
	public ModelAndView AdminUserDetails(){
		return new ModelAndView("/employee/AdminUserDetails");
	}
*/
	@RequestMapping(value = "/employee/internalreg", method =RequestMethod.POST)
	public ModelAndView InternalRegister(@ModelAttribute("user") User newUser/*, RedirectAttributes redir*/){
	//	try{
				ModelAndView model = new ModelAndView();

		String name = newUser.getName();
		String email = newUser.getEmail();
		String role = newUser.getRole();
		String address = newUser.getAddress();
		String city = newUser.getCity();
		String state = newUser.getState();
		String country = newUser.getCountry();
		String pincode = newUser.getZipcode();
		String number = newUser.getPhone();
		String dob = newUser.getDob();
		String ssn = newUser.getSsn();
		String username = email.split("@")[0];
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");


		//Validation of form fields
		Validator validator = new Validator();
		Boolean isValidated = true;
		String str[] = {"ssn", "number", "email", "name"};
		for(String word : str)
		{
			if(validator.validateName(word) == false) {
				isValidated = false;
	  //          redir.addFlashAttribute("error_message",word+" Not Valid");
			}
		}

		//check if the username and phone number are unique
		UserRegistrationDaoImpl udao = ctx.getBean("userRegistrationDaoImpl", UserRegistrationDaoImpl.class);
		EmpFunctionsDaoImpl edao = ctx.getBean("empFunctionsDaoImpl",EmpFunctionsDaoImpl.class);
		if(udao.isUnique(username, number, email, "users")==false)
		{	isValidated = false;
		//	redir.addFlashAttribute("error_message","email/number/username already exists. Select new ones");
		}

		if(isValidated){
			OneTimePasswordDao otpDao = ctx.getBean("OneTimePasswordDao",OneTimePasswordDao.class);
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

			Random rand = new Random();
			String rawPassword = Long.toString((long) (rand.nextInt(999999 - 100000) + 100000));
			String password = encoder.encode(rawPassword);
		//	dob = encoder.encode(dob);
			udao.setInternalUser(name, role, address, city, state, country, pincode, number, email, dob, ssn, username);
			udao.setLoginDetails(username, password, role, email);

			LogsDaoImpl logsDao= ctx.getBean("logsDaoImpl",LogsDaoImpl.class);
			logsDao.saveLogs("Internal User creation","Successful",userID, "internal");
        	//redir.addFlashAttribute("error_msg","Registration successful. Password sent to " + newUser.getEmail());
            model.setViewName("redirect:/employee/AdminDashboard");

		}
		else{
			LogsDaoImpl logsDao= ctx.getBean("logsDaoImpl",LogsDaoImpl.class);
			logsDao.saveLogs("Internal User creation","Failed",userID, "internal");
            model.setViewName("redirect:/employee/RegistrationInternalEmployee");
		}
		ctx.close();

		return model;
	/*
		}catch(Exception e){
			throw new HandlerClass();
		}
		*/
	}


	@RequestMapping(value = "/employee/adminPendingRequest", method =RequestMethod.POST)
	public ModelAndView pendingRequests(HttpServletRequest request, @RequestParam("requestID") int requestId, @RequestParam("requestDecision") String reqDecision,
			@RequestParam("userId") int userId/*,RedirectAttributes redir*/){

	//	try{
			ModelAndView model = new ModelAndView();

			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
			EmpFunctionsDaoImpl fdao = ctx.getBean("empFunctionsDaoImpl",EmpFunctionsDaoImpl.class);
			LogsDaoImpl ldao = ctx.getBean("logsDaoImpl", LogsDaoImpl.class);
			ldao.saveLogs("pending request", reqDecision, userId, "internal");

			if(reqDecision.equals("approve"))
				fdao.approveAdminRequest(requestId);
			else
				fdao.deletePendingRequest(requestId);
		//	redir.addFlashAttribute("error_msg","Request"+reqDecision);
			model.setViewName("/employee/AdminPendingRequest");
			ldao.saveLogs("internal request"+reqDecision, "for"+userId, userID, "internal");
			ctx.close();
			return model;
/*
		}catch(Exception e){
			throw new HandlerClass();
		}
*/	}


	@RequestMapping(value = "/employee/adminModify", method =RequestMethod.POST)
	public ModelAndView pendingRequests(HttpServletRequest request, @RequestParam("address") String address, @RequestParam("state") String state,  @RequestParam("city") String city ,
			 @RequestParam("zipcode") String zipcode, @RequestParam("country") String country, @RequestParam("phone") String phone,
			 @RequestParam("id") int userId,RedirectAttributes redir){
		try{
			ModelAndView model = new ModelAndView();

			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
			EmpFunctionsDaoImpl fdao = ctx.getBean("empFunctionsDaoImpl",EmpFunctionsDaoImpl.class);
			LogsDaoImpl ldao = ctx.getBean("logsDaoImpl", LogsDaoImpl.class);

			/*  write the dao code for admin modify
	         *
	        */
			fdao.modify(address, city, state, country, zipcode,phone, userId);
			redir.addFlashAttribute("error_msg","Modified the address for "+userId);
			User user = fdao.getUser(userId);
			model.addObject("user",user);
			PII pii = fdao.getUserPII(userId);
			model.addObject("pii",pii);
			model.setViewName("/employee/AdminUserDetails");
			ldao.saveLogs("Modified internal account", "for"+userId, userID, "internal");

			ctx.close();
			return model;

		}catch(Exception e){
			throw new HandlerClass();
		}
	}


	@RequestMapping(value = "/admin/searchInternalUser", method =RequestMethod.POST)
	public ModelAndView searchInternalUser(HttpServletRequest request, @RequestParam("employeeID") int employeeID/*, RedirectAttributes redir*/){
//		try{

			ModelAndView model =new ModelAndView();

			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
			EmpFunctionsDaoImpl fdao = ctx.getBean("empFunctionsDaoImpl",EmpFunctionsDaoImpl.class);
			LogsDaoImpl ldao = ctx.getBean("logsDaoImpl", LogsDaoImpl.class);

        /*
         * write the dao code for admin approval
         */
			if(fdao.existInteralUser(employeeID))
			{
				ldao.saveLogs("searched for internal user", ""+employeeID, userID, "internal");
				User employeeObj = fdao.getUser(employeeID);
				model.addObject("user",employeeObj);
				//redir.addFlashAttribute("error_msg","Employee Found");
				model.setViewName("/employee/AdminSearchUser");
			}
			else{
				//redir.addFlashAttribute("error_msg","Employee Not Found");
				model.setViewName("/employee/AdminSearchUser");
			}
			ctx.close();
			return model;
/*
		}catch(Exception e){
			throw new HandlerClass();
		}
*/
	}


	@RequestMapping(value = "/admin/showAccountDetails", method =RequestMethod.POST)
	public ModelAndView showAccountDetails(HttpServletRequest request, @RequestParam("employeeID") int employeeID, RedirectAttributes redir){

		try{
			ModelAndView model = new ModelAndView();
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
			EmpFunctionsDaoImpl fdao = ctx.getBean("empFunctionsDaoImpl",EmpFunctionsDaoImpl.class);
			LogsDaoImpl ldao = ctx.getBean("logsDaoImpl", LogsDaoImpl.class);

			ldao.saveLogs("Accessed details of employee", ""+employeeID, userID, "internal");
			User user = fdao.getUser(employeeID);
			PII pii = fdao.getUserPII(employeeID);
			model.addObject("pii", pii);
			model.addObject("user",user);

			model.setViewName("/employee/AdminSearchUserDetails");
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
			model.setViewName("/employee/AdminSearchUser");
			ctx.close();
			return model;

		}catch(Exception e){
			throw new HandlerClass();
		}

	}

	@RequestMapping("/employee/AdminUserDetails")
	public ModelAndView AdminUserDetails(HttpServletRequest request, RedirectAttributes redir){
		try{
			ModelAndView model = new ModelAndView();
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
			EmpFunctionsDaoImpl fdao = ctx.getBean("empFunctionsDaoImpl",EmpFunctionsDaoImpl.class);
			LogsDaoImpl ldao = ctx.getBean("logsDaoImpl", LogsDaoImpl.class);

			ldao.saveLogs("Accessed details of employee", ""+userID, userID, "internal");
			User user = fdao.getUser(userID);
			PII pii = fdao.getUserPII(userID);
			model.addObject("pii", pii);
			model.addObject("user",user);

			model.setViewName("/employee/AdminUserDetails");
			ctx.close();
			return model;
		}catch(Exception e){
			throw new HandlerClass();
		}
	}

	@RequestMapping(value = "/employee/AdminModify", method =RequestMethod.POST)
	public ModelAndView ModifyPersonal(HttpServletRequest request, @RequestParam("address") String address, @RequestParam("state") String state,  @RequestParam("city") String city ,
			 @RequestParam("zipcode") String zipcode, @RequestParam("country") String country, @RequestParam("phone") String phone,
			 @RequestParam("id") int userId,RedirectAttributes redir){
		try{
			ModelAndView model = new ModelAndView();

			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
			EmpFunctionsDaoImpl fdao = ctx.getBean("empFunctionsDaoImpl",EmpFunctionsDaoImpl.class);
			LogsDaoImpl ldao = ctx.getBean("logsDaoImpl", LogsDaoImpl.class);

			fdao.modify(address, city, state, zipcode, country, phone, userId);
			redir.addFlashAttribute("error_msg","Modified request for admin account for "+userId);

			User user = fdao.getUser(userId);
			model.addObject("user",user);
			PII pii = fdao.getUserPII(userId);
			model.addObject("pii",pii);
			model.setViewName("/employee/AdminProfile");
			ldao.saveLogs("Modified Admin account details", "for"+userId, userID, "internal");

			ctx.close();
			return model;

		}catch(Exception e){
			throw new HandlerClass();
		}
	}
}
