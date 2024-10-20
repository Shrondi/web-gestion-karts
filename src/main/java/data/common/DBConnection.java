package data.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A class to manage the MySQL connection (general methods and configuration).
 * */

public class DBConnection {

	protected Connection connection = null;

	public Connection getConnection(){
	    
		try {
			 final String url = System.getenv("DB_URL");
			 final String user = System.getenv("DB_USER");
			 final String password = System.getenv("DB_PASSWORD");
			
			Class.forName("com.mysql.jdbc.Driver");
			this.connection = (Connection) DriverManager.getConnection(url, user, password);
			System.err.println("Database connection successfully opened!");
			
		}catch (SQLException e) {
			System.err.println("Connection to MySQL has failed!");
			e.printStackTrace();
			
		} catch (ClassNotFoundException e) {
			System.err.println("JDBC Driver not found.");
			e.printStackTrace();
		}
		
		return this.connection;
	}

	public void closeConnection() {
		try {
			if(this.connection != null && !this.connection.isClosed()) {
				this.connection.close();
				System.err.println("Database connection successfully closed!");
			}
		} catch (SQLException e) {
			System.err.println("Error while trying to close the connection.");
			e.printStackTrace();
		}
	}
}
