package com.tao.searchresult.model;

public class shopResultVO {
	private Integer shopno;
	private String title;
	private Integer spno;
	private String name;
	private String pro_desc;
	private Integer hits;
	private Integer locno;

	public Integer getHits() {
		return hits;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}

	public Integer getShopno() {
		return shopno;
	}

	public void setShopno(Integer shopno) {
		this.shopno = shopno;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getSpno() {
		return spno;
	}

	public void setSpno(Integer spno) {
		this.spno = spno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPro_desc() {
		return pro_desc;
	}

	public void setPro_desc(String pro_desc) {
		this.pro_desc = pro_desc;
	}

	public Integer getLocno() {
		return locno;
	}

	public void setLocno(Integer locno) {
		this.locno = locno;
	}
}
