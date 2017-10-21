package com.group10.controllers.commons;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.group10.controllers.security.HandlerClass;

@Controller
public class ForgotPassword {
	String role;
	int userID;
	String username;
	
	public void setGlobals(HttpServletRequest request){
		role = (String) request.getSession().getAttribute("role");
		//userID = (int) request.getSession().getAttribute("userID");
		userID = 1;
		username = (String) request.getSession().getAttribute("username");		
	}
	
	@ExceptionHandler(HandlerClass.class)
    public String handleResourceNotFoundException() {
        return "redirect:/raiseexception";
    }
	
	
	@RequestMapping("forgotpassowrd")
		public ModelAndView ForgotPass(){
			return new ModelAndView("/commons/ForgotPassword");
		}
	@RequestMapping("verifyemail")
		public ModelAndView verifyEmail(HttpServletRequest servlet){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");	
		OtpDaoImpl odao = ctx.getBean("otpDaoImpl", OtpDaoImpl.class);
		
		return model;
	}
	
	
}
