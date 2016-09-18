package com.tao.news.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.tao.news.model.NewsService;
import com.tao.news.model.NewsVO;
import com.tao.shop.model.ShopService;
import com.tao.shopproduct.model.ShopproductService;
import com.tao.shopproduct.model.ShopproductVO;

public class NewsServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if("news".equals(action)){
			RequestDispatcher successView = req.getRequestDispatcher("/news/index_back.jsp"); 
			successView.forward(req, res);
			return;
		}

//		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求
//			List<String> errorMsgs = new LinkedList<String>();
//			req.setAttribute("errorMsgs", errorMsgs);

//			try {
//				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
//				String str = req.getParameter("newsno");
//				if (str == null || (str.trim()).length() == 0) {
//					errorMsgs.add("請輸入最新消息編號");
//				}
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/news/index_back.jsp");
//					failureView.forward(req, res);
//					return;
//				}
//
//				Integer newsno = null;
//				try {
//					newsno = new Integer(str);
//				} catch (Exception e) {
//					errorMsgs.add("最新消息編號格式不正確");
//				}
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/news/index_back.jsp");
//					failureView.forward(req, res);
//					return;
//				}
//				/*************************** 2.開始查詢資料 *****************************************/
//				NewsService newsSvc = new NewsService();
//				NewsVO newsVO = newsSvc.getOneNews(newsno);
//				if (newsVO == null) {
//					errorMsgs.add("查無資料");
//				}
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/news/index_back.jsp");
//					failureView.forward(req, res);
//					return;// 程式中斷
//				}
//				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
//				req.setAttribute("newsVO", newsVO); // 資料庫取出的newsVO物件,存入req
//				String url = "/news/index_back.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
//				successView.forward(req, res);
//
//				/*************************** 其他可能的錯誤處理 *************************************/
//			} catch (Exception e) {
//				errorMsgs.add("無法取得資料:" + e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/news/index_back.jsp");
//				failureView.forward(req, res);
//			}
//		}

