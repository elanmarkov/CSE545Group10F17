package com.group10.dao.transaction;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.group10.dao.otp.GenerateRandomOTP;

// The mail service in this module is based on the following tutorial:
// https://www.mkyong.com/spring/spring-sending-e-mail-via-gmail-smtp-server-with-mailsender/

public class SendAlertByMail {
    private MailSender mailSender;
    public void setMailSender (MailSender mailSender) {
        this.mailSender = mailSender;
    }
    public void sendAlert(String email) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("cse545g10@gmail.com");
        message.setTo(email);
        message.setSubject("Alert!");
        message.setText("A critical transaction involving your account was initiated."
        		+ " Please check your account. If you are not able to access your account,"
        		+ "please use the Forgot Password feature to recover your account.");
        mailSender.send(message);
    }

}