package com.tao.cases.model;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.tao.jimmy.util.model.ConnectionProvider;

public class CaseProductDAOImpl implements CaseProductDAO {

	private static final String INSERT_STMT = "INSERT INTO caseProduct VALUES (caseProduct_seq.NEXTVAL, ?,?,?,?,?,?,?,?,?,?,?,0)";
	// private static final String UPDATE_STMT = "UPDATE caseProduct SET "
	// +
	// "memno=?,name=?,unitprice=?,pic1=?,pic2=?,pic3=?,pmime1=?,pmime2=?,pmime3=?,cpdesc=?,subcatno=? "
	// + "WHERE cpno=?";
	private static final String UPDATE_LOCKFLAG = "UPDATE caseProduct SET lockflag=1 WHERE cpno=?";
	private static final String UPDATE_STMT_FIRST_PART = "UPDATE caseProduct SET "
			+ "name=?,unitprice=?, cpdesc=?, subcatno=? ";
	private static final String DELETE_STMT = "DELETE FROM caseProduct WHERE cpno=?";
	private static final String SELECT_ALL = "SELECT cpno, memno, name, unitprice, "
			+ "cpdesc, subcatno,"
			+ "pmime1,pmime2,pmime3, lockflag "
			+ "FROM caseProduct";
	private static final String SELECT_ALL_HAS_PIC = "SELECT cpno, memno, name, unitprice, "
			+ "pic1, pic2, pic3, pmime1, pmime2, pmime3,"
			+ "cpdesc, subcatno, lockflag " + "FROM caseProduct";

	private static final String ORDER_BY = "ORDER BY cpno";

	// private static final String SELECT_BY_PK_HAS_PIC =
	// "SELECT cpno, memno, name, unitprice, pic1,pic2,pic3,pmime1,pmime2,pmime3,cpdesc,subcatno "
	// + "FROM caseProduct WHERE cpno=?";
	// private static final String SELECT_BY_PK =
	// "SELECT cpno, memno, name, unitprice, cpdesc, subcatno "
	// + "FROM caseProduct WHERE cpno=?";
	// private static final String SELECT_BY_OWNER_HAS_PIC =
	// "SELECT cpno, memno, name, unitprice, pic1,pic2,pic3,pmime1,pmime2,pmime3,cpdesc,subcatno "
	// + "FROM caseProduct WHERE memno=?";
	// private static final String SELECT_BY_OWNER =
	// "SELECT cpno, memno, name, unitprice, cpdesc, subcatno "
	// + "FROM caseProduct WHERE memno=?";

	public CaseProductDAOImpl() {
		super();
	}

