package com.tao.androidshopproduct.model;

import java.util.List;

public class ShopproductService {

	private Shopproduct_interface dao;

	public ShopproductService() {
		dao = new ShopproductDAO();
	}

	public ShopproductVO addShopproduct(Integer shopno, String name,
			Double unitprice, byte[] pic1, byte[] pic2, byte[] pic3,
			String pmime1, String pmime2, String pmime3, String pro_desc,
			Integer subcatno, String spec1, String spec2, String spec3,
			Integer isrecomm) {

		ShopproductVO Shopproduct = new ShopproductVO();

//		Shopproduct.setSpno(spno);
		Shopproduct.setShopno(shopno);
		Shopproduct.setName(name);
		Shopproduct.setUnitprice(unitprice);
		Shopproduct.setPic1(pic1);
		Shopproduct.setPic2(pic2);
		Shopproduct.setPic3(pic3);
		Shopproduct.setPmime1(pmime1);
		Shopproduct.setPmime2(pmime2);
		Shopproduct.setPmime3(pmime3);
		Shopproduct.setPro_desc(pro_desc);
		Shopproduct.setSubcatno(subcatno);
		Shopproduct.setSpec1(spec1);
		Shopproduct.setSpec2(spec2);
		Shopproduct.setSpec3(spec3);
		Shopproduct.setIsrecomm(isrecomm);		

		dao.insert(Shopproduct);

		return Shopproduct;
	}

	public ShopproductVO updateShopproduct(Integer spno, Integer shopno,
			String name, Double unitprice, byte[] pic1, byte[] pic2,
			byte[] pic3, String pmime1, String pmime2, String pmime3,
			String pro_desc, Integer subcatno, String spec1, String spec2,
			String spec3, Integer isrecomm) {

		ShopproductVO Shopproduct = new ShopproductVO();

		Shopproduct.setSpno(spno);
		Shopproduct.setShopno(shopno);
		Shopproduct.setName(name);
		Shopproduct.setUnitprice(unitprice);
		Shopproduct.setPic1(pic1);
		Shopproduct.setPic2(pic2);
		Shopproduct.setPic3(pic3);
		Shopproduct.setPmime1(pmime1);
		Shopproduct.setPmime2(pmime2);
		Shopproduct.setPmime3(pmime3);
		Shopproduct.setPro_desc(pro_desc);
		Shopproduct.setSubcatno(subcatno);
		Shopproduct.setSpec1(spec1);
		Shopproduct.setSpec2(spec2);
		Shopproduct.setPic3(pic3);
		Shopproduct.setIsrecomm(isrecomm);	

		dao.update(Shopproduct);

		return Shopproduct;
	}

	public void deleteShopproduct(Integer spno) {
		dao.delete(spno);
	}

	public ShopproductVO getOneShopproduct(Integer spno) {
		return dao.findByPrimaryKey(spno);
	}

	public List<ShopproductVO> getAll() {
		return dao.getAll();
	}
}
