/*
 * Author: Kevin Everly
 */
package com.group10.dao.customer;

import java.util.Random;

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

	public CreditAccount getCreditAccount(int userID) {
		String sql = "SELECT * FROM credit_accounts WHERE userID = " + userID;
		try {
			return  (CreditAccount)this.getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper(CreditAccount.class));
		} catch (Exception e) {
			return null;
		}
	}

	// By Harsha
		public boolean createSavingsAccount(int userId) {
			String query = "select count(*) from savings_accounts where userId="+userId;
			int exists = this.getJdbcTemplate().queryForObject(query, Integer.class);
			if(exists==1){
				Random rand = new java.util.Random();
				String accNo= String.valueOf((int) (10000000*rand.nextFloat()));
				String sql = "insert into savings_accounts (userId, accountNumber, balance) values (?,?,?)";
				this.getJdbcTemplate().update(sql, new Object[]{userId, accNo, 0}); 
				return true;
			}	
			return false;
		}

		public boolean createCheckingAccount(int userId) {
			String query = "select count(*) from checking_accounts where userId="+userId;
			int exists = this.getJdbcTemplate().queryForObject(query, Integer.class);
			if(exists==1){
				Random rand = new java.util.Random();
				String accNo= String.valueOf((int) (10000000*rand.nextFloat()));
				String sql = "insert into checking_accounts (userId, accountNumber, balance) values (?,?,?)";
				this.getJdbcTemplate().update(sql, new Object[]{userId, accNo, 0}); 
				return true;
			}	
			return false;
		}

		public boolean createCreditAccount(int userId) {

			String query = "select count(*) from credit_accounts where userId="+userId;
			int exists = this.getJdbcTemplate().queryForObject(query, Integer.class);
			if(exists==1){
				Random rand = new java.util.Random();
				String accNo= String.valueOf((int) (10000000*rand.nextFloat()));
				String sql = "insert into credit_accounts (userId, accountNumber, currentAmountDue) values (?,?,?)";
				this.getJdbcTemplate().update(sql, new Object[]{userId, accNo, 0});
				return true;
			}
			return false;
		}
}
