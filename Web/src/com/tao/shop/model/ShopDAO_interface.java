package com.tao.shop.model;

import java.util.*;


public interface ShopDAO_interface {
    public void insert(ShopVO shopVO);
    public void update(ShopVO shopVO);
    public void delete(Integer shopno);
    public void update_status(Integer status,Integer shopno);
    public ShopVO findByPrimaryKey(Integer shopno);
    public List<ShopVO> getAll();
    public List<ShopVO> getAll(Map<String, String[]> map); 
    public List<ShopVO> getWaitApproveShop();
	public ShopVO checkStatus(Integer shopno);
}
