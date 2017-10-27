package com.group10.dbmodels;

import java.sql.Timestamp;

public class OTP {
	private String email;
	private String hexValOTP;
	private int numGuesses;
	private Timestamp stamp;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getHexValOTP() {
		return hexValOTP;
	}
	public void setHexValOTP(String hexValOTP) {
		this.hexValOTP = hexValOTP;
	}
	public int getNumGuesses() {
		return numGuesses;
	}
	public void setNumGuesses(int numGuesses) {
		this.numGuesses = numGuesses;
	}
	public Timestamp getStamp() {
		return stamp;
	}
	public void setStamp(Timestamp stamp) {
		this.stamp = stamp;
	}


}