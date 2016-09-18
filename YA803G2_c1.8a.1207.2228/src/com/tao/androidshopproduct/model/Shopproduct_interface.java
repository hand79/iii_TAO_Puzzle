package com.tao.androidshopproduct.model;

import java.util.List;


public interface Shopproduct_interface {
    public int insert(ShopproductVO sptVO);
    public int update(ShopproductVO sptVO);
    public int delete(Integer spno);
    public ShopproductVO findByPrimaryKey(Integer spno);
    public List<ShopproductVO> getAll();
    //萬用複合查詢(傳入參數型態Map)(回傳 List)
//  public List<EmpVO> getAll(Map<String, String[]> map); 

}
