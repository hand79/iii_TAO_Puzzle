package com.tao.task;

import java.sql.Timestamp;
import java.util.Set;
import java.util.TimerTask;

import com.tao.cases.model.CasesService;
import com.tao.cases.model.CasesVO;
import com.tao.order.model.OrderService;

public class CaseOverTask extends TimerTask {
	@Override
	public void run() {
		Timestamp date = new Timestamp(super.scheduledExecutionTime());
		Timestamp now = new Timestamp(System.currentTimeMillis());
		System.out.println("CaseOverTask: execute task scheduled at " + date
				+ ", execution start time is " + now);
		doCaseOverTask(now);
		System.out.println("CaseOverTask: execute task scheduled at " + date
				+ ", execution end time is " + now);
	}
	
	public void doTaskNow(){
		Timestamp now = new Timestamp(System.currentTimeMillis());
		System.out.println("CaseOverTask: execute task manually triggered by user, execution start time is " + now);
		doCaseOverTask(now);
		System.out.println("CaseOverTask: execute task manually triggered by user, execution end time is " + now);
	}
	
	
	
	private void doCaseOverTask(Timestamp now) {
		CasesService cSvc = new CasesService();
		OrderService oSvc = new OrderService();
		Set<CasesVO> cSet = cSvc.getByStatuses(CasesVO.STATUS_PRIVATE,
				CasesVO.STATUS_PUBLIC);
		for (CasesVO cvo : cSet) {
			if (cvo.getEtime().getTime() <= now.getTime()) {
				if (oSvc.getTotalOrderQty(cvo.getCaseno()) >= cvo.getMinqty()) {
					boolean flag = cSvc.overCase(cvo.getCaseno());
					System.out.println("CaseOverTask: over case "
							+ cvo.getCaseno() + ", success=" + flag);
				} else {
					boolean flag = cSvc.cancelCase(cvo.getCaseno());
					System.out.println("CaseOverTask: cancel case "
							+ cvo.getCaseno() + ", success=" + flag);
				}
			}
		}
		
	}
}