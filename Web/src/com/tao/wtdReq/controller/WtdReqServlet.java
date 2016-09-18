package com.tao.wtdReq.controller;

import java.io.IOException;
import java.sql.Timestamp;
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

import com.tao.dpsOrd.model.DpsOrdService;
import com.tao.dpsOrd.model.DpsOrdVO;
import com.tao.wtdReq.model.*;

@WebServlet("/WtdReqServlet")
public class WtdReqServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if("default".equals(action)){//借過用
			req.getRequestDispatcher("/wtdReq/listAllWtdReq.jsp").forward(req, res);
			return;
		}

		if ("getOne_For_Display".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("wtdreqno");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				Integer wtdreqno = null;
				try {
					wtdreqno = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/wtdReq/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				WtdReqService wtdSvc = new WtdReqService();
				WtdReqVO wtdReqVO = wtdSvc.getOneWtdReq(wtdreqno);
				if (wtdReqVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/wtdReq/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				req.setAttribute("wtdReqVO", wtdReqVO); // 資料庫取出的empVO物件,存入req
				String url = "/wtdReq/listOneWtdReq.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/wtdReq/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				Integer wtdreqno = new Integer(req.getParameter("wtdreqno"));

				WtdReqService wtdSvc = new WtdReqService();
				WtdReqVO wtdReqVO = wtdSvc.getOneWtdReq(wtdreqno);

				req.setAttribute("wtdReqVO", wtdReqVO);
				String url = "/wtdReq/update_wtdReq_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/wtdReq/listAllWtdReq.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) { // 來自update_dpsOrd_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL");

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				Integer wtdreqno = new Integer(req.getParameter("wtdreqno").trim());
				Timestamp wtdreqt = null;
				try{
				wtdreqt = Timestamp.valueOf(req.getParameter("wtdreqt").trim());
				}catch(IllegalArgumentException e){
					errorMsgs.add("請輸入日期!");
				}
				Double wtdamnt = null;
				try {
					wtdamnt = new Double(req.getParameter("wtdamnt").trim());
				} catch (NumberFormatException e) {
					wtdamnt = 0.0;
					errorMsgs.add("請填數字.");
				}

				Integer memno = new Integer(req.getParameter("memno").trim());
				String wtdac = req.getParameter("wtdac").trim();
				String reqsts = req.getParameter("reqsts").trim();

				WtdReqVO wtdReqVO = new WtdReqVO();
				wtdReqVO.setWtdreqno(wtdreqno);
				wtdReqVO.setWtdreqt(wtdreqt);
				wtdReqVO.setWtdamnt(wtdamnt);
				wtdReqVO.setMemno(memno);
				wtdReqVO.setWtdac(wtdac);
				wtdReqVO.setReqsts(reqsts);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("wtdReqVO", wtdReqVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/wtdReq/update_wtdReq_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				WtdReqService wtdSvc = new WtdReqService();
				wtdReqVO = wtdSvc.updateWtdReq(wtdreqno, wtdreqt, memno,
						wtdamnt, wtdac, reqsts);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("wtdReqVO", wtdReqVO);
				
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/wtdReq/update_wtdReq_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) { // 來自addEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				Timestamp wtdreqt = new Timestamp(System.currentTimeMillis());
				Integer memno = new Integer(req.getParameter("memno").trim());
				Double wtdamnt = new Double(req.getParameter("wtdamnt").trim());
				String wtdac = req.getParameter("wtdac").trim();
				String reqsts = req.getParameter("reqsts").trim();

				WtdReqVO wtdReqVO = new WtdReqVO();
				wtdReqVO.setWtdreqt(wtdreqt);
				wtdReqVO.setMemno(memno);
				wtdReqVO.setWtdamnt(wtdamnt);
				wtdReqVO.setWtdac(wtdac);
				wtdReqVO.setReqsts(reqsts);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("wtdReqVO", wtdReqVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/wtdReq/addWtdReq.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				WtdReqService wtdSvc = new WtdReqService();
				wtdReqVO = wtdSvc.addWtdReq(wtdreqt, memno, wtdamnt, wtdac,
						reqsts);
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/wtdReq/listAllWtdReq.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/wtdReq/addWtdReq.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert_F".equals(action)) { // 來自addEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				Timestamp wtdreqt = new Timestamp(System.currentTimeMillis());
				Integer memno = new Integer(req.getParameter("memno").trim());
				Double wtdamnt = new Double(req.getParameter("wtdamnt").trim());
				String wtdac = req.getParameter("wtdac").trim();
				String reqsts = req.getParameter("reqsts").trim();

				WtdReqVO wtdReqVO = new WtdReqVO();
				wtdReqVO.setWtdreqt(wtdreqt);
				wtdReqVO.setMemno(memno);
				wtdReqVO.setWtdamnt(wtdamnt);
				wtdReqVO.setWtdac(wtdac);
				wtdReqVO.setReqsts(reqsts);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("wtdReqVO", wtdReqVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/select_page_wallet.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				WtdReqService wtdSvc = new WtdReqService();
				wtdReqVO = wtdSvc.addWtdReq(wtdreqt, memno, wtdamnt, wtdac,
						reqsts);
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/select_page_wallet.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/select_page_wallet.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL");

			try {
				/*************************** 1.接收請求參數 ***************************************/
				Integer wtdReqno = new Integer(req.getParameter("wtdreqno"));

				/*************************** 2.開始刪除資料 ***************************************/
				WtdReqService wtdSvc = new WtdReqService();
				wtdSvc.deleteWtdReq(wtdReqno);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/wtdReq/listAllWtdReq.jsp");
				failureView.forward(req, res);
			}

		}

		if ("listWtdReq_ByCompositeQuery".equals(action)) { // 來自select_page.jsp的複合查詢請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {

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
				WtdReqService wtdSvc = new WtdReqService();
				List<WtdReqVO> list = wtdSvc.getAll(map);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("listWtdReq_ByCompositeQuery", list); // 資料庫取出的list物件,存入request
				RequestDispatcher successView = req
						.getRequestDispatcher("/wtdReq/listAllWtdReqByMemno.jsp"); // 成功轉交listEmps_ByCompositeQuery.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/select_page_wallet.jsp");
				failureView.forward(req, res);
			}
		}

		if ("listWtdReq_ByCompositeQuery_B".equals(action)) { // 來自select_page.jsp的複合查詢請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {

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
				WtdReqService wtdSvc = new WtdReqService();
				List<WtdReqVO> list = wtdSvc.getAll(map);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("listWtdReq_ByCompositeQuery", list); // 資料庫取出的list物件,存入request
				RequestDispatcher successView = req
						.getRequestDispatcher("/wtdReq/AfterSearch_wtdReq.jsp"); // 成功轉交listEmps_ByCompositeQuery.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/wtdReq/AfterSearch_wtdReq.jsp");
				failureView.forward(req, res);
			}
		}
		
	}

}
