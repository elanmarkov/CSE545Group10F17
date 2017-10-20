package com.group10.dao.transaction;

import java.math.BigDecimal;
import java.util.List;

import com.group10.dbmodels.Transaction;

public interface TransactionDao {
	public List<Transaction> getById(int id, String table);
	public boolean save(Transaction transaction);
	public void update(Transaction employer);
	public boolean deleteById(int id, String type);
	public Transaction createExternalTransaction(int payerAccountNumber, BigDecimal amount, int payeeAccountNumber,
			String description, String transactionType);

}
