package com.app.banking_api.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnection {
	
	private static Connection connection;

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/banking?serverTimezone=PST";
		String username = "root";
		String password = "25349543";
			connection = DriverManager.getConnection(url, username, password);
			return connection;
	}
}
	//SingleTon Java Class

