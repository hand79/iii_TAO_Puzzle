package com.tao.category.model;

import java.util.List;

public class CategoryService {
	private CategoryDAO_interface dao;

	public CategoryService() {
		dao = new CategoryDAO();
	}

	public CategoryVO addCategory(String catname) {
		CategoryVO categoryVO = new CategoryVO();
		CategoryVO categoryVO2 = new CategoryVO();
		categoryVO.setCatname(catname);
		dao.insert(categoryVO);
		categoryVO2=dao.getOneCategoryVOByCatName(catname);
		categoryVO.setCatno(categoryVO2.getCatno());
		return categoryVO;
	}

	public CategoryVO updateCategory(Integer catno, String catname) {
		CategoryVO categoryVO = new CategoryVO();
		categoryVO.setCatno(catno);
		categoryVO.setCatname(catname);
		dao.insert(categoryVO);
		return categoryVO;
	}

	public void deleteCategory(Integer catno) {
		dao.delete(catno);
	}

	public CategoryVO getOneCategory(Integer catno) {

		return dao.getOneCategoryVOByCateNo(catno);
	}
	
	public CategoryVO getOneCategory(String catname) {

		return dao.getOneCategoryVOByCatName(catname);
	}
	
	public List<CategoryVO> getAll() {
		return dao.getAll();
	}
}
