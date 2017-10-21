package com.group10.controllers;

/**Custom Packages**/
import com.group10.dbmodels.*;

/**Dependencies**/
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserDetailsEmployeeController {

	String role;
	int userID;
	String username;
	
	public void setGlobals(HttpServletRequest request)
	{
		role = (String) request.getSession().getAttribute("role");
		userID = (Integer) request.getSession().getAttribute("userID");
		username = (String) request.getSession().getAttribute("username");
	}
	
	@RequestMapping("/employee/UserDetailsEmployee")
	public ModelAndView customerHome(HttpServletRequest request)
	{
		try {
			InternalUser employee = new InternalUser();
			PII info = new PII();
			employee.setAddress("ASU");
			employee.setRole("Admin");
			employee.setId(1);
			employee.setName("Yau");
			employee.setPhone("911");
			employee.setEmail("yau@aol.com");
			
			ModelAndView model = new ModelAndView("UserDetailsEmployee");
			model.addObject("user", employee);
			model.addObject("pii", info);
			return model;
		} catch(Exception e){
			System.out.println(e);
			//TODO: Redirect
			return null;
		}
	}
	
}
