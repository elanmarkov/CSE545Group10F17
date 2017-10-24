package com.group10.controllers.customer;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.group10.controllers.security.HandlerClass;
import com.group10.dao.transaction.TransactionDaoImpl;
import com.group10.dao.transaction.TransferDAO;
import com.group10.dao.otp.OtpDaoImpl;
import com.group10.dbmodels.Transaction;
import com.group10.dao.otp.OneTimePassword;
@Controller
public class InternalFunds {

	String role;
	int userID;
	String username;
public void setGlobals(HttpServletRequest request) {
		role = (String) request.getSession().getAttribute("role");
		System.out.println(role + "role");
		userID = (Integer) request.getSession().getAttribute("userID");
		username = (String) request.getSession().getAttribute("username");
	}
@ExceptionHandler(HandlerClass.class)
public String handleResourceNotFoundException() 
	{
        return "redirect:/raiseexception";
    }
@RequestMapping("/customers/Funds transfer_between accounts_Customer")
public ModelAndView internalFundTransfer(HttpServletRequest request) throws IOException {
		try{
			setGlobals(request);
			ModelAndView model = new ModelAndView("/customers/Funds transfer_between accounts_Customer");
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("resources/DaoDetails.xml");
			TransferDAO transferDAO = ctx.getBean("transferDAO", TransferDAO.class);
			int payerId = userID;
			List<Integer> presentuserAcc = transferDAO.getMultipleAccounts(payerId);

			List<String> userAcc = new ArrayList<String>();
			for (Integer currentElements : presentuserAcc) {
				transferDAO.getPayerAccounts(currentElements, userAcc);
			}

			model.addObject("userAcc", userAcc);
			model.addObject("displayPanel", "internalFundTransfer");
			request.getSession().setAttribute("userAcc", userAcc);
			ctx.close();
			return model;
		}
		catch(Exception e)
		{
			throw new HandlerClass(); 
		}
		
	}
@RequestMapping(value = "/customers/Funds transfer_between accounts_Customer", method = RequestMethod.POST)
public ModelAndView InternalSubmit(HttpServletRequest request) throws ParseException {
	try{
		@SuppressWarnings("unchecked")
		List<String> userAcc = (List<String>) request.getSession().getAttribute("userAcc");
		ModelAndView model = new ModelAndView("/customers/Funds transfer_between accounts_Customer");
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("resources/DaoDetails.xml");
		TransferDAO transferDAO = ctx.getBean("transferDAO", TransferDAO.class);
	    setGlobals(request);
		System.out.println("------------");
		String amount_str = request.getParameter("itpinputAmount");
		if (!(amount_str.replaceAll(",", "").matches("^(\\d+\\.)?\\d+$")) || amount_str.isEmpty()) 
		{
			model.addObject("success", false);
			model.addObject("error_msg", "Invalid Amount!");
			ctx.close();
			return model;
		}
		String payerAccount_str = request.getParameter("itpselectPayerAccount");
		if(!payerAccount_str.matches("\\d+:(CHECKING|SAVINGS)")){
			model.addObject("success", false);
			model.addObject("error_msg", " Invalid Credentials!");
			ctx.close();
			return model;
		}
		String payeeAccount_str = request.getParameter("itpselectPayeeAccount");
		if(!payeeAccount_str.matches("\\d+:(CHECKING|SAVINGS)")){
			model.addObject("success", false);
			model.addObject("error_msg", " Invalid Credentials!");
			ctx.close();
			return model;
		}
		BigDecimal amount = new BigDecimal(amount_str);
		int payerAccountNumber = Integer.parseInt(request.getParameter("itpselectPayerAccount").split(":")[0]);
		String payerAccountType = request.getParameter("itpselectPayerAccount").split(":")[1];
		int payeeAccountNumber = Integer.parseInt(request.getParameter("itpselectPayeeAccount").split(":")[0]);
		String payeeAccountType = request.getParameter("itpselectPayeeAccount").split(":")[1];
		String description = request.getParameter("itpTextArea");
		if (!(userAcc.contains(payerAccountNumber + ":" + payerAccountType)
				&& userAcc.contains(payeeAccountNumber + ":" + payeeAccountType))) 
		{
			model.addObject("success", false);
			model.addObject("error_msg", " Invalid Request!");
			ctx.close();
			return model;
		}
		if ((payerAccountNumber + ":" + payerAccountType)
				.equalsIgnoreCase(payeeAccountNumber + ":" + payeeAccountType)) 
		{
			model.addObject("success", false);
			model.addObject("error_msg", " Payer and Payee account are same!");
			ctx.close();
			return model;

		}
		boolean amountValid = transferDAO.validateAmount(payerAccountNumber, amount);
		if (!amountValid) 
		{

			System.out.println("Inadequate balance!");
			model.addObject("success", false);
			model.addObject("error_msg", "Insufficient balance!");
			ctx.close();
			return model;
		}
		TransactionDaoImpl extTransactionDAO = ctx.getBean("TransactionDao", TransactionDaoImpl.class);
		Transaction extTransferTrans = extTransactionDAO.createExternalTransaction(payerAccountNumber, amount,
				payeeAccountNumber, description, "internalFundTfr");
		extTransactionDAO.save(extTransferTrans);
		transferDAO.updateHold(payerAccountNumber, amount);
		model.addObject("success", true);
		model.addObject("payee_info", payeeAccountNumber + "-" + payeeAccountType);
		model.addObject("payer_info", payerAccountNumber + "-" + payerAccountType);
		model.addObject("Amount", amount);
		ctx.close();
		return model;
	}catch(Exception e){
		throw new HandlerClass(); 
	}
	}
@RequestMapping(value = "Login/OtpVerify", method = RequestMethod.POST)
public ModelAndView verifyTransactionOTP(HttpServletRequest request, @RequestParam("otpdata") String otp) throws ParseException {
	try{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("resources/DaoDetails.xml");
		setGlobals(request);
		TransactionDaoImpl extTransactionDAO = ctx.getBean("TransactionDao", TransactionDaoImpl.class);
		TransferDAO transferDAO = ctx.getBean("transferDAO", TransferDAO.class);
		OtpDaoImpl otpdao = ctx.getBean("OtpDaoImpl",OtpDaoImpl.class);
		Transaction extTransferTrans = (Transaction)request.getSession().getAttribute("transaction");
		String email = username;
		String message = otpdao.verifyOTP(otp, email);
		if(message.equals("OTP Validated")) 
		{
			String inputMode = (String)request.getSession().getAttribute("inputMode");
			ModelAndView model = new ModelAndView("customerPages/transferConfirmation");
			otpdao.sendEmailToUser(email, inputMode, extTransferTrans.getAmount());
			extTransactionDAO.save(extTransferTrans);
			transferDAO.updateHold(extTransferTrans.getPayer_id(), extTransferTrans.getAmount());
			model.addObject("success", true);
			model.addObject("payee_info", inputMode);
			String payerAccountType = (String)request.getSession().getAttribute("eptpselectPayerAccount");
			model.addObject("payer_info", extTransferTrans.getPayer_id() + "-" + payerAccountType);
			model.addObject("Amount", extTransferTrans.getAmount());
			ctx.close();
			return model;
		}
		else {
			ModelAndView model = new ModelAndView("Login/OtpVerify");
			model.addObject("error_msg", message);
			ctx.close();
			return model;
		}
	}
	catch(Exception e)
	{
		throw new HandlerClass(); 
	}
}}

	

