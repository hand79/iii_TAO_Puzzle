package com.tao.cases.model;

import java.util.List;

public class CaseProductService {
	private CaseProductDAO dao;

	public CaseProductService() {
		dao = new CaseProductDAOImpl();
	}

	public Integer addCaseProduct(Integer memno, String name,
			Integer unitprice, byte[] pic1, byte[] pic2, byte[] pic3,
			String pmime1, String pmime2, String pmime3, String cpdesc,
			Integer subcatno) {
		CaseProductVO vo = new CaseProductVO();

		vo.setMemno(memno);
		vo.setName(name);
		vo.setUnitprice(unitprice);
		vo.setPic1(pic1);
		vo.setPic2(pic2);
		vo.setPic3(pic3);
		vo.setPmime1(pmime1);
		vo.setPmime2(pmime2);
		vo.setPmime3(pmime3);
		vo.setCpdesc(cpdesc);
		vo.setSubcatno(subcatno);

		Integer genKey = dao.insert(vo);
		return genKey;
	};

	// public int update(Integer cpno, Integer memno, String name, Integer
	// unitprice,
	// byte[] pic1, byte[] pic2, byte[] pic3, String pmime1,
	// String pmime2, String pmime3, String cpdesc, Integer subcatno){
	// CaseProductVO vo = new CaseProductVO();
	//
	// vo.setCpno(cpno);
	// vo.setName(name);
	// vo.setUnitprice(unitprice);
	// vo.setPic1(pic1);
	// vo.setPic2(pic2);
	// vo.setPic3(pic3);
	// vo.setPmime1(pmime1);
	// vo.setPmime2(pmime2);
	// vo.setPmime3(pmime3);
	// vo.setCpdesc(cpdesc);
	// vo.setSubcatno(subcatno);
	//
	// int updateCount = dao.updateByEdit(vo);
	// return updateCount;
	// };

	public int updateCaseProduct(Integer cpno, String name, Integer unitprice,
			byte[] pic1, String pmime1, byte[] pic2, String pmime2,
			byte[] pic3, String pmime3, String cpdesc, Integer subcatno) {

		CaseProductVO vo = new CaseProductVO();

		vo.setCpno(cpno);
		vo.setName(name);
		vo.setUnitprice(unitprice);
		vo.setPic1(pic1);
		vo.setPic2(pic2);
		vo.setPic3(pic3);
		vo.setPmime1(pmime1);
		vo.setPmime2(pmime2);
		vo.setPmime3(pmime3);
		vo.setCpdesc(cpdesc);
		vo.setSubcatno(subcatno);

		int updateCount = dao.updateByEdit(vo);
		return updateCount;
	}

	public int deleteCaseProduct(Integer cpno) {
		int updateCount = dao.delete(cpno);
		return updateCount;
	}
	@Deprecated
	public CaseProductVO getOneByPK(Integer cpno) {
		CaseProductVO vo = dao.findByPrimaryKey(cpno);
		return vo;
	}
	public CaseProductVO getOneByPrimaryKeyHasPic(Integer cpno) {
		CaseProductVO vo = dao.findByPrimaryKey(cpno, true);
		return vo;
	}
	
	public CaseProductVO getOneByPrimaryKeyNoPic(Integer cpno) {
		CaseProductVO vo = dao.findByPrimaryKey(cpno, false);
		return vo;
	}
	@Deprecated
	public CaseProductVO getOneByPK(Integer cpno, boolean hasPic) {
		CaseProductVO vo = dao.findByPrimaryKey(cpno, hasPic);
		return vo;
	}
	public CaseProductVO getOneByPrimaryKey(Integer cpno, boolean hasPic) {
		CaseProductVO vo = dao.findByPrimaryKey(cpno, hasPic);
		return vo;
	}
	
	@Deprecated
	public List<CaseProductVO> getByOwnerNo(Integer memno) {
		List<CaseProductVO> list = dao.findByOwnerNo(memno);
		return list;
	}
	
	public List<CaseProductVO> getByOwnerNo(Integer memno, boolean hasPic) {
		List<CaseProductVO> list = dao.findByOwnerNo(memno, hasPic);
		return list;
	}
	@Deprecated
	public List<CaseProductVO> getAll() {
		List<CaseProductVO> list = dao.getAll();
		return list;
	}
	
	public List<CaseProductVO> getAllHasPic() {
		List<CaseProductVO> list = dao.getAll(true);
		return list;
	}

	public List<CaseProductVO> getAllNoPic() {
		List<CaseProductVO> list = dao.getAll(false);
		return list;
	}

	public List<CaseProductVO> getAll(boolean hasPic) {
		List<CaseProductVO> list = dao.getAll(hasPic);
		return list;
	}

}
