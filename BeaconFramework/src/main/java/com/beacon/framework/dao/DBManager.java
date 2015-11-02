package com.beacon.framework.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beacon.framework.config.Config;

public class DBManager {
	
	private static final Logger LOG = LoggerFactory.getLogger(DBManager.class);

	private static ThreadLocal<Connection> connections = new ThreadLocal<Connection>();

	private static final String connectionUrl = Config.CONNECTION_URL.getValue();
	
	private static final String dbUserName = Config.DB_USERNAME.getValue();
	
	private static final String dbPassword = Config.DB_PASSWORD.getValue();
	
	private static final String driverName = Config.DB_DRIVER_NAME.getValue();
	
	static{
		LOG.debug("Trying to Access the Driver : " + driverName);
		try{
			Class.forName(driverName);
		}catch (ClassNotFoundException e) {
			LOG.error("Driver Not loaded " + driverName);
			e.printStackTrace();
		}
	}
	
	public Connection openConnection(){
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(connectionUrl, dbUserName, dbPassword);
			setConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public Connection getConnection(){
		if(getCurrentConnection() != null){
			return getCurrentConnection();
		}
		return openConnection();
	}

	public void closeConnection(){
		Connection connection = getCurrentConnection();
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	public static Connection getCurrentConnection() {
		return connections.get();
	}

	public void setConnection(Connection connection) {
		connections.set(connection);
	}
}
