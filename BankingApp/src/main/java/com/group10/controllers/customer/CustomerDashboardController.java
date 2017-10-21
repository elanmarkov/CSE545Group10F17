package com.group10.controllers.customer;
import com.group10.dbmodels.*;
import java.util.List;

import com.group10.dbmodels.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.group10.dao.customerDashboard.CustomerDashboardDaoImpl;
import com.group10.dbmodels.CheckingAccount;
import com.group10.dbmodels.CreditCard;
import com.group10.dbmodels.SavingsAccount;
import com.group10.dbmodels.Transaction;

@Controller
public class CustomerDashboardController {
	
	@RequestMapping("/customer/dashboard")
	public ModelAndView display(){
		SavingsAccount savingsAccount = new SavingsAccount();
		CheckingAccount checkingAccount = new CheckingAccount();
		CreditCard creditAccount = new CreditCard();
		int userId = 123456;
 		ApplicationContext  ctx = new ClassPathXmlApplicationContext("DaoDetails.xml"); 
	    CustomerDashboardDaoImpl sdao = ctx.getBean("customerDashboardDaoImpl" , CustomerDashboardDaoImpl.class);
	    savingsAccount = sdao.savingsAccountDetails(userId);
	    checkingAccount = sdao.checkingAccountDetails(userId);
	    creditAccount = sdao.ccAccountDetails(userId);
	    List<Transaction> transactionsSavings = sdao.transactions(userId,"savings");
	    List<Transaction> transactionsChekings = sdao.transactions(userId,"checkings");
	    List<Transaction> transactionsCredit = sdao.transactions(userId,"credit");	   
	    
	    ModelAndView model = new ModelAndView("dashboard");
	    model.addObject("savingsAccount", savingsAccount);
	    model.addObject("checkingAccount", checkingAccount);
	    model.addObject("creditCard", creditAccount);
	    model.addObject("transactionsSavings", transactionsSavings);
	    model.addObject("transactionsChekings", transactionsChekings);
	    model.addObject("transactionsCredit", transactionsCredit);
		return model;		

	}
	
}
