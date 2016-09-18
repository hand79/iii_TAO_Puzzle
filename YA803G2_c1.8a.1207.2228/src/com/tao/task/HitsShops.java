package com.tao.task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.sql.DataSource;

import com.tao.util.model.DataSourceHolder;

public class HitsShops {
	private static DataSource ds = DataSourceHolder.getDataSource();
	
	private final static String UPDATE_HITS_STM="UPDATE Shop SET hits = hits + ? WHERE shopno=?";
	public int updateHits(Map<Integer, Integer> contextHitsRecord) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_HITS_STM);
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
}
