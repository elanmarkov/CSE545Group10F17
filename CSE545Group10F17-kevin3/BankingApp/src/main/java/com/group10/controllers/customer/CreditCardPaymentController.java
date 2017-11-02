package com.group10.controllers.customer;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.omg.CORBA.Request;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.group10.controllers.security.HandlerClass;
import com.group10.dao.customer.CustomerAccountsDao;
import com.group10.dao.customerDashboard.CustomerDashboardDaoImpl;
import com.group10.dao.transaction.ExternalTransactionDaoImpl;
import com.group10.dbmodels.CreditCard;


@Controller
public class CreditCardPaymentController {

	String role;
	int userID;
	String username;
	
	public void setGlobals(HttpServletRequest request){
		role = (String) request.getSession().getAttribute("role");
		userID = (Integer) request.getSession().getAttribute("userID");
		username = (String) request.getSession().getAttribute("username");	
	}
	
	@ExceptionHandler(HandlerClass.class)
    public String handleResourceNotFoundException() {
        return "redirect:/exception";
    }
	
	
	@RequestMapping(value = "customer/creditPayment", method = RequestMethod.POST)
	public ModelAndView showCreditPaymentPage(HttpServletRequest request){
		try{
			setGlobals(request);
			ModelAndView model = new ModelAndView("customer/CustomerDashboard");
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
			ExternalTransactionDaoImpl edao = ctx.getBean("externalTransactionDaoImpl",ExternalTransactionDaoImpl.class);
			CustomerAccountsDao cdao = ctx.getBean("customerAccountDao", CustomerAccountsDao.class);
			CustomerDashboardDaoImpl ddao = ctx.getBean("customerDashboardDaoImpl",CustomerDashboardDaoImpl.class);
			String fromAccountID = cdao.getCheckingAccNumber(userID);
			int userID = (Integer) request.getSession().getAttribute("userID");
			CreditCard cc = cdao.getCreditCard(userID);
			
			edao.createPendingTransaction(userID, cc.getCurrentAmountDue(), cc.getCreditCardNumber(), fromAccountID, "credit");
			return model;
			
		}catch(Exception e){
			throw new HandlerClass();
		}
	}
}
	
	