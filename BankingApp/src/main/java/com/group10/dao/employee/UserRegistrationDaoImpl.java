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
		String sql = "select count(*) from "+table+ " where userid='"+userid+"' and phone='"+phone+"' and email='"+email+"'";
		int count = this.getJdbcTemplate().queryForObject(sql, Integer.class);
		if(count>0)	
			return false;
		return true;
	} 
	
	public void setInternalUser(String name, String designation, String address, String city, String state, String country, String pincode, String phone, String email, String dob, String ssn, String username){
		String sql = "insert into internal_users (name, designation, address, city, state, country, zipcode, phone, email) values(?,?,?,?,?,?,?,?,?)";
		this.getJdbcTemplate().update(sql, new Object[]{name, designation, address, city, state, country, pincode, phone, email});

		int userId = this.getJdbcTemplate().queryForObject("select userId from internal_users where email='"+email+"'", Integer.class);
		String sql2 = "insert into pii_info (userid, dob, ssn) values(?,?,?)";
		this.getJdbcTemplate().update(sql2, new Object[]{userId, dob,ssn});

	}
	
	public void setLoginDetails(String username, String password, String role, String email){
		int userId = this.getJdbcTemplate().queryForObject("select userId from internal_users, external_users where email='"+email+"'", Integer.class);
		
		String sql = "insert into user_login(username, password, role, accountStatus, otpExpireStatus, attempts,lastModified, userId)  values(?,?,?,?,?,?,NOW(),?)";
		this.getJdbcTemplate().update(sql, new Object[]{username, password, role, 1,0,0, userId});	
	}
	
	public void setExternalUser(String name,String designation, String address, String city, String state, String country, String pincode, String phone, String email, String dob, String ssn, String username){
		String sql = "insert into external_users (name, designation, address, city, state, country, zipcode, phone, email) values(?,?,?,?,?,?,?,?,?)";
		this.getJdbcTemplate().update(sql, new Object[]{name, designation, address, city, state, country, pincode, phone, email});

		int userId = this.getJdbcTemplate().queryForObject("select userId from internal_users where email='"+email+"'", Integer.class);
		String sql2 = "insert into pii_info (userId, dob, ssn) values(?,?,?)";
		this.getJdbcTemplate().update(sql2, new Object[]{userId, dob,ssn});
	}
	
	public void updatePassword(String username,  String newPassword){
		String sql = "update user_login set password = " + newPassword + "where username="+ username;
		this.getJdbcTemplate().update(sql);
	}
	
}