package com.ya803g2.jdbc.util;

public class BlobRowData {
	private byte[][] pics;
	private String[] mimes;
	private String pkValue;
	public byte[][] getPics() {
		return pics;
	}
	public void setPics(byte[][] pics) {
		this.pics = pics;
	}
	public String[] getMimes() {
		return mimes;
	}
	public void setMimes(String[] mimes) {
		this.mimes = mimes;
	}
	public String getPkValue() {
		return pkValue;
	}
	public void setPkValue(String pkValue) {
		this.pkValue = pkValue;
	}
	public BlobRowData(byte[][] pics, String[] mimes, String pkValue) {
		super();
		this.pics = pics;
		this.mimes = mimes;
		this.pkValue = pkValue;
	}
	
	
}
