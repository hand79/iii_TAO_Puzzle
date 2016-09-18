package com.tao.order.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.tao.jimmy.member.TinyMemberDAO;
import com.tao.jimmy.member.TinyMemberDAOImpl;
import com.tao.jimmy.util.model.ColumnValue;
import com.tao.jimmy.util.model.ColumnValueBundle;
import com.tao.jimmy.util.model.Compare;
import com.tao.jimmy.util.model.ConnectionProvider;
import com.tao.jimmy.util.model.SQLHelper;

public class OrderDAOImpl implements OrderDAO {

	private static final String INSERT_STMT = "INSERT INTO orders (ordno, bmemno, cmemno, caseno, qty, price, ordtime, status, ship)"
			+ "VALUES (orders_seq.NEXTVAL, ?,?,?,?,?,?,?,?)";

	private static final String CANCEL_STMT = "UPDATE orders SET status=? WHERE ordno=? AND ( status="
			+ OrderDAO.STATUS_CREATED +" OR status=" + OrderDAO.STATUS_CONFLICT+" )";

	private static final String UPDATE_CRATE_STMT = "UPDATE orders SET crate=?, cratedesc=? WHERE ordno=?";
	private static final String UPDATE_BRATE_STMT = "UPDATE orders SET brate=?, bratedesc=? WHERE ordno=?";
	private static final String UPDATE_STATUS_STMT = "UPDATE orders SET status=? WHERE ordno=?";

	private static final String UPDATE_STATUS_BY_CASE_OVER = "UPDATE orders SET status="
			+ OrderDAO.STATUS_ACHIEVED
			+ " WHERE caseno=? AND status="
			+ OrderDAO.STATUS_CREATED;

	private static final String UPDATE_STATUS_BY_CASE_CANCEL = "UPDATE orders SET status="
			+ OrderDAO.STATUS_CANCELED
			+ " WHERE caseno=? AND (status="
			+ OrderDAO.STATUS_CREATED
			+ " OR status="
			+ OrderDAO.STATUS_ACHIEVED + ")";

	private static final String UPDATE_MEM_POINT = "UPDATE member SET point = point + ? WHERE memno=?";

	private static final String UPDATE_WITHHOLD = "UPDATE member SET money = money + ?, withhold = withhold + ? WHERE memno=?";

	private static final String REMOVE_FELLOW = "DELETE FROM wishList WHERE memno=? AND caseno=?";

	private static final String CALCULATE_TOTAL_QTY = "SELECT SUM(qty) FROM orders WHERE caseno=? AND status!=?";

	private static final String SELECT_ALL = "SELECT "
			+ "ordno, bmemno, cmemno, caseno, qty, price, ordtime, status, "
			+ "brate, bratedesc, crate, cratedesc, ship " + "FROM orders";

	private static final String ORDER_BY_COL_NAME = "ordno";
	private static final String[] TIMESTAMP_COLS = new String[] { "ordtime" };

	private static final Object ACCESS_LOCK = new Object();

