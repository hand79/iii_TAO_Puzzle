package com.tao.jimmy.util.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class ParameterParser {
	private Set<String> tableColumns;
	private Set<String> multipleSelectionCols;
	private Set<String> specialParams;
	private boolean showMsg = false;

	protected ParameterParser(String allColumns,
			String multipleSelectionCols, String specialParams, String delimiter) {
		this.tableColumns = getSetFromString(allColumns, delimiter);
		this.multipleSelectionCols = getSetFromString(multipleSelectionCols,
				delimiter);
		this.specialParams = getSetFromString(specialParams, delimiter);
	}

	public ColumnValue[] doSimpleMapping(Map<String, String[]> paramMap) {
		if (paramMap == null || paramMap.size() == 0) {
			return null;
		}
		Set<String> keySet = paramMap.keySet();
		List<ColumnValue> list = new ArrayList<>();

		for (String key : keySet) {
			String trimedKey = key.trim();
			if (!tableColumns.contains(trimedKey)
					&& !specialParams.contains(trimedKey)) {
				continue;
			}
			ColumnValue c = null;
			if (specialParams.contains(trimedKey)) {
				c = processSpecialParams(trimedKey, paramMap.get(key));
			} else {
				String value = paramMap.get(key)[0];
				if (validString(value))
					c = new ColumnValue(trimedKey, value);
			}
			if (c != null)
				list.add(c);
		}
		ColumnValue[] output = list.toArray(new ColumnValue[list.size()]);
		return output;

	}

	public ColumnValueBundle[] doComplicateMaping(Map<String, String[]> paramMap) {
		if (paramMap == null || paramMap.size() == 0) {
			return null;
		}
		Set<String> keySet = paramMap.keySet();
		List<ColumnValueBundle> outputList = new ArrayList<>();

		// ColumnValueBundle[] output = new
		// ColumnValueBundle[multipleSelectionCols
		// .size() + 1];
		// int outputCounter = 0;

		List<ColumnValue> list = new ArrayList<>();

		for (String key : keySet) {
			key = key.trim();
			String trimedKey = key.trim();
			if (!tableColumns.contains(trimedKey)
					&& !specialParams.contains(trimedKey)) {
				continue;
			}
			if (showMsg) {
				System.out.println();
				System.out.println("key: " + key);
			}
			ColumnValue c = null;
			if (specialParams.contains(trimedKey)) {
				if (showMsg)
					System.out.println("dospecial");
				c = processSpecialParams(trimedKey, paramMap.get(key));

			} else if (multipleSelectionCols.contains(trimedKey)) {
				if (showMsg)
					System.out.println("doMultiple");
				String[] values = paramMap.get(key);
				List<ColumnValue> group = new ArrayList<>();
				for (int i = 0; i < values.length; i++) {
					if (validString(values[i])) {
						group.add(new ColumnValue(trimedKey, values[i]));
					}
				}
				if (group.size() != 0) {
					ColumnValueBundle bundle = new ColumnValueBundle(false,
							group.toArray(new ColumnValue[group.size()]));
					outputList.add(bundle);
					if (showMsg)
						System.out
								.println("Add Multiple to Bundle list: Multiple length="
										+ group.size());
				}
				continue;

			} else {
				if (showMsg)
					System.out.println("do normal");
				String value = paramMap.get(key)[0];
				if (value != null && value.length() != 0)
					c = new ColumnValue(trimedKey, value);
			}// End of parameter type check

			if (c != null) {
				if (showMsg)
					System.out.println("added to misc list");
				list.add(c);
			}

		}// End for loop
		if (showMsg)
			System.out.println();
		ColumnValue[] listArray = list.toArray(new ColumnValue[list.size()]);
		if (listArray != null && listArray.length != 0) {
			outputList.add(new ColumnValueBundle(listArray));
		}
		return outputList.toArray(new ColumnValueBundle[outputList.size()]);
	}

	protected Set<String> getTableColumns() {
		return tableColumns;
	}

	protected Set<String> getMultipleSelectionCols() {
		return multipleSelectionCols;
	}

	protected Set<String> getSpecialParams() {
		return specialParams;
	}

	private Set<String> getSetFromString(String cols, String delimiter) {
		Set<String> set = new LinkedHashSet<>();
		if (cols != null) {
			String[] colArray = cols.split(delimiter);
			for (String s : colArray) {
				s = s.trim();
				if (s.length() != 0) {
					set.add(s);
				}
			}
		}
		return set;
	}

	protected boolean validString(String str) {
		return (str != null && str.trim().length() != 0);
	}

	protected boolean validArray(String[] strArray) {
		return (strArray != null && strArray.length != 0);
	}

	abstract protected ColumnValue processSpecialParams(String trimedKey,
			String[] values);

}
