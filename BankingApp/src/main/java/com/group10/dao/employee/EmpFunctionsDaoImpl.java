package com.group10.dao.employee;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.group10.dbmodels.DbLogs;
import com.group10.dbmodels.User;
import com.group10.dbmodels.PII;
import com.group10.dbmodels.PendingAccountChangeRequests;
import com.group10.dbmodels.PendingExternalRequests;
import com.group10.dbmodels.PendingInternalRequests;

public class EmpFunctionsDaoImpl extends JdbcDaoSupport{
	
	public List<PendingInternalRequests> getAdminPendingRequests(){
		String sql = "select * from pending_internal_requests";
		return this.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<PendingInternalRequests>(PendingInternalRequests.class));
	}
	
	public void modify(String address,String city,String state, String country, String zipcode, String phone, int userId){
		String sql = "Update users set address='"+address+"',state='"+state+"',city='"+city+"',zipcode='"+zipcode+"',country='"+country+"',phone='"+phone+"' where id="+userId;
		this.getJdbcTemplate().update(sql);
	}

	
	public boolean validateUserLogin(String username, String password, String role) {
		// TODO Auto-generated method stub
		String sql = "select count(*) from user_login where username='"+username+"' and password='"+password+"'and role='"+role+"'";
		int res = this.getJdbcTemplate().queryForObject(sql,Integer.class);
		return res==1?true:false;
		//return true;
	}

	
	public User getUser(int id) {
		// TODO Auto-generated method stub
		String sql = "select * from users where id="+id;
		return (User)this.getJdbcTemplate().queryForObject(sql,new BeanPropertyRowMapper(User.class));
	
	}

	public boolean existUser(int employeeID) {
		// TODO Auto-generated method stub
		String sql = "select count(*) from users where id="+employeeID;
		return this.getJdbcTemplate().queryForObject(sql, Integer.class)==1?true:false;	
	}

	public void deleteInternalUser(String employeeId) {
		// TODO Auto-generated method stub
		String sql = "delete from users where id="+employeeId;
		this.getJdbcTemplate().update(sql);		
	}

	public PII getUserPII(int userID) {
		// TODO Auto-generated method stub
		String sql = "select * from pii_info where userId="+userID;
		return (PII)this.getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper(PII.class));
	}

	public void approveAdminRequest(int requestId) {
		String sql = "select * from pending_internal_requests where id="+requestId;
		PendingInternalRequests req = (PendingInternalRequests)this.getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper(PendingInternalRequests.class));
		String sql2 = "Update users set address='"+req.getAddress()+"',state='"+req.getState()+"',city='"+req.getCity()+"',zipcode='"+req.getZipcode()+"',country='"+req.getCountry()+"',phone='"+req.getPhone()+"' where id="+req.getUserId();
		this.getJdbcTemplate().update(sql2);
		deletePendingRequest(requestId);
	}

	public void deletePendingRequest(int requestId) {
		// TODO Auto-generated method stub
		String sql = "delete from pending_internal_requests where id="+requestId;
		this.getJdbcTemplate().update(sql);
		
	}

	public int getUserIdByName(String email) {
		// TODO Auto-generated method stub
		String sql = "select id from users where email='"+email+"'";
		return this.getJdbcTemplate().queryForObject(sql, Integer.class);
	}

	public User getInternalUser(int employeeID) {
		// TODO Auto-generated method stub
		String sql = "select * from users where id="+employeeID+" and role in ('manager','regular','admin')";
		return (User)this.getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper(User.class));
	}
	
	public User getExternalUser(int customerId) {
		// TODO Auto-generated method stub
		String sql = "select * from users where id="+customerId+" and role in ('customer','merchant')";
		return (User)this.getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper(User.class));
	}

	public void deleteExternalUser(int customerId) {
		String sql = "delete from users where id="+customerId+" and role in ('customer','merchant')";
		this.getJdbcTemplate().update(sql);	
	}
	
	public void deleteInternalUser(int employeeID) {
		String sql = "delete from users where id="+employeeID+" and role in ('admin','manager','regular')";
		this.getJdbcTemplate().update(sql);	
	}

	public boolean existExternalUser(int customerID) {
		// TODO Auto-generated method stub
				String sql = "select count(*) from users where id="+customerID+ " and role in ('customer','merchant')";
				return this.getJdbcTemplate().queryForObject(sql, Integer.class)==1?true:false;	
	}
	public boolean existInternalUser(int employeeID) {
		// TODO Auto-generated method stub
				String sql = "select count(*) from users where id="+employeeID+" and role in ('admin','manager','regular')";
				return this.getJdbcTemplate().queryForObject(sql, Integer.class)==1?true:false;	
	}

	public boolean existTier1User(int employeeID) {
		// TODO Auto-generated method stub
		String sql = "select count(*) from users where id="+employeeID+" and role='regular";
		return this.getJdbcTemplate().queryForObject(sql, Integer.class)==1?true:false;		
		}

	public User getTier1User(int employeeID) {
		// TODO Auto-generated method stub
		String sql = "select * from users where id="+employeeID+" and role ='regular'";
		return (User)this.getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper(User.class));

	}

	public List<PendingAccountChangeRequests> getExternalPendingRequests() {
		String sql = "select * from pending_ac_requests";
		return this.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<PendingAccountChangeRequests>(PendingAccountChangeRequests.class));	}

	public void approveTier2Request(int requestId) {
		String sql = "select * from pending_ac_requests where id="+requestId;
		PendingAccountChangeRequests req = (PendingAccountChangeRequests)this.getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper(PendingAccountChangeRequests.class));
		String sql2 = "Update users set address='"+req.getAddress()+"',state='"+req.getState()+"',city='"+req.getCity()+"',zipcode='"+req.getZipcode()+"',country='"+req.getCountry()+"',phone='"+req.getPhone()+"' where id="+req.getUserId();
		this.getJdbcTemplate().update(sql2);
		deleteTier2Request(requestId);
		
	}

	public void deleteTier2Request(int requestId) {
		String sql = "delete from pending_ac_requests where id="+requestId;
		this.getJdbcTemplate().update(sql);		
	}

	public void generateInternalRequest(String address, String city, String state, String zipcode, String country,
			String phone, int userId) {
		String sql = "insert into pending_internal_requests (userId,address,city,state,country,zipcode,phone) values ("+
				userId + ",'"+address+"','"+city+"','"+state +"','"+country+"','"+zipcode+"','"+phone+"')";
		this.getJdbcTemplate().update(sql);		

		
	}

	public boolean existInteralUser(int employeeID) {
		// TODO Auto-generated method stub
		String sql = "select count(*) from users where id="+employeeID+" and role in ('ROLE_MANAGER','ROLE_REGULAR')";
		return this.getJdbcTemplate().queryForObject(sql, Integer.class)==1?true:false;	
	}
	

}