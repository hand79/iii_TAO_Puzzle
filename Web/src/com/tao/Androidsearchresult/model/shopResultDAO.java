package com.tao.Androidsearchresult.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.tao.androidshop.model.ShopVO;
import com.tao.member.controller.LocnoAreaConverter;
import com.tao.util.model.DataSourceHolder;

public class shopResultDAO implements shopResultVO_interface {

	private static DataSource ds = DataSourceHolder.getDataSource();
	
	private static final String FIND_SHOP_BY_NAME_KEY_STMT = 
			"SELECT shopno,memno,title,shop_desc,pic,mime,locno,addr,lng,lat,phone,fax,email,ship_desc,other_desc,hits,status FROM shop where status=2 and title like ?";
	
//	private static final String FIND_SHOP_BY_NAME_KEY_STMT = 
//			"select s.shopno, s.title, p.spno, p.name, p.pro_desc, s.hits from shop s, shopproduct p "
//			+ "where s.shopno=p.shopno and s.status=2 and s.title like ?";
	
	private static final String FIND_SHOP_BY_SUBCATNO_STMT = 
			"select s.shopno, s.title, p.spno, p.name, p.pro_desc, s.hits from shop s, shopproduct p "
			+ "where s.shopno=p.shopno and s.status=2 and p.subcatno = ? and s.locno between ? and ?";

	@Override
	public List<ShopVO> findShopByNameKey(String keyword) {
		List<ShopVO> list = new ArrayList<ShopVO>();
		ShopVO shopVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_SHOP_BY_NAME_KEY_STMT);
			pstmt.setString(1, "%" + keyword + "%");
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
				
				list.add(shopVO);
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
	public List<shopResultVO> findShopBySubCat(Integer subcatno, Integer area) {
		List<shopResultVO> list = new ArrayList<shopResultVO>();
		shopResultVO shopresultVO = null;
		Integer startIndex = 0;
		Integer endIndex = 0;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_SHOP_BY_SUBCATNO_STMT);
			pstmt.setInt(1, subcatno);
			
/*			if (area == 1) {
				startIndex = 1;
				endIndex = 95;
			} else if (area == 2) {
				startIndex = 96;
				endIndex = 183;
			} else if (area == 3){
				startIndex = 184;
				endIndex = 313;
			} else {
				startIndex = 314;
				endIndex = 355;
			}*/
			
			if ((area!=null) && (area.toString().length()!=0)) {
				if ((area>=1) && (area<=10)){
					Integer[] fromTo = LocnoAreaConverter.areaToLocno(area);
					startIndex = fromTo[0];
					System.out.println(startIndex);
					endIndex = fromTo[1];
					System.out.println(endIndex);
				} else {
					//not valid input, set to default
					startIndex = 1;
					endIndex = 355;
				}
			} else {
				// didn't input area, set to fefault
				startIndex = 1;
				endIndex = 355;
			}
			
			pstmt.setInt(2, startIndex);
			pstmt.setInt(3, endIndex);
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				shopresultVO = new shopResultVO();
				shopresultVO.setShopno(rs.getInt(1));
				shopresultVO.setTitle(rs.getString(2));
				shopresultVO.setSpno(rs.getInt(3));
				shopresultVO.setName(rs.getString(4));
				shopresultVO.setPro_desc(rs.getString(5));
				shopresultVO.setHits(rs.getInt(6));
				list.add(shopresultVO);				
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
	
	
}
