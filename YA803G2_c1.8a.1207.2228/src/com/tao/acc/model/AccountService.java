package com.tao.acc.model;

import java.util.List;

public class AccountService {
	private AccountDAO_interface dao;

	public AccountService() {
		dao = new AccountDAO();
	}

	public AccountVO addAcount(String acc,String accpw, String nick, String email) {
		AccountVO accountVO = new AccountVO();
		accountVO.setAcc(acc);
		accountVO.setNick(nick);
		accountVO.setEmail(email);
		accountVO.setAccpw(accpw);
		dao.insert(accountVO);
		return accountVO;
	}

	public AccountVO updateAcount(String acc, String nick, String email) {
		AccountVO accountVO = new AccountVO();
		accountVO.setAcc(acc);
		accountVO.setNick(nick);
		accountVO.setEmail(email);
		dao.update(accountVO);
		return accountVO;
	}
	public AccountVO updateByPw(AccountVO accountVO) {
		
		dao.updateByPw(accountVO);
		return accountVO;
	}
	public AccountVO updateByEmail(AccountVO accountVO) {
		
		dao.updateByEmail(accountVO);
		return accountVO;
	}
	
	public void deleteAccount(String acc) {
		dao.delete(acc);
	}

	public AccountVO getOneAccount(String acc) {
		return dao.getOneAccountVOByACC(acc);
	}

	public List<AccountVO> getAll() {
		return dao.getAll();
	}
	
}
