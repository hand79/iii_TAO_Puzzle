package com.tao.member.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.tao.util.model.DataSourceHolder;


public class MemberDAO implements MemberDAO_interface {

	private static DataSource ds = DataSourceHolder.getDataSource();

	private static final String INSERT_STMT = 
		"INSERT INTO member (memno, memid, mempw, fname, lname, idnum, gender, locno, "
		+ "addr, tel, email, photo, mime, point, money, addays, status, type, withhold) "
		+ "VALUES (member_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, 0, 7, 0, ?, 0)";
	private static final String GET_ALL_STMT = 
		"SELECT memno, memid, mempw, fname, lname, idnum, gender, locno, addr, tel, "
		+ "email, point, money, addays, status, type, withhold FROM member order by memno";
	private static final String GET_ONE_STMT = 
		"SELECT memno, memid, mempw, fname, lname, idnum, gender, locno, addr, tel, "
		+ "email, photo, mime, point, money, addays, status, type, withhold FROM member where memno = ?";
	private static final String GET_ONE_STMT_NO_PIC = "SELECT memno, memid, mempw, fname, lname, idnum, gender, locno, addr, tel, "
			+ "email, point, money, addays, status, type, withhold FROM member where memno = ?";
	private static final String GET_ONE_BY_ID_STMT = 
			"SELECT memno, memid, mempw, fname, lname, idnum, gender, locno, addr, tel, "
			+ "email, photo, mime, point, money, addays, status, type, withhold FROM member where memid = ?";
	private static final String DELETE = 
		"DELETE FROM member where memno = ?";
	private static final String UPDATE = 
		"UPDATE member set memid=?, mempw=?, fname=?, lname=?, idnum=?, gender=?, "
		+ "locno=?, addr=?, tel=?, email=?, mime=?, point=?, money=?, "
		+ "addays=?, status=?, type=?, withhold=? where memno = ?";
	
	private static final String UPDATE_BACK_STMT = 
			"UPDATE member set status=? where memno = ?";

	private static final String UPDATE_FRONT_STMT = 
			"UPDATE member set mempw=?, fname=?, lname=?, locno=?, addr=?, tel=?, email=? where memno = ?";
	
	private static final String UPDATE_FRONT_WITH_PHOTO_STMT = 
			"UPDATE member set mempw=?, fname=?, lname=?, locno=?, addr=?, tel=?, email=?, photo=?, mime=? where memno = ?";
	
	private static final String UPDATE_PIC_STMT = 
			"UPDATE member set photo=?, mime=? where memno = ?";
	private static final String ID_EXIST_CHECKER_STMT = 
			"SELECT memid FROM member where memid = ?";
	private static final String GET_ALL_ID_STMT = 
			"SELECT memid FROM member order by memno";
	private static final String GET_ALL_PENDING_SHOP_MEMBER_STMT = 
			"SELECT memno FROM member where status=0 and type=1 order by memno";
	
