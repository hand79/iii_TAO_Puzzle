package com.tao.jimmy.location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import com.tao.jimmy.util.model.ConnectionProvider;

public class LocationDAOImpl implements LocationDAO {
	private String getCountys = "SELECT UNIQUE county FROM location";
	private String sql = "SELECT min(locno) as s, max(locno) as e FROM LOCATION where county =?";
	private String getAll = "SELECT locno, county, town FROM location";
	private String orderBy = "ORDER BY locno";

	@Override
	public Set<CountyVO> findCounties() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Set<CountyVO> set = new TreeSet<>();
		Connection con2 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;
		try {
			con = ConnectionProvider.getConnection();
			pstmt = con.prepareStatement(getCountys);
			rs = pstmt.executeQuery();
			con2 = ConnectionProvider.getConnection();
			pstmt2 = con.prepareStatement(sql);
			rs2 = null;
			while (rs.next()) {
				String countyName = rs.getString(1);
				pstmt2.setString(1, countyName);
				rs2 = pstmt2.executeQuery();
				rs2.next();
				CountyVO vo = new CountyVO();
				vo.setName(countyName);
				vo.setFrom(rs2.getInt("s"));
				vo.setTo(rs2.getInt("e"));
				set.add(vo);
			}
			rs2.close();
			pstmt2.close();
			con2.close();
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
				if (rs2 != null) {
					rs.close();
				}
				if (pstmt2 != null) {
					pstmt.close();
				}
				if (con2 != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
		return set;
	}

	@Override
	public Set<LocationVO> getAll() {
		Set<LocationVO> set = new LinkedHashSet<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionProvider.getConnection();
			pstmt = con.prepareStatement(getAll + " " + orderBy);
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				LocationVO lvo = retrieveCurrentRow(rs);
				set.add(lvo);
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
	public Set<LocationVO> findByCounty(CountyVO vo) {
		Set<LocationVO> set = new LinkedHashSet<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionProvider.getConnection();
			pstmt = con.prepareStatement(getAll
					+ " WHERE locno BETWEEN ? AND ? " + orderBy);
			pstmt.setInt(1, vo.getFrom());
			pstmt.setInt(2, vo.getTo());

			rs = pstmt.executeQuery();
			while (rs.next()) {
				LocationVO lvo = retrieveCurrentRow(rs);
				set.add(lvo);
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

	private LocationVO retrieveCurrentRow(ResultSet rs) throws SQLException {
		LocationVO vo = new LocationVO();
		vo.setCounty(rs.getString("county"));
		vo.setTown(rs.getString("town"));
		vo.setLocno(rs.getInt("locno"));
		return vo;

	}

	@Override
	public LocationVO findByPrimaryKey(Integer locno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LocationVO vo = new LocationVO();
		try {
			con = ConnectionProvider.getConnection();
			pstmt = con.prepareStatement(getAll
					+ " WHERE locno=? " + orderBy);
			pstmt.setInt(1, locno);
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

}
