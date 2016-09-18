package com.tao.order.model;

import com.tao.jimmy.util.model.ColumnValue;
import com.tao.jimmy.util.model.Compare;
import com.tao.jimmy.util.model.ParameterParser;

public class OrderParameterParser extends ParameterParser {
	private static String ALL_COLS = "ordno, bmemno, cmemno, caseno, qty, price, ordtime, status, brate, bratedesc, crate, cratedesc";
	private static final String MULTIPLE_COLS = "bmemno, cmemno";
	private static final String SPECIAL_PARAMS = "price, stime, etime";
	private static final String DELIMETER = ",";

	protected OrderParameterParser() {
		super(ALL_COLS, MULTIPLE_COLS, SPECIAL_PARAMS, DELIMETER);
	}

	@Override
	protected ColumnValue processSpecialParams(String trimedKey, String[] values) {
		ColumnValue c = null;
		if ("price".equals(trimedKey) && validArray(values)
				&& values[0] != null && values[0].contains("@")) {
			String[] valueTokens = values[0].split("@");
			if (valueTokens.length == 2 && validString(valueTokens[1])) {
				switch (valueTokens[0]) {
				case "gt":
					c = new ColumnValue("price", valueTokens[1],
							Compare.GTEqual);
					break;
				case "lt":
					c = new ColumnValue("price", valueTokens[1],
							Compare.LTEqual);
					break;
				case "eq":
					c = new ColumnValue("price", valueTokens[1],
							Compare.Equal);
					break;
				}
			}
		}
		
		if("stime".equals(trimedKey)){
			c = new ColumnValue("ordtime", values[0], Compare.GTEqual);
		}
		
		if("etime".equals(trimedKey)){
			c = new ColumnValue("ordtime", values[0], Compare.LTEqual);
		}
		
		return c;
	}

	
}
