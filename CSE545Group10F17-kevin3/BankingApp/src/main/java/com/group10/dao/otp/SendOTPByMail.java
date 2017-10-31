package com.group10.dao.otp;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

// The mail service in this module is based on the following tutorial:
// https://www.mkyong.com/spring/spring-sending-e-mail-via-gmail-smtp-server-with-mailsender/

public class SendOTPByMail {
    private MailSender mailSender;
    public void setMailSender (MailSender mailSender) {
        this.mailSender = mailSender;
    }
    public String sendOTPReturnHexVal(String email) {
        GenerateRandomOTP generator = new GenerateRandomOTP();
        String OTP = generator.getNextOTP();
        // send a mail here
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("cse545g10@gmail.com");
        message.setTo(email);
        message.setSubject("OTP Value");
        message.setText("Your OTP is: " + OTP);
        mailSender.send(message);

        return OTP;
    }

}