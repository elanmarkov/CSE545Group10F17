package com.group10.dao.transaction;

import java.math.BigDecimal;
import java.util.List;

import com.group10.dbmodels.Transaction;

public class TransactionDaoImpl implements TransactionDao{

	public List<Transaction> getById(int id, String table) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean save(Transaction transaction) {
		// TODO Auto-generated method stub
		return false;
	}

	public void update(Transaction employer) {
		// TODO Auto-generated method stub
		
	}

	public boolean deleteById(int id, String type) {
		// TODO Auto-generated method stub
		return false;
	}

	public Transaction createExternalTransaction(int payerAccountNumber, BigDecimal amount, int payeeAccountNumber,
			String description, String transactionType) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
