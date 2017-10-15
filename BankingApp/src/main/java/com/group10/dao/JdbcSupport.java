package com.group10.dao;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class JdbcSupport extends JdbcDaoSupport {
	public int getDatabases(){
		String sql = "select count(*) from sakila.actor";
		return this.getJdbcTemplate().queryForObject(sql, Integer.class);
	}
}
