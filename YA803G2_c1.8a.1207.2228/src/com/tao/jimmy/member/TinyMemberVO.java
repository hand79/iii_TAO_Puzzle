package com.tao.jimmy.member;

import java.io.Serializable;

public class TinyMemberVO implements Serializable{
	private static final long serialVersionUID = -2879761620928611007L;
	private Integer memno;
	private String memid;
	private Integer point;
	
	public TinyMemberVO(){
		super();
	}
	public Integer getMemno() {
		return memno;
	}
	public void setMemno(Integer memno) {
		this.memno = memno;
	}
	public String getMemid() {
		return memid;
	}
	public void setMemid(String memid) {
		this.memid = memid;
	}
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	
}

