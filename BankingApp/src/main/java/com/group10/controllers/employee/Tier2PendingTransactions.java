/*
 * Author: Kevin Everly
 */
package com.group10.controllers.employee;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.group10.controllers.security.HandlerClass;
import com.group10.dao.employee.EmpFunctionsDaoImpl;
import com.group10.dao.transaction.*;
import com.group10.dbmodels.PendingTransaction;

@Controller
public class Tier2PendingTransactions {
	
	@ExceptionHandler(HandlerClass.class)
    public String handleResourceNotFoundException() {
        return "redirect:/exception";
    }
	@RequestMapping("/employee/Tier2TransactionManagement")
	public  ModelAndView loadPendingReqPage(){
		ModelAndView model = new ModelAndView("/employee/Tier2TransactionManagement");
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		ExternalTransactionDaoImpl extDao = ctx.getBean("externalTransactionDaoImpl",ExternalTransactionDaoImpl.class);
		List<PendingTransaction> trans = extDao.getAllPendingTransactions();
		model.addObject("transaction_list", trans);
		return model;
	}
	
	@RequestMapping(value = "/tier2/transactionReview", method = RequestMethod.POST) 
	public ModelAndView reviewedTransaction(HttpServletRequest request, @RequestParam("transactionID") String transId, 
			@RequestParam("requestDecision") String requestDecision, RedirectAttributes redir) {
		
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
			ExternalTransactionDaoImpl extDao = ctx.getBean("externalTransactionDaoImpl",ExternalTransactionDaoImpl.class);
			
			if (requestDecision.equals("approve")) {
				//TODO: PASS REVIEWER ID FROM SESSION and role
				extDao.approveTransaction(transId, 1, "tier2");
			} else if (requestDecision.equals("reject")) {
				//TODO: PASS REVIEWER ID FROM SESSION
				extDao.declineTransaction(transId, 1, "tier2");
			}
			
			ModelAndView model = new ModelAndView("redirect:/employee/Tier2TransactionManagement");
			return model;
	}
	
	@RequestMapping(value = "/tier2/transactionNew", method = RequestMethod.POST)
	public ModelAndView newTransaction(HttpServletRequest request, @RequestParam("senderAccountNumber") String fromAccountID,
			@RequestParam("receiverAccountNumber") String toAccountID, @RequestParam("amountToAdd") double amount, RedirectAttributes redir) {
		try {
			int userId = 1;
			
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
			ExternalTransactionDaoImpl extDao = ctx.getBean("externalTransactionDaoImpl",ExternalTransactionDaoImpl.class);
			
			if (extDao.checkAccountNumberValidity(fromAccountID, userId) && extDao.checkAccountNumberValidity(toAccountID, userId)) {
				//Tier 2 can't create critical transactions
				if (amount < 5000) {
					extDao.createPendingTransaction(1, amount, toAccountID, fromAccountID, "HARDCODED DESCRIPTION");
					redir.addFlashAttribute("error_message: Not authorized to create critical transactions");
				}
			}
			ModelAndView model = new ModelAndView("redirect:/employee/Tier2TransactionManagement");
			return model;
			
		} catch (Exception e) {
			throw new HandlerClass();
		}	
	}
}
