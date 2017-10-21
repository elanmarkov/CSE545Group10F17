package com.group10.dbmodels;

import java.math.BigInteger;
import java.sql.Date;

public class CreditCard {
	private float interest;
	private int credit_limit;
	private BigInteger available_balance;
	private BigInteger current_amount_due;
	private Date cycle_date;
	private Date due_date;
	private int account_number;
	private BigInteger last_bill_amount;
	private float apr;
	
	public float getInterest() {
		return interest;
	}
	public void setInterest(float interest) {
		this.interest = interest;
	}
	
	public int getCredit_limit() {
		return credit_limit;
	}
	public void setCredit_limit(int credit_limit) {
		this.credit_limit = credit_limit;
	}
	public BigInteger getAvailable_balance() {
		return available_balance;
	}
	public void setAvailable_balance(BigInteger available_balance) {
		this.available_balance = available_balance;
	}
	public BigInteger getCurrent_amount_due() {
		return current_amount_due;
	}
	public void setCurrent_amount_due(BigInteger current_amount_due) {
		this.current_amount_due = current_amount_due;
	}
	public Date getCycle_date() {
		return cycle_date;
	}
	public void setCycle_date(Date cycle_date) {
		this.cycle_date = cycle_date;
	}
	public Date getDue_date() {
		return due_date;
	}
	public void setDue_date(Date due_date) {
		this.due_date = due_date;
	}
	public int getAccount_number() {
		return account_number;
	}
	public void setAccount_number(int account_number) {
		this.account_number = account_number;
	}
	public BigInteger getLast_bill_amount() {
		return last_bill_amount;
	}
	public void setLast_bill_amount(BigInteger last_bill_amount) {
		this.last_bill_amount = last_bill_amount;
	}
	public float getApr() {
		return apr;
	}
	public void setApr(float apr) {
		this.apr = apr;
	}
	


}
