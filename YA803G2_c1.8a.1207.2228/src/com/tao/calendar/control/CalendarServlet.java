package com.tao.calendar.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.jasper.tagplugins.jstl.core.ForEach;

import com.google.gson.Gson;
import com.tao.calender.model.CalendarCaseJoinVO;
import com.tao.calender.model.CalendarDataJoinDAO;
import com.tao.calender.model.CalendarService;
import com.tao.calender.model.CalendarVO;
import com.tao.member.model.MemberService;
import com.tao.member.model.MemberVO;


public class CalendarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");
		HttpSession session=req.getSession();

		MemberVO memberVO=(MemberVO)session.getAttribute("CurrentUser");
	//	MemberVO memberVO=new MemberVO();
//		memberVO.setMemno(1001);
		
		if(memberVO==null){
			return;
		}
		
		List<CalendarVO>calendarVOs=new ArrayList<CalendarVO>();
		CalendarService calendarSvc =new CalendarService();
		List<CalendarCaseJoinVO> joinOrder=calendarSvc.getAllByCaseJoinOrders(memberVO.getMemno());
		List<CalendarCaseJoinVO> joinWishlist=calendarSvc.getAllByCaseJoinWishList(memberVO.getMemno());
		
		/****************************¸ê®Æ¶Ç°e*****************************************/	
		if(joinOrder.size()>0){
			for (CalendarCaseJoinVO calendarCaseJoinVO : joinOrder) {
				CalendarVO calendarVO=new CalendarVO();
				calendarVO.setStart(calendarCaseJoinVO.getEtime());
				calendarVO.setTitle(calendarCaseJoinVO.getTitle());
				calendarVO.setUrl(req.getContextPath()+"/cases/cases.do?action=caseDetail&caseno="+calendarCaseJoinVO.getCaseno());
				calendarVOs.add(calendarVO);
			}
		}
		
		if(joinWishlist.size()>0){
			for (CalendarCaseJoinVO calendarCaseJoinVO : joinWishlist) {
				CalendarVO calendarVO=new CalendarVO();
				calendarVO.setStart(calendarCaseJoinVO.getEtime());
				calendarVO.setTitle(calendarCaseJoinVO.getTitle());
				calendarVO.setUrl(req.getContextPath()+"/cases/cases.do?action=caseDetail&caseno="+calendarCaseJoinVO.getCaseno());
				calendarVOs.add(calendarVO);
			}
		}
		 res.setContentType("application/json");
		 res.setCharacterEncoding("UTF-8");
		 PrintWriter out = res.getWriter();
		 out.write(new Gson().toJson(calendarVOs));
		
	}

}
