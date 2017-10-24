package com.group10.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ClassPathXmlApplicationContext;
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
import com.group10.dbmodels.UserDetails;


@Controller
public class LoginController {

	String role;
	int userID;
	String username;
	
	
	public void setGlobals(HttpServletRequest request){
		role = (String) request.getSession().getAttribute("role");
		userID = 1;
		username = (String) request.getSession().getAttribute("username");		
	}
	
	@ExceptionHandler(HandlerClass.class)
    public String handleResourceNotFoundException() {
        return "redirect:/exception";
    }
	
	
	@RequestMapping("/login")
	public  ModelAndView Login(){
		return new ModelAndView("/login/Login");
	}
	
	@RequestMapping(value = "/loginSubmit", method = RequestMethod.POST)
	public ModelAndView loginForm(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("roleSelection") String role,
			HttpServletRequest request, RedirectAttributes redir) {
		try
		{	
			ModelAndView model = new ModelAndView();
			UserDetails user = new UserDetails();
			
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
			EmpFunctionsDaoImpl edao = ctx.getBean("empFunctionsDaoImpl",EmpFunctionsDaoImpl.class);
			 if(edao.validateUserLogin(username, password, role)){
				if(role.compareTo("customer") == 0)
				{
					model.setViewName("/employee/CustomerDashboard");
				}
				else if(role.equals("merchant"))
				{
					model.setViewName("/employee/merchant_Dashboard");
				}
				else if(role.equals("employee"))
				{
					model.setViewName("/employee/EmployeeDashboard");
				}
				else if(role.equals("manager"))
				{
					model.setViewName("/customer/Internal_users_dashboard");
				}
				else if(role.equals("admin"))
				{
					model.setViewName("/employee/AdminDashboard");
				}
			}
			else
			{
				redir.addFlashAttribute("error_msg", "user does not exist. Try again");
				model.setViewName("/login/Login");
			}
			return model;
		}
		catch(Exception e){
			//TODO: Redirect
			throw new HandlerClass();
		}
	}
}
