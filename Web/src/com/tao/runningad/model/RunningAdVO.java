package com.tao.runningad.model;

import java.sql.Date;

public class RunningAdVO {
	private Integer adno;
	private byte[] adpic;
	private String mime;
	private Date sdate;
	private Date edate;
	private Integer memno;
	private Date reqtime;
	private Integer tst;
	private Integer dtime;
	private Integer caseno;
	

	public Integer getAdno() {
		return adno;
	}
	public void setAdno(Integer adno) {
		this.adno = adno;
	}
	public byte[] getAdpic() {
		return adpic;
	}
	public void setAdpic(byte[] adpic) {
		this.adpic = adpic;
	}
	public String getMime() {
		return mime;
	}
	public void setMime(String mime) {
		this.mime = mime;
	}
	public Date getSdate() {
		return sdate;
	}
	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}
	public Date getEdate() {
		return edate;
	}
	public void setEdate(Date edate) {
		this.edate = edate;
	}
	public Integer getMemno() {
		return memno;
	}
	public void setMemno(Integer memno) {
		this.memno = memno;
	}
	public Date getReqtime() {
		return reqtime;
	}
	public void setReqtime(Date reqtime) {
		this.reqtime = reqtime;
	}
	public Integer getTst() {
		return tst;
	}
	public void setTst(Integer tst) {
		this.tst = tst;
	}
	public Integer getDtime() {
		return dtime;
	}
	public void setDtime(Integer dtime) {
		this.dtime = dtime;
	}
	public Integer getCaseno() {
		return caseno;
	}
	public void setCaseno(Integer caseno) {
		this.caseno = caseno;
	}
	

}
