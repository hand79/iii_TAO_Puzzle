package com.tao.shop.model;


public class ShopVO implements java.io.Serializable{
	private Integer shopno;
	private Integer memno;
	private String title;
	private String shop_desc;
	private byte[] pic;
	private String mime;
	private Integer locno;
	private String addr;
	private Double lng;
	private Double lat;
	private String phone;
	private String fax;
	private String email;
	private String ship_desc;
	private String other_desc;
	private Integer hits;
	private Integer status;
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
	public String getShop_desc() {
		return shop_desc;
	}
	public void setShop_desc(String shop_desc) {
		this.shop_desc = shop_desc;
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
	public Integer getLocno() {
		return locno;
	}
	public void setLocno(Integer locno) {
		this.locno = locno;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public Double getLng() {
		return lng;
	}
	public void setLng(Double lng) {
		this.lng = lng;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getShip_desc() {
		return ship_desc;
	}
	public void setShip_desc(String ship_desc) {
		this.ship_desc = ship_desc;
	}
	public String getOther_desc() {
		return other_desc;
	}
	public void setOther_desc(String other_desc) {
		this.other_desc = other_desc;
	}
	public Integer getHits() {
		return hits;
	}
	public void setHits(Integer hits) {
		this.hits = hits;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
