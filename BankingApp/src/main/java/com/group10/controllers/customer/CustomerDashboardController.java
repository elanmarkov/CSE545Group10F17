package com.group10.controllers.customer;
import com.group10.dbmodels.*;

import java.util.ArrayList;
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

@Controller
public class CustomerDashboardController {
	
	@RequestMapping("/customer/dashboard")
	public ModelAndView display(){

 		ApplicationContext  ctx = new ClassPathXmlApplicationContext("DaoDetails.xml"); 
	    CustomerDashboardDaoImpl sdao = ctx.getBean("customerDashboardDaoImpl" , CustomerDashboardDaoImpl.class);
	    
	    int userId = 1;
	    SavingsAccount savings = sdao.savingsAccountDetails(userId);
	    CheckingAccount checking = sdao.checkingAccountDetails(userId);
	    //CreditAccount credit = sdao.creditAccountDetails(userId);
	    
	    List<PendingTransaction> pendingSavings = sdao.pendingTransactions(savings.getAccountNumber());
	    List<CompletedTransaction> completedSavings = sdao.completedTransactions(savings.getAccountNumber());
	    List<PendingTransaction> pendingChecking = sdao.pendingTransactions(checking.getAccountNumber());
	    List<CompletedTransaction> completedChecking = sdao.completedTransactions(checking.getAccountNumber());
	    //List<PendingTransaction> pendingCredit = sdao.pendingTransactions(credit.getAccountNumber());	   
	    
	    ModelAndView model = new ModelAndView("/customer/CustomerDashboard");
	    model.addObject("pendingSavings", pendingSavings);
	    model.addObject("completedSavings", completedSavings);
	    model.addObject("pendingChecking", pendingChecking);
	    model.addObject("completedChecking", completedChecking);
	    
		return model;		

	}
	
}
