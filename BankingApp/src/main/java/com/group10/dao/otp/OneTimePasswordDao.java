package com.group10.dao.otp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.group10.dbmodels.Customer;
import com.group10.dbmodels.OTP;
import com.group10.dbmodels.LoginAuthentication;

public class OneTimePasswordDao extends JdbcDaoSupport{

	String role;
	int userID;
	String username;
	final int maxAttempts = 5;
	final int maxTimeMS = 300000;

	public void setGlobals(HttpServletRequest request) {
		role = (String) request.getSession().getAttribute("role");
		userID = (Integer) request.getSession().getAttribute("userID");
		username = (String) request.getSession().getAttribute("username");
	}
	public String checkEmailSendOTP(String email) {
		String retVal = "Error!";
		String checker = "SELECT * FROM user_login WHERE username = '" + email + "' LIMIT 1";
		List<LoginAuthentication> matches = this.getJdbcTemplate().query(checker, new BeanPropertyRowMapper<LoginAuthentication>(LoginAuthentication.class));
		if(matches.size() == 0) {
			retVal = "No such user.";
		}
		else {
			String OTPQuery = "SELECT * FROM OTP WHERE email = '" + email + "' LIMIT 1";
			List<OTP> otpmatch = this.getJdbcTemplate().query(OTPQuery, new BeanPropertyRowMapper<OTP>(OTP.class));
			if(otpmatch.size() == 0) {
				String OTP = sendOTPByEmail(email);
				Timestamp issueTime = new Timestamp(System.currentTimeMillis());
				String OTPInsert = "insert into OTP (email, hexValOTP, issueTime, attempts) values(?,?,?,?)";
				this.getJdbcTemplate().update(OTPInsert, new Object[]{email, OTP, issueTime, 0});
				retVal = "OTP issued. Check your email.";
			}
			else {
				if(otpmatch.get(0).getAttempts() > maxAttempts) {
					retVal = "Exceeded otp limits. Account locked. Please contact bank staff.";
				}
				else {
					retVal = "You already have been issued an OTP. Please enter that value.";
				}
			}
		}
		return retVal;
	}
	public String validateOTP(String otp, String email) {
		String retVal = "OTP not validated";
		String trueOTP = "";
		String OTPQuery = "SELECT * FROM OTP WHERE email = '" + email + "' LIMIT 1";
		List<OTP> matches = this.getJdbcTemplate().query(OTPQuery, new BeanPropertyRowMapper<OTP>(OTP.class));
		if(matches.size()==0) return retVal;
		trueOTP = matches.get(0).getHexValOTP();
		long currTime = (new Timestamp(System.currentTimeMillis())).getTime();
		long stampedTime = (new Timestamp(System.currentTimeMillis())).getTime();
		//long stampedTime = matches.get(0).getIssueTime().getTime();
		long timeDelay = currTime - stampedTime;
		int numGuesses = matches.get(0).getAttempts();
		if(otp.equals(trueOTP) && numGuesses <= maxAttempts && timeDelay < maxTimeMS) {
			retVal = "OTP validated";
			this.getJdbcTemplate().execute("DELETE FROM OTP WHERE email = '" + email + "'");
		}
		else {
			if(numGuesses > maxAttempts) {
				retVal = "Too many attempts. Account locked.";
			}
			else if (timeDelay >= maxTimeMS) {
				retVal = "OTP Expired. Please try again.";
				this.getJdbcTemplate().execute("DELETE FROM otp WHERE email = '" + email + "'");
			}
			else {
				String update = "UPDATE OTP SET hexValOTP = " + trueOTP + ", issueTime = '"
						+ matches.get(0).getIssueTime() + "', attempts = " + (numGuesses + 1) + " WHERE email = '" + email
						+ "'";
				this.getJdbcTemplate().update(update);
			}
		}
		return retVal;
	}
	private String sendOTPByEmail(String email) {
		ApplicationContext context =
	             new ClassPathXmlApplicationContext("Spring-Mail.xml");
		SendOTPByMail sender = (SendOTPByMail) context.getBean("SendOTPByMail");
		((ClassPathXmlApplicationContext) context).close();
		return sender.sendOTPReturnHexVal(email);
	}
}