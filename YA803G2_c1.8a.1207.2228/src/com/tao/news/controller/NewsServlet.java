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

//		if ("getOne_For_Display".equals(action)) { // �Ӧ�select_page.jsp���ШD
//			List<String> errorMsgs = new LinkedList<String>();
//			req.setAttribute("errorMsgs", errorMsgs);

//			try {
//				/*************************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z **********************/
//				String str = req.getParameter("newsno");
//				if (str == null || (str.trim()).length() == 0) {
//					errorMsgs.add("�п�J�̷s�����s��");
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
//					errorMsgs.add("�̷s�����s���榡�����T");
//				}
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/news/index_back.jsp");
//					failureView.forward(req, res);
//					return;
//				}
//				/*************************** 2.�}�l�d�߸�� *****************************************/
//				NewsService newsSvc = new NewsService();
//				NewsVO newsVO = newsSvc.getOneNews(newsno);
//				if (newsVO == null) {
//					errorMsgs.add("�d�L���");
//				}
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/news/index_back.jsp");
//					failureView.forward(req, res);
//					return;// �{�����_
//				}
//				/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) *************/
//				req.setAttribute("newsVO", newsVO); // ��Ʈw���X��newsVO����,�s�Jreq
//				String url = "/news/index_back.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\���
//				successView.forward(req, res);
//
//				/*************************** ��L�i�઺���~�B�z *************************************/
//			} catch (Exception e) {
//				errorMsgs.add("�L�k���o���:" + e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/news/index_back.jsp");
//				failureView.forward(req, res);
//			}
//		}

//		if ("getOne_For_Update".equals(action)) { // �Ӧ�index_back.jsp���ШD
//
//			List<String> errorMsgs = new LinkedList<String>();
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			try {
//				/*************************** 1.�����ШD�Ѽ� ****************************************/
//				Integer newsno = new Integer(req.getParameter("newsno"));
//
//				/*************************** 2.�}�l�d�߸�� ****************************************/
//				NewsService newsSvc = new NewsService();
//				NewsVO newsVO = newsSvc.getOneNews(newsno);
//
//				/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) ************/
//				req.setAttribute("newsVO", newsVO); 
//				String url = "/news/index_back.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);
//				successView.forward(req, res);
//
//				/*************************** ��L�i�઺���~�B�z **********************************/
//			} catch (Exception e) {
//				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
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
				/*************************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z **********************/
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
					errorMsgs.add("�п�J���!");
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
				/*************************** 2.�}�l�ק��� *****************************************/
				NewsService newsSvc = new NewsService();
				newsVO = newsSvc.updateNews(newsno, title, text, pubtime);
				/*************************** 3.�ק粒��,�ǳ����(Send the Success view) *************/
				String url = requestURL+"?whichPage="+whichPage;
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (Exception e) {
				errorMsgs.add("�ק��ƥ���:" + e.getMessage());
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
				/*********************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z *************************/
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
					errorMsgs.add("�п�J���!");
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
				/*************************** 2.�}�l�s�W��� ***************************************/
				NewsService newsSvc = new NewsService();
				newsVO = newsSvc.addNews(title, text, pubtime);
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				List<NewsVO> list  = newsSvc.getAll();
				req.setAttribute("listNews_ByCompositeQuery", list);
				String url = requestURL+"?whichPage="+whichPage;
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
				/*************************** ��L�i�઺���~�B�z **********************************/
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
			/*************************** 1.�����ШD�Ѽ� ***************************************/
			Integer newsno = new Integer(req.getParameter("newsno"));
			/*************************** 2.�}�l�R����� ***************************************/
			NewsService newsSvc = new NewsService();
			newsSvc.deleteNews(newsno);
			/*************************** 3.�R������,�ǳ����(Send the Success view) ***********/
			if (requestURL.equals("/news/search_page_back.jsp")) {
				HttpSession session = req.getSession();
				Map<String, String[]> map = (Map<String, String[]>) session.getAttribute("map");
				List<NewsVO> list = newsSvc.getAll(map);
				req.setAttribute("listNews_ByCompositeQuery", list);
			}
			RequestDispatcher successView = req.getRequestDispatcher("/news/search_page_back.jsp");
			successView.forward(req, res);

			/*************************** ��L�i�઺���~�B�z **********************************/
			 } catch (Exception e) {
			 errorMsgs.add("�R����ƥ���:"+e.getMessage());
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
				/***************************1.�N��J����ରMap**********************************/ 

				HttpSession session = req.getSession();
				Map<String, String[]> map = (Map<String, String[]>)session.getAttribute("map");
				if (req.getParameter("whichPage") == null){
					HashMap<String, String[]> map1 = (HashMap<String, String[]>)req.getParameterMap();
					HashMap<String, String[]> map2 = new HashMap<String, String[]>();
					map2 = (HashMap<String, String[]>)map1.clone();
					session.setAttribute("map",map2);
					map = (HashMap<String, String[]>)req.getParameterMap();
				} 
				
				/***************************2.�}�l�ƦX�d��***************************************/
				NewsService newsSvc = new NewsService();
				List<NewsVO> list  = newsSvc.getAll(map);
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
				req.setAttribute("listNews_ByCompositeQuery", list); 
				RequestDispatcher successView = req.getRequestDispatcher("/news/search_page_back.jsp"); 
				successView.forward(req, res);
				/***************************��L�i�઺���~�B�z**********************************/
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
				/*************************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z **********************/
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
					errorMsgs.add("�п�J���!");
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
				/*************************** 2.�}�l�ק��� *****************************************/
				NewsService newsSvc = new NewsService();
				newsVO = newsSvc.updateNews(newsno, title, text, pubtime);
				/*************************** 3.�ק粒��,�ǳ����(Send the Success view) *************/
				if (requestURL.equals("/news/search_page_back.jsp")) {
					HttpSession session = req.getSession();
					Map<String, String[]> map = (Map<String, String[]>) session.getAttribute("map");
					List<NewsVO> list = newsSvc.getAll(map);
					req.setAttribute("listNews_ByCompositeQuery", list);
				}
				
				req.setAttribute("newsVO", new NewsService().getOneNews(newsno));
				RequestDispatcher successView = req.getRequestDispatcher("/news/search_page_back.jsp"); 
				successView.forward(req, res);
				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (Exception e) {
				errorMsgs.add("�ק��ƥ���:" + e.getMessage());
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
