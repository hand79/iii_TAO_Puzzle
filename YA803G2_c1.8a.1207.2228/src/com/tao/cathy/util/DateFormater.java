package com.tao.cathy.util;

import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;

public class DateFormater {
	private static Format fm = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public static String formatTimestamp(Timestamp ts) {

		String str = fm.format(ts);

		return str;
	}
	public static String formatTimestamp2(Timestamp ts) {
		Format fm = new SimpleDateFormat("yyyy-MM-dd");
		String str = fm.format(ts);

		return str;
	}
	
}
