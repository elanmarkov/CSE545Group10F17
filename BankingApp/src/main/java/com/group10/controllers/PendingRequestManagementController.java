package com.group10.controllers;

/**Custom Packages**/
import com.group10.dbmodels.*;

/**Dependencies**/
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PendingRequestManagementController {

	String role;
	int userID;
	String username;
	
	public void setGlobals(HttpServletRequest request)
	{
		role = (String) request.getSession().getAttribute("role");
		userID = (Integer) request.getSession().getAttribute("userID");
		username = (String) request.getSession().getAttribute("username");
	}
	
	@RequestMapping("/employee/PendingRequestManagement")
	public ModelAndView customerHome(HttpServletRequest request)
	{
		try {
			Transaction temp = new Transaction();
			temp.setId(1);
			temp.setPayer_id(123);
			temp.setPayee_id(456);
			temp.setAmount(100000000000000000.00);
			temp.setTransaction_type("Mah $$$$$$");
			temp.setCritical(true);
			ModelAndView model = new ModelAndView("PendingRequestManagement");
			model.addObject("transaction", temp);
			return model;
		} catch(Exception e){
			System.out.println(e);
			//TODO: Redirect
			return null;
		}
	}
	
}
