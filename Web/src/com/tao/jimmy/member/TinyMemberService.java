package com.tao.jimmy.member;

import java.util.Set;

public class TinyMemberService {
	private TinyMemberDAO dao;
	public TinyMemberService(){
		dao = new TinyMemberDAOImpl();
	}
	
	public TinyMemberVO getOneByPK(Integer memno){
		return dao.findByPrimeryKey(memno);
	}
	
	public Set<TinyMemberVO> getSetByIdKeyword(String idKeyword){
		return dao.findByMemId(idKeyword);
	}
	public Set<TinyMemberVO> getAll(){
		return dao.getAll();
	}
	
	public int increaseWithhold(Integer memno, Integer amount){
		return dao.increaseWithhold(memno, amount);
	}
	public int decreaseWithhold(Integer memno, Integer amount){
		return dao.decreaseWithhold(memno, amount);
	}
	
	public int changeMoney(Integer memno, Integer amount){
		return dao.changeMoney(memno, amount);
	}
}

