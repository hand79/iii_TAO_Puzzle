package com.tao.androidshopproduct.model;

import java.sql.Connection;
import java.sql.DriverManager;
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



public class ShopproductDAO implements Shopproduct_interface {
	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	private static DataSource ds = DataSourceHolder.getDataSource();

	private static final String INSERT_STMT = "INSERT INTO shopproduct (spno,shopno,name,unitprice,pic1,pic2,pic3,pmime1,pmime2,pmime3,pro_desc,subcatno,spec1,spec2,spec3,isrecomm) VALUES (shopproduct_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT spno,shopno,name,unitprice,pic1,pic2,pic3,pmime1,pmime2,pmime3,pro_desc,subcatno,spec1,spec2,spec3,isrecomm FROM shopproduct order by spno";
	private static final String GET_ONE_STMT = "SELECT spno,shopno,name,unitprice,pic1,pic2,pic3,pmime1,pmime2,pmime3,pro_desc,subcatno,spec1,spec2,spec3,isrecomm FROM shopproduct where spno = ?";
	private static final String DELETE = "DELETE FROM shopproduct where spno = ?";
	private static final String UPDATE = "UPDATE shopproduct set shopno=?,name=?,unitprice=?,pic1=?,pic2=?,pic3=?,pmime1=?,pmime2=?,pmime3=?,pro_desc=?,subcatno=?,spec1=?,spec2=?,spec3=?,isrecomm=? where spno = ?";
	
	@Override
	public int insert(ShopproductVO sptVO) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		Connection con = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			
			pstmt.setInt(1, sptVO.getShopno());
			pstmt.setString(2, sptVO.getName());
			pstmt.setDouble(3, sptVO.getUnitprice());
			pstmt.setBytes(4, sptVO.getPic1());
			pstmt.setBytes(5, sptVO.getPic2());
			pstmt.setBytes(6, sptVO.getPic3());
			pstmt.setString(7, sptVO.getPmime1());
			pstmt.setString(8, sptVO.getPmime2());
			pstmt.setString(9, sptVO.getPmime3());
			pstmt.setString(10, sptVO.getPro_desc());
			pstmt.setInt(11, sptVO.getSubcatno());
			pstmt.setString(12, sptVO.getSpec1());
			pstmt.setString(13, sptVO.getSpec2());
			pstmt.setString(14, sptVO.getSpec3());
			pstmt.setInt(15, sptVO.getIsrecomm());

