package com.group10.controllers.employee;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import com.group10.controllers.security.CustomPasswordEncoder;
import com.group10.dao.otp.OneTimePasswordDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
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
import com.group10.dbmodels.User;
import com.group10.dbmodels.PII;
import com.group10.dbmodels.PendingAccountChangeRequests;
import com.group10.dbmodels.PendingExternalRequests;
import com.group10.dbmodels.PendingInternalRequests;


@Controller
public class Tier2 {


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





	@RequestMapping("/employee/RegistrationExternalEmployee")
	public ModelAndView extReg(){
		return new ModelAndView("/employee/RegistrationExternalEmployee");
	}

	@RequestMapping(value ="/employee/externalreg", method =RequestMethod.POST)
	public ModelAndView ExternalRegister(HttpServletRequest request, @ModelAttribute("user") User newUser, RedirectAttributes redir) throws Exception{
		try{
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
		String username = email;
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");


		//Validation of form fields
		Validator validator = new Validator();
		Boolean isValidated = true;
		String str[] = {"ssn", "number", "email", "name"};
		for(String word : str)
		{
			if(validator.validateName(word) == false) {
				isValidated = false;
				
	        //   redir.addFlashAttribute("error_message",word+" Not Valid");
			}
		}

		//check if the username and phone number are unique
		UserRegistrationDaoImpl udao = ctx.getBean("userRegistrationDaoImpl", UserRegistrationDaoImpl.class);
		EmpFunctionsDaoImpl edao = ctx.getBean("empFunctionsDaoImpl",EmpFunctionsDaoImpl.class);
		if(udao.isUnique(username, number)==false)
		{	
			isValidated = false;
			String error_msg="email/number/username already exists. Select new ones ";
			model.addObject("error_msg", error_msg);
			redir.addFlashAttribute("error_message","email/number/username already exists. Select new ones");
		}

		if(isValidated){
			CustomPasswordEncoder encoder = new CustomPasswordEncoder();

			//Create otp password
			Random rand = new Random();
			String rawPassword = Long.toString((long) (rand.nextInt(999999 - 100000) + 100000));
			String password = encoder.encode(rawPassword);

			//Set Info
			//encrypt PII
			setGlobals(request);
			User user = edao.getUser(userID);
						
			udao.setExternalUser(name, role, address, city, state, country, pincode, number, email, dob, ssn, email);
			udao.setLoginDetails(email, password, role, email);
			udao.setLoginAttempts(email, 0);

			// Create OTP and send Email
			ClassPathXmlApplicationContext ctx2 = new ClassPathXmlApplicationContext("DaoDetails.xml");
			
			LogsDaoImpl logsDao= ctx.getBean("logsDaoImpl",LogsDaoImpl.class);
			logsDao.saveLogs("Internal User creation","Successful",userID, "internal");
        	//redir.addFlashAttribute("error_msg","Registration successful. Password sent to " + newUser.getEmail());
			role = (String) request.getSession().getAttribute("role");
            if(role.compareTo("ROLE_MANAGER")==0) {model.setViewName("/employee/Tier2Dashboard");}
            else {model.setViewName("/employee/Tier1Dashboard");}
			//model.setViewName("/employee/Tier1Dashboard");
		}
		else{
			LogsDaoImpl logsDao= ctx.getBean("logsDaoImpl",LogsDaoImpl.class);
			logsDao.saveLogs("Internal User creation","Failed",userID, "internal");
            model.setViewName("/employee/RegistrationExternalEmployee");
		}
		ctx.close();
		
		return model;

		}catch(Exception e){
			throw new HandlerClass();
		}
	}



	@RequestMapping("/employee/Tier2Dashboard")
	public ModelAndView tier2DashboardPage(){
		return new ModelAndView("/employee/Tier2Dashboard");
	}

	@RequestMapping("/employee/Tier2PendingRequest")
	public ModelAndView tier2PendingRequest(){
		try{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		EmpFunctionsDaoImpl edao = ctx.getBean("empFunctionsDaoImpl",EmpFunctionsDaoImpl.class);
		ModelAndView model = new ModelAndView();
		List<PendingAccountChangeRequests> pending_list = edao.getExternalPendingRequests();
		model.addObject("pending_list", pending_list);
		model.setViewName("/employee/Tier2PendingRequest");
		ctx.close();
		return model;
	}catch(Exception e){
		throw new HandlerClass();
	}
	}

