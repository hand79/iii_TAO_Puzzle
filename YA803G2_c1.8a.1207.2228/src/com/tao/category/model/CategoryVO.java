package com.tao.category.model;

public class CategoryVO	implements java.io.Serializable {
	private Integer catno;
	private String catname;

	public Integer getCatno() {
		return catno;
	}

	public void setCatno(Integer catno) {
		this.catno = catno;
	}

	public String getCatname() {
		return catname;
	}

	public void setCatname(String catname) {
		this.catname = catname;
	}

}
