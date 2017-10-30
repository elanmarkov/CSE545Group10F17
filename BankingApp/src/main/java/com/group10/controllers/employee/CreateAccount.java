package com.group10.controllers.employee;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.group10.controllers.security.HandlerClass;
import com.group10.dao.customer.CustomerAccountsDao;
import com.group10.dao.employee.EmpFunctionsDaoImpl;

@Controller
public class CreateAccount {
	
	
	@ExceptionHandler(HandlerClass.class)
    public String handleResourceNotFoundException() {
        return "redirect:/exception";
    }

	
	@RequestMapping("/employee/CreateUserAccounts")
	public ModelAndView createAccount(){
		return new ModelAndView("/employee/CreateUserAccounts");
	}
	
	@RequestMapping(value = "/employee/createAccounts", method= RequestMethod.POST)
	public ModelAndView createAccounts(HttpServletRequest request, @RequestParam("username") String userEmail,@RequestParam("savings") String saving,@RequestParam("credit") String credit
			, @RequestParam("checking") String checking, RedirectAttributes redir){
		try{
			ModelAndView model = new ModelAndView();
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
			CustomerAccountsDao cdao = ctx.getBean("customerAccountDao", CustomerAccountsDao.class);

			Integer userId = cdao.getUserIdByEmail(userEmail);
			if (userId != null) {

				if (saving.equals("yes")) {
					if (cdao.createSavingsAccount(userId)) {
						redir.addFlashAttribute("error_msg", "Created savings account successfully");
						//	model.setViewName("");
					} else
						redir.addFlashAttribute("error_msg", "unable to create savings account");
				}
				if (checking.equals("yes")) {
					if (cdao.createCheckingAccount(userId))
						redir.addFlashAttribute("error_msg", "Created checking account successfully");
					else
						redir.addFlashAttribute("error_msg", "unable to create checking account");

				}
				if (credit.equals("yes")) {
					if (cdao.createCreditAccount(userId))
						redir.addFlashAttribute("error_msg", "Created credit account successfully");
					else
						redir.addFlashAttribute("error_msg", "unable to create credit account");

				}
			}
			model.setViewName("redirect:/employee/CreateUserAccounts");
			ctx.close();
			return model;
			
		}catch(Exception e){
			throw new HandlerClass();
		}
	}
}