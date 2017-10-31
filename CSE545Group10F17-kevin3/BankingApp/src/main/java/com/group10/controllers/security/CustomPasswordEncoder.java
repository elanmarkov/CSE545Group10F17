package com.group10.controllers.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CustomPasswordEncoder implements PasswordEncoder {

    public String encode(CharSequence rawPassword) {
        MessageDigest hasher = null;
        try {
            hasher = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error: No such algorithm in Java MessageDigest");
            e.printStackTrace();
        }

        String hashVal = rawPassword.toString();
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

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        MessageDigest hasher = null;
        try {
            hasher = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error: No such algorithm in Java MessageDigest");
            e.printStackTrace();
        }

        String hashVal = rawPassword.toString();
        StringBuffer hashBuffer = new StringBuffer();
        byte[] byteHashVal = hashVal.getBytes();
        hasher.update(byteHashVal);
        byte[] byteHash = hasher.digest(); // receive hash value
        for (byte bytes : byteHash) {
            //convert hash to hex value
            hashBuffer.append(String.format("%02x", bytes & 0xff));
        }
        hasher.reset();
        if(hashBuffer.toString().equals(encodedPassword)) {
            return true;
        } else {
            return false;
        }
    }
}