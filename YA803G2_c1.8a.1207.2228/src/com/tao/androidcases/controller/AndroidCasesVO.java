package com.tao.androidcases.controller;

import java.io.Serializable;

import com.tao.Androidsearchresult.model.shopResultVO;
import com.tao.androidshop.model.ShopVO;
import com.tao.androidshopproduct.model.ShopproductVO;
import com.tao.caseRep.model.CaseRepVO;
import com.tao.cases.model.CaseProductVO;
import com.tao.cases.model.CasesVO;
import com.tao.category.model.CategoryVO;
import com.tao.dpsOrd.model.DpsOrdVO;
import com.tao.jimmy.location.LocationVO;
import com.tao.member.model.MemberVO;
import com.tao.news.model.NewsVO;
import com.tao.order.model.OrderVO;
import com.tao.wishList.model.WishListVO;





public class AndroidCasesVO implements Serializable {
	
	private CaseProductVO caseproductvo;
	private byte[] pic1;
	private Integer unitprice;
	private CasesVO casesvo;
	private MemberVO membervo;
	private Integer TotalOty;
	private Integer helpCaseQA;
	private ShopVO shopvo;
	private ShopproductVO shopproductVO;
	private DpsOrdVO dpsOrdVO;
	private OrderVO orderVO;
	private NewsVO newsVO;
	private WishListVO wishListVO;
	private String result;
	private Integer finalRate;
	private CaseRepVO caseRepVO;
	private String newstitle;
	private Integer count;
	private CategoryVO categoryVO;
	private shopResultVO shopkeywordVO;
	private LocationVO locationVO;
	public CaseProductVO getCaseproductvo() {
		return caseproductvo;
	}
	public void setCaseproductvo(CaseProductVO caseproductvo) {
		this.caseproductvo = caseproductvo;
	}
	public byte[] getPic1() {
		return pic1;
	}
	public void setPic1(byte[] pic1) {
		this.pic1 = pic1;
	}
	public Integer getUnitprice() {
		return unitprice;
	}
	public void setUnitprice(Integer unitprice) {
		this.unitprice = unitprice;
	}
	public CasesVO getCasesvo() {
		return casesvo;
	}
	public void setCasesvo(CasesVO casesvo) {
		this.casesvo = casesvo;
	}
	public MemberVO getMembervo() {
		return membervo;
	}
	public void setMembervo(MemberVO membervo) {
		this.membervo = membervo;
	}
	public Integer getTotalOty() {
		return TotalOty;
	}
	public void setTotalOty(Integer totalOty) {
		TotalOty = totalOty;
	}
	public Integer getHelpCaseQA() {
		return helpCaseQA;
	}
	public void setHelpCaseQA(Integer helpCaseQA) {
		this.helpCaseQA = helpCaseQA;
	}
	public ShopVO getShopvo() {
		return shopvo;
	}
	public void setShopvo(ShopVO shopvo) {
		this.shopvo = shopvo;
	}
	public ShopproductVO getShopproductVO() {
		return shopproductVO;
	}
	public void setShopproductVO(ShopproductVO shopproductVO) {
		this.shopproductVO = shopproductVO;
	}
	public DpsOrdVO getDpsOrdVO() {
		return dpsOrdVO;
	}
	public void setDpsOrdVO(DpsOrdVO dpsOrdVO) {
		this.dpsOrdVO = dpsOrdVO;
	}
	public OrderVO getOrderVO() {
		return orderVO;
	}
	public void setOrderVO(OrderVO orderVO) {
		this.orderVO = orderVO;
	}
	public NewsVO getNewsVO() {
		return newsVO;
	}
	public void setNewsVO(NewsVO newsVO) {
		this.newsVO = newsVO;
	}
	public WishListVO getWishListVO() {
		return wishListVO;
	}
	public void setWishListVO(WishListVO wishListVO) {
		this.wishListVO = wishListVO;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Integer getFinalRate() {
		return finalRate;
	}
	public void setFinalRate(Integer finalRate) {
		this.finalRate = finalRate;
	}
	public CaseRepVO getCaseRepVO() {
		return caseRepVO;
	}
	public void setCaseRepVO(CaseRepVO caseRepVO) {
		this.caseRepVO = caseRepVO;
	}
	public String getNewstitle() {
		return newstitle;
	}
	public void setNewstitle(String newstitle) {
		this.newstitle = newstitle;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public CategoryVO getCategoryVO() {
		return categoryVO;
	}
	public void setCategoryVO(CategoryVO categoryVO) {
		this.categoryVO = categoryVO;
	}
	public shopResultVO getShopkeywordVO() {
		return shopkeywordVO;
	}
	public void setShopkeywordVO(shopResultVO shopkeywordVO) {
		this.shopkeywordVO = shopkeywordVO;
	}
	public LocationVO getLocationVO() {
		return locationVO;
	}
	public void setLocationVO(LocationVO locationVO) {
		this.locationVO = locationVO;
	}
	
	

	
	
//	private CasesVO casesvo;
//	private String memId;
//	private CaseProduct cpvo;
	
}
