package com.tao.util.model;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataSourceHolder {
	private static DataSource ds;
	static{
		try {
			Context ctx = new javax.naming.InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	public static DataSource getDataSource() {
		return ds;
	}
	
}
