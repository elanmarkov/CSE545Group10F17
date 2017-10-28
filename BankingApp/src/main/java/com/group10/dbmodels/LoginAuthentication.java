package com.group10.dbmodels;

import java.sql.Timestamp;

public class LoginAuthentication {

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(int accountStatus) {
		this.accountStatus = accountStatus;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Timestamp getStamp() {
		return stamp;
	}
	public void setStamp(Timestamp stamp) {
		this.stamp = stamp;
	}
	private String username;
    private String password;
    private String role;
    private int accountStatus;
    private int attempts;
    public int getAttempts() {
		return attempts;
	}
	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}
	private Timestamp stamp;
    private int userId;
	   
}
