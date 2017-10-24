package com.group10.controllers.customer;
import com.group10.dao.transaction.TransactionDaoImpl;
import com.group10.dao.transaction.TransferDAO;
import com.group10.dbmodels.Transaction;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;
import java.util.Map.Entry;
import com.group10.controllers.security.*;


//Merchant Payment Controller
@Controller
public class MPC {

	private String role;
	private Integer userID;
	private String username;

	private void setGlobals(HttpServletRequest request) {
		role = (String) request.getSession().getAttribute("role");
		userID = Integer.parseInt(request.getSession().getAttribute("userID"));
		username = (String) request.getSession().getAttribute("username");
	}

	@ExceptionHandler(HandlerClass.class)
	public String handleResourceNotFoundException() {
		return "redirect:/raiseexception";
	}

	@RequestMapping("customers/merchantMakePayment")
	public ModelAndView UserToMerchantPayment (HttpServletRequest request) throws SQLException, ParseException {
		int sentAmount = 0;
		Boolean result = false;

		//populate class globals
		setGlobals(request);

		//Store map of parameters instead of calling request.get over and over
		//Note: Haven't tested - might break code
		Map<String, String[]> param_map = request.getParameterMap();
		
		// if the incoming request is not from a merchant, return redirect to /logout page
		if(this.role.equals("ROLE_MERCHANT")){
			return new ModelAndView("redirect:" + "/logout");;
		}
		
		//Use DaoDetails.xml to access the TransferDAO and TransactionDaoImpl classes
		//Note: transactionDaoImpl.java is not completed
		ClassPathXmlApplicationContext ctx   = new ClassPathXmlApplicationContext("DaoDetails.xml");
		TransferDAO transferDAO              = ctx.getBean("transferDAO", TransferDAO.class);
		TransactionDaoImpl extTransactionDAO = ctx.getBean("transactionDao", TransactionDaoImpl.class);

		try{
			//Create the model to be returned - successful or not
			ModelAndView model = new ModelAndView("Customers/merchantMakePayment");
			
			//Obtain list of user's current authorized merchants and add the list object to the new model
			List<String> merchantAccounts = transferDAO.getExistingMerchantAccounts(this.userID);
			model.addObject("merchantAccounts", merchantAccounts);

			//Obtain list of authorized merchant IDs
			List<Integer> merchantAccountIds = new ArrayList<Integer>();
			for(String item: merchantAccounts){
				merchantAccountIds.add(Integer.parseInt(item.split(":")[1].trim()));
			}
		
			//If user tries to make payment
			if (param_map.containsKey("requestAmount") && param_map.containsKey("userSelection")) {

				//Check that the chosen merchant has correct regex pattern
				if(!(param_map.get("userSelection")[0].matches("[a-zA-Z ]+\\w\\s*:\\d+"))){
					model.addObject("success", false);
					model.addObject("error_msg", "Interfering with User Accounts!");
					ctx.close();
					return model;
				}

				//Check for unclean/empty input on sentAmount string
				String sentAmount_str = param_map.get("requestAmount")[0].trim();
				if(!(sentAmount_str.replaceAll(",", "").matches("^(\\d+\\.)?\\d+$")) || sentAmount_str.isEmpty()){
					model.addObject("success", false);
					model.addObject("error_msg", "Invalid Amt")  ;
					ctx.close();
					return model;
				}

				//Capture amount of money to be sent from the front-end ui
				try {
					sentAmount = Integer.parseInt(sentAmount_str);
				} catch(Exception e) {
					model.addObject("success", false);
					model.addObject("error_msg", "Send value not permissible");
					ctx.close();
					return model;
				}

				// If user selects an appropriate merchant
				if(param_map.get("userSelection")[0] != null) {
					String selectedMerchantID = param_map.get("userSelection")[0].split(":")[1].trim();
					String userName = param_map.get("userSelection")[0].split(":")[0]; // unused so far

					//Check that selected Merchant is authorized
					if(!merchantAccountIds.contains(selectedMerchantID)){
						model.addObject("success", false);
						model.addObject("error_msg", " Fradulent Merchant");
						ctx.close();
						return model;
					}

					//Process the payment and obtain results hashmap
					HashMap<String,String> paymap = transferDAO.processPayment(selectedMerchantID, sentAmount, this.userID);

					//Collect results
					int accepted = Integer.parseInt(paymap.get("accepted").trim());
					int userAccNum = Integer.parseInt(paymap.get("userAccount").trim());
					int merchantAccNum = Integer.parseInt(paymap.get("merchantCheckingAccount").trim());
					String description = "Transferred $"+sentAmount+" from Account:"+userAccNum+" to Account:"+merchantAccNum+"";

					//convert sentAmount as a java BigDecimal
					BigDecimal amount = new BigDecimal(sentAmount);

					//if all payment results are OK
					if(userAccNum && merchantAccNum && accepted) {   
						//And if sentAmount is less than $1000                     
						if(amount.compareTo(new BigDecimal("1000"))>=0) {
							model.addObject("success", false);
							model.addObject("error_msg", " The Amt Can't exceed 1000$");
							ctx.close();
							return model;
						} else {
							//transfer the money and save the transaction and transfer result
							Transaction externalTransfer = extTransactionDAO.createExternalTransaction(userAccNum, amount, merchantAccNum, description, "MERCHANT");
							extTransactionDAO.save(externalTransfer);
							result = transferDAO.updateAvailableBalance(amount, userAccNum);
						}
					//if payment results are not OK
					} else {
						model.addObject("success", false);
						model.addObject("error_msg", " insufficient Funds Available");
						ctx.close();
						return model;
					}
				}
			}

			//finally, if user's balance is successfully updated, then switch on success flag and return the completed model
			if(result){
				model.addObject("success",true);
			}

			ctx.close();
			return model;
		} catch(Exception e) {
			throw new HandlerClass(); 
		}    
	}
}