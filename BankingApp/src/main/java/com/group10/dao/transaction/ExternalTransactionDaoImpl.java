/*
 * Author: Kevin Everly
 */
package com.group10.dao.transaction;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.group10.dbmodels.PendingTransaction;
import com.group10.dbmodels.CompletedTransaction;

public class ExternalTransactionDaoImpl extends JdbcDaoSupport  {


	public PendingTransaction createPendingTransaction(int initiatorID, double amount, String toAccountID, String fromAccountID, String description) {
		
		PendingTransaction trans = new PendingTransaction(initiatorID, amount, toAccountID, fromAccountID, description);
		
		String updateSQL = "INSERT INTO pending_transactions (amount,initiatorID,stamp,toAccountID,description,fromAccountID) values (?,?,NOW(),?,?,?)";
		this.getJdbcTemplate().update(updateSQL, new Object[]{ trans.getAmount(), trans.getInitiatorID(), trans.getToAccountID(), 
				trans.getDescription(), trans.getFromAccountID()});
		return trans;
	}
	
	public PendingTransaction createPendingTransactionFromTransfer(int initiatorID, double amount, String transferMode, String payeeInfo, String fromAccountID ) {
		
		List<String> toAccountIDs = new ArrayList<String>();
		if (transferMode.equals("payeeEmail")) {
			try {
				String sql = "SELECT id FROM users WHERE email = '" + payeeInfo + "'";
				Integer toUserId = this.getJdbcTemplate().queryForObject(sql, Integer.class);
				
				sql = "SELECT accountNumber FROM checking_accounts\n" + 
						"WHERE userId = " + toUserId + "\n" +
						"UNION SELECT accountNumber FROM savings_accounts\n" + 
						"WHERE userId = " + toUserId;
				 toAccountIDs = this.getJdbcTemplate().queryForList(sql, String.class);
				 
			} catch (Exception e) {
				System.out.println("Email does not exist");
				return null;
			}
			
		} else if (transferMode.equals("payeePhone")) {
			try {
				String sql = "SELECT * FROM users WHERE phone = " + payeeInfo;
				Integer toUserId = this.getJdbcTemplate().queryForObject(sql, Integer.class);
				
				sql = "SELECT accountNumber FROM checking_accounts\n" + 
						"WHERE userId = " + toUserId + "\n" +
						"UNION SELECT accountNumber FROM savings_accounts\n" + 
						"WHERE userId = " + toUserId;
				 toAccountIDs = this.getJdbcTemplate().queryForList(sql, String.class);
				 
			} catch (Exception e) {
				System.out.println("Phone does not exist");
				return null;
			}
		} else if (transferMode.equals("payeeAccountNumber")) {
			String sql = "SELECT count(*) FROM checking_accounts, savings_accounts \n" + 
					"WHERE checking_accounts.userId ="+payeeInfo+" OR savings_accounts.userId ="+payeeInfo;
			Integer count = this.getJdbcTemplate().queryForObject(sql, Integer.class);
			if (count > 0) {
				toAccountIDs.add(payeeInfo);
			} else {
				System.out.println("ACCOUNT DOES NOT EXIST");
				return null;
			}
		}
		
		//Yeah. We are hardcoding getting the first account. Do something about it. 
		PendingTransaction trans = new PendingTransaction(initiatorID, amount, toAccountIDs.get(0), fromAccountID, "Money Transfer");
		
		String updateSQL = "INSERT INTO pending_transactions (amount,initiatorID,stamp,toAccountID,description,fromAccountID) values (?,?,NOW(),?,?,?)";
		this.getJdbcTemplate().update(updateSQL, new Object[]{ trans.getAmount(), trans.getInitiatorID(), trans.getToAccountID(), 
				trans.getDescription(), trans.getFromAccountID()});
		return trans;
		
	}
	
