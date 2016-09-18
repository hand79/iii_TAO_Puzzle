package com.tao.hibernate.jimmy;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import com.tao.jimmy.util.TimestampFormater;

public class OrderVO implements Serializable{
	public static final int STATUS_CREATED = 0;
	public static final int STATUS_ACHIEVED = 1;
	public static final int STATUS_BUYER_COMFIRMED = 2;
	public static final int STATUS_CREATOR_COMFIRMED = 3;
	public static final int STATUS_CONFLICT = 4;
	public static final int STATUS_COMPLETED = 5;
	public static final int STATUS_CANCELED = 9;
	public static final int STATUS_BOTH_COMFIRMED = 6;
	private static final long serialVersionUID = 3969928328975582083L;
	private Integer ordno;
	
	private MemberVO bmemvo;
	private MemberVO cmemvo;
	private CasesVO casevo;
	
//	private Set<OrderVO> orders;
//	private Integer bmemno;
//	private Integer cmemno;
//	private Integer caseno;
	
	private Integer qty;
	private Integer price;
	private Timestamp ordtime;
	private Integer status;
	private Integer brate;
	private String brateDesc;
	private Integer crate;
	private String crateDesc;
	private Integer ship;
	
	public OrderVO(){
		super();
	}

	public Integer getOrdno() {
		return ordno;
	}

	public void setOrdno(Integer ordno) {
		this.ordno = ordno;
	}

//	public Integer getBmemno() {
//		return bmemno;
//	}
//
//	public void setBmemno(Integer bmemno) {
//		this.bmemno = bmemno;
//	}
//
//	public Integer getCmemno() {
//		return cmemno;
//	}
//
//	public void setCmemno(Integer cmemno) {
//		this.cmemno = cmemno;
//	}

//	public Integer getCaseno() {
//		return caseno;
//	}
//
//	public void setCaseno(Integer caseno) {
//		this.caseno = caseno;
//	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Timestamp getOrdtime() {
		return ordtime;
	}

	public void setOrdtime(Timestamp ordtime) {
		this.ordtime = ordtime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getBrate() {
		return brate;
	}

	public void setBrate(Integer brate) {
		this.brate = brate;
	}

	public String getBrateDesc() {
		return brateDesc;
	}

	public void setBrateDesc(String brateDesc) {
		this.brateDesc = brateDesc;
	}

	public Integer getCrate() {
		return crate;
	}

	public void setCrate(Integer crate) {
		this.crate = crate;
	}

	public String getCrateDesc() {
		return crateDesc;
	}

	public void setCrateDesc(String crateDesc) {
		this.crateDesc = crateDesc;
	}
	
	public String getFormatedOrdtime(){
		return TimestampFormater.format(ordtime);
	}

	public Integer getShip() {
		return ship;
	}

	public void setShip(Integer ship) {
		this.ship = ship;
	}

	public MemberVO getBmemvo() {
		return bmemvo;
	}

	public void setBmemvo(MemberVO bmemvo) {
		this.bmemvo = bmemvo;
	}

	public MemberVO getCmemvo() {
		return cmemvo;
	}

	public void setCmemvo(MemberVO cmemvo) {
		this.cmemvo = cmemvo;
	}

	public CasesVO getCasevo() {
		return casevo;
	}

	public void setCasevo(CasesVO casevo) {
		this.casevo = casevo;
	}

//	public Set<OrderVO> getOrders() {
//		return orders;
//	}
//
//	public void setOrders(Set<OrderVO> orders) {
//		this.orders = orders;
//	}
}