	@RequestMapping(value = "/tier2/pendingRequest", method =RequestMethod.POST)
	public ModelAndView pendingRequests(HttpServletRequest request, @RequestParam("requestID") int requestId, @RequestParam("requestDecision") String reqDecision,
			@RequestParam("userId") int userId, RedirectAttributes redir){

		try{
			ModelAndView model = new ModelAndView();

			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
			EmpFunctionsDaoImpl fdao = ctx.getBean("empFunctionsDaoImpl",EmpFunctionsDaoImpl.class);
			LogsDaoImpl ldao = ctx.getBean("logsDaoImpl", LogsDaoImpl.class);
			ldao.saveLogs("Account change request", reqDecision, userId, "external");

			if(reqDecision.equals("approve"))
				fdao.approveTier2Request(requestId);
			else
				fdao.deleteTier2Request(requestId);
		//	redir.addFlashAttribute("error_msg","Request"+reqDecision);
			model.setViewName("/employee/Tier2PendingRequest");
			ctx.close();
			return model;

		}catch(Exception e){
			throw new HandlerClass();
		}
	}


	@RequestMapping("/employee/Tier2SearchUser")
	public ModelAndView Tier2SearchUser(){
		return new ModelAndView("/employee/Tier2SearchUser");
	}

	@RequestMapping("/employee/Tier2UserDetails")
	public ModelAndView Tier2UserDetails(){
		return new ModelAndView("/employee/Tier2UserDetails");
	}


	@RequestMapping(value = "/tier2/searchInternalUser", method =RequestMethod.POST)
	public ModelAndView searchInternalUser(HttpServletRequest request, @RequestParam("employeeID") String employeeEmail/*, RedirectAttributes redir*/){
		try{

			ModelAndView model =new ModelAndView();

			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
			EmpFunctionsDaoImpl fdao = ctx.getBean("empFunctionsDaoImpl",EmpFunctionsDaoImpl.class);
			LogsDaoImpl ldao = ctx.getBean("logsDaoImpl", LogsDaoImpl.class);
			if(fdao.existTier1User(employeeEmail))
			{
				int employeeID = fdao.getUserIdByName(employeeEmail);
				ldao.saveLogs("searched for internal user", ""+employeeID, userID, "internal");
				User employeeObj = fdao.getTier1User(employeeID);
				model.addObject("employeeObj",employeeObj);
				//redir.addFlashAttribute("error_msg","Employee Found");
			}
			else{
				//redir.addFlashAttribute("error_msg","Employee Not Found");
			}
			model.setViewName("/employee/Tier2SearchUser");
			ctx.close();
			return model;

		}catch(Exception e){
			throw new HandlerClass();
		}

	}