			updateCount = pstmt.executeUpdate();

			
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
		}
		return updateCount;

	}

	@Override
	public int update(ShopproductVO sptVO) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		Connection con = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, sptVO.getShopno());
			pstmt.setString(2, sptVO.getName());
			pstmt.setDouble(3, sptVO.getUnitprice());
			pstmt.setBytes(4, sptVO.getPic1());
			pstmt.setBytes(5, sptVO.getPic2());
			pstmt.setBytes(6, sptVO.getPic3());
			pstmt.setString(7, sptVO.getPmime1());
			pstmt.setString(8, sptVO.getPmime2());
			pstmt.setString(9, sptVO.getPmime3());
			pstmt.setString(10, sptVO.getPro_desc());
			pstmt.setInt(11, sptVO.getSubcatno());
			pstmt.setString(12, sptVO.getSpec1());
			pstmt.setString(13, sptVO.getSpec2());
			pstmt.setString(14, sptVO.getSpec3());
			pstmt.setInt(15, sptVO.getIsrecomm());
			pstmt.setInt(16, sptVO.getSpno());

			updateCount = pstmt.executeUpdate();

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
		}
		return updateCount;
	}

	@Override
	public int delete(Integer spno) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		Connection con = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, spno);

			updateCount = pstmt.executeUpdate();


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
		}
		return updateCount;
	}

	@Override
	public ShopproductVO findByPrimaryKey(Integer spno) {
		ShopproductVO sptVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, spno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				sptVO = new ShopproductVO();
				sptVO.setSpno(rs.getInt("spno"));
				sptVO.setShopno(rs.getInt("shopno"));
				sptVO.setName(rs.getString("name"));
				sptVO.setUnitprice(rs.getDouble("unitprice"));
				sptVO.setPic1(rs.getBytes("pic1"));
				sptVO.setPic2(rs.getBytes("pic2"));
				sptVO.setPic3(rs.getBytes("pic3"));
				sptVO.setPmime1(rs.getString("pmime1"));
				sptVO.setPmime2(rs.getString("pmime2"));
				sptVO.setPmime3(rs.getString("pmime3"));
				sptVO.setPro_desc(rs.getString("pro_desc"));
				sptVO.setSubcatno(rs.getInt("subcatno"));
				sptVO.setSpec1(rs.getString("spec1"));
				sptVO.setSpec2(rs.getString("spec2"));
				sptVO.setSpec3(rs.getString("spec3"));
				sptVO.setIsrecomm(rs.getInt("isrecomm"));

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
		return sptVO;

	}

	@Override
	public List<ShopproductVO> getAll() {
		List<ShopproductVO> list = new ArrayList<ShopproductVO>();
		ShopproductVO sptVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				sptVO = new ShopproductVO();
				sptVO.setSpno(rs.getInt("spno"));
				sptVO.setShopno(rs.getInt("shopno"));
				sptVO.setName(rs.getString("name"));
				sptVO.setUnitprice(rs.getDouble("unitprice"));
				sptVO.setPic1(rs.getBytes("pic1"));
				sptVO.setPic2(rs.getBytes("pic2"));
				sptVO.setPic3(rs.getBytes("pic3"));
				sptVO.setPmime1(rs.getString("pmime1"));
				sptVO.setPmime2(rs.getString("pmime2"));
				sptVO.setPmime3(rs.getString("pmime3"));
				sptVO.setPro_desc(rs.getString("pro_desc"));
				sptVO.setSubcatno(rs.getInt("subcatno"));
				sptVO.setSpec1(rs.getString("spec1"));
				sptVO.setSpec2(rs.getString("spec2"));
				sptVO.setSpec3(rs.getString("spec3"));
				sptVO.setIsrecomm(rs.getInt("isrecomm"));
				list.add(sptVO);
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
	public static void testshoppd() {
		

		ShopproductDAO dao = new ShopproductDAO();
		
		// 新增
//		ShopproductVO ShoppdVO1 = new ShopproductVO();
//		
//		//shopVO1.setSpno();
//		ShoppdVO1.setShopno(8);
//		ShoppdVO1.setName("草莓蛋糕");
//		ShoppdVO1.setUnitprice(500.00);
//		ShoppdVO1.setPic1(null);
//		ShoppdVO1.setPic2(null);
//		ShoppdVO1.setPic3(null);
//		ShoppdVO1.setPmime1(".jpg");
//		ShoppdVO1.setPmime2(".png");
//		ShoppdVO1.setPmime3(".xxx");
//		ShoppdVO1.setPro_desc("*商品成分：大湖新鮮草莓/日本生鮮奶油/法國無鹽發酵奶油/天然香草棒/D-山梨醇/轉化糖漿 *保存期限：5度C冷藏7天 (需扣除宅配1天)(本商品為新鮮食材製作，不含防腐劑)");
//		ShoppdVO1.setSubcatno(8);
//		ShoppdVO1.setSpec1("1條");
//		ShoppdVO1.setSpec2("2條");
//		ShoppdVO1.setSpec3("3條");
//		ShoppdVO1.setIsrecomm(2);
//
//
//		int updateCount_insert = dao.insert(ShoppdVO1);
//		System.out.println(updateCount_insert);

		// 修改
		ShopproductVO ShoppdVO2 = new ShopproductVO();
		
		ShoppdVO2.setSpno(16);
		ShoppdVO2.setShopno(8);
		ShoppdVO2.setName("香草蛋糕");
		ShoppdVO2.setUnitprice(500.00);
		ShoppdVO2.setPic1(null);
		ShoppdVO2.setPic2(null);
		ShoppdVO2.setPic3(null);
		ShoppdVO2.setPmime1(".xxx");
		ShoppdVO2.setPmime2(".xxx");
		ShoppdVO2.setPmime3(".xxx");
		ShoppdVO2.setPro_desc("*商品成分：香草/日本生鮮奶油/法國無鹽發酵奶油/天然香草棒/D-山梨醇/轉化糖漿 *保存期限：5度C冷藏7天 (需扣除宅配1天)(本商品為新鮮食材製作，不含防腐劑)");
		ShoppdVO2.setSubcatno(8);
		ShoppdVO2.setSpec1("1條");
		ShoppdVO2.setSpec2("1條");
		ShoppdVO2.setSpec3("1條");
		ShoppdVO2.setIsrecomm(2);

		int updateCount_update = dao.update(ShoppdVO2);
		System.out.println(updateCount_update);

//		// 刪除
//		dao.delete(12);
//		System.out.println(updateCount_delete);
//
//		//查詢
//		ShopproductVO ShoppdVO3 = dao.findByPrimaryKey(1);
//		System.out.print(ShoppdVO3.getShopno()+ ",");
//		System.out.print(ShoppdVO3.getName()+ ",");
//		System.out.print(ShoppdVO3.getUnitprice()+ ",");
//		System.out.print(ShoppdVO3.getPic1()+ ",");
//		System.out.print(ShoppdVO3.getPic2()+ ",");
//		System.out.print(ShoppdVO3.getPic3()+ ",");
//		System.out.print(ShoppdVO3.getPmime1()+ ",");
//		System.out.print(ShoppdVO3.getPmime2()+ ",");
//		System.out.print(ShoppdVO3.getPmime3()+ ",");
//		System.out.print(ShoppdVO3.getPro_desc()+ ",");
//		System.out.print(ShoppdVO3.getSubcatno()+ ",");
//		System.out.print(ShoppdVO3.getSpec1()+ ",");
//		System.out.print(ShoppdVO3.getSpec2()+ ",");
//		System.out.print(ShoppdVO3.getSpec3()+ ",");
//		System.out.print(ShoppdVO3.getIsrecomm()+ ",");
//		System.out.println("---------------------");
//
//		// 查詢
//		List<ShopproductVO> list = dao.getAll();
//		for (ShopproductVO aShoppd : list) {
//			System.out.print(aShoppd.getShopno()+ ",");
//			System.out.print(aShoppd.getName()+ ",");
//			System.out.print(aShoppd.getUnitprice()+ ",");
//			System.out.print(aShoppd.getPic1()+ ",");
//			System.out.print(aShoppd.getPic2()+ ",");
//			System.out.print(aShoppd.getPic3()+ ",");
//			System.out.print(aShoppd.getPmime1()+ ",");
//			System.out.print(aShoppd.getPmime2()+ ",");
//			System.out.print(aShoppd.getPmime3()+ ",");
//			System.out.print(aShoppd.getPro_desc()+ ",");
//			System.out.print(aShoppd.getSubcatno()+ ",");
//			System.out.print(aShoppd.getSpec1()+ ",");
//			System.out.print(aShoppd.getSpec2()+ ",");
//			System.out.print(aShoppd.getSpec3()+ ",");
//			System.out.print(aShoppd.getIsrecomm()+ ",");
//			System.out.println();
//		}
	}
}
