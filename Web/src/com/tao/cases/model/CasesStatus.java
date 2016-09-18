package com.tao.cases.model;

public class CasesStatus {

	public static String getStatusName(int status) {
		String re = null;
		switch (status) {
		case CasesVO.STATUS_COMPLETED:
			re = "�w����";
			break;
		case CasesVO.STATUS_CREATED:
			re = "�w�إ�";
			break;
		case CasesVO.STATUS_OVER:
			re = "�w����";
			break;
		case CasesVO.STATUS_PRIVATE:
			re = "�W�[��(���K)";
			break;
		case CasesVO.STATUS_PUBLIC:
			re = "�W�[��";
			break;
		case CasesVO.STATUS_DELETED:
			re = "�w�R��";
			break;
		case CasesVO.STATUS_CANCELED:
			re = "�w����";
			break;
		}
		return re;
	}
	public static String getDisplayStatusName(int status) {
		String re = null;
		switch (status) {
		case CasesVO.STATUS_COMPLETED:
			re = "�w����";
			break;
		case CasesVO.STATUS_CREATED:
			re = "���W�[";
			break;
		case CasesVO.STATUS_OVER:
			re = "�w����";
			break;
		case CasesVO.STATUS_PRIVATE:
			re = "�Ҷ���(�p�K)";
			break;
		case CasesVO.STATUS_PUBLIC:
			re = "�Ҷ���";
			break;
		case CasesVO.STATUS_DELETED:
			re = "�w�R��";
			break;
		case CasesVO.STATUS_CANCELED:
			re = "�w����";
			break;
		}
		return re;
	}

	public static int[] getAllStatusValues() {
		return new int[] { 0, 1, 2, 3, 4, 5, 6};

	}

}
