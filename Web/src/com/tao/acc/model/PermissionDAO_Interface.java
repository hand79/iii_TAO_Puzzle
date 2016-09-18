package com.tao.acc.model;

import java.util.List;

public interface PermissionDAO_Interface {
	
	public PermissionVO getPermissionVOByPerno(Integer perno);

	public List<PermissionVO> getAll();
}
