package com.tao.jimmy.casesupport;

public class WishListService {
	private WishListDAO dao;

	public WishListService() {
		super();
		dao = new WishListDAOImpl();
	}

	public Integer insert(Integer memno, Integer caseno) {
		WishListVO vo = new WishListVO();
		vo.setMemno(memno);
		vo.setCaseno(caseno);
		return dao.insert(vo);
	}
	
	public boolean hasWish(Integer memno, Integer caseno){
		return dao.hasWish(memno, caseno);
	}
}
