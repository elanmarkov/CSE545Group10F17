package com.group10.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.group10.dao.logs.LogsDaoImpl;
import com.group10.dbmodels.DbLogs;

@Controller
public class HelloController {

	@RequestMapping(value = "/hellopage", method =RequestMethod.POST)
	public ModelAndView mymethod(HttpServletRequest request){
		
		ModelAndView model = new ModelAndView();
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("DaoDetails.xml");
		LogsDaoImpl ldao= ctx.getBean("logsDaoImpl",LogsDaoImpl.class);
		List<DbLogs> loglist = ldao.getAllLogs();
		model.addObject("loglist", loglist);
		model.setViewName("hellopage");
		return model;
		
	}
}
