package com.tao.news.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.sql.DataSource;

import com.tao.util.model.DataSourceHolder;


public class NewsDAO implements NewsDAO_interface {

	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	private static DataSource ds = DataSourceHolder.getDataSource();

	private static final String INSERT_STMT = "INSERT INTO news (newsno,title,text,pubtime) VALUES (news_seq.NEXTVAL, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT newsno,title,text,pubtime FROM news order by pubtime desc, newsno desc";
	private static final String GET_ONE_STMT = "SELECT newsno,title,text,pubtime FROM news where newsno = ?";
	private static final String DELETE = "DELETE FROM news where newsno = ?";
	private static final String UPDATE = "UPDATE news set title=?, text=?, pubtime=? where newsno = ?";

	@Override
	public void insert(NewsVO newsVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			
			pstmt.setString(1, newsVO.getTitle());
			pstmt.setString(2, newsVO.getText());
			pstmt.setTimestamp(3, newsVO.getPubtime());


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
	public void update(NewsVO newsVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			
			pstmt.setString(1, newsVO.getTitle());
			pstmt.setString(2, newsVO.getText());
			pstmt.setTimestamp(3, newsVO.getPubtime());
			pstmt.setInt(4, newsVO.getNewsno());

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
	public void delete(Integer newsno) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, newsno);

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
	public NewsVO findByPrimaryKey(Integer newsno) {
		NewsVO newsVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, newsno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// newsVO 也稱為 Domain objects
				newsVO = new NewsVO();
				newsVO.setNewsno(rs.getInt("newsno"));
				newsVO.setTitle(rs.getString("title"));
				newsVO.setText(rs.getString("text"));
				newsVO.setPubtime(rs.getTimestamp("pubtime"));

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
		return newsVO;
	}

	@Override
	public List<NewsVO> getAll() {
		List<NewsVO> list = new ArrayList<NewsVO>();
		NewsVO newsVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				newsVO = new NewsVO();
				newsVO.setNewsno(rs.getInt("newsno"));
				newsVO.setTitle(rs.getString("title"));
				newsVO.setText(rs.getString("text"));
				newsVO.setPubtime(rs.getTimestamp("pubtime"));
				list.add(newsVO); // Store the row in the list
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
	public List<NewsVO> getAll(Map<String, String[]> map) {
		List<NewsVO> list = new ArrayList<NewsVO>();
		NewsVO newsVO = null;
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
			
			con = ds.getConnection();
			String finalSQL = "select * from news "
		          + jdbcUtil_CompositeQuery_News.get_WhereCondition(map)
		          + "order by newsno";
			pstmt = con.prepareStatement(finalSQL);
			System.out.println("●●finalSQL(by DAO) = "+finalSQL);
			rs = pstmt.executeQuery();
	
			while (rs.next()) {
				newsVO = new NewsVO();
				newsVO.setNewsno(rs.getInt("newsno"));
				newsVO.setTitle(rs.getString("title"));
				newsVO.setText(rs.getString("text"));
				newsVO.setPubtime(rs.getTimestamp("pubtime"));
				
				list.add(newsVO); // Store the row in the List
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
	public List<NewsVO> getByNews(Integer newsno) {
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("newsno", new String[] { newsno.toString() });
		return getAll(map);
	}

}
