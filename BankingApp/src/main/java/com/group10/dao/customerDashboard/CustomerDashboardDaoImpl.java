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
import com.group10.dbmodels.Transaction;

public class CustomerDashboardDaoImpl extends JdbcDaoSupport {

	public List<PendingTransaction> pendingTransactions(String accountNumber) {
		String query = "SELECT * FROM pending_transactions where toAccountID="+accountNumber + " OR fromAccountID="+accountNumber;
		return this.getJdbcTemplate().query(query, new BeanPropertyRowMapper<PendingTransaction>(PendingTransaction.class));
	}
	
	public List<CompletedTransaction> completedTransactions(String accountNumber) {
		String query = "SELECT * FROM completed_transactions where toAccountID="+accountNumber + " OR fromAccountID="+accountNumber;
		return this.getJdbcTemplate().query(query, new BeanPropertyRowMapper<CompletedTransaction>(CompletedTransaction.class));
	}
	
	public SavingsAccount savingsAccountDetails(int userId){
		String query = "select * from savings_accounts where userId=" + userId;
		return (SavingsAccount)this.getJdbcTemplate().queryForObject(query, new BeanPropertyRowMapper(SavingsAccount.class));	
	}

	public CheckingAccount checkingAccountDetails(int userId){
		String query = "select * from checking_accounts where userId=" + userId;
		return (CheckingAccount)this.getJdbcTemplate().queryForObject(query, new BeanPropertyRowMapper(CheckingAccount.class));			
	}

	public CreditAccount creditAccountDetails(int userId){
		String query = "select * from credit_accounts where userId=" + userId;
		return (CreditAccount)this.getJdbcTemplate().queryForObject(query, new BeanPropertyRowMapper(CreditAccount.class));		
	}
}
