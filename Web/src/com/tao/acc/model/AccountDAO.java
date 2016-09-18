package com.tao.acc.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.tao.util.model.DataSourceHolder;

public class AccountDAO implements AccountDAO_interface {
	private static DataSource ds = DataSourceHolder.getDataSource();
	private static final String INSERT_STMT = "INSERT INTO account (acc,nick,accpw,email) VALUES (?, ?, ?,?)";
	private static final String GET_ALL_ACC_DATA = "SELECT acc,nick,accpw,email FROM account order by acc";
	private static final String GET_BY_ACC = "SELECT acc,nick,accpw,email FROM account where acc = ?";
	private static final String DELETE = "DELETE FROM account where acc = ?";
	private static final String UPDATE = "UPDATE account set nick=?, email=?  where acc = ?";
	private static final String USER_PW ="UPDATE account set accpw=?  where acc = ?";
	private static final String USER_EMAIL ="UPDATE account set email=?  where acc = ?";
	@Override
	public void insert(AccountVO accountVO) {
		// TODO Auto-generated method stub

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, accountVO.getAcc());
			pstmt.setString(2, accountVO.getNick());
			pstmt.setString(3, accountVO.getAccpw());
			pstmt.setString(4, accountVO.getEmail());
			pstmt.executeUpdate();

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

	}

	@Override
	public void update(AccountVO accountVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, accountVO.getNick());
			pstmt.setString(2,accountVO.getEmail());
			pstmt.setString(3,accountVO.getAcc());
			pstmt.executeUpdate();

			// Handle any driver errors
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
	}
	@Override
	public void updateByPw(AccountVO accountVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(USER_PW);
			pstmt.setString(1, accountVO.getAccpw());
			pstmt.setString(2, accountVO.getAcc());
		
			pstmt.executeUpdate();

			// Handle any driver errors
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
	}
	
	@Override
	public void updateByEmail(AccountVO accountVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(USER_EMAIL);
			pstmt.setString(1, accountVO.getEmail());
			pstmt.setString(2, accountVO.getAcc());
		
			pstmt.executeUpdate();

			// Handle any driver errors
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
	}
	
	@Override
	public void delete(String acc) {
		// TODO Auto-generated method stub

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, acc);

			pstmt.executeUpdate();

			// Handle any driver errors
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
	}

	@Override
	public AccountVO getOneAccountVOByACC(String acc) {
		// TODO Auto-generated method stub
		AccountVO accountVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_ACC);

			pstmt.setString(1, acc);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				accountVO = new AccountVO();
				accountVO.setAcc(rs.getString("acc"));
				accountVO.setNick(rs.getString("nick"));
				accountVO.setAccpw(rs.getString("accpw"));
				accountVO.setEmail(rs.getString("email"));

			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			
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
			
		return accountVO;

	}

	

	@Override
	public List<AccountVO> getAll() {
		// TODO Auto-generated method stub
		List<AccountVO> list = new ArrayList<AccountVO>();
		AccountVO accountVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_ACC_DATA);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				accountVO = new AccountVO();
				accountVO.setAcc(rs.getString("acc"));
				accountVO.setNick(rs.getString("nick"));
				accountVO.setAccpw(rs.getString("accpw"));
				accountVO.setEmail(rs.getString("email"));
				list.add(accountVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (SQLException se) {
			se.printStackTrace();
//			throw new RuntimeException("A database error occured. "
//					+ se.getMessage());
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

	

}
