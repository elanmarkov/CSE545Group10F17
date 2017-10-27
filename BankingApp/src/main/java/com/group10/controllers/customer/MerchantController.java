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

@Controller
public class MerchantController {

	@RequestMapping("/authorizeMerchant")
	public ModelAndView loadMerchantPage() {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		CustomerAccountsDao accountsDao = ctx.getBean("customerAccountDao",CustomerAccountsDao.class);
		CheckingAccount checking = accountsDao.getCheckingAccount(1); // TODO: MAKE THIS THE CURRENT USERID FROM SESSION
		SavingsAccount savings = accountsDao.getSavingsAccount(1); // TODO: MAKE THIS THE CURRENT USERID FROM SESSION
		
		ModelAndView model = new ModelAndView("customer/merchantMakePayment");
		model.addObject("savings", savings);
		model.addObject("checking", checking);
		return model;
	}
	
	@RequestMapping("/customer/requestPayment")
	public ModelAndView requestMoney(@RequestParam("transferFrom") String transferFrom, @RequestParam("transferTo") String transferTo,
			@RequestParam("amount") double amount) {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		ExternalRequestsDao extDao = ctx.getBean("externalRequestsDao",ExternalRequestsDao.class);
		
		// TODO: CHANGE TO USER ID FROM SESSION
		if (extDao.checkAccountNumberValidity(transferTo, 1) && extDao.checkAccountNumberValidity(transferFrom, 1)) {
			extDao.createPendingRequest(transferFrom, transferTo, amount, 1); //TODO: USE USER_ID FROM SESSION
			return new ModelAndView("redirect:/customer/dashboard");
		} else {
			return new ModelAndView("redirect:/customer/dashboard");
			//redir.addFlashAttribute("error_message",accountNumber+" Not Valid");
		}
	}
}
