package com.tao.acc.model;

public class AccountVO implements java.io.Serializable{
	private String acc;
	private String nick;
	private String accpw;
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAcc() {
		return acc;
	}

	public void setAcc(String acc) {
		this.acc = acc;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getAccpw() {
		return accpw;
	}

	public void setAccpw(String accpw) {
		this.accpw = accpw;
	}

}
