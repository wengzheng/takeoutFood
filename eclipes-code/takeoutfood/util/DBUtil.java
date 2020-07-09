package cn.edu.zucc.takeoutfood.util;

import java.sql.Connection;

public class DBUtil {
	private static final String jdbcUrl="jdbc:mysql://localhost:3306/takeoutfood";
	private static final String dbUser="root";
	private static final String dbPwd="oracle";
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Connection getConnection() throws java.sql.SQLException{
		Connection conn= java.sql.DriverManager.getConnection(jdbcUrl, dbUser, dbPwd);
		System.out.println(conn);
		return conn;
	}
}
