package com.tao.jimmy.casesupport;

public interface WishListDAO {
	Integer insert(WishListVO vo);
	boolean hasWish(Integer memno, Integer caseno);
}