	public List<PendingTransaction> getPendingNonCriticalTransactions() {
		
		String sql = "SELECT * FROM pending_transactions WHERE amount < 5000 LIMIT 10";
		return this.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<PendingTransaction>(PendingTransaction.class));
		
	}
	
	public List<PendingTransaction> getAllPendingTransactions() {
		
		String sql = "SELECT * FROM pending_transactions LIMIT 10";
		return this.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<PendingTransaction>(PendingTransaction.class));
		
	}
	
	public void approveTransaction(String transactionID, int reviewerID, String role) {

		// Get the pending transaction that has now been reviewed
		String sql = "SELECT * FROM pending_transactions WHERE id="+transactionID; 
		PendingTransaction pendTrans = (PendingTransaction)this.getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper(PendingTransaction.class));
		
		if ((pendTrans.getAmount() > 5000) && !(role.equals("tier2"))) {
			//TODO: ERROR. UNAUTHORIZED ATTEMPT TO APPROVE CRITICAL TRANSACTION
		} else if(role.equals("tier1") || role.equals("tier2")){
			// DELETE PENDING TRANSACTION
			String deleteSQL = "DELETE FROM pending_transactions WHERE id ="+transactionID;
			this.getJdbcTemplate().update(deleteSQL);
			
			//True for withdraws and two ended transactions
			if (pendTrans.getFromAccountID() != null) {
				
				//Get table that the FROM account is located in
				String tableSQL = "SELECT `table` FROM accNumToTableRel WHERE accountNumber = "+ pendTrans.getFromAccountID();
				String table = this.getJdbcTemplate().queryForObject(tableSQL, String.class);
				
				//Get the balance of that account
				String balanceSQL = "SELECT balance FROM "+table+" WHERE accountNumber = "+pendTrans.getFromAccountID();
				double balance = this.getJdbcTemplate().queryForObject(balanceSQL, Double.class);
				
				//Subtract differnce (balance - amount)
				balance = balance - pendTrans.getAmount();
				
				//Update table with new balance
				String updateSQL = "UPDATE "+table+" SET balance = "+balance+" WHERE accountNumber="+pendTrans.getFromAccountID();
				this.getJdbcTemplate().update(updateSQL);
			
			} 
			
			//True for deposits and two ended transactions
			if (pendTrans.getToAccountID() != null) { 
				//Get table that the FROM account is located in
				String tableSQL = "SELECT `table` FROM accNumToTableRel WHERE accountNumber = "+ pendTrans.getToAccountID();
				String table = this.getJdbcTemplate().queryForObject(tableSQL, String.class);
				
				//Get the balance of that account
				String balanceSQL = "SELECT balance FROM "+table+" WHERE accountNumber = "+pendTrans.getToAccountID();
				double balance = this.getJdbcTemplate().queryForObject(balanceSQL, Double.class);
				
				//Subtract differnce (balance - amount)
				balance = balance + pendTrans.getAmount();
				
				//Update table with new balance
				String updateSQL = "UPDATE "+table+" SET balance = "+balance+" WHERE accountNumber="+pendTrans.getToAccountID();
				this.getJdbcTemplate().update(updateSQL);
			}
			
			// Add completed transaction to database
			CompletedTransaction compTrans = new CompletedTransaction(pendTrans, reviewerID, "Approved"); // Make a completedTransaction object
			String updateSQL = "INSERT INTO completed_transactions (amount,initiatorID,stamp,completedStamp,toAccountID,description,fromAccountID,reviewerID,status) values (?,?,?,NOW(),?,?,?,?,?)";
			this.getJdbcTemplate().update(updateSQL, new Object[]{compTrans.getAmount(), compTrans.getInitiatorID(), compTrans.getStamp(), compTrans.getToAccountID(), 
					compTrans.getDescription(), compTrans.getFromAccountID(), compTrans.getReviewerID(), compTrans.getStatus()});
						
		}
	}
	
	public void declineTransaction(String transactionID, int reviewerID, String role) {
		
		// Get the pending transaction that has now been reviewed
		String sql = "SELECT * FROM pending_transactions WHERE id="+transactionID; 
		PendingTransaction pendTrans = (PendingTransaction)this.getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper(PendingTransaction.class));
		
		if ((pendTrans.getAmount() > 5000) && !(role.equals("tier2"))) {
			//TODO: ERROR. UNAUTHORIZED ATTEMPT TO APPROVE CRITICAL TRANSACTION
		} else {
			// DELETE PENDING TRANSACTION
			String deleteSQL = "DELETE FROM pending_transactions WHERE id ="+transactionID;
			this.getJdbcTemplate().update(deleteSQL);
			
			// Add completed transaction to database
			CompletedTransaction compTrans = new CompletedTransaction(pendTrans, reviewerID, "Rejected"); // Make a completedTransaction object
			String updateSQL = "INSERT INTO completed_transactions (amount,initiatorID,stamp,completedStamp,toAccountID,description,fromAccountID,reviewerID,status) values (?,?,?,NOW(),?,?,?,?,?)";
			this.getJdbcTemplate().update(updateSQL, new Object[]{compTrans.getAmount(), compTrans.getInitiatorID(), compTrans.getStamp(), compTrans.getToAccountID(), 
					compTrans.getDescription(), compTrans.getFromAccountID(), compTrans.getReviewerID(), compTrans.getStatus()});
		}
	}
	
	public boolean checkAccountNumberValidity(String accountNumber, int userId) {
		
		String sql  = "SELECT count(*) FROM accNumToTableRel WHERE (userId = "+userId+" AND accountNumber = "+accountNumber+")";
		
		Integer count = this.getJdbcTemplate().queryForObject(sql, Integer.class);
		
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean checkPendingTransactionIDValidity(String transactionID, int userId) {
		
		String sql = "SELECT count(*) FROM pending_transactions WHERE (userId = "+userId+" AND transactionID = "+transactionID+")";
		
		Integer count = this.getJdbcTemplate().queryForObject(sql, Integer.class);
		
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}
}
