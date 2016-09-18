package com.ya803g2.jdbc.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

public class DAO {
	private String user = "YA803G2";
	private String password = "YA803G2";
	private JTextArea textArea;

	DAO(String user, String password, JTextArea textArea)
			throws ClassNotFoundException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		if (user != null && user.trim().length() != 0) {
			this.user = user;
		}
		if (password != null && password.trim().length() != 0) {
			this.password = password;
		}
		this.textArea = textArea;
	}

	public void writeCLOB(List<String> clobs, String tableName, String pkColName,
			String clobColName) {
		Connection con = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<String> msgs = new ArrayList<>();
		try {
			con = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe", user, password);

			msgs.add("DB Connection Established.");
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT " + pkColName + " FROM " + tableName
					+ " ORDER BY " + pkColName + " ASC");
			ArrayList<String> list = new ArrayList<String>();
			while (rs.next()) {
				String s = rs.getString(1);
				list.add(s);
			}
			rs.close();/**/
			stmt.close();/**/

			msgs.add("\n****\nQuery Table \"" + tableName + "\" and get "
					+ list.size() + " rows.");
			System.out.println("Query Table \"" + tableName + "\" and get "
					+ list.size() + " rows.");
			pstmt = con.prepareStatement("UPDATE " + tableName + " SET "
					+ clobColName + "=? WHERE " + pkColName + "=?");
			int max = Math.max(list.size(), clobs.size());
			
			for(int i = 0; i< max; i++){
				pstmt.setString(1, clobs.get(i));
				pstmt.setString(2, list.get(i));
				int updateCount = pstmt.executeUpdate();
				msgs.add(updateCount + " Row("
						+ pkColName + "=" + list.get(i)
						+ ") Updated.");
			}

		} catch (SQLException e) {
			msgs.add(e.getMessage());
			StackTraceElement[] stes = e.getStackTrace();
			for (StackTraceElement ste : stes) {
				msgs.add(ste.toString());
			}
			e.printStackTrace(System.err);
		} finally {
			try {
				if(pstmt != null){
					pstmt.close();
				}
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		msgs.add("");
		msgs.add("Finish");
		printMsgs(msgs);
	}

	public void writeToDB(List<Table> tables, File picDir) {
		Connection con = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<String> msgs = new ArrayList<>();
		try {
			con = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe", user, password);

			msgs.add("DB Connection Established.");

			// iterate tables
			for (Table table : tables) {

				// query table
				stmt = con.createStatement();
				rs = stmt.executeQuery("SELECT " + table.getPkColumn()
						+ " FROM " + table.getName() + " ORDER BY "
						+ table.getPkColumn() + " ASC");

				ArrayList<String> list = new ArrayList<String>();
				while (rs.next()) {
					String s = rs.getString(1);
					list.add(s);
				}
				rs.close();/**/
				stmt.close();/**/

				msgs.add("\n****\nQuery Table \"" + table.getName()
						+ "\" and get " + list.size() + " rows.");
				System.out.println("Query Table \"" + table.getName()
						+ "\" and get " + list.size() + " rows.");

				pstmt = con.prepareStatement(table.getUpdateSql());
				File tPicDir = new File(picDir, table.getName());
				FileFilter ff = new FileFilter() {

					@Override
					public boolean accept(File pathname) {
						if (pathname.isDirectory())
							return true;
						return false;
					}
				};
				FilenameFilter fnf = new FilenameFilter() {
					@Override
					public boolean accept(File dir, String name) {
						String lname = name.toLowerCase();
						if (lname.endsWith(".jpg") || lname.endsWith(".jpeg")
								|| lname.endsWith(".gif")
								|| lname.endsWith(".png")) {
							return true;
						}
						return false;
					}
				};

				File[] rows = tPicDir.listFiles(ff);
				if (rows != null) {
					// update rows
					for (int i = 0; i < list.size(); i++) {
						File rowDir = null;
						if (i < rows.length)
							rowDir = rows[i];

						if (rowDir != null && rowDir.exists()
								&& rowDir.isDirectory()) {
							msgs.add("rowDir '" + rowDir.getName()
									+ "' comfirmed, begin update...");
							File[] pics = rowDir.listFiles(fnf);

							int picCount = table.getBlob().size();
							for (int j = 1; j <= picCount; j++) {
								File current = null;
								if (j <= pics.length)
									current = pics[j - 1];
								FileInputStream in = null;
								String mime = null;
								if (current != null && current.exists()) {
									in = new FileInputStream(current);
									mime = getMimeType(current);
								}
								pstmt.setBinaryStream(j * 2 - 1, in);
								pstmt.setString(j * 2, mime);
							}
							pstmt.setString(picCount * 2 + 1, list.get(i));
							int updateCount = pstmt.executeUpdate();
							msgs.add(updateCount + " Row("
									+ table.getPkColumn() + "=" + list.get(i)
									+ ") Updated.");
						}
					}
				}
				pstmt.close();
			}
		} catch (SQLException | FileNotFoundException e) {
			msgs.add(e.getMessage());
			StackTraceElement[] stes = e.getStackTrace();
			for (StackTraceElement ste : stes) {
				msgs.add(ste.toString());
			}
			e.printStackTrace(System.err);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		msgs.add("");
		msgs.add("Finish");
		printMsgs(msgs);
	}

	private void printMsgs(List<String> msgs) {
		if (textArea != null) {
			textArea.setText("");
			for (String s : msgs) {
				textArea.append("\n" + s);
			}
		}
	}

	private String getMimeType(File current) {
		String path = current.getPath();
		String fileExtName = path.substring(path.lastIndexOf(".") + 1,
				path.length()).toLowerCase();
		switch (fileExtName) {
		case "jpg":
		case "jpeg":
			return "image/jpeg";
		case "gif":
			return "image/gif";
		case "png":
			return "image/png";
		default:
			return null;
		}

	}
}