	@Override
	public Integer insert(CaseProductVO cpVO) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer re = 0;
		try {
			conn = getConnection();

			String cols[] = { "CPNO" };
			pstmt = conn.prepareStatement(INSERT_STMT, cols);

			pstmt.setInt(1, cpVO.getMemno());
			pstmt.setString(2, cpVO.getName());
			pstmt.setInt(3, cpVO.getUnitprice());

			pstmt.setBytes(4, cpVO.getPic1());
			pstmt.setBytes(5, cpVO.getPic2());
			pstmt.setBytes(6, cpVO.getPic3());
			pstmt.setString(7, cpVO.getPmime1());
			pstmt.setString(8, cpVO.getPmime2());
			pstmt.setString(9, cpVO.getPmime3());
			pstmt.setString(10, cpVO.getCpdesc());
			pstmt.setInt(11, cpVO.getSubcatno());
			pstmt.executeUpdate();

			rs = pstmt.getGeneratedKeys();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			if (rs.next()) {
				do {
					for (int i = 1; i <= columnCount; i++) {
						re = rs.getInt(i);
					}
				} while (rs.next());
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
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
		return re;
	}

	// @Override
	// public int update(CaseProductVO cpVO) {
	// Connection con = null;
	// PreparedStatement pstmt = null;
	// int updateCount = 0;
	// try {
	// con = getConnection();
	// pstmt = con.prepareStatement(UPDATE_STMT);
	//
	// pstmt.setInt(1, cpVO.getMemno());
	// pstmt.setString(2, cpVO.getName());
	// pstmt.setInt(3, cpVO.getUnitprice());
	//
	// pstmt.setBytes(4, cpVO.getPic1());
	// pstmt.setBytes(5, cpVO.getPic2());
	// pstmt.setBytes(6, cpVO.getPic3());
	//
	// pstmt.setString(7, cpVO.getPmime1());
	// pstmt.setString(8, cpVO.getPmime2());
	// pstmt.setString(9, cpVO.getPmime3());
	//
	// pstmt.setString(10, cpVO.getCpdesc());
	// pstmt.setInt(11, cpVO.getSubcatno());
	// pstmt.setInt(12, cpVO.getCpno());
	// updateCount = pstmt.executeUpdate();
	//
	// } catch (SQLException e) {
	// e.printStackTrace();
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
	//
	// return updateCount;
	// }

	@Override
	public int updateByEdit(CaseProductVO cpVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int updateCount = 0;
		StringBuilder sql = new StringBuilder(UPDATE_STMT_FIRST_PART);
		boolean hasPic1 = false, hasPic2 = false, hasPic3 = false;
		if (cpVO.getPic1() != null && cpVO.getPmime1() != null) {
			hasPic1 = true;
			sql.append(", pic1=?, pmime1=?");
		}
		if (cpVO.getPic2() != null && cpVO.getPmime2() != null) {
			hasPic2 = true;
			sql.append(", pic2=?, pmime2=?");
		}
		if (cpVO.getPic3() != null && cpVO.getPmime2() != null) {
			hasPic3 = true;
			sql.append(", pic3=?, pmime3=?");
		}

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql.append(" WHERE cpno=?")
					.toString());
			int index = 5;
			pstmt.setString(1, cpVO.getName());
			pstmt.setInt(2, cpVO.getUnitprice());
			pstmt.setString(3, cpVO.getCpdesc());
			pstmt.setInt(4, cpVO.getSubcatno());
			if (hasPic1) {
				pstmt.setBytes(index++, cpVO.getPic1());
				pstmt.setString(index++, cpVO.getPmime1());
			}
			if (hasPic2) {
				pstmt.setBytes(index++, cpVO.getPic2());
				pstmt.setString(index++, cpVO.getPmime2());
			}
			if (hasPic3) {
				pstmt.setBytes(index++, cpVO.getPic3());
				pstmt.setString(index++, cpVO.getPmime3());
			}
			pstmt.setInt(index, cpVO.getCpno());
			updateCount = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
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
	public int updateLockflagToLockIt(Integer cpno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int updateCount = 0;

		try {
			con = getConnection();
			pstmt = con.prepareStatement(UPDATE_LOCKFLAG);
			pstmt.setInt(1, cpno);
			updateCount = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
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
	public int delete(Integer cpno) {
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		int updateCount = 0;
		try {
			pstmt = con.prepareStatement(DELETE_STMT);
			pstmt.setInt(1, cpno);
			updateCount = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return updateCount;
	}

	@Override
	@Deprecated
	public CaseProductVO findByPrimaryKey(Integer cpno) {
		return findByPrimaryKey(cpno, true);
	}

	@Override
	public CaseProductVO findByPrimaryKey(Integer cpno, boolean hasPic) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CaseProductVO vo = null;
		try {
			con = getConnection();
			String sql;
			if (hasPic) {
				sql = SELECT_ALL_HAS_PIC + " WHERE cpno=? " + ORDER_BY;
			} else {
				sql = SELECT_ALL + " WHERE cpno=? " + ORDER_BY;
			}
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cpno);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				vo = retrieveCurrentRow(rs, hasPic);
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
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
	@Deprecated
	public List<CaseProductVO> findByOwnerNo(Integer memno) {
		return findByOwnerNo(memno, true);
	}

	@Override
	public List<CaseProductVO> findByOwnerNo(Integer memno, boolean hasPic) {
		List<CaseProductVO> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConnection();
			String sql;
			if (hasPic) {
				sql = SELECT_ALL_HAS_PIC + " WHERE memno=? " + ORDER_BY;
			} else {
				sql = SELECT_ALL + " WHERE memno=? " + ORDER_BY;
			}
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, memno);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CaseProductVO vo = retrieveCurrentRow(rs, hasPic);
				list.add(vo);
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
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

	private CaseProductVO retrieveCurrentRow(ResultSet rs, boolean hasPic)
			throws SQLException, IOException {
		CaseProductVO vo = new CaseProductVO();
		vo.setCpno(rs.getInt("CPNO"));
		vo.setMemno(rs.getInt("MEMNO"));
		vo.setName(rs.getString("NAME"));
		vo.setUnitprice(rs.getInt("UNITPRICE"));
		vo.setSubcatno(rs.getInt("SUBCATNO"));
		vo.setCpdesc(rs.getString("CPDESC"));
		vo.setPmime1(rs.getString("PMIME1"));
		vo.setPmime2(rs.getString("PMIME2"));
		vo.setPmime3(rs.getString("PMIME3"));

		if (hasPic) {
			vo.setPic1(rs.getBytes("PIC1"));
			vo.setPic2(rs.getBytes("PIC2"));
			vo.setPic3(rs.getBytes("PIC3"));
		}
		vo.setLockflag(rs.getInt("lockflag"));
		return vo;
	}

	// private byte[] getByteArray(InputStream in) throws IOException {
	//
	// byte[] re = null;
	// if (in != null) {
	// byte[] pic = new byte[1024];
	// ByteArrayOutputStream out = new ByteArrayOutputStream();
	// int length = 0 ;
	// while ((length = in.read(pic)) != -1){
	// out.write(pic, 0, length);
	// }
	// re = out.toByteArray();
	// }
	// return re;
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

	@Override
	@Deprecated
	public List<CaseProductVO> getAll() {
		return getAll(true);
	}

	@Override
	public List<CaseProductVO> getAll(boolean hasPic) {
		List<CaseProductVO> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConnection();
			String sql;
			if (hasPic) {
				sql = SELECT_ALL_HAS_PIC + " " + ORDER_BY;
			} else {
				sql = SELECT_ALL + " " + ORDER_BY;
			}
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CaseProductVO vo = retrieveCurrentRow(rs, hasPic);
				list.add(vo);
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
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

}
