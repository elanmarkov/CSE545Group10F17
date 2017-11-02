package com.group10.dbmodels;

public class CreditCard {
	private int id;
	private String creditCardNumber;
	private int userId;
	private int cvv;
	private double creditLimit;
	private double currentAmountDue;
	private String cycleDate;
	private String dueDate;
	private double lastBillAmount;
	private double apr;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public int getCvv() {
		return cvv;
	}
	public void setCvv(int cvv) {
		this.cvv = cvv;
	}
	public double getCreditLimit() {
		return creditLimit;
	}
	public void setCreditLimit(double creditLimit) {
		this.creditLimit = creditLimit;
	}
	public double getCurrentAmountDue() {
		return currentAmountDue;
	}
	public void setCurrentAmountDue(double currentAmountDue) {
		this.currentAmountDue = currentAmountDue;
	}
	public String getCycleDate() {
		return cycleDate;
	}
	public void setCycleDate(String cycleDate) {
		this.cycleDate = cycleDate;
	}
	public String getdueDate() {
		return dueDate;
	}
	public void setdueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public double getLastBillAmount() {
		return lastBillAmount;
	}
	public void setLastBillAmount(double lastBillAmount) {
		this.lastBillAmount = lastBillAmount;
	}
	public double getApr() {
		return apr;
	}
	public void setApr(double apr) {
		this.apr = apr;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
}