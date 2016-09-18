package com.tao.hotcasesandshops.model;

import java.util.List;

public class HotShopService {
	private HotShopDAO_interface dao;
	
	public HotShopService() {
		dao=new HotShopDAO();

	}
	public HotShopVO findByShopno(Integer shopno){
		return dao.findByShopno(shopno);
		
	}
	public HotShopVO findByIsrecommPic(Integer shopno){
		return dao.findByIsrecommPic(shopno);
		
	}
	/********************Get_ALL_No_Pic************************************/
	public List<HotShopVO>getAllByHits(){
		return dao.getAllByHits();
		
	}
	public List<HotShopVO>getAllByHitsStatus(){
		return dao.getAllByHitsStatus();
		
	} 
	public List<HotShopVO>getAllByHitsStatusLocno(Integer locno){
		return dao.getAllByHitsStatusLocno(locno);
		
	}
}
