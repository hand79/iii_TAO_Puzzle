package com.tao.category.model;

public class SubCategoryVO implements java.io.Serializable{
	private Integer subcatno;
	private String subcatname;
	private Integer catno;

	public Integer getSubcatno() {
		return subcatno;
	}

	public void setSubcatno(Integer subcatno) {
		this.subcatno = subcatno;
	}

	public String getSubcatname() {
		return subcatname;
	}

	public void setSubcatname(String subcatname) {
		this.subcatname = subcatname;
	}

	public Integer getCatno() {
		return catno;
	}

	public void setCatno(Integer catno) {
		this.catno = catno;
	}

}
