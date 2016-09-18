package com.tao.hotcasesandshops.model;

public class HotShopVO {
	private Integer shopno;
	private Integer	memno;
	private String title;
	private byte[] pic;
	private String mime;
	private Integer locno;
	private Integer hits;
	private Integer Status;
		
	
	public Integer getLocno() {
		return locno;
	}
	public void setLocno(Integer locno) {
		this.locno = locno;
	}
	public Integer getShopno() {
		return shopno;
	}
	public void setShopno(Integer shopno) {
		this.shopno = shopno;
	}
	public Integer getMemno() {
		return memno;
	}
	public void setMemno(Integer memno) {
		this.memno = memno;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public byte[] getPic() {
		return pic;
	}
	public void setPic(byte[] pic) {
		this.pic = pic;
	}
	public String getMime() {
		return mime;
	}
	public void setMime(String mime) {
		this.mime = mime;
	}
	public Integer getHits() {
		return hits;
	}
	public void setHits(Integer hits) {
		this.hits = hits;
	}
	public Integer getStatus() {
		return Status;
	}
	public void setStatus(Integer status) {
		Status = status;
	}

	

}
