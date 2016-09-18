package com.tao.shopRep.model;

import java.io.Serializable;

public class ShopRepVO implements Serializable {
	
	private Integer srepno;
	private Integer shopno;
	private Integer repno;
	private String sreprsn;
	
	public Integer getSrepno() {
		return srepno;
	}
	public void setSrepno(Integer srepno) {
		this.srepno = srepno;
	}
	public Integer getShopno() {
		return shopno;
	}
	public void setShopno(Integer shopno) {
		this.shopno = shopno;
	}
	public Integer getRepno() {
		return repno;
	}
	public void setRepno(Integer repno) {
		this.repno = repno;
	}
	public String getSreprsn() {
		return sreprsn;
	}
	public void setSreprsn(String sreprsn) {
		this.sreprsn = sreprsn;
	}
	

}
