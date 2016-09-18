package com.tao.shopproduct.model;

import java.util.List;
import java.util.Map;


public interface ShopproductDAO_interface {
    public int insert(ShopproductVO spVO);
    public int update(ShopproductVO spVO);
    public int delete(Integer spno);
    public ShopproductVO findByPrimaryKey(Integer spno);
    public List<ShopproductVO> getAll();
    public List<ShopproductVO> getAll(Map<String, String[]> map); 
    public List<ShopproductVO> getByshop(Integer shopno);
}
