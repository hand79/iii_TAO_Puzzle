package com.tao.task;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Timer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Servlet implementation class CaseOverTaskServlet
 */
public class CaseOverTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int executeRate = 60; // minute
	private Timer timer;
	private int firstExecuteMinute = 5;
	private boolean active = false;
   
    
	@Override
	public void init() throws ServletException {
		super.init();
		ServletContext context = getServletContext();
		String enable = getInitParameter("EnableCaseOverTask");
		if (!"true".equals(enable)) {
			return;
		}
		active = true;
		timer = new Timer();
		String executeRateStr = getInitParameter("COTExecutionPeroid");
		if (executeRateStr != null && executeRateStr.matches("\\d+")) {
			this.executeRate = Integer.parseInt(executeRateStr);
		}
		String firstExeTime = getInitParameter("COTFirstExeTime");
		if (firstExeTime != null && firstExeTime.matches("\\d+")) {
			int firstExecuteMinute = Integer.parseInt(firstExeTime);
			if (firstExecuteMinute >= 0 && firstExecuteMinute <= 60) {
				this.firstExecuteMinute = firstExecuteMinute;
			}
		}

		CaseOverTask task = new CaseOverTask();
		context.setAttribute("CaseOverTask", task);
		Calendar c = Calendar.getInstance();
		GregorianCalendar gc = new GregorianCalendar(c.get(Calendar.YEAR),
				c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH),
				c.get(Calendar.HOUR_OF_DAY), firstExecuteMinute);
		timer.scheduleAtFixedRate(task, gc.getTime(), executeRate * 1000 * 60);
		System.out
				.println("CaseOverTasServlet: context started, schedule CaseOverTask at fixed rate: "
						+ executeRate + " minutes");
		
		
	}


	@Override
	public void destroy() {
		super.destroy();
		if (active) {
			timer.cancel();
			System.out
					.println("CaseOverTasServlet: context destroyed, cancel CaseOverTask.");
		}
		
	}

	

}
