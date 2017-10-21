package com.group10.dbmodels;

import java.sql.Timestamp;

public class CompletedExternalRequests {
	private int id;
	private double amount;
	private Timestamp stamp;
	private int to_account_id;
	private int from_account_id;
	private String description;
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
	public int getTo_account_id() {
		return to_account_id;
	}
	public void setTo_account_id(int to_account_id) {
		this.to_account_id = to_account_id;
	}
	public int getFrom_account_id() {
		return from_account_id;
	}
	public void setFrom_account_id(int from_account_id) {
		this.from_account_id = from_account_id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
