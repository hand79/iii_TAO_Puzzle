package com.tao.acc.model;

import java.util.List;

public interface AccountDAO_interface {
	
	public void insert(AccountVO accountVO);

	public void update(AccountVO accountVO);

	public void delete(String acc);

	public AccountVO getOneAccountVOByACC(String acc);
	
	public void updateByPw(AccountVO accountVO);
	
	public void updateByEmail(AccountVO accountVO);
	public List<AccountVO> getAll();
}
