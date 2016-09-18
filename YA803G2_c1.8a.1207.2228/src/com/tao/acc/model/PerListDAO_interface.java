package com.tao.acc.model;

import java.util.List;
import java.util.Set;

public interface PerListDAO_interface {
	
	public void insert(PerListVO perListVO);

	public void delete(PerListVO perListVO);

	public PerListVO getOnePerListVOByPernoACC(Integer perno, String acc);
		
	public Set<PerListVO> getAllOneAccAllPermissionByACC(String acc);

	public Set<PerListVO> getAllOnePernoAllAccByPerno(Integer perno);

	public List<PerListVO> getAll();
}
