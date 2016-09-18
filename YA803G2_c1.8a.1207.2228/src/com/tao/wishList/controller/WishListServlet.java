package com.tao.wishList.controller;

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

import com.tao.member.model.MemberVO;
import com.tao.wishList.model.WishListService;
import com.tao.wishList.model.WishListVO;

@WebServlet("/WishListServlet")
public class WishListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
 
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

//		if ("getOne_For_Display".equals(action)) {
//			List<String> errorMsgs = new LinkedList<String>();
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			try {
//				String str = req.getParameter("wlno");
//				if (str == null || (str.trim()).length() == 0) {
//					errorMsgs.add("請輸入編號");
//				}
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/wishList/select_page.jsp");
//					failureView.forward(req, res);
//					return;
//				}
//
//				Integer wlno = null;
//				try {
//					wlno = new Integer(str);
//				} catch (Exception e) {
//					errorMsgs.add("編號格式不正確");
//				}
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/wishList/select_page.jsp");
//					failureView.forward(req, res);
//					return;
//				}
//
//				WishListService wishSvc = new WishListService();
//				WishListVO wishListVO = wishSvc.getOneWishList(wlno);
//
//				if (wishListVO == null) {
//					errorMsgs.add("查無資料");
//				}
//
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/wishList/select_page.jsp");
//					failureView.forward(req, res);
//					return;
//				}
//
//				req.setAttribute("wishListVO", wishListVO);
//				String url = "/wishList/listOneWishList.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);
//				successView.forward(req, res);
//
//			} catch (Exception e) {
//				errorMsgs.add("無法取得資料:" + e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/wishList/select_page.jsp");
//				failureView.forward(req, res);
//			}
//		}
//
//		if ("getOne_For_Update".equals(action)) {
//			List<String> errorMsgs = new LinkedList<String>();
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			try {
//				Integer wlno = new Integer(req.getParameter("wlno"));
//
//				WishListService wishSvc = new WishListService();
//				WishListVO wishListVO = wishSvc.getOneWishList(wlno);
//
//				req.setAttribute("wishListVO", wishListVO);
//				String url = "/wishList/update_wishList_input.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);
//				successView.forward(req, res);
//			} catch (Exception e) {
//				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/wishList/listAllWishList.jsp");
//				failureView.forward(req, res);
//			}
//		}
//
//		if ("update".equals(action)) {
//			List<String> errorMsgs = new LinkedList<String>();
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			try {
//				Integer wlno = new Integer(req.getParameter("wlno").trim());
//				Integer memno = new Integer(req.getParameter("memno").trim());
//				Integer caseno = new Integer(req.getParameter("caseno").trim());
//
//				WishListVO wishListVO = new WishListVO();
//
//				wishListVO.setWlno(wlno);
//				wishListVO.setMemno(memno);
//				wishListVO.setCaseno(caseno);
//
//				if (!errorMsgs.isEmpty()) {
//					req.setAttribute("wishListVO", wishListVO); 
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/wishList/update_wishList_input.jsp");
//					failureView.forward(req, res);
//					return;
//				}
//
//				WishListService wishSvc = new WishListService();
//				wishListVO = wishSvc.updateWishList(wlno, memno, caseno);
//
//				req.setAttribute("wishListVO", wishListVO);
//				String url = "/wishList/listOneWishList.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url); 
//				successView.forward(req, res);
//
//			} catch (Exception e) {
//				errorMsgs.add("修改資料失敗:" + e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/wishList/update_wishList_input.jsp");
//				failureView.forward(req, res);
//			}
//		}
//
//		if ("insert".equals(action)) {
//
//			List<String> errorMsgs = new LinkedList<String>();
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			try {
//				Integer memno = new Integer(req.getParameter("memno").trim());
//				Integer caseno = new Integer(req.getParameter("caseno").trim());
//
//				WishListVO wishListVO = new WishListVO();
//
//				wishListVO.setMemno(memno);
//				wishListVO.setCaseno(caseno);
//
//				if (!errorMsgs.isEmpty()) {
//					req.setAttribute("wishListVO", wishListVO); 
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/wishList/addWishList.jsp");
//					failureView.forward(req, res);
//					return;
//				}
//
//				WishListService wishSvc = new WishListService();
//				wishListVO = wishSvc.addWishList(memno, caseno);
//
//				String url = "/wishList/listAllWishList.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);
//				successView.forward(req, res);
//
//			} catch (Exception e) {
//				errorMsgs.add(e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/wishList/addWishList.jsp");
//				failureView.forward(req, res);
//			}
//		}

		if ("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");

			try {
				Integer wlno = new Integer(req.getParameter("wlno"));

				WishListService wishSvc = new WishListService();
				WishListVO wishListVO = wishSvc.getOneWishList(wlno);
				wishSvc.deleteWishList(wlno);
				
//				if(requestURL.equals("/wishList/listAllWishListByMemno.jsp")){
//					HttpSession session = req.getSession();
//					Map<String, String[]> map = (Map<String, String[]>)session.getAttribute("map");
//					List<WishListVO> list = wishSvc.getAll(map);
//					req.setAttribute("listWishList_ByCompositeQuery", list);
//				}
//
//				String url = requestURL;
//				RequestDispatcher successView = req.getRequestDispatcher(url);
//				successView.forward(req, res);
				doListWishList(req, res);
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/wishList/listAllWishListByMemno.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("listWishList".equals(action)) {
			doListWishList(req, res);
		}

	}

	private void doListWishList(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		// 			
		MemberVO memvo = (MemberVO) req.getSession().getAttribute("CurrentUser");
		if(memvo == null){
			req.getSession().setAttribute("location", req.getRequestURI() + "?" + req.getQueryString());
			res.sendRedirect(req.getContextPath()  + "/Login.jsp");
			return;
		}
		Map<String, String[]> map = new HashMap<>(); 
		map.put("memno", new String[]{memvo.getMemno().toString()});
		
		WishListService wishSvc = new WishListService();
		List<WishListVO> list  = wishSvc.getAll(map);
//		req.getSession().setAttribute("map", map);
		req.setAttribute("listWishList_ByCompositeQuery", list); // 資料庫取出的list物件,存入request
		RequestDispatcher successView = req
				.getRequestDispatcher("/wishList/listAllWishListByMemno.jsp"); // 成功轉交listEmps_ByCompositeQuery.jsp
		successView.forward(req, res);
		
	}
}
