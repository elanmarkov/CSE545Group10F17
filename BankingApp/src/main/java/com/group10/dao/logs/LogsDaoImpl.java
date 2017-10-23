package com.group10.dao.logs;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.group10.dbmodels.DbLogs;

public class LogsDaoImpl extends JdbcDaoSupport implements LogsDao {

	public List<DbLogs> getUserById(int userId, String type) {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM " + type + "_log WHERE userId = " + userId;
		return this.getJdbcTemplate().query(query, new BeanPropertyRowMapper<DbLogs>(DbLogs.class));
	}

	public List<DbLogs> getLogs(String type) {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM " + type + "_log ORDER BY timestamp DESC LIMIT 25";
		return this.getJdbcTemplate().query(query, new BeanPropertyRowMapper<DbLogs>(DbLogs.class));
	}

	public Boolean saveLogs(DbLogs log, String table){
		String query = "INSERT INTO " + table + "_log (activity, userid, details, timestamp) values (?,?,?,?)";
		return this.getJdbcTemplate().update(query, new Object[]{log.getActivity(), log.getUserid(), log.getDetails(), log.getTimestamp()})==1?true:false; 
	}
	
	public void saveLogs(String activity, String details, int userid, String table){
		String query = "INSERT INTO " + table + "_log (activity, userid, details, timestamp) values (?,?,?, NOW())";
		 this.getJdbcTemplate().update(query, new Object[]{activity, userid, details }); 
	}
	
	public int number(){
		String sql="select count(*) from sakila.actor";
		return this.getJdbcTemplate().queryForObject(sql, Integer.class);
	}
}
