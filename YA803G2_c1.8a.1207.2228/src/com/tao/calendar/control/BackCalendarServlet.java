package com.tao.calendar.control;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.LinkedHashSet;

import java.util.Set;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.jasper.tagplugins.jstl.core.ForEach;

import com.google.gson.Gson;
import com.tao.calender.model.CalendarCaseJoinVO;
import com.tao.calender.model.CalendarDataJoinDAO;
import com.tao.calender.model.CalendarService;
import com.tao.calender.model.CalendarVO;
import com.tao.cases.model.CasesService;
import com.tao.cases.model.CasesVO;
import com.tao.jimmy.util.TimestampFormater;
import com.tao.member.model.MemberService;
import com.tao.member.model.MemberVO;


public class BackCalendarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if("default".equals(request.getParameter("action"))){
			request.getRequestDispatcher("/calendar/backcalendar.jsp").forward(request, response);
			return;
		}
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		CasesService casesSvc =new CasesService();
		Set<CalendarVO> vos= new LinkedHashSet<CalendarVO>();
		Integer[] statuses={1,2};
		Set<CasesVO> cases = casesSvc.getByStatuses(statuses);
		
		for (CasesVO casesVO : cases) {
			CalendarVO calendarVO=new CalendarVO();
			calendarVO.setStart(TimestampFormater.format(casesVO.getEtime()));
			calendarVO.setTitle(casesVO.getShortenedTitle());
			calendarVO.setUrl(req.getContextPath()+"/cases/cases.do?action=caseDetail&caseno="+casesVO.getCaseno());
			vos.add(calendarVO);
		}
		
		 res.setContentType("application/json");
		 res.setCharacterEncoding("UTF-8");
		 PrintWriter out = res.getWriter();
		 out.write(new Gson().toJson(vos));
		
	}

}
