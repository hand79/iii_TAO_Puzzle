package com.tao.androidshop.model;

import java.util.*;


public interface ShopDAO_interface {
    public void insert(ShopVO shopVO);
    public void update(ShopVO shopVO);
    public void delete(Integer shopno);
    public ShopVO findByPrimaryKey(Integer shopno);
    public List<ShopVO> getAll();
    //�U�νƦX�d��(�ǤJ�Ѽƫ��AMap)(�^�� List)
//  public List<EmpVO> getAll(Map<String, String[]> map); 

}
