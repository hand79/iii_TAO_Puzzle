package com.tao.cases.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.tao.jimmy.util.model.ColumnValue;
import com.tao.jimmy.util.model.ColumnValueBundle;
import com.tao.jimmy.util.model.Compare;
import com.tao.jimmy.util.model.ConnectionProvider;
import com.tao.jimmy.util.model.SQLHelper;
import com.tao.order.model.OrderDAO;
import com.tao.order.model.OrderDAOImpl;

public class CasesDAOImpl implements CasesDAO {

	private static final String INSERT_STMT = "INSERT INTO cases ("
			+ "caseno, title, memno, cpno,spno, locno, discount, "
			+ "minqty, maxqty,"
			+ " status, casedesc, ship1, shipcost1, ship2, shipcost2, threshold"
			// + "stime, etime, "
			+ ") VALUES(" + "cases_seq.NEXTVAL,?,?,?,?,?,?,"
			// + "?,?,"
			+ "?,?,?,?,?,?,?,?,?)";// no hits

	private static final String UPDATE_BY_EDIT_STMT = "UPDATE cases SET "
			+ "title=?, locno=?, discount=?, minqty=?, maxqty=?,"
			+ " casedesc=?, ship1=?, shipcost1=?, ship2=?, shipcost2=?, threshold=? "
			+ "WHERE caseno=?";

	private static final String UPDATE_STATUS_STMT = "UPDATE cases SET status=? WHERE caseno=?";
	private static final String OPEN_CASE_STMT = "UPDATE cases SET stime=?, etime=?, status=? WHERE caseno=?";

	private static final String DELETE_STMT = "DELETE FROM cases WHERE caseno=?";

	private static final String UPDATE_HITS_STMT = "UPDATE cases SET hits = hits + ? WHERE caseno=?";

	private static final String SELECT_ALL_FULLDATA = "SELECT "
			+ "caseno, title, memno, cpno, spno, locno, discount, "
			+ "stime, etime, minqty, maxqty, status, "
			+ "hits, casedesc, ship1, shipcost1, ship2, shipcost2, threshold "
			+ "FROM cases";
	private static final String[] TIMESTAMP_COLS = { "stime", "etime" };
	private static final String ORDER_BY_COL_NAME = "caseno";
	private static final String CLOSE_CASE = "UPDATE cases SET status="
			+ CasesVO.STATUS_OVER + " WHERE caseno=? AND (status="
			+ CasesVO.STATUS_PRIVATE + " OR status=" + CasesVO.STATUS_PUBLIC
			+ ")";
	private static final String CANCEL_CASE = "UPDATE cases SET status="
			+ CasesVO.STATUS_CANCELED + " WHERE caseno=? AND (status="
			+ CasesVO.STATUS_PRIVATE + " OR status=" + CasesVO.STATUS_PUBLIC
			+ " OR status=" + CasesVO.STATUS_OVER + ")";
	private static final Object UPDATE_STATUS_LOCK = new Object();

	// private static final String UPDATE_STMT =
	// "UPDATE cases SET title=?,memno=?, cpno=?, spno=?, locno=?, discount=?, stime=?, etime=?, minqty=?, maxqty=?,"
	// +
	// "status=?, hits=?, casedesc=?, ship1=?, shipcost1=?, ship2=?, shipcost2=? WHERE caseno=?";

	// private static final String SELECT_ALL_LITE =
	// "SELECT caseno, title, memno, cpno,spno,locno,discount,stime,etime,minqty,maxqty,status,"
	// + "FROM cases";