	@Override
	public int insert(MemberVO memberVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			String cols[] = {"memno"};
			pstmt = con.prepareStatement(INSERT_STMT,cols);
			
			pstmt.setString(1, memberVO.getMemid());
			pstmt.setString(2, memberVO.getMempw());
			pstmt.setString(3, memberVO.getFname());
			pstmt.setString(4, memberVO.getLname());
			pstmt.setString(5, memberVO.getIdnum());
			pstmt.setString(6, memberVO.getGender());
			pstmt.setInt(7, memberVO.getLocno());
			pstmt.setString(8, memberVO.getAddr());
			pstmt.setString(9, memberVO.getTel());
			pstmt.setString(10, memberVO.getEmail());
			pstmt.setBytes(11, memberVO.getPhoto());
			pstmt.setString(12, memberVO.getMime());
			pstmt.setInt(13, memberVO.getType());

			updateCount = pstmt.executeUpdate();
			
			//掘取對應的自增主鍵值
			ResultSet rs = pstmt.getGeneratedKeys();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			if (rs.next()) {
				do {
					for (int i = 1; i <= columnCount; i++) {
						String key = rs.getString(i);
						System.out.println("自增主鍵值(" + i + ") = " + key +"(剛新增成功的員工編號)");
					}
				} while (rs.next());
			} else {
				System.out.println("NO KEYS WERE GENERATED.");
			}
			rs.close();

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
	public int update(MemberVO memberVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, memberVO.getMemid());
			pstmt.setString(2, memberVO.getMempw());
			pstmt.setString(3, memberVO.getFname());
			pstmt.setString(4, memberVO.getLname());
			pstmt.setString(5, memberVO.getIdnum());
			pstmt.setString(6, memberVO.getGender());
			pstmt.setInt(7, memberVO.getLocno());
			pstmt.setString(8, memberVO.getAddr());
			pstmt.setString(9, memberVO.getTel());
			pstmt.setString(10, memberVO.getEmail());
			pstmt.setString(11, memberVO.getMime());
			pstmt.setInt(12, memberVO.getPoint());
			pstmt.setInt(13, memberVO.getMoney());
			pstmt.setInt(14, memberVO.getAddays());
			pstmt.setInt(15, memberVO.getStatus());
			pstmt.setInt(16, memberVO.getType());
			pstmt.setInt(17, memberVO.getWithhold());
			pstmt.setInt(18, memberVO.getMemno());	

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
	public int delete(Integer memno) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setInt(1, memno);
			
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
	public MemberVO findByPrimaryKey(Integer memno) {
		MemberVO memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setInt(1, memno);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// memberVO 也稱為 Domain objects
				memberVO = new MemberVO();
				memberVO.setMemno(rs.getInt("memno"));
				memberVO.setMemid(rs.getString("memid"));
				memberVO.setMempw(rs.getString("mempw"));
				memberVO.setFname(rs.getString("fname"));
				memberVO.setLname(rs.getString("lname"));
				memberVO.setIdnum(rs.getString("idnum"));
				memberVO.setGender(rs.getString("gender"));
				memberVO.setLocno(rs.getInt("locno"));
				memberVO.setAddr(rs.getString("addr"));
				memberVO.setTel(rs.getString("tel"));
				memberVO.setEmail(rs.getString("email"));
				memberVO.setPhoto(rs.getBytes("photo"));
				memberVO.setMime(rs.getString("mime"));
				memberVO.setPoint(rs.getInt("point"));
				memberVO.setMoney(rs.getInt("money"));
				memberVO.setAddays(rs.getInt("addays"));
				memberVO.setStatus(rs.getInt("status"));
				memberVO.setType(rs.getInt("type"));
				memberVO.setWithhold(rs.getInt("withhold"));
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
		return memberVO;
	}

	@Override
	public List<MemberVO> getAll() {
		List<MemberVO> list = new ArrayList<MemberVO>();
		MemberVO memberVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// memberVO 也稱為 Domain objects
				memberVO = new MemberVO();
				memberVO.setMemno(rs.getInt("memno"));
				memberVO.setMemid(rs.getString("memid"));
				memberVO.setMempw(rs.getString("mempw"));
				memberVO.setFname(rs.getString("fname"));
				memberVO.setLname(rs.getString("lname"));
				memberVO.setIdnum(rs.getString("idnum"));
				memberVO.setGender(rs.getString("gender"));
				memberVO.setLocno(rs.getInt("locno"));
				memberVO.setAddr(rs.getString("addr"));
				memberVO.setTel(rs.getString("tel"));
				memberVO.setEmail(rs.getString("email"));
				//memberVO.setPhoto(rs.getBytes("photo"));
				//memberVO.setMime(rs.getString("mime"));
				memberVO.setPoint(rs.getInt("point"));
				memberVO.setMoney(rs.getInt("money"));
				memberVO.setAddays(rs.getInt("addays"));
				memberVO.setStatus(rs.getInt("status"));
				memberVO.setType(rs.getInt("type"));
				memberVO.setWithhold(rs.getInt("withhold"));
				list.add(memberVO); // Store the row in the vector
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
	public int updatePic(Integer memno, byte[] photo, String mime) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_PIC_STMT);
			
			pstmt.setBytes(1, photo);
			pstmt.setInt(2, memno);	

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
	public MemberVO findByMemberID(String memid) {
		MemberVO memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_ID_STMT);
			
			pstmt.setString(1, memid);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// memberVO 也稱為 Domain objects
				memberVO = new MemberVO();
				memberVO.setMemno(rs.getInt("memno"));
				memberVO.setMemid(rs.getString("memid"));
				memberVO.setMempw(rs.getString("mempw"));
				memberVO.setFname(rs.getString("fname"));
				memberVO.setLname(rs.getString("lname"));
				memberVO.setIdnum(rs.getString("idnum"));
				memberVO.setGender(rs.getString("gender"));
				memberVO.setLocno(rs.getInt("locno"));
				memberVO.setAddr(rs.getString("addr"));
				memberVO.setTel(rs.getString("tel"));
				memberVO.setEmail(rs.getString("email"));
				memberVO.setPhoto(rs.getBytes("photo"));
				memberVO.setMime(rs.getString("mime"));
				memberVO.setPoint(rs.getInt("point"));
				memberVO.setMoney(rs.getInt("money"));
				memberVO.setAddays(rs.getInt("addays"));
				memberVO.setStatus(rs.getInt("status"));
				memberVO.setType(rs.getInt("type"));
				memberVO.setWithhold(rs.getInt("withhold"));
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
		return memberVO;
	}

