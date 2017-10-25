package com.group10.dao.employee;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.group10.dbmodels.DbLogs;
import com.group10.dbmodels.InternalUser;
import com.group10.dbmodels.PII;
import com.group10.dbmodels.PendingInternalRequests;
import com.group10.dbmodels.UserDetails;

public class EmpFunctionsDaoImpl extends JdbcDaoSupport{
	
	public List<PendingInternalRequests> getAdminPendingRequests(){
		String sql = "select * from pending_internal_requests";
		return this.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<PendingInternalRequests>(PendingInternalRequests.class));
	}
	
	public boolean adminModify(String address,String city,String state, String zipcode, String country, String phone, int userId){
		String sql = "Update internal_users set address='"+address+"',state='"+state+"',city='"+city+"',zipcode='"+zipcode+"',country='"+country+"',phone='"+phone+"' where userId='"+userId+"'";
		String sql2 = "delete * from pending_internal_requests where userid='"+userId+"'";
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
		String sql = "select count(*) from user_login where username='"+username+"' and password='"+password+"'and role='"+role+"'";
		int res = this.getJdbcTemplate().queryForObject(sql,Integer.class);
		return res==1?true:false;
		//return true;
	}

	public List<DbLogs> getLogs() {
		String query = "SELECT * FROM internal_log, external_log ORDER BY timestamp DESC LIMIT 25";
		return this.getJdbcTemplate().query(query, new BeanPropertyRowMapper<DbLogs>(DbLogs.class));
	}

	public InternalUser getInternalUser(String name) {
		// TODO Auto-generated method stub
		String sql = "select * from internal_users where name='"+name+"'";
		return this.getJdbcTemplate().queryForObject(sql, InternalUser.class);
	
	}

	public boolean existInternalUser(String employeeName) {
		// TODO Auto-generated method stub
		String sql = "select count(*) from internal_users where name='"+employeeName+"'";
		return this.getJdbcTemplate().queryForObject(sql, Integer.class)==1?true:false;	
	}

	public void deleteInternalUser(String employeeId) {
		// TODO Auto-generated method stub
		String sql = "delete * from internal_users where id='"+employeeId+"'";
		this.getJdbcTemplate().update(sql);		
	}

	public PII getUserPII(String userID) {
		// TODO Auto-generated method stub
		String sql = "select * from pii_info where userid='"+userID+"'";
		return (PII)this.getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper(PII.class));
	}

	public void approveAdminRequest(int requestId) {
		String sql = "select * from pending_internal_requests where requestId='"+requestId+"'";
		PendingInternalRequests req = (PendingInternalRequests)this.getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper(PendingInternalRequests.class));
		String sql2 = "Update internal_users set address='"+req.getAddress()+"',state='"+req.getState()+"',city='"+req.getCity()+"',zipcode='"+req.getZipcode()+"',country='"+req.getCountry()+"',phone='"+req.getPhone()+"' where userId='"+req.getUserId()+"'";
		this.getJdbcTemplate().update(sql2);
		deletePendingRequest(requestId);
	}

	public void deletePendingRequest(int requestId) {
		// TODO Auto-generated method stub
		String sql = "delete * from pending_internal_requests where requestId='"+requestId+"'";
		this.getJdbcTemplate().update(sql);
		
	}

	public int getUserIdByName(String username) {
		// TODO Auto-generated method stub
		String sql = "select userid from user_login where username='"+username+"'";
		return this.getJdbcTemplate().queryForObject(sql, Integer.class);
	}
	
	

}
