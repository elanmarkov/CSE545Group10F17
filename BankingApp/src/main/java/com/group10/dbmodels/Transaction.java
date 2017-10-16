package com.group10.dbmodels;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Transaction {
	private int id;
	private int payer_id;
	private int payee_id;
	private BigDecimal amount;
	private String hashvalue;
	private String transaction_type;
	private String description;
	private String status;
	private String approver;
	private boolean critical;
	private Timestamp timestamp_created;
	private Timestamp timestamp_updated;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPayer_id() {
		return payer_id;
	}
	public void setPayer_id(int payer_id) {
		this.payer_id = payer_id;
	}
	public int getPayee_id() {
		return payee_id;
	}
	public void setPayee_id(int payee_id) {
		this.payee_id = payee_id;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getHashvalue() {
		return hashvalue;
	}
	public void setHashvalue(String hashvalue) {
		this.hashvalue = hashvalue;
	}
	public String getTransaction_type() {
		return transaction_type;
	}
	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
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
	public String getApprover() {
		return approver;
	}
	public void setApprover(String approver) {
		this.approver = approver;
	}
	public boolean getCritical() {
		return critical;
	}
	public void setCritical(boolean critical) {
		this.critical = critical;
	}
	public Timestamp getTimestamp_created() {
		return timestamp_created;
	}
	public void setTimestamp_created(Timestamp timestamp_created) {
		this.timestamp_created = timestamp_created;
	}
	public Timestamp getTimestamp_updated() {
		return timestamp_updated;
	}
	public void setTimestamp_updated(Timestamp timestamp_updated) {
		this.timestamp_updated = timestamp_updated;
	}
	
}