	@Override
	public Integer insert(CasesVO vo) {
		Integer genKey = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			String[] cols = { "CASENO" };
			pstmt = con.prepareStatement(INSERT_STMT, cols);
			pstmt.setString(1, vo.getTitle());
			pstmt.setInt(2, vo.getMemno());
			/**/
			pstmt.setString(3, processNullableIntColumn(vo.getCpno()));// may be
																		// null
			pstmt.setString(4, processNullableIntColumn(vo.getSpno()));// may be
																		// null
			/**/
			pstmt.setInt(5, vo.getLocno());
			pstmt.setInt(6, vo.getDiscount());
			pstmt.setInt(7, vo.getMinqty());// may be null, 0 instead
			pstmt.setInt(8, vo.getMaxqty());// may be null, 0 instead
			pstmt.setInt(9, CasesVO.STATUS_CREATED);
			pstmt.setString(10, vo.getCasedesc());
			pstmt.setString(11, vo.getShip1());
			pstmt.setInt(12, vo.getShipcost1());
			pstmt.setString(13, vo.getShip2());
			/**/
			pstmt.setString(14, processNullableIntColumn(vo.getShipcost2()));// may
																				// be
																				// null
			/**/
			pstmt.setInt(15, vo.getThreshold());
			// pstmt.setTimestamp(16, vo.getStime());
			// pstmt.setTimestamp(17, vo.getEtime());
			pstmt.executeUpdate();

			rs = pstmt.getGeneratedKeys();
			rs.next();
			genKey = rs.getInt(1);

		} catch (SQLException e) {
			e.printStackTrace(System.err);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
		return genKey;
	}

	// @Override
	// public int update(CaseVO vo) {
	// int updateCount = 0;
	// Connection con = null;
	// PreparedStatement pstmt = null;
	// try {
	// con = getConnection();
	// pstmt = con.prepareStatement(UPDATE_STMT);
	//
	// pstmt.setString(1, vo.getTitle());
	// pstmt.setInt(2, vo.getMemno());
	// pstmt.setInt(3, vo.getCpno());
	// pstmt.setInt(4, vo.getSpno());
	// pstmt.setInt(5, vo.getLocno());
	// pstmt.setInt(6, vo.getDiscount());
	// pstmt.setTimestamp(7, vo.getStime());
	// pstmt.setTimestamp(8, vo.getEtime());
	// pstmt.setInt(9, vo.getMinqty());
	// pstmt.setInt(10, vo.getMaxqty());
	// pstmt.setInt(11, vo.getStatus());
	// pstmt.setInt(12, vo.getHits());
	// pstmt.setString(13, vo.getCasedesc());
	// pstmt.setString(14, vo.getShip1());
	// pstmt.setInt(15, vo.getShipcost1());
	// pstmt.setString(16, vo.getShip2());
	// pstmt.setInt(17, vo.getShipcost2());
	// pstmt.setInt(18, vo.getCaseno());
	//
	// updateCount = pstmt.executeUpdate();
	//
	// } catch (SQLException e) {
	// e.printStackTrace(System.err);
	// } finally {
	// try {
	// if (pstmt != null) {
	// pstmt.close();
	// }
	// if (con != null) {
	// con.close();
	// }
	// } catch (Exception e) {
	// e.printStackTrace(System.err);
	// }
	// }
	// return updateCount;
	// }

