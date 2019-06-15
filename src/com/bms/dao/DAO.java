package com.bms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {
	
	private static String url = "jdbc:sqlserver://localhost:1433;DatabaseName=bookManageSystem";
	private static String className = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static String username = "sa";
	private static String password = "Jian031018";
	public static Connection connection = null;
	
	static {
		try {
			Class.forName(className); //加载数据库驱动
			connection = DriverManager.getConnection(url, username, password);//获取数据库连接
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		return connection;
	}
	

}
