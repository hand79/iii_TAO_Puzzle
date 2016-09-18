package com.tao.calender.model;

import java.util.List;

public interface CalendarDataJoin_interface {
	public List<CalendarCaseJoinVO> getAllByCaseJoinWishList(Integer memno );
	public List<CalendarCaseJoinVO> getAllByCaseJoinOrders(Integer memno );
}
