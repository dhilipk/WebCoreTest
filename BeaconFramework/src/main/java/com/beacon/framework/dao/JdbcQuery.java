package com.beacon.framework.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.beacon.framework.config.SkipException;

public class JdbcQuery {
	
	private static final Logger LOG = LoggerFactory.getLogger(JdbcQuery.class);
	
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
	
	public Boolean update(String sql) {
        Statement st = null;
        try {
			st = dbManager.getConnection().createStatement();
	        int i = st.executeUpdate(sql);
	        if (i == -1) {
	        	LOG.warn("db error : " + sql);
	            return false;
	        }
	        st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return true;
    }
	
	public void assertEquals(String sql, List<String> expected){
		List<String> actuals = new ArrayList<String>();
		ResultSet rs = executeQuery(sql);
		try{
			while(rs.next()){
				actuals.add(rs.getString(1));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		Assert.assertEquals(actuals, expected);
	}
	
	public void assertEquals(String sql, String expected){
		assertEquals(sql, Arrays.asList(expected));
	}
	
	public static void main(String[] args) {
		JdbcQuery j = new JdbcQuery();
		j.update("CREATE TABLE EMPLOYEE ( id INTEGER IDENTITY, name VARCHAR(256), address VARCHAR(256))");
		j.update("INSERT INTO EMPLOYEE(id,name,address) values(20, 'Dhilip', 'Chennai')");
		j.update("INSERT INTO EMPLOYEE(id,name,address) values(21, 'Kumar', 'Chennai')");
		j.assertEquals("select name from EMPLOYEE", Arrays.asList("Dhilip", "Kumar"));
	}
	
}
