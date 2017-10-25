/*
 * Author: Kevin Everly
 */
package com.group10.dbmodels;

import java.security.Timestamp;

public class CompletedTransaction {
	private int id;
	private double amount;
	private int initiatorID;
	private Timestamp stamp;
	private Timestamp completedStamp; // The time that the transaction was approved/rejected
	private Integer toAccountID;
	private String description;
	private Integer fromAccountID;
	private int reviewerID; // The employee that approved/rejected
	private String status; //Rejected or Approved
	
	public CompletedTransaction() {
		// Empty constructor used for Beans
	}
	public CompletedTransaction(PendingTransaction pendTrans, int reviewerID, String status) {
		this.amount = pendTrans.getAmount();
		this.toAccountID = pendTrans.getToAccountID();
		this.description = pendTrans.getDescription();
		this.fromAccountID = pendTrans.getFromAccountID();
		this.reviewerID = reviewerID;
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
	public int getInitiatorID() {
		return this.initiatorID;
	}
	public void setInitiatorID(int initiatorID) {
		this.initiatorID = initiatorID;
	}
	public Timestamp getStamp() {
		return stamp;
	}
	public void setStamp(Timestamp stamp) {
		this.stamp = stamp;
	}
	public Timestamp getCompletedStamp() {
		return this.completedStamp;
	}
	public void setCompletedStamp(Timestamp compStamp) {
		this.completedStamp = compStamp;
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
	public int getReviewerID() {
		return reviewerID;
	}
	public void setReviewerID(int reviewerID) {
		this.reviewerID = reviewerID;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
