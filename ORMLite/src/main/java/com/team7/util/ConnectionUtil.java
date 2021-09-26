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
	private static Logger log = Logger.getLogger(ConnectionUtil.class);
	private static Connection conn = null;

	// we want a private constructor
	private ConnectionUtil() {
		log.info("Creating constructor.");
		super();
	}

	//static to persist the connection
	public static Connection getConnection() {
		try {
			log.info("Attempting to connect.");
			if (conn != null && !conn.isClosed()) {
				log.info("returned connection");
				return conn;
			}
		} catch (SQLException e) {
			log.error("failed to reuse a connection");
			return null;
		}
		log.info("Creating properties.");
		Properties prop = new Properties();
		
		String url = "";
		String username = "";
		String password = "";
		
		try {
			log.info("Attempting to load properties file.");
			prop.load(new FileReader("\\src\\main\\resources\\new.properties"));
			log.info("Getting url.");
			url = prop.getProperty("url");
			log.info("Getting username.");
			username = prop.getProperty("username");
			log.info("Getting password.");
			password = prop.getProperty("password");
			log.info("Connectiong to driver.");
			conn = DriverManager.getConnection(url, username, password);
			log.info("db connection established");
		} catch (IOException e) {
			System.out.println("CAN't FIND FILE");
			log.error("cannot locate application.properties file");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQL EXCPEITON");
			log.error("cannot establish database connection");
			return null;
		}
		log.info("Exiting getConnection method.");
		return conn;
	}
	
	public static void main(String[] args) {
		
		Connection conn = ConnectionUtil.getConnection();
	}

}
