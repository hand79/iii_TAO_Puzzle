package com.tao.location.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.tao.util.model.DataSourceHolder;

public class LocationDAO implements LocationDAO_interface {

	private static DataSource ds = DataSourceHolder.getDataSource();
	
	private static final String GET_ONE_STMT = 
			"SELECT locno, county, town FROM location where locno = ?";
	
	private static final String GET_ALL_STMT = 
			"SELECT locno, county, town FROM location order by locno";
	
	
	private static final String GET_ALL_UNIQ_COUNTY_STMT = 
			"SELECT county FROM location order by locno";

	private static final String GET_MATCHED_TOWN_STMT = 
			"SELECT locno, town FROM location where locno between ? and ?";
	
	@Override
	public LocationVO findByPrimaryKey(Integer locno) {
		LocationVO locationVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setInt(1, locno);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				locationVO = new LocationVO();
				locationVO.setLocno(rs.getInt("locno"));
				locationVO.setCounty(rs.getString("county"));
				locationVO.setTown(rs.getString("town"));
			}

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
		return locationVO;
	}

	@Override
	public List<LocationVO> getAll() {
		List<LocationVO> list = new ArrayList<LocationVO>();
		LocationVO locationVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				locationVO = new LocationVO();
				
				locationVO.setLocno(rs.getInt("locno")); 
				locationVO.setCounty(rs.getString("county"));
				locationVO.setTown(rs.getString("town"));
				list.add(locationVO);
				
			}
			// Handle any SQL errors
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
	public List<String[]> getUniqCounty() {
		List<String[]> list = new ArrayList<String[]>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String countyCurrent = null;
		String countyLast = null;
		int cStart = 1;
		int cEnd = 0;
		int index = 0;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_UNIQ_COUNTY_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {				
				countyCurrent = rs.getString("county");
				index++;
				if (countyLast == null){
//					list.add(countyCurrent);
					cStart = index;
				} else {
					if (!countyCurrent.equals(countyLast)){
//						list.add(countyCurrent);
						cEnd = index-1;
						String[] item = {countyLast,  Integer.toString(cStart), Integer.toString(cEnd)};
						list.add(item);
						cStart = index;
					}
				}				
				countyLast = countyCurrent; 
			}
			String[] item = {countyLast,  Integer.toString(cStart), Integer.toString(index)};
			list.add(item);
			
			// Handle any SQL errors
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
	public List<String[]> getMatchedTowns(String countyRange) {
		List<String[]> list = new ArrayList<String[]>();
//		LocationVO locationVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MATCHED_TOWN_STMT);
			
			String[] ranger = countyRange.split("-"); 
			pstmt.setInt(1, Integer.parseInt(ranger[0]));
			pstmt.setInt(2, Integer.parseInt(ranger[1]));
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
//				locationVO = new LocationVO();
//				
//				locationVO.setLocno(rs.getInt("locno")); 
//				locationVO.setTown(rs.getString("town"));
//				list.add(locationVO);
				
				String[] townInfo = {Integer.toString(rs.getInt("locno")), rs.getString("town")};
				list.add(townInfo);
				
			}
			// Handle any SQL errors
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
