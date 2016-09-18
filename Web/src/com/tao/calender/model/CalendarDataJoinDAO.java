package com.tao.calender.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;



import com.tao.util.model.DataSourceHolder;

public class CalendarDataJoinDAO implements CalendarDataJoin_interface {
	private static DataSource ds = DataSourceHolder.getDataSource();
	private final static String CASE_JOIN_WISHLIST="select A.CASENO,A.TITLE,TO_CHAR(A.ETIME,'yyyy-mm-dd hh24:mi:ss')as ETIME from cases a,wishlist b where a.caseno=b.caseno and (A.STATUS=1 or A.STATUS=2) and b.MEMNO=?";
	private final static String CASE_JOIN_ORDERS="select A.CASENO,A.TITLE,TO_CHAR(A.ETIME,'yyyy-mm-dd hh24:mi:ss')as ETIME from cases a,orders c where a.caseno =c.caseno and c.status=0 and C.BMEMNO=?";
	@Override
	public List<CalendarCaseJoinVO> getAllByCaseJoinWishList(Integer memno) {
		// TODO Auto-generated method stub
		List<CalendarCaseJoinVO> list = new ArrayList<CalendarCaseJoinVO>();
		CalendarCaseJoinVO calendarCaseJoinVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(CASE_JOIN_WISHLIST);
			pstmt.setInt(1, memno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				calendarCaseJoinVO = new CalendarCaseJoinVO();
				calendarCaseJoinVO.setCaseno(rs.getInt("caseno"));
				calendarCaseJoinVO.setEtime(rs.getString("etime"));
				calendarCaseJoinVO.setTitle(rs.getString("title"));
				list.add(calendarCaseJoinVO); // Store the row in the list
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
	public List<CalendarCaseJoinVO> getAllByCaseJoinOrders(Integer memno) {
		// TODO Auto-generated method stub
		List<CalendarCaseJoinVO> list = new ArrayList<CalendarCaseJoinVO>();
		CalendarCaseJoinVO calendarCaseJoinVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(CASE_JOIN_ORDERS);
			pstmt.setInt(1, memno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				calendarCaseJoinVO = new CalendarCaseJoinVO();
				calendarCaseJoinVO.setCaseno(rs.getInt("caseno"));
				calendarCaseJoinVO.setEtime(rs.getString("etime"));
				calendarCaseJoinVO.setTitle(rs.getString("title"));
				list.add(calendarCaseJoinVO); // Store the row in the list
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
