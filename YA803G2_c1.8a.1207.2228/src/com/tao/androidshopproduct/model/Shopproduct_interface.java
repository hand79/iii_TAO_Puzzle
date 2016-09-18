package com.tao.androidshopproduct.model;

import java.util.List;


public interface Shopproduct_interface {
    public int insert(ShopproductVO sptVO);
    public int update(ShopproductVO sptVO);
    public int delete(Integer spno);
    public ShopproductVO findByPrimaryKey(Integer spno);
    public List<ShopproductVO> getAll();
    //�U�νƦX�d��(�ǤJ�Ѽƫ��AMap)(�^�� List)
//  public List<EmpVO> getAll(Map<String, String[]> map); 

}
