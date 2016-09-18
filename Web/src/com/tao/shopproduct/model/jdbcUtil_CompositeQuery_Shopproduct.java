/*
 *  1. �U�νƦX�d��-�i�ѫȤ���H�N�W�����Q�d�ߪ����
 *  2. ���F�קK�v�T�į�:
 *        �ҥH�ʺA���͸U��SQL������,���d�ҵL�N�ĥ�MetaData���覡,�]�u�w��ӧO��Table�ۦ���ݭn�ӭӧO�s�@��
 * */

package com.tao.shopproduct.model;

import java.util.*;

public class jdbcUtil_CompositeQuery_Shopproduct {

	public static String get_aCondition_For_Oracle(String columnName, String value) {

		String aCondition = null;
		
		if ("spno".equals(columnName)) 
			aCondition = columnName + "=" + value;
		else if ("shopno".equals(columnName)) 
			aCondition = columnName + "=" + value;
		else if ("name".equals(columnName)) 
			aCondition = columnName + " like '%" + value + "%'";
		else if ("subcatno".equals(columnName)) 
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

	public static void testshopproductquery() {

		// �t�X req.getParameterMap()��k �^�� java.util.Map<java.lang.String,java.lang.String[]> ������
		Map<String, String[]> map = new TreeMap<String, String[]>();
		map.put("spno", new String[] { "3002" });
		map.put("shopno", new String[] { "2002" });
		map.put("name", new String[] { "�]" });
		map.put("subcatno", new String[] { "23" });
		map.put("action", new String[] { "getXXX" }); // �`�NMap�̭��|�t��action��key

		String finalSQL = "select * from shopproduct "
				          + jdbcUtil_CompositeQuery_Shopproduct.get_WhereCondition(map)
				          + "order by spno";
		System.out.println("����finalSQL = " + finalSQL);

	}
}
