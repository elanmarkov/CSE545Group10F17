package com.group10.dao.employee;

import java.util.regex.Pattern;


public class Validator{
	public Boolean validateEmail(String email){
		String pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*" +
			      "@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		return Pattern.matches(pattern, email);
	}
	
	public Boolean validateName(String name ){
		String pattern = "[a-zA-Z\\s]+";
		return Pattern.matches(pattern, name);
	}
	public Boolean validateNumber(String number){
		String pattern = "[0-9]+";
		return Pattern.matches(pattern, String.valueOf(number));
	}
	public Boolean validatePassword(String password ){
		String pattern =  "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,16}$";
		return Pattern.matches(pattern, password);
	}
	public Boolean validateSSN(String ssn){
		String pattern = "^[0-9]{3}\\-?[0-9]{2}\\-?[0-9]{4}$";
		return Pattern.matches(pattern, ssn);
	}
}