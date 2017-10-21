package com.group10.controllers;

/**Custom Packages**/
import com.group10.dbmodels.*;

/**Dependencies**/
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminSearchEmployeeController {

	String role;
	int userID;
	String username;
	
	public void setGlobals(HttpServletRequest request)
	{
		role = (String) request.getSession().getAttribute("role");
		userID = (Integer) request.getSession().getAttribute("userID");
		username = (String) request.getSession().getAttribute("username");
	}
	
	@RequestMapping("/employee/AdminSearchEmployee")
	public ModelAndView customerHome(HttpServletRequest request)
	{
		try {
			ExternalUser user = new ExternalUser();
			InternalUser cus = new InternalUser();
			
			ModelAndView model = new ModelAndView("AdminSearchEmployee");
			model.addObject("employeeObj", user);
			model.addObject("customerObj", cus);
			
			return model;
		} catch(Exception e){
			System.out.println(e);
			//TODO: Redirect
			return null;
		}
	}
	
}
