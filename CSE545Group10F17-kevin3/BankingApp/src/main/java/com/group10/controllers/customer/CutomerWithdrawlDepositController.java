/*
 * Author: Kevin Everly
 */
package com.group10.controllers.customer;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.group10.controllers.security.HandlerClass;
import com.group10.dao.customer.CustomerAccountsDao;
import com.group10.dao.transaction.ExternalTransactionDaoImpl;
import com.group10.dbmodels.*;

@Controller
public class CutomerWithdrawlDepositController {

	private int userId;
	
	@ExceptionHandler(HandlerClass.class)
	public String handleResourceNotFoundException() {
		return "redirect:/exception";
	}
	
	@RequestMapping("/customer/deposit")
	public  ModelAndView loadDespositPage(HttpServletRequest request){
		try{
		userId = (Integer) request.getSession().getAttribute("userID");

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		CustomerAccountsDao accountsDao = ctx.getBean("customerAccountDao",CustomerAccountsDao.class);
		CheckingAccount checking = accountsDao.getCheckingAccount(userId); // TODO: MAKE THIS THE CURRENT USERID FROM SESSION
		SavingsAccount savings = accountsDao.getSavingsAccount(userId); // TODO: MAKE THIS THE CURRENT USERID FROM SESSION
		CreditAccount credit = accountsDao.getCreditAccount(userId);

		ModelAndView model = new ModelAndView("/customer/Transaction_Deposit_Customer");
		model.addObject("checking", checking);
		model.addObject("savings", savings);
		model.addObject("credit", credit);
		return model;
		}catch(Exception e){
			throw new HandlerClass();
		}
	}
	
	@RequestMapping("/depositMoney")
	public ModelAndView despositMoney(HttpServletRequest request, @RequestParam("accountNumber") String accountNumber,
			@RequestParam("amount") double amount, RedirectAttributes redir) {
		try{
		userId = (Integer) request.getSession().getAttribute("userID");

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		ExternalTransactionDaoImpl extDao = ctx.getBean("externalTransactionDaoImpl",ExternalTransactionDaoImpl.class);

		// TODO: CHANGE TO USER ID FROM SESSION
		if (extDao.checkAccountNumberValidity(accountNumber, userId)) {
			extDao.createPendingTransaction(userId, amount, accountNumber, null, "Deposit");
			return new ModelAndView("redirect:/customer/deposit");
		} else {
			return new ModelAndView("redirect:/customer/deposit");
			//redir.addFlashAttribute("error_message",accountNumber+" Not Valid");
		}
		}catch(Exception e){
			throw new HandlerClass();
		}

	}
	
	@RequestMapping("/customer/withdraw")
	public  ModelAndView loadWithdrawPage(HttpServletRequest request){
		try{
		userId = (Integer) request.getSession().getAttribute("userID");

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		CustomerAccountsDao accountsDao = ctx.getBean("customerAccountDao",CustomerAccountsDao.class);
		CheckingAccount checking = accountsDao.getCheckingAccount(userId); // TODO: MAKE THIS THE CURRENT USERID FROM SESSION
		SavingsAccount savings = accountsDao.getSavingsAccount(userId); // TODO: MAKE THIS THE CURRENT USERID FROM SESSION
		
		ModelAndView model = new ModelAndView("/customer/Transaction_Withdraw_Customer");
		model.addObject("checking", checking);
		model.addObject("savings", savings);
		return model;
		}catch(Exception e){
			throw new HandlerClass();
		}
	}
	
	@RequestMapping("/withdrawMoney")
	public ModelAndView withdrawMoney(HttpServletRequest request, @RequestParam("amount") double amount,
									  @RequestParam("accountNumber") String accountNumber, RedirectAttributes redir) {
		try{
		userId = (Integer) request.getSession().getAttribute("userID");

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		ExternalTransactionDaoImpl extDao = ctx.getBean("externalTransactionDaoImpl",ExternalTransactionDaoImpl.class);
		
		if (extDao.checkAccountNumberValidity(accountNumber, userId)) {
			extDao.createPendingTransaction(userId, amount, null, accountNumber, "Withdraw");
			return new ModelAndView("redirect:/customer/withdraw");
		} else {
			return new ModelAndView("redirect:/customer/withdraw");
			//redir.addFlashAttribute("error_message",accountNumber+" Not Valid");
		}	
		}catch(Exception e){
			throw new HandlerClass();
		}
	}
}
