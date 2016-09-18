package com.tao.Androidsearchresult.model;

import java.util.List;

import com.tao.androidshop.model.ShopVO;



public class shopResultService {
	private shopResultVO_interface dao; 
	
	public shopResultService(){
		dao = new shopResultDAO();
	}
	
	public List<ShopVO> findShopByNameKey(String keyword){
		return dao.findShopByNameKey(keyword);
	}
	
	public List<shopResultVO> findShopBySubCat(Integer subcatno, Integer area){
		return dao.findShopBySubCat(subcatno, area);
	}
}
