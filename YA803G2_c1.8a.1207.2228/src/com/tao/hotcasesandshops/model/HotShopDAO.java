package com.tao.hotcasesandshops.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.tao.util.model.DataSourceHolder;

public class HotShopDAO implements HotShopDAO_interface {
	private static DataSource ds = DataSourceHolder.getDataSource();
	
	private static final String GET_ALL_BY_HITS = "select shopno,memno,title,locno,hits,status from shop  order by hits desc,shopno asc";
	private static final String GET_ALL_BY_HITS_STATUS = "select shopno,memno,title,locno,hits,status from shop where status=2 order by hits desc,shopno asc";
	private static final String GET_ONE_BY_SHOPNO = "select shopno,memno,pic,mime,title,hits,locno,status from shop where shopno=?";
	private static final String GET_ALL_BY_SHOPNO_lOCNO = "select shopno,memno,title,locno,hits,status from shop where status=2 and locno=? order by hits desc,shopno asc";
	private static final String GET_ONE_SHOP_ISRECOMM_PRODUCT_PIC="select b.pic1,b.pmime1 from shop a,shopproduct b where a.shopno=b.shopno and b.isrecomm =1 and a.shopno = ?";	
	@Override
	public HotShopVO findByShopno(Integer shopno) {
		// TODO Auto-generated method stub
		HotShopVO hotShopVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_SHOPNO);
			pstmt.setInt(1, shopno);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				hotShopVO = new HotShopVO();
				hotShopVO.setShopno(rs.getInt("shopno"));
				hotShopVO.setMemno(rs.getInt("memno"));
				hotShopVO.setTitle(rs.getString("title"));
				hotShopVO.setPic(rs.getBytes("pic"));
				hotShopVO.setMime(rs.getString("mime"));
				hotShopVO.setLocno(rs.getInt("locno"));
				hotShopVO.setHits(rs.getInt("hits"));
				hotShopVO.setStatus(rs.getInt("status"));
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
		return hotShopVO;
	}
	
	@Override
	public List<HotShopVO> getAllByHits() {
		// TODO Auto-generated method stub
		List<HotShopVO> list = new ArrayList<HotShopVO>();
		HotShopVO hotShopVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_BY_HITS);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				hotShopVO = new HotShopVO();
				hotShopVO.setShopno(rs.getInt("shopno"));
				hotShopVO.setMemno(rs.getInt("memno"));
				hotShopVO.setTitle(rs.getString("title"));
				hotShopVO.setPic(rs.getBytes("pic"));
				hotShopVO.setMime(rs.getString("mime"));
				hotShopVO.setLocno(rs.getInt("locno"));
				hotShopVO.setHits(rs.getInt("hits"));
				hotShopVO.setStatus(rs.getInt("status"));
				list.add(hotShopVO); // Store the row in the list

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
	public List<HotShopVO> getAllByHitsStatus() {
		// TODO Auto-generated method stub
		List<HotShopVO> list = new ArrayList<HotShopVO>();
		HotShopVO hotShopVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_BY_HITS_STATUS);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				hotShopVO = new HotShopVO();
				hotShopVO.setShopno(rs.getInt("shopno"));
				hotShopVO.setMemno(rs.getInt("memno"));
				hotShopVO.setTitle(rs.getString("title"));
				hotShopVO.setLocno(rs.getInt("locno"));
				hotShopVO.setHits(rs.getInt("hits"));
				hotShopVO.setStatus(rs.getInt("status"));
				list.add(hotShopVO); // Store the row in the list

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
	public List<HotShopVO> getAllByHitsStatusLocno(Integer locno) {
		// TODO Auto-generated method stub
		List<HotShopVO> list = new ArrayList<HotShopVO>();
		HotShopVO hotShopVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_BY_SHOPNO_lOCNO);
			pstmt.setInt(1, locno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				hotShopVO = new HotShopVO();
				hotShopVO.setShopno(rs.getInt("shopno"));
				hotShopVO.setMemno(rs.getInt("memno"));
				hotShopVO.setTitle(rs.getString("title"));
				hotShopVO.setLocno(rs.getInt("locno"));
				hotShopVO.setHits(rs.getInt("hits"));
				hotShopVO.setStatus(rs.getInt("status"));
				list.add(hotShopVO); // Store the row in the list

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
	public HotShopVO findByIsrecommPic(Integer shopno) {
		// TODO Auto-generated method stub
		HotShopVO hotShopVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_SHOP_ISRECOMM_PRODUCT_PIC);
			pstmt.setInt(1, shopno);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				hotShopVO = new HotShopVO();
				hotShopVO.setPic(rs.getBytes("pic1"));
				hotShopVO.setMime(rs.getString("pmime1"));
				
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
		return hotShopVO;
	}

	

}
