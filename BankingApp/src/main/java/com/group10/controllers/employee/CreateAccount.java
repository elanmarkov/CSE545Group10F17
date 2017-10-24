package com.group10.controllers.employee;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.group10.controllers.security.HandlerClass;
import com.group10.dao.employee.EmpFunctionsDaoImpl;

@Controller
public class CreateAccount {
	
	
	@ExceptionHandler(HandlerClass.class)
    public String handleResourceNotFoundException() {
        return "redirect:/exception";
    }

	@RequestMapping("/employee/createAccounts")
	public ModelAndView createAccounts(HttpServletRequest request,@RequestParam("userName") String userName,@RequestParam("credit") String credit
			,@RequestParam("savings") String saving,@RequestParam("checking") String checking, RedirectAttributes redir){
		try{
			ModelAndView model = new ModelAndView();
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
			EmpFunctionsDaoImpl edao = ctx.getBean("empFunctionsDaoImpl", EmpFunctionsDaoImpl.class);		
			if(saving.equals("yes")){
				if(edao.createSavingsAccount(userName)){
					redir.addFlashAttribute("error_msg","Created savings account successfully");
				//	model.setViewName("");
				}else
					redir.addFlashAttribute("error_msg","unable to create savings account");
			}
			if(checking.equals("yes"))	{
				if(edao.createCheckingAccount(userName))
					redir.addFlashAttribute("error_msg","Created checking account successfully");
				else
					redir.addFlashAttribute("error_msg","unable to create checking account");
	
			}
			if(checking.equals("yes"))	{
				if(edao.createCreditAccount(userName))
					redir.addFlashAttribute("error_msg","Created credit account successfully");
				else
					redir.addFlashAttribute("error_msg","unable to create credit account");
	
			}
			return model;
			
		}catch(Exception e){
			throw new HandlerClass();
		}
	}
}
	

