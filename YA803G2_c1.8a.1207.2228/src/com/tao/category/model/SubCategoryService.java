package com.tao.category.model;


import java.util.Set;

public class SubCategoryService {
	private SubCategoryDAO_interface dao;

	public SubCategoryService() {
		dao = new SubCategoryDAO();
	}

	public SubCategoryVO addSubCategory(String subcatname,Integer catno) {
		SubCategoryVO subCategoryVO = new SubCategoryVO();
		SubCategoryVO subCategoryVO2 = new SubCategoryVO();
		subCategoryVO.setSubcatname(subcatname);
		subCategoryVO.setCatno(catno);
		dao.insert(subCategoryVO);
		subCategoryVO2=	dao.getOneSubCategoryVOBySubCateName(subcatname);
		subCategoryVO.setSubcatno(subCategoryVO2.getCatno());
		return subCategoryVO;
	}

	public SubCategoryVO updateSubCategory(Integer subcatno,String subcatname,Integer catno) {
		SubCategoryVO subCategoryVO = new SubCategoryVO();
		subCategoryVO.setSubcatno(subcatno);
		subCategoryVO.setSubcatname(subcatname);
		subCategoryVO.setCatno(catno);
		dao.update(subCategoryVO);
		return subCategoryVO;
	}

	public void deleteSubCategory(Integer subcatno) {
		dao.delete(subcatno);
	}
	
	public SubCategoryVO getOneSubCategory(Integer subcatno)
	{
		return dao.getOneSubCategoryVOBySubCateNo(subcatno);
	}
	public SubCategoryVO getOneSubCategory(String subcatname)
	{
		return dao.getOneSubCategoryVOBySubCateName(subcatname);
	}
	
	public Set<SubCategoryVO> getAllByCategory(Integer catno){
		return dao.getAllSubCategoryByCatno(catno);
	}

	
	public Set<SubCategoryVO> getAll() {
		return dao.getAll();
	}
}