//		if ("getOne_For_Update".equals(action)) { // 來自index_back.jsp的請求
//
//			List<String> errorMsgs = new LinkedList<String>();
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			try {
//				/*************************** 1.接收請求參數 ****************************************/
//				Integer newsno = new Integer(req.getParameter("newsno"));
//
//				/*************************** 2.開始查詢資料 ****************************************/
//				NewsService newsSvc = new NewsService();
//				NewsVO newsVO = newsSvc.getOneNews(newsno);
//
//				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
//				req.setAttribute("newsVO", newsVO); 
//				String url = "/news/index_back.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);
//				successView.forward(req, res);
//
//				/*************************** 其他可能的錯誤處理 **********************************/
//			} catch (Exception e) {
//				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
//
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/news/index_back.jsp");
//				failureView.forward(req, res);
//			}
//		}

		if ("update".equals(action)) { 
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL"); 
			String whichPage = req.getParameter("whichPage"); 
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				Integer newsno = new Integer(req.getParameter("newsno").trim());
				String title = new String(req.getParameter("title").trim());
				String text = new String(req.getParameter("text").trim());
				
				java.sql.Timestamp pubtime = null;
				try {
					pubtime = java.sql.Timestamp
							.valueOf(req.getParameter("pubtime1").trim() + " "
									+ req.getParameter("pubtime2").trim()
									+ ":00");
				} catch (IllegalArgumentException e) {
					pubtime = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				NewsVO newsVO = new NewsVO();
				newsVO.setNewsno(newsno);
				newsVO.setText(text);
				newsVO.setText(text);
				newsVO.setPubtime(pubtime);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("newsVO", newsVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/news/index_back.jsp");
					failureView.forward(req, res);
					return; 
				}
				/*************************** 2.開始修改資料 *****************************************/
				NewsService newsSvc = new NewsService();
				newsVO = newsSvc.updateNews(newsno, title, text, pubtime);
				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				String url = requestURL+"?whichPage="+whichPage;
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/news/index_back.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			req.setCharacterEncoding("UTF-8");
			String requestURL = req.getParameter("requestURL"); 
			String whichPage = req.getParameter("whichPage"); 
			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String title = req.getParameter("title").trim();
				String text = req.getParameter("text").trim();

				java.sql.Timestamp pubtime = null;
				try {
					pubtime = java.sql.Timestamp
							.valueOf(req.getParameter("pubtime1").trim() + " "
									+ req.getParameter("pubtime2").trim()
									+ ":00");
				} catch (IllegalArgumentException e) {
					pubtime = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				NewsVO newsVO = new NewsVO();
				newsVO.setTitle(title);
				newsVO.setText(text);
				newsVO.setPubtime(pubtime);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("newsVO", newsVO); 
					RequestDispatcher failureView = req
							.getRequestDispatcher("/news/index_back.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始新增資料 ***************************************/
				NewsService newsSvc = new NewsService();
				newsVO = newsSvc.addNews(title, text, pubtime);
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				List<NewsVO> list  = newsSvc.getAll();
				req.setAttribute("listNews_ByCompositeQuery", list);
				String url = requestURL+"?whichPage="+whichPage;
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/news/index_back.jsp");

				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) { 
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL"); 
			String whichPage = req.getParameter("whichPage"); 
			 try {
			/*************************** 1.接收請求參數 ***************************************/
			Integer newsno = new Integer(req.getParameter("newsno"));
			/*************************** 2.開始刪除資料 ***************************************/
			NewsService newsSvc = new NewsService();
			newsSvc.deleteNews(newsno);
			/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
			if (requestURL.equals("/news/search_page_back.jsp")) {
				HttpSession session = req.getSession();
				Map<String, String[]> map = (Map<String, String[]>) session.getAttribute("map");
				List<NewsVO> list = newsSvc.getAll(map);
				req.setAttribute("listNews_ByCompositeQuery", list);
			}
			RequestDispatcher successView = req.getRequestDispatcher("/news/search_page_back.jsp");
			successView.forward(req, res);

			/*************************** 其他可能的錯誤處理 **********************************/
			 } catch (Exception e) {
			 errorMsgs.add("刪除資料失敗:"+e.getMessage());
			 RequestDispatcher failureView = req
			 .getRequestDispatcher("/news/index_back.jsp");
			 failureView.forward(req, res);
			 }
		}
		
		if ("listNews_ByCompositeQuery".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setCharacterEncoding("UTF-8");
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************1.將輸入資料轉為Map**********************************/ 

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
				NewsService newsSvc = new NewsService();
				List<NewsVO> list  = newsSvc.getAll(map);
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("listNews_ByCompositeQuery", list); 
				RequestDispatcher successView = req.getRequestDispatcher("/news/search_page_back.jsp"); 
				successView.forward(req, res);
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/news/search_page_back.jsp");
				failureView.forward(req, res);
			}
		}		
		if ("queryupdate".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setCharacterEncoding("UTF-8");
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				Integer newsno = new Integer(req.getParameter("newsno").trim());
				String title = new String(req.getParameter("title").trim().getBytes("ISO-8859-1"), "UTF-8");
				String text = new String(req.getParameter("text").trim().getBytes("ISO-8859-1"), "UTF-8");
				
				java.sql.Timestamp pubtime = null;
				try {
					pubtime = java.sql.Timestamp
							.valueOf(req.getParameter("pubtime1").trim() + " "
									+ req.getParameter("pubtime2").trim()
									+ ":00");
				} catch (IllegalArgumentException e) {
					pubtime = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				NewsVO newsVO = new NewsVO();
				newsVO.setNewsno(newsno);
				newsVO.setText(text);
				newsVO.setText(text);
				newsVO.setPubtime(pubtime);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("newsVO", newsVO); 
					RequestDispatcher failureView = req
							.getRequestDispatcher("/news/index_back.jsp");
					failureView.forward(req, res);
					return; 
				}
				/*************************** 2.開始修改資料 *****************************************/
				NewsService newsSvc = new NewsService();
				newsVO = newsSvc.updateNews(newsno, title, text, pubtime);
				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				if (requestURL.equals("/news/search_page_back.jsp")) {
					HttpSession session = req.getSession();
					Map<String, String[]> map = (Map<String, String[]>) session.getAttribute("map");
					List<NewsVO> list = newsSvc.getAll(map);
					req.setAttribute("listNews_ByCompositeQuery", list);
				}
				
				req.setAttribute("newsVO", new NewsService().getOneNews(newsno));
				RequestDispatcher successView = req.getRequestDispatcher("/news/search_page_back.jsp"); 
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/news/index_back.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("queryAjax".equals(action)) {
			NewsService newsSvc = new NewsService();
			List<NewsVO> set = newsSvc.getAll();
			req.setAttribute("queryAjax", set);
			RequestDispatcher successView = req.getRequestDispatcher("/news/index_back.jsp"); 
			successView.forward(req, res);
		}
	}
}
