/*
 *  1. �U�νƦX�d��-�i�ѫȤ���H�N�W�����Q�d�ߪ����
 *  2. ���F�קK�v�T�į�:
 *        �ҥH�ʺA���͸U��SQL������,���d�ҵL�N�ĥ�MetaData���覡,�]�u�w��ӧO��Table�ۦ���ݭn�ӭӧO�s�@��
 * */


package com.tao.news.model;

import java.util.*;

public class jdbcUtil_CompositeQuery_News {

	public static String get_aCondition_For_Oracle(String columnName, String value) {

		String aCondition = null;
		
		if ("newsno".equals(columnName)) // �Ω��L
			aCondition = columnName + "=" + value;
		else if ("title".equals(columnName)) // �Ω�varchar
			aCondition = columnName + " like '%" + value + "%'";
//		else if ("pubtime".equals(columnName))                          // �Ω�Oracle��date
//			aCondition = "to_char(" + columnName + ",'yyyy-mm-dd')='" + value + "'";
		else if ("pubtimeStart".equals(columnName))
			aCondition = "pubtime >= to_timestamp('" + value+ "','YYYY-MM-DD')";
		else if ("pubtimeEnd".equals(columnName))
			aCondition = "pubtime <= to_timestamp('" + value+ "','YYYY-MM-DD')";
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

	public static void testnewsquery() {

		// �t�X req.getParameterMap()��k �^�� java.util.Map<java.lang.String,java.lang.String[]> ������
		Map<String, String[]> map = new TreeMap<String, String[]>();
		map.put("newsno", new String[] { "11" });
		map.put("title", new String[] { "testTitle" });
		map.put("pubtimeStart", new String[] { "2014-11-03" });
		map.put("pubtimeEnd", new String[] { "2014-11-06" });
		map.put("action", new String[] { "getXXX" }); // �`�NMap�̭��|�t��action��key

		String finalSQL = "select * from news "
				          + jdbcUtil_CompositeQuery_News.get_WhereCondition(map)
				          + "order by newsno;";
		System.out.println("����finalSQL = " + finalSQL);

	}
}
