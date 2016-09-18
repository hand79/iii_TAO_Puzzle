package com.tao.calender.model;

import java.util.List;

public class CalendarService {
	private CalendarDataJoin_interface dao ;

	public CalendarService() {
		super();
		dao = new CalendarDataJoinDAO();
	}
	public  List<CalendarCaseJoinVO> getAllByCaseJoinOrders (Integer memno){
		return dao.getAllByCaseJoinOrders(memno);
	}
	
	public List<CalendarCaseJoinVO> getAllByCaseJoinWishList(Integer memno){
		return dao.getAllByCaseJoinWishList(memno);
	}
	
}
