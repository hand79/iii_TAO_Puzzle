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
	// �@�����ε{����,�w��@�Ӹ�Ʈw ,�@�Τ@��DataSource�Y�i
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
				// shopVO �]�٬� Domain objects
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
		
		// �s�W
		ShopVO shopVO1 = new ShopVO();
		
//		shopVO1.setShopno("");
		shopVO1.setMemno(6);
		shopVO1.setTitle("�X�n��");
		shopVO1.setShop_desc("About �X�n�̰�����U�Ȧn���~��A�S���ϥιA�Ħ۵M�B���O���X�n��");
		shopVO1.setPic(null);
		shopVO1.setMime(null);
		shopVO1.setLocno(10);
		shopVO1.setAddr("�H�q��59��");
		shopVO1.setLng(1.1234567);
		shopVO1.setLat(2.1234567);
		shopVO1.setPhone("03-9889249");
		shopVO1.setFax("03-9884105");
		shopVO1.setEmail("info@hoya-rice.com.tw");
		shopVO1.setShip_desc("��1000���K�B�O");
		shopVO1.setOther_desc("1.�I�ڸ�T�Цܡu�q�ʳ�v���d�ߡA�è̷ӫ��w��������I�ڡA�H�K�����ǳƥX�f�Ʃy�]�I�ڴ����G�Щ��f��10�ѫe�I�ڡA�Y��f��Z���U��鬰10�Ѥ�(�t10��)�A�ݩ�U���駹���I�ڡ^�C 2.�Y�S���̳W�w�ɶ��I�ڡA�������v����f��C 3.�p�פJ�ڶ����~�]�t�h�סB�ֶ׵��I�ڥ��ѱ��p�^�A�����|�P�z�s���ڶ��Ʃy��G�^");
		shopVO1.setHits(0);
		shopVO1.setStatus(1);





		dao.insert(shopVO1);

		// �ק�
		ShopVO shopVO2 = new ShopVO();
		shopVO2.setShopno(8);
		shopVO2.setMemno(6);
		shopVO2.setTitle("2-�X�n��");
		shopVO2.setShop_desc("2-About �X�n�̰�����U�Ȧn���~��A�S���ϥιA�Ħ۵M�B���O���X�n��");
		shopVO2.setPic(null);
		shopVO2.setMime(null);
		shopVO2.setLocno(10);
		shopVO2.setAddr("�H�q��59��");
		shopVO2.setLng(2.1234567);
		shopVO2.setLat(3.1234567);
		shopVO2.setPhone("2-03-9889249");
		shopVO2.setFax("2-03-9884105");
		shopVO2.setEmail("2-info@hoya-rice.com.tw");
		shopVO2.setShip_desc("2-��1000���K�B�O");
		shopVO2.setOther_desc("2-1.�I�ڸ�T�Цܡu�q�ʳ�v���d�ߡA�è̷ӫ��w��������I�ڡA�H�K�����ǳƥX�f�Ʃy�]�I�ڴ����G�Щ��f��10�ѫe�I�ڡA�Y��f��Z���U��鬰10�Ѥ�(�t10��)�A�ݩ�U���駹���I�ڡ^�C 2.�Y�S���̳W�w�ɶ��I�ڡA�������v����f��C 3.�p�פJ�ڶ����~�]�t�h�סB�ֶ׵��I�ڥ��ѱ��p�^�A�����|�P�z�s���ڶ��Ʃy��G�^");
		shopVO2.setHits(0);
		shopVO2.setStatus(2);

		dao.update(shopVO2);

		// �R��
		dao.delete(7);

		//�d��
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

		// �d��
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
