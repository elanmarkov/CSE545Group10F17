package com.group10.jdbc;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.group10.dao.JdbcSupport;

public class test {
	public static void main(String[] args) {
			
		ApplicationContext  ctx = new ClassPathXmlApplicationContext("DaoDetails.xml"); 
	    JdbcSupport sdao = ctx.getBean("jdbcSupport" , JdbcSupport.class);
		System.out.println(sdao.getDatabases());
			
		}
}
