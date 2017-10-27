/*
 * Author: Kevin Every
 */
package com.group10.dao.transaction;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.group10.dbmodels.CheckingAccount;
import com.group10.dbmodels.CompletedExternalRequest;
import com.group10.dbmodels.CompletedTransaction;
import com.group10.dbmodels.PendingExternalRequests;
import com.group10.dbmodels.PendingTransaction;

public class ExternalRequestsDao  extends JdbcDaoSupport{

	public List<PendingExternalRequests> getPendingRequests(int userID) {
		String sql = "SELECT * FROM pending_external_requests WHERE payerID = " + userID;
		return this.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<PendingExternalRequests>(PendingExternalRequests.class));
	}
	
	public void reviewRequest(String status, int requestID) {
		String sql = "SELECT * FROM pending_external_requests WHERE id = " + requestID;
		PendingExternalRequests extReq = (PendingExternalRequests)this.getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper(PendingExternalRequests.class));
		
		String deleteSQL = "DELETE FROM pending_external_requests WHERE id ="+requestID;
		this.getJdbcTemplate().update(deleteSQL);
		
		// Add completed transaction to database
		CompletedExternalRequest compReq = new CompletedExternalRequest(extReq, status); // Make a completedTransaction object
		String updateSQL = "INSERT INTO completed_external_requests (amount,stamp,completedStamp,toAccountID,fromAccountID,description,payerID,initiatorID,status) values (?,?,NOW(),?,?,?,?,?,?)";
		this.getJdbcTemplate().update(updateSQL, new Object[]{compReq.getAmount(), compReq.getStamp(),
				compReq.getToAccountID(), compReq.getFromAccountID(), compReq.getDescription(), compReq.getPayerID(), 
				compReq.getInitiatorID(), compReq.getStatus()});
		
		// If approved, create a pending transaction
		if (status.equals("approve")) {
			PendingTransaction trans = new PendingTransaction(compReq);
			updateSQL = "INSERT INTO pending_transactions (amount,initiatorID,stamp,toAccountID,description,fromAccountID) values (?,?,NOW(),?,?,?)";
			this.getJdbcTemplate().update(updateSQL, new Object[]{ trans.getAmount(), trans.getInitiatorID(), trans.getToAccountID(), 
					trans.getDescription(), trans.getFromAccountID()});
		}
		
	}
	
	public void createPendingRequest(String fromAccountID, String toAccountID, double amount, int initiatorID) {
		String sql = "SELECT userId FROM checking_accounts WHERE accountNumber="+fromAccountID+"\n"+ 
					"UNION SELECT userId FROM savings_accounts WHERE accountNumber="+fromAccountID;
		
		int payerID = this.getJdbcTemplate().queryForObject(sql, Integer.class);
		
		PendingExternalRequests req = new PendingExternalRequests(amount, toAccountID, fromAccountID, "Description", payerID, initiatorID);
		
		String updateSQL = "INSERT INTO pending_external_requests (amount, stamp, toAccountID, fromAccountID, description, payerID, initiatorID) values (?,NOW(),?,?,?,?,?)";
		this.getJdbcTemplate().update(updateSQL, new Object[] {req.getAmount(), req.getToAccountID(), req.getFromAccountID(), req.getDescription(),
				req.getPayerID(), req.getInitiatorID()});
	}
}
