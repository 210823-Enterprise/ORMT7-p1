package com.team7.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConnectionUtil {
	
	//TODO create logger here

	private static Connection conn = null;

	// we want a private constructor
	private ConnectionUtil() {
		super();
	}

	//static to persist the connection
	public static Connection getConnection() {
		try {
			if (conn != null && !conn.isClosed()) {
				//log.info("returned connection");
				return conn;
			}
		} catch (SQLException e) {
			//log.error("failed to reuse a connection");
			return null;
		}
		
		Properties prop = new Properties();
		
		String url = "";
		String username = "";
		String password = "";
		
		try {
			prop.load(new FileReader("target/classes/new.properties"));
			url = prop.getProperty("url");
			username = prop.getProperty("username");
			password = prop.getProperty("password");
			conn = DriverManager.getConnection(url, username, password);
			
			//log.info("db connection established");
		} catch (IOException e) {
			//log.error("cannot locate application.properties file");
			e.printStackTrace();
		} catch (SQLException e) {
			//log.error("cannot establish database connection");
			return null;
		}
		return conn;
	}

}