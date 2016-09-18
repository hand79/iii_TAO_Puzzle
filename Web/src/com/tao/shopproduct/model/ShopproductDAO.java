package com.tao.shopproduct.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.tao.util.model.DataSourceHolder;

public class ShopproductDAO implements ShopproductDAO_interface {
	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	private static DataSource ds = DataSourceHolder.getDataSource();

	private static final String INSERT_STMT = "INSERT INTO shopproduct (spno,shopno,name,unitprice,pic1,pic2,pic3,pmime1,pmime2,pmime3,pro_desc,subcatno,spec1,spec2,spec3,isrecomm) VALUES (shopproduct_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//	private static final String GET_ALL_STMT = "SELECT spno,shopno,name,unitprice,pic1,pic2,pic3,pmime1,pmime2,pmime3,pro_desc,subcatno,spec1,spec2,spec3,isrecomm FROM shopproduct order by spno";
	private static final String GET_ALL_STMT = "SELECT spno,shopno,name,unitprice,pro_desc,subcatno,spec1,spec2,spec3,isrecomm FROM shopproduct";
	private static final String ORDER_BY = "order by spno";
	private static final String GET_ONE_STMT = "SELECT spno,shopno,name,unitprice,pic1,pic2,pic3,pmime1,pmime2,pmime3,pro_desc,subcatno,spec1,spec2,spec3,isrecomm FROM shopproduct where spno = ?";
	private static final String DELETE = "DELETE FROM shopproduct where spno = ?";
	private static final String UPDATE = "UPDATE shopproduct set shopno=?,name=?,unitprice=?,pic1=?,pic2=?,pic3=?,pmime1=?,pmime2=?,pmime3=?,pro_desc=?,subcatno=?,spec1=?,spec2=?,spec3=?,isrecomm=? where spno = ?";
	private static final String UPDATE_BY_EDIT = "UPDATE shopproduct set name=?,unitprice=?,pro_desc=?,subcatno=?,spec1=?,spec2=?,spec3=?,isrecomm=?";

