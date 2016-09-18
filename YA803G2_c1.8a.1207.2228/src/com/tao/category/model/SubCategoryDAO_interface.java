package com.tao.category.model;


import java.util.Set;

public interface SubCategoryDAO_interface {

	public void insert(SubCategoryVO subCategoryVO);

	public void update(SubCategoryVO subCategoryVO);

	public void delete(Integer subcatno);
	
	public SubCategoryVO getOneSubCategoryVOBySubCateNo(Integer subcatno);
	
	public SubCategoryVO getOneSubCategoryVOBySubCateName(String subcatname);
	
	public Set<SubCategoryVO> getAllSubCategoryByCatno(Integer catno);

	public Set<SubCategoryVO> getAll();
}
