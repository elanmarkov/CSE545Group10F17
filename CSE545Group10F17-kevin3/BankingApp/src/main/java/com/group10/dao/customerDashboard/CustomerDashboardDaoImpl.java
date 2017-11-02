package com.group10.dao.customerDashboard;

import java.util.List;

import com.group10.dbmodels.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class CustomerDashboardDaoImpl extends JdbcDaoSupport {

	public List<PendingTransaction> pendingTransactions(String accountNumber) {
		String query = "SELECT * FROM pending_transactions where toAccountID="+accountNumber + " OR fromAccountID="+accountNumber;
		try {
			return this.getJdbcTemplate().query(query, new BeanPropertyRowMapper<PendingTransaction>(PendingTransaction.class));
		} catch (Exception e) {
			return null;
		}
	}
	
	public List<CompletedTransaction> completedTransactions(String accountNumber) {
		String query = "SELECT * FROM completed_transactions where toAccountID="+accountNumber + " OR fromAccountID="+accountNumber;
		try {
			return this.getJdbcTemplate().query(query, new BeanPropertyRowMapper<CompletedTransaction>(CompletedTransaction.class));
		} catch (Exception e) {
			return null;
		}
	}
	
	public SavingsAccount savingsAccountDetails(int userId){
		String query = "select * from savings_accounts where userId=" + userId;
		try {
			return (SavingsAccount) this.getJdbcTemplate().queryForObject(query, new BeanPropertyRowMapper(SavingsAccount.class));
		} catch (Exception e) {
			return null;
		}
	}

	public CheckingAccount checkingAccountDetails(int userId){
		String query = "select * from checking_accounts where userId=" + userId;
		try {
			return (CheckingAccount) this.getJdbcTemplate().queryForObject(query, new BeanPropertyRowMapper(CheckingAccount.class));
		} catch (Exception e) {
			return null;
		}
	}
	
	

	public CreditAccount creditAccount(int userID) {
		String sql = "SELECT * FROM credit_accounts WHERE userId = " + userID;
		try {
			return  (CreditAccount)this.getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper(CreditAccount.class));
		} catch (Exception e) {
			return null;
		}
	}
	

	public User getUserById(int userId) {
		String sql = "SELECT * FROM users WHERE id="+userId;
		return (User) this.getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper(User.class));
	}
}
