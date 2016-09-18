package com.tao.acc.model;

import java.util.List;
import java.util.Set;

public class PerListService {
	private PerListDAO_interface dao;

	public PerListService() {
		dao = new PerListDAO();
	}

	public PerListVO addAccountPermission(Integer perno, String acc) {
		PerListVO perListVO = new PerListVO();
		perListVO.setPerno(perno);
		perListVO.setAcc(acc);
		dao.insert(perListVO);
		return perListVO;
	}

	public void deleteAccountPermission(Integer perno, String acc) {
		PerListVO perListVO = new PerListVO();
		perListVO.setPerno(perno);
		perListVO.setAcc(acc);
		dao.delete(perListVO);
	}

	public PerListVO getOneAccountOnePermission(Integer perno, String acc) {

		return dao.getOnePerListVOByPernoACC(perno, acc);
	}

	public Set<PerListVO> getAllOneAccountPermissionInfo(String acc) {
		return dao.getAllOneAccAllPermissionByACC(acc);
	}

	public Set<PerListVO> getOnePermissionAllAccountInfo(Integer perno) {
		return dao.getAllOnePernoAllAccByPerno(perno);
	}

	public List<PerListVO> getAll() {
		return dao.getAll();
	}

}
