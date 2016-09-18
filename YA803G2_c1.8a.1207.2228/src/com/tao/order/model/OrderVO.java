package com.tao.order.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.tao.jimmy.util.TimestampFormater;

public class OrderVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3969928328975582083L;
	private Integer ordno;
	private Integer bmemno;
	private Integer cmemno;
	private Integer caseno;
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

	public Integer getBmemno() {
		return bmemno;
	}

	public void setBmemno(Integer bmemno) {
		this.bmemno = bmemno;
	}

	public Integer getCmemno() {
		return cmemno;
	}

	public void setCmemno(Integer cmemno) {
		this.cmemno = cmemno;
	}

	public Integer getCaseno() {
		return caseno;
	}

	public void setCaseno(Integer caseno) {
		this.caseno = caseno;
	}

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
}

