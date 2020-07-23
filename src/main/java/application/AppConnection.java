package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AppConnection {
	
	public static Connection connection;
	public static Statement st;
	
	static {
	    try {
	        connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/", "root", "root");
	        st = connection.createStatement();
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public AppConnection() {
	    try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
