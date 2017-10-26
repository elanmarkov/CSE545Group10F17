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
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		ExternalTransactionDaoImpl extDao = ctx.getBean("externalTransactionDaoImpl",ExternalTransactionDaoImpl.class);
		
		extDao.createPendingTransaction(1, amount, transferTo, transferFrom, "Money Transfer");
		ModelAndView model = new ModelAndView("redirect:/customer/dashboard");
		return model;
	}
	
	@RequestMapping("/transferfundsToOthersAccounts")
	public ModelAndView submitTransferToOthers(HttpServletRequest request, @RequestParam("transferMode") String transferMode, @RequestParam("payeeInfo") String payeeInfo,
			@RequestParam("transferFrom") String transferFrom, @RequestParam("transferAmount") double amount, RedirectAttributes redir) {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		ExternalTransactionDaoImpl extDao = ctx.getBean("externalTransactionDaoImpl",ExternalTransactionDaoImpl.class);
		
		extDao.createPendingTransactionFromTransfer(1, amount, transferMode, payeeInfo, transferFrom);
		ModelAndView model = new ModelAndView("redirect:/customer/dashboard");
		return model;
	}
	@RequestMapping("/customer/transferToOthers")
	public ModelAndView loadTransferToOthersPage() {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		CustomerAccountsDao accountsDao = ctx.getBean("customerAccountDao",CustomerAccountsDao.class);
		CheckingAccount checking = accountsDao.getCheckingAccount(1); // TODO: MAKE THIS THE CURRENT USERID FROM SESSION
		SavingsAccount savings = accountsDao.getSavingsAccount(1); // TODO: MAKE THIS THE CURRENT USERID FROM SESSION
		
		ModelAndView model = new ModelAndView("/customer/FundsTransfer_send_to_others_Customer");
		model.addObject("checking", checking);
		model.addObject("savings", savings);
		return model;
	}
}
