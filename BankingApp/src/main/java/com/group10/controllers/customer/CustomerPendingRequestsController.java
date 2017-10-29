
package com.group10.controllers.customer;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.group10.dao.transaction.ExternalRequestsDao;
import com.group10.dao.transaction.ExternalTransactionDaoImpl;
import com.group10.dbmodels.PendingExternalRequests;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomerPendingRequestsController {

	int userId;
	
	@RequestMapping("/customer/pendingRequests")
	public ModelAndView pendingRequests(HttpServletRequest request) {

		userId = (Integer) request.getSession().getAttribute("userID");

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		ExternalRequestsDao extDao = ctx.getBean("externalRequestsDao",ExternalRequestsDao.class);
		
		List<PendingExternalRequests> extRequests = extDao.getPendingRequests(userId); // TODO: USE USERID FROM SESSION
		ModelAndView model = new ModelAndView("/customer/PendingRequestManagement");
		model.addObject("requestList", extRequests);
		return model;
	}
	
	@RequestMapping("/employee/PendingRequestManagement")
	public ModelAndView reviewRequest(HttpServletRequest request, @RequestParam("requestType") String requestType, @RequestParam("requestID") int requestID) {

		userId = (Integer) request.getSession().getAttribute("userID");

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		ExternalRequestsDao extDao = ctx.getBean("externalRequestsDao",ExternalRequestsDao.class);
		
		if (extDao.checkPendingRequestIDValidity(requestID, userId)) {
			extDao.reviewRequest(requestType, requestID);
		}
		ModelAndView model = new ModelAndView("redirect:/customer/pendingRequests");
		return model;
	}
}
