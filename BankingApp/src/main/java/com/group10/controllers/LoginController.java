package com.group10.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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


@Controller
public class LoginController {

	public void setSession(HttpServletRequest request, String role, int userID, String username){
		request.getSession().setAttribute("role", role);
		request.getSession().setAttribute("userID", userID);
		request.getSession().setAttribute("username", username);		
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
	public ModelAndView loginForm(@RequestParam("username") String email, @RequestParam("password") String password, @RequestParam("roleSelection") String role,
			HttpServletRequest request, RedirectAttributes redir) {
	//	try{
			
			
			ModelAndView model = new ModelAndView();
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
			EmpFunctionsDaoImpl edao = ctx.getBean("empFunctionsDaoImpl",EmpFunctionsDaoImpl.class);
			String username= email.split("@")[0];
		    int userID = edao.getUserIdByName(email);
			setSession(request, role, userID, username);			
			
			if(edao.validateUserLogin(username, password, role)){
				if(role.compareTo("customer") == 0)
				{
					model.setViewName("/customers/CustomerDashboard");
				}
				else if(role.equals("merchant"))
				{
					model.setViewName("/customers/merchant_Dashboard");
				}
				else if(role.equals("employee"))
				{
					model.setViewName("/employee/Tier1Dashboard");
				}
				else if(role.equals("manager"))
				{
					model.setViewName("/employee/Tier2Dashboard");
					//model.setViewName("/welcomepage");

				}
				else if(role.equals("admin"))
				{
					model.setViewName("/employee/AdminDashboard");
					//model.setViewName("welcomepage");
				}
			}
			else
			{
				redir.addFlashAttribute("error_msg", "user does not exist. Try again");
				model.setViewName("/login/Login");
			}
			return model;
	/*	}
		catch(Exception e){
			//TODO: Redirect
			System.out.println(e);
			//logger.error("errroe",e);
			throw new HandlerClass();
		}
	*/}
}
