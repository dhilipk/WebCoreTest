package com.beacon.framework.dao;

import java.sql.Connection;

public class SessionManager {

	private static ThreadLocal<Connection> connections = new ThreadLocal<Connection>();

	public static Connection getCurrentConnection() {
		return connections.get();
	}

	public static void setConnection(Connection connection) {
		SessionManager.connections.set(connection);
	}
	
}
