package com.group10.controllers.employee;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.group10.dao.transaction.ExternalTransactionDaoImpl;
import com.group10.dbmodels.PendingTransaction;

@Controller
public class Tier1PendingTransactions {
	
	@RequestMapping("/employee/Tier1TransactionManagement")
	public  ModelAndView loadPendingReqPage(){
		ModelAndView model = new ModelAndView("/employee/Tier1TransactionManagement");
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		ExternalTransactionDaoImpl extDao = ctx.getBean("externalTransactionDaoImpl",ExternalTransactionDaoImpl.class);
		List<PendingTransaction> trans = extDao.getPendingNonCriticalTransactions();
		model.addObject("transaction_list", trans);
		return model;
	}
	
	@RequestMapping(value = "/tier1/transactionReview", method = RequestMethod.POST) 
	public ModelAndView reviewedTransaction(HttpServletRequest request, @RequestParam("transactionID") String transId, @RequestParam("requestDecision") String requestDecision, RedirectAttributes redir) {
		
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
			ExternalTransactionDaoImpl extDao = ctx.getBean("externalTransactionDaoImpl",ExternalTransactionDaoImpl.class);
			
			if (requestDecision.equals("approve")) {
				//TODO: PASS REVIEWER ID FROM SESSION
				extDao.approveTransaction(transId, 1, "tier1");
			} else if (requestDecision.equals("reject")) {
				//TODO: PASS REVIEWER ID FROM SESSION
				extDao.declineTransaction(transId, 1, "tier1");
			}
			
			ModelAndView model = new ModelAndView("redirect:/employee/Tier1TransactionManagement");
			return model;
	}
	
	@RequestMapping(value = "/tier1/transactionNew", method = RequestMethod.POST)
	public ModelAndView newTransaction(HttpServletRequest request, @RequestParam("senderAccountNumber") int fromAccountID, @RequestParam("receiverAccountNumber") int toAccountID, @RequestParam("amountToAdd") double amount) {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		ExternalTransactionDaoImpl extDao = ctx.getBean("externalTransactionDaoImpl",ExternalTransactionDaoImpl.class);
		
		//Tier 2 can't create critical transactions
		if (amount < 5000) {
			extDao.createPendingTransaction(1, amount, toAccountID, fromAccountID, "HARDCODED DESCRIPTION");
		}
		
		ModelAndView model = new ModelAndView("redirect:/employee/Tier1TransactionManagement");
		return model;
		
	}
	
}
