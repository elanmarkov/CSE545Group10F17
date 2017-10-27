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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.group10.dao.employee.EmpFunctionsDaoImpl;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

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

    	if(role.equalsIgnoreCase("ROLE_ADMIN")) arg1.sendRedirect("/BankingApp/employee/AdminDashboard");
    	else if(role.equalsIgnoreCase("ROLE_REGULAR")) arg1.sendRedirect("/BankingApp/employee/Tier1Dashboard");
    	else if(role.equalsIgnoreCase("ROLE_MANAGER")) arg1.sendRedirect("/BankingApp/employee/Tier2Dashboard");
    	else if(role.equalsIgnoreCase("ROLE_CUSTOMER")) arg1.sendRedirect("/BankingApp/customer/CustomerDashboard");
    	else if(role.equalsIgnoreCase("ROLE_MERCHANT")) arg1.sendRedirect("/BankingApp/customer/Merchantdashboard");
    	ctx.close();  
	}
/*
//Verify captcha received during the login session
public boolean verify(String captcha) throws IOException{

	final String url = "https://www.google.com/recaptcha/api/siteverify";
	final String secret = "6LecawoUAAAAAJExbMTuQv-w2frvPCGcboe_Lo2q";
	final String USER_AGENT = "Mozilla/5.0";

		if (captcha == null || "".equals(captcha)) {
			return false;
		}
		
		try{
			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
	
			// add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	
			String postParams = "secret=" + secret + "&response="+ captcha;
	
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(postParams);
			wr.flush();
			wr.close();
	
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
	
			//parse JSON response and return 'success' value
			JsonReader jsonReader = Json.createReader(new StringReader(response.toString()));
			JsonObject jsonObject = jsonReader.readObject();
			jsonReader.close();
			
			return jsonObject.getBoolean("success");
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
	}
*/
}
