package com.group10.dbmodels;

import java.sql.Timestamp;

public class MerchantPayment {
	private int id;
	private double amount;
	private Timestamp stamp;
	private int payer_id;
	private int token;
	private String description;
	private String status;
	private int merchant_id;
	
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
	public int getPayer_id() {
		return payer_id;
	}
	public void setPayer_id(int payer_id) {
		this.payer_id = payer_id;
	}
	public int getToken() {
		return token;
	}
	public void setToken(int token) {
		this.token = token;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getMerchant_id() {
		return merchant_id;
	}
	public void setMerchant_id(int merchant_id) {
		this.merchant_id = merchant_id;
	}
}
