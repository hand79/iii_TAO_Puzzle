package com.tao.runningad.model;

import java.util.*;
import java.sql.*;
import java.sql.Date;

import javax.sql.DataSource;

import com.tao.util.model.DataSourceHolder;

public class RunningAdDAO implements RunningAdDAO_interface {

	private static DataSource ds = DataSourceHolder.getDataSource();

	private static final String INSERT_STMT = "INSERT INTO runningad (adno, adpic, mime, sdate, edate, memno, reqtime, tst, dtime, caseno) "
			+ "VALUES (runningad_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE runningad set adpic=?, mime=?, sdate=?, edate=?, memno=?, reqtime=?, tst = ?, dtime = ?, caseno = ? where adno = ?";
	private static final String DELETE = "DELETE FROM runningad where adno = ?";

	private static final String GET_ONE_STMT = "SELECT adno, adpic, mime, to_char(sdate,'yyyy-mm-dd') sdate, to_char(edate,'yyyy-mm-dd') edate, "
			+ "memno, to_char(reqtime,'yyyy-mm-dd') reqtime, tst, dtime, caseno FROM runningad where adno = ?";

	private static final String GET_ALL_STMT = "SELECT adno, mime, to_char(sdate,'yyyy-mm-dd') sdate, to_char(edate,'yyyy-mm-dd') edate, "
			+ "memno, to_char(reqtime,'yyyy-mm-dd') reqtime, tst, dtime, caseno FROM runningad order by adno";

	private static final String INSERT_FROM_USER_STMT = "INSERT INTO runningad (adno, adpic, mime, memno, reqtime, tst, dtime, caseno) VALUES (runningad_seq.NEXTVAL, ?, ?, ?, ?, 0, ?, ?)";

	private static final String UPDATE_FOR_APPROVE_1 = "UPDATE runningad set sdate=sysdate, tst = ? where adno = ?";
	private static final String UPDATE_FOR_APPROVE_2 = "UPDATE runningad set edate=sdate+dtime where adno = ?";

	private static final String UPDATE_FOR_REJECT = "UPDATE runningad set tst = ? where adno = ?";

	private static final String GET_ALL_BY_MEMNO_STMT = "SELECT adno, adpic, mime, to_char(sdate,'yyyy-mm-dd') sdate, to_char(edate,'yyyy-mm-dd') edate, "
			+ "memno, to_char(reqtime,'yyyy-mm-dd') reqtime, tst, dtime, caseno FROM runningad where memno = ? order by adno";
	
	private static final String GET_ALL_BY_TST_STMT = "SELECT adno, to_char(sdate,'yyyy-mm-dd') sdate, to_char(edate,'yyyy-mm-dd') edate, "
			+ "memno, to_char(reqtime,'yyyy-mm-dd') reqtime, tst, dtime, caseno FROM runningad where tst = ? order by adno";

	private static final String GET_ALL_ACTIVE_ADS_STMT = "SELECT adno, caseno FROM runningad where tst = 1 order by adno";

	private static final String REMOVE_EXPIRED_ADS_STMT = "UPDATE runningad set tst=2 where adno=?";

	private static final String GET_NOW_STMT = "SELECT to_char(sysdate,'yyyy-mm-dd') now from dual";

	@Override
	public int insert(RunningAdVO runningAdVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			String cols[] = { "adno" };
			pstmt = con.prepareStatement(INSERT_STMT, cols);

			pstmt.setBytes(1, runningAdVO.getAdpic());
			pstmt.setString(2, runningAdVO.getMime());
			pstmt.setDate(3, runningAdVO.getSdate());
			pstmt.setDate(4, runningAdVO.getEdate());
			pstmt.setInt(5, runningAdVO.getMemno());
			pstmt.setDate(6, runningAdVO.getReqtime());
			pstmt.setInt(7, runningAdVO.getTst());
			pstmt.setInt(8, runningAdVO.getDtime());
			pstmt.setInt(9, runningAdVO.getCaseno());

			updateCount = pstmt.executeUpdate();

			ResultSet rs = pstmt.getGeneratedKeys();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			if (rs.next()) {
				do {
					for (int i = 1; i <= columnCount; i++) {
						String key = rs.getString(i);
						System.out.println("自增主鍵值(" + i + ") = " + key
								+ "(剛新增成功的員工編號)");
					}
				} while (rs.next());
			} else {
				System.out.println("NO KEYS WERE GENERATED.");
			}
			rs.close();

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
		return updateCount;
	}

