package edu.fatec.alunos.control.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
	private static ConnectDB instance;
	private Connection conn;
	private String url = "jdbc:mysql://localhost/alunos";
	private String user = "admin";
	private String psswrd = "";

	private ConnectDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static ConnectDB getInstance() {
		if (instance == null) {
			instance = new ConnectDB();
		}
		return instance;
	}

	public Connection getConnection() throws SQLException {
		if (conn == null || conn.isClosed()) {
			conn = DriverManager.getConnection(url, user, psswrd);
		}
		return conn;
	}

}
