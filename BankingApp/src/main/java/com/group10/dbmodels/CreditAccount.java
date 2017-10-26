package com.group10.dbmodels;

import java.math.BigInteger;

public class CreditAccount {
	private int id;
	private int userId;
	private String accountNumber;
	private double currentAmountDue;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public double getCurrentAmountDue() {
		return currentAmountDue;
	}
	public void setCurrentAmountDue(double currentAmountDue) {
		this.currentAmountDue = currentAmountDue;
	} 
}
