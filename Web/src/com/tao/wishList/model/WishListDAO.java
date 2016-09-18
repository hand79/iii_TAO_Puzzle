package com.tao.wishList.model;

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

import com.tao.helen.util.jdbcUtil_ComQuery_WishList;
import com.tao.util.model.DataSourceHolder;
import com.tao.wtdReq.model.WtdReqVO;

public class WishListDAO implements WishListDAO_interface {

	private static DataSource ds = DataSourceHolder.getDataSource();

	private static final String INSERT_STMT = "INSERT INTO wishlist (wlno,memno,caseno) VALUES (wishlist_seq.NEXTVAL, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT wlno,memno,caseno FROM wishlist order by wlno";
	private static final String GET_ONE_STMT = "SELECT wlno,memno,caseno FROM wishlist where wlno = ?";
	private static final String DELETE = "DELETE FROM wishlist where wlno = ?";
	private static final String UPDATE = "UPDATE wishlist set memno=?, caseno=? where wlno = ?";

	@Override
	public void insert(WishListVO wishListVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, wishListVO.getMemno());
			pstmt.setInt(2, wishListVO.getCaseno());

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
	public void update(WishListVO wishListVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, wishListVO.getMemno());
			pstmt.setInt(2, wishListVO.getCaseno());
			pstmt.setInt(3, wishListVO.getWlno());

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
	public void delete(Integer wlno) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, wlno);

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
	public WishListVO findByPrimaryKey(Integer wlno) {
		WishListVO wishListVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, wlno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo ¤]ºÙ¬° Domain objects
				wishListVO = new WishListVO();
				wishListVO.setWlno(rs.getInt("wlno"));
				wishListVO.setMemno(rs.getInt("memno"));
				wishListVO.setCaseno(rs.getInt("caseno"));

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
		return wishListVO;
	}

	@Override
	public List<WishListVO> getAll() {
		List<WishListVO> list = new ArrayList<WishListVO>();
		WishListVO wishListVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				wishListVO = new WishListVO();
				wishListVO.setWlno(rs.getInt("wlno"));
				wishListVO.setMemno(rs.getInt("memno"));
				wishListVO.setCaseno(rs.getInt("caseno"));
				list.add(wishListVO);
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
	public List<WishListVO> getAll(Map<String, String[]> map) {
		List<WishListVO> list = new ArrayList<WishListVO>();
		WishListVO wishlistVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			String finalSQL = "select * from wishlist "
					+ jdbcUtil_ComQuery_WishList.get_WhereCondition(map)
					+ "order by wlno";
			pstmt = con.prepareStatement(finalSQL);
			System.out.println("¡´¡´finalSQL(by DAO) = " + finalSQL);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				wishlistVO = new WishListVO();
				wishlistVO.setWlno(rs.getInt("wlno"));
				wishlistVO.setMemno(rs.getInt("memno"));
				wishlistVO.setCaseno(rs.getInt("caseno"));
				list.add(wishlistVO);
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
