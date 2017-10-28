package com.group10.dao.customerDashboard;

import java.util.List;

import com.group10.dbmodels.CheckingAccount;
import com.group10.dbmodels.CreditCard;
import com.group10.dbmodels.SavingsAccount;

public interface CustomerDashboardDao {

	public CreditCard ccAccountDetails(int userId); 
	public SavingsAccount savingsAccountDetails(int userId);
	public CheckingAccount checkingAccountDetails(int userId);
}
