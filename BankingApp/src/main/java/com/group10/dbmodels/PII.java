package com.group10.dbmodels;

public class PII {
	private int id;
	private String external_users_id;
	private String date_of_birth;
	private String ssn_number;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getExternal_users_id() {
		return external_users_id;
	}
	public void setExternal_users_id(String external_users_id) {
		this.external_users_id = external_users_id;
	}
	public String getDate_of_birth() {
		return date_of_birth;
	}
	public void setDate_of_birth(String date_of_birth) {
		this.date_of_birth = date_of_birth;
	}
	public String getSsn_number() {
		return ssn_number;
	}
	public void setSsn_number(String ssn_number) {
		this.ssn_number = ssn_number;
	}

}
