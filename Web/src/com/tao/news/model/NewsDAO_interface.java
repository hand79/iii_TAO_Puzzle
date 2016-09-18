package com.tao.news.model;

import java.sql.Timestamp;
import java.util.*;

import com.tao.shopproduct.model.ShopproductVO;

public interface NewsDAO_interface {
    public void insert(NewsVO newsVO);
    public void update(NewsVO newsVO);
    public void delete(Integer newsno);
    public NewsVO findByPrimaryKey(Integer newsno);
    public List<NewsVO> getAll();
    public List<NewsVO> getAll(Map<String, String[]> map); 
    public List<NewsVO> getByNews(Integer newsno);
}
