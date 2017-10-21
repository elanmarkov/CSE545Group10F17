package com.group10.controllers;

/**Custom Packages**/
import com.group10.dbmodels.*;

/**Dependencies**/
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FundsTransferController {

	String role;
	int userID;
	String username;
	
	public void setGlobals(HttpServletRequest request)
	{
		role = (String) request.getSession().getAttribute("role");
		userID = (Integer) request.getSession().getAttribute("userID");
		username = (String) request.getSession().getAttribute("username");
	}
	
	@RequestMapping("/customer/fundsTransfer")
	public ModelAndView customerHome(HttpServletRequest request)
	{
		try {
			SavingsAccount savingsAccount = new SavingsAccount();
			savingsAccount.setAccount_no("12495");
			
			CheckingAccount checkingAccount = new CheckingAccount();
			checkingAccount.setAccount_no("88493");
			
			ModelAndView model = new ModelAndView("fundsTransfer");
			model.addObject("savings",savingsAccount);
			model.addObject("checking", checkingAccount);
			return model;
		} catch(Exception e){
			System.out.println(e);
			//TODO: Redirect
			return null;
		}
	}
	
}
