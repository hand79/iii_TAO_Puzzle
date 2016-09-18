package com.tao.androidshop.model;

import java.util.*;


public interface ShopDAO_interface {
    public void insert(ShopVO shopVO);
    public void update(ShopVO shopVO);
    public void delete(Integer shopno);
    public ShopVO findByPrimaryKey(Integer shopno);
    public List<ShopVO> getAll();
    //萬用複合查詢(傳入參數型態Map)(回傳 List)
//  public List<EmpVO> getAll(Map<String, String[]> map); 

}
