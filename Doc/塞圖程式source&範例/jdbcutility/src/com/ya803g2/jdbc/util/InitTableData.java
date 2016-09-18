package com.ya803g2.jdbc.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import com.google.gson.Gson;

public class InitTableData {

	public static Vector<Table> getTables(File file) throws IOException {
		if (!file.exists()) {
			throw new IOException("file does not exist");
		}
		String str = null;
		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(file));) {
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}
		}
		String[] tables = sb.toString().split(";");
		Vector<Table> vector = new Vector<>();
		for (String s : tables) {
			if (s.trim().startsWith("//") || s.trim().length() == 0)
				continue;
			Table t = gson.fromJson(s, Table.class);
			vector.add(t);
		}
		return vector;
	}

	public static Vector<Table> getTables(String json) throws IOException {
		Gson gson = new Gson();
		String[] tables = json.split(";");
		Vector<Table> vector = new Vector<>();
		for (String s : tables) {
			Table t = gson.fromJson(s, Table.class);
			vector.add(t);
		}
		return vector;
	}

}
