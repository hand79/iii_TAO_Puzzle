package iii.ya803g2.login;

import java.util.List;

public interface MemberInterface {
	void insert(MemberVO memberVO);
	void update(MemberVO memberVO);
	void delete(Integer memno);
    MemberVO findByPrimaryKey(Integer memno);
    List<MemberVO> getAll();
}