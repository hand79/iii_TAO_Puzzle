/*
 *  1. 萬用複合查詢-可由客戶端隨意增減任何想查詢的欄位
 *  2. 為了避免影響效能:
 *        所以動態產生萬用SQL的部份,本範例無意採用MetaData的方式,也只針對個別的Table自行視需要而個別製作之
 * */


package com.tao.shop.model;

import java.util.*;

public class jdbcUtil_CompositeQuery_Shop {

	public static String get_aCondition_For_Oracle(String columnName, String value) {

		String aCondition = null;
		
		if ("shopno".equals(columnName)) 
			aCondition = columnName + "=" + value;
		else if ("memno".equals(columnName)) 
			aCondition = columnName + "=" + value;
		else if ("title".equals(columnName)) 
			aCondition = columnName + " like '%" + value + "%'";
		else if ("locno".equals(columnName)) 
			aCondition = columnName + "=" + value;
		else if ("status".equals(columnName)) 
			aCondition = columnName + "=" + value;
		


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

	public static void testshopquery() {

		// 配合 req.getParameterMap()方法 回傳 java.util.Map<java.lang.String,java.lang.String[]> 之測試
		Map<String, String[]> map = new TreeMap<String, String[]>();
		map.put("shopno", new String[] { "2002" });
		map.put("memno", new String[] { "1014" });
		map.put("title", new String[] { "洪" });
		map.put("locno", new String[] { "16" });
		map.put("status", new String[] { "3" });
		map.put("action", new String[] { "getXXX" }); // 注意Map裡面會含有action的key

		String finalSQL = "select * from shop "
				          + jdbcUtil_CompositeQuery_Shop.get_WhereCondition(map)
				          + "order by shopno";
		System.out.println("●●finalSQL = " + finalSQL);

	}
}
