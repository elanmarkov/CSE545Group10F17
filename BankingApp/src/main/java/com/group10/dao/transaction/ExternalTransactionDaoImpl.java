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
	
	public PendingTransaction createPendingTransaction(int initiatorID, double amount, int toAccountID, int fromAccountID, String description) {
		
		PendingTransaction trans = new PendingTransaction(initiatorID, amount, toAccountID, fromAccountID, description);
		
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
		
		if ((pendTrans.getAmount() > 5000) && (role.compareTo("tier2") != 0)) {
			//TODO: ERROR. UNAUTHORIZED ATTEMPT TO APPROVE CRITICAL TRANSACTION
		} else {
			// DELETE PENDING TRANSACTION
			String deleteSQL = "DELETE FROM pending_transactions WHERE id ="+transactionID;
			this.getJdbcTemplate().update(deleteSQL);
			
			//TODO: IF NOT ENOUGH FUNDS, DECLINE TRANSACTION AND CHANGE DESCRIPTION TO "INSUFFICIENT FUNDS".
			// IF SUFFICIANT, ACTUALY MOVE FUNDS BETWEEN ACCOUNTS
			
			// Add completed transaction to database
			CompletedTransaction compTrans = new CompletedTransaction(pendTrans, reviewerID, "Approved"); // Make a completedTransaction object
			String updateSQL = "INSERT INTO completed_transactions (amount,initiatorID,stamp,toAccountID,description,fromAccountID,reviewerID,status) values (?,?,NOW(),?,?,?,?,?)";
			this.getJdbcTemplate().update(updateSQL, new Object[]{compTrans.getAmount(), compTrans.getInitiatorID(), compTrans.getToAccountID(), 
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
