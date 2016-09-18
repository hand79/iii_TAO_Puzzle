package com.tao.shop.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.tao.util.model.DataSourceHolder;

public class ShopDAO implements ShopDAO_interface {
	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	private static DataSource ds = DataSourceHolder.getDataSource();

	private static final String INSERT_STMT = "INSERT INTO shop (shopno,memno,title,shop_desc,pic,mime,locno,addr,lng,lat,phone,fax,email,ship_desc,other_desc,hits,status)"
			+ "VALUES (shop_seq.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	// private static final String GET_ALL_STMT =
	// "SELECT shopno,memno,title,shop_desc,pic,mime,locno,addr,lng,lat,phone,fax,email,ship_desc,other_desc,hits,status FROM shop  order by shopno";
	private static final String GET_ALL_STMT = "SELECT shopno,memno,title,shop_desc,locno,addr,lng,lat,phone,fax,email,ship_desc,other_desc,hits,status FROM shop";
	private static final String GET_WAIT_SHOP = "SELECT shopno,memno,title,shop_desc,pic,mime,locno,addr,lng,lat,phone,fax,email,ship_desc,other_desc,hits,status FROM shop where status=1 order by shopno";
	private static final String GET_ONE_STMT = "SELECT shopno,memno,title,shop_desc,pic,mime,locno,addr,lng,lat,phone,fax,email,ship_desc,other_desc,hits,status FROM shop where shopno = ?";
	private static final String DELETE = "DELETE FROM shop where shopno = ?";
	private static final String UPDATE = "UPDATE shop set memno=?,title=?,shop_desc=?,locno=?,addr=?,lng=?,lat=?,phone=?,fax=?,email=?,ship_desc=?,other_desc=?,hits=?,status=?";
	private static final String UPDATE_STATUS = "UPDATE shop set status=? where shopno = ?";
	private static final String CHECK_STATUS = "SELECT shopno,memno,title,shop_desc,pic,mime,locno,addr,lng,lat,phone,fax,email,ship_desc,other_desc,hits,status FROM shop where status=2 and shopno=?";
	private static final String ORDER_BY = "order by shopno";

	// (shopno,memno,title,shop_desc,pic,mime,locno,addr,lng,lat,phone,fax,email,ship_desc,other_desc,hits,status)
	// (shop_seq.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
	// shopno=?,memno=?,title=?,shop_desc=?,pic=?,mime=?,locno=?,addr=?,lng=?,lat=?,phone=?,fax=?,email=?,ship_desc=?,other_desc=?,hits=?,status=?

	@Override
	public void insert(ShopVO shopVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, shopVO.getMemno());
			pstmt.setString(2, shopVO.getTitle());
			pstmt.setString(3, shopVO.getShop_desc());
			pstmt.setBytes(4, shopVO.getPic());
			pstmt.setString(5, shopVO.getMime());
			pstmt.setInt(6, shopVO.getLocno());
			pstmt.setString(7, shopVO.getAddr());
			pstmt.setDouble(8, shopVO.getLng());
			pstmt.setDouble(9, shopVO.getLat());
			pstmt.setString(10, shopVO.getPhone());
			pstmt.setString(11, shopVO.getFax());
			pstmt.setString(12, shopVO.getEmail());
			pstmt.setString(13, shopVO.getShip_desc());
			pstmt.setString(14, shopVO.getOther_desc());
			pstmt.setInt(15, shopVO.getHits());
			pstmt.setInt(16, shopVO.getStatus());

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	public void update(ShopVO shopVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			
			String stmt = UPDATE;
			if(shopVO.getPic() !=null){
				stmt += " ,pic=?,mime=?";
			}
			
			pstmt = con.prepareStatement(stmt + " WHERE shopno=?");

			pstmt.setInt(1, shopVO.getMemno());
			pstmt.setString(2, shopVO.getTitle());
			pstmt.setString(3, shopVO.getShop_desc());
			pstmt.setInt(4, shopVO.getLocno());
			pstmt.setString(5, shopVO.getAddr());
			pstmt.setDouble(6, shopVO.getLng());
			pstmt.setDouble(7, shopVO.getLat());
			pstmt.setString(8, shopVO.getPhone());
			pstmt.setString(9, shopVO.getFax());
			pstmt.setString(10, shopVO.getEmail());
			pstmt.setString(11, shopVO.getShip_desc());
			pstmt.setString(12, shopVO.getOther_desc());
			pstmt.setInt(13, shopVO.getHits());
			pstmt.setInt(14, shopVO.getStatus());
			int i = 15;
			if(shopVO.getPic() !=null){
				pstmt.setBytes(i++, shopVO.getPic());
				pstmt.setString(i++, shopVO.getMime());
			}
			pstmt.setInt(i++, shopVO.getShopno());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	public void delete(Integer shopno) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, shopno);

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	public ShopVO findByPrimaryKey(Integer shopno) {
		ShopVO shopVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, shopno);

