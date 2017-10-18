package com.group10.dao.customerDashboard;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.group10.dbmodels.CreditCard;
import com.group10.dbmodels.Transaction;

public class CustomerDashboardDaoImpl extends JdbcDaoSupport {

	public List<Transaction> transactions(int userId, String type) {
		String query = "SELECT * FROM transaction where user_id= " + userId + "and transaction_type="+type;
		return this.getJdbcTemplate().query(query, new BeanPropertyRowMapper<Transaction>(Transaction.class));
	}
	
	public int savingsAccountDetails(int userId){
		String query = "select * from savings_account where user_id=" + userId;
		return this.getJdbcTemplate().queryForObject(query, Integer.class);	
	}

	public int checkingAccountDetails(int userId){
		String query = "select * from checking_account where user_id=" + userId;
		return this.getJdbcTemplate().queryForObject(query, Integer.class);			
	}

	public CreditCard ccAccountDetails(int userId){
		String query = "select * from creditcard_account where user_id=" + userId;
		return this.getJdbcTemplate().queryForObject(query, CreditCard.class);	
	}
}
