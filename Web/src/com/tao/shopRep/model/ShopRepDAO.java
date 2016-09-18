package com.tao.shopRep.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.tao.helen.util.jdbcUtil_ComQuery_ShopRep;
import com.tao.helen.util.jdbcUtil_ComQuery_WtdReq;
import com.tao.util.model.DataSourceHolder;
import com.tao.wtdReq.model.WtdReqVO;

public class ShopRepDAO implements ShopRepDAO_interface {
	
	private static DataSource ds = DataSourceHolder.getDataSource();
	
	private static final String INSERT_STMT = "INSERT INTO shoprep (srepno,shopno,repno,sreprsn) VALUES (shoprep_seq.NEXTVAL, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT srepno,shopno,repno,sreprsn FROM shoprep order by srepno";
	private static final String GET_ONE_STMT = "SELECT srepno,shopno,repno,sreprsn FROM shoprep where srepno = ?";
	private static final String DELETE = "DELETE FROM shoprep where srepno = ?";
	private static final String UPDATE = "UPDATE shoprep set shopno=?, repno=?, sreprsn=? where srepno = ?";

	@Override
	public void insert(ShopRepVO shopRepVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, shopRepVO.getShopno());
			pstmt.setInt(2, shopRepVO.getRepno());
			pstmt.setString(3, shopRepVO.getSreprsn());
			
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
	public void update(ShopRepVO shopRepVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, shopRepVO.getShopno());
			pstmt.setInt(2, shopRepVO.getRepno());
			pstmt.setString(3, shopRepVO.getSreprsn());
			pstmt.setInt(4, shopRepVO.getSrepno());
			
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
	public void delete(Integer srepno) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, srepno);
			
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
	public ShopRepVO findByPrimaryKey(Integer srepno) {
		ShopRepVO shopRepVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, srepno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo ¤]ºÙ¬° Domain objects
				shopRepVO = new ShopRepVO();
				shopRepVO.setSrepno(rs.getInt("srepno"));
				shopRepVO.setShopno(rs.getInt("shopno"));
				shopRepVO.setRepno(rs.getInt("repno"));
				shopRepVO.setSreprsn(rs.getString("sreprsn"));

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
		return shopRepVO;
	}

	@Override
	public List<ShopRepVO> getAll() {
		List<ShopRepVO> list = new ArrayList<ShopRepVO>();
		ShopRepVO shopRepVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				shopRepVO = new ShopRepVO();
				shopRepVO.setSrepno(rs.getInt("srepno"));
				shopRepVO.setShopno(rs.getInt("shopno"));
				shopRepVO.setRepno(rs.getInt("repno"));
				shopRepVO.setSreprsn(rs.getString("sreprsn"));
				list.add(shopRepVO);
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
	public List<ShopRepVO> getAll(Map<String, String[]> map) {
		List<ShopRepVO> list = new ArrayList<ShopRepVO>();
		ShopRepVO shopRepVO = null; 
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			con = ds.getConnection();
			String finalSQL = "select * from shoprep "
					+ jdbcUtil_ComQuery_ShopRep.get_WhereCondition(map)
					+ "order by srepno";
			pstmt = con.prepareStatement(finalSQL);
			System.out.println("¡´¡´finalSQL(by DAO) = " + finalSQL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				shopRepVO = new ShopRepVO();
				shopRepVO.setSrepno(rs.getInt("srepno"));
				shopRepVO.setRepno(rs.getInt("repno"));
				shopRepVO.setShopno(rs.getInt("shopno"));
				shopRepVO.setSreprsn(rs.getString("sreprsn"));
				list.add(shopRepVO);
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