	@Override
	public int update(RunningAdVO runningAdVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setBytes(1, runningAdVO.getAdpic());
			pstmt.setString(2, runningAdVO.getMime());
			pstmt.setDate(3, runningAdVO.getSdate());
			pstmt.setDate(4, runningAdVO.getEdate());
			pstmt.setInt(5, runningAdVO.getMemno());
			pstmt.setDate(6, runningAdVO.getReqtime());
			pstmt.setInt(7, runningAdVO.getTst());
			pstmt.setInt(8, runningAdVO.getDtime());
			pstmt.setInt(9, runningAdVO.getCaseno());
			pstmt.setInt(10, runningAdVO.getAdno());

			updateCount = pstmt.executeUpdate();

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
		return updateCount;
	}

	@Override
	public int delete(Integer adno) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, adno);

			updateCount = pstmt.executeUpdate();

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
		return updateCount;
	}

	@Override
	public RunningAdVO findByPrimaryKey(Integer adno) {
		RunningAdVO runningAdVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, adno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// runningAdVO Domain objects
				runningAdVO = new RunningAdVO();
				runningAdVO.setAdno(rs.getInt("adno"));
				// runningAdVO.setAdpic(rs.getBinaryStream("adpic"));
				runningAdVO.setAdpic(rs.getBytes("adpic"));
				runningAdVO.setMime(rs.getString("mime"));
				runningAdVO.setSdate(rs.getDate("sdate"));
				runningAdVO.setEdate(rs.getDate("edate"));
				runningAdVO.setMemno(rs.getInt("memno"));
				runningAdVO.setReqtime(rs.getDate("reqtime"));
				runningAdVO.setTst(rs.getInt("tst"));
				runningAdVO.setDtime(rs.getInt("dtime"));
				runningAdVO.setCaseno(rs.getInt("caseno"));
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
		return runningAdVO;
	}

	@Override
	public List<RunningAdVO> getAll() {
		List<RunningAdVO> list = new ArrayList<RunningAdVO>();
		RunningAdVO runningAdVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				runningAdVO = new RunningAdVO();
				runningAdVO.setAdno(rs.getInt("adno"));
				runningAdVO.setMime(rs.getString("mime"));
				runningAdVO.setSdate(rs.getDate("sdate"));
				runningAdVO.setEdate(rs.getDate("edate"));
				runningAdVO.setMemno(rs.getInt("memno"));
				runningAdVO.setReqtime(rs.getDate("reqtime"));
				runningAdVO.setTst(rs.getInt("tst"));
				runningAdVO.setDtime(rs.getInt("dtime"));
				runningAdVO.setCaseno(rs.getInt("caseno"));
				list.add(runningAdVO); // Store the row in the vector
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
	public int insertFromUser(RunningAdVO runningAdVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			System.out.println("before get connection");
			con = ds.getConnection();
			String cols[] = { "adno" };
			pstmt = con.prepareStatement(INSERT_FROM_USER_STMT, cols);
			System.out.println("after pstmt");
			pstmt.setBytes(1, runningAdVO.getAdpic());
			pstmt.setString(2, runningAdVO.getMime());
			pstmt.setInt(3, runningAdVO.getMemno());
			pstmt.setDate(4, new java.sql.Date(System.currentTimeMillis()));
			pstmt.setInt(5, runningAdVO.getDtime());
			pstmt.setInt(6, runningAdVO.getCaseno());
			System.out.println("Before insert");
			updateCount = pstmt.executeUpdate();

			ResultSet rs = pstmt.getGeneratedKeys();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			if (rs.next()) {
				do {
					for (int i = 1; i <= columnCount; i++) {
						String key = rs.getString(i);
						System.out.println("自增主鍵值(" + i + ") = " + key
								+ "(剛新增成功的員工編號)");
					}
				} while (rs.next());
			} else {
				System.out.println("NO KEYS WERE GENERATED.");
			}
			rs.close();

		} catch (SQLException se) {
			se.printStackTrace();
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
		return updateCount;
	}

	@Override
	public int updateToManage(RunningAdVO runningAdVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;

		try {

			con = ds.getConnection();

			int tst = runningAdVO.getTst();
			if (tst == 1) {
				pstmt1 = con.prepareStatement(UPDATE_FOR_APPROVE_1);
				pstmt1.setInt(1, runningAdVO.getTst());
				pstmt1.setInt(2, runningAdVO.getAdno());
				updateCount = pstmt1.executeUpdate();

				pstmt2 = con.prepareStatement(UPDATE_FOR_APPROVE_2);
				pstmt2.setInt(1, runningAdVO.getAdno());
				updateCount += pstmt2.executeUpdate();
			} else if (tst == 3) {
				pstmt1 = con.prepareStatement(UPDATE_FOR_REJECT);
				pstmt1.setInt(1, runningAdVO.getTst());
				pstmt1.setInt(2, runningAdVO.getAdno());
				updateCount = pstmt1.executeUpdate();
			}

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt1 != null) {
				try {
					pstmt1.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}

			if (pstmt2 != null) {
				try {
					pstmt2.close();
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
		return updateCount;
	}

	@Override
	public List<RunningAdVO> getAllByMemno(Integer memno) {
		List<RunningAdVO> list = new ArrayList<RunningAdVO>();
		RunningAdVO runningAdVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_BY_MEMNO_STMT);
			pstmt.setInt(1, memno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				runningAdVO = new RunningAdVO();
				runningAdVO.setAdno(rs.getInt("adno"));
				runningAdVO.setAdpic(rs.getBytes("adpic"));
				runningAdVO.setMime(rs.getString("mime"));
				runningAdVO.setSdate(rs.getDate("sdate"));
				runningAdVO.setEdate(rs.getDate("edate"));
				runningAdVO.setMemno(rs.getInt("memno"));
				runningAdVO.setReqtime(rs.getDate("reqtime"));
				runningAdVO.setTst(rs.getInt("tst"));
				runningAdVO.setDtime(rs.getInt("dtime"));
				runningAdVO.setCaseno(rs.getInt("caseno"));
				list.add(runningAdVO); // Store the row in the vector
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
	public List<RunningAdVO> getAllActiveAds() {
		List<RunningAdVO> list = new ArrayList<RunningAdVO>();
		RunningAdVO runningAdVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_ACTIVE_ADS_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				runningAdVO = new RunningAdVO();
				runningAdVO.setAdno(rs.getInt("adno"));
				runningAdVO.setCaseno(rs.getInt("caseno"));
				list.add(runningAdVO); // Store the row in the vector
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
	public int RemoveExpiredAds(Integer adno) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(REMOVE_EXPIRED_ADS_STMT);

			pstmt.setInt(1, adno);

			updateCount = pstmt.executeUpdate();

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
		return updateCount;
	}

	@Override
	public Date getSysDate() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Date now = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_NOW_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				now = rs.getDate("now");
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
		return now;
	}

	@Override
	public List<RunningAdVO> getAllByStatus(Integer tst) {
		List<RunningAdVO> list = new ArrayList<RunningAdVO>();
		RunningAdVO runningAdVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_BY_TST_STMT);
			pstmt.setInt(1, tst);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				runningAdVO = new RunningAdVO();
				runningAdVO.setAdno(rs.getInt("adno"));
				runningAdVO.setSdate(rs.getDate("sdate"));
				runningAdVO.setEdate(rs.getDate("edate"));
				runningAdVO.setMemno(rs.getInt("memno"));
				runningAdVO.setReqtime(rs.getDate("reqtime"));
				runningAdVO.setTst(rs.getInt("tst"));
				runningAdVO.setDtime(rs.getInt("dtime"));
				runningAdVO.setCaseno(rs.getInt("caseno"));
				list.add(runningAdVO); // Store the row in the vector
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
