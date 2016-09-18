package com.tao.cases.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.tao.jimmy.util.model.ConnectionProvider;

public class CaseQADAOImpl implements CaseQADAO {
	private static final String INSERT_STMT = "INSERT INTO caseQA (qano, memno, caseno, question, qtime) VALUES (caseQA_seq.NEXTVAL, ?,?,?,?)";
//	private static final String UPDATE_STMT = "UPDATE caseQA SET memno=?, caseno=?, question=?, answer=?, qtime=?, atime=? WHERE qano=?";
	private static final String DELETE_STMT = "DELETE FROM caseQA WHERE qano=?";

	private static final String UPDATE_ANS_STMT = "UPDATE caseQA SET answer=?, atime=? WHERE qano=?";
	private static final String SELECT_ALL = "SELECT qano, memno, caseno, question, answer, qtime, atime "
			+ "FROM caseQA";

	@Override
	public Integer insert(CaseQAVO qavo) {
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer genKey = 0;

		try {
			String[] cols = { "QANO" };
			pstmt = con.prepareStatement(INSERT_STMT, cols);
			pstmt.setInt(1, qavo.getMemno());
			pstmt.setInt(2, qavo.getCaseno());
			pstmt.setString(3, qavo.getQuestion());
			pstmt.setTimestamp(4, qavo.getQtime());
			pstmt.executeUpdate();

			rs = pstmt.getGeneratedKeys();
			rs.next();
			genKey = rs.getInt(1);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return genKey;
	}

	@Override
	public int delete(Integer qano) {
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		int updateCount = 0;
		try {
			pstmt = con.prepareStatement(DELETE_STMT);
			pstmt.setInt(1, qano);
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
	public int updateAnswer(Integer qano, String ans, Timestamp atime) {
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		int updateCount = 0;
		try {
			pstmt = con.prepareStatement(UPDATE_ANS_STMT);
			pstmt.setString(1, ans);
			pstmt.setTimestamp(2, atime);
			pstmt.setInt(3, qano);
			updateCount = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return updateCount;
	}

	@Override
	public List<CaseQAVO> findByCaseNo(Integer caseno) {
		return findByCaseNo(caseno, CaseQADAO.ALL);
	}

	@Override
	public List<CaseQAVO> findByCaseNo(Integer caseno, int filter) {
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CaseQAVO> list = new Vector<>();
		String sql = SELECT_ALL + " WHERE caseno=?";

		try {
			switch (filter) {
			case CaseQADAO.ANSWERED:
				sql += " AND atime IS NOT NULL  order by qano";
				break;
			case CaseQADAO.UNANSWERED:
				sql += " AND atime IS NULL  order by qano";
				break;
			case CaseQADAO.ALL:
			default:
			}
//			System.out.println(sql);
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, caseno);
			rs = pstmt.executeQuery();
			list = new ArrayList<>();
			while (rs.next()) {
				CaseQAVO vo = retrieveCurrentVO(rs);
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	private CaseQAVO retrieveCurrentVO(ResultSet rs) throws SQLException {
		CaseQAVO vo = new CaseQAVO();
		vo.setQano(rs.getInt("QANO"));
		vo.setMemno(rs.getInt("MEMNO"));
		vo.setCaseno(rs.getInt("CASENO"));
		vo.setQuestion(rs.getString("QUESTION"));
		vo.setAnswer(rs.getString("ANSWER"));
		vo.setQtime(rs.getTimestamp("QTIME"));
		vo.setAtime(rs.getTimestamp("ATIME"));
		return vo;
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

	@Override
	public CaseQAVO findByPrimaryKey(Integer qano) {
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CaseQAVO vo = null;
		String sql = SELECT_ALL + " WHERE qano=? order by qano";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, qano);
			rs = pstmt.executeQuery();
			if(rs.next()){
				vo = retrieveCurrentVO(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return vo;
	}

	@Override
	public List<CaseQAVO> getAll() {
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CaseQAVO> list =new ArrayList<>();

		try {
			pstmt = con.prepareStatement(SELECT_ALL + " order by qano");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				CaseQAVO vo = retrieveCurrentVO(rs);
				list.add(vo);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

}
