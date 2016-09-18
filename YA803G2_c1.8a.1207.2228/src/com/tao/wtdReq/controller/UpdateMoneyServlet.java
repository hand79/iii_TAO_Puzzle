package com.tao.wtdReq.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tao.jimmy.member.TinyMemberService;


import com.tao.member.model.MemberService;
import com.tao.member.model.MemberVO;
import com.tao.wtdReq.model.WtdReqService;
import com.tao.wtdReq.model.WtdReqVO;

@WebServlet("/updateMoneyServlet")
public class UpdateMoneyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("UpdateMoney_When_insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				Timestamp wtdreqt = new Timestamp(System.currentTimeMillis());
				Integer memno = new Integer(req.getParameter("memno").trim());
				Double wtdamnt = null;
				try{
					wtdamnt = new Double(req.getParameter("wtdamnt").trim());
				}catch(NumberFormatException e){
					errorMsgs.add("請填數字");
				}
				if(wtdamnt < 1000){
					errorMsgs.add("就跟你說僅接受$1000以上的申請，講不聽欸");
				}
				
				MemberService membSvc = new MemberService();
				MemberVO memVO = membSvc.findByPrimaryKey(memno);
				Integer money = memVO.getMoney();
				double cash = (double) money;
				if(wtdamnt > cash){
					errorMsgs.add("餘額不足，不會算術逆？");
				}
				
				String wtdac = req.getParameter("wtdac").trim();
				if(wtdac == null || wtdac.length() ==0){
					errorMsgs.add("請輸入銀行帳戶");
				}
				String reqsts = req.getParameter("reqsts").trim();

				WtdReqVO wtdReqVO = new WtdReqVO();
				wtdReqVO.setWtdreqt(wtdreqt);
				wtdReqVO.setMemno(memno);
				wtdReqVO.setWtdamnt(wtdamnt);
				wtdReqVO.setWtdac(wtdac);
				wtdReqVO.setReqsts(reqsts);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("wtdReqVO", wtdReqVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/select_page_wallet.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				WtdReqService wtdSvc = new WtdReqService();
				wtdReqVO = wtdSvc.addWtdReq(wtdreqt, memno, wtdamnt, wtdac,
						reqsts);
				
				int amount  = -(wtdamnt.intValue());
				 
				TinyMemberService memSvc = new TinyMemberService();
				memSvc.changeMoney(memno, amount);		
				
				String url = "/wtdReq/successPage_wtdReq.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("新增申請失敗" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/wtdReq/errorPage_wtdReq.jsp");
				failureView.forward(req, res);
			}
		}

		if ("UpdateMoney_When_update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL");

			try {
				Integer wtdreqno = new Integer(req.getParameter("wtdreqno").trim());
				Timestamp wtdreqt = null;
				try {
					wtdreqt = Timestamp.valueOf(req.getParameter("wtdreqt")
							.trim());
				} catch (IllegalArgumentException e) {
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

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("wtdReqVO", wtdReqVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/wtdReq/update_wtdReq_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				if ("CANCELLED".equals(reqsts)) {
					int amount = wtdamnt.intValue();
					TinyMemberService memSvc = new TinyMemberService();
					memSvc.changeMoney(memno, amount);
				}
				
				WtdReqService wtdSvc = new WtdReqService();
				wtdReqVO = wtdSvc.updateWtdReq(wtdreqno, wtdreqt, memno,
						wtdamnt, wtdac, reqsts);

				req.setAttribute("wtdReqVO", wtdReqVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = requestURL;

				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("更新狀態失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/wtdReq/update_wtdReq_input.jsp");
				failureView.forward(req, res);
			}
		}

	}
}
