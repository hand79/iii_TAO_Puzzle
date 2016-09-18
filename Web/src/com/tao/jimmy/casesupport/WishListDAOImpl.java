package com.tao.jimmy.casesupport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.tao.jimmy.util.model.ConnectionProvider;

public class WishListDAOImpl implements WishListDAO {

	@Override
	public Integer insert(WishListVO vo) {
		Integer genKey = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionProvider.getConnection();
			String[] cols = { "wlno" };
			pstmt = con
					.prepareStatement(
							"INSERT INTO wishlist (wlno, memno, caseno) VALUES (wishlist_seq.NEXTVAL, ?, ?)",
							cols);
			pstmt.setInt(1, vo.getMemno());
			pstmt.setInt(2, vo.getCaseno());
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				genKey = rs.getInt(1);
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
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return genKey;
	}

	@Override
	public boolean hasWish(Integer memno, Integer caseno) {
		boolean result = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionProvider.getConnection();
			pstmt = con
					.prepareStatement("SELECT COUNT(*) FROM wishlist WHERE memno=? AND caseno=?");
			pstmt.setInt(1, memno);
			pstmt.setInt(2, caseno);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int count = rs.getInt(1);
				if (count > 0) {
					result = true;
				}
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
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
