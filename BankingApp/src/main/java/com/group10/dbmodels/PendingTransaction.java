/*
 * Author: Kevin Everly
 */
package com.group10.dbmodels;

import java.sql.Timestamp;

public class PendingTransaction {
	private int id;
	private int initiatorID;
	private double amount;
	private Timestamp stamp;
	private Integer toAccountID;
	private String description;
	private Integer fromAccountID;
	
	public PendingTransaction() {
		// Empty constructor used for Beans
	}
	public PendingTransaction(int initiatorID, double amount, Integer toAccountID, Integer fromAccountID, String description) {
		this.initiatorID = initiatorID;
		this.amount = amount;
		this.stamp = new Timestamp(System.currentTimeMillis());
		this.toAccountID = toAccountID;
		this.fromAccountID = fromAccountID;
		this.description = description;
	}
	public PendingTransaction(CompletedExternalRequest req) {
		this.initiatorID = req.getInitiatorID();
		this.amount = req.getAmount();
		this.stamp = req.getCompletedStamp();
		this.toAccountID = req.getToAccountID();
		this.fromAccountID = req.getFromAccountID();
		this.description = req.getDescription();
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
	public Integer getToAccountID() {
		return toAccountID;
	}
	public void setToAccountID(Integer toAccountID) {
		this.toAccountID = toAccountID;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getFromAccountID() {
		return fromAccountID;
	}
	public void setFromAccountID(Integer fromAccountID) {
		this.fromAccountID = fromAccountID;
	}
	
	
}
