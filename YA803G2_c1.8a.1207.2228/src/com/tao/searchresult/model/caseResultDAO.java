package com.tao.searchresult.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.tao.member.controller.LocnoAreaConverter;
import com.tao.util.model.DataSourceHolder;

public class caseResultDAO implements caseResultVO_interface{

	private static DataSource ds = DataSourceHolder.getDataSource();
	
	private static final String FIND_CASE_BY_TITLE_KEY_STMT = 
			"select s.title, s.caseno, s.memno, m.memid, s.casedesc, s.cpno from cases s, caseproduct p, member m "
			+ "where s.cpno=p.cpno and m.memno=s.memno and s.status=1 and s.title like ?";
	
	private static final String FIND_CASE_BY_SUBCATNO_STMT = 
			"select s.title, s.caseno, s.memno, m.memid, s.casedesc, s.cpno from cases s, caseproduct p, member m "
			+ "where s.cpno=p.cpno and m.memno=s.memno and s.status=1 and p.subcatno = ? and s.locno between ? and ?";
	
	
	@Override
	public List<caseResultVO> findCaseByTitleKey(String keyword) {
		List<caseResultVO> list = new ArrayList<caseResultVO>();
		caseResultVO caseresultVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_CASE_BY_TITLE_KEY_STMT);
			pstmt.setString(1, "%" + keyword + "%");
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				caseresultVO = new caseResultVO();
				caseresultVO.setTitle(rs.getString(1));				
				caseresultVO.setCaseno(rs.getInt(2));
				caseresultVO.setMemno(rs.getInt(3));
				caseresultVO.setMemid(rs.getString(4));
				caseresultVO.setCasedesc(rs.getString(5));
				caseresultVO.setCpno(rs.getInt(6));
				list.add(caseresultVO);
				
				
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
	public List<caseResultVO> findCaseBySubCat(Integer subcatno, Integer area) {
		List<caseResultVO> list = new ArrayList<caseResultVO>();
		caseResultVO caseresultVO = null;
		Integer startIndex = 0;
		Integer endIndex = 0;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_CASE_BY_SUBCATNO_STMT);
			pstmt.setInt(1, subcatno);
			
/*			if (area == 1) {
				startIndex = 1;
				endIndex = 95;
			} else if (area == 2) {
				startIndex = 96;
				endIndex = 183;
			} else if (area == 3){
				startIndex = 184;
				endIndex = 313;
			} else {
				startIndex = 314;
				endIndex = 355;
			}*/
			
			if ((area!=null) && (area.toString().length()!=0)) {
				if ((area>=1) && (area<=10)){
					Integer[] fromTo = LocnoAreaConverter.areaToLocno(area);
					startIndex = fromTo[0];
					System.out.println(startIndex);
					endIndex = fromTo[1];
					System.out.println(endIndex);
				} else {
					//not valid input, set to default
					startIndex = 1;
					endIndex = 355;
				}
			} else {
				// didn't input area, set to fefault
				startIndex = 1;
				endIndex = 355;
			}
			
			pstmt.setInt(2, startIndex);
			pstmt.setInt(3, endIndex);
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				caseresultVO = new caseResultVO();
				caseresultVO.setTitle(rs.getString(1));				
				caseresultVO.setCaseno(rs.getInt(2));
				caseresultVO.setMemno(rs.getInt(3));
				caseresultVO.setMemid(rs.getString(4));
				caseresultVO.setCasedesc(rs.getString(5));
				caseresultVO.setCpno(rs.getInt(6));
				list.add(caseresultVO);
				
				
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
