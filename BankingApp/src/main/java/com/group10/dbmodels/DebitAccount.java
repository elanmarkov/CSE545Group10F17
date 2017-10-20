package com.group10.dbmodels;

public class DebitAccount extends BankAccount {

	private double currentBalance;
	
	public DebitAccount(AccountType accountType) {
		this.setAccountType(accountType);
	}

	public double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(double currentBalance) {
		this.currentBalance = currentBalance;
	}		
}
