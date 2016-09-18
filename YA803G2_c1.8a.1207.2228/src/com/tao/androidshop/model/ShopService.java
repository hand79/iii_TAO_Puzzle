package com.tao.androidshop.model;

import java.util.List;

public class ShopService {

	private ShopDAO_interface dao;

	public ShopService() {
		dao = new ShopDAO();
	}

	public ShopVO addShop(Integer memno, String title,
			String shop_desc, byte[] pic, String mime, Integer locno,
			String addr, Double lng, Double lat, String phone, String fax,
			String email, String ship_desc, String other_desc, Integer hits,
			Integer status) {

		ShopVO shopVO = new ShopVO();

		shopVO.setMemno(memno);
		shopVO.setTitle(title);
		shopVO.setShop_desc(shop_desc);
		shopVO.setPic(pic);
		shopVO.setMime(mime);
		shopVO.setLocno(locno);
		shopVO.setAddr(addr);
		shopVO.setLng(lng);
		shopVO.setLat(lat);
		shopVO.setPhone(phone);
		shopVO.setFax(fax);
		shopVO.setEmail(email);
		shopVO.setShip_desc(ship_desc);
		shopVO.setOther_desc(other_desc);
		shopVO.setHits(hits);
		shopVO.setStatus(status);


		dao.insert(shopVO);

		return shopVO;
	}

	public ShopVO updateShop(Integer shopno, Integer memno, String title,
			String shop_desc, byte[] pic, String mime, Integer locno,
			String addr, Double lng, Double lat, String phone, String fax,
			String email, String ship_desc, String other_desc, Integer hits,
			Integer status) {

		ShopVO shopVO = new ShopVO();

		shopVO.setShopno(shopno);
		shopVO.setMemno(memno);
		shopVO.setTitle(title);
		shopVO.setShop_desc(shop_desc);
		shopVO.setPic(pic);
		shopVO.setMime(mime);
		shopVO.setLocno(locno);
		shopVO.setAddr(addr);
		shopVO.setLng(lng);
		shopVO.setLat(lat);
		shopVO.setPhone(phone);
		shopVO.setFax(fax);
		shopVO.setEmail(email);
		shopVO.setShip_desc(ship_desc);
		shopVO.setOther_desc(other_desc);
		shopVO.setHits(hits);
		shopVO.setStatus(status);

		dao.update(shopVO);

		return shopVO;
	}

	public void deleteShop(Integer shopno) {
		dao.delete(shopno);
	}

	public ShopVO getOneShop(Integer shopno) {
		return dao.findByPrimaryKey(shopno);
	}

	public List<ShopVO> getAll() {
		return dao.getAll();
	}
}
