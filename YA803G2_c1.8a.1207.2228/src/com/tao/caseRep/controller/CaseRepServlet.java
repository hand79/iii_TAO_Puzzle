package com.tao.caseRep.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tao.caseRep.model.CaseRepService;
import com.tao.caseRep.model.CaseRepVO;
import com.tao.shopRep.model.ShopRepService;
import com.tao.shopRep.model.ShopRepVO;

@WebServlet("/CaseRepServlet")
public class CaseRepServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		if("default".equals(action)){//後端借過用
			req.getRequestDispatcher("/caseRep/listAllCaseRep.jsp").forward(req, res);
		}
		if ("getOne_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String str = req.getParameter("crepno");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入編號");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/select_page_report.jsp");
					failureView.forward(req, res);
					return;
				}
				Integer crepno = null;
				try {
					crepno = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("編號格式不正確");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/select_page_report.jsp");
					failureView.forward(req, res);
					return;
				}
				CaseRepService caseSvc = new CaseRepService();
				CaseRepVO caseRepVO = caseSvc.getOneCaseRep(crepno);
				if (caseRepVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/select_page_report.jsp");
					failureView.forward(req, res);
					return;
				}
				req.setAttribute("caseRepVO", caseRepVO);
				String url = "/caseRep/listOneCaseRep.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/select_page_report.jsp");
				failureView.forward(req, res);
			}
		}
		if ("getOne_For_Update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL");

			try {
				Integer crepno = new Integer(req.getParameter("crepno"));

				CaseRepService caseSvc = new CaseRepService();
				CaseRepVO caseRepVO = caseSvc.getOneCaseRep(crepno);

				req.setAttribute("caseRepVO", caseRepVO);
				String url = "/caseRep/update_caseRep_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL");

			try {
				Integer crepno = new Integer(req.getParameter("crepno"));
				Integer repno = new Integer(req.getParameter("repno"));
				Integer susno = new Integer(req.getParameter("susno"));
				Integer repcaseno = new Integer(req.getParameter("repcaseno"));
				String creprsn = req.getParameter("creprsn");

				CaseRepVO caseRepVO = new CaseRepVO();

				caseRepVO.setCrepno(crepno);
				caseRepVO.setRepno(repno);
				caseRepVO.setSusno(susno);
				caseRepVO.setRepcaseno(repcaseno);
				caseRepVO.setCreprsn(creprsn);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("caseRepVO", caseRepVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/caseRep/update_caseRep_input.jsp");
					failureView.forward(req, res);
					return;
				}
				CaseRepService caseSvc = new CaseRepService();
				caseRepVO = caseSvc.updateCaseRep(crepno, repno, susno,
						repcaseno, creprsn);

				req.setAttribute("caseRepVO", caseRepVO);
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/caseRep/update_caseRep_input.jsp");
				failureView.forward(req, res);
			}
		}
		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				Integer repno = new Integer(req.getParameter("repno"));
				Integer susno = new Integer(req.getParameter("susno"));
				Integer repcaseno = new Integer(req.getParameter("repcaseno"));
				String creprsn = req.getParameter("creprsn");

				CaseRepVO caseRepVO = new CaseRepVO();

				caseRepVO.setRepno(repno);
				caseRepVO.setSusno(susno);
				caseRepVO.setRepcaseno(repcaseno);
				caseRepVO.setCreprsn(creprsn);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("caseRepVO", caseRepVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/caseRep/addCaseRep.jsp");
					failureView.forward(req, res);
					return;
				}

				CaseRepService caseSvc = new CaseRepService();
				caseRepVO = caseSvc
						.addCaseRep(repno, susno, repcaseno, creprsn);

				String url = "/caseRep/listAllCaseRep.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/caseRep/addCaseRep.jsp");
				failureView.forward(req, res);
			}
		}
		if ("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL");

			try {
				Integer crepno = new Integer(req.getParameter("crepno"));

				CaseRepService caseSvc = new CaseRepService();
				caseSvc.deleteCaseRep(crepno);

				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/caseRep/listAllCaseRep.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("listCaseRep_ByCompositeQuery".equals(action)){
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try{
				
				/*************************** 1.將輸入資料轉為Map **********************************/
				// 採用Map<String,String[]> getParameterMap()的方法
				// 注意:an immutable java.util.Map
				// Map<String, String[]> map = req.getParameterMap();
				HttpSession session = req.getSession();
				Map<String, String[]> map = (Map<String, String[]>) session
						.getAttribute("map");
				if (req.getParameter("whichPage") == null) {
					HashMap<String, String[]> map1 = (HashMap<String, String[]>) req
							.getParameterMap();
					HashMap<String, String[]> map2 = new HashMap<String, String[]>();
					map2 = (HashMap<String, String[]>) map1.clone();
					session.setAttribute("map", map2);
					map = (HashMap<String, String[]>) req.getParameterMap();
				}

				/*************************** 2.開始複合查詢 ***************************************/
				CaseRepService crepSvc = new CaseRepService();
				List<CaseRepVO> list = crepSvc.getAll(map);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("listCaseRep_ByCompositeQuery", list); // 資料庫取出的list物件,存入request
				RequestDispatcher successView = req
						.getRequestDispatcher("/caseRep/AfterSearch_caseRep.jsp"); // 成功轉交listEmps_ByCompositeQuery.jsp
				successView.forward(req, res);
				
			}catch(Exception e){
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/caseRep/AfterSearch_caseRep.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
