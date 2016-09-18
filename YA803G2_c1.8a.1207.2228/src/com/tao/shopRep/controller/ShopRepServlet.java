package com.tao.shopRep.controller;

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

import com.tao.shopRep.model.ShopRepService;
import com.tao.shopRep.model.ShopRepVO;


@WebServlet("/ShopRepServlet")
public class ShopRepServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		if("default".equals(action)){//��ݭɹL��
			req.getRequestDispatcher("/shopRep/listAllShopRep.jsp").forward(req, res);
		}
		if ("getOne_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String str = req.getParameter("srepno");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("�п�J�s��");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/select_page_report.jsp");
					failureView.forward(req, res);
					return;
				}

				Integer srepno = null;
				try {
					srepno = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("�s���榡�����T");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/select_page_report.jsp");
					failureView.forward(req, res);
					return;
				}

				ShopRepService shopSvc = new ShopRepService();
				ShopRepVO shopRepVO = shopSvc.getOneShopRep(srepno);
				if (shopRepVO == null) {
					errorMsgs.add("�d�L���");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/select_page_report.jsp");
					failureView.forward(req, res);
					return;
				}
				req.setAttribute("shopRepVO", shopRepVO);
				String url = "/shopRep/listOneShopRep.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
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
				Integer srepno = new Integer(req.getParameter("srepno"));

				ShopRepService shopSvc = new ShopRepService();
				ShopRepVO shopRepVO = shopSvc.getOneShopRep(srepno);

				req.setAttribute("shopRepVO", shopRepVO);
				String url = "/shopRep/update_shopRep_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
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
				Integer srepno = new Integer(req.getParameter("srepno").trim());
				Integer shopno = new Integer(req.getParameter("shopno").trim());
				Integer repno = new Integer(req.getParameter("repno").trim());
				String sreprsn = req.getParameter("sreprsn").trim();

				ShopRepVO shopRepVO = new ShopRepVO();

				shopRepVO.setSrepno(srepno);
				shopRepVO.setShopno(shopno);
				shopRepVO.setRepno(repno);
				shopRepVO.setSreprsn(sreprsn);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("shopRepVO", shopRepVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/shopRep/update_shopRep_input.jsp");
					failureView.forward(req, res);
					return;
				}
				ShopRepService shopSvc = new ShopRepService();
				shopRepVO = shopSvc.updateShopRep(srepno, shopno, repno,
						sreprsn);

				req.setAttribute("shopRepVO", shopRepVO);
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("�ק��ƥ���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/shopRep/update_shopRep_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				Integer shopno = new Integer(req.getParameter("shopno").trim());
				Integer repno = new Integer(req.getParameter("repno").trim());
				String sreprsn = req.getParameter("sreprsn").trim();

				ShopRepVO shopRepVO = new ShopRepVO();

				shopRepVO.setShopno(shopno);
				shopRepVO.setRepno(repno);
				shopRepVO.setSreprsn(sreprsn);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("shopRepVO", shopRepVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/shopRep/addShopRep.jsp");
					failureView.forward(req, res);
					return;
				}
				ShopRepService shopSvc = new ShopRepService();
				shopRepVO = shopSvc.addShopRep(shopno, repno, sreprsn);

				String url = "/shopRep/listAllShopRep.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/shopRep/addShopRep.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL");

			try {
				Integer srepno = new Integer(req.getParameter("srepno"));

				ShopRepService shopSvc = new ShopRepService();
				shopSvc.deleteShopRep(srepno);

				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("�R����ƥ���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/shopRep/listAllShopRep.jsp");
				failureView.forward(req, res);
			}
		}

		if ("listShopRep_ByCompositeQuery".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try{
				/*************************** 1.�N��J����ରMap **********************************/
				// �ĥ�Map<String,String[]> getParameterMap()����k
				// �`�N:an immutable java.util.Map
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

				/*************************** 2.�}�l�ƦX�d�� ***************************************/
				ShopRepService srepSvc = new ShopRepService();
				List<ShopRepVO> list = srepSvc.getAll(map);

				/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) ************/
				req.setAttribute("listShopRep_ByCompositeQuery", list); // ��Ʈw���X��list����,�s�Jrequest
				RequestDispatcher successView = req
						.getRequestDispatcher("/shopRep/AfterSearch_shopRep.jsp"); // ���\���listEmps_ByCompositeQuery.jsp
				successView.forward(req, res);

				
			}catch(Exception e){
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/shopRep/AfterSearch_shopRep.jsp");
				failureView.forward(req, res);
			}
			
		}
		
	}

}
