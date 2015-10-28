package com.beacon.framework.driver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcQuery {
	
	private DBManager dbManager = new DBManager();
	
	public void executeQuery(String sql){
		Connection c = dbManager.getConnection();
		Statement stmt;
		try {
			stmt = c.createStatement();

			ResultSet rs = stmt.executeQuery(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public synchronized void update(String expression) {
        Statement st = null;
        try {
			st = dbManager.getConnection().createStatement();
	        int i = st.executeUpdate(expression);
	        if (i == -1) {
	            System.out.println("db error : " + expression);
	        }
	        st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }  
	public static void main(String[] args) {
		JdbcQuery j = new JdbcQuery();
		j.update(
                "CREATE TABLE sample_table ( id INTEGER IDENTITY, str_col VARCHAR(256), num_col INTEGER)");
		j.executeQuery("select * from sample_table");
	}
}
