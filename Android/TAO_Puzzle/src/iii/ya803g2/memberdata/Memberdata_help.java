package iii.ya803g2.memberdata;

public class Memberdata_help { // VO- Value Object
	private int ordno;//訂單編號
	private int bmemno;//購買會員編號
	private int caseno;//合購案編號
	private int price;//訂單金額
	private int ordtime;//下單時間
	private int memno;//會員編號
	private int memid;//會員帳號
	private byte[] image;//會員照片
	
	private String cratedesc;//對方給的評價敘述
	
	

	public Memberdata_help(int bmemno , int ordtime , String cratedesc) {
		super();
		this.bmemno = bmemno;
		this.ordtime = ordtime;
		this.cratedesc = cratedesc;
	}
	
	
	
	public int getOrdno() {
		return ordno;
	}
	public void setOrdno(int ordno) {
		this.ordno = ordno;
	}
	public int getBmemno() {
		return bmemno;
	}
	public void setBmemno(int bmemno) {
		this.bmemno = bmemno;
	}
	public int getCaseno() {
		return caseno;
	}
	public void setCaseno(int caseno) {
		this.caseno = caseno;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getOrdtime() {
		return ordtime;
	}
	public void setOrdtime(int ordtime) {
		this.ordtime = ordtime;
	}
	public int getMemno() {
		return memno;
	}
	public void setMemno(int memno) {
		this.memno = memno;
	}
	public int getMemid() {
		return memid;
	}
	public void setMemid(int memid) {
		this.memid = memid;
	}
	public String getCratedesc() {
		return cratedesc;
	}
	public void setCratedesc(String cratedesc) {
		this.cratedesc = cratedesc;
	}



	public byte[] getImage() {
		return image;
	}



	public void setImage(byte[] image) {
		this.image = image;
	}
	
}
	
	
	

	
