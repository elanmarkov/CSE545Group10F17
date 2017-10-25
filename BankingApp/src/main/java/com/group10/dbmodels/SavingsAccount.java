package com.group10.dbmodels;

import java.math.BigInteger;

public class SavingsAccount {
	private int userId;
	private int accountNumber;
	private double balance;
	
	public int getUserId() {
		return userId;
	}
	public void setUserID(int userID) {
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
