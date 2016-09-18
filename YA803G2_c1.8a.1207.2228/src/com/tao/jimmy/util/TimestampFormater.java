package com.tao.jimmy.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TimestampFormater {
	private static SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd kk:mm");
	
	public static String format(Timestamp ts){
		String re=null;
		if(ts != null)
			re = sdf.format(ts);
//		re = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM).format(ts);
		return re;
	} 
}
