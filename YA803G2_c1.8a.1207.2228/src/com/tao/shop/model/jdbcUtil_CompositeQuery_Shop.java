/*
 *  1. �U�νƦX�d��-�i�ѫȤ���H�N�W�����Q�d�ߪ����
 *  2. ���F�קK�v�T�į�:
 *        �ҥH�ʺA���͸U��SQL������,���d�ҵL�N�ĥ�MetaData���覡,�]�u�w��ӧO��Table�ۦ���ݭn�ӭӧO�s�@��
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

				System.out.println("���e�X�d�߸�ƪ�����count = " + count);
			}
		}
		
		return whereCondition.toString();
	}

	public static void testshopquery() {

		// �t�X req.getParameterMap()��k �^�� java.util.Map<java.lang.String,java.lang.String[]> ������
		Map<String, String[]> map = new TreeMap<String, String[]>();
		map.put("shopno", new String[] { "2002" });
		map.put("memno", new String[] { "1014" });
		map.put("title", new String[] { "�x" });
		map.put("locno", new String[] { "16" });
		map.put("status", new String[] { "3" });
		map.put("action", new String[] { "getXXX" }); // �`�NMap�̭��|�t��action��key

		String finalSQL = "select * from shop "
				          + jdbcUtil_CompositeQuery_Shop.get_WhereCondition(map)
				          + "order by shopno";
		System.out.println("����finalSQL = " + finalSQL);

	}
}
