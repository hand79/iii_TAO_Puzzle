package com.tao.member.model;

import java.util.List;
import java.util.Map;

public class MemberService {
	private MemberDAO_interface dao;
	
	public MemberService(){
		dao = new MemberDAO();
	}
	
	public MemberVO addMember(String memid, String mempw, String fname, 
	String lname, String idnum, String gender, Integer locno, String addr, String tel, 
	String email, byte[] photo, String mime, Integer type){
		
		MemberVO memberVO= new MemberVO();
		
		//memberVO.setMemno(memno);
		memberVO.setMemid(memid);
		memberVO.setMempw(mempw);
		memberVO.setFname(fname);
		memberVO.setLname(lname);
		memberVO.setIdnum(idnum);
		memberVO.setGender(gender);
		memberVO.setLocno(locno);
		memberVO.setAddr(addr);
		memberVO.setTel(tel);
		memberVO.setEmail(email);
		memberVO.setPhoto(photo);
		memberVO.setType(type);
		memberVO.setMime(mime);
		dao.insert(memberVO);
		return memberVO;
	}
	
	public MemberVO updateMember(Integer memno, String memid, String mempw, String fname, 
			String lname, String idnum, String gender, Integer locno, String addr, String tel, 
			String email, String mime, Integer point, Integer money, Integer addays, 
			Integer status, Integer type, Integer withhold){
		
		MemberVO memberVO= new MemberVO();
		
		memberVO.setMemno(memno);
		memberVO.setMemid(memid);
		memberVO.setMempw(mempw);
		memberVO.setFname(fname);
		memberVO.setLname(lname);
		memberVO.setIdnum(idnum);
		memberVO.setGender(gender);
		memberVO.setLocno(locno);
		memberVO.setAddr(addr);
		memberVO.setTel(tel);
		memberVO.setEmail(email);
		memberVO.setMime(mime);
		memberVO.setPoint(point);
		memberVO.setMoney(money);
		memberVO.setAddays(addays);
		memberVO.setStatus(status);
		memberVO.setType(type);
		memberVO.setWithhold(withhold);
		dao.update(memberVO);
		return memberVO;
	}
	
	public MemberVO update_Back_Member(Integer status, Integer memno){
		
		MemberVO memberVO= new MemberVO();
		
		memberVO.setMemno(memno);
		
		memberVO.setStatus(status);
		
		
		dao.updateBack(memberVO);
		return memberVO;
	}
	
	public MemberVO update_Front_Member(String mempw, String fname, String lname, Integer locno, String addr, String tel, 
			String email, Integer memno){
		
		MemberVO memberVO= new MemberVO();
		
		memberVO.setMemno(memno);
		memberVO.setMempw(mempw);
		memberVO.setFname(fname);
		memberVO.setLname(lname);
		memberVO.setLocno(locno);
		memberVO.setAddr(addr);
		memberVO.setTel(tel);
		memberVO.setEmail(email);
		dao.updateFront(memberVO);
		return memberVO;
	}
	
	public MemberVO updateFrontWithPhoto(String mempw, String fname, String lname, Integer locno, String addr, String tel, 
			String email, byte[] photo, String mime,  Integer memno){
		
		MemberVO memberVO= new MemberVO();
		
		memberVO.setMemno(memno);
		memberVO.setMempw(mempw);
		memberVO.setFname(fname);
		memberVO.setLname(lname);
		memberVO.setLocno(locno);
		memberVO.setAddr(addr);
		memberVO.setTel(tel);
		memberVO.setEmail(email);
		memberVO.setPhoto(photo);
		memberVO.setMime(mime);
		dao.updateFrontWithPhoto(memberVO);
		return memberVO;
	}
	
	public int delete(Integer memno){
		return dao.delete(memno);
	}
	
	public MemberVO findByPrimaryKey(Integer memno){
		return dao.findByPrimaryKey(memno);
	}
	
	public MemberVO findByMemberID(String memid){
		return dao.findByMemberID(memid);
	}
	
	public List<MemberVO> getAll(){
		return dao.getAll();		
	}
	
	public int updatePic(Integer memno, byte[] photo, String mime){
		return dao.updatePic(memno, photo, mime);
	}

	public boolean idExistChecker(String memid){
		return dao.idExistChecker(memid);
	}
	
	public List<String> getAllID(){
		return dao.getAllID();
	}
	
	public List<MemberVO> getAll(Map<String, String[]> map){
		return dao.getAll(map);
	}

	public MemberVO findByPrimaryKeyNoPic(Integer memno){
		return dao.findByPrimaryKeyNoPic(memno);
	}
	public MemberVO findByPrimaryKeyWithPic(Integer memno){
		return dao.findByPrimaryKeyWithPic(memno);
	}
	public List<MemberVO> getAllPendingShopMember(){
		return dao.getAllPendingShopMember();
	}
}
