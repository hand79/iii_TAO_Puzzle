package com.tao.dpsOrd.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collections;
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
import com.tao.member.model.MemberVO;

public class DpsOrdServlet extends HttpServlet {
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
			req.getRequestDispatcher("/dpsOrd/listAllDpsOrd.jsp").forward(req, res);
			return;
		}
		
		if ("getOne_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String str = req.getParameter("dpsordno");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/dpsOrd/select_page_dps.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				Integer dpsordno = null;
				try {
					dpsordno = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("編號格式不正確");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/dpsOrd/select_page_dps.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				DpsOrdService dpsSvc = new DpsOrdService();
				DpsOrdVO dpsOrdVO = dpsSvc.getOneDpsOrd(dpsordno);
				if (dpsOrdVO == null) {
					errorMsgs.add("查無資料");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/dpsOrd/select_page_dps.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				req.setAttribute("dpsOrdVO", dpsOrdVO);
				String url = "/dpsOrd/listOneDpsOrd.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/dpsOrd/select_page_dps.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				Integer dpsordno = new Integer(req.getParameter("dpsordno"));

				DpsOrdService dpsSvc = new DpsOrdService();
				DpsOrdVO dpsOrdVO = dpsSvc.getOneDpsOrd(dpsordno);

				req.setAttribute("dpsOrdVO", dpsOrdVO);
				String url = "/dpsOrd/update_dpsOrd_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/dpsOrd/listAllDpsOrd.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL");

			try {
				Integer dpsordno = null;
				try {
					dpsordno = new Integer(req.getParameter("dpsordno")
							.trim());
				} catch (NumberFormatException e) {
					dpsordno = 1;
					errorMsgs.add("請填數字.");
				}
						
				Timestamp dpsordt = null;
				try{
					dpsordt = Timestamp.valueOf(req.getParameter("dpsordt").trim());
				}catch(IllegalArgumentException e){
					dpsordt = new Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入儲值時間");
				}
				
				Double dpsamnt = null;
				try {
					dpsamnt = new Double(req.getParameter("dpsamnt").trim());
				} catch (NumberFormatException e) {
					dpsamnt = 0.0;
					errorMsgs.add("請填數字.");
				}
				Integer memno = null;
				try {
					memno = new Integer(req.getParameter("memno").trim());
				} catch (NumberFormatException e) {
					memno = 1001;
					errorMsgs.add("請填會員編號");
				}
						
				String dpshow = req.getParameter("dpshow").trim();
				if(dpshow == null || dpshow.length() ==0){
					errorMsgs.add("請輸入儲值方式");
				}
				String ordsts = req.getParameter("ordsts").trim();
				String atmac = req.getParameter("atmac").trim();
				
				DpsOrdVO dpsOrdVO = new DpsOrdVO();

				dpsOrdVO.setDpsordno(dpsordno);
				dpsOrdVO.setDpsordt(dpsordt);
				dpsOrdVO.setDpsamnt(dpsamnt);
				dpsOrdVO.setMemno(memno);
				dpsOrdVO.setDpshow(dpshow);
				dpsOrdVO.setOrdsts(ordsts);
				dpsOrdVO.setAtmac(atmac);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("dpsOrdVO", dpsOrdVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/dpsOrd/update_dpsOrd_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				DpsOrdService dpsSvc = new DpsOrdService();
				dpsOrdVO = dpsSvc.updateDpsOrdVO(dpsordno, dpsordt, dpsamnt,
						memno, dpshow, ordsts, atmac);

				req.setAttribute("dpsOrdVO", dpsOrdVO); // 資料庫update成功後,正確的的empVO物件,存入req

				String url = requestURL;				
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/dpsOrd/update_dpsOrd_input.jsp");
				failureView.forward(req, res);
			}
		}
		

		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				Timestamp dpsordt = new Timestamp(System.currentTimeMillis());
				Double dpsamnt =null;
				try {
					dpsamnt = new Double(req.getParameter("dpsamnt").trim());
				} catch (NumberFormatException e) {
					dpsamnt = 1000.0;
					errorMsgs.add("請輸入數字");
				}
				Integer memno = null;
				try {
					memno = new Integer(req.getParameter("memno").trim());
				} catch (NumberFormatException e) {
					memno = 1002;
					errorMsgs.add("請輸入會員編號");
				}
				String dpshow = req.getParameter("dpshow").trim();
				if(dpshow == null || dpshow.length() ==0){
					errorMsgs.add("請選擇儲值方式");
				}
				String ordsts = req.getParameter("ordsts").trim();
				String atmac = req.getParameter("atmac").trim();

				DpsOrdVO dpsOrdVO = new DpsOrdVO();

				dpsOrdVO.setDpsordt(dpsordt);
				dpsOrdVO.setDpsamnt(dpsamnt);
				dpsOrdVO.setMemno(memno);
				dpsOrdVO.setDpshow(dpshow);
				dpsOrdVO.setOrdsts(ordsts);
				dpsOrdVO.setAtmac(atmac);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("dpsOrdVO", dpsOrdVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/dpsOrd/addDpsOrd.jsp");
					failureView.forward(req, res);
					return;
				}

				DpsOrdService dpsSvc = new DpsOrdService();
				dpsOrdVO = dpsSvc.addDpsOrdVO(dpsordt, dpsamnt, memno, dpshow,
						ordsts, atmac);

				String url = "/dpsOrd/listAllDpsOrd.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/dpsOrd/addDpsOrd.jsp");
				failureView.forward(req, res);
			}
			return;

		}		
		
		if ("insert_A".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				Timestamp dpsordt = new Timestamp(System.currentTimeMillis());
				Double dpsamnt =null;
				try {
					dpsamnt = new Double(req.getParameter("dpsamnt").trim());
				} catch (NumberFormatException e) {
					dpsamnt = 1000.0;
					errorMsgs.add("請輸入數字");
				}
				Integer memno = null;
				try {
					memno = new Integer(req.getParameter("memno").trim());
				} catch (NumberFormatException e) {
					memno = 1002;
					errorMsgs.add("請輸入會員編號");
				}
				String dpshow = req.getParameter("dpshow").trim();
				if(dpshow == null || dpshow.length() ==0){
					errorMsgs.add("請選擇儲值方式");
				}
				String ordsts = req.getParameter("ordsts").trim();
				if(ordsts == null || ordsts.length() ==0){
					errorMsgs.add("請輸入儲值訂單狀態");
				}
				String atmac = req.getParameter("atmac").trim();

				DpsOrdVO dpsOrdVO = new DpsOrdVO();

				dpsOrdVO.setDpsordt(dpsordt);
				dpsOrdVO.setDpsamnt(dpsamnt);
				dpsOrdVO.setMemno(memno);
				dpsOrdVO.setDpshow(dpshow);
				dpsOrdVO.setOrdsts(ordsts);
				dpsOrdVO.setAtmac(atmac);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("dpsOrdVO", dpsOrdVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/dpsOrd/insert_dpsOrd_atm.jsp");
					failureView.forward(req, res);
					return;
				}

				DpsOrdService dpsSvc = new DpsOrdService();
				dpsOrdVO = dpsSvc.addDpsOrdVO(dpsordt, dpsamnt, memno, dpshow,
						ordsts, atmac);

				String url = "/select_page_wallet.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);
//				successView.forward(req, res);
				res.sendRedirect(req.getContextPath() + url);

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/dpsOrd/insert_dpsOrd_atm.jsp");
				failureView.forward(req, res);
			}
			return;

		}	
		
		if ("insert_C".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				Timestamp dpsordt = new Timestamp(System.currentTimeMillis());
				Double dpsamnt =null;
				try {
					dpsamnt = new Double(req.getParameter("dpsamnt").trim());
				} catch (NumberFormatException e) {
					dpsamnt = 1000.0;
					errorMsgs.add("請輸入數字");
				}
				Integer memno = null;
				try {
					memno = new Integer(req.getParameter("memno").trim());
				} catch (NumberFormatException e) {
					memno = 1002;
					errorMsgs.add("請輸入會員編號");
				}
				String dpshow = req.getParameter("dpshow").trim();
				if(dpshow == null || dpshow.length() ==0){
					errorMsgs.add("請選擇儲值方式");
				}
				String ordsts = req.getParameter("ordsts").trim();
				if(ordsts == null || ordsts.length() ==0){
					errorMsgs.add("請輸入儲值訂單狀態");
				}
				String atmac = req.getParameter("atmac").trim();
				if(atmac == null || atmac.length() ==0){
					errorMsgs.add("請輸入atm");
				}

				DpsOrdVO dpsOrdVO = new DpsOrdVO();

				dpsOrdVO.setDpsordt(dpsordt);
				dpsOrdVO.setDpsamnt(dpsamnt);
				dpsOrdVO.setMemno(memno);
				dpsOrdVO.setDpshow(dpshow);
				dpsOrdVO.setOrdsts(ordsts);
				dpsOrdVO.setAtmac(atmac);

				if (!errorMsgs.isEmpty()) {
					
					req.setAttribute("dpsOrdVO", dpsOrdVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/dpsOrd/insert_dpsOrd_credit.jsp");
					failureView.forward(req, res);
					return;
				}
				DpsOrdService dpsSvc = new DpsOrdService();
				dpsOrdVO = dpsSvc.addDpsOrdVO(dpsordt, dpsamnt, memno, dpshow,
						ordsts, atmac);

				String url = "/select_page_wallet.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/dpsOrd/insert_dpsOrd_credit.jsp");
				failureView.forward(req, res);
			}
			return;

		}	
			
		if ("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				Integer dpsordno = new Integer(req.getParameter("dpsordno"));

				DpsOrdService dpsSvc = new DpsOrdService();
				dpsSvc.deleteDpsOrd(dpsordno);

				String url = "/dpsOrd/listAllDpsOrd.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/dpsOrd/listAllDpsOrd.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("listDpsOrd_ByCompositeQuery".equals(action)) { // 來自select_page.jsp的複合查詢請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				
				/***************************1.將輸入資料轉為Map**********************************/ 
				//採用Map<String,String[]> getParameterMap()的方法 
				//注意:an immutable java.util.Map 
				//Map<String, String[]> map = req.getParameterMap();
				HttpSession session = req.getSession();
//				Map<String, String[]> map = (Map<String, String[]>)session.getAttribute("map");
				MemberVO memvo = (MemberVO)session.getAttribute("CurrentUser");
				if(memvo == null){
					session.setAttribute("location", req.getRequestURI() + "?action=listDpsOrd_ByCompositeQuery");
					res.sendRedirect(req.getContextPath()  + "/Login.jsp");
					return;
				}
				Map<String, String[]> map = new HashMap<>();
				map.put("memno", new String[]{memvo.getMemno().toString()});
				
//				if (req.getParameter("whichPage") == null){
//					HashMap<String, String[]> map1 = (HashMap<String, String[]>)req.getParameterMap();
//					HashMap<String, String[]> map2 = new HashMap<String, String[]>();
//					map2 = (HashMap<String, String[]>)map1.clone();
//					session.setAttribute("map",map2);
//					map = (HashMap<String, String[]>)req.getParameterMap();
//				} 
				
				/***************************2.開始複合查詢***************************************/
				DpsOrdService dpsSvc = new DpsOrdService();
				List<DpsOrdVO> list  = dpsSvc.getAll(map);
				Collections.reverse(list);
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("listDpsOrd_ByCompositeQuery", list); // 資料庫取出的list物件,存入request
				RequestDispatcher successView = req.getRequestDispatcher("/dpsOrd/listAllDpsOrdByMemno.jsp"); // 成功轉交listEmps_ByCompositeQuery.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/select_page_wallet.jsp");
				failureView.forward(req, res);
			}
		}
		if ("listDpsOrd_ByCompositeQuery_B".equals(action)) { // 來自select_page.jsp的複合查詢請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				
				/***************************1.將輸入資料轉為Map**********************************/ 
				//採用Map<String,String[]> getParameterMap()的方法 
				//注意:an immutable java.util.Map 
				//Map<String, String[]> map = req.getParameterMap();
				HttpSession session = req.getSession();
				Map<String, String[]> map = (Map<String, String[]>)session.getAttribute("map");
				if (req.getParameter("whichPage") == null){
					HashMap<String, String[]> map1 = (HashMap<String, String[]>)req.getParameterMap();
					HashMap<String, String[]> map2 = new HashMap<String, String[]>();
					map2 = (HashMap<String, String[]>)map1.clone();
					session.setAttribute("map",map2);
					map = (HashMap<String, String[]>)req.getParameterMap();
				} 
				
				/***************************2.開始複合查詢***************************************/
				DpsOrdService dpsSvc = new DpsOrdService();
				List<DpsOrdVO> list  = dpsSvc.getAll(map);
				
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("listDpsOrd_ByCompositeQuery", list); // 資料庫取出的list物件,存入request
				RequestDispatcher successView = req.getRequestDispatcher("/dpsOrd/AfterSearch_dpsOrd.jsp"); // 成功轉交listEmps_ByCompositeQuery.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/dpsOrd/AfterSearch_dpsOrd.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
