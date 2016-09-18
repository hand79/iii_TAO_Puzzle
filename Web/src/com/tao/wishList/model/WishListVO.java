package com.tao.wishList.model;

import java.io.Serializable;

public class WishListVO implements Serializable {

	private Integer wlno;
	private Integer memno;
	private Integer caseno;
	
	public Integer getWlno() {
		return wlno;
	}
	public void setWlno(Integer wlno) {
		this.wlno = wlno;
	}
	public Integer getMemno() {
		return memno;
	}
	public void setMemno(Integer memno) {
		this.memno = memno;
	}
	public Integer getCaseno() {
		return caseno;
	}
	public void setCaseno(Integer caseno) {
		this.caseno = caseno;
	}
	
	
	
}
