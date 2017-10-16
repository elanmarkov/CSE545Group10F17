package com.group10.dao.transaction;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.group10.dbmodels.Transaction;

public class ExternalTransactionDaoImpl extends JdbcDaoSupport  {


	public Transaction createExternalTransaction(int payer_id, BigDecimal amount, int payeeId,
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
			if(amount.compareTo(new BigDecimal("5000"))>1)
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
	
}
