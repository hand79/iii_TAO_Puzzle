package com.tao.task;

import java.sql.Timestamp;
import java.util.List;
import java.util.TimerTask;

import com.tao.order.model.OrderDAO;
import com.tao.order.model.OrderService;
import com.tao.order.model.OrderVO;

public class OrderFinishTask extends TimerTask {

	@Override
	public void run() {
		Timestamp date = new Timestamp(super.scheduledExecutionTime());
		Timestamp now = new Timestamp(System.currentTimeMillis());
		System.out.println("OrderFinishTask: execute task scheduled at "
				+ date + ", execution start time is " + now);
		doOrderProcessTask(now);
		System.out.println("OrderFinishTask: execute task scheduled at "
				+ date + ", execution end time is "
				+ new Timestamp(System.currentTimeMillis()));
	}

	public void doTaskNow() {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		System.out
				.println("OrderFinishTask: execute task manually triggered by user, execution start time is "
						+ now);
		doOrderProcessTask(now);
		System.out
				.println("OrderFinishTask: execute task manually triggered by user, execution end time is "
						+ new Timestamp(System.currentTimeMillis()));
	}

	private void doOrderProcessTask(Timestamp now) {
		OrderService oSvc = new OrderService();
		List<OrderVO> candidateList = oSvc
				.findByStatus(OrderDAO.STATUS_BOTH_COMFIRMED);
		int finishCount = oSvc.finishOrder(candidateList);
		if (finishCount != candidateList.size()) {
			System.out
					.println("OrderFinishTask: **ERROR** : some subjected order does not be finished successfully.");
		}
		System.out.println("OrderFinishTask: total update count is "
				+ finishCount);
	}
}