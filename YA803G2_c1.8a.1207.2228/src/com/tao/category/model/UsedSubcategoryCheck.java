package com.tao.category.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.tao.util.model.DataSourceHolder;

public class UsedSubcategoryCheck  {
	private static DataSource ds = DataSourceHolder.getDataSource();

	private static final String SELECT_ALL_BY_CASE_PRODUCT = "SELECT cpno,subcatno FROM caseProduct where subcatno=? ";
	private static final String SELECT_ALL_BY_SHOP_PRODUCT = "SELECT spno,subcatno FROM shopproduct where subcatno=? ";

	
	public static boolean CheckByCase(Integer subcatno) {
		// TODO Auto-generated method stub
		int count = 0;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(SELECT_ALL_BY_CASE_PRODUCT);
			pstmt.setInt(1, subcatno);
			
			rs = pstmt.executeQuery();
	
			while (rs.next()) {

				count++;
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		if (count > 0) {
			return true;
		} else {

			return false;
		}
	}
	
	public static boolean CheckByShop(Integer subcatno) {
		// TODO Auto-generated method stub
		int count = 0;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(SELECT_ALL_BY_SHOP_PRODUCT);
			pstmt.setInt(1, subcatno);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {

				count++;
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		if (count > 0) {
			return true;
		} else {

			return false;
		}
	}
}
