package com.tao.caseRep.model;

import java.io.Serializable;

public class CaseRepVO implements Serializable {
	
	private Integer crepno;
	private Integer repno;
	private Integer susno;
	private Integer repcaseno;
	private String creprsn;
	
	public Integer getCrepno() {
		return crepno;
	}
	public void setCrepno(Integer crepno) {
		this.crepno = crepno;
	}
	public Integer getRepno() {
		return repno;
	}
	public void setRepno(Integer repno) {
		this.repno = repno;
	}
	public Integer getSusno() {
		return susno;
	}
	public void setSusno(Integer susno) {
		this.susno = susno;
	}
	public Integer getRepcaseno() {
		return repcaseno;
	}
	public void setRepcaseno(Integer repcaseno) {
		this.repcaseno = repcaseno;
	}
	public String getCreprsn() {
		return creprsn;
	}
	public void setCreprsn(String creprsn) {
		this.creprsn = creprsn;
	}
	
	

}
