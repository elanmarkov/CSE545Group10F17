package com.group10.controllers.customer;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.group10.dao.customer.CustomerAccountsDao;
import com.group10.dao.transaction.ExternalRequestsDao;
import com.group10.dbmodels.CheckingAccount;
import com.group10.dbmodels.SavingsAccount;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MerchantController {

	int userId;

	@RequestMapping("/authorizeMerchant")
	public ModelAndView loadMerchantPage(HttpServletRequest request) {

		userId = (Integer) request.getSession().getAttribute("userID");

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		CustomerAccountsDao accountsDao = ctx.getBean("customerAccountDao",CustomerAccountsDao.class);
		CheckingAccount checking = accountsDao.getCheckingAccount(userId);
		SavingsAccount savings = accountsDao.getSavingsAccount(userId);
		
		ModelAndView model = new ModelAndView("customer/merchantMakePayment");
		model.addObject("savings", savings);
		model.addObject("checking", checking);
		return model;
	}
	
	@RequestMapping("/customer/requestPayment")
	public ModelAndView requestMoney(HttpServletRequest request, @RequestParam("transferFrom") String transferFrom, @RequestParam("transferTo") String transferTo,
			@RequestParam("amount") double amount) {

		userId = (Integer) request.getSession().getAttribute("userID");

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		ExternalRequestsDao extDao = ctx.getBean("externalRequestsDao",ExternalRequestsDao.class);
		
		// TODO: CHANGE TO USER ID FROM SESSION
		if (extDao.checkAccountNumberValidity(transferTo, userId) && extDao.checkAccountNumberValidity(transferFrom, userId)) {
			extDao.createPendingRequest(transferFrom, transferTo, amount, userId);
			return new ModelAndView("redirect:/customer/dashboard");
		} else {
			return new ModelAndView("redirect:/customer/dashboard");
			//redir.addFlashAttribute("error_message",accountNumber+" Not Valid");
		}
	}
}
