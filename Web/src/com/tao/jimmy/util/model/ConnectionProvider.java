package com.tao.jimmy.util.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.tao.util.model.DataSourceHolder;

public class ConnectionProvider {

//	static {
//		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//	}

	public static Connection getJNDIConnection() throws SQLException {
		DataSource ds = DataSourceHolder.getDataSource();
		if (ds != null) {
			Connection conn = ds.getConnection();
			return conn;
		} else {
			throw new SQLException("Could not find Data Source");
		}
	}

//	public static Connection getJDBCConnection() throws SQLException {
//		Connection conn = DriverManager.getConnection(
//				"jdbc:oracle:thin@localhost:1521:xe", "YA803G2", "YA803G2");
//		return conn;
//	}

	public static Connection getConnection() throws SQLException {
		return getJNDIConnection();
	}
}
