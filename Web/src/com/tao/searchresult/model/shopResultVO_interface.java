package com.tao.searchresult.model;

import java.util.List;

public interface shopResultVO_interface {
	public List<shopResultVO> findShopByNameKey(String keyword);
	
	public List<shopResultVO> findShopBySubCat(Integer subcatno, Integer area);
	

}
