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
import com.group10.dbmodels.Transaction;

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
@RequestMapping("customers/FundsTransfer_between_accounts_Customer")
public ModelAndView internalFundTransfer(HttpServletRequest request) throws IOException {
		try{
			setGlobals(request);
			ModelAndView model = new ModelAndView("customers/Funds_transfer_between accounts_Customer");
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("resources/DaoDetails.xml");
			TransferDAO transferDAO = ctx.getBean("transferDAO", TransferDAO.class);
			int payerId = userID;
			List<Integer> presentuserAcc = transferDAO.getMultipleAccounts(payerId);

			List<String> userAcc = new ArrayList<String>();
			for (Integer currentElements : presentuserAcc) {
				transferDAO.getPayerAccounts(currentElements, userAcc);
			}

			model.addObject("userAccounts", userAcc);
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
	@RequestMapping(value = "Customer/Transfer", method = RequestMethod.POST)
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
		
		String originAccount = request.getParameter("itpselectPayerAccount");
		if(!originAccount.matches("\\d+:(CHECKING|SAVINGS)")){
			model.addObject("success", false);
			model.addObject("error_msg", " Invalid Credentials!");
			ctx.close();
			return model;
		}

		Double amount = new Double(amount_str);
		int originAccountNumber = Integer.parseInt(request.getParameter("itpselectPayerAccount").split(":")[0]);
		String originAccountType = request.getParameter("itpselectPayerAccount").split(":")[1];		
		boolean amountValid = transferDAO.validateAmount(originAccountNumber, amount);
		if (!amountValid) 
		{

			System.out.println("Inadequate Funds!");
			model.addObject("success", false);
			model.addObject("error_msg", "Insufficient Funds!");
			ctx.close();
			return model;
		}
		String description = null;
		TransactionDaoImpl extTransactionDAO = ctx.getBean("TransactionDao", TransactionDaoImpl.class);
		Transaction extTransferTrans = extTransactionDAO.createExternalTransaction(originAccountNumber, amount,
				originAccountNumber, description, "internalFundTfr");
		extTransactionDAO.save(extTransferTrans);
		transferDAO.updateHold(originAccountNumber, amount);
		model.addObject("success", true);
		model.addObject("payer_info", originAccountNumber + "-" + originAccountType);
		model.addObject("Amount", amount);
		ctx.close();
		return model;
	}catch(Exception e){
		throw new HandlerClass(); 
	}
	}

}

