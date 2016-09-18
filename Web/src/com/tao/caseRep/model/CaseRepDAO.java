package com.tao.caseRep.model;

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

import com.tao.helen.util.jdbcUtil_ComQuery_CaseRep;
import com.tao.helen.util.jdbcUtil_ComQuery_WtdReq;
import com.tao.util.model.DataSourceHolder;
import com.tao.wtdReq.model.WtdReqVO;

public class CaseRepDAO implements CaseRepDAO_interface {

	private static DataSource ds = DataSourceHolder.getDataSource();

	private static final String INSERT_STMT = "INSERT INTO caserep (crepno,repno,susno,repcaseno,creprsn) VALUES (caserep_seq.NEXTVAL, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT crepno,repno,susno,repcaseno,creprsn FROM caserep order by crepno";
	private static final String GET_ONE_STMT = "SELECT crepno,repno,susno,repcaseno,creprsn FROM caserep where crepno = ?";
	private static final String DELETE = "DELETE FROM caserep where crepno = ?";
	private static final String UPDATE = "UPDATE caserep set repno=?, susno=?, repcaseno=?, creprsn=? where crepno = ?";

	@Override
	public void insert(CaseRepVO caseRepVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, caseRepVO.getRepno());
			pstmt.setInt(2, caseRepVO.getSusno());
			pstmt.setInt(3, caseRepVO.getRepcaseno());
			pstmt.setString(4, caseRepVO.getCreprsn());

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
	public void update(CaseRepVO caseRepVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, caseRepVO.getRepno());
			pstmt.setInt(2, caseRepVO.getSusno());
			pstmt.setInt(3, caseRepVO.getRepcaseno());
			pstmt.setString(4, caseRepVO.getCreprsn());
			pstmt.setInt(5, caseRepVO.getCrepno());

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
	public void delete(Integer crepno) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, crepno);

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
	public CaseRepVO findByPrimaryKey(Integer crepno) {
		CaseRepVO caseRepVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, crepno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo ¤]ºÙ¬° Domain objects
				caseRepVO = new CaseRepVO();
				caseRepVO.setCrepno(rs.getInt("crepno"));
				caseRepVO.setRepno(rs.getInt("repno"));
				caseRepVO.setSusno(rs.getInt("susno"));
				caseRepVO.setRepcaseno(rs.getInt("repcaseno"));
				caseRepVO.setCreprsn(rs.getString("creprsn"));
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
		return caseRepVO;
	}

	@Override
	public List<CaseRepVO> getAll() {
		List<CaseRepVO> list = new ArrayList<CaseRepVO>();
		CaseRepVO caseRepVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				caseRepVO = new CaseRepVO();
				caseRepVO.setCrepno(rs.getInt("crepno"));
				caseRepVO.setRepno(rs.getInt("repno"));
				caseRepVO.setSusno(rs.getInt("susno"));
				caseRepVO.setRepcaseno(rs.getInt("repcaseno"));
				caseRepVO.setCreprsn(rs.getString("creprsn"));
				list.add(caseRepVO);
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
	public List<CaseRepVO> getAll(Map<String, String[]> map) {
		List<CaseRepVO> list = new ArrayList<CaseRepVO>();
		CaseRepVO caseRepVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			con = ds.getConnection();
			String finalSQL = "select * from caseRep "
					+ jdbcUtil_ComQuery_CaseRep.get_WhereCondition(map)
					+ "order by crepno";
			pstmt = con.prepareStatement(finalSQL);
			System.out.println("¡´¡´finalSQL(by DAO) = " + finalSQL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				caseRepVO = new CaseRepVO();
				caseRepVO.setCrepno(rs.getInt("crepno"));
				caseRepVO.setRepno(rs.getInt("repno"));
				caseRepVO.setSusno(rs.getInt("susno"));
				caseRepVO.setRepcaseno(rs.getInt("repcaseno"));
				caseRepVO.setCreprsn(rs.getString("creprsn"));
				list.add(caseRepVO);
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
