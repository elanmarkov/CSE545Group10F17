package com.group10.dao.employee;

import java.util.HashMap;
import java.util.Random;


import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.group10.dbmodels.User;

public class UserRegistrationDaoImpl extends JdbcDaoSupport  
{
	public boolean isUnique(String username, String phone, String email, String table) {
		// TODO Auto-generated method stub
		String sql = "select count(*) from "+table+ " where name='"+username+"' and phone='"+phone+"' and email='"+email+"'";
		int count = this.getJdbcTemplate().queryForObject(sql, Integer.class);
		if(count>0)	
			return false;
		return true;
	} 
	
	public void setInternalUser(String name, String role, String address, String city, String state, String country, String pincode, String phone, String email, String dob, String ssn){
		String sql = "insert into users (name, role, address, city, state, country, zipcode, phone, email) values(?,?,?,?,?,?,?,?,?)";
		this.getJdbcTemplate().update(sql, new Object[]{name, role, address, city, state, country, pincode, phone, email});

		int userId = this.getJdbcTemplate().queryForObject("select id from users where email='"+email+"'", Integer.class);
		String sql2 = "insert into pii_info (userId, dob, ssn) values(?,?,?)";
		this.getJdbcTemplate().update(sql2, new Object[]{userId, dob, ssn});

	}
	
	public void setLoginDetails(String username, String password, String role, String email){
		int userId = this.getJdbcTemplate().queryForObject("select id from users where email='"+email+"'", Integer.class);
		
		String sql = "insert into user_login(username, password, enabled, role, accountNonExpired, accountNonLocked, credentialsNonExpired, otpNonLocked)  values(?,?,?,?,?,?,?,?)";
		this.getJdbcTemplate().update(sql, new Object[]{username, password, 1, role, 1,1,1,1});
	}
	
	public void setLoginAttempts(String email, int attempts){
		int userId = this.getJdbcTemplate().queryForObject("select id from users where email='"+email+"'", Integer.class);
		
		String sql = "insert into user_login_attempts(username, attempts)  values(?,?)";
		this.getJdbcTemplate().update(sql, new Object[]{email, attempts});	
	}
	
	public void setExternalUser(String name,String role, String address, String city, String state, String country, String zipcode, String phone, String email, String dob, String ssn, String username){
		String sql = "insert into users (name, role, address, city, state, country, zipcode, phone, email) values(?,?,?,?,?,?,?,?,?)";
		this.getJdbcTemplate().update(sql, new Object[]{name, role, address, city, state, country, zipcode, phone, email});

		int userId = this.getJdbcTemplate().queryForObject("select id from users where email='"+email+"'", Integer.class);
		String sql2 = "insert into pii_info (userId, dob, ssn) values(?,?,?)";
		this.getJdbcTemplate().update(sql2, new Object[]{userId, dob,ssn});

	}
	
	public void updatePassword(String username,  String newPassword){
		String sql = "update user_login set password = '" + newPassword + "' where username='"+ username+"'";
		this.getJdbcTemplate().update(sql);

		String sql2 = "UPDATE user_login SET enabled = 1 WHERE username = '"+username+"'";
		this.getJdbcTemplate().update(sql);
	}
	
}