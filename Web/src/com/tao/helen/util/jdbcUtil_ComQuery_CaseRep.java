package com.tao.helen.util;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class jdbcUtil_ComQuery_CaseRep {

	public static String get_aCondition_For_Oracle(String columnName, String value) {

		String aCondition = null;

		if ("crepno".equals(columnName) || "repno".equals(columnName) || "susno".equals(columnName)|| "repcaseno".equals(columnName)) // �Ω��L
			aCondition = columnName + "=" + value;
		else if ("creprsn".equals(columnName) || "creprsn".equals(columnName)) // �Ω�varchar
			aCondition = columnName + " like '%" + value + "%'";
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

	public static void main(String argv[]) {

		// �t�X req.getParameterMap()��k �^�� java.util.Map<java.lang.String,java.lang.String[]> ������
		Map<String, String[]> map = new TreeMap<String, String[]>();
		map.put("crepno", new String[] { "2" });
		map.put("action", new String[] { "getXXX" }); // �`�NMap�̭��|�t��action��key

		String finalSQL = "select * from caserep "
				          + jdbcUtil_ComQuery_DpsOrd.get_WhereCondition(map)
				          + "order by crepno";
		System.out.println("����finalSQL = " + finalSQL);

	}
}
