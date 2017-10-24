package com.group10.dao.employee;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.group10.dbmodels.PendingInternalRequests;
import com.group10.dbmodels.UserDetails;

public class EmpFunctionsDaoImpl extends JdbcDaoSupport{
	
	public List<PendingInternalRequests> getAdminPendingRequests(){
		String sql = "select * from pending_internal_requests";
		return this.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<PendingInternalRequests>(PendingInternalRequests.class));
	}
	
	public boolean approveAdminRequest(String address,String city,String state, String zipcode, String country, String phone, int userId){
		String sql = "Update internal_users set address="+address+",state="+state+",city="+city+",zipcode="+zipcode+",country="+country+",phone="+phone+" where userId="+userId;
		String sql2 = "delete * from pending_internal_requests where userid="+userId;
		this.getJdbcTemplate().update(sql2);
		return this.getJdbcTemplate().update(sql)==1?true:false;
	}

	public boolean createSavingsAccount(String userName) {
		// TODO Auto-generated method stub
		return true;
		
	}

	public boolean createCheckingAccount(String userName) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean createCreditAccount(String userName) {
		
		// TODO Auto-generated method stub
		return false;
	}

	public boolean validateUserLogin(String username, String password, String role) {
		// TODO Auto-generated method stub
	//	String sql = "select count(*) from user_login where username="+username+",password="+password+",role="+role;
	//	int res = this.getJdbcTemplate().queryForObject(sql,Integer.class);
		//return res==1?true:false;
		return true;
	}
	
	

}
