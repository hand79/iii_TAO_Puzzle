package com.tao.caseRep.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tao.cases.model.CasesService;


public class CancelCaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if("CancelCase_from_back".equals(action)){

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL");
			
			try{
				
				Integer caseno =null;
				try{
					caseno = new Integer (req.getParameter("repcaseno").trim());
					}catch(NullPointerException e){
						errorMsgs.add("取不到repcaseno");
					}
				
				CasesService casSvc = new CasesService();
				Boolean isCancel = casSvc.cancelCase(caseno);
				
				if(isCancel == false){
					errorMsgs.add("此合購案不可下架");
					RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
					failureView.forward(req, res);
					return;
				}
				
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			}catch(Exception e){
				errorMsgs.add("下架失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/caseRep/listAllCaseRep.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
