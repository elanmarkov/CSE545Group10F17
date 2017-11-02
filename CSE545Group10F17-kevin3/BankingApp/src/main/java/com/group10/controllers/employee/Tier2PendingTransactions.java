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

	int userId;

	@ExceptionHandler(HandlerClass.class)
    public String handleResourceNotFoundException() {
        return "redirect:/exception";
    }

	@RequestMapping("/employee/Tier2TransactionManagement")
	public  ModelAndView loadPendingReqPage(HttpServletRequest request){
		try{
		ModelAndView model = new ModelAndView("/employee/Tier2TransactionManagement");
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		ExternalTransactionDaoImpl extDao = ctx.getBean("externalTransactionDaoImpl",ExternalTransactionDaoImpl.class);
		List<PendingTransaction> trans = extDao.getAllPendingTransactions();
		model.addObject("transaction_list", trans);
		return model;

	}catch(Exception e){
		throw new HandlerClass();
	}
	}
	
	@RequestMapping(value = "/tier2/transactionReview", method = RequestMethod.POST) 
	public ModelAndView reviewedTransaction(HttpServletRequest request, @RequestParam("transactionID") String transId, 
			@RequestParam("requestDecision") String requestDecision, RedirectAttributes redir) {
		try{
		userId = (Integer) request.getSession().getAttribute("userID");
		int reviewerId = userId;
		String role = (String) request.getSession().getAttribute("role");

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		ExternalTransactionDaoImpl extDao = ctx.getBean("externalTransactionDaoImpl",ExternalTransactionDaoImpl.class);

		if (requestDecision.equals("approve")) {
			extDao.approveTransaction(transId, reviewerId, role);
		} else if (requestDecision.equals("reject")) {
			extDao.declineTransaction(transId, reviewerId, role);
		}

		ModelAndView model = new ModelAndView("redirect:/employee/Tier2TransactionManagement");
		return model;
		}catch(Exception e){
			throw new HandlerClass();
		}
		}
	
	@RequestMapping(value = "/tier2/transactionNew", method = RequestMethod.POST)
	public ModelAndView newTransaction(HttpServletRequest request, @RequestParam("senderAccountNumber") String fromAccountID,
			@RequestParam("receiverAccountNumber") String toAccountID, @RequestParam("amountToAdd") double amount, RedirectAttributes redir) {
		try {
			userId = (Integer) request.getSession().getAttribute("userID");
			
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
