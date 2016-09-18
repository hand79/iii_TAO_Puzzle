package iii.ya803g2.shopsearchpage;


public class ShopproductVO implements java.io.Serializable{
	private Integer spno;
	private Integer shopno;
	private String name;
	private Double unitprice;
	private byte[] pic1;
	private byte[] pic2;
	private byte[] pic3;
	private String pmime1;
	private String pmime2;
	private String pmime3;
	private String pro_desc;
	private Integer subcatno;
	private String spec1;
	private String spec2;
	private String spec3;
	private Integer isrecomm;
	public Integer getSpno() {
		return spno;
	}
	public void setSpno(Integer spno) {
		this.spno = spno;
	}
	public Integer getShopno() {
		return shopno;
	}
	public void setShopno(Integer shopno) {
		this.shopno = shopno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getUnitprice() {
		return unitprice;
	}
	public void setUnitprice(Double unitprice) {
		this.unitprice = unitprice;
	}
	public byte[] getPic1() {
		return pic1;
	}
	public void setPic1(byte[] pic1) {
		this.pic1 = pic1;
	}
	public byte[] getPic2() {
		return pic2;
	}
	public void setPic2(byte[] pic2) {
		this.pic2 = pic2;
	}
	public byte[] getPic3() {
		return pic3;
	}
	public void setPic3(byte[] pic3) {
		this.pic3 = pic3;
	}
	public String getPmime1() {
		return pmime1;
	}
	public void setPmime1(String pmime1) {
		this.pmime1 = pmime1;
	}
	public String getPmime2() {
		return pmime2;
	}
	public void setPmime2(String pmime2) {
		this.pmime2 = pmime2;
	}
	public String getPmime3() {
		return pmime3;
	}
	public void setPmime3(String pmime3) {
		this.pmime3 = pmime3;
	}
	public String getPro_desc() {
		return pro_desc;
	}
	public void setPro_desc(String pro_desc) {
		this.pro_desc = pro_desc;
	}
	public Integer getSubcatno() {
		return subcatno;
	}
	public void setSubcatno(Integer subcatno) {
		this.subcatno = subcatno;
	}
	public String getSpec1() {
		return spec1;
	}
	public void setSpec1(String spec1) {
		this.spec1 = spec1;
	}
	public String getSpec2() {
		return spec2;
	}
	public void setSpec2(String spec2) {
		this.spec2 = spec2;
	}
	public String getSpec3() {
		return spec3;
	}
	public void setSpec3(String spec3) {
		this.spec3 = spec3;
	}
	public Integer getIsrecomm() {
		return isrecomm;
	}
	public void setIsrecomm(Integer isrecomm) {
		this.isrecomm = isrecomm;
	}
	
	//(spno,shopno,name,unitprice,pic1,pic2,pic3,pmime1,pmime2,pmime3,pro_desc,subcatno,spec1,spec2,spec3,isrecomm)
	//(SHOP_seq.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
	//spno=?,shopno=?,name=?,unitprice=?,pic1=?,pic2=?,pic3=?,pmime1=?,pmime2=?,pmime3=?,
	//pro_desc=?,subcatno=?,spec1=?,spec2=?,spec3=?,isrecomm=?
	
	
}
