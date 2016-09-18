package com.tao.member.model;

import java.util.List;
import java.util.Map;

public interface MemberDAO_interface {
	public int insert(MemberVO memberVO);
	public int update(MemberVO memberVO);
	public int updateBack(MemberVO memberVO);
	public int updateFront(MemberVO memberVO);	
	public int updateFrontWithPhoto(MemberVO memberVO);
	public int delete(Integer memno);
	public MemberVO findByPrimaryKey(Integer memno);
	public MemberVO findByPrimaryKeyNoPic(Integer memno);
	public MemberVO findByPrimaryKeyWithPic(Integer memno);
	public MemberVO findByMemberID(String memid);
	public List<MemberVO> getAll();
	public List<MemberVO> getAll(Map<String, String[]> map);
	
	public int updatePic(Integer memno, byte[] photo, String mime);
	
	public boolean idExistChecker(String memid);
	
	public List<String> getAllID();
	public List<MemberVO> getAllPendingShopMember();
}
