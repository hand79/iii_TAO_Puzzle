package com.tao.searchresult.model;

import java.util.List;

public class shopResultService {
	private shopResultVO_interface dao; 
	
	public shopResultService(){
		dao = new shopResultDAO();
	}
	
	public List<shopResultVO> findShopByNameKey(String keyword){
		return dao.findShopByNameKey(keyword);
	}
	
	public List<shopResultVO> findShopBySubCat(Integer subcatno, Integer area){
		return dao.findShopBySubCat(subcatno, area);
	}
}