	@Override
	public Integer insert(OrderVO ordvo) {
		Integer genKey = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			con.setAutoCommit(false);
			/**** REMOVE FELLOW *****/
			pstmt = con.prepareStatement(REMOVE_FELLOW);
			pstmt.setInt(1, ordvo.getBmemno());
			pstmt.setInt(2, ordvo.getCaseno());
			int delCount = pstmt.executeUpdate();
			System.out.println("OrderDAOImpl: Remove " + delCount
					+ " record from wishlist.");
			pstmt.close();
			/**** UPDATE MONEY & WITHHOLD *****/
			pstmt = con.prepareStatement(UPDATE_WITHHOLD);
			pstmt.setInt(1, -1 * ordvo.getPrice());// money
			pstmt.setInt(2, ordvo.getPrice());// withhold
			pstmt.setInt(3, ordvo.getBmemno());
			int updateCount = pstmt.executeUpdate();
			if (updateCount != 1) {
				throw new SQLException(
						"OrderDAOImpl: Failed to update member withhold, update count="
								+ updateCount);
			}
			pstmt.close();
			/**** INSERT ORDER RECORD *****/
			String[] cols = { "ORDNO" };
			pstmt = con.prepareStatement(INSERT_STMT, cols);
			pstmt.setInt(1, ordvo.getBmemno());
			pstmt.setInt(2, ordvo.getCmemno());
			pstmt.setInt(3, ordvo.getCaseno());
			pstmt.setInt(4, ordvo.getQty());
			pstmt.setInt(5, ordvo.getPrice());
			pstmt.setTimestamp(6, ordvo.getOrdtime());
			pstmt.setInt(7, OrderDAO.STATUS_CREATED);
			pstmt.setInt(8, ordvo.getShip());
			pstmt.executeUpdate();
			con.commit();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				genKey = rs.getInt(1);
			}
		} catch (SQLException e) {
			try {
				con.rollback();
				System.out.println("OrderDAOImpl: Transaction Rollbacked");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
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

	@Override
	public int updateBRate(Integer brate, String bratedesc, Integer ordno) {
		return updateRate(brate, bratedesc, ordno, true);
	}

	@Override
	public int updateCRate(Integer crate, String cratedesc, Integer ordno) {
		return updateRate(crate, cratedesc, ordno, false);
	}

	private int updateRate(Integer rate, String ratedesc, Integer ordno,
			boolean isBuyer) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query_mem = SELECT_ALL + " WHERE ordno=?";
		String update_sql = null;
		OrderVO vo = null;
		if (isBuyer) {
			update_sql = UPDATE_BRATE_STMT;
		} else {
			update_sql = UPDATE_CRATE_STMT;
		}
		try {
			con = getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query_mem);
			pstmt.setInt(1, ordno);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				vo = retrieveCurrentRowData(rs);
			} else {
				throw new SQLException(
						"OrderDAOImpl::updateRate: invalid ordno");
			}
			rs.close();
			pstmt.close();

			pstmt = con.prepareStatement(update_sql);
			pstmt.setInt(1, rate);
			pstmt.setString(2, ratedesc);
			pstmt.setInt(3, ordno);
			updateCount = pstmt.executeUpdate();
			pstmt.close();
			if (updateCount != 1) {
				throw new SQLException(
						"OrderDAOImpl::updateRate: update order record failed.");
			}

			pstmt = con.prepareStatement(query_mem);
			Integer memno;
			if (isBuyer) {
				memno = vo.getBmemno();
			} else {
				memno = vo.getCmemno();
			}
			pstmt = con.prepareStatement(UPDATE_MEM_POINT);
			pstmt.setInt(1, rate);
			pstmt.setInt(2, memno);
			updateCount = pstmt.executeUpdate();
			if (updateCount != 1) {
				throw new SQLException(
						"OrderDAOImpl::updateRate: update member point failed.");
			}
			con.commit();
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
		return updateCount;
	}

	@Override
	public int updateStatus(Integer ordno, Integer status) {
		if (status == OrderDAO.STATUS_BUYER_COMFIRMED
				|| status == OrderDAO.STATUS_CREATOR_COMFIRMED) {
			return checkAndUpdateStatus(ordno, status);
		}
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();

			pstmt = con.prepareStatement(UPDATE_STATUS_STMT);
			pstmt.setInt(1, status);
			pstmt.setInt(2, ordno);
			synchronized (ACCESS_LOCK) {
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

	private int checkAndUpdateStatus(Integer ordno, Integer status) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt0 = null, pstmt1 = null;
		ResultSet rs = null;
		OrderVO vo = null;
		try {
			con = getConnection();
			pstmt0 = con.prepareStatement(SELECT_ALL + " WHERE ordno=?");
			pstmt0.setInt(1, ordno);
			pstmt1 = con.prepareStatement(UPDATE_STATUS_STMT);
			synchronized (ACCESS_LOCK) {
				rs = pstmt0.executeQuery();
				if (rs.next()) {
					vo = retrieveCurrentRowData(rs);
				}
				if (vo == null) {
					throw new SQLException(
							"OrderDAOImpl::checkAndUpdateStatus: invalid ordno");
				}
				pstmt0.close();

				if ((vo.getStatus() == OrderDAO.STATUS_CREATOR_COMFIRMED && status == OrderDAO.STATUS_BUYER_COMFIRMED)
						|| (vo.getStatus() == OrderDAO.STATUS_BUYER_COMFIRMED && status == OrderDAO.STATUS_CREATOR_COMFIRMED)) {
					status = OrderDAO.STATUS_BOTH_COMFIRMED;
				}
				pstmt1.setInt(1, status);
				pstmt1.setInt(2, ordno);

				updateCount = pstmt1.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace(System.err);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt0 != null) {
					pstmt0.close();
				}
				if (pstmt1 != null) {
					pstmt1.close();
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
	public void updateStatusByCaseOver(Integer caseno, Connection con)
			throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(UPDATE_STATUS_BY_CASE_OVER);
			pstmt.setInt(1, caseno);
			synchronized (ACCESS_LOCK) {
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			throw new SQLException("Update Order Status Failed. \n"
					+ e.getMessage());
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
	}

	@Override
	public int finishOrders(List<OrderVO> list) {

		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt0 = null, pstmt1 = null;
		try {
			con = getConnection();
			pstmt0 = con.prepareStatement(UPDATE_STATUS_STMT);
			// UPDATE orders SET status=? WHERE ordno=?
			pstmt1 = con.prepareStatement(UPDATE_WITHHOLD);
			// UPDATE member SET money = money + ?, withhold = withhold + ?
			// WHERE memno=?
			synchronized (ACCESS_LOCK) {
				for (OrderVO ovo : list) {
					if (ovo.getStatus() != OrderDAO.STATUS_BOTH_COMFIRMED) {
						continue;
					}
					try {
						con.setAutoCommit(false);
						int returnedInt = 0;
						pstmt0.setInt(1, OrderDAO.STATUS_COMPLETED);
						pstmt0.setInt(2, ovo.getOrdno());
						returnedInt += pstmt0.executeUpdate();

						int price = ovo.getPrice();
						// money = money + 0
						pstmt1.setInt(1, 0);
						// withhold = withhold + (-1* price)
						pstmt1.setInt(2, -1 * price);
						pstmt1.setInt(3, ovo.getBmemno());
						returnedInt += pstmt1.executeUpdate();

						// money = money + price
						pstmt1.setInt(1, price);
						// withhold = withhold + 0
						pstmt1.setInt(2, 0);
						pstmt1.setInt(3, ovo.getCmemno());
						returnedInt += pstmt1.executeUpdate();
						if (returnedInt != 3) {
							con.rollback();
							System.out
									.println("OrderDAOImpl::finishOrders: finish order "
											+ ovo.getOrdno()
											+ " failed, "
											+ "at least one of three update failed or not correct.");
						} else {
							con.commit();
						}
					} catch (SQLException e) {
						try {
							con.rollback();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						System.out
								.println("OrderDAOImpl::finishOrders: finish order "
										+ ovo.getOrdno()
										+ " failed, "
										+ e.getMessage());
					}
					updateCount++;
				}// end of for loop
			}// end of synchronized block

		} catch (SQLException e) {
			e.printStackTrace(System.err);
		} finally {
			try {
				if (pstmt0 != null) {
					pstmt0.close();
				}
				if (pstmt1 != null) {
					pstmt1.close();
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
	public int cancelOrder(Integer ordno) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt0 = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		OrderVO ordvo = null;

		try {
			con = getConnection();
			pstmt0 = con.prepareStatement(SELECT_ALL + " WHERE ordno=?");
			pstmt0.setInt(1, ordno);
			pstmt1 = con.prepareStatement(CANCEL_STMT);
			pstmt1.setInt(1, OrderDAO.STATUS_CANCELED);
			pstmt1.setInt(2, ordno);
			pstmt2 = con.prepareStatement(UPDATE_WITHHOLD);
			con.setAutoCommit(false);
			synchronized (ACCESS_LOCK) {
				rs = pstmt0.executeQuery();
				if (rs.next()) {
					ordvo = retrieveCurrentRowData(rs);
				} else {
					throw new SQLException(
							"Cancel Order Failed: could not found order.");
				}
				/**** UPDATE ORDER STATUS *****/
				updateCount = pstmt1.executeUpdate();
				if (updateCount == 0) {
					throw new SQLException(
							"Cancel Order Failed: the order does not allow to be canceled.");
				}
				/**** UPDATE MEMBER MONEY *****/
				pstmt2.setInt(1, ordvo.getPrice());// money
				pstmt2.setInt(2, -1 * ordvo.getPrice());// withhold
				pstmt2.setInt(3, ordvo.getBmemno());
				updateCount = pstmt2.executeUpdate();
				/**** VALIDATE UPDATE COUNT *****/
				if (updateCount != 1) {
					throw new SQLException(
							"Cancel Order Failed: could not update member money.");
				}
				/**** COMMIT TRANSACTION *****/
				con.commit();
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
				if (rs != null) {
					rs.close();
				}
				if (pstmt1 != null) {
					pstmt1.close();
				}
				if (pstmt2 != null) {
					pstmt2.close();
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
	public void cancelOrderByCaseCancel(Integer caseno, Connection con)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(SELECT_ALL + " WHERE caseno=?");
			pstmt.setInt(1, caseno);
			rs = pstmt.executeQuery();
			List<OrderVO> list = new ArrayList<>();
			while (rs.next()) {
				list.add(retrieveCurrentRowData(rs));
			}
			pstmt.close();

			pstmt = con.prepareStatement(UPDATE_STATUS_BY_CASE_CANCEL);
			pstmt.setInt(1, caseno);
			synchronized (ACCESS_LOCK) {
				int updateCount = pstmt.executeUpdate();
				if(updateCount != list.size()){
					throw new SQLException("::cancelOrderByCaseCancel: update count does not match with expectation.");
				}
				TinyMemberDAO tmemdao = new TinyMemberDAOImpl();
				tmemdao.batchDecreaseWithholdByCancelCase(list, con);
			}
		} catch (SQLException e) {
			throw new SQLException(
					"::cancelOrderByCaseCancel: Update Order Status Failed. \n"
							+ e.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
	}

	@Override
	public OrderVO findByPrimaryKey(Integer ordno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OrderVO vo = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SELECT_ALL
					+ " WHERE ordno=? ORDER BY " + ORDER_BY_COL_NAME);
			pstmt.setInt(1, ordno);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				vo = retrieveCurrentRowData(rs);
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
		return vo;
	}

	@Override
	public List<OrderVO> findByBuyer(Integer memno, boolean match,
			int... filter) {
		List<ColumnValue> list = new ArrayList<>();
		list.add(new ColumnValue("bmemno", Integer.toString(memno)));
		Compare c = match ? Compare.Equal : Compare.NotEqual;
		if (filter != null) {
			for (int i : filter) {
				list.add(new ColumnValue("status", Integer.toString(i), c));
			}
		}
		return findByWhere(list.toArray(new ColumnValue[list.size()]));
	}

	@Override
	public List<OrderVO> findByCreator(Integer cemno) {
		ColumnValue cv = new ColumnValue("cmemno", Integer.toString(cemno));
		return findByWhere(cv);
	}

	@Override
	public List<OrderVO> findByCase(Integer caseno) {
		ColumnValue cv = new ColumnValue("caseno", Integer.toString(caseno));
		return findByWhere(cv);
	}

	@Override
	public List<OrderVO> findByCase(Integer caseno, Integer status,
			boolean matchStatus) {
		Compare c;
		if (matchStatus) {
			c = Compare.Equal;
		} else {
			c = Compare.NotEqual;
		}
		ColumnValue cv1 = new ColumnValue("caseno", Integer.toString(caseno));
		ColumnValue cv2 = new ColumnValue("status", Integer.toString(status), c);
		return findByWhere(cv1, cv2);
	}

	@Override
	public List<OrderVO> findByTimeInterval(Timestamp from) {

		return findByTimeInterval(from,
				new Timestamp(new java.util.Date().getTime()));
	}

	@Override
	public List<OrderVO> findByTimeInterval(Timestamp from, Timestamp to) {
		ColumnValue c1 = new ColumnValue("ordtime", from.toString(),
				Compare.GreaterThan);
		ColumnValue c2 = new ColumnValue("ordtime", to.toString(),
				Compare.LessTahn);
		ColumnValue[] whereArgs = new ColumnValue[] { c1, c2 };
		String sql = SQLHelper.buildQueryStmt(SELECT_ALL, whereArgs,
				ORDER_BY_COL_NAME, true);
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<OrderVO> list = new ArrayList<>();
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);

			SQLHelper.setPreparedStmt(pstmt, whereArgs,
					new String[] { "ordtime" }, SQLHelper.TIMESTAMP_COLS);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				OrderVO vo = retrieveCurrentRowData(rs);
				list.add(vo);
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
		return list;
	}

	@Override
	public List<OrderVO> findByStatus(Integer status) {
		ColumnValue cv = new ColumnValue("STATUS", Integer.toString(status));
		return findByWhere(cv);
	}

	@Override
	public List<OrderVO> findByWhere(ColumnValue... whereArgs) {
		String sql = SQLHelper.buildQueryStmt(SELECT_ALL, whereArgs,
				ORDER_BY_COL_NAME, true);
		List<OrderVO> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			SQLHelper.setPreparedStmt(pstmt, whereArgs, TIMESTAMP_COLS, null);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				OrderVO vo = retrieveCurrentRowData(rs);
				list.add(vo);
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

		return list;
	}

	@Override
	public List<OrderVO> findByPrice(Integer price, Compare c) {

		ColumnValue cv = new ColumnValue("price", price.toString(), c);
		List<OrderVO> list = findByWhere(cv);
		return list;
	}

	@Override
	public List<OrderVO> findByCompositeQuery(boolean matchCondition,
			ColumnValueBundle... whereArgs) {
		List<OrderVO> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			String sql = SQLHelper.buildComplicatedQueryStmt(SELECT_ALL,
					ORDER_BY_COL_NAME, true, true, false, matchCondition,
					whereArgs);
			pstmt = con.prepareStatement(sql);
			SQLHelper.setComplicatedPreparedStmt(pstmt, TIMESTAMP_COLS, null,
					whereArgs);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				OrderVO vo = retrieveCurrentRowData(rs);
				list.add(vo);
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

		return list;
	}

	@Override
	public List<OrderVO> findByCompositeQuery(ColumnValueBundle... whereArgs) {
		return findByCompositeQuery(true, whereArgs);
	}

	@Override
	public List<OrderVO> getAll() {
		List<OrderVO> list = findByWhere(new ColumnValue[0]);
		return list;
	}

	@Override
	public Integer getTotalOrderQty(Integer caseno, Integer status) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer qty = 0;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(CALCULATE_TOTAL_QTY);
			pstmt.setInt(1, caseno);
			pstmt.setInt(2, status);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				qty = rs.getInt(1);
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
		return qty;
	}

	private OrderVO retrieveCurrentRowData(ResultSet rs) throws SQLException {
		OrderVO vo = new OrderVO();

		vo.setOrdno(rs.getInt("ORDNO"));
		vo.setBmemno(rs.getInt("BMEMNO"));
		vo.setCmemno(rs.getInt("CMEMNO"));
		vo.setCaseno(rs.getInt("CASENO"));
		vo.setQty(rs.getInt("QTY"));
		vo.setPrice(rs.getInt("PRICE"));
		vo.setOrdtime(rs.getTimestamp("ORDTIME"));
		vo.setStatus(rs.getInt("STATUS"));
		vo.setBrate(getNullableInteger(rs, "BRATE"));
		vo.setBrateDesc(rs.getString("BRATEDESC"));
		vo.setCrate(getNullableInteger(rs, "CRATE"));
		vo.setCrateDesc(rs.getString("CRATEDESC"));
		vo.setShip(rs.getInt("SHIP"));
		return vo;
	}

	private Integer getNullableInteger(ResultSet rs, String colname)
			throws SQLException {
		Integer i = null;
		String s = rs.getString(colname);
		if (s != null) {
			i = new Integer(s);
		}
		return i;
	}

	private Connection getConnection() {
		Connection con = null;
		try {
			con = ConnectionProvider.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

}
