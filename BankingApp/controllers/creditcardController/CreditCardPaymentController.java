package com.group10.controllers.creditcardController;

import java.math.BigDecimal;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.group10.controllers.security.ExceptionHandlerClass;
import com.group10.dao.creditcard.CreditCardDOA;
import com.group10.dao.creditcard.CustomerDAOHelper;
import com.group10.dao.customer.CustomerDAO;
import com.group10.dao.customer.ExtUserDaoImpl;
import com.group10.dao.log.LogDaoImpl;
import com.group10.dao.transaction.ExternalTransactionDAO;
import com.group10.dao.transaction.TransferDAO;
import com.group10.db.model.BankAccountExternal;
import com.group10.db.model.CreditAccount;
import com.group10.db.model.Customer;
import com.group10.db.model.DatabaseLog;
import com.group10.db.model.Transaction;



@Controller
public class CreditCardPaymentController {

	String role;
	int userID;
	String username;
	
	public void setGlobals(HttpServletRequest request){
		role = (String) request.getSession().getAttribute("role");
		userID = (int) request.getSession().getAttribute("userID");
		username = (String) request.getSession().getAttribute("username");	
	}
	
	@ExceptionHandler(ExceptionHandlerClass.class)
    public String handleResourceNotFoundException() {
        return "redirect:/raiseexception";
    }
	
	
	@RequestMapping("/customer/creditPayment")
	public ModelAndView showCreditPaymentPage(HttpServletRequest request){
		try{
			setGlobals(request);
			ModelAndView model = new ModelAndView("customerPages/creditPaymentPage");
			CreditCardDOA doa = CustomerDAOHelper.creditCardDAO();
			CustomerDAO cust_dao = CustomerDAOHelper.customerDAO();
			Customer cust = cust_dao.getCustomer(userID);
			
			CreditAccount account = doa.getCreditAccount(cust);
			
			model.addObject("creditAccount",account);
			return model;
		}catch(Exception e){
			throw new ExceptionHandlerClass(); 
		}
		
	}
	
	@RequestMapping(value="/customer/makePayement", method = RequestMethod.POST)
	public ModelAndView makePayment(HttpServletRequest request, @RequestParam("inputAmountField") String amount) throws ParseException {
		
		boolean success = false;
		setGlobals(request); 
		try{
			ModelAndView model = new ModelAndView("customerPages/creditPaymentPage");
			BigDecimal amt = new BigDecimal(amount);
			
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
			CreditCardDOA doa = CustomerDAOHelper.creditCardDAO();
			CustomerDAO cust_dao = CustomerDAOHelper.customerDAO();
			BankAccountExternal checkingAccount = new BankAccountExternal();
			Customer customer = cust_dao.getCustomer(userID);
			ClassPathXmlApplicationContext ctx_log = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
			LogDaoImpl logDao = ctx_log.getBean("DatabaseLogDao", LogDaoImpl.class);
			DatabaseLog dblog = new DatabaseLog();
			CreditAccount account = doa.getCreditAccount(customer);
			ExtUserDaoImpl CustomerDAO = ctx.getBean("ExtUserDaoImpl", ExtUserDaoImpl.class);
			checkingAccount = CustomerDAO.getAccount(customer, checkingAccount,"CHECKING");
			
			TransferDAO transferDAO = ctx.getBean("transferDAO", TransferDAO.class);
			boolean amountValid = transferDAO.validateAmount(checkingAccount.getAccount_number(), amt);

			if (!amountValid) {

				System.out.println("Inadequate balance!");
				model.addObject("paymentResult", "0");
				ctx.close();
				return model;
			}
			ExternalTransactionDAO extTransactionDAO = ctx.getBean("extTransactionDAO",ExternalTransactionDAO.class);
			Transaction extTransferTrans = extTransactionDAO.createExternalTransaction(checkingAccount.getAccount_number(), amt, account.getAccountNumber(), "Credit Card payment", "CC_PAYMENT");
			success = extTransactionDAO.save(extTransferTrans, "transaction_pending");
			
			transferDAO.updateHold(checkingAccount.getAccount_number(), amt);
			
			if (success) {
				model.addObject("paymentResult", "1");
				dblog.setActivity("credit_card_payment");
	            dblog.setDetails("Credit card payment made for account" + account.getAccountNumber());
	            dblog.setUserid((int) request.getSession().getAttribute("userID"));
	            logDao.save(dblog, "external_log");
	            ctx_log.close();
			} else {
				model.addObject("paymentResult", "0");
			}
			ctx.close();
			return model;
		}catch(Exception e){
			throw new ExceptionHandlerClass(); 
		}
		
	}
}