	@Override
	public int insert(ShopproductVO spVO) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		Connection con = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, spVO.getShopno());
			pstmt.setString(2, spVO.getName());
			pstmt.setDouble(3, spVO.getUnitprice());
			pstmt.setBytes(4, spVO.getPic1());
			pstmt.setBytes(5, spVO.getPic2());
			pstmt.setBytes(6, spVO.getPic3());
			pstmt.setString(7, spVO.getPmime1());
			pstmt.setString(8, spVO.getPmime2());
			pstmt.setString(9, spVO.getPmime3());
			pstmt.setString(10, spVO.getPro_desc());
			pstmt.setInt(11, spVO.getSubcatno());
			pstmt.setString(12, spVO.getSpec1());
			pstmt.setString(13, spVO.getSpec2());
			pstmt.setString(14, spVO.getSpec3());
			pstmt.setInt(15, spVO.getIsrecomm());

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
	public int update(ShopproductVO spVO) {
		// int updateCount = 0;
		// PreparedStatement pstmt = null;
		// Connection con = null;
		//
		// try {
		// con = ds.getConnection();
		// pstmt = con.prepareStatement(UPDATE);
		//
		// pstmt.setInt(1, spVO.getShopno());
		// pstmt.setString(2, spVO.getName());
		// pstmt.setDouble(3, spVO.getUnitprice());
		// pstmt.setBytes(4, spVO.getPic1());
		// pstmt.setBytes(5, spVO.getPic2());
		// pstmt.setBytes(6, spVO.getPic3());
		// pstmt.setString(7, spVO.getPmime1());
		// pstmt.setString(8, spVO.getPmime2());
		// pstmt.setString(9, spVO.getPmime3());
		// pstmt.setString(10, spVO.getPro_desc());
		// pstmt.setInt(11, spVO.getSubcatno());
		// pstmt.setString(12, spVO.getSpec1());
		// pstmt.setString(13, spVO.getSpec2());
		// pstmt.setString(14, spVO.getSpec3());
		// pstmt.setInt(15, spVO.getIsrecomm());
		// pstmt.setInt(16, spVO.getSpno());
		//
		// updateCount = pstmt.executeUpdate();
		//
		// } catch (SQLException se) {
		// throw new RuntimeException("A database error occured. "
		// + se.getMessage());
		// } finally {
		// if (pstmt != null) {
		// try {
		// pstmt.close();
		// } catch (SQLException se) {
		// se.printStackTrace(System.err);
		// }
		// }
		// }
		// return updateCount;
		int updateCount = 0;
		PreparedStatement pstmt = null;
		Connection con = null;
		StringBuilder sql = new StringBuilder(UPDATE_BY_EDIT);
		try {
			con = ds.getConnection();
			boolean hasPic1 = false, hasPic2 = false, hasPic3 = false;
			if (spVO.getPic1() != null && spVO.getPmime1() != null) {
				hasPic1 = true;
				sql.append(", pic1=?, pmime1=?");
			}
			if (spVO.getPic2() != null && spVO.getPmime2() != null) {
				hasPic2 = true;
				sql.append(", pic2=?, pmime2=?");
			}
			if (spVO.getPic3() != null && spVO.getPmime3() != null) {
				hasPic3 = true;
				sql.append(", pic3=?, pmime3=?");
			}
			pstmt = con
					.prepareStatement(sql.append(" WHERE spno=?").toString());
			// "UPDATE shopproduct set name=?,unitprice=?,pro_desc=?,subcatno=?,spec1=?,spec2=?,spec3=?,isrecomm=?";
			pstmt.setString(1, spVO.getName());
			pstmt.setDouble(2, spVO.getUnitprice());
			pstmt.setString(3, spVO.getPro_desc());
			pstmt.setInt(4, spVO.getSubcatno());
			pstmt.setString(5, spVO.getSpec1());
			pstmt.setString(6, spVO.getSpec2());
			pstmt.setString(7, spVO.getSpec3());
			pstmt.setInt(8, spVO.getIsrecomm());
			int index = 9;
			if (hasPic1) {
				pstmt.setBytes(index++, spVO.getPic1());
				pstmt.setString(index++, spVO.getPmime1());
			}
			if (hasPic2) {
				pstmt.setBytes(index++, spVO.getPic2());
				pstmt.setString(index++, spVO.getPmime2());
			}
			if (hasPic3) {
				pstmt.setBytes(index++, spVO.getPic3());
				pstmt.setString(index++, spVO.getPmime3());
			}

			pstmt.setInt(index, spVO.getSpno());

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
		ShopproductVO spVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, spno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				spVO = retrieveCurrentRow(rs, true);

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
		return spVO;

	}

	private ShopproductVO retrieveCurrentRow(ResultSet rs,
			boolean includePicture) throws SQLException {
		ShopproductVO spVO = new ShopproductVO();
		spVO.setSpno(rs.getInt("spno"));
		spVO.setShopno(rs.getInt("shopno"));
		spVO.setName(rs.getString("name"));
		spVO.setUnitprice(rs.getDouble("unitprice"));
		if (includePicture) {
			spVO.setPic1(rs.getBytes("pic1"));
			spVO.setPic2(rs.getBytes("pic2"));
			spVO.setPic3(rs.getBytes("pic3"));
			spVO.setPmime1(rs.getString("pmime1"));
			spVO.setPmime2(rs.getString("pmime2"));
			spVO.setPmime3(rs.getString("pmime3"));
		}
		spVO.setPro_desc(rs.getString("pro_desc"));
		spVO.setSubcatno(rs.getInt("subcatno"));
		spVO.setSpec1(rs.getString("spec1"));
		spVO.setSpec2(rs.getString("spec2"));
		spVO.setSpec3(rs.getString("spec3"));
		spVO.setIsrecomm(rs.getInt("isrecomm"));
		return spVO;
	}

	@Override
	public List<ShopproductVO> getAll() {
		List<ShopproductVO> list = new ArrayList<ShopproductVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT + " " + ORDER_BY);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ShopproductVO spVO = retrieveCurrentRow(rs, false);
				list.add(spVO); // Store the row in the List
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
	public List<ShopproductVO> getAll(Map<String, String[]> map) {
		List<ShopproductVO> list = new ArrayList<ShopproductVO>();
		

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			String finalSQL = GET_ALL_STMT + " "
					+ jdbcUtil_CompositeQuery_Shopproduct
							.get_WhereCondition(map) + ORDER_BY;
			pstmt = con.prepareStatement(finalSQL);
			System.out.println("ShopproductDAO: ●●finalSQL = " + finalSQL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ShopproductVO spVO = retrieveCurrentRow(rs, false);
				list.add(spVO); // Store the row in the List
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
	public List<ShopproductVO> getByshop(Integer shopno) {
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("shopno", new String[] { shopno.toString() });
		return getAll(map);
	}

}
