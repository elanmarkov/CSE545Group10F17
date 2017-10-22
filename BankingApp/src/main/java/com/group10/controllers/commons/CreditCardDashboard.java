package com.group10.controllers.commons;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.group10.controllers.security.HandlerClass;
import com.group10.dao.creditcard.creditcardDao;
import com.group10.dao.creditcard.CustomerDaoHelper;
import com.group10.dbmodels.CreditAccount;
import com.group10.dbmodels.Customer;
import com.group10.dbmodels.TransactionModel;

 @Controller
public class CreditCardDashboard{
	public ModelAndView model_ch = new ModelAndView("????/???");
	String role;
	int userID;
	String username;
	
	public void setGlobals(HttpServletRequest request)
	{
		role = (String) request.getSession().getAttribute("role");
		userID = (int) request.getSession().getAttribute("userID");
		username = (String) request.getSession().getAttribute("username");
	}
	
	@ExceptionHandler(HandlerClass.class)
	public String handleResourceNotFoundException()
	{
        return "redirect:/exception";
    }
	
	@RequestMapping("/???/???")
	public ModelAndView showCreditHome(HttpServletRequest request)
	{
		setGlobals(request);
		try 
		{
			ModelAndView model = new ModelAndView("??/??");
			CreditAccount account = getCreditInfo (userID);
			model.addObject("creditAccount",account);
			CreditCardDao transdao1 = CustomerDaoHelper.creditCardDao();

			int month = new Date().getMonth();
			int year = new Date().getYear() + 1900;
			
			List<String> months = new ArrayList<String>();
			for (int i = 0; i <= month + 1; i ++ )
			{
				String value = getMonthFromNo(i);
				value = value +  " " + year;
				months.add(value);
			}
			model.addObject("selectedMonth",month);
			model.addObject("months",months);
			List<Transaction> transactionsCredit = transdao1.getTransactionForMonth(account,month-1);
		    model.addObject("transactions",transactionsCredit);
		    return model;
		} 
		catch (Exception e)
		{
			throw new ExceptionHandlerClass();
			
		}
	}
	@RequestMapping(value="/??/???", method = RequestMethod.POST)
	
	public ModelAndView getTransactions(HttpServletRequest request,@RequestParam("monthPicker") String interval)
	{
		setGlobals(request);
		try
		{
			ModelAndView model = new ModelAndView("??/??");
			CreditAccount account = getCreditInfoForUser(userID);
			model.addObject("creditAccount",account);
			CreditCardDoa transdao1 = CustomerDaoHelper.creditCardDao();
			int monthNo= getMonthFromString(interval);
			int month= new Date().getMonth();
			int year= new Date().getYear()+ 1990;
			
			List<String> months= new ArrayList<>();
			for(int i=0;i<=month+1;i++)
			{
			String Value=getMonthFromNo(i);
			value=value +" "+ year;
			months.add(value);
		    }
			model.addObject("selectedMonth",interval);
			model.addObject("months",months);
			List<Transaction> transactionsCredit = transdao1.getTransactionForMonth(account, monthNum);
			model.addObject("transations", transactionsCredit );
			return model;
		}
		catch(Exception e)
		{
			throw new ExceptionHandlerClass();
		}
				
	}
	private int getMonthFromString(String monthStr) {
		int monthNo = 0;
		if (monthStr.contains("Jan")) {
			monthNo = 0;
		} else if  (monthStr.contains("Feb")) {
			monthNo = 1;
		} else if  (monthStr.contains("Mar")) {
			monthNo = 2;
		} else if  (monthStr.contains("April")) {
			monthNo = 3;
		} else if  (monthStr.contains("May")) {
			monthNo = 4;
		} else if  (monthStr.contains("June")) {
			monthNo = 5;
		}  else if  (monthStr.contains("July")) {
			monthNo = 6;
		}  else if  (monthStr.contains("Aug")) {
			monthNo = 7;
		}  else if  (monthStr.contains("Sept")) {
			monthNo = 8;
		}  else if  (monthStr.contains("Oct")) {
			monthNo = 9;
		}  else if  (monthStr.contains("Nov")) {
			monthNo = 10;
		}  else if  (monthStr.contains("Dec")) {
			monthNo = 11;
		} 
		return monthNo;
	}
	
	private String getMonthFromNo(int i) {
		String value = "";
		switch (i) {
		case 0:
			value = "Jan";
			break;
		case 1:
			value = "Feb";
			break;
		case 2:
			value = "Mar";
			break;
		case 3:
			value = "April";
			break;
		case 4:
			value = "May";
			break;
		case 5:
			value = "June";
			break;
		case 6:
			value = "July";
			break;
		case 7:
			value = "Aug";
			break;
		case 8:
			value = "Sept";
			break;
		case 9:
			value = "Oct";
			break;
		case 10:
			value = "Nov";
			break;
		case 11:
			value = "Dec";
			break;
		
		
			
		}
		return value;
	}
}

