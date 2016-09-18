package com.tao.hibernate.jimmy;

public class MemberVO {
	private Integer memno;
	private String memid;
//	private String mempw;
	private String fname;
	private String lname;
	private String idnum;
	private String gender;
//	private Integer locno;
//	private String addr;
	private String tel;
	private String email;
//	private Integer point;
	private Integer money;
//	private Integer addays;
//	private Integer status;
//	private Integer type;
	private Integer withhold; 
	
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
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getIdnum() {
		return idnum;
	}
	public void setIdnum(String idnum) {
		this.idnum = idnum;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getMoney() {
		return money;
	}
	public void setMoney(Integer money) {
		this.money = money;
	}
	public Integer getWithhold() {
		return withhold;
	}
	public void setWithhold(Integer withhold) {
		this.withhold = withhold;
	}


}
