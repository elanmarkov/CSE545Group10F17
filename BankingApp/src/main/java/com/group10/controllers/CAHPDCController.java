package com.group10.controllers;

/**Custom Packages**/
import com.group10.dbmodels.*;

/**Dependencies**/
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CAHPDCController {

	String role;
	int userID;
	String username;
	
	public void setGlobals(HttpServletRequest request)
	{
		role = (String) request.getSession().getAttribute("role");
		userID = (Integer) request.getSession().getAttribute("userID");
		username = (String) request.getSession().getAttribute("username");
	}
	
	@RequestMapping("/customer/Checking_Account_Home_Page-Dashboard_Customer")
	public ModelAndView customerHome(HttpServletRequest request)
	{
		try {
			ExternalUser user = new ExternalUser();
			CheckingAccount acc = new CheckingAccount();
			Transaction trans = new Transaction();
			
			ModelAndView model = new ModelAndView("Checking_Account_Home_Page-Dashboard_Customer");
			model.addObject("payee", user);
			model.addObject("transaction", trans);
			model.addObject("checkingAccount", acc);
			
			return model;
		} catch(Exception e){
			System.out.println(e);
			//TODO: Redirect
			return null;
		}
	}
	
}
