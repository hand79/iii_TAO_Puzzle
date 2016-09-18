package com.tao.acc.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.tao.util.model.DataSourceHolder;

public class PermissionDAO implements PermissionDAO_Interface {
	private static DataSource ds = DataSourceHolder.getDataSource();

	private static final String GET_ALL_STMT = "SELECT perno,perdesc FROM permission order by perno";
	private static final String GET_BY_PERNO = "SELECT perno,perdesc FROM permission where perno = ?";
	
	
	@Override
	public PermissionVO getPermissionVOByPerno(Integer perno) {
		// TODO Auto-generated method stub
		PermissionVO permissionVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_PERNO);

			pstmt.setInt(1, perno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				permissionVO = new PermissionVO();
				permissionVO.setPerno(rs.getInt("perno"));
				permissionVO.setPerdesc(rs.getString("perdesc"));
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
		return permissionVO;
	}
	
	
	@Override
	public List<PermissionVO> getAll() {
		// TODO Auto-generated method stub
		List<PermissionVO> list = new ArrayList<PermissionVO>();
		PermissionVO permissionVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				permissionVO = new PermissionVO();
				permissionVO.setPerno(rs.getInt("perno"));
				permissionVO.setPerdesc(rs.getString("perdesc"));

				list.add(permissionVO); // Store the row in the list
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
		return list;
	}

	

	

}