	@Override
	public int updateByEdit(CasesVO vo) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(UPDATE_BY_EDIT_STMT);
			pstmt.setString(1, vo.getTitle());
			pstmt.setInt(2, vo.getLocno());
			pstmt.setInt(3, vo.getDiscount());
			pstmt.setInt(4, vo.getMinqty());
			pstmt.setInt(5, vo.getMaxqty());
			pstmt.setString(6, vo.getCasedesc());
			pstmt.setString(7, vo.getShip1());
			pstmt.setInt(8, vo.getShipcost1());
			pstmt.setString(9, vo.getShip2());
			pstmt.setInt(10, vo.getShipcost2());
			pstmt.setInt(11, vo.getThreshold());
			pstmt.setInt(12, vo.getCaseno());

			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace(System.err);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
		return updateCount;
	}

	int delete(Integer caseno) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);
			pstmt.setInt(1, caseno);
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace(System.err);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
		return updateCount;
	}

	@Override
	public int updateStatus(Integer caseno, Integer status) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(UPDATE_STATUS_STMT);
			pstmt.setInt(1, status);
			pstmt.setInt(2, caseno);
			synchronized (UPDATE_STATUS_LOCK) {
				updateCount = pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace(System.err);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
		return updateCount;
	}

	@Override
	public CasesVO findByPrimaryKey(Integer caseno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CasesVO vo = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SELECT_ALL_FULLDATA
					+ " WHERE caseno = ? ORDER BY " + ORDER_BY_COL_NAME);
			pstmt.setInt(1, caseno);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				vo = retrieveCurrentRow(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace(System.err);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
		return vo;
	}

	@Override
	public Set<CasesVO> getAll() {
		return findByWhere(new ColumnValue[] {});
	}
	@Override
	public Set<CasesVO> findByAreaSearch(Integer locnoFrom, Integer locnoTo,
			Integer subcatno) {
		Set<CasesVO> set = new LinkedHashSet<>();
		String cols = "c.caseno, c.title, c.memno, c.cpno, c.spno, c.locno, c.discount, "
				+ "c.stime, c.etime, c.minqty, c.maxqty, c.status, "
				+ "c.hits, c.casedesc, c.ship1, c.shipcost1, c.ship2, c.shipcost2, c.threshold ";
		String sqlcp = "SELECT "
				+ cols
				+ " FROM cases c INNER JOIN caseproduct cp ON c.cpno=cp.cpno WHERE c.locno >="+locnoFrom+" AND c.locno <="+locnoTo+" AND cp.subcatno="
				+ subcatno + " AND c.status=" + CasesVO.STATUS_PUBLIC;
		String sqlsp = "SELECT "
				+ cols
				+ " FROM cases c INNER JOIN shopproduct sp ON c.spno=sp.spno WHERE c.locno >="+locnoFrom+" AND c.locno <="+locnoTo+" AND sp.subcatno="
				+ subcatno + " AND c.status=" + CasesVO.STATUS_PUBLIC;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sqlcp);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CasesVO vo = retrieveCurrentRow(rs);
				set.add(vo);
			}
			rs.close();
			pstmt.close();
			pstmt = con.prepareStatement(sqlsp);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CasesVO vo = retrieveCurrentRow(rs);
				set.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace(System.err);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}

		return set;
	}

	@Override
	public Set<CasesVO> findByCreator(Integer memno) {
		ColumnValue c = new ColumnValue("memno", memno.toString());
		return findByWhere(c);
	}

	@Override
	public Set<CasesVO> findByCreator(Integer memno, boolean reverse) {

		return null;
	}

	@Override
	public Set<CasesVO> findByShopProductNums(Integer... spnos) {
		ColumnValue[] whereArgs = new ColumnValue[spnos.length];
		for (int i = 0; i < spnos.length; i++) {
			whereArgs[i] = new ColumnValue("spno", spnos[i].toString());
		}
		return findByWhere(false, whereArgs);
	}

	@Override
	public Set<CasesVO> findByCaseProductNums(Integer... cpnos) {
		ColumnValue[] whereArgs = new ColumnValue[cpnos.length];
		for (int i = 0; i < cpnos.length; i++) {
			whereArgs[i] = new ColumnValue("cpno", cpnos[i].toString());
		}
		return findByWhere(false, whereArgs);
	}

	@Override
	public Set<CasesVO> findByLocations(Integer... locnos) {
		ColumnValue[] whereArgs = new ColumnValue[locnos.length];
		for (int i = 0; i < locnos.length; i++) {
			whereArgs[i] = new ColumnValue("locno", locnos[i].toString());
		}
		return findByWhere(false, whereArgs);
	}

	@Override
	public Set<CasesVO> findByStatuses(Integer... statuses) {
		ColumnValue[] whereArgs = new ColumnValue[statuses.length];
		for (int i = 0; i < statuses.length; i++) {
			whereArgs[i] = new ColumnValue("status", statuses[i].toString());
		}
		return findByWhere(false, whereArgs);
	}

	@Override
	public Set<CasesVO> findExcludeStatuses(Integer... statuses) {
		ColumnValue[] whereArgs = new ColumnValue[statuses.length];
		for (int i = 0; i < statuses.length; i++) {
			whereArgs[i] = new ColumnValue("status", statuses[i].toString(),
					Compare.NotEqual);
		}
		return findByWhere(false, whereArgs);
	}

	@Override
	public Set<CasesVO> findByTitleKeyword(String keyword, Integer locnoFrom, Integer locnoTo) {
		ColumnValue c = new ColumnValue("title", "%" + keyword + "%", true);
		ColumnValue c2 = new ColumnValue("status",
				Integer.toString(CasesVO.STATUS_PUBLIC));
		ColumnValue c3 = new ColumnValue("locno",locnoFrom.toString(), Compare.GTEqual);
		ColumnValue c4 = new ColumnValue("locno", locnoTo.toString(),Compare.LTEqual);
		return findByWhere(c, c2, c3, c4);
	}

	@Override
	public Set<CasesVO> findByTimeInterval(Timestamp stimefrom,
			Timestamp stimeto, Timestamp etimefrom, Timestamp etimeto) {
		List<ColumnValue> list = new ArrayList<>();
		if (stimefrom != null) {
			list.add(new ColumnValue("stime", stimefrom.toString(),
					Compare.GTEqual));
		}
		if (stimeto != null) {
			list.add(new ColumnValue("stime", stimeto.toString(),
					Compare.LTEqual));
		}
		if (etimefrom != null) {
			list.add(new ColumnValue("etime", etimefrom.toString(),
					Compare.GTEqual));
		}
		if (etimeto != null) {
			list.add(new ColumnValue("etime", stimeto.toString(),
					Compare.LTEqual));
		}

		return findByWhere(list.toArray(new ColumnValue[list.size()]));
	}

	@Override
	public Set<CasesVO> findByWhere(boolean useAnd, ColumnValue... whereArgs) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Set<CasesVO> list = new LinkedHashSet<>();
		try {
			con = getConnection();
			String sql = SQLHelper.buildQueryStmt(SELECT_ALL_FULLDATA,
					whereArgs, ORDER_BY_COL_NAME, true, useAnd);
			pstmt = con.prepareStatement(sql);
			SQLHelper.setPreparedStmt(pstmt, whereArgs, TIMESTAMP_COLS,
					SQLHelper.TIMESTAMP_COLS);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CasesVO vo = retrieveCurrentRow(rs);
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace(System.err);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
		return list;
	}

	@Override
	public Set<CasesVO> findByWhere(ColumnValue... whereArgs) {
		return findByWhere(true, whereArgs);
	}

	@Override
	public Set<CasesVO> findByCompositeQuery(
			ColumnValueBundle... whereArgsBundle) {
		return findByCompositeQuery(true, whereArgsBundle);
	}

	@Override
	public Set<CasesVO> findByCompositeQuery(boolean matchCondition,
			ColumnValueBundle... whereArgsBundle) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Set<CasesVO> list = new LinkedHashSet<>();
		try {
			con = getConnection();
			String sql = SQLHelper.buildComplicatedQueryStmt(
					SELECT_ALL_FULLDATA, ORDER_BY_COL_NAME, true, true, false,
					matchCondition, whereArgsBundle);

			pstmt = con.prepareStatement(sql);

			SQLHelper.setComplicatedPreparedStmt(pstmt, TIMESTAMP_COLS, null,
					whereArgsBundle);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				CasesVO vo = retrieveCurrentRow(rs);
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace(System.err);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
		return list;
	}

	private CasesVO retrieveCurrentRow(ResultSet rs) throws SQLException {
		CasesVO vo = new CasesVO();
		vo.setCaseno(rs.getInt("caseno"));
		vo.setTitle(rs.getString("title"));
		vo.setMemno(rs.getInt("memno"));
		vo.setCpno(rs.getInt("cpno"));
		vo.setSpno(rs.getInt("spno"));
		vo.setLocno(rs.getInt("locno"));
		vo.setDiscount(rs.getInt("discount"));
		vo.setStime(rs.getTimestamp("stime"));
		vo.setEtime(rs.getTimestamp("etime"));
		vo.setMinqty(rs.getInt("minqty"));
		vo.setMaxqty(rs.getInt("maxqty"));
		vo.setStatus(rs.getInt("status"));
		vo.setHits(rs.getInt("hits"));
		vo.setCasedesc(rs.getString("casedesc"));
		vo.setShip1(rs.getString("ship1"));
		vo.setShipcost1(rs.getInt("shipcost1"));
		vo.setShip2(rs.getString("ship2"));
		vo.setShipcost2(rs.getInt("shipcost2"));
		vo.setThreshold(rs.getInt("threshold"));
		return vo;
	}

	// private CaseVOLite retrieveCurrentRowLite(ResultSet rs) throws
	// SQLException {
	// CaseVOLite vo = new CaseVOLite();
	// vo.setCaseno(rs.getInt("caseno"));
	// vo.setTitle(rs.getString("title"));
	// vo.setMemno(rs.getInt("memno"));
	// vo.setCpno(rs.getInt("cpno"));
	// vo.setSpno(rs.getInt("spno"));
	// vo.setLocno(rs.getInt("locno"));
	// vo.setDiscount(rs.getInt("discount"));
	// vo.setStime(rs.getTimestamp("stime"));
	// vo.setEtime(rs.getTimestamp("etime"));
	// vo.setMinqty(rs.getInt("minqty"));
	// vo.setMaxqty(rs.getInt("maxqty"));
	// vo.setStatus(rs.getInt("status"));
	// return vo;
	// }

	private Connection getConnection() {
		Connection con = null;
		try {
			con = ConnectionProvider.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	private String processNullableIntColumn(Integer i) {
		String str = null;
		if (i != null && i.intValue() != 0) {
			str = i.toString();
		}
		return str;
	}

	@Override
	public int updateHits(Map<Integer, Integer> contextHitsRecord) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(UPDATE_HITS_STMT);
			Set<Entry<Integer, Integer>> entries = contextHitsRecord.entrySet();
			for (Entry<Integer, Integer> en : entries) {
				pstmt.setInt(1, en.getValue());
				pstmt.setInt(2, en.getKey());
				updateCount += pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace(System.err);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
		return updateCount;
	}

	@Override
	public boolean overCases(Integer caseno) {
		boolean flag = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(CLOSE_CASE);
			pstmt.setInt(1, caseno);
			synchronized (UPDATE_STATUS_LOCK) {
				int updateCount = pstmt.executeUpdate();
				if (updateCount != 1) {
					throw new SQLException(
							"CasesDAO::overCases: Failed to Update Case Status");
				}
				OrderDAO dao = new OrderDAOImpl();
				dao.updateStatusByCaseOver(caseno, con);
				con.commit();
				flag = true;
			}
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace(System.err);
			}
			e.printStackTrace(System.err);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
		return flag;
	}

	@Override
	public boolean cancelCase(Integer caseno) {
		boolean flag = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(CANCEL_CASE);
			pstmt.setInt(1, caseno);
			synchronized (UPDATE_STATUS_LOCK) {
				int updateCount = pstmt.executeUpdate();
				if (updateCount != 1) {
					throw new SQLException(
							"CasesDAO::cancelCases: Failed to Update Case Status");
				}
				OrderDAO dao = new OrderDAOImpl();
				dao.cancelOrderByCaseCancel(caseno, con);
				con.commit();
				flag = true;
			}
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace(System.err);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
		return flag;
	}

	@Override
	public int openCases(Integer caseno, Timestamp stime, Timestamp etime,
			Boolean isPrivate) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			con.setAutoCommit(false);
			pstmt = con
					.prepareStatement("SELECT cpno, spno FROM cases WHERE caseno=?");
			pstmt.setInt(1, caseno);
			rs = pstmt.executeQuery();
			boolean useCP = true;
			int pno = 0;
			if (rs.next()) {
				if ((pno = rs.getInt("cpno")) == 0) {
					useCP = false;
					pno = rs.getInt("spno");
				}
			} else {
				throw new SQLException(
						"CasesDAOImpl::openCases: invalid caseno");
			}
			pstmt.close();
			pstmt = con.prepareStatement(OPEN_CASE_STMT);
			int status = isPrivate ? CasesVO.STATUS_PRIVATE
					: CasesVO.STATUS_PUBLIC;
			pstmt.setTimestamp(1, stime);
			pstmt.setTimestamp(2, etime);
			pstmt.setInt(3, status);
			pstmt.setInt(4, caseno);
			synchronized (UPDATE_STATUS_LOCK) {
				updateCount = pstmt.executeUpdate();
			}
			if (updateCount != 1) {
				throw new SQLException(
						"CasesDAOImpl::openCases: update case status failed.");
			}
			if (useCP) {
				CaseProductDAO dao = new CaseProductDAOImpl();
				updateCount = dao.updateLockflagToLockIt(pno);
				if (updateCount != 1) {
					throw new SQLException(
							"CasesDAOImpl::openCases: update caseproduct status failed.");
				}
			}
			con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace(System.err);
			}
			e.printStackTrace(System.err);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
		return updateCount;
	}

}
