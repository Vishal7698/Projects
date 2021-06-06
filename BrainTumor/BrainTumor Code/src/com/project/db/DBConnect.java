package com.project.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect {

	
	static Connection connection;
	private static String DBNAME="brain_db";
	private static String DBUsername="root";
	private static String DBPassword="root";
	
	private static String dbUrl = "jdbc:mysql://localhost:3306/"+DBNAME;
	
	
	public static Connection getConnection() {
		// TODO Auto-generated method stub
		try
		{
			
			Class.forName("com.mysql.jdbc.Driver");
			connection=DriverManager.getConnection(dbUrl,DBUsername,DBPassword);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return connection;
	}
	
}

