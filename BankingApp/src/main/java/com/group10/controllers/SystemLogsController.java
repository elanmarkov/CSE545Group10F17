package com.group10.controllers;

/**Custom Packages**/
import com.group10.dbmodels.*;

/**Dependencies**/
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.math.BigDecimal;
import java.sql.Date;

@Controller
public class SystemLogsController {

	String role;
	int userID;
	String username;
	
	public void setGlobals(HttpServletRequest request)
	{
		role = (String) request.getSession().getAttribute("role");
		userID = (Integer) request.getSession().getAttribute("userID");
		username = (String) request.getSession().getAttribute("username");
	}
	
	@RequestMapping("/employee/SystemLogs")
	public ModelAndView customerHome(HttpServletRequest request)
	{
		try {
			DbLogs log = new DbLogs();
			log.setActivity("Not suspicious at all.");
			log.setDetails("Completely Legit");
			log.setTimestamp(new Date(10, 20, 2017));
			log.setUserid(1);
			ModelAndView model = new ModelAndView("SystemLogs");
			model.addObject("log", log);
			return model;
		} catch(Exception e){
			System.out.println(e);
			//TODO: Redirect
			return null;
		}
	}
	
}
