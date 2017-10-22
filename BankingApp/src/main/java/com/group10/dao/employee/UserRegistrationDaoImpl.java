package com.group10.dao.employee;

import java.util.HashMap;
import java.util.Random;


import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.group10.dbmodels.InternalUser;
import com.group10.dbmodels.UserDetails;

public class UserRegistrationDaoImpl extends JdbcDaoSupport  implements UserRegistrationDao
{
	public Boolean isUnique(String userid, String phone, String email, String table) {
		// TODO Auto-generated method stub
		String sql = "select count(*) from "+table+ "where userid="+userid+", phone="+phone+", email="+email;
		int count = this.getJdbcTemplate().queryForObject(sql, Integer.class);
		if(count>0)	
			return false;
		return true;
	} 
	
	public void setInternalUser(String name, String designation, String address, String city, String state, String country, String pincode, String phone, String email, String dob, String ssn, String username){
		String sql = "insert into internal_users (name, designation, address, city, state, country, pincode, phone, email, dob, ssn, username) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		this.getJdbcTemplate().update(sql, new Object[]{name, designation, address, city, state, country, pincode, phone, email, dob, ssn, username});
	}
	
	public void setUserDetails(String username, String password, String role){
		UserDetails userDetails = new UserDetails();
		userDetails.setPassword(password);
		userDetails.setUsername(username);
		userDetails.setRole(role);
		String sql = "insert into user_details(username, password, role)  values(?,?,?)";
		this.getJdbcTemplate().update(sql, new Object[]{userDetails.getUsername(), userDetails.getPassword(), userDetails.getRole()});	
	}
	
	public void setExternalUser(String name, String address, String city, String state, String country, String pincode, String phone, String email, String dob, String ssn, String username){
		String sql = "insert into internal_users (name, address, city, state, country, pincode, phone, email, dob, ssn, username) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		this.getJdbcTemplate().update(sql, new Object[]{name, address, city, state, country, pincode, phone, email, dob, ssn, username});
	}
	
	public void updatePassword(String username,  String newPassword){
		String sql = "update users set password = " + newPassword + "where username="+ username;
		this.getJdbcTemplate().update(sql);
	}
	
}
