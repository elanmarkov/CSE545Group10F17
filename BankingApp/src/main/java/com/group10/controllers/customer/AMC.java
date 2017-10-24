package com.group10.controllers.customer;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.group10.controllers.security.HandlerClass;
import com.group10.dao.transaction.TransferDAO;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//Authorize Merchant Controller
@Controller
public class AMC {
	private String role;
	private int userID;
	private String username;

	@ExceptionHandler(HandlerClass.class)
	public String handleResourceNotFoundException() {      
		return "redirect:/raiseexception";    
	}

	private void setGlobals(HttpServletRequest request) {
		role = (String) request.getSession().getAttribute("role");
		userID = (Integer) request.getSession().getAttribute("userID");	
		username = (String) request.getSession().getAttribute("username");
	}

	@RequestMapping("/customers/authorizeMerchant_customer")
	public ModelAndView authorizeMerchants(HttpServletRequest request) throws SQLException {
		boolean authorized = false;     
		boolean removed = false;

		//Store map of parameters instead of calling request.get over and over
		//Note: Haven't tested - might break code
		Map<String, String[]> param_map = request.getParameterMap();

		//populate class globals
		setGlobals(request);

		// if the incoming request is from a merchant, return redirect to /logout page
		if(this.role.equals("ROLE_MERCHANT")){
			return new ModelAndView("redirect:" + "/logout");;
		}

		//Use DaoDetails.xml to access the TransferDAO class
		//Note: modified "DaoDetails.xml" to include 'transferDAO' bean				
		TransferDAO transferDAO = new ClassPathXmlApplicationContext("/main/resources/DaoDetails.xml").getBean("transferDAO", TransferDAO.class);

		//Obtain a list of all merchantIDs
		List<String> allMerchantAccounts = transferDAO.getNewMerchantAccounts(this.userID);
		List<Integer> merchantIDs = new ArrayList<Integer>();

		for(String merchant: allMerchantAccounts) {
			merchantIDs.add(Integer.parseInt(merchant.split(":")[1].trim()));
		}

		try{
			//Customer selects a merchant from list to authorize
			if(param_map.containsKey("merchantSelection"))
			{
				//If merchant string is incorrect, exit with error
				if(!(param_map.get("merchantSelection")[0].matches("[a-zA-Z ]+\\w\\s*:\\d+"))){
					ModelAndView model = new ModelAndView("Customers/authorizeMerchant_Customer");
					model.addObject("success", false);
					model.addObject("error_msg", "Tampering Merchant Accounts!");
					ctx.close();
					return model;
				}

				//If chosen merchant ID is not in list of all merchants, exit with error
				int merchantID = Integer.parseInt(param_map.get("merchantSelection")[0].split(":")[1]);
				if(!merchantIDs.contains(merchantID)){
					ModelAndView model = new ModelAndView("Customers/authorizeMerchant_Customer");
					model.addObject("success", false);
					model.addObject("error_msg", "Merchant is not authorized!");
					ctx.close();
					return model;
				}
				authorized = transferDAO.addMerchantToUser(merchantID,this.userID);  
			}

			//Remove merchant is user chooses to
			//Note: Not sure if "deleteMerchant" is added anywhere, or whether it is related to 'name=${merchant.id.merchantAction}'
			if(param_map.containsKey("deleteMerchant")){
				int merchantID = Integer.parseInt(param_map.get("deleteMerchant")[0].split(":")[1]);
				removed = transferDAO.deleteMerchantConnection(this.userID, merchantID);
			}

			//Note: redundant because 'getExistingMerchantAccounts' is the same as 'getNewMerchantAccounts'
			//List<String> merchantList = transferDAO.getExistingMerchantAccounts(this.userID);
			List<String> merchantList = allMerchantAccounts.subList(0, allMerchantAccounts.size());

			//If a merchant was authorized or removed, redirect to same page with a new model,
			//Else, return the old model
			if(authorized || removed){
				ModelAndView newModel = new ModelAndView("redirect:" + "/Customers/authorizeMerchant_Customer");
				newModel.addObject("merchantList",merchantList);
				newModel.addObject("allMerchantAccounts", allMerchantAccounts);
				ctx.close();
				return newModel;
			} else {
				ModelAndView model = new ModelAndView("Customers/authorizeMerchant_Customer");
				model.addObject("merchantList",merchantList);
				model.addObject("allMerchantAccounts", allMerchantAccounts);
				request.getSession().setAttribute("allMerchantAccounts", allMerchantAccounts);
				ctx.close();
				return model;
			}
		}
		catch(Exception e){
			throw new HandlerClass(); 
		}
	}
}