	@Override
	public boolean idExistChecker(String memid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String receivedId = null;
		System.out.println("memid first out = " + memid);
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(ID_EXIST_CHECKER_STMT);
			
			pstmt.setString(1, memid);
			
			rs = pstmt.executeQuery();
			

			while (rs.next()) {
				receivedId = (rs.getString("memid"));
			}
			System.out.println("receivedId first out = " + receivedId);
			
			if (receivedId==null){
				return false; // return false means this ID does not exist in the database, so, false = not exists = available to use.  
			}else {
				return true; 
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
	}

	@Override
	public List<String> getAllID() {
		List<String> list = new ArrayList<String>();
		String ids = "";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_ID_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// memberVO 也稱為 Domain objects
				ids = (rs.getString("memid"));
				list.add(ids); // Store the row in the vector
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
	public List<MemberVO> getAll(Map<String, String[]> map) {
		List<MemberVO> list = new ArrayList<MemberVO>();
		MemberVO memberVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			String finalSQL = "select * from member "
					+ CompositeQuery_Mem.get_WhereCondition(map)
					+ "order by memno";
			pstmt = con.prepareStatement(finalSQL);
			System.out.println("●●finalSQL(by DAO) = "+finalSQL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// memberVO 也稱為 Domain objects
				memberVO = new MemberVO();
				memberVO.setMemno(rs.getInt("memno"));
				memberVO.setMemid(rs.getString("memid"));
				memberVO.setMempw(rs.getString("mempw"));
				memberVO.setFname(rs.getString("fname"));
				memberVO.setLname(rs.getString("lname"));
				memberVO.setIdnum(rs.getString("idnum"));
				memberVO.setGender(rs.getString("gender"));
				memberVO.setLocno(rs.getInt("locno"));
				memberVO.setAddr(rs.getString("addr"));
				memberVO.setTel(rs.getString("tel"));
				memberVO.setEmail(rs.getString("email"));
				//memberVO.setPhoto(rs.getBytes("photo"));
				memberVO.setMime(rs.getString("mime"));
				memberVO.setPoint(rs.getInt("point"));
				memberVO.setMoney(rs.getInt("money"));
				memberVO.setAddays(rs.getInt("addays"));
				memberVO.setStatus(rs.getInt("status"));
				memberVO.setType(rs.getInt("type"));
				memberVO.setWithhold(rs.getInt("withhold"));
				list.add(memberVO); // Store the row in the vector
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
	public int updateBack(MemberVO memberVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_BACK_STMT);
			
		
			pstmt.setInt(1, memberVO.getStatus());
						
			pstmt.setInt(2, memberVO.getMemno());	

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
	public int updateFront(MemberVO memberVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_FRONT_STMT);
			
			pstmt.setString(1, memberVO.getMempw());
			pstmt.setString(2, memberVO.getFname());
			pstmt.setString(3, memberVO.getLname());
			pstmt.setInt(4, memberVO.getLocno());
			pstmt.setString(5, memberVO.getAddr());
			pstmt.setString(6, memberVO.getTel());
			pstmt.setString(7, memberVO.getEmail());
			pstmt.setInt(8, memberVO.getMemno());	

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

	private MemberVO findByPrimaryKey(Integer memno, boolean hasPic) {
		MemberVO memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
	
			con = ds.getConnection();
			pstmt = con.prepareStatement(hasPic ? GET_ONE_STMT
					: GET_ONE_STMT_NO_PIC);
	
			pstmt.setInt(1, memno);
	
			rs = pstmt.executeQuery();
	
			if (rs.next()) {
				// memberVO 也稱為 Domain objects
				memberVO = new MemberVO();
				memberVO.setMemno(rs.getInt("memno"));
				memberVO.setMemid(rs.getString("memid"));
				memberVO.setMempw(rs.getString("mempw"));
				memberVO.setFname(rs.getString("fname"));
				memberVO.setLname(rs.getString("lname"));
				memberVO.setIdnum(rs.getString("idnum"));
				memberVO.setGender(rs.getString("gender"));
				memberVO.setLocno(rs.getInt("locno"));
				memberVO.setAddr(rs.getString("addr"));
				memberVO.setTel(rs.getString("tel"));
				memberVO.setEmail(rs.getString("email"));
				if (hasPic) {
					memberVO.setPhoto(rs.getBytes("photo"));
					memberVO.setMime(rs.getString("mime"));
				}
				memberVO.setPoint(rs.getInt("point"));
				memberVO.setMoney(rs.getInt("money"));
				memberVO.setAddays(rs.getInt("addays"));
				memberVO.setStatus(rs.getInt("status"));
				memberVO.setType(rs.getInt("type"));
				memberVO.setWithhold(rs.getInt("withhold"));
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
		return memberVO;
	}

	@Override
	public MemberVO findByPrimaryKeyNoPic(Integer memno) {
		return findByPrimaryKey(memno, false);
	}

	@Override
	public MemberVO findByPrimaryKeyWithPic(Integer memno) {
		return findByPrimaryKey(memno, true);
	}
	
	@Override
	public List<MemberVO> getAllPendingShopMember() {
		List<MemberVO> list = new ArrayList<MemberVO>();
		MemberVO memberVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_PENDING_SHOP_MEMBER_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// memberVO 也稱為 Domain objects
				memberVO = new MemberVO();
				memberVO.setMemno(rs.getInt("memno"));
				list.add(memberVO); // Store the row in the vector
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
	public int updateFrontWithPhoto(MemberVO memberVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_FRONT_WITH_PHOTO_STMT);
			
			pstmt.setString(1, memberVO.getMempw());
			pstmt.setString(2, memberVO.getFname());
			pstmt.setString(3, memberVO.getLname());
			pstmt.setInt(4, memberVO.getLocno());
			pstmt.setString(5, memberVO.getAddr());
			pstmt.setString(6, memberVO.getTel());
			pstmt.setString(7, memberVO.getEmail());
			pstmt.setBytes(8, memberVO.getPhoto());
			pstmt.setString(9, memberVO.getMime());
			pstmt.setInt(10, memberVO.getMemno());	

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
	
	
}
