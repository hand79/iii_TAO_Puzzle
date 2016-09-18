package com.tao.helen.util;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class jdbcUtil_ComQuery_WtdReq {
	
	public static String get_aCondition_For_Oracle(String columnName, String value){
		
		String aCondition = null;
		
		if ("wtdreqno".equals(columnName) || "wtdamnt".equals(columnName) || "memno".equals(columnName)) // 用於其他
			aCondition = columnName + "=" + value;
		else if ("wtdac".equals(columnName) || "wtdac".equals(columnName)) // 用於varchar
			aCondition = columnName + " like '%" + value + "%'";
		else if ("reqsts".equals(columnName) || "reqsts".equals(columnName)) // 用於varchar
			aCondition = columnName + " like '%" + value + "%'";
		else if ("wtdreqt".equals(columnName))                          // 用於Oracle的date
			aCondition = "to_char(" + columnName + ",'yyyy-mm-dd')='" + value + "'";

		return aCondition + " ";
	}
	
	public static String get_WhereCondition(Map<String, String[]> map) {
		
		Set<String> keys = map.keySet();
		StringBuffer whereCondition = new StringBuffer();
		int count = 0;
		for (String key : keys) {
			String value = map.get(key)[0];
			if (value != null && value.trim().length() != 0	&& !"action".equals(key)) {
				count++;
				String aCondition = get_aCondition_For_Oracle(key, value.trim());

				if (count == 1)
					whereCondition.append(" where " + aCondition);
				else
					whereCondition.append(" and " + aCondition);

				System.out.println("有送出查詢資料的欄位數count = " + count);
			}
		}
		
		return whereCondition.toString();
	}
	
	public static void main(String argv[]){
		
		// 配合 req.getParameterMap()方法 回傳 java.util.Map<java.lang.String,java.lang.String[]> 之測試
				Map<String, String[]> map = new TreeMap<String, String[]>();
				map.put("wtdreqno", new String[] { "2" });
				map.put("action", new String[] { "getXXX" }); // 注意Map裡面會含有action的key

				String finalSQL = "select * from wtdreq "
						          + jdbcUtil_ComQuery_DpsOrd.get_WhereCondition(map)
						          + "order by wtdreqno";
				System.out.println("●●finalSQL = " + finalSQL);
	}

}
