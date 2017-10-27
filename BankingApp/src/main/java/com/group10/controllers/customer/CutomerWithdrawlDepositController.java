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
		
		ModelAndView model = new ModelAndView("/customer/Transaction_Deposit_Customer");
		model.addObject("checking", checking);
		model.addObject("savings", savings);
		return model;
	}
	
	@RequestMapping("/depositMoney")
	public ModelAndView despositMoney(HttpServletRequest request, @RequestParam("depositAccount") String accountType, 
			@RequestParam("amount") double amount, @RequestParam("accountNumber") String accountNumber, RedirectAttributes redir) {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		ExternalTransactionDaoImpl extDao = ctx.getBean("externalTransactionDaoImpl",ExternalTransactionDaoImpl.class);
		
		// TODO: CHANGE TO USER ID FROM SESSION
		if (extDao.checkAccountNumberValidity(accountNumber, 1)) {
			extDao.createPendingTransaction(1, amount, accountNumber, null, "DESCRIPTION");
			return new ModelAndView("redirect:/customer/deposit");
		} else {
			return new ModelAndView("redirect:/customer/deposit");
			//redir.addFlashAttribute("error_message",accountNumber+" Not Valid");
		}

	}
	
	@RequestMapping("/customer/withdraw")
	public  ModelAndView loadWithdrawPage(){

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		CustomerAccountsDao accountsDao = ctx.getBean("customerAccountDao",CustomerAccountsDao.class);
		CheckingAccount checking = accountsDao.getCheckingAccount(1); // TODO: MAKE THIS THE CURRENT USERID FROM SESSION
		SavingsAccount savings = accountsDao.getSavingsAccount(1); // TODO: MAKE THIS THE CURRENT USERID FROM SESSION
		
		ModelAndView model = new ModelAndView("/customer/Transaction_Withdraw_Customer");
		model.addObject("checking", checking);
		model.addObject("savings", savings);
		return model;
	}
	
	@RequestMapping("/withdrawMoney")
	public ModelAndView withdrawMoney(HttpServletRequest request, @RequestParam("withdrawAccount") String accountType, 
			@RequestParam("amount") double amount, @RequestParam("accountNumber") String accountNumber, RedirectAttributes redir) {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		ExternalTransactionDaoImpl extDao = ctx.getBean("externalTransactionDaoImpl",ExternalTransactionDaoImpl.class);
		
		// TODO: CHANGE TO USER ID FROM SESSION
		if (extDao.checkAccountNumberValidity(accountNumber, 1)) {
			extDao.createPendingTransaction(1, amount, accountNumber, null, "DESCRIPTION");
			return new ModelAndView("redirect:/customer/deposit");
		} else {
			return new ModelAndView("redirect:/customer/deposit");
			//redir.addFlashAttribute("error_message",accountNumber+" Not Valid");
		}		
	}
}
