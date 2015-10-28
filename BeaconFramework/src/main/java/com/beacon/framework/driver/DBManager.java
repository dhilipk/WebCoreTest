package com.beacon.framework.driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.beacon.framework.config.Config;

public class DBManager {
	
	private static final String connectionUrl = Config.CONNECTION_URL.getValue();
	
	private static final String dbUserName = Config.DB_USERNAME.getValue();
	
	private static final String dbPassword = Config.DB_PASSWORD.getValue();
	
	public Connection openConnection(){
		Connection connection = null;
		try {
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			Class.forName("org.hsqldb.jdbcDriver");
			connection = DriverManager.getConnection(connectionUrl, dbUserName, dbPassword);
			SessionManager.setConnection(connection);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public Connection getConnection(){
		if(SessionManager.getCurrentConnection() != null){
			return SessionManager.getCurrentConnection();
		}
		return openConnection();
	}

	public void closeConnection(){
		Connection connection = SessionManager.getCurrentConnection();
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