	@RequestMapping(value = "/tier2/searchExternalUser", method =RequestMethod.POST)
	public ModelAndView searchExternalUser(HttpServletRequest request, @RequestParam("customerID") String customerEmail/*, RedirectAttributes redir*/){
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
				//redir.addFlashAttribute("error_msg","Employee Found");
			}
			else{
				//redir.addFlashAttribute("error_msg","Employee Not Found");
			}
			model.setViewName("/employee/Tier2SearchUser");
			ctx.close();
			return model;

		}catch(Exception e){
			throw new HandlerClass();
		}

	}

	@RequestMapping(value = "/tier2/showInternalAccount", method =RequestMethod.POST)
	public ModelAndView showInternalAccount(HttpServletRequest request, @RequestParam("employeeID") int employeeID, RedirectAttributes redir){

		try{
			ModelAndView model = new ModelAndView();
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
			EmpFunctionsDaoImpl fdao = ctx.getBean("empFunctionsDaoImpl",EmpFunctionsDaoImpl.class);
			LogsDaoImpl ldao = ctx.getBean("logsDaoImpl", LogsDaoImpl.class);
			ldao.saveLogs("Accessed details of employee", ""+employeeID, userID, "internal");
			User user = fdao.getTier1User(employeeID);
			model.addObject("user",user);

			model.setViewName("/employee/Tier2UserDetails");
			ctx.close();
			return model;
		}catch(Exception e){
			throw new HandlerClass();
		}
	}

	@RequestMapping(value = "/tier2/showExternalAccount", method =RequestMethod.POST)
	public ModelAndView showExternalAccount(HttpServletRequest request, @RequestParam("customerID") int customerID, RedirectAttributes redir){

		try{
			ModelAndView model = new ModelAndView();
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
			EmpFunctionsDaoImpl fdao = ctx.getBean("empFunctionsDaoImpl",EmpFunctionsDaoImpl.class);
			LogsDaoImpl ldao = ctx.getBean("logsDaoImpl", LogsDaoImpl.class);
			ldao.saveLogs("Accessed details of customer", ""+customerID, userID, "external");
			User user = fdao.getExternalUser(customerID);
			model.addObject("user",user);

			model.setViewName("/employee/Tier2UserDetails");
			ctx.close();
			return model;
		}catch(Exception e){
			throw new HandlerClass();
		}
	}

	@RequestMapping(value = "/tier2/deleteExternalUser", method =RequestMethod.POST)
	public ModelAndView deleteInternalUser(HttpServletRequest request, @RequestParam("customerID") int customerID, RedirectAttributes redir){
		try{
			ModelAndView model = new ModelAndView();
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
			EmpFunctionsDaoImpl fdao = ctx.getBean("empFunctionsDaoImpl",EmpFunctionsDaoImpl.class);
			LogsDaoImpl ldao = ctx.getBean("logsDaoImpl", LogsDaoImpl.class);
			fdao.deleteExternalUser(customerID);
			ldao.saveLogs("deleted external user", ""+customerID, userID, "external");
			model.setViewName("/employee/Tier2SearchUser");
			ctx.close();
			return model;

		}catch(Exception e){
			throw new HandlerClass();
		}
	}


	@RequestMapping(value = "/tier2/modifyAccount", method =RequestMethod.POST)
	public ModelAndView internalModify(HttpServletRequest request, @RequestParam("address") String address, @RequestParam("state") String state,  @RequestParam("city") String city ,
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
			fdao.modify(address, city, state, country,zipcode, phone, userId);
			redir.addFlashAttribute("error_msg","Modified the address for "+userId);
			User user = fdao.getUser(userId);
			model.addObject("user",user);
			model.setViewName("/employee/Tier2UserDetails");
			ldao.saveLogs("Modified internal account", "address change", userID, "internal");

			ctx.close();
			return model;

		}catch(Exception e){
			throw new HandlerClass();
		}
	}


	@RequestMapping("/employee/Tier2Profile")
	public ModelAndView Tier2ProfilePage(HttpServletRequest request){
	try{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		EmpFunctionsDaoImpl fdao = ctx.getBean("empFunctionsDaoImpl",EmpFunctionsDaoImpl.class);
		LogsDaoImpl ldao = ctx.getBean("logsDaoImpl", LogsDaoImpl.class);
		ModelAndView model = new ModelAndView();
		setGlobals(request);
		ldao.saveLogs("account accessed for user", ""+userID, userID, "tier2");
		User user = fdao.getUser(userID);
		model.addObject("user",user);
		PII pii = fdao.getUserPII(userID);
		model.addObject("pii", pii);
		//redir.addFlashAttribute("error_msg","Employee Found");
		model.setViewName("/employee/Tier2Profile");
		return model;
	}catch(Exception e){
		throw new HandlerClass();
	}
	}

	// Modify his personal account
	@RequestMapping(value = "/employee/tier2Modify", method =RequestMethod.POST)
	public ModelAndView modifyRequests(HttpServletRequest request, @RequestParam("address") String address, @RequestParam("state") String state,  @RequestParam("city") String city ,
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
			fdao.generateInternalRequest(address, city, state, country, zipcode, phone, userId);
			redir.addFlashAttribute("error_msg","Generated request for account modification for "+userId);

			User user = fdao.getUser(userID);
			model.addObject("user",user);
			PII pii = fdao.getUserPII(userID);
			model.addObject("pii",pii);
			model.setViewName("/employee/Tier2Profile");
			ldao.saveLogs("Tier2 account", "for"+userID, userID, "internal");

			ctx.close();
			return model;

		}catch(Exception e){
			throw new HandlerClass();
		}
	}

	@RequestMapping("/employee/Tier2CreateUserAccounts")
	public ModelAndView createAccount2(){

		return new ModelAndView("/employee/Tier2CreateUserAccounts");
	}


}