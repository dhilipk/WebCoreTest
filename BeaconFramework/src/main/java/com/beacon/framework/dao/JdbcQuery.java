package com.beacon.framework.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.beacon.framework.config.SkipException;

public class JdbcQuery {
	
	private DBManager dbManager = new DBManager();
	
	public ResultSet executeQuery(String sql) {
		Connection c = dbManager.getConnection();
		Statement stmt;
		ResultSet rs = null;
		try {
			stmt = c.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SkipException("SQL Query is wrong");
		}
		return rs;
	}
	
	public Boolean isRowPresent(String sql){
		Boolean rowElement = false;
		ResultSet rs;
		try {
			rs = executeQuery(sql);
			
			if(rs.next()){
				rowElement = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SkipException("Row Element Accessable Exception");
		}
		return rowElement;
	}
	public synchronized void update(String sql) {
        Statement st = null;
        try {
			st = dbManager.getConnection().createStatement();
	        int i = st.executeUpdate(sql);
	        if (i == -1) {
	            System.out.println("db error : " + sql);
	        }
	        st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }  
	
}
