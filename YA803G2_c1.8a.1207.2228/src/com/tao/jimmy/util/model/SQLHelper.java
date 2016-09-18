package com.tao.jimmy.util.model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class SQLHelper {
	private static boolean printSQL = false;
	public static int DATE_COLS = 1;
	public static int TIMESTAMP_COLS = 2;

	synchronized public static boolean isPrintSQL() {
		return printSQL;
	}

	synchronized public static void setPrintSQL(boolean printSQL) {
		SQLHelper.printSQL = printSQL;
	}

	public static String buildQueryStmt(String seletAllStmt,
			String whereStmtWithWhere/* with WHERE */, String orderByColName,
			boolean useDescending) {
		String s = null;
		boolean printSQL = isPrintSQL();
		if (seletAllStmt != null && seletAllStmt.trim().length() != 0) {
			StringBuilder sql = new StringBuilder(seletAllStmt.trim());

			if (whereStmtWithWhere != null
					&& whereStmtWithWhere.trim().length() != 0) {
				sql.append(" ").append(whereStmtWithWhere);
			}

			if (orderByColName != null && orderByColName.trim().length() != 0) {
				String desc = useDescending ? " DESC" : "";
				sql.append(" ORDER BY ").append(orderByColName).append(desc);
			}

			s = sql.toString();
			if (printSQL) {
				System.out.println("=================================");
				System.out.println("Statement generated by SQLBuilder ("
						+ new java.util.Date() + "):");
				System.out.println(s);
				System.out.println("=================================");
			}
		}
		return s;
	}

	public static String buildQueryStmt(String selectAllStmt,
			ColumnValue[] whereArgs, String orderByColName,
			boolean buildPreparedStmt, boolean useAnd, boolean useDescending) {
		if (whereArgs == null || whereArgs.length == 0) {
			return buildQueryStmt(selectAllStmt, "", orderByColName,
					useDescending);
		}
		ColumnValueBundle[] bd = new ColumnValueBundle[] { new ColumnValueBundle(
				useAnd, whereArgs) };
		return buildComplicatedQueryStmt(selectAllStmt, orderByColName,
				buildPreparedStmt, useAnd, useDescending, bd);
		// String whereCondition = null;
		// if (whereArgs != null) {
		//
		// StringBuilder sql = new StringBuilder("WHERE ");
		//
		// String link = useAnd ? " AND " : " OR ";
		// int length = whereArgs.length;
		// for (int i = 0; i < length; i++) {
		// ColumnValue current = whereArgs[i];
		// sql.append("(").append(current.getColname());
		//
		// if (current.isBetweenFlag()) {
		// String str1 = buildPreparedStmt ? "?" : current
		// .getColvalue();
		// String str2 = buildPreparedStmt ? "?" : current
		// .getColvalue2forBetween();
		// sql.append(" BETWEEN ").append(str1).append(" AND ")
		// .append(str2);
		// } else if (current.isLikeFlag()) {
		// String str = buildPreparedStmt ? "?" : current
		// .getColvalue();
		//
		// sql.append(" LIKE ").append(str);
		// } else if (current.isUseNull()) {
		// String str = current.isNull() ? " IS NULL" : " IS NOT NULL";
		// sql.append(str);
		// } else {
		// String value = buildPreparedStmt ? "?" : current
		// .getColvalue();
		// String operator = null;
		// switch (current.getCompare()) {
		// case GreaterThan:
		// operator = ">";
		// break;
		// case LessTahn:
		// operator = "<";
		// break;
		// case GTEqual:
		// operator = ">=";
		// break;
		// case LTEqual:
		// operator = "<=";
		// break;
		// case Equal:
		// default:
		// operator = "=";
		// }
		// sql.append(operator).append(value);
		// }
		// sql.append(")");
		// if (i != length - 1) {
		// sql.append(link);
		// }
		// }
		// whereCondition = sql.toString();
		// }
		//
		// String str = buildQueryStmt(selectAllStmt, whereCondition,
		// orderByColName, useDescending);
		// return str;
	}

	public static String buildQueryStmt(String selectAllStmt,
			ColumnValue[] whereArgs, String orderByColName,
			boolean buildPreparedStmt, boolean useAnd) {

		return buildQueryStmt(selectAllStmt, whereArgs, orderByColName,
				buildPreparedStmt, useAnd, false);
	}

	public static String buildQueryStmt(String selectAllStmt,
			ColumnValue[] whereArgs, String orderByColName,
			boolean buildPreparedStmt) {

		return buildQueryStmt(selectAllStmt, whereArgs, orderByColName,
				buildPreparedStmt, true, false);
	}

	public static void setPreparedStmt(PreparedStatement pstmt,
			ColumnValue[] whereArgs) throws SQLException {
		setPreparedStmt(pstmt, whereArgs, null, null);
	}

	public static void setPreparedStmt(PreparedStatement pstmt,
			ColumnValue[] whereArgs, String[] cols, int type)
			throws SQLException {
		if (type == TIMESTAMP_COLS)
			setPreparedStmt(pstmt, whereArgs, cols, null);
		else if (type == DATE_COLS)
			setPreparedStmt(pstmt, whereArgs, null, cols);
	}

	public static void setPreparedStmt(PreparedStatement pstmt,
			ColumnValue[] whereArgs, String[] timeStampCols, String[] dateCols)
			throws SQLException {
		ColumnValueBundle cvb = null;
		if (whereArgs != null && whereArgs.length != 0) {
			cvb = new ColumnValueBundle(whereArgs);
		}
		setComplicatedPreparedStmt(pstmt, timeStampCols, dateCols, cvb);
		// if (whereArgs != null) {
		// int length = whereArgs.length;
		// int count = 1;
		//
		// for (int i = 0; i < length; i++) {
		// ColumnValue current = whereArgs[i];
		// String name = current.getColname();
		// if (current.isUseNull()) {
		// continue;
		// }
		// if (timeStampCols != null && timeStampCols.length != 0) {
		// boolean timestampflag = false;
		// for (String str : timeStampCols) {
		// if (name.toLowerCase().equals(str.toLowerCase())) {
		// timestampflag = true;
		// break;
		// }
		// }
		// if (timestampflag) {
		// if (current.isBetweenFlag()) {
		// pstmt.setTimestamp(count++,
		// Timestamp.valueOf(current.getColvalue()));
		// pstmt.setTimestamp(count++, Timestamp
		// .valueOf(current.getColvalue2forBetween()));
		// } else {
		// pstmt.setTimestamp(count++,
		// Timestamp.valueOf(current.getColvalue()));
		// }
		// continue;
		// }
		// }
		// if (dateCols != null && dateCols.length != 0) {
		// boolean dateflag = false;
		// for (String str : dateCols) {
		// if (name.toLowerCase().equals(str.toLowerCase())) {
		// dateflag = true;
		// break;
		// }
		// }
		// if (dateflag) {
		// if (current.isBetweenFlag()) {
		// pstmt.setDate(count++,
		// Date.valueOf(current.getColvalue()));
		// pstmt.setDate(count++, Date.valueOf(current
		// .getColvalue2forBetween()));
		// } else {
		// pstmt.setDate(count++,
		// Date.valueOf(current.getColvalue()));
		// }
		// continue;
		// }
		// }
		// if (current.isBetweenFlag()) {
		// pstmt.setString(count++, current.getColvalue());
		// pstmt.setString(count++, current.getColvalue2forBetween());
		// } else {
		// pstmt.setString(count++, current.getColvalue());
		// }
		//
		// }
		// }
	}

	public static String buildComplicatedQueryStmt(String selectAllStmt,
			String orderByColName, boolean buildPreparedStmt, boolean useAnd,
			boolean useDescending, ColumnValueBundle... whereArgBuldles) {

		return buildComplicatedQueryStmt(selectAllStmt, orderByColName,
				buildPreparedStmt, useAnd, useDescending, true, whereArgBuldles);
	}

	public static String buildComplicatedQueryStmt(String selectAllStmt,
			String orderByColName, boolean buildPreparedStmt, boolean useAnd,
			boolean useDescending, boolean matchCondition,
			ColumnValueBundle... whereArgBuldles) {
		if (printSQL)
			System.out
					.println("**** SQLHelper.buildComplicatedQueryStmt Start ****");
		String whereCondition = null;
		if (isValidArray(whereArgBuldles)) {
			String whereLeading =  matchCondition ? "WHERE ": "WHERE NOT ( ";
			String whereEnding = matchCondition ? "" : " )";
			StringBuilder sql = new StringBuilder(whereLeading);
			String outerLink = useAnd ? " AND " : " OR ";

			int bundlesLength = 0;
			for (ColumnValueBundle cvb : whereArgBuldles) {
				if (cvb != null) {
					bundlesLength++;
				}
			}

			for (int j = 0; j < bundlesLength; j++) {
				ColumnValueBundle currentBuldle = whereArgBuldles[j];
				if (currentBuldle == null) {
					continue;
				}
				if (printSQL)
					System.out.println("Current Buldle: " + currentBuldle);
				ColumnValue[] currentArray = currentBuldle.getColumnValues();
				String link = currentBuldle.isUseAnd() ? " AND " : " OR ";
				int length = currentArray.length;

				if (whereArgBuldles.length > 1 && length > 1) {
					sql.append("(");
				}

				for (int i = 0; i < length; i++) {
					ColumnValue current = currentArray[i];
					if (current == null) {
						continue;
					}
					sql.append("(").append(current.getColname());

					if (current.isBetweenFlag()) {
						String str1 = buildPreparedStmt ? "?" : current
								.getColvalue();
						String str2 = buildPreparedStmt ? "?" : current
								.getColvalue2forBetween();
						sql.append(" BETWEEN ").append(str1).append(" AND ")
								.append(str2);
					} else if (current.isLikeFlag()) {
						String str = buildPreparedStmt ? "?" : current
								.getColvalue();

						sql.append(" LIKE ").append(str);
					} else if (current.isUseNull()) {
						String str = current.isNull() ? " IS NULL"
								: " IS NOT NULL";
						sql.append(str);
					} else {
						String value = buildPreparedStmt ? "?" : current
								.getColvalue();
						String operator = null;
						switch (current.getCompare()) {
						case GreaterThan:
							operator = ">";
							break;
						case LessTahn:
							operator = "<";
							break;
						case GTEqual:
							operator = ">=";
							break;
						case LTEqual:
							operator = "<=";
							break;
						case NotEqual:
							operator = "!=";
							break;
						case Equal:
						default:
							operator = "=";
						}
						sql.append(operator).append(value);
					}
					sql.append(")");
					if (i != length - 1) {
						sql.append(link);
					}

				}// End iterating ColumnValue[]
				if (whereArgBuldles.length > 1 && length > 1) {
					sql.append(")");
				}
				if (j != bundlesLength - 1) {
					sql.append(outerLink);
					if (printSQL)
						System.out
								.println("Appending outer Link: " + outerLink);
				}
			}// End iterating ColumnValueBundle[]
			whereCondition = sql.toString() + whereEnding;
			if (printSQL)
				System.out.println("Completed whereCondition: "
						+ whereCondition);
		}

		String str = buildQueryStmt(selectAllStmt, whereCondition,
				orderByColName, useDescending);
		if (printSQL)
			System.out
					.println("**** SQLHelper.buildComplicatedQueryStmt End   ****");
		return str;
	}

	public static void setComplicatedPreparedStmt(PreparedStatement pstmt,
			String[] timeStampCols, String[] dateCols,
			ColumnValueBundle... whereArgBuldles) throws SQLException {
		if (printSQL)
			System.out
					.println("++++ SQLHelper.setComplicatedPreparedStmt Start ++++");
		if (isValidArray(whereArgBuldles)) {
			int count = 1;
			for (int j = 0; j < whereArgBuldles.length; j++) {
				ColumnValueBundle currentBundle = whereArgBuldles[j];
				if (currentBundle == null) {
					continue;
				}
				ColumnValue[] whereArgs = currentBundle.getColumnValues();
				if (isValidArray(whereArgs)) {
					int length = whereArgs.length;

					for (int i = 0; i < length; i++) {
						ColumnValue current = whereArgs[i];
						if (current == null) {
							continue;
						}
						String name = current.getColname();
						if (current.isUseNull()) {
							continue;
						}// Escape "is null" and "is not null" conditions
						if (isValidArray(timeStampCols)) {
							boolean timestampflag = false;
							for (String str : timeStampCols) {
								if (name.toLowerCase()
										.equals(str.toLowerCase())) {
									timestampflag = true;
									break;
								}
							}
							if (timestampflag) {
								if (current.isBetweenFlag()) {
									if (printSQL)
										System.out
												.print(" SetTimestamp (Between from): "
														+ count);
									pstmt.setTimestamp(count++, Timestamp
											.valueOf(current.getColvalue()));
									if (printSQL)
										System.out
												.print(" SetTimestamp (Between to): "
														+ count);
									pstmt.setTimestamp(count++, Timestamp
											.valueOf(current
													.getColvalue2forBetween()));
								} else {
									if (printSQL)
										System.out.print(" SetTimestamp: "
												+ count);
									pstmt.setTimestamp(count++, Timestamp
											.valueOf(current.getColvalue()));
								}
								continue;
							}
						}// End of SetTimestamp
						if (isValidArray(dateCols)) {
							boolean dateflag = false;
							for (String str : dateCols) {
								if (name.toLowerCase()
										.equals(str.toLowerCase())) {
									dateflag = true;
									break;
								}
							}
							if (dateflag) {
								if (current.isBetweenFlag()) {
									if (printSQL)
										System.out
												.print(" SetDate (Between from): "
														+ count);
									pstmt.setDate(count++,
											Date.valueOf(current.getColvalue()));
									if (printSQL)
										System.out
												.print(" SetDate (Between to): "
														+ count);
									pstmt.setDate(count++, Date.valueOf(current
											.getColvalue2forBetween()));
								} else {
									if (printSQL)
										System.out.print(" SetDate: " + count);
									pstmt.setDate(count++,
											Date.valueOf(current.getColvalue()));
								}
								continue;
							}
						}// End of SetDate
						if (current.isBetweenFlag()) {
							if (printSQL)
								System.out.print(" SetString (Between from): "
										+ count);
							pstmt.setString(count++, current.getColvalue());
							if (printSQL)
								System.out.print(" SetString (Between to): "
										+ count);
							pstmt.setString(count++,
									current.getColvalue2forBetween());
						} else {
							if (printSQL)
								System.out.print(" SetString: " + count);
							pstmt.setString(count++, current.getColvalue());
						}// End of SetString

						if (count % 5 == 1 && count != 1) {
							System.out.println();
						}
					}// End of for loop (iterate whereArg)
				}// End of whereArg check
			}// End of for loop (iterate whereArgBundles)
		}// End of whereArgBundles check
		if (printSQL)
			System.out.println();
		if (printSQL)
			System.out
					.println("++++ SQLHelper.setComplicatedPreparedStmt End   ++++");
	}// End of setComplictedPreparedStmt()

	private static boolean isValidArray(Object[] array) {
		return (array != null && array.length != 0);
	}
}// End of Class

