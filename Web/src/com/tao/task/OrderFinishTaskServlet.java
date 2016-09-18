package com.tao.task;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Timer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class OrderFinishTaskServlet
 */
public class OrderFinishTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int executeRate = 60;
	private int firstExecuteMinute = 15;
	private Timer timer;
	private boolean active = false;
	@Override
	public void destroy() {
		super.destroy();
		if (active) {
			timer.cancel();
			System.out
					.println("OrderFinishTaskServlet: context destroyed, cancel OrderFinishTask.");
		}
		
	}
	@Override
	public void init() throws ServletException {
		super.init();
		ServletContext context = getServletContext();
		String enable = getInitParameter("EnableOrderFinishTask");
		if (!"true".equals(enable)) {
			return;
		}

		active = true;
		timer = new Timer();
		String executeRateStr = getInitParameter("OFTExecutionPeroid");
		if (executeRateStr != null && executeRateStr.matches("\\d+")) {
			this.executeRate = Integer.parseInt(executeRateStr);
		}

		String firstExeTime = getInitParameter("OFTFirstExeTime");
		if (firstExeTime != null && firstExeTime.matches("\\d+")) {
			int firstExecuteMinute = Integer.parseInt(firstExeTime);
			if (firstExecuteMinute >= 0 && firstExecuteMinute <= 60) {
				this.firstExecuteMinute = firstExecuteMinute;
			}
		}

		OrderFinishTask task = new OrderFinishTask();
		context.setAttribute("OrderFinishTask", task);
		Calendar c = Calendar.getInstance();
		GregorianCalendar gc = new GregorianCalendar(c.get(Calendar.YEAR),
				c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH),
				c.get(Calendar.HOUR_OF_DAY), firstExecuteMinute);
		timer.scheduleAtFixedRate(task, gc.getTime(), executeRate * 1000 * 60);
		System.out
				.println("OrderFinishTaskServlet: context started, schedule OrderFinishTask at fixed rate: "
						+ executeRate + " minutes");
		
	}   
    
  
	

}
