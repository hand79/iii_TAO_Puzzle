package com.tao.acc.model;

import java.util.List;

public class PermissionService {
	private PermissionDAO_Interface dao;

	public PermissionService() {
		dao = new PermissionDAO();
	}

	public PermissionVO getPermission(Integer perno) {
		return dao.getPermissionVOByPerno(perno);
	}

	public List<PermissionVO> getAll() {
		return dao.getAll();
	}
}
