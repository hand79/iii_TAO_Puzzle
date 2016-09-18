package com.tao.jimmy.util.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class ParamParserWrapper {
	private ParameterParser pp;
	private String[] joinParams;

	public ParamParserWrapper(ParameterParser pp, String[] joinParams) {
		this.pp = pp;
		this.joinParams = joinParams;
	}

	public ColumnValue[] doSimpleMapping(Map<String, String[]> paramMap) {
		ColumnValue[] cvs = pp.doSimpleMapping(paramMap);
		return cvs;
	}

	public ColumnValueBundle[] doComplicateMaping(Map<String, String[]> paramMap) {
		Map<String, String[]> map = processMap(paramMap);
		ColumnValueBundle[] cbs = pp.doComplicateMaping(map);
		return cbs;
	}

	private Map<String, String[]> processMap(Map<String, String[]> map) {
		Map<String, String[]> newMap = new HashMap<>();
		Set<String> keySet = map.keySet();
		for (String key : keySet) {
			if (key == null || key.trim().length() == 0) {
				continue;
			}
			String[] value = map.get(key);
			String trimedKey = key.trim();

			// check joinParams
			if (joinParams != null && joinParams.length != 0) {
				// iterate joinParams elements
				for (String joinCol : joinParams) {
					if (joinCol == null || joinCol.length() == 0) {
						continue;// escape if it is null or empty string
					}
					if (trimedKey.equals(joinCol.trim())) {
						value = getJoinedValues(trimedKey, value);
						key = getJoinColumnName(trimedKey);
						break;
					}
				}
			}// End of joinParams check
			if (key != null && value != null) {
				newMap.put(key, value);
			}
		}// End of for loop
		return newMap;
	}

	abstract protected String getJoinColumnName(String joinParam);

	abstract protected String[] getJoinedValues(String joinParam, String[] value);
}
