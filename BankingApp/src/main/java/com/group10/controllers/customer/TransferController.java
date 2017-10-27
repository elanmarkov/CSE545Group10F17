package com.group10.controllers.customer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.group10.dao.customer.CustomerAccountsDao;
import com.group10.dao.transaction.ExternalTransactionDaoImpl;
import com.group10.dbmodels.CheckingAccount;
import com.group10.dbmodels.PendingTransaction;
import com.group10.dbmodels.SavingsAccount;

@Controller
public class TransferController {
	
	@RequestMapping("/customer/transferBetweenAccounts")
	public ModelAndView loadTransferBetweenAccountsPage() {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		CustomerAccountsDao accountsDao = ctx.getBean("customerAccountDao",CustomerAccountsDao.class);
		CheckingAccount checking = accountsDao.getCheckingAccount(1); // TODO: MAKE THIS THE CURRENT USERID FROM SESSION
		SavingsAccount savings = accountsDao.getSavingsAccount(1); // TODO: MAKE THIS THE CURRENT USERID FROM SESSION
		
		ModelAndView model = new ModelAndView("/customer/FundsTransfer_between_accounts_Customer");
		model.addObject("checking", checking);
		model.addObject("savings", savings);
		return model;
	}
	
	@RequestMapping("/transferfundsBetweenAccounts")
	public ModelAndView submitTransferBetweenAccounts(HttpServletRequest request, @RequestParam("transferTo") String transferTo,
			@RequestParam("transferFrom") String transferFrom, @RequestParam("transferAmount") double amount, RedirectAttributes redir) {
		
		int userId = 1;
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		ExternalTransactionDaoImpl extDao = ctx.getBean("externalTransactionDaoImpl",ExternalTransactionDaoImpl.class);
		
		if (extDao.checkAccountNumberValidity(transferTo, userId) && extDao.checkAccountNumberValidity(transferFrom, userId)) {
			extDao.createPendingTransaction(userId, amount, transferTo, transferFrom, "Money Transfer");
			return new ModelAndView("redirect:/customer/dashboard");
		} else {
			return new ModelAndView("redirect:/customer/dashboard");
		}
	}
	
	@RequestMapping("/transferfundsToOthersAccounts")
	public ModelAndView submitTransferToOthers(HttpServletRequest request, @RequestParam("transferMode") String transferMode, @RequestParam("payeeInfo") String payeeInfo,
			@RequestParam("transferFrom") String transferFrom, @RequestParam("transferAmount") double amount, RedirectAttributes redir) {
		
		int userId = 1;
				
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		ExternalTransactionDaoImpl extDao = ctx.getBean("externalTransactionDaoImpl",ExternalTransactionDaoImpl.class);
		
		if (extDao.checkAccountNumberValidity(transferFrom, userId)) {
			PendingTransaction resultTrans = extDao.createPendingTransactionFromTransfer(userId, amount, transferMode, payeeInfo, transferFrom);
			if (resultTrans == null) {
				System.out.println("payeeInfo was invalid"); //TODO: ADD FLASH MESSAGE
			}
			return new ModelAndView("redirect:/customer/dashboard");
			
		} else {
			return new ModelAndView("redirect:/customer/dashboard");
		}
	}
	
	@RequestMapping("/customer/transferToOthers")
	public ModelAndView loadTransferToOthersPage() {
		
		int userId = 1;
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		CustomerAccountsDao accountsDao = ctx.getBean("customerAccountDao",CustomerAccountsDao.class);
		CheckingAccount checking = accountsDao.getCheckingAccount(userId); // TODO: MAKE THIS THE CURRENT USERID FROM SESSION
		SavingsAccount savings = accountsDao.getSavingsAccount(userId); // TODO: MAKE THIS THE CURRENT USERID FROM SESSION
		
		ModelAndView model = new ModelAndView("/customer/FundsTransfer_send_to_others_Customer");
		model.addObject("checking", checking);
		model.addObject("savings", savings);
		return model;
	}
}
