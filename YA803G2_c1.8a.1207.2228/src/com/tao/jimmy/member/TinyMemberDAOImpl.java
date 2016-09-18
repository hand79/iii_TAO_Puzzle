package com.tao.jimmy.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.tao.jimmy.util.model.ConnectionProvider;
import com.tao.order.model.OrderVO;

public class TinyMemberDAOImpl implements TinyMemberDAO {
	private static final int INCREASE = 0;
	private static final int DECREASE = 1;
	private String getAll = "SELECT memno, memid, point FROM member";
	private String orderBy = "ORDER BY memno";
	private String updateWithhold = "UPDATE member SET money = money + ?, withhold = withhold + ? WHERE memno=?";
	private String changeMoney = "UPDATE member SET money = money + ? WHERE memno=?";
	private static final Object ACCESS_LOCK = new Object();

	@Override
	public TinyMemberVO findByPrimeryKey(Integer memno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		TinyMemberVO vo = null;
		try {
			con = ConnectionProvider.getConnection();
			pstmt = con.prepareStatement(getAll + " WHERE memno=? " + orderBy);
			pstmt.setInt(1, memno);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				vo = retrieveCurrentRow(rs);
			}
		} catch (Exception e) {
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
	public Set<TinyMemberVO> getAll() {
		Set<TinyMemberVO> set = new LinkedHashSet<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionProvider.getConnection();
			pstmt = con.prepareStatement(getAll + " " + orderBy);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				TinyMemberVO vo = retrieveCurrentRow(rs);
				set.add(vo);
			}
		} catch (Exception e) {
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

	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private TinyMemberVO retrieveCurrentRow(ResultSet rs) throws SQLException {
		TinyMemberVO vo = new TinyMemberVO();
		vo.setMemid(rs.getString("memid"));
		vo.setMemno(rs.getInt("memno"));
		vo.setPoint(rs.getInt("point"));
		return vo;
	}

	@Override
	public Set<TinyMemberVO> findByMemId(String idKeyword) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Set<TinyMemberVO> set = new LinkedHashSet<>();
		try {
			con = ConnectionProvider.getConnection();
			pstmt = con.prepareStatement(getAll + " WHERE memid LIKE ? "
					+ orderBy);
			pstmt.setString(1, "%" + idKeyword + "%");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				TinyMemberVO vo = retrieveCurrentRow(rs);
				set.add(vo);
			}
		} catch (Exception e) {
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
	public int increaseWithhold(Integer memno, Integer amount) {
		return updateWithhold(memno, amount, INCREASE);
	}

	@Override
	public int decreaseWithhold(Integer memno, Integer amount) {
		return updateWithhold(memno, amount, DECREASE);
	}

	private int updateWithhold(Integer memno, Integer amount, int change) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			int moneyChange = 0;
			int withholdChang = 0;
			con = ConnectionProvider.getConnection();
			pstmt = con.prepareStatement(updateWithhold);
			if (change == INCREASE) {
				moneyChange = amount * -1;
				withholdChang = amount;
			}
			if (change == DECREASE) {
				moneyChange = amount;
				withholdChang = amount * -1;
			}
			pstmt.setInt(1, moneyChange);
			pstmt.setInt(2, withholdChang);
			pstmt.setInt(3, memno);
			synchronized (ACCESS_LOCK) {
				updateCount = pstmt.executeUpdate();
			}
		} catch (Exception e) {
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
	public void batchDecreaseWithholdByCancelCase(List<OrderVO> list,
			Connection con) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(updateWithhold);
			for (OrderVO ordvo : list) {
				pstmt.setInt(1, ordvo.getPrice());// money
				pstmt.setInt(2, -1 * ordvo.getPrice());// withhold
				pstmt.setInt(3, ordvo.getBmemno());
				pstmt.executeUpdate();
			}
		} catch (Exception e) {
			throw new SQLException("Update member withhold failed.\n"
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
	public int changeMoney(Integer memno, Integer amount) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionProvider.getConnection();
			pstmt = con.prepareStatement(changeMoney);
			pstmt.setInt(1, amount);
			pstmt.setInt(2, memno);
			synchronized (ACCESS_LOCK) {
				updateCount = pstmt.executeUpdate();
			}
		} catch (Exception e) {
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
