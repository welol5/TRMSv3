package dev.elliman.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnectionUtil {
	private static DBConnectionUtil cu = null;
	private static Properties properties;
	
	private DBConnectionUtil() {
		properties = new Properties();
		
		try {
			InputStream dbProperties = DBConnectionUtil.class.getClassLoader().getResourceAsStream("database.properties");
			properties.load(dbProperties);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static synchronized DBConnectionUtil getDBConnectionUtil() {
		if(cu == null) {
			cu = new DBConnectionUtil();
			return cu;
		} else {
			return cu;
		}
	}
	
	public Connection getConnection() {
		Connection conn = null;
		
		try {
			Class.forName(properties.getProperty("drv"));
			conn = DriverManager.getConnection(
						properties.getProperty("url"),
						properties.getProperty("username"),
						properties.getProperty("password")
					);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
}
