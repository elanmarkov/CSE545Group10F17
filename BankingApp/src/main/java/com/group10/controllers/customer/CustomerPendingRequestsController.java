
package com.group10.controllers.customer;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.group10.dao.transaction.ExternalRequestsDao;
import com.group10.dao.transaction.ExternalTransactionDaoImpl;
import com.group10.dbmodels.PendingExternalRequest;

@Controller
public class CustomerPendingRequestsController {

	@RequestMapping("/customer/pendingRequests")
	public ModelAndView pendingRequests() {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		ExternalRequestsDao extDao = ctx.getBean("externalRequestsDao",ExternalRequestsDao.class);
		
		List<PendingExternalRequest> extRequests = extDao.getPendingRequests(1); // TODO: USE USERID FROM SESSION
		ModelAndView model = new ModelAndView("/Customers/PendingRequestManagement");
		model.addObject("transactionList", extRequests);
		return model;
	}
	
	@RequestMapping("/employee/PendingRequestManagement")
	public ModelAndView reviewRequest(@RequestParam("requestType") String requestType, @RequestParam("requestID") int requestID) {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		ExternalRequestsDao extDao = ctx.getBean("externalRequestsDao",ExternalRequestsDao.class);
		
		extDao.reviewRequest(requestType, requestID);
		ModelAndView model = new ModelAndView("redirect:/customer/pendingRequests");
		return model;
	}
}
