package com.group10.controllers.commons;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
import com.group10.dao.otp.OneTimePasswordDao;
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


	@RequestMapping("/login/forgotpassword")
	public ModelAndView ForgotPass(){
		return new ModelAndView("/login/ForgetPassword");
	}

	@RequestMapping(value = "/login/forgotpassword/verifyemail", method = RequestMethod.POST)
	public ModelAndView verifyEmail(HttpServletRequest request, @RequestParam("recoveryEmail") String email){

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		OneTimePasswordDao odao = ctx.getBean("OneTimePasswordDao", OneTimePasswordDao.class);
		String message = odao.checkEmailSendOTP(email);
		if(message.equals("Exceeded otp limits. Account locked. Please contact bank staff.") ||
				message.equals("No such user.") || message.equals("Error!")){
			ModelAndView model = new ModelAndView("redirect:/login/Login");
			ctx.close();
			return model;
		}
		else{
			ModelAndView model = new ModelAndView("/login/OtpVerify");
			request.getSession().removeAttribute("forgotpassemail");
			request.getSession().setAttribute("forgotpassemail", email);
			model.addObject("message", message);
			ctx.close();
			return model;
		}
	}

	@RequestMapping(value = "/login/OTPVerify", method = RequestMethod.POST)
	public ModelAndView verifyOtp(HttpServletRequest request, @RequestParam("otp") String otp){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		OneTimePasswordDao odao = ctx.getBean("OneTimePasswordDao",OneTimePasswordDao.class);
		String email = (String) request.getSession().getAttribute("forgotpassemail");
		String message = odao.validateOTP(otp, email);
		if(message.equals("OTP not validated") ||
				message.equals("Too many attempts. Account locked.") ||
				message.equals("OTP Expired. Please try again.")){
			ModelAndView model = new ModelAndView("redirect:/login/forgotpassword");
			model.addObject("message", message);
			ctx.close();
			return model;
		}else{
			ModelAndView model = new ModelAndView("/login/ChangePassword");
			model.addObject("message", message);
			ctx.close();
			return model;
		}
	}

	@RequestMapping(value = "/changepassword", method = RequestMethod.POST)
	public ModelAndView changePasswordSubmit(RedirectAttributes redir, HttpServletRequest request, @RequestParam("newPassword") String newPassword,@RequestParam("confirmpassword") String confirmPassword) {
		//setGlobals(request); // TODO: LOG USER IN WHEN THEY SUBMIT CORRECT OTP
		userID = 5;
		username = "keverly1@asu.edu";
		ModelAndView model = new ModelAndView();
		String username = (String)request.getSession().getAttribute("forgotpassemail");
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		UserRegistrationDaoImpl udao = ctx.getBean("userRegistrationDaoImpl", UserRegistrationDaoImpl.class);

		Validator validator = new Validator();
		if(newPassword.equals(confirmPassword) && validator.validatePassword(confirmPassword))
		{
			String hashedPass = hash(newPassword);
			udao.updatePassword(username, hashedPass);
			model.setViewName("redirect:/login");

			LogsDaoImpl ldao = ctx.getBean("logsDaoImpl", LogsDaoImpl.class);
			ldao.saveLogs("Forgot password : " + username, "passsword changed successfully",userID, "internal");
			ctx.close();
			return model;
		}
		else{
			model.setViewName("redirect:/login/forgotpassword");
			LogsDaoImpl ldao = ctx.getBean("logsDaoImpl", LogsDaoImpl.class);
			ldao.saveLogs("Forgot password : " + username,"passsword change falied", userID, "internal");
			redir.addFlashAttribute("exception_message","password validation failed, try again");
			ctx.close();
			return model;
		}
	}

	@RequestMapping("/login/ChangePassword")
	public ModelAndView changePassword() {
		return new ModelAndView("/login/ChangePassword");
	}

	public String hash(String password) {

		MessageDigest hasher = null;
		try {
			hasher = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Error: No such algorithm in Java MessageDigest");
			e.printStackTrace();
		}

		String hashVal = password;
		StringBuffer hashBuffer = new StringBuffer();
		byte[] byteHashVal = hashVal.getBytes();
		hasher.update(byteHashVal);
		byte[] byteHash = hasher.digest(); // receive hash value
		for (byte bytes : byteHash) {
			//convert hash to hex value
			hashBuffer.append(String.format("%02x", bytes & 0xff));
		}
		hasher.reset();
		return hashBuffer.toString();
	}
}

