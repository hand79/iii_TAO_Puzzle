package com.tao.task;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.*;

import com.tao.runningad.model.RunningAdService;
import com.tao.runningad.model.RunningAdVO;

import java.sql.Timestamp;
import java.util.*;

public class RunningAdsScheduleServlet extends HttpServlet {
	// private static DataSource ds = DataSourceHolder.getDataSource();

	Timer timer;
	int i = 0;
	Calendar cal;

	public void init() throws ServletException {
		cal = new GregorianCalendar();
		TimerTask task = new TimerTask() {
			RunningAdService ras = new RunningAdService();

			@Override
			public void run() {
				System.out.println("RunningAdsScheduleServlet: Execute Task secheduled at " + new Timestamp(scheduledExecutionTime()) + ", execution time is " + new Timestamp(System.currentTimeMillis()));
				List<RunningAdVO> list = ras.getAll();
				for (RunningAdVO raVO : list) {
					Date edate = raVO.getEdate();
					Date now = ras.getSysDate();

					if (edate != null && raVO.getTst() == 1) {
						if (!edate.after(now)) {
							ras.RemoveExpiredAds(raVO.getAdno());
							System.out
									.println("Remove Ad no." + raVO.getAdno());
						}
					}
				}
			}
		};
		timer = new Timer();
		timer.scheduleAtFixedRate(task, cal.getTime(), 1 * 60 * 60 * 1000);
		System.out.println("RunningAdsScheduleServlet: 已建立排程!");
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
	}

	public void destroy() {
		timer.cancel();
		System.out.println("RunningAdsScheduleServlet: 已移除排程!");
	}

}