package com.group10.controllers;

/**Custom Packages**/
import com.group10.dbmodels.*;

/**Dependencies**/
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CreditPaymentPageCustomerController {

	String role;
	int userID;
	String username;
	
	public void setGlobals(HttpServletRequest request)
	{
		role = (String) request.getSession().getAttribute("role");
		userID = (Integer) request.getSession().getAttribute("userID");
		username = (String) request.getSession().getAttribute("username");
	}
	
	@RequestMapping("/customer/Credit_Payment_Page_Customer")
	public ModelAndView customerHome(HttpServletRequest request)
	{
		try {
			CreditCard card = new CreditCard();
			
			
			ModelAndView model = new ModelAndView("Credit_Payment_Page_Customer");
			model.addObject("creditAccount", card);
			
			return model;
		} catch(Exception e){
			System.out.println(e);
			//TODO: Redirect
			return null;
		}
	}
	
}
