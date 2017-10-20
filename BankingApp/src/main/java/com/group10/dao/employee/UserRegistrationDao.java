package com.group10.dao.employee;

public interface UserRegistrationDao {
	public Boolean isUnique(String userid, String phone, String email, String table);
	
}
