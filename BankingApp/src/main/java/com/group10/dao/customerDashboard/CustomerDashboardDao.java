package com.group10.dao.customerDashboard;

import java.util.List;

import com.group10.dbmodels.CheckingAccount;
import com.group10.dbmodels.CreditCard;
import com.group10.dbmodels.SavingsAccount;
import com.group10.dbmodels.Transaction;

public interface CustomerDashboardDao {

	public List<Transaction> transactions(int userId, String type); 
	public CreditCard ccAccountDetails(int userId); 
	public SavingsAccount savingsAccountDetails(int userId);
	public CheckingAccount checkingAccountDetails(int userId);
}
