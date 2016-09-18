package com.tao.shopRep.model;

import java.util.List;
import java.util.Map;

public interface ShopRepDAO_interface {
	public void insert(ShopRepVO shopRepVO);
	public void update(ShopRepVO shopRepVO);
	public void delete(Integer srepno);
	public ShopRepVO findByPrimaryKey(Integer srepno);
	public List<ShopRepVO> getAll();
	
	public List<ShopRepVO> getAll(Map<String, String[]>map);
}
