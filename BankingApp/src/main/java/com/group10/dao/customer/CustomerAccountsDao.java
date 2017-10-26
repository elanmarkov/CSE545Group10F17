/*
 * Author: Kevin Everly
 */
package com.group10.dao.customer;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.group10.dbmodels.*;

public class CustomerAccountsDao extends JdbcDaoSupport {
	
	public CheckingAccount getCheckingAccount(int userID) {
		String sql = "SELECT * FROM checking_accounts WHERE userID = " + userID;
		try {
			return (CheckingAccount)this.getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper(CheckingAccount.class));
		} catch (Exception e) {
			return null;
		}
	}
	
	public SavingsAccount getSavingsAccount(int userID) {
		String sql = "SELECT * FROM savings_accounts WHERE userID = " + userID;
		try {
			return  (SavingsAccount)this.getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper(SavingsAccount.class));
		} catch (Exception e) {
			return null;
		}
	}
}
