package com.group10.dbmodels;

import java.sql.Timestamp;

public class PendingExternalRequest {
	private int id;
	private double amount;
	private Timestamp stamp;
	private String toAccountID;
	private String fromAccountID;
	private String description;
	private int payerID;
	private int initiatorID;
	
	public PendingExternalRequest () {
		// NEEDED FOR BEANS
	}
	public PendingExternalRequest(double amount, String toAccountID, String fromAccountID, String description, int payerID, int initiatorID) {
		this.amount = amount;
		this.toAccountID = toAccountID;
		this.fromAccountID = fromAccountID;
		this.description = description;
		this.payerID = payerID;
		this.initiatorID = initiatorID;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getToAccountID() {
		return toAccountID;
	}
	public void setToAccountID(String toAccountID) {
		this.toAccountID = toAccountID;
	}
	public String getFromAccountID() {
		return fromAccountID;
	}
	public void setFromAccountID(String fromAccountID) {
		this.fromAccountID = fromAccountID;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getPayerID() {
		return payerID;
	}
	public void setPayerID(int payerID) {
		this.payerID = payerID;
	}
	public int getInitiatorID() {
		return initiatorID;
	}
	public void setInitiatorID(int initiatorID) {
		this.initiatorID = initiatorID;
	}
}
