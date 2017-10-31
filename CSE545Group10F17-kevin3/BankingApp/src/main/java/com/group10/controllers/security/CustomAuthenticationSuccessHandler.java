package com.group10.controllers.security;
/*
 * Auhor: Harsha Vardhan Yellamelli
 * Reference: https://javapointers.com/tutorial/spring-custom-authenticationsuccesshandler-example-2/
 */
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
/*
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
*/
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.group10.dao.employee.EmpFunctionsDaoImpl;

public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationFailureHandler implements AuthenticationSuccessHandler {

	public void onAuthenticationSuccess(HttpServletRequest arg0, HttpServletResponse arg1, Authentication arg2)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpSession session = arg0.getSession();
	//	boolean captchaVerified = verify(arg0.getParameter("g-recaptcha-response"));
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
        String role_raw = arg2.getAuthorities().toString();
        String role = role_raw.substring(1, role_raw.length() -1 );
        EmpFunctionsDaoImpl edao = ctx.getBean("empFunctionsDaoImpl", EmpFunctionsDaoImpl.class);

        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = authUser.getUsername();
    	int userID = edao.getUserIdByName(username);
    	session.setAttribute("username", username);
		session.setAttribute("userID", userID);
		session.setAttribute("role", role);
		arg1.setStatus(HttpServletResponse.SC_OK);

		if (authUser.isEnabled()) {
			if(role.equalsIgnoreCase("ROLE_ADMIN")) arg1.sendRedirect("/BankingApp/employee/AdminDashboard");
			else if(role.equalsIgnoreCase("ROLE_REGULAR")) arg1.sendRedirect("/BankingApp/employee/Tier1Dashboard");
			else if(role.equalsIgnoreCase("ROLE_MANAGER")) arg1.sendRedirect("/BankingApp/employee/Tier2Dashboard");
			else if(role.equalsIgnoreCase("ROLE_CUSTOMER")) arg1.sendRedirect("/BankingApp/customer/dashboard");
			else if(role.equalsIgnoreCase("ROLE_MERCHANT")) arg1.sendRedirect("/BankingApp/customer/dashboard");

		} else {
			arg1.sendRedirect("/BankingApp/login/ChangePassword");
			ctx.close();
		}

	}
}