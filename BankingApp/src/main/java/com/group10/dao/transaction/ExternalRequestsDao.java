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
import com.group10.dbmodels.PendingExternalRequest;
import com.group10.dbmodels.PendingTransaction;

public class ExternalRequestsDao  extends JdbcDaoSupport{

	public List<PendingExternalRequest> getPendingRequests(int userID) {
		String sql = "SELECT * FROM pending_external_requests WHERE receiverID = " + userID;
		return this.getJdbcTemplate().query(sql, new BeanPropertyRowMapper<PendingExternalRequest>(PendingExternalRequest.class));
	}
	
	public void reviewRequest(String status, int requestID) {
		String sql = "SELECT * FROM pending_external_requests WHERE id = " + requestID;
		PendingExternalRequest extReq = (PendingExternalRequest)this.getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper(PendingExternalRequest.class));
		
		String deleteSQL = "DELETE FROM pending_external_requests WHERE id ="+requestID;
		this.getJdbcTemplate().update(deleteSQL);
		
		// Add completed transaction to database
		CompletedExternalRequest compReq = new CompletedExternalRequest(extReq, status); // Make a completedTransaction object
		String updateSQL = "INSERT INTO completed_external_requests (amount,stamp,completedStamp,toAccountID,fromAccountID,description,receiverID,initiatorID,status) values (?,?,NOW(),?,?,?,?,?,?)";
		this.getJdbcTemplate().update(updateSQL, new Object[]{compReq.getAmount(), compReq.getStamp(),
				compReq.getToAccountID(), compReq.getFromAccountID(), compReq.getDescription(), compReq.getReceiverID(), 
				compReq.getInitiatorID(), compReq.getStatus()});
		
		// If approved, create a pending transaction
		if (status.equals("approve")) {
			PendingTransaction trans = new PendingTransaction(compReq);
			updateSQL = "INSERT INTO pending_transactions (amount,initiatorID,stamp,toAccountID,description,fromAccountID) values (?,?,NOW(),?,?,?)";
			this.getJdbcTemplate().update(updateSQL, new Object[]{ trans.getAmount(), trans.getInitiatorID(), trans.getToAccountID(), 
					trans.getDescription(), trans.getFromAccountID()});
		}
		
	}
}
