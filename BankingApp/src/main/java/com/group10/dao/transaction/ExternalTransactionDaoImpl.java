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

import com.group10.dbmodels.Transaction;
import com.group10.dbmodels.PendingTransaction;
import com.group10.dbmodels.CompletedTransaction;

public class ExternalTransactionDaoImpl extends JdbcDaoSupport  {

	// Author: Harsha
	public Transaction createExternalTransaction(int payer_id, double amount, int payeeId,
			String description, String transaction_type) {
		// TODO Auto-generated method stub
			Transaction extTransaction = new Transaction();
			extTransaction.setPayee_id(payer_id);
			extTransaction.setPayer_id(payer_id);
			extTransaction.setAmount(amount);
			extTransaction.setDescription(description);
			extTransaction.setTransaction_type(transaction_type);
			extTransaction.setStatus("pending");
			extTransaction.setApprover("");
			if(amount > 5000.0)
				extTransaction.setCritical(true);
			else
				extTransaction.setCritical(false);
			Timestamp ts = new Timestamp(new Date().getTime());
			extTransaction.setTimestamp_created(ts);
			extTransaction.setTimestamp_updated(ts);
			extTransaction.setHashvalue("");
			
			String sql = "INSERT INTO transaction (payer_id,payee_id,amount,hashvalue,transaction_type,description,status,approver,critical,timestamp_created,timestamp_updated) values (?,?,?,?,?,?,?,?,?,NOW(),NOW())";
			this.getJdbcTemplate().update(sql, new Object[]{extTransaction.getPayer_id(), extTransaction.getPayee_id(), extTransaction.getAmount(), extTransaction.getHashvalue(),
					extTransaction.getTransaction_type(), extTransaction.getDescription(), extTransaction.getStatus(), extTransaction.getApprover(), extTransaction.getCritical(),
					extTransaction.getTimestamp_created(), extTransaction.getTimestamp_updated()});		
			
			return extTransaction;
	}
	
	/*
	 * Author: Kevin Everly
	 */
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
			
			//TODO: IF NOT ENOUGH FUNDS, DECLINE TRANSACTION AND CHANGE DESCRIPTION TO "INSUFFICIENT FUNDS".
			// IF SUFFICIANT, ACTUALY MOVE FUNDS BETWEEN ACCOUNTS
			
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
		
		if ((pendTrans.getAmount() > 5000) && (role.compareTo("tier2") != 0)) {
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
	
}
