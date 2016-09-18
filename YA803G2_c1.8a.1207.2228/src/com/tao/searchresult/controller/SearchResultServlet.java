package com.tao.searchresult.controller;

import com.tao.cases.model.CasesParser;
import com.tao.cases.model.CasesService;
import com.tao.cases.model.CasesVO;
import com.tao.jimmy.util.model.ColumnValue;
import com.tao.jimmy.util.model.ColumnValueBundle;
import com.tao.member.controller.LocnoAreaConverter;
import com.tao.searchresult.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchResultServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("searchMenuCase".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				// Integer subcatno = new Integer(req.getParameter("subcatno"));

				String subcatnoStr = req.getParameter("subcatno");
				Integer subcatno = null;
				if (subcatnoStr == null || (subcatnoStr.trim()).length() == 0) {
					errorMsgs.add("�п�ܤl����");
				} else {
					subcatno = new Integer(subcatnoStr);
				}

				// Integer area = new Integer(req.getParameter("area"));

				Integer area = (Integer) req.getSession().getAttribute("area");
				if ((area == null) || (area >= 11) || (area <= 0)) {
					area = 10; // set to default if area is not between 1 to 10

				}

				Integer[] locnos = LocnoAreaConverter.areaToLocno(area);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/search/searchCase.jsp");
					failureView.forward(req, res);
					return; // �{�����_
				}

				/*************************** 2.�}�l�d�߸�� ****************************************/
				Set<CasesVO> set = new CasesService().findByAreaSearch(
						locnos[0], locnos[1], subcatno);
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				if (set.size() <= 0 || set == null) {
					errorMsgs.add("�������d�L���");
				}
				req.setAttribute("set", set);
				String url = "/search/searchCase.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/search/searchCase.jsp");
				failureView.forward(req, res);
			}

		} // end of searchMenuCase statement

		if ("searchMenuShop".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				// Integer subcatno = new Integer(req.getParameter("subcatno"));

				String subcatnoStr = req.getParameter("subcatno");
				Integer subcatno = null;
				if (subcatnoStr == null || (subcatnoStr.trim()).length() == 0) {
					errorMsgs.add("�п�ܤl����");
				} else {
					subcatno = new Integer(subcatnoStr);
				}

				// Integer area = new Integer(req.getParameter("area"));

				Integer area = (Integer) req.getSession().getAttribute("area");
				if ((area == null) || (area >= 11) || (area <= 0)) {
					area = 10;
				}

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/search/searchShop.jsp");
					failureView.forward(req, res);
					return; // �{�����_
				}
				/*************************** 2.�}�l�d�߸�� ****************************************/
				shopResultService srs = new shopResultService();
				List<shopResultVO> list = srs.findShopBySubCat(subcatno, area);
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				if (list.size() <= 0 || list == null) {
					errorMsgs.add("�������d�L���");
				}
				req.setAttribute("list", list);
				String url = "/search/searchShop.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/search/searchShop.jsp");
				failureView.forward(req, res);
			}
		} // end of searchMenuShop statement

		if ("searchKeyCase".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				req.setCharacterEncoding("UTF-8");
				String keywordtemp = req.getParameter("keyword");
				if (keywordtemp == null || keywordtemp.length() == 0) {
					errorMsgs.add("�п�J����r");
				}
				String keyword = new String(keywordtemp.getBytes("ISO-8859-1"),
						"UTF-8");

				boolean isKeyOK = InputChecker.checkKeyword(keyword);

				if (!isKeyOK) {
					errorMsgs.add("�j�M�п�J����, �^��, �P�Ʀr. �ФŨϥίS��r��.");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/search/searchCase.jsp");
					failureView.forward(req, res);
					return;
				}
				Integer area = (Integer) req.getSession().getAttribute("area");
				if (area == null || area < 1 || area > 10) {
					area = 10;
				}
				Integer[] locnos = LocnoAreaConverter.areaToLocno(area);
				System.out.println("area=" + area+ " locnoFrom="+  locnos[0] + " locnoTo="+ locnos[1]);
				System.out.println("keyword=" + keyword);
				/*************************** 2.�}�l�d�߸�� ****************************************/

				CasesService casesSvc = new CasesService();
				Set<CasesVO> set = casesSvc.getByTitleKeyword(keyword, locnos[0], locnos[1]);

				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				if (set == null || set.size() <= 0) {
					errorMsgs.add("������r�d�L���");
				} else {
					req.setAttribute("set", set);
				}
				String url = "/search/searchCase.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/search/searchCase.jsp");
				failureView.forward(req, res);
			}

		} // end of searchKeyCase statement

		if ("searchKeyShop".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				req.setCharacterEncoding("UTF-8");
				String keywordtemp = req.getParameter("keyword");
				if (keywordtemp == null || keywordtemp.length() == 0) {
					errorMsgs.add("�п�J����r");
				}
				String keyword = new String(keywordtemp.getBytes("ISO-8859-1"),
						"UTF-8");

				boolean isKeyOK = InputChecker.checkKeyword(keyword);
				if (!isKeyOK) {
					errorMsgs.add("�j�M�п�J����, �^��, �P�Ʀr. �ФŨϥίS��r��.");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/search/searchShop.jsp");
					failureView.forward(req, res);
					return;
				}

				System.out.println(keyword);

				/*************************** 2.�}�l�d�߸�� ****************************************/
				shopResultService srs = new shopResultService();
				List<shopResultVO> list = srs.findShopByNameKey(keyword);
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				if (list.size() <= 0 || list == null) {
					errorMsgs.add("������r�d�L���");
				}
				req.setAttribute("list", list);
				String url = "/search/searchShop.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/search/searchShop.jsp");
				failureView.forward(req, res);
			}
		} // end of searchKeyShop statement

	}
}
