package com.tao.wishList.model;

import java.util.*;

public class WishListService {

	private WishListDAO_interface dao;

	public WishListService() {
		dao = new WishListDAO();
	}

	public WishListVO addWishList(Integer memno, Integer caseno) {

		WishListVO wishListVO = new WishListVO();

		wishListVO.setMemno(memno);
		wishListVO.setCaseno(caseno);
		dao.insert(wishListVO);

		return wishListVO;
	}

	public WishListVO updateWishList(Integer wlno, Integer memno, Integer caseno) {

		WishListVO wishListVO = new WishListVO();

		wishListVO.setWlno(wlno);
		wishListVO.setMemno(memno);
		wishListVO.setCaseno(caseno);
		dao.update(wishListVO);

		return wishListVO;

	}

	public void deleteWishList(Integer wlno) {
		dao.delete(wlno);
	}

	public WishListVO getOneWishList(Integer wlno) {
		return dao.findByPrimaryKey(wlno);
	}

	public List<WishListVO> getAll() {
		return dao.getAll();
	}
	
	public List<WishListVO> getAll(Map<String, String[]> map) {
		return dao.getAll(map);
	}
	
}
