package com.tao.hotcasesandshops.model;



import java.util.List;



public interface HotShopDAO_interface {
	public HotShopVO findByShopno(Integer shopno);
	public HotShopVO findByIsrecommPic(Integer shopno);
	public List<HotShopVO>getAllByHits();
	public List<HotShopVO>getAllByHitsStatus(); 
	public List<HotShopVO>getAllByHitsStatusLocno(Integer locno); 
    
}
