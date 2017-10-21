package com.group10.dbmodels;

public class CreditAccount {
	private int id;
	private int external_users_id;
	private String account_no;
	private double balance;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getExternal_users_id() {
		return external_users_id;
	}
	public void setExternal_users_id(int external_users_id) {
		this.external_users_id = external_users_id;
	}
	public String getAccount_no() {
		return account_no;
	}
	public void setAccount_no(String account_no) {
		this.account_no = account_no;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
}
