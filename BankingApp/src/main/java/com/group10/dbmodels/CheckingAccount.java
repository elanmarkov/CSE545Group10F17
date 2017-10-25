package com.group10.dbmodels;


public class CheckingAccount {
	
	private int userId;
	private int accountNumber;
	private double balance;
	
	public int getUserID() {
		return userId;
	}
	public void setUserId(int userID) {
		this.userId = userId;
	}
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
}
