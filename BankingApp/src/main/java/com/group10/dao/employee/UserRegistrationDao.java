package com.group10.dao.employee;

public interface UserRegistrationDao {
	public Boolean isUnique(String userid, int phone, String email, String table);
	
}
