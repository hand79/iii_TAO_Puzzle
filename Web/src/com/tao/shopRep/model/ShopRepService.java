package com.tao.shopRep.model;

import java.util.List;
import java.util.Map;

import com.tao.caseRep.model.CaseRepVO;

public class ShopRepService {

	private ShopRepDAO_interface dao;

	public ShopRepService() {
		dao = new ShopRepDAO();
	}

	public ShopRepVO addShopRep(Integer shopno, Integer repno, String sreprsn) {

		ShopRepVO shopRepVO = new ShopRepVO();

		shopRepVO.setShopno(shopno);
		shopRepVO.setRepno(repno);
		shopRepVO.setSreprsn(sreprsn);
		dao.insert(shopRepVO);

		return shopRepVO;
	}

	public ShopRepVO updateShopRep(Integer srepno, Integer shopno,
			Integer repno, String sreprsn) {

		ShopRepVO shopRepVO = new ShopRepVO();

		shopRepVO.setSrepno(srepno);
		shopRepVO.setShopno(shopno);
		shopRepVO.setRepno(repno);
		shopRepVO.setSreprsn(sreprsn);
		dao.update(shopRepVO);

		return shopRepVO;

	}

	public void deleteShopRep(Integer srepno) {
		dao.delete(srepno);
	}

	public ShopRepVO getOneShopRep(Integer srepno) {
		return dao.findByPrimaryKey(srepno);
	}

	public List<ShopRepVO> getAll() {
		return dao.getAll();
	}
	
	public List<ShopRepVO> getAll(Map<String,String[]> map){
		return dao.getAll(map);
	
	}
}
