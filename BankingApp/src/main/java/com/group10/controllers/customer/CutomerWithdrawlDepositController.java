/*
 * Author: Kevin Everly
 */
package com.group10.controllers.customer;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.group10.dao.customer.CustomerAccountsDao;
import com.group10.dao.transaction.ExternalTransactionDaoImpl;
import com.group10.dbmodels.*;

@Controller
public class CutomerWithdrawlDepositController {

	@RequestMapping("/customer/deposit")
	public  ModelAndView loadDespositPage(){
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		CustomerAccountsDao accountsDao = ctx.getBean("customerAccountDao",CustomerAccountsDao.class);
		CheckingAccount checking = accountsDao.getCheckingAccount(1); // TODO: MAKE THIS THE CURRENT USERID FROM SESSION
		SavingsAccount savings = accountsDao.getSavingsAccount(1); // TODO: MAKE THIS THE CURRENT USERID FROM SESSION
		
		ModelAndView model = new ModelAndView("/Customers/Transaction_Deposit_Customer");
		model.addObject("checking", checking);
		model.addObject("savings", savings);
		return model;
	}
	
	@RequestMapping("/depositMoney")
	public ModelAndView despositMoney(HttpServletRequest request, @RequestParam("depositAccount") String accountType, 
			@RequestParam("amount") double amount, @RequestParam("accountNumber") int accountNumber, RedirectAttributes redir) {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		ExternalTransactionDaoImpl extDao = ctx.getBean("externalTransactionDaoImpl",ExternalTransactionDaoImpl.class);
		
		//TODO MAKE DYNAMIC
		if (accountType.equals("savings")) {
			extDao.createPendingTransaction(1, amount, accountNumber, null, "Deposit to savings");
		} else if (accountType.equals("checking")){
			extDao.createPendingTransaction(1, amount, accountNumber, null, "Deposit to checking");
		}
		
		return new ModelAndView("redirect:/customer/deposit");
	}
	
	@RequestMapping("/customer/withdraw")
	public  ModelAndView loadWithdrawPage(){

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		CustomerAccountsDao accountsDao = ctx.getBean("customerAccountDao",CustomerAccountsDao.class);
		CheckingAccount checking = accountsDao.getCheckingAccount(1); // TODO: MAKE THIS THE CURRENT USERID FROM SESSION
		SavingsAccount savings = accountsDao.getSavingsAccount(1); // TODO: MAKE THIS THE CURRENT USERID FROM SESSION
		
		ModelAndView model = new ModelAndView("/Customers/Transaction_Withdraw_Customer");
		model.addObject("checking", checking);
		model.addObject("savings", savings);
		return model;
	}
	
	@RequestMapping("/withdrawMoney")
	public ModelAndView withdrawMoney(HttpServletRequest request, @RequestParam("withdrawAccount") String accountType, 
			@RequestParam("amount") double amount, @RequestParam("accountNumber") int accountNumber, RedirectAttributes redir) {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		ExternalTransactionDaoImpl extDao = ctx.getBean("externalTransactionDaoImpl",ExternalTransactionDaoImpl.class);
		
		//TODO MAKE DYNAMIC
		if (accountType.equals("savings")) {
			extDao.createPendingTransaction(1, amount, null, accountNumber, "Withdraw from savings");
		} else if (accountType.equals("checking")){
			extDao.createPendingTransaction(1, amount, null, accountNumber, "Withdraw from checking");
		}
		
		return new ModelAndView("redirect:/customer/withdraw");
	}
}
