package com.group10.dao.logs;
import java.util.List;

import com.group10.dbmodels.DbLogs;

public interface LogsDao {
	public List<DbLogs> getUserById(int userId, String type);
	public List<DbLogs> getLogs(String type);
}
