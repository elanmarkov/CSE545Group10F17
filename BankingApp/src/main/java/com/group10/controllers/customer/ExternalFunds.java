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

@Controller
public class ExternalFunds {
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

@RequestMapping("/customer/externalFundTransfer")
	public ModelAndView externalFundTransfer(HttpServletRequest request) throws IOException {
		try{
			setGlobals(request);
			ModelAndView model = new ModelAndView("customerPages/transferFunds");
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("resources/DaoDetails.xml");
			TransferDAO transferDAO = ctx.getBean("transferDAO", TransferDAO.class);
			int payerId = userID;
			List<Integer> currentUserAccounts = transferDAO.getMultipleAccounts(payerId);
			List<String> FilledPayeeAcc = new ArrayList<String>();
			List<String> userAcc = new ArrayList<String>();
			for (Integer currentElements : currentUserAccounts) 
			{
				transferDAO.getRelatedAccounts(currentElements, FilledPayeeAcc);
				transferDAO.getPayerAccounts(currentElements, userAcc);
			}

			for (String elem : FilledPayeeAcc) 
			{
				System.out.println("Payee: " + elem);
			}
			model.addObject("payeeAccounts", FilledPayeeAcc);
			model.addObject("userAcc", userAcc);
			model.addObject("displayPanel", "externalFundTransfer");
			request.getSession().setAttribute("userAcc", userAcc);
			request.getSession().setAttribute("payeeAccounts", FilledPayeeAcc);
			ctx.close();
			return model;
		}
		catch(Exception e)
		{
			throw new HandlerClass(); 
		}
		
	}

@RequestMapping(value = "/customer/EmailPhoneTransferSubmit", method = RequestMethod.POST)
	public ModelAndView EmailPhoneSubmit(HttpServletRequest request) throws ParseException {
		try{
			@SuppressWarnings("unchecked")
			List<String> userAcc = (List<String>) request.getSession().getAttribute("userAcc");

			setGlobals(request);
			String modeOfTransfer = request.getParameter("eptpModeOfTransfer");
			String inputMode = request.getParameter("eptpinputMode");
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
			TransferDAO transferDAO = ctx.getBean("transferDAO", TransferDAO.class);
			ModelAndView model = new ModelAndView("customerPages/transferConfirmation");
			String payerAccount_str = request.getParameter("eptpselectPayerAccount");
			if(!payerAccount_str.matches("\\d+:(CHECKING|SAVINGS)")){
				model.addObject("success", false);
				model.addObject("error_msg", " Invalid Credentials!");
				ctx.close();
				return model;
			}
			
			int PayeeAccountNumber = transferDAO.fetchAccountNumber(modeOfTransfer, inputMode);
			int payerAccountNumber = Integer.parseInt(request.getParameter("eptpselectPayerAccount").split(":")[0]);
			String payerAccountType = request.getParameter("eptpselectPayerAccount").split(":")[1];

			if (!(userAcc.contains(payerAccountNumber + ":" + payerAccountType))) {
				model.addObject("success", false);
				model.addObject("error_msg", " Invalid Request!");
				ctx.close();
				return model;
			}

			String amount_str = request.getParameter("eptpinputAmount");
					if (!(amount_str.replaceAll(",", "").matches("^(\\d+\\.)?\\d+$")) || amount_str.isEmpty()) {
				model.addObject("success", false);
				model.addObject("error_msg", "Invalid Amount!");
				ctx.close();
				return model;
			}
			BigDecimal amount = new BigDecimal(amount_str);
			String description = request.getParameter("eptpTextArea");

			if (PayeeAccountNumber != -1) {
				boolean amountValid = transferDAO.validateAmount(payerAccountNumber, amount);
				if (!amountValid) {
					System.out.println("Inadequate balance!");
					model.addObject("success", false);
					model.addObject("error_msg", "Insufficient balance!");
					ctx.close();
					return model;
				}
				
				TransactionDaoImpl extTransactionDAO = ctx.getBean("TransactionDao", TransactionDaoImpl.class);
				Transaction extTransferTrans = extTransactionDAO.createExternalTransaction(payerAccountNumber, amount,
						PayeeAccountNumber, description, "emailphoneFundTfr");
				
				BigDecimal lessThan1k = new BigDecimal(1000);
				if(extTransferTrans.getAmount().compareTo(lessThan1k) == -1) {
					extTransactionDAO.save(extTransferTrans);
					transferDAO.updateHold(payerAccountNumber, amount);
					model.addObject("success", true);
					model.addObject("payee_info", inputMode);
					model.addObject("payer_info", payerAccountNumber + "-" + payerAccountType);
					model.addObject("Amount", amount);
					ctx.close();
					return model;
				}
				
				model = new ModelAndView("customerPages/transferOTP");
				OtpDaoImpl otpdao = ctx.getBean("OtpDaoImpl", OtpDaoImpl.class);
				String email = username;
				String message = otpdao.processOTP(email);
				
				if(message.equals("Account Locked")) 
				{
					model = new ModelAndView("customerPages/transferConfirmation");
					model.addObject("success", false);
					model.addObject("error_msg", "OTP Locked");
				} 
				else {
					request.getSession().setAttribute("transaction", extTransferTrans);
					request.getSession().setAttribute("inputMode",inputMode); 
					request.getSession().setAttribute("eptpselectPayerAccount",payerAccountType);
				}
				ctx.close();
				return model;
			} 
			else {
				model.addObject("success", false);
				model.addObject("error_msg", "Payee account not found for given Email/Phone!");
				ctx.close();
				return model;
			}
		}
		catch(Exception e)
		{
			throw new HandlerClass(); 
		}
		

	}
	
	@RequestMapping(value = "/customer/verifyTransactionOTP", method = RequestMethod.POST)
	public ModelAndView verifyTransactionOTP(HttpServletRequest request, @RequestParam("otpdata") String otp) throws ParseException {
		try{
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("jdbc/config/DaoDetails.xml");
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
				ModelAndView model = new ModelAndView("customerPages/transferOTP");
				model.addObject("error_msg", message);
				ctx.close();
				return model;
			}
		}
		catch(Exception e)
		{
			throw new HandlerClass(); 
		}
	}
}

