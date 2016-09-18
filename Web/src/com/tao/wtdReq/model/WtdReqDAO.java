package com.tao.wtdReq.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.tao.helen.util.jdbcUtil_ComQuery_WtdReq;
import com.tao.util.model.DataSourceHolder;

public class WtdReqDAO implements WtdReqDAO_interface {

	private static DataSource ds = DataSourceHolder.getDataSource();

	private static final String INSERT_STMT = "INSERT INTO wtdreq (wtdreqno,wtdreqt,memno,wtdamnt,wtdac,reqsts) VALUES (wtdreq_seq.NEXTVAL, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT wtdreqno,wtdreqt,memno,wtdamnt,wtdac,reqsts FROM wtdreq order by wtdreqno";
	private static final String GET_ONE_STMT = "SELECT wtdreqno,wtdreqt,memno,wtdamnt,wtdac,reqsts FROM wtdreq where wtdreqno = ?";
	private static final String DELETE = "DELETE FROM wtdreq where wtdreqno = ?";
	private static final String UPDATE = "UPDATE wtdreq set wtdreqt=?, memno=?, wtdamnt=?, wtdac=?, reqsts=? where wtdreqno = ?";

	@Override
	public void insert(WtdReqVO wtdReqVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setTimestamp(1, wtdReqVO.getWtdreqt());
			pstmt.setInt(2, wtdReqVO.getMemno());
			pstmt.setDouble(3, wtdReqVO.getWtdamnt());
			pstmt.setString(4, wtdReqVO.getWtdac());
			pstmt.setString(5, wtdReqVO.getReqsts());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void update(WtdReqVO wtdReqVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setTimestamp(1, wtdReqVO.getWtdreqt());
			pstmt.setInt(2, wtdReqVO.getMemno());
			pstmt.setDouble(3, wtdReqVO.getWtdamnt());
			pstmt.setString(4, wtdReqVO.getWtdac());
			pstmt.setString(5, wtdReqVO.getReqsts());
			pstmt.setInt(6, wtdReqVO.getWtdreqno());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void delete(Integer wtdreqno) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, wtdreqno);

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public WtdReqVO findByPrimaryKey(Integer wtdreqno) {
		WtdReqVO wtdReqVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, wtdreqno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo ¤]ºÙ¬° Domain objects
				wtdReqVO = new WtdReqVO();
				wtdReqVO.setWtdreqno(rs.getInt("wtdreqno"));
				wtdReqVO.setWtdreqt(rs.getTimestamp("wtdreqt"));
				wtdReqVO.setMemno(rs.getInt("memno"));
				wtdReqVO.setWtdamnt(rs.getDouble("wtdamnt"));
				wtdReqVO.setWtdac(rs.getString("wtdac"));
				wtdReqVO.setReqsts(rs.getString("reqsts"));

			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return wtdReqVO;
	}

	@Override
	public List<WtdReqVO> getAll() {
		List<WtdReqVO> list = new ArrayList<WtdReqVO>();
		WtdReqVO wtdReqVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				wtdReqVO = new WtdReqVO();
				wtdReqVO.setWtdreqno(rs.getInt("wtdreqno"));
				wtdReqVO.setWtdreqt(rs.getTimestamp("wtdreqt"));
				wtdReqVO.setMemno(rs.getInt("memno"));
				wtdReqVO.setWtdamnt(rs.getDouble("wtdamnt"));
				wtdReqVO.setWtdac(rs.getString("wtdac"));
				wtdReqVO.setReqsts(rs.getString("reqsts"));
				list.add(wtdReqVO);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public List<WtdReqVO> getAll(Map<String, String[]> map) {

		List<WtdReqVO> list = new ArrayList<WtdReqVO>();
		WtdReqVO wtdReqVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			String finalSQL = "select * from wtdreq "
					+ jdbcUtil_ComQuery_WtdReq.get_WhereCondition(map)
					+ "order by wtdreqno";
			pstmt = con.prepareStatement(finalSQL);
			System.out.println("¡´¡´finalSQL(by DAO) = " + finalSQL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				wtdReqVO = new WtdReqVO();
				wtdReqVO.setWtdreqno(rs.getInt("wtdreqno"));
				wtdReqVO.setWtdreqt(rs.getTimestamp("wtdreqt"));
				wtdReqVO.setWtdamnt(rs.getDouble("wtdamnt"));
				wtdReqVO.setMemno(rs.getInt("memno"));
				wtdReqVO.setWtdac(rs.getString("wtdac"));
				wtdReqVO.setReqsts(rs.getString("reqsts"));
				list.add(wtdReqVO);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured."
					+ se.getMessage());
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
