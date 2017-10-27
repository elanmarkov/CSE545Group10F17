package com.group10.dao.otp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.group10.dbmodels.Customer;
import com.group10.dbmodels.OTP;
import com.group10.dbmodels.LoginAuthentication;

public class OneTimePasswordDao extends JdbcDaoSupport{

	String role;
	int userID;
	String username;

	public void setGlobals(HttpServletRequest request) {
		role = (String) request.getSession().getAttribute("role");
		userID = (Integer) request.getSession().getAttribute("userID");
		username = (String) request.getSession().getAttribute("username");
	}
	
	public String verifyEmail(String email) {
		try {
			InternetAddress emailAddress = new InternetAddress(email);
			emailAddress.validate();
		} catch (AddressException ae) {
			return "Invalid User";
		}
		String query = "SELECT * FROM users WHERE username = '" + email + "' LIMIT 1";
		List<LoginAuthentication> userList = this.getJdbcTemplate().query(query,
				new BeanPropertyRowMapper<LoginAuthentication>(LoginAuthentication.class));
		if (userList.size() == 0)
			return "Invalid User";
		if (userList.get(0).getAttempts() == 0)
			return "Exceeded otp limits. Account locked. Contact bank";
		// Make a call to process OTP
		return processOTP(userList.get(0).getUsername());
	}
	
	public String verifyOTP(String otp, String email) {
		String query = "SELECT * FROM otp_table WHERE userEmail = '" + email + "' LIMIT 1";
		List<OTP> otpList = this.getJdbcTemplate().query(query, new BeanPropertyRowMapper<OTP>(OTP.class));
		if (otpList.size() == 0)
			return "Error in verifying OTP";
		long databaseDate = otpList.get(0).getStamp().getTime();
		long currentDate = (new Timestamp(System.currentTimeMillis())).getTime();
		if (otpList.get(0).getOtp().equals(otp) == false)
			return "Incorrect OTP";
		if ((currentDate - databaseDate > 180000))
			return "OTP Expired";
		String sql = "DELETE FROM otp_table WHERE userEmail = '" + email + "'";
		this.getJdbcTemplate().execute(sql);
		return "OTP Validated";
	}
	
	public String processOTP(String email) {
		String query = "SELECT * FROM otp_table WHERE userEmail = '" + email + "' LIMIT 1";
		List<OTP> otpList = this.getJdbcTemplate().query(query, new BeanPropertyRowMapper<OTP>(OTP.class));
		String otp = "";
		if (otpList.size() == 0) {
			otp = generateOTP(email);
			String sql = "INSERT INTO otp_table (userEmail,otp,timestamp,attempts) VALUES (?,?,?,?)";
			this.getJdbcTemplate().update(sql);
			return "User Verified";
		}
		if (otpList.get(0).getAttempts() >= 3) {
			String sql = "UPDATE users SET otpNonLocked = " + 0 + " WHERE username = '" + email + "'";
			this.getJdbcTemplate().update(sql);
			sql = "DELETE FROM otp_table WHERE userEmail = '" + email + "'";
			this.getJdbcTemplate().execute(sql);
			return "Account Locked";
		}
		otp = generateOTP(email);
		query = "UPDATE otp_table SET attempts = " + (otpList.get(0).getAttempts() + 1) + ", timestamp = '"
				+ (new Timestamp(System.currentTimeMillis())) + "', otp = " + otp + " WHERE userEmail = '" + email
				+ "'";
		;
		this.getJdbcTemplate().update(query);
		return "User Verified";
	}
	
	
	public String generateOTP(String email) {
		SendOTPByMail OTPMailer = new SendOTPByMail(email);
		return OTPMailer.sendOTPReturnHexVal();
	}

	public String getEmailFromPayerID(int payerid) {
		String sql = "SELECT email FROM external_users WHERE id =" + username + " LIMIT 1";
		List<Customer> customerList = this.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Customer>(Customer.class));
		if (customerList.size() == 0)
			return "User Not Found";
		return customerList.get(0).getEmail();
	}
	
	public String getOTPFromEmail(String email) {
		String sql = "SELECT otp FROM otp_table WHERE userEmail ='" + email + "' LIMIT 1";
		List<OTP> otpList = this.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<OTP>(OTP.class));
		if (otpList.size() == 0)
			return "OTP Not Found";
		return otpList.get(0).getUserEmail();
	}

	public void sendEmailToUser(String email, String payeeid, double amount) {
		Properties prop = new Properties();
		try {
			prop.load(OneTimePasswordDao.class.getClassLoader().getResourceAsStream("smtp.properties"));	
			
		} catch(FileNotFoundException fne) {
			fne.printStackTrace();
			return;
		} catch(IOException ioe) {
			ioe.printStackTrace();
			return;
		}

		final String username = prop.getProperty("username");
		final String password = prop.getProperty("password");
		prop= new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("securebanking.group10@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject("A critical transaction has been initiated");
			{
				message.setText("Transfer amount to: " + payeeid + "\n\nAmount: " + amount);
			}
			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	
}