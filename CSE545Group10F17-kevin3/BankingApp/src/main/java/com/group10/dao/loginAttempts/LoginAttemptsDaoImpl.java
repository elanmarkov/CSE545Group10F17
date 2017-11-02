package com.group10.dao.loginAttempts;
/* 
 * Author : Harsha vardhan
 */


import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.authentication.LockedException;

import com.group10.dbmodels.DbLogs;
import com.group10.dbmodels.LoginAuthentication;

public class LoginAttemptsDaoImpl extends JdbcDaoSupport {
	
	
	
	
	public void updateFailAttempts(String username) {
		int attempts = this.getJdbcTemplate().queryForObject("select attempts from user_login_attempts where username='"+username+"'", Integer.class)+1;
		String sql = "update user_login_attempts set attempts="+attempts+ " where username='"+username+"'";
		this.getJdbcTemplate().update(sql);	
		if(attempts>3){
			this.getJdbcTemplate().update("update user_login set accountNonLocked = 0 WHERE username ='"+username+"'");
		}
	}

	public LoginAuthentication getUserAttempts(String username) {
		String sql = "select * from user_login_attempts where username='"+username+"'";
		//return this.getJdbcTemplate().queryForObject(sql, Integer.class);
		return (LoginAuthentication)this.getJdbcTemplate().queryForObject(sql,new BeanPropertyRowMapper(LoginAuthentication.class));
	}

	public void resetFailAttempts(String username) {
		String sql = "update user_login_attempts set attempts=0 where username='"+username+"'";
		this.getJdbcTemplate().update(sql);
	}

	public boolean isUserExists(String username) {
		String sql = "select count(*) from user_login where username='"+username+"'";
		return this.getJdbcTemplate().queryForObject(sql,Integer.class)==1?true:false;
	}

	public void lockUserAccount(String username) {
		// TODO Auto-generated method stub
		String sql = "update user_login set accountNonLocked=0 where username='"+username+"'";
		this.getJdbcTemplate().update(sql);
	}
	
	public void unLockUserAccount(String username){
		String sql = "update user_login set accountNonLocked=1 where username='"+username+"'";
		this.getJdbcTemplate().update(sql);
	}

	public int getNoUserAttempts(String username) {
		String sql = "select attempts from user_login_attempts where username='"+username+"'";
		//return this.getJdbcTemplate().queryForObject(sql, Integer.class);
		return this.getJdbcTemplate().queryForObject(sql,Integer.class);
	}
	
	
	
}