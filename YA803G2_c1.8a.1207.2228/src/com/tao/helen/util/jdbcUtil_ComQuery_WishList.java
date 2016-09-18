package com.tao.helen.util;

import java.util.*;


public class jdbcUtil_ComQuery_WishList {

	public static String get_aCondition_For_Oracle(String columnName,
			String value) {

		String aCondition = null;

		if ("wlno".equals(columnName) || "caseno".equals(columnName)
				|| "memno".equals(columnName)) // �Ω��L
			aCondition = columnName + "=" + value;
		return aCondition + " ";
	}

	public static String get_WhereCondition(Map<String, String[]> map) {

		Set<String> keys = map.keySet();
		StringBuffer whereCondition = new StringBuffer();
		int count = 0;
		for (String key : keys) {
			String value = map.get(key)[0];
			if (value != null && value.trim().length() != 0
					&& !"action".equals(key)) {
				count++;
				String aCondition = get_aCondition_For_Oracle(key, value.trim());

				if (count == 1)
					whereCondition.append(" where " + aCondition);

				System.out.println("���e�X�d�߸�ƪ�����count = " + count);
			}
		}

		return whereCondition.toString();
	}

	public static void main(String argv[]) {

		// �t�X req.getParameterMap()��k �^��
		// java.util.Map<java.lang.String,java.lang.String[]> ������
		Map<String, String[]> map = new TreeMap<String, String[]>();
		map.put("wlno", new String[] { "2" });
		map.put("action", new String[] { "getXXX" }); // �`�NMap�̭��|�t��action��key

		String finalSQL = "select * from wishlist "
				+ jdbcUtil_ComQuery_WishList.get_WhereCondition(map)
				+ "order by wlno";
		System.out.println("����finalSQL = " + finalSQL);
	}

}
