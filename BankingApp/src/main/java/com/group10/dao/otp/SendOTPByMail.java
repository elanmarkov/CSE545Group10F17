package com.group10.dao.otp;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

// The mail service in this module is based on the following tutorial:
// https://www.mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/

public class SendOTPByMail {
	public String sendOTPReturnHexVal(String email) {
		GenerateRandomOTP generator = new GenerateRandomOTP();
		String OTP = generator.getNextOTP();
		// send a mail here
		
		final String username = "cse545g10@gmail.com";
		final String password = "cse545group10";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true"); // use SSL on HTTPS implmntn
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "465"); // SSL port
		
		Session session = Session.getInstance(props,
	    new javax.mail.Authenticator() {
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		 }
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(email));
			message.setSubject("OTP Value");
			message.setText("Your OTP is: " + OTP);

			Transport.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		return OTP;
	}

}