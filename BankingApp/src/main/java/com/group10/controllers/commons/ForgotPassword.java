package com.group10.controllers.commons;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.group10.controllers.security.HandlerClass;
import com.group10.dao.employee.UserRegistrationDaoImpl;
import com.group10.dao.employee.Validator;
import com.group10.dao.logs.LogsDaoImpl;
import com.group10.dao.otp.OtpDaoImpl;
import com.group10.dbmodels.DbLogs;

@Controller
public class ForgotPassword {
	String role;
	int userID;
	String username;
	
	public void setGlobals(HttpServletRequest request){
		role = (String) request.getSession().getAttribute("role");
		userID = (Integer) request.getSession().getAttribute("userID");
		username = (String) request.getSession().getAttribute("username");		
	}
	
	@ExceptionHandler(HandlerClass.class)
    public String handleResourceNotFoundException() {
        return "redirect:/exception";
    }
	
	
	@RequestMapping("/forgotpassowrd")
		public ModelAndView ForgotPass(){
			return new ModelAndView("/login/ForgotPassword");
		}
	@RequestMapping(value = "forgotpassword/verifyemail", method = RequestMethod.POST)
		public ModelAndView verifyEmail(HttpServletRequest request, @RequestParam("Email") String email){
			setGlobals(request);
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");	
			OtpDaoImpl odao = ctx.getBean("otpDaoImpl", OtpDaoImpl.class);
			String message = odao.verifyEmail(email);
			if(message.equalsIgnoreCase("Exceeded otp limits. Account locked. Contact bank")){
				ModelAndView model = new ModelAndView("/login/login");
				ctx.close();
				return model;
			}
			else{
				ModelAndView model = new ModelAndView("/login/ForgotPasswordOtp");
				request.getSession().removeAttribute("forgotpassemail");
		        request.getSession().setAttribute("forgotpassemail", email);
		        model.addObject("message", message);
		        ctx.close();
				return model;
			}
		}
	
	@RequestMapping(value = "forgotpassword/verifyotp", method = RequestMethod.POST)
	public ModelAndView verifyOtp(HttpServletRequest request, @RequestParam("otp") String otp){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		OtpDaoImpl odao = ctx.getBean("otpDaoImpl",OtpDaoImpl.class);
		String email = (String) request.getSession().getAttribute("forgotpassemail");
		String message = odao.verifyOTP(otp, email);
		if(message.equalsIgnoreCase("Incorrect OTP") || message.equalsIgnoreCase("Error in verifying OTP")){
			ModelAndView model = new ModelAndView("/login/ForgotPasswordOtp");
			model.addObject("message", message);
			ctx.close();
			return model;
		}else{
			ModelAndView model = new ModelAndView("ChangePassword");
	        model.addObject("message", message);
	        ctx.close();
	        return model;
		}
	}
	
	@RequestMapping(value = "/forgotpassword/changepassword", method = RequestMethod.POST)
	public ModelAndView changePassword(RedirectAttributes redir, HttpServletRequest request, @RequestParam("newpassword") String newPassword,@RequestParam("confirmpassword") String confirmPassword) {
		ModelAndView model = new ModelAndView();
		String username = (String)request.getSession().getAttribute("forgotpassemail");
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
        UserRegistrationDaoImpl udao = ctx.getBean("userRegistrationDaoImpl", UserRegistrationDaoImpl.class);
        String type;
        if(role.equals("ADMIN")||role.equals("TIER2")||role.equals("TIER1"))
	    	type = "internal";
	    else type = "external";
        Validator validator = new Validator();
        if(newPassword.equals(confirmPassword) && validator.validatePassword(newPassword))
        {
        	BCryptPasswordEncoder Encoder = new BCryptPasswordEncoder();
            udao.updatePassword(Encoder.encode(newPassword), username);
	        model.setViewName("/login/login");

            LogsDaoImpl ldao = ctx.getBean("logsDaoImpl", LogsDaoImpl.class); 	    
            ldao.saveLogs("Forgot password : " + username, "passsword changed successfully",userID, "internal");      	
            ctx.close();
            return model;
        }
        else{
            model.setViewName("/forgotpassword/changepassword");
        	LogsDaoImpl ldao = ctx.getBean("logsDaoImpl", LogsDaoImpl.class);
            ldao.saveLogs("Forgot password : " + username,"passsword change falied", userID, "internal");    
             redir.addFlashAttribute("exception_message","password validation failed, try again");
             ctx.close();
             return model;
        }
	}
}
