package com.group10.dbmodels;

import java.sql.Timestamp;

public class CompletedExternalRequest {

	private int id;
	private double amount;
	private Timestamp stamp;
	private Timestamp completedStamp;
	private int toAccountID;
	private int fromAccountID;
	private String description;
	private int receiverID;
	private int initiatorID;
	private String status;
	
	public CompletedExternalRequest() {
		
	}
	public CompletedExternalRequest(PendingExternalRequest req, String status) {
		this.amount = req.getAmount();
		this.stamp = req.getStamp();
		this.toAccountID = req.getToAccountID();
		this.fromAccountID = req.getFromAccountID();
		this.description = req.getDescription();
		this.receiverID = req.getReceiverID();
		this.initiatorID = req.getInitiatorID();
		this.status = status;
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
	public Timestamp getCompletedStamp() {
		return completedStamp;
	}
	public void setCompletedStamp(Timestamp completedStamp) {
		this.completedStamp = completedStamp;
	}
	public int getToAccountID() {
		return toAccountID;
	}
	public void setToAccountID(int toAccountID) {
		this.toAccountID = toAccountID;
	}
	public int getFromAccountID() {
		return fromAccountID;
	}
	public void setFromAccountID(int fromAccountID) {
		this.fromAccountID = fromAccountID;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getReceiverID() {
		return receiverID;
	}
	public void setReceiverID(int receiverID) {
		this.receiverID = receiverID;
	}
	public int getInitiatorID() {
		return initiatorID;
	}
	public void setInitiatorID(int initiatorID) {
		this.initiatorID = initiatorID;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
