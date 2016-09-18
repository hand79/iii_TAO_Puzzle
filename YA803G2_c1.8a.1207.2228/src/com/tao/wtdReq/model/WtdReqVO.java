package com.tao.wtdReq.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class WtdReqVO implements Serializable {

	private Integer wtdreqno;
	private Timestamp wtdreqt;
	private Integer memno;
	private Double wtdamnt;
	private String wtdac;
	private String reqsts;
	
	public Integer getWtdreqno() {
		return wtdreqno;
	}
	public void setWtdreqno(Integer wtdreqno) {
		this.wtdreqno = wtdreqno;
	}
	public Timestamp getWtdreqt() {
		return wtdreqt;
	}
	public void setWtdreqt(Timestamp wtdreqt) {
		this.wtdreqt = wtdreqt;
	}
	public Integer getMemno() {
		return memno;
	}
	public void setMemno(Integer memno) {
		this.memno = memno;
	}
	public Double getWtdamnt() {
		return wtdamnt;
	}
	public void setWtdamnt(Double wtdamnt) {
		this.wtdamnt = wtdamnt;
	}
	public String getWtdac() {
		return wtdac;
	}
	public void setWtdac(String wtdac) {
		this.wtdac = wtdac;
	}
	public String getReqsts() {
		return reqsts;
	}
	public void setReqsts(String reqsts) {
		this.reqsts = reqsts;
	}
	
	
	
}
