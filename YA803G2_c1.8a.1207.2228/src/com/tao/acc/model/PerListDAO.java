package com.tao.acc.model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.tao.util.model.DataSourceHolder;

public class PerListDAO implements PerListDAO_interface{
	private static DataSource ds = DataSourceHolder.getDataSource();
	private static final String INSERT_STMT = 	"INSERT INTO Perlist (acc,perno) VALUES (?,?)";
	private static final String GET_ALL_STMT = "SELECT acc,perno FROM perlist order by acc";
	private static final String GET_ONE_ACC_ONE_PERMISSION = "SELECT acc,perno FROM perlist where acc = ? and perno = ?";
	private static final String GET_ONE_ACC_ALL_PERMISSION_BY_ACC = "SELECT acc,perno FROM perlist where acc = ?";
	private static final String GET_ONE_PERMISSION_ALL_ACC_BY_PERNO = "SELECT acc,perno FROM perlist where perno = ?";
	private static final String DELETE = "DELETE FROM Perlist where perno = ? and acc = ?";
		
	@Override
	public void insert(PerListVO perListVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, perListVO.getAcc());
			pstmt.setInt(2, perListVO.getPerno());
			
			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
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
	}
	
	@Override
	public void delete(PerListVO perListVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, perListVO.getPerno());
			pstmt.setString(2,perListVO.getAcc());
			
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
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
	}
	

	
	@Override
	public PerListVO getOnePerListVOByPernoACC(Integer perno, String acc) {
		// TODO Auto-generated method stub
		PerListVO perListVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_ACC_ONE_PERMISSION);
			pstmt.setString(1,acc);
			pstmt.setInt(2, perno);
			

			rs = pstmt.executeQuery();

			while (rs.next()) {
			
				perListVO = new PerListVO();
				perListVO.setPerno(rs.getInt("perno"));
				perListVO.setAcc(rs.getString("acc"));
				
			}

			// Handle any driver errors
		} catch (SQLException se) {
			se.printStackTrace();
//			throw new RuntimeException("A database error occured. "
//					+ se.getMessage());
//			// Clean up JDBC resources
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
		return perListVO;
	}
	
	
	@Override
	public List<PerListVO> getAll() {
		// TODO Auto-generated method stub
		List<PerListVO> list = new ArrayList<PerListVO>();
		PerListVO perListVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO �]�٬� Domain objects
				perListVO = new PerListVO();
				perListVO.setPerno(rs.getInt("perno"));
				perListVO.setAcc(rs.getString("acc"));
				
				list.add(perListVO); // Store the row in the list
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

	@Override
	public Set<PerListVO> getAllOneAccAllPermissionByACC(String acc) {
		// TODO Auto-generated method stub
		Set<PerListVO> set = new LinkedHashSet<PerListVO>();
		PerListVO perListVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_ACC_ALL_PERMISSION_BY_ACC);
			pstmt.setString(1, acc);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				perListVO = new PerListVO();
				perListVO.setPerno(rs.getInt("perno"));
				perListVO.setAcc(rs.getString("acc"));
				
				set.add(perListVO); 
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
		return set;
	}

	@Override
	public Set<PerListVO>  getAllOnePernoAllAccByPerno(Integer perno) {
		// TODO Auto-generated method stub
		Set<PerListVO> set = new LinkedHashSet<PerListVO>();
		PerListVO perListVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_PERMISSION_ALL_ACC_BY_PERNO);
			pstmt.setInt(1, perno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				perListVO = new PerListVO();
				perListVO.setPerno(rs.getInt("perno"));
				perListVO.setAcc(rs.getString("acc"));
				
				set.add(perListVO); 
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
		return set;
	}

	

	




	
	
	

}
