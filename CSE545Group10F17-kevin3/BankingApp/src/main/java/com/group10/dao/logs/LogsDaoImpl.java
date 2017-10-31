package com.group10.dao.logs;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.group10.dbmodels.DbLogs;

public class LogsDaoImpl extends JdbcDaoSupport {

	public List<DbLogs> getLogsForUserById(int userId, String type) {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM logs WHERE userId ='" + userId+"', and logtype='"+type+"'";
		return this.getJdbcTemplate().query(query, new BeanPropertyRowMapper<DbLogs>(DbLogs.class));
	}

	public List<DbLogs> getLogs(String type) {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM logs where logtype='"+type+"' ORDER BY timestamp DESC LIMIT 25";
		return this.getJdbcTemplate().query(query, new BeanPropertyRowMapper<DbLogs>(DbLogs.class));
	}

	public void saveLogs(String activity, String details, int userid, String type){
		String query = "INSERT INTO logs (activity, userid, details, stamp, logtype) values (?,?,?, NOW(),?)";
		 this.getJdbcTemplate().update(query, new Object[]{activity, userid, details,type}); 
	}

	public  List<DbLogs> getAllLogs() {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM logs";
		return this.getJdbcTemplate().query(query, new BeanPropertyRowMapper<DbLogs>(DbLogs.class));
	}
	
}