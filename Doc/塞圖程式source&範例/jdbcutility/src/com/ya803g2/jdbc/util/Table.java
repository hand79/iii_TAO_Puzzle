package com.ya803g2.jdbc.util;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Table {
	private String name;
	private Map<String, String> blob;// blobColumn, blobExtColumn
	private String pkColumn;

	public Table(String name, Map<String, String> blob, String pkColumn) {
		super();
		this.name = name;
		this.blob = blob;
		this.pkColumn = pkColumn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> getBlob() {
		return blob;
	}

	public void setBlob(Map<String, String> blob) {
		this.blob = blob;
	}

	public String getPkColumn() {
		return pkColumn;
	}

	public void setPkColumn(String pkColumn) {
		this.pkColumn = pkColumn;
	}

	public String getInsertSql() {
		if (blob.isEmpty())
			return null;
		StringBuilder sql = new StringBuilder("INSERT INTO " + name + " ");
		StringBuilder sql2 = new StringBuilder("VALUES (");

		Set<Entry<String, String>> es = blob.entrySet();
		int total = es.size();

		for (Entry<String, String> e : es) {
			sql.append(e.getKey() + "," + e.getValue() + ",");
		}
		sql = sql.deleteCharAt(sql.length() - 1).append(" ");

		for (int i = 0; i < total; i++) {
			sql2.append("?,?,");
		}
		sql2 = sql2.deleteCharAt(sql2.length() - 1).append(")");

		return sql.toString() + sql2.toString();
	}

	public String getUpdateSql() {
		StringBuilder sql = new StringBuilder("UPDATE " + name + " SET ");
		Set<Entry<String, String>> es = blob.entrySet();
		for (Entry<String, String> e : es) {
			sql.append(e.getKey() + "=?," + e.getValue() + "=?,");
		}
		sql = sql.deleteCharAt(sql.length() - 1).append(
				" WHERE " + pkColumn + "=?");
		return sql.toString();
	}
}
