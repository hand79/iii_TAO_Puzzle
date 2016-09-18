package com.tao.dpsOrd.model;

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

import com.tao.helen.util.jdbcUtil_ComQuery_DpsOrd;
import com.tao.util.model.DataSourceHolder;

public class DpsOrdDAO implements DpsOrdDAO_interface {
	
	private static DataSource ds = DataSourceHolder.getDataSource();
	
	private static final String INSERT_STMT = "INSERT INTO dpsord (dpsordno,dpsordt,dpsamnt,memno,dpshow,ordsts,atmac) VALUES (dpsord_seq.NEXTVAL, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT dpsordno,dpsordt,dpsamnt,memno,dpshow,ordsts,atmac FROM dpsord order by dpsordno";
	private static final String GET_ONE_STMT = "SELECT dpsordno,dpsordt,dpsamnt,memno,dpshow,ordsts,atmac FROM dpsord where dpsordno = ?";
	private static final String DELETE = "DELETE FROM dpsord where dpsordno = ?";
	private static final String UPDATE = "UPDATE dpsord set dpsordt=?, dpsamnt=?, memno=?, dpshow=?, ordsts=?, atmac=? where dpsordno = ?";

	@Override
	public void insert(DpsOrdVO dpsOrdVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setTimestamp(1, dpsOrdVO.getDpsordt());
			pstmt.setDouble(2, dpsOrdVO.getDpsamnt());
			pstmt.setInt(3, dpsOrdVO.getMemno());
			pstmt.setString(4, dpsOrdVO.getDpshow());
			pstmt.setString(5, dpsOrdVO.getOrdsts());
			pstmt.setString(6, dpsOrdVO.getAtmac());
			
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
	public void update(DpsOrdVO dpsOrdVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setTimestamp(1, dpsOrdVO.getDpsordt());
			pstmt.setDouble(2, dpsOrdVO.getDpsamnt());
			pstmt.setInt(3, dpsOrdVO.getMemno());
			pstmt.setString(4, dpsOrdVO.getDpshow());
			pstmt.setString(5, dpsOrdVO.getOrdsts());
			pstmt.setString(6, dpsOrdVO.getAtmac());
			pstmt.setInt(7, dpsOrdVO.getDpsordno());
			
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
	public void delete(Integer dpsordno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, dpsordno);
			
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
	public DpsOrdVO findByPrimaryKey(Integer dpsordno) {
		DpsOrdVO dpsOrdVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, dpsordno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo ¤]ºÙ¬° Domain objects

				dpsOrdVO = new DpsOrdVO();
				dpsOrdVO.setDpsordno(rs.getInt("dpsordno"));
				dpsOrdVO.setDpsordt(rs.getTimestamp("dpsordt"));
				dpsOrdVO.setDpsamnt(rs.getDouble("dpsamnt"));
				dpsOrdVO.setMemno(rs.getInt("memno"));
				dpsOrdVO.setDpshow(rs.getString("dpshow"));
				dpsOrdVO.setOrdsts(rs.getString("ordsts"));
				dpsOrdVO.setAtmac(rs.getString("atmac"));
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
		return dpsOrdVO;
	}

	@Override
	public List<DpsOrdVO> getAll() {
		List<DpsOrdVO> list = new ArrayList<DpsOrdVO>();
		DpsOrdVO dpsOrdVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				dpsOrdVO = new DpsOrdVO();
				dpsOrdVO.setDpsordno(rs.getInt("dpsordno"));
				dpsOrdVO.setDpsordt(rs.getTimestamp("dpsordt"));
				dpsOrdVO.setDpsamnt(rs.getDouble("dpsamnt"));
				dpsOrdVO.setMemno(rs.getInt("memno"));
				dpsOrdVO.setDpshow(rs.getString("dpshow"));
				dpsOrdVO.setOrdsts(rs.getString("ordsts"));
				dpsOrdVO.setAtmac(rs.getString("atmac"));
				list.add(dpsOrdVO);
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
	public List<DpsOrdVO> getAll(Map<String, String[]> map) {
		List<DpsOrdVO> list = new ArrayList<DpsOrdVO>();
		DpsOrdVO dpsOrdVO = null;
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			con = ds.getConnection();
			String finalSQL = "select * from dpsord "
			          + jdbcUtil_ComQuery_DpsOrd.get_WhereCondition(map)
			          + "order by dpsordt";
			pstmt = con.prepareStatement(finalSQL);
			System.out.println("¡´¡´finalSQL(by DAO) = "+finalSQL);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				dpsOrdVO = new DpsOrdVO();
				dpsOrdVO.setDpsordno(rs.getInt("dpsordno"));
				dpsOrdVO.setDpsordt(rs.getTimestamp("dpsordt"));
				dpsOrdVO.setMemno(rs.getInt("memno"));
				dpsOrdVO.setDpsamnt(rs.getDouble("dpsamnt"));
				dpsOrdVO.setDpshow(rs.getString("dpshow"));
				dpsOrdVO.setOrdsts(rs.getString("ordsts"));
				dpsOrdVO.setAtmac(rs.getString("atmac"));
				list.add(dpsOrdVO); // Store the row in the List
			
		}
		}catch(SQLException se){
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			
		}finally{
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
			}if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
			
		}return list;
		
	}

}
