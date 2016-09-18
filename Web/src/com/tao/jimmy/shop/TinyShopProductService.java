package com.tao.jimmy.shop;

import java.util.Set;

public class TinyShopProductService {
	private TinyShopProductDAO dao;

	public TinyShopProductService() {
		dao = new TinyShopProductDAOImpl();
	}

	public Set<TinyShopProductVO> findByShopNo(Integer shopno) {

		return dao.findByShopNo(shopno);
	}

}
