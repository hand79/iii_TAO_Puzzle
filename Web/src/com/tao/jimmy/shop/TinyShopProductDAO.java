package com.tao.jimmy.shop;

import java.util.Set;

public interface TinyShopProductDAO {
	Set<TinyShopProductVO> findByShopNo(Integer shopno);
}