			rs = pstmt.executeQuery();
		
			if (rs.next()) {
				shopVO = retrieveCurrentRow(rs, true);
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
		return shopVO;
	}

	@Override
	public List<ShopVO> getAll() {

		List<ShopVO> list = new ArrayList<ShopVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT + " " + ORDER_BY);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ShopVO shopVO = retrieveCurrentRow(rs, false);
				list.add(shopVO); // Store the row in the list
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

	private ShopVO retrieveCurrentRow(ResultSet rs, boolean includePicture)
			throws SQLException {
		ShopVO shopVO = new ShopVO();

		shopVO.setShopno(rs.getInt("shopno"));
		shopVO.setMemno(rs.getInt("memno"));
		shopVO.setTitle(rs.getString("title"));
		shopVO.setShop_desc(rs.getString("shop_desc"));
		if (includePicture) {
			shopVO.setPic(rs.getBytes("pic"));
			shopVO.setMime(rs.getString("mime"));
		}
		shopVO.setLocno(rs.getInt("locno"));
		shopVO.setAddr(rs.getString("addr"));
		shopVO.setLng(rs.getDouble("lng"));
		shopVO.setLat(rs.getDouble("lat"));
		shopVO.setPhone(rs.getString("phone"));
		shopVO.setFax(rs.getString("fax"));
		shopVO.setEmail(rs.getString("email"));
		shopVO.setShip_desc(rs.getString("ship_desc"));
		shopVO.setOther_desc(rs.getString("other_desc"));
		shopVO.setHits(rs.getInt("hits"));
		shopVO.setStatus(rs.getInt("status"));
		return shopVO;
	}

	@Override
	public void update_status(Integer status, Integer shopno) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STATUS);

			pstmt.setInt(1, status);
			pstmt.setInt(2, shopno);

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	public List<ShopVO> getAll(Map<String, String[]> map) {
		List<ShopVO> list = new ArrayList<ShopVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			String finalSQL = GET_ALL_STMT + " "
					+ jdbcUtil_CompositeQuery_Shop.get_WhereCondition(map)
					+ " " + ORDER_BY;
			pstmt = con.prepareStatement(finalSQL);
			System.out.println("ShopDAO: ●●finalSQL= " + finalSQL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ShopVO shopVO = retrieveCurrentRow(rs, false);
				list.add(shopVO); // Store the row in the List
			}

			// Handle any SQL errors
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
	public List<ShopVO> getWaitApproveShop() {
		List<ShopVO> list = new ArrayList<ShopVO>();
		ShopVO shopVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_WAIT_SHOP);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				shopVO = new ShopVO();

				shopVO.setShopno(rs.getInt("shopno"));
				shopVO.setMemno(rs.getInt("memno"));
				shopVO.setTitle(rs.getString("title"));
				shopVO.setShop_desc(rs.getString("shop_desc"));
				shopVO.setPic(rs.getBytes("pic"));
				shopVO.setMime(rs.getString("mime"));
				shopVO.setLocno(rs.getInt("locno"));
				shopVO.setAddr(rs.getString("addr"));
				shopVO.setLng(rs.getDouble("lng"));
				shopVO.setLat(rs.getDouble("lat"));
				shopVO.setPhone(rs.getString("phone"));
				shopVO.setFax(rs.getString("fax"));
				shopVO.setEmail(rs.getString("email"));
				shopVO.setShip_desc(rs.getString("ship_desc"));
				shopVO.setOther_desc(rs.getString("other_desc"));
				shopVO.setHits(rs.getInt("hits"));
				shopVO.setStatus(rs.getInt("status"));

				list.add(shopVO); // Store the row in the list
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
	public ShopVO checkStatus(Integer shopno) {
		ShopVO shopVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(CHECK_STATUS);

			pstmt.setInt(1, shopno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				shopVO = new ShopVO();
				shopVO.setShopno(rs.getInt("shopno"));
				shopVO.setMemno(rs.getInt("memno"));
				shopVO.setTitle(rs.getString("title"));
				shopVO.setShop_desc(rs.getString("shop_desc"));
				shopVO.setPic(rs.getBytes("pic"));
				shopVO.setMime(rs.getString("mime"));
				shopVO.setLocno(rs.getInt("locno"));
				shopVO.setAddr(rs.getString("addr"));
				shopVO.setLng(rs.getDouble("lng"));
				shopVO.setLat(rs.getDouble("lat"));
				shopVO.setPhone(rs.getString("phone"));
				shopVO.setFax(rs.getString("fax"));
				shopVO.setEmail(rs.getString("email"));
				shopVO.setShip_desc(rs.getString("ship_desc"));
				shopVO.setOther_desc(rs.getString("other_desc"));
				shopVO.setHits(rs.getInt("hits"));
				shopVO.setStatus(rs.getInt("status"));
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
		return shopVO;
	}

}
