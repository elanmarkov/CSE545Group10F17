package com.group10.dbmodels;

import java.sql.Timestamp;

public class OTP {
	private String email;
	private String hexValOTP;
	private Timestamp issueTime;
	private int attempts;
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
	public int getAttempts() {
		return attempts;
	}
	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}
	public Timestamp getIssueTime() {
		return issueTime;
	}
	public void setStamp(Timestamp issueTime) {
		this.issueTime = issueTime;
	}


}