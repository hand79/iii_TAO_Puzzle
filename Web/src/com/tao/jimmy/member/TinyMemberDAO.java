package com.tao.jimmy.member;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import com.tao.order.model.OrderVO;

public interface TinyMemberDAO {
	public TinyMemberVO findByPrimeryKey(Integer memno);
	public Set<TinyMemberVO> findByMemId(String idKeyword);

	public Set<TinyMemberVO> getAll();
	public int increaseWithhold(Integer memno, Integer amount);
	public int decreaseWithhold(Integer memno, Integer amount);
	
	public int changeMoney(Integer memno, Integer amount);
	public void batchDecreaseWithholdByCancelCase(List<OrderVO> list ,Connection con) throws SQLException;
	
	
}
