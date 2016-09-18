package com.tao.wishList.model;

import java.util.*;



public interface WishListDAO_interface {
	public void insert(WishListVO wishListVO);
	public void update(WishListVO wishListVO);
	public void delete(Integer wlno);
	public WishListVO findByPrimaryKey(Integer wlno);
	public List<WishListVO> getAll();
	
	public List<WishListVO> getAll(Map<String, String[]> map);
}
