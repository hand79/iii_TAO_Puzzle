package com.tao.hotcasesandshops.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.tao.util.model.DataSourceHolder;

public class HotCaseDAO implements HotCaseDAO_interface {
	private static DataSource ds = DataSourceHolder.getDataSource();
	

	private static final String GET_ONE_BY_CASENO_DESCHITS = "select a.caseno,a.title,a.CPNO,a.SPNO,a.locno,a.status,a.hits,b.pic1,b.pmime1 from cases a,caseproduct b where a.cpno=b.cpno and a.caseno=?";
	private static final String GET_ALL_BY_DESCHITS = "select a.caseno,a.title,a.CPNO,a.SPNO,a.locno,a.status,a.hits from cases a,caseproduct b where a.cpno=b.cpno order by a.hits desc ,a.caseno asc";
	private static final String GET_ALL_BY_STATUS_DESCHITS_CP = "select a.caseno,a.title,a.CPNO,a.SPNO,a.locno,a.status,a.hits from cases a,caseproduct b where a.cpno=b.cpno and a.status=1 order by a.hits desc,a.caseno asc";
	private static final String GET_ALL_BY_STATUS_DESCHITS_SP = "select a.caseno,a.title,a.CPNO,a.SPNO,a.locno,a.status,a.hits from cases a,shopproduct b where a.spno=b.spno and a.status=1 order by a.hits desc,a.caseno asc";
	
	private static final String GET_ALL_BY_STATUS_lOCNO_DESCHITS = "select a.caseno,a.title,a.CPNO,a.SPNO,a.locno,a.status,a.hits from cases a,caseproduct b where a.cpno=b.cpno and a.status=1 and a.locno=? order by a.hits desc,a.caseno asc";
	private static final String Get_ONE_PIC="select b.pic1,b.pmime1 from cases a,caseproduct b where a.cpno=b.cpno and a.caseno = ?";
	@Override
	public HotCaseVO findByCaseno(Integer caseno) {
		// TODO Auto-generated method stub
		HotCaseVO hotCaseVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_CASENO_DESCHITS);
			pstmt.setInt(1, caseno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO ¤]ºÙ¬° Domain objects
				hotCaseVO = new HotCaseVO();
				hotCaseVO.setCaseno(rs.getInt("caseno"));
				hotCaseVO.setTitle(rs.getString("title"));
				hotCaseVO.setCpno(rs.getInt("cpno"));
				hotCaseVO.setSpno(rs.getInt("spno"));
				hotCaseVO.setLocno(rs.getInt("locno"));
				hotCaseVO.setStatus(rs.getInt("status"));
				hotCaseVO.setHits(rs.getInt("hits"));
				hotCaseVO.setPic1(rs.getBytes("pic1"));
				hotCaseVO.setPmime1(rs.getString("pmime1"));
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
		return hotCaseVO;
	}
	
	@Override
	public List<HotCaseVO> getAllByHits() {
		// TODO Auto-generated method stub
		List<HotCaseVO> list = new ArrayList<HotCaseVO>();
		HotCaseVO hotCaseVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_BY_DESCHITS);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO ¤]ºÙ¬° Domain objects
				hotCaseVO = new HotCaseVO();
				hotCaseVO.setCaseno(rs.getInt("caseno"));
				hotCaseVO.setTitle(rs.getString("title"));
				hotCaseVO.setCpno(rs.getInt("cpno"));
				hotCaseVO.setSpno(rs.getInt("spno"));
				hotCaseVO.setLocno(rs.getInt("locno"));
				hotCaseVO.setStatus(rs.getInt("status"));
				hotCaseVO.setHits(rs.getInt("hits"));
			;
				
				list.add(hotCaseVO); // Store the row in the list
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
	public List<HotCaseVO> getAllByHitsStatus() {
		// TODO Auto-generated method stub
		List<HotCaseVO> list = new ArrayList<HotCaseVO>();
		HotCaseVO hotCaseVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_BY_STATUS_DESCHITS_CP);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO ¤]ºÙ¬° Domain objects
				hotCaseVO = new HotCaseVO();
				hotCaseVO.setCaseno(rs.getInt("caseno"));
				hotCaseVO.setTitle(rs.getString("title"));
				hotCaseVO.setCpno(rs.getInt("cpno"));
				hotCaseVO.setSpno(rs.getInt("spno"));
				hotCaseVO.setLocno(rs.getInt("locno"));
				hotCaseVO.setStatus(rs.getInt("status"));
				hotCaseVO.setHits(rs.getInt("hits"));
				list.add(hotCaseVO); // Store the row in the list
			}
			rs.close();
			pstmt.close();
			pstmt =  con.prepareStatement(GET_ALL_BY_STATUS_DESCHITS_SP);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO ¤]ºÙ¬° Domain objects
				hotCaseVO = new HotCaseVO();
				hotCaseVO.setCaseno(rs.getInt("caseno"));
				hotCaseVO.setTitle(rs.getString("title"));
				hotCaseVO.setCpno(rs.getInt("cpno"));
				hotCaseVO.setSpno(rs.getInt("spno"));
				hotCaseVO.setLocno(rs.getInt("locno"));
				hotCaseVO.setStatus(rs.getInt("status"));
				hotCaseVO.setHits(rs.getInt("hits"));
				list.add(hotCaseVO); // Store the row in the list
			}
			Collections.sort(list, new Comparator<HotCaseVO>() {
				@Override
				public int compare(HotCaseVO o1, HotCaseVO o2) {
					return	-1 * o1.getHits().compareTo(o2.getHits());
				}
			});
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
	public List<HotCaseVO> getAllByHitsStatusLocno(Integer locno) {
		// TODO Auto-generated method stub
		List<HotCaseVO> list = new ArrayList<HotCaseVO>();
		HotCaseVO hotCaseVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_BY_STATUS_lOCNO_DESCHITS);
			pstmt.setInt(1, locno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO ¤]ºÙ¬° Domain objects
				hotCaseVO = new HotCaseVO();
				hotCaseVO.setCaseno(rs.getInt("caseno"));
				hotCaseVO.setTitle(rs.getString("title"));
				hotCaseVO.setCpno(rs.getInt("cpno"));
				hotCaseVO.setSpno(rs.getInt("spno"));
				hotCaseVO.setLocno(rs.getInt("locno"));
				hotCaseVO.setStatus(rs.getInt("status"));
				hotCaseVO.setHits(rs.getInt("hits"));
			
				
				list.add(hotCaseVO); // Store the row in the list
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
	public HotCaseVO findByCasenoPic(Integer caseno) {
		// TODO Auto-generated method stub
		HotCaseVO hotCaseVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_CASENO_DESCHITS);
			pstmt.setInt(1, caseno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO ¤]ºÙ¬° Domain objects
				hotCaseVO = new HotCaseVO();
				hotCaseVO.setPic1(rs.getBytes("pic1"));
				hotCaseVO.setPmime1(rs.getString("pmime1"));
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
		return hotCaseVO;
	}

	

}
