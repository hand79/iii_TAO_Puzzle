package com.tao.androidshop.model;

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


public class ShopDAO implements ShopDAO_interface{
	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	private static DataSource ds = DataSourceHolder.getDataSource();
	
	private static final String INSERT_STMT = "INSERT INTO shop (shopno,memno,title,shop_desc,pic,mime,locno,addr,lng,lat,phone,fax,email,ship_desc,other_desc,hits,status)"
			+ "VALUES (shop_seq.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT shopno,memno,title,shop_desc,pic,mime,locno,addr,lng,lat,phone,fax,email,ship_desc,other_desc,hits,status FROM shop order by shopno";
	private static final String GET_ONE_STMT = "SELECT shopno,memno,title,shop_desc,pic,mime,locno,addr,lng,lat,phone,fax,email,ship_desc,other_desc,hits,status FROM shop where shopno = ?";
	private static final String DELETE = "DELETE FROM shop where shopno = ?";
	private static final String UPDATE = "UPDATE shop set memno=?,title=?,shop_desc=?,pic=?,mime=?,locno=?,addr=?,lng=?,lat=?,phone=?,fax=?,email=?,ship_desc=?,other_desc=?,hits=?,status=? where shopno = ?";
//	(shopno,memno,title,shop_desc,pic,mime,locno,addr,lng,lat,phone,fax,email,ship_desc,other_desc,hits,status)
//	(shop_seq.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
//	shopno=?,memno=?,title=?,shop_desc=?,pic=?,mime=?,locno=?,addr=?,lng=?,lat=?,phone=?,fax=?,email=?,ship_desc=?,other_desc=?,hits=?,status=?
	private static final String For_Android = "SELECT shopno,memno,title,shop_desc,pic,mime,locno,addr,lng,lat,phone,fax,email,ship_desc,other_desc,hits,status FROM shop order by status";
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
			pstmt = con.prepareStatement(UPDATE);
		
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
			pstmt.setInt(17, shopVO.getShopno());
			

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

			while (rs.next()) {
				// shopVO 也稱為 Domain objects
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
		return shopVO;
	}
	
	@Override
	public List<ShopVO> getAll() {

		List<ShopVO> list = new ArrayList<ShopVO>();
		ShopVO shopVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
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
	
	public static void testshop() {
		

		ShopDAO dao = new ShopDAO();
		
		// 新增
		ShopVO shopVO1 = new ShopVO();
		
//		shopVO1.setShopno("");
		shopVO1.setMemno(6);
		shopVO1.setTitle("合鴨米");
		shopVO1.setShop_desc("About 合鴨米堅持給顧客好的品質，沒有使用農藥自然、環保的合鴨米");
		shopVO1.setPic(null);
		shopVO1.setMime(null);
		shopVO1.setLocno(10);
		shopVO1.setAddr("信義路59號");
		shopVO1.setLng(1.1234567);
		shopVO1.setLat(2.1234567);
		shopVO1.setPhone("03-9889249");
		shopVO1.setFax("03-9884105");
		shopVO1.setEmail("info@hoya-rice.com.tw");
		shopVO1.setShip_desc("滿1000元免運費");
		shopVO1.setOther_desc("1.付款資訊請至「訂購單」中查詢，並依照指定日期完成付款，以便店長準備出貨事宜（付款期限：請於到貨日10天前付款，若到貨日距離下單日為10天內(含10天)，需於下當單日完成付款）。 2.若沒有依規定時間付款，店長有權更改到貨日。 3.如匯入款項錯誤（含多匯、少匯等付款失敗情況），店長會與您連絡款項事宜喔：）");
		shopVO1.setHits(0);
		shopVO1.setStatus(1);





		dao.insert(shopVO1);

		// 修改
		ShopVO shopVO2 = new ShopVO();
		shopVO2.setShopno(8);
		shopVO2.setMemno(6);
		shopVO2.setTitle("2-合鴨米");
		shopVO2.setShop_desc("2-About 合鴨米堅持給顧客好的品質，沒有使用農藥自然、環保的合鴨米");
		shopVO2.setPic(null);
		shopVO2.setMime(null);
		shopVO2.setLocno(10);
		shopVO2.setAddr("信義路59號");
		shopVO2.setLng(2.1234567);
		shopVO2.setLat(3.1234567);
		shopVO2.setPhone("2-03-9889249");
		shopVO2.setFax("2-03-9884105");
		shopVO2.setEmail("2-info@hoya-rice.com.tw");
		shopVO2.setShip_desc("2-滿1000元免運費");
		shopVO2.setOther_desc("2-1.付款資訊請至「訂購單」中查詢，並依照指定日期完成付款，以便店長準備出貨事宜（付款期限：請於到貨日10天前付款，若到貨日距離下單日為10天內(含10天)，需於下當單日完成付款）。 2.若沒有依規定時間付款，店長有權更改到貨日。 3.如匯入款項錯誤（含多匯、少匯等付款失敗情況），店長會與您連絡款項事宜喔：）");
		shopVO2.setHits(0);
		shopVO2.setStatus(2);

		dao.update(shopVO2);

		// 刪除
		dao.delete(7);

		//查詢
		ShopVO shopVO3 = dao.findByPrimaryKey(1);
		System.out.print(shopVO3.getShopno()+ ",");
		System.out.print(shopVO3.getMemno()+ ",");
		System.out.print(shopVO3.getTitle()+ ",");
		System.out.print(shopVO3.getShop_desc()+ ",");
		System.out.print(shopVO3.getPic()+ ",");
		System.out.print(shopVO3.getMime()+ ",");
		System.out.print(shopVO3.getLocno()+ ",");
		System.out.print(shopVO3.getAddr()+ ",");
		System.out.print(shopVO3.getLng()+ ",");
		System.out.print(shopVO3.getLat()+ ",");
		System.out.print(shopVO3.getPhone()+ ",");
		System.out.print(shopVO3.getFax()+ ",");
		System.out.print(shopVO3.getEmail()+ ",");
		System.out.print(shopVO3.getShip_desc()+ ",");
		System.out.print(shopVO3.getOther_desc()+ ",");
		System.out.print(shopVO3.getHits()+ ",");
		System.out.print(shopVO3.getStatus()+ ",");
		System.out.println("---------------------");

		// 查詢
		List<ShopVO> list = dao.getAll();
		for (ShopVO aShop : list) {
			System.out.print(aShop.getShopno()+ ",");
			System.out.print(aShop.getMemno()+ ",");
			System.out.print(aShop.getTitle()+ ",");
			System.out.print(aShop.getShop_desc()+ ",");
			System.out.print(aShop.getPic()+ ",");
			System.out.print(aShop.getMime()+ ",");
			System.out.print(aShop.getLocno()+ ",");
			System.out.print(aShop.getAddr()+ ",");
			System.out.print(aShop.getLng()+ ",");
			System.out.print(aShop.getLat()+ ",");
			System.out.print(aShop.getPhone()+ ",");
			System.out.print(aShop.getFax()+ ",");
			System.out.print(aShop.getEmail()+ ",");
			System.out.print(aShop.getShip_desc()+ ",");
			System.out.print(aShop.getOther_desc()+ ",");
			System.out.print(aShop.getHits()+ ",");
			System.out.print(aShop.getStatus()+ ",");
			System.out.println();
		}
	}
}
