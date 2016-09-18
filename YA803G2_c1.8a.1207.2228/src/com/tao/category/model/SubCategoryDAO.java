package com.tao.category.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.sql.DataSource;

import com.tao.util.model.DataSourceHolder;

public class SubCategoryDAO implements SubCategoryDAO_interface {
	private static DataSource ds = DataSourceHolder.getDataSource();
	
	private static final String INSERT_STMT = "INSERT INTO SubCategory (subcatno,subcatname,catno) VALUES (SubCategory_seq.NEXTVAL, ? , ?)";
	private static final String GET_ALL_STMT = "SELECT subcatno,subcatname,catno FROM SubCategory order by subcatno";
	private static final String GET_BY_SUBCATNAME = "SELECT subcatno,subcatname,catno FROM SubCategory where subcatname = ?";
	private static final String GET_BY_SUBCATNO = "SELECT subcatno,subcatname,catno FROM SubCategory where subcatno = ?";
	private static final String GET_ALL_SUBCATEGORY_BY_CATNO = "SELECT subcatno,subcatname,catno FROM SubCategory where catno = ?";
	private static final String DELETE = "DELETE FROM SubCategory where subcatno = ?";
	private static final String UPDATE = "UPDATE SubCategory set  subcatname=?,catno=? where subcatno = ?";

	@Override
	public void insert(SubCategoryVO subCategoryVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, subCategoryVO.getSubcatname());
			pstmt.setInt(2, subCategoryVO.getCatno());
			
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
	public void update(SubCategoryVO subCategoryVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, subCategoryVO.getSubcatname());
			pstmt.setInt(2, subCategoryVO.getCatno());
			pstmt.setInt(3, subCategoryVO.getSubcatno());
			pstmt.executeUpdate();

			// Handle any driver errors
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
	public void delete(Integer subcatno) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, subcatno);

			pstmt.executeUpdate();

			// Handle any driver errors
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
	public SubCategoryVO getOneSubCategoryVOBySubCateNo(Integer subcatno) {
		// TODO Auto-generated method stub
		SubCategoryVO subCategoryVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_SUBCATNO);

			pstmt.setInt(1, subcatno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				subCategoryVO = new SubCategoryVO();
				subCategoryVO.setSubcatno(rs.getInt("subcatno"));
				subCategoryVO.setSubcatname(rs.getString("subcatname"));
				subCategoryVO.setCatno(rs.getInt("catno"));
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
		return subCategoryVO;
	}
	
	

	@Override
	public Set<SubCategoryVO> getAllSubCategoryByCatno(Integer catno) {
		// TODO Auto-generated method stub
		Set<SubCategoryVO> set = new LinkedHashSet<SubCategoryVO>();
		SubCategoryVO subCategoryVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_SUBCATEGORY_BY_CATNO);
			pstmt.setInt(1, catno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				subCategoryVO = new SubCategoryVO();
				subCategoryVO.setSubcatno(rs.getInt("subcatno"));
				subCategoryVO.setSubcatname(rs.getString("subcatname"));
				subCategoryVO.setCatno(rs.getInt("catno"));
				
				set.add(subCategoryVO); // Store the row in the list
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
		return set;
		
	}

	@Override
	public Set<SubCategoryVO> getAll() {
		// TODO Auto-generated method stub
		Set<SubCategoryVO> list = new LinkedHashSet<SubCategoryVO>();
		SubCategoryVO subCategoryVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				subCategoryVO = new SubCategoryVO();
				subCategoryVO.setSubcatno(rs.getInt("subcatno"));
				subCategoryVO.setSubcatname(rs.getString("subcatname"));
				subCategoryVO.setCatno(rs.getInt("catno"));
				
				list.add(subCategoryVO); // Store the row in the list
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
	public SubCategoryVO getOneSubCategoryVOBySubCateName(String subcatname) {
		// TODO Auto-generated method stub
		SubCategoryVO subCategoryVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_SUBCATNAME);

			pstmt.setString(1, subcatname);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				subCategoryVO = new SubCategoryVO();
				subCategoryVO.setSubcatno(rs.getInt("subcatno"));
				subCategoryVO.setSubcatname(rs.getString("subcatname"));
				subCategoryVO.setCatno(rs.getInt("catno"));
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
		return subCategoryVO;
	}

	
}
