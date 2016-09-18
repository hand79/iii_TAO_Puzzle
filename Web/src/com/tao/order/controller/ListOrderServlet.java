package com.tao.order.controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tao.order.model.*;


public class ListOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("listAllOrderByMemno".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				Integer memno = new Integer(req.getParameter("memno"));

				OrderService orderSvc = new OrderService();
				List<OrderVO> orderVO = orderSvc.findByBuyer(memno);

				if (orderVO == null) {
					errorMsgs.add("無參與紀錄");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/select_page_wallet.jsp");
					failureView.forward(req, res);
					return;
				}
				req.setAttribute("listAllOrderByMemno", orderVO);
				String url = "/order/listAllOrderByMemno.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("無法取得參與紀錄" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/select_page_wallet.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
