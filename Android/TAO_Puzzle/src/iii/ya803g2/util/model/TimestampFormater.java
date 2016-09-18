package iii.ya803g2.util.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class TimestampFormater {
	private static SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd kk:mm");
	
	public static String format(Timestamp ts){
		String re=null;
		if(ts != null)
			re = sdf.format(ts);
		return re;
	} 
}
