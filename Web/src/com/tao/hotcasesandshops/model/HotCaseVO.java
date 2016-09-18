package com.tao.hotcasesandshops.model;

public class HotCaseVO {
	private Integer caseno;
	private	String title;
	private	Integer cpno;
	private	Integer spno;
	private Integer locno;
	private Integer status;
	private	Integer hits;
	private byte[] pic1;
	private	String pmime1;
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getCaseno() {
		return caseno;
	}
	public void setCaseno(Integer caseno) {
		this.caseno = caseno;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getCpno() {
		return cpno;
	}
	public void setCpno(Integer cpno) {
		this.cpno = cpno;
	}
	public Integer getSpno() {
		return spno;
	}
	public void setSpno(Integer spno) {
		this.spno = spno;
	}
	public Integer getLocno() {
		return locno;
	}
	public void setLocno(Integer locno) {
		this.locno = locno;
	}
	public Integer getHits() {
		return hits;
	}
	public void setHits(Integer hits) {
		this.hits = hits;
	}
	public byte[] getPic1() {
		return pic1;
	}
	public void setPic1(byte[] pic1) {
		this.pic1 = pic1;
	}
	public String getPmime1() {
		return pmime1;
	}
	public void setPmime1(String pmime1) {
		this.pmime1 = pmime1;
	}
	
	
}
