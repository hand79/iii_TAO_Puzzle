package com.tao.location.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tao.location.model.LocationService;
import com.tao.location.model.LocationVO;
import com.tao.member.model.MemberService;
import com.tao.member.model.MemberVO;

public class LocationServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
//		String value = req.getParameter("value");
//		res.getWriter().print(action);
//		res.getWriter().print(value);
		
		if ("gettown".equals(action)) { // 來自dropdown list on county change的請求
			res.getWriter().print("in 1234");
			
			try {
				/***************************1.接收請求參數****************************************/
				String countyRange = req.getParameter("countyRange");
				
				/***************************2.開始查詢資料****************************************/
				LocationService locSvc = new LocationService();
				List<String[]> townsList = locSvc.getMatchedTowns(countyRange);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("townsList", townsList);         // 資料庫取出的townsList物件,存入req
				String url = "/member/front/signupAlexV5.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交signupAlexV5.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/member/front/signupAlexV5.jsp");
				failureView.forward(req, res);
			}
		} // end of getTownAjax statement
		
	}

}
