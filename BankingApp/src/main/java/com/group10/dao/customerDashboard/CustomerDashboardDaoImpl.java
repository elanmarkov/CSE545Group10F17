package com.group10.dao.customerDashboard;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.group10.dbmodels.CheckingAccount;
import com.group10.dbmodels.CompletedTransaction;
import com.group10.dbmodels.CreditAccount;
import com.group10.dbmodels.CreditCard;
import com.group10.dbmodels.PendingTransaction;
import com.group10.dbmodels.SavingsAccount;

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

	public CreditAccount creditAccountDetails(int userId){
		String query = "select * from credit_accounts where userId=" + userId;
		try {
			return (CreditAccount) this.getJdbcTemplate().queryForObject(query, new BeanPropertyRowMapper(CreditAccount.class));
		} catch (Exception e) {
			return null;
		}
	}
}
