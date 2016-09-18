package com.tao.cases.model;

import com.tao.jimmy.util.model.ColumnValue;
import com.tao.jimmy.util.model.Compare;
import com.tao.jimmy.util.model.ParameterParser;

public class CasesParameterParser extends ParameterParser {
	private static String TABLE_COLS = "caseno, title, memno, cpno, spno, locno, discount, "
			+ "stime, etime, minqty, maxqty, status, "
			+ "hits, casedesc, ship1, shipcost1, ship2, shipcost2, threshold ";
	private static String MULTPLE_SELECTION_COLS = "spno, locno";
	private static String ADDITIONAL_PARAMS = "stimefrom, stimeto, etimefrom, etimeto";
	private static String DELIMITER = ",";

	public CasesParameterParser() {
		super(TABLE_COLS, MULTPLE_SELECTION_COLS, ADDITIONAL_PARAMS, DELIMITER);
	}

	protected ColumnValue processSpecialParams(String trimedKey, String[] values) {
		ColumnValue c = null;
		if (values == null || values.length == 0 || values[0] == null
				|| values[0].length() == 0) {
			return c;
		}
		String value = values[0];
		Compare o = null;
		String colname = null;
		switch (trimedKey) {
		case "stimefrom":
			colname = "stime";
			o = Compare.GTEqual;
			break;
		case "etimefrom":
			colname = "etime";
			o = Compare.GTEqual;
			break;
		case "stimeto":
			colname = "stime";
			o = Compare.LTEqual;
			break;
		case "etimeto":
			colname = "etime";
			o = Compare.LTEqual;
			break;
		}
		c = new ColumnValue(colname, value, o);
		return c;
	}

}
