package com.ravi.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHandlings {

	private static final String URL = "jdbc:mysql://localhost:3306/quizapp";
	private static final String USERNAME = "root";
	private static final String PASWORD = "Raviteja@2001";
	
	public static Connection getConnection() {
		Connection conn = null;
		
		try {
			//MYSQL Driver connection
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USERNAME, PASWORD);
		}catch(ClassNotFoundException e) {
			System.out.println("MySQL jdbc Driver Not Found");
			e.printStackTrace();
		}catch(SQLException e) {
			System.out.println("Connection Failed! check out console.");
			e.printStackTrace();
		}
		return conn;
	}
}
