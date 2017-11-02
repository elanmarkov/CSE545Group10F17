/*
 * Author: Kevin Everly
 */
package com.group10.dao.customer;

import java.util.Calendar;
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
		String sql = "SELECT * FROM credit_accounts WHERE userId = " + userID;
		try {
			return  (CreditAccount)this.getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper(CreditAccount.class));
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public CreditCard getCreditCard(int userID) {
		String sql = "SELECT * FROM credit_cards WHERE userId = " + userID;
		try {
			return  (CreditCard)this.getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper(CreditCard.class));
		} catch (Exception e) {
			return null;
		}
	}

	// By Harsha
		public boolean createSavingsAccount(int userId) {
			String query = "select count(*) from savings_accounts where userId="+userId;
			int exists = this.getJdbcTemplate().queryForObject(query, Integer.class);
			if(exists == 0){
				Random rand = new java.util.Random();
				String accNo= String.valueOf((int) (10000000*rand.nextFloat()));
				String sql = "insert into savings_accounts (userId, accountNumber, balance) values (?,?,?)";
				this.getJdbcTemplate().update(sql, new Object[]{userId, accNo, 0});

				// Add to accNumToTableRel
				sql = "INSERT INTO accNumToTableRel (accountNumber, `table`, userId) values (?,?,?)";
				this.getJdbcTemplate().update(sql, new Object[]{accNo, "savings_accounts", userId});

				return true;
			}	
			return false;
		}

		public boolean createCheckingAccount(int userId) {
			String query = "select count(*) from checking_accounts where userId="+userId;
			int exists = this.getJdbcTemplate().queryForObject(query, Integer.class);
			if(exists == 0){
				Random rand = new java.util.Random();
				String accNo= String.valueOf((int) (10000000*rand.nextFloat()));
				String sql = "insert into checking_accounts (userId, accountNumber, balance) values (?,?,?)";
				this.getJdbcTemplate().update(sql, new Object[]{userId, accNo, 0});

				// Add to accNumToTableRel
				sql = "INSERT INTO accNumToTableRel (accountNumber, `table`, userId) values (?,?,?)";
				this.getJdbcTemplate().update(sql, new Object[]{accNo, "checking_accounts", userId});

				return true;
			}	
			return false;
		}

		public boolean createCreditAccount(int userId) {

			String query = "select count(*) from credit_accounts where userId="+userId;
			int exists = this.getJdbcTemplate().queryForObject(query, Integer.class);
			if(exists == 0){
				Random rand = new java.util.Random();
				String accNo= String.valueOf((int) (10000000*rand.nextFloat()));
				String sql = "insert into credit_accounts (userId, accountNumber, balance) values (?,?,?)";
				this.getJdbcTemplate().update(sql, new Object[]{userId, accNo, 1000});
			
				// Add to accNumToTableRel
				sql = "INSERT INTO accNumToTableRel (accountNumber, `table`, userId) values (?,?,?)";
				this.getJdbcTemplate().update(sql, new Object[]{accNo, "credit_accounts", userId});

				String cvv= String.valueOf((int) (1000*rand.nextFloat()));
				
				String query2 = "insert into credit_cards (creditCardNumber,userId, cvv, creditLimit, currentAmountDue, cycleDate, dueDate, lastBillAmount, apr) values (?,?,?,?,?,NOW(),NOW(),?,?)";
				this.getJdbcTemplate().update(query2, new Object[]{accNo, userId, cvv, 1000,0,0,15});
				
				
				return true;
			}
			return false;
		}
		
		public String getCheckingAccNumber(int userId){
			return this.getJdbcTemplate().queryForObject("select accountNumber from checking_accounts where userId="+userId, String.class);
		}
		
		public String getCreditAccNumber(int userId){
			return this.getJdbcTemplate().queryForObject("select creditCardNumber from credit_cards where userId="+userId, String.class);
		}

		public Integer getUserIdByEmail(String email) {
			 String sql = "SELECT id FROM users WHERE email = '"+email+"'";
			 try {
			 	Integer userId = this.getJdbcTemplate().queryForObject(sql, Integer.class);
			 	return userId;
			 } catch (Exception e) {
			 	return null;
			 }
		}


}
