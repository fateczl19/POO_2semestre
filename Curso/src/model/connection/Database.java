package model.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	
	public static Connection getConnection() throws SQLException{
		Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/Curso", "SA", "");
		return con;
	}
	
}