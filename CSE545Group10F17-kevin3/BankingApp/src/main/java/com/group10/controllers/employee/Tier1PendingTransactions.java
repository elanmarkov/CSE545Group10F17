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

	int userId;

	@RequestMapping("/employee/Tier1TransactionManagement")
	public  ModelAndView loadPendingReqPage(HttpServletRequest request){

		userId = (Integer) request.getSession().getAttribute("userID");

		ModelAndView model = new ModelAndView("/employee/Tier1TransactionManagement");
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		ExternalTransactionDaoImpl extDao = ctx.getBean("externalTransactionDaoImpl",ExternalTransactionDaoImpl.class);
		List<PendingTransaction> trans = extDao.getPendingNonCriticalTransactions();
		model.addObject("transaction_list", trans);
		return model;
	}
	
	@RequestMapping(value = "/tier1/transactionReview", method = RequestMethod.POST) 
	public ModelAndView reviewedTransaction(HttpServletRequest request, @RequestParam("transactionID") String transId, @RequestParam("requestDecision") String requestDecision, RedirectAttributes redir) {

		userId = (Integer) request.getSession().getAttribute("userID");
		int reviewerId = userId;
		String role = (String) request.getSession().getAttribute("role");
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		ExternalTransactionDaoImpl extDao = ctx.getBean("externalTransactionDaoImpl",ExternalTransactionDaoImpl.class);
		
		if (extDao.checkPendingTransactionIDValidity(transId, reviewerId)) {
			if (requestDecision.equals("approve")) {
				extDao.approveTransaction(transId, reviewerId, role);
			} else if (requestDecision.equals("reject")) {
				extDao.declineTransaction(transId, reviewerId, role);
			}
		} 
		
		ModelAndView model = new ModelAndView("redirect:/employee/Tier1TransactionManagement");
		return model;
	}
	
	@RequestMapping(value = "/tier1/transactionNew", method = RequestMethod.POST)
	public ModelAndView newTransaction(HttpServletRequest request, @RequestParam("senderAccountNumber") String fromAccountNumber, @RequestParam("receiverAccountNumber") String toAccountNumber, @RequestParam("amountToAdd") double amount) {

		userId = (Integer) request.getSession().getAttribute("userID");
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		ExternalTransactionDaoImpl extDao = ctx.getBean("externalTransactionDaoImpl",ExternalTransactionDaoImpl.class);
		
		if (extDao.checkAccountNumberValidity(toAccountNumber, userId) &&extDao.checkAccountNumberValidity(fromAccountNumber, userId)) {
			//Tier 2 can't create critical transactions
			if (amount < 5000) {
				extDao.createPendingTransaction(userId, amount, toAccountNumber, fromAccountNumber, "HARDCODED DESCRIPTION");
			}
		}
		
		ModelAndView model = new ModelAndView("redirect:/employee/Tier1TransactionManagement");
		return model;
	}
}
