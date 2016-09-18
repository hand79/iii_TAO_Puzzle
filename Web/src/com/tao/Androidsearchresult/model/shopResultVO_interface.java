package com.tao.Androidsearchresult.model;

import java.util.List;

import com.tao.androidshop.model.ShopVO;



public interface shopResultVO_interface {
	public List<ShopVO> findShopByNameKey(String keyword);
	
	public List<shopResultVO> findShopBySubCat(Integer subcatno, Integer area);
	

}
