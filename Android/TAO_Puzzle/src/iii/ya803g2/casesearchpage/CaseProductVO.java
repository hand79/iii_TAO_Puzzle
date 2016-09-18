package iii.ya803g2.casesearchpage;

import java.io.Serializable;

public class CaseProductVO implements Serializable {

	private static final long serialVersionUID = 6139028425799636468L;
	private Integer cpno;
	private Integer memno;
	private String name;
	private Integer unitprice;
	private byte[] pic1;
	private byte[] pic2;
	private byte[] pic3;
	private String pmime1;
	private String pmime2;
	private String pmime3;
	private String cpdesc;
	private Integer subcatno;
	
	public CaseProductVO(){
		super();
	}
	
	
	public Integer getCpno() {
		return cpno;
	}
	public void setCpno(Integer cpno) {
		this.cpno = cpno;
	}
	public Integer getMemno() {
		return memno;
	}
	public void setMemno(Integer memno) {
		this.memno = memno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getUnitprice() {
		return unitprice;
	}
	public void setUnitprice(Integer unitprice) {
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
	public String getCpdesc() {
		return cpdesc;
	}
	public void setCpdesc(String cpdesc) {
		this.cpdesc = cpdesc;
	}
	public Integer getSubcatno() {
		return subcatno;
	}
	public void setSubcatno(Integer subcatno) {
		this.subcatno = subcatno;
	}
	
	
	
	
	
}
