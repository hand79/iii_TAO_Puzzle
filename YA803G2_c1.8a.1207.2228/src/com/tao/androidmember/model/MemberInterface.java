package com.tao.androidmember.model;

import java.util.List;

public interface MemberInterface {
    public int insert(MemberVO memberVO);
    public int update(MemberVO memberVO);
    public int delete(Integer memno);
    public MemberVO findByPrimaryKey(Integer memno);
    public MemberVO findByMemberID(String memid);
    public List<MemberVO> getAll();
    public int updatePic(Integer memno, byte[] photo , String mime);
}