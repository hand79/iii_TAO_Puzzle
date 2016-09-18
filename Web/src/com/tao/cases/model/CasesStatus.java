package com.tao.cases.model;

public class CasesStatus {

	public static String getStatusName(int status) {
		String re = null;
		switch (status) {
		case CasesVO.STATUS_COMPLETED:
			re = "已完成";
			break;
		case CasesVO.STATUS_CREATED:
			re = "已建立";
			break;
		case CasesVO.STATUS_OVER:
			re = "已結束";
			break;
		case CasesVO.STATUS_PRIVATE:
			re = "上架中(隱密)";
			break;
		case CasesVO.STATUS_PUBLIC:
			re = "上架中";
			break;
		case CasesVO.STATUS_DELETED:
			re = "已刪除";
			break;
		case CasesVO.STATUS_CANCELED:
			re = "已取消";
			break;
		}
		return re;
	}
	public static String getDisplayStatusName(int status) {
		String re = null;
		switch (status) {
		case CasesVO.STATUS_COMPLETED:
			re = "已完成";
			break;
		case CasesVO.STATUS_CREATED:
			re = "未上架";
			break;
		case CasesVO.STATUS_OVER:
			re = "已結束";
			break;
		case CasesVO.STATUS_PRIVATE:
			re = "募集中(私密)";
			break;
		case CasesVO.STATUS_PUBLIC:
			re = "募集中";
			break;
		case CasesVO.STATUS_DELETED:
			re = "已刪除";
			break;
		case CasesVO.STATUS_CANCELED:
			re = "已取消";
			break;
		}
		return re;
	}

	public static int[] getAllStatusValues() {
		return new int[] { 0, 1, 2, 3, 4, 5, 6};

	}

}
