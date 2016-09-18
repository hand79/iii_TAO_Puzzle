package com.tao.dpsOrd.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class DpsOrdVO implements Serializable {

	private Integer dpsordno;
	private Timestamp dpsordt;
	private Double dpsamnt;
	private Integer memno;
	private String dpshow;
	private String ordsts;
	private String atmac;
	
	public Integer getDpsordno() {
		return dpsordno;
	}
	public void setDpsordno(Integer dpsordno) {
		this.dpsordno = dpsordno;
	}
	public Timestamp getDpsordt() {
		return dpsordt;
	}
	public void setDpsordt(Timestamp dpsordt) {
		this.dpsordt = dpsordt;
	}
	public Double getDpsamnt() {
		return dpsamnt;
	}
	public void setDpsamnt(Double dpsamnt) {
		this.dpsamnt = dpsamnt;
	}
	public Integer getMemno() {
		return memno;
	}
	public void setMemno(Integer memno) {
		this.memno = memno;
	}
	public String getDpshow() {
		return dpshow;
	}
	public void setDpshow(String dpshow) {
		this.dpshow = dpshow;
	}
	public String getOrdsts() {
		return ordsts;
	}
	public void setOrdsts(String ordsts) {
		this.ordsts = ordsts;
	}
	public String getAtmac() {
		return atmac;
	}
	public void setAtmac(String atmac) {
		this.atmac = atmac;
	}
	
	
	
}
