package com.tao.category.model;

import java.util.List;

public interface CategoryDAO_interface {

	public void insert(CategoryVO categoryVO);

	public void update(CategoryVO categoryVO);

	public void delete(Integer catno);
	
	public CategoryVO getOneCategoryVOByCateNo(Integer catno);
	
	public CategoryVO getOneCategoryVOByCatName(String catname);
	
	public List<CategoryVO> getAll();
}
