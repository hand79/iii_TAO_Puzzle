package com.tao.androidmember.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.tao.util.model.DataSourceHolder;
 
 
public class MemberDAO implements MemberInterface {
 
    private static DataSource ds = DataSourceHolder.getDataSource();
 
    private static final String INSERT_STMT = 
        "INSERT INTO member (memno, memid, mempw, fname, lname, idnum, gender, locno, "
        + "addr, tel, email, photo, mime, point, money, addays, status, type, withhold) "
        + "VALUES (member_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_ALL_STMT = 
        "SELECT memno, memid, mempw, fname, lname, idnum, gender, locno, addr, tel, "
        + "email, photo, mime, point, money, addays, status, type, withhold FROM member order by memno";
    private static final String GET_ONE_STMT = 
        "SELECT memno, memid, mempw, fname, lname, idnum, gender, locno, addr, tel, "
        + "email, photo, mime, point, money, addays, status, type, withhold FROM member where memno = ?";
    private static final String GET_ONE_BY_ID_STMT = 
            "SELECT memno, memid, mempw, fname, lname, idnum, gender, locno, addr, tel, "
            + "email, photo, mime, point, money, addays, status, type, withhold FROM member where memid = ?";
    private static final String DELETE = 
        "DELETE FROM member where memno = ?";
    private static final String UPDATE = 
        "UPDATE member set memid=?, mempw=?, fname=?, lname=?, idnum=?, gender=?, "
        + "locno=?, addr=?, tel=?, email=?, mime=?, point=?, money=?, "
        + "addays=?, status=?, type=?, withhold=? where memno = ?";
    private static final String UPDATE_PIC_STMT = 
            "UPDATE member set mime=?, photo=? where memno = ?";
     
   
    
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
            pstmt.setInt(13, memberVO.getPoint());
            pstmt.setInt(14, memberVO.getMoney());
            pstmt.setInt(15, memberVO.getAddays());
            pstmt.setInt(16, memberVO.getStatus());
            pstmt.setInt(17, memberVO.getType());
            pstmt.setInt(18, memberVO.getWithhold());
 
            updateCount = pstmt.executeUpdate();
             
            //??òÂ?ñÂ?çÊ?âÁ?ÑËá™Â¢û‰∏ª?çµ??
            ResultSet rs = pstmt.getGeneratedKeys();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            if (rs.next()) {
                do {
                    for (int i = 1; i <= columnCount; i++) {
                        String key = rs.getString(i);
                        System.out.println("?á™Â¢û‰∏ª?çµ??(" + i + ") = " + key +"(??õÊñ∞Â¢ûÊ?êÂ?üÁ?ÑÂì°Â∑•Á∑®???)");
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
                // memberVO ‰πüÁ®±?Ç∫ Domain objects
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
                // memberVO ‰πüÁ®±?Ç∫ Domain objects
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
    public int updatePic(Integer memno, byte[] photo , String mime) {
        int updateCount = 0;
        Connection con = null;
        PreparedStatement pstmt = null;
 
        try {
 
            con = ds.getConnection();
            pstmt = con.prepareStatement(UPDATE_PIC_STMT);
            pstmt.setString(1, mime);
         
            pstmt.setBytes(2, photo);
            
            pstmt.setInt(3, memno); 
            
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
                // memberVO ‰πüÁ®±?Ç∫ Domain objects
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

    
     
}
