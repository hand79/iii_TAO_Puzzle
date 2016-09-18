package com.tao.jimmy.util.model;

import java.io.Serializable;

public class ColumnValue implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8098816194461467204L;
//	public static enum Compare{
//		Equal, GreaterThan, LessTahn, GTEqual, LTEqual
//	};
	private String colname;
	private String colvalue;
	private boolean betweenFlag = false;
	private String colvalue2forBetween;
	private boolean likeFlag = false;
	private boolean useNull = false;
	private boolean isNull = false;
	private Compare compare = Compare.Equal;
	
	public ColumnValue(String colname, String colvalue, Compare compareCondition) {
		super();
		setColname(colname);
		setColvalue(colvalue);
		this.compare = compareCondition;
	}

	public ColumnValue(String colname, String colvalue) {
		super();
		setColname(colname);
		setColvalue(colvalue);
	}

	public ColumnValue(String colname, String colvalue,
			String colvalue2forBetween) {
		super();
		setColname(colname);
		setColvalue(colvalue);
		this.betweenFlag = true;
		setColvalue2forBetween(colvalue2forBetween);
	}

	public ColumnValue(String colname, String colvalue, boolean likeFlag) {
		super();
		setColname(colname);
		setColvalue(colvalue);
		this.likeFlag = likeFlag;
	}

	public ColumnValue(String colname, boolean isNull) {
		super();
		setColname(colname);
		this.useNull = true;
		this.isNull = isNull;
	}

	public String getColname() {
		return colname;
	}

	public String getColvalue() {
		return colvalue;
	}

	public boolean isBetweenFlag() {
		return betweenFlag;
	}

	public String getColvalue2forBetween() {
		return colvalue2forBetween;
	}

	public boolean isLikeFlag() {
		return likeFlag;
	}

	public boolean isUseNull() {
		return useNull;
	}

	public boolean isNull() {
		return isNull;
	}
	
	public Compare getCompare() {
		return compare;
	}

	private void setColname(String colname) {
		if (colname != null && colname.trim().length() != 0)
			this.colname = colname;
		else
			throw new IllegalArgumentException(
					"Colname should not be null or empty string");
	}

	private void setColvalue(String colvalue) {
		if(colvalue != null && colvalue.trim().length() != 0)
			this.colvalue = colvalue;
		else
			throw new IllegalArgumentException(
					"Colvalue should not be null or empty string");
	}

	private void setColvalue2forBetween(String colvalue2forBetween) {
		if(colvalue2forBetween != null && colvalue2forBetween.trim().length() != 0)
			this.colvalue2forBetween = colvalue2forBetween;
		else
			throw new IllegalArgumentException(
					"Colvalue2forBetween should not be null or empty string");
		
	}

	@Override
	public String toString() {
		
		return "{" + colname +":"+ colvalue + "}";
	}

}
