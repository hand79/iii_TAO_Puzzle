package com.tao.blogin.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tao.acc.model.AccountService;
import com.tao.acc.model.AccountVO;
import com.tao.acc.model.PerListService;
import com.tao.acc.model.PerListVO;

public class BackLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();

		if ("loginCheck".equals(action)) {
			List<String> message = new LinkedList<String>();
			String acc = req.getParameter("loginacc");
			String accpw = req.getParameter("loginaccpw");
			StringBuffer stringBuffer = new StringBuffer();

			/******************** 查詢有無這筆帳號 *******************************/
			AccountService accountSvc = new AccountService();
			if (accountSvc.getOneAccount(acc) == null) {
				message.add("查無此帳號");
			} else if (!accountSvc.getOneAccount(acc).getAccpw().equals(accpw)) {
				message.add("密碼錯誤");
			} else {
				message.add("ok");
			}
			for (String string : message) {
				stringBuffer.append(string);
			}
			res.setContentType("text/html");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			out.write(stringBuffer.toString());
			out.flush();
			out.close();
		}

		if ("login".equals(action)) {
			List<String> message = new LinkedList<String>();
			try {
				String acc = req.getParameter("loginacc");
				String accpw = req.getParameter("loginaccpw");
				StringBuffer stringBuffer = new StringBuffer();
				
				/******************** 查詢有無這筆帳號 *******************************/
				AccountService accountSvc = new AccountService();
				AccountVO accountVO = accountSvc.getOneAccount(acc);
				if (accountVO == null) {
					message.add("查無此帳號");
				} else if (!accountSvc.getOneAccount(acc).getAccpw()
						.equals(accpw)) {
					message.add("密碼錯誤");
				} else {
					message.add("ok");
				}
				for (String string : message) {
					stringBuffer.append(string);
				}
				// Send the use back to the form, if there were errors

				if (!(stringBuffer.toString().equals("ok"))) {
					req.setAttribute("ermessage", message);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/b/backlogin.jsp");
					failureView.forward(req, res);

					return;
				}

				/********************* 處理核對成功並轉送 ***********************************/
				PerListService perListSvc = new PerListService();
				Set<PerListVO> perListVOs = perListSvc
						.getAllOneAccountPermissionInfo(acc);
				session.setAttribute("bAccount", accountVO);
				session.setAttribute("bAccountPerlist", perListVOs);

					
				res.sendRedirect(req.getContextPath()+"/back/BackLoginServlet.do");
				return;

		

				/******************** 其他錯誤 ***************************************/
			} catch (Exception e) {

				message.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/b/backlogin.jsp");
				failureView.forward(req, res);
			}
			
		}
		if ("logout".equals(action)) {
			
			session.removeAttribute("bAccount");
			session.removeAttribute("bAccountPerlist");
			res.sendRedirect(req.getContextPath()+"/b/backlogin.jsp");			
			return;
		}
			
			
		if(action == null){		
			req.getRequestDispatcher("/b/backindex.jsp").forward(req, res);
		}
	}

}
