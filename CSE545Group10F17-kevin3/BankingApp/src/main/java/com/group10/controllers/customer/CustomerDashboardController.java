package com.group10.controllers.customer;
import com.group10.dbmodels.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.group10.dbmodels.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.group10.controllers.security.HandlerClass;
import com.group10.dao.customer.CustomerAccountsDao;
import com.group10.dao.customerDashboard.CustomerDashboardDaoImpl;
import com.group10.dao.employee.EmpFunctionsDaoImpl;
import com.group10.dao.logs.LogsDaoImpl;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomerDashboardController {

	int userId;

	@RequestMapping("/customer/dashboard")
	public ModelAndView display(HttpServletRequest request){
	try{	
		userId = (Integer) request.getSession().getAttribute("userID");

		ModelAndView model = new ModelAndView("/customer/CustomerDashboard");

 		ApplicationContext  ctx = new ClassPathXmlApplicationContext("DaoDetails.xml"); 
	    CustomerDashboardDaoImpl sdao = ctx.getBean("customerDashboardDaoImpl" , CustomerDashboardDaoImpl.class);
	    CustomerAccountsDao cdao = ctx.getBean("customerAccountDao", CustomerAccountsDao.class);
	    SavingsAccount savings = sdao.savingsAccountDetails(userId);
	    CheckingAccount checking = sdao.checkingAccountDetails(userId);
		CreditCard credit = cdao.getCreditCard(userId);

		model.addObject("savings", savings);
		model.addObject("checking", checking);
		model.addObject("credit", credit);

	    if (savings != null ) {
			List<PendingTransaction> pendingSavings = sdao.pendingTransactions(savings.getAccountNumber());
			List<CompletedTransaction> completedSavings = sdao.completedTransactions(savings.getAccountNumber());
			model.addObject("pendingSavings", pendingSavings);
			model.addObject("completedSavings", completedSavings);

		}
		if (checking != null) {
			List<PendingTransaction> pendingChecking = sdao.pendingTransactions(checking.getAccountNumber());
			List<CompletedTransaction> completedChecking = sdao.completedTransactions(checking.getAccountNumber());
			model.addObject("pendingChecking", pendingChecking);
			model.addObject("completedChecking", completedChecking);

		}
		if (credit != null) {
			List<PendingTransaction> pendingCredit = sdao.pendingTransactions(credit.getCreditCardNumber());
			List<CompletedTransaction> completedCredit = sdao.completedTransactions(credit.getCreditCardNumber());
			
			model.addObject("creditCard", credit);
			model.addObject("pendingCredit", pendingCredit);
			model.addObject("completedCredit", completedCredit);
		}

		return model;		
	}catch(Exception e){
		throw new HandlerClass();
	}
	}

	@RequestMapping("/customer/profile")
	public ModelAndView viewProfile(HttpServletRequest request) {

		userId = (Integer) request.getSession().getAttribute("userID");

		ApplicationContext  ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		CustomerDashboardDaoImpl sdao = ctx.getBean("customerDashboardDaoImpl" , CustomerDashboardDaoImpl.class);

		User user = sdao.getUserById(userId);
		ModelAndView model = new ModelAndView("customer/customerUserDetails");
		model.addObject(user);
		return model;

	}
	
	@RequestMapping("/customer/modify")
	public ModelAndView internalModify(HttpServletRequest request, @RequestParam("address") String address, @RequestParam("state") String state,  @RequestParam("city") String city ,
			 @RequestParam("zipcode") String zipcode, @RequestParam("country") String country, @RequestParam("phone") String phone,
			 @RequestParam("id") int userId,RedirectAttributes redir){
		try{
			ModelAndView model = new ModelAndView();

			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
			EmpFunctionsDaoImpl fdao = ctx.getBean("empFunctionsDaoImpl",EmpFunctionsDaoImpl.class);
			LogsDaoImpl ldao = ctx.getBean("logsDaoImpl", LogsDaoImpl.class);

			fdao.createExternalRequest(address, city, state, country, zipcode, phone, userId);
			redir.addFlashAttribute("error_msg","Modified the address for "+userId);
			User user = fdao.getUser(userId);
			model.addObject("user",user);
			model.setViewName("/employee/Tier2UserDetails");
			ldao.saveLogs("Modified internal account", "address change", userId, "internal");

			ctx.close();
			return model;

		}catch(Exception e){
			throw new HandlerClass();
		}
	}
	
}
