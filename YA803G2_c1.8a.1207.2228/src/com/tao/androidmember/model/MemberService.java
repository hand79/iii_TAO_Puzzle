package com.tao.androidmember.model;

import java.util.List;

public class MemberService {
	private MemberInterface dao;
	
	public MemberService(){
		dao = new MemberDAO();
	}
	
	public MemberVO addMember(String memid, String mempw, String fname, 
	String lname, String idnum, String gender, Integer locno, String addr, String tel, 
	String email, byte[] photo, String mime, Integer point, Integer money, Integer addays, 
	Integer status, Integer type, Integer withhold){
		
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
		memberVO.setMime(mime);
		memberVO.setPoint(point);
		memberVO.setMoney(money);
		memberVO.setAddays(addays);
		memberVO.setStatus(status);
		memberVO.setType(type);
		memberVO.setWithhold(withhold);
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
	
	public int updatePic(Integer memno, byte[] photo , String mime){
		return dao.updatePic(memno, photo, mime);
	}

}
