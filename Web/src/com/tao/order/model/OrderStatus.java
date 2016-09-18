package com.tao.order.model;

public class OrderStatus {
	public static String getStatusName(int status) {
		String str = null;
		switch (status) {
		case OrderDAO.STATUS_ACHIEVED:
			str = "�w����";
			break;
		case OrderDAO.STATUS_BUYER_COMFIRMED:
			str = "�έ��w�T�{";
			break;
		case OrderDAO.STATUS_CREATOR_COMFIRMED:
			str = "�D�ʤw�T�{";
			break;
		case OrderDAO.STATUS_CANCELED:
			str = "�w����";
			break;
		case OrderDAO.STATUS_COMPLETED:
			str = "�w����";
			break;
		case OrderDAO.STATUS_CONFLICT:
			str = "�ȯɳB�z";
			break;
		case OrderDAO.STATUS_CREATED:
			str = "�ݵ���";
			break;
		case OrderDAO.STATUS_BOTH_COMFIRMED:
			str = "�ݼ���";
			break;
		default:
		}
		return str;
	}

	public static int[] getAllStatusValues() {
		return new int[] { 0, 1, 2, 3, 4, 5, 6, 9 };

	}
}
