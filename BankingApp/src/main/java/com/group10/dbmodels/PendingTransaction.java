package com.group10.dbmodels;

import java.sql.Timestamp;

public class PendingTransaction {
	private int id;
	private int initiatorID;
	private double amount;
	private Timestamp stamp;
	private int toAccountID;
	private String description;
	private int fromAccountID;
	
	public PendingTransaction() {
		
	}
	public PendingTransaction(int initiatorID, double amount, int toAccountID, int fromAccountID, String description) {
		this.initiatorID = initiatorID;
		this.amount = amount;
		this.stamp = new Timestamp(System.currentTimeMillis());
		this.toAccountID = toAccountID;
		this.fromAccountID = fromAccountID;
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getInitiatorID() {
		return initiatorID;
	}
	public void setInitiatorID(int initiatorID) {
		this.initiatorID = initiatorID;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Timestamp getStamp() {
		return stamp;
	}
	public void setStamp(Timestamp stamp) {
		this.stamp = stamp;
	}
	public int getToAccountID() {
		return toAccountID;
	}
	public void setToAccountID(int toAccountID) {
		this.toAccountID = toAccountID;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getFromAccountID() {
		return fromAccountID;
	}
	public void setFromAccountID(int fromAccountID) {
		this.fromAccountID = fromAccountID;
	}
	
	
}
