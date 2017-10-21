package com.group10.dbmodels;

public class CreditCard {
	private int id;
	private String credit_card_number;
	private int external_users_id;
	private int cvv;
	private double credit_limit;
	private double current_amount_due;
	private String cycle_date;
	private String due_date;
	private double last_bill_amount;
	private double apr;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCredit_card_number() {
		return credit_card_number;
	}
	public void setCredit_card_number(String credit_card_number) {
		this.credit_card_number = credit_card_number;
	}
	public int getExternal_users_id() {
		return external_users_id;
	}
	public void setExternal_users_id(int external_users_id) {
		this.external_users_id = external_users_id;
	}
	public int getCvv() {
		return cvv;
	}
	public void setCvv(int cvv) {
		this.cvv = cvv;
	}
	public double getCredit_limit() {
		return credit_limit;
	}
	public void setCredit_limit(double credit_limit) {
		this.credit_limit = credit_limit;
	}
	public double getCurrent_amount_due() {
		return current_amount_due;
	}
	public void setCurrent_amount_due(double current_amount_due) {
		this.current_amount_due = current_amount_due;
	}
	public String getCycle_date() {
		return cycle_date;
	}
	public void setCycle_date(String cycle_date) {
		this.cycle_date = cycle_date;
	}
	public String getDue_date() {
		return due_date;
	}
	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}
	public double getLast_bill_amount() {
		return last_bill_amount;
	}
	public void setLast_bill_amount(double last_bill_amount) {
		this.last_bill_amount = last_bill_amount;
	}
	public double getApr() {
		return apr;
	}
	public void setApr(double apr) {
		this.apr = apr;
	}
}
