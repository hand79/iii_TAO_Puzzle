package com.tao.order.model;

public class OrderStatus {
	public static String getStatusName(int status) {
		String str = null;
		switch (status) {
		case OrderDAO.STATUS_ACHIEVED:
			str = "已結案";
			break;
		case OrderDAO.STATUS_BUYER_COMFIRMED:
			str = "團員已確認";
			break;
		case OrderDAO.STATUS_CREATOR_COMFIRMED:
			str = "主購已確認";
			break;
		case OrderDAO.STATUS_CANCELED:
			str = "已取消";
			break;
		case OrderDAO.STATUS_COMPLETED:
			str = "已完成";
			break;
		case OrderDAO.STATUS_CONFLICT:
			str = "糾紛處理";
			break;
		case OrderDAO.STATUS_CREATED:
			str = "待結案";
			break;
		case OrderDAO.STATUS_BOTH_COMFIRMED:
			str = "待撥款";
			break;
		default:
		}
		return str;
	}

	public static int[] getAllStatusValues() {
		return new int[] { 0, 1, 2, 3, 4, 5, 6, 9 };

	}
}
