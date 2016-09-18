package com.tao.jimmy.shop;

import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Set;

import com.tao.jimmy.util.model.ConnectionProvider;

public class TinyShopProductDAOImpl implements TinyShopProductDAO {

	@Override
	public Set<TinyShopProductVO> findByShopNo(Integer shopno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Set<TinyShopProductVO> set = new LinkedHashSet<>();
		
		try {
			con = ConnectionProvider.getConnection();
			String sql = "SELECT spno FROM shopproduct WHERE shopno=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, shopno);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				TinyShopProductVO vo = new TinyShopProductVO();
				vo.setSpno(rs.getInt(1));
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
	
	

}
