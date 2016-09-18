package com.tao.dpsOrd.controller;

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

import com.tao.dpsOrd.model.DpsOrdService;
import com.tao.dpsOrd.model.DpsOrdVO;
import com.tao.jimmy.member.TinyMemberService;


public class UpdateMoneyDpsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if("UpdateMoney_When_UpdateDps".equals(action)){
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL");
			
			try{
				Integer dpsordno = new Integer(req.getParameter("dpsordno").trim());	
				Timestamp dpsordt = null;
				try{
					dpsordt = Timestamp.valueOf(req.getParameter("dpsordt").trim());
				}catch(IllegalArgumentException e){
					errorMsgs.add("�п�J�x�Ȯɶ�");
				}
				
				Double dpsamnt = null;
				try {
					dpsamnt = new Double(req.getParameter("dpsamnt").trim());
				} catch (NumberFormatException e) {
					dpsamnt = 0.0;
					errorMsgs.add("�ж�Ʀr.");
				}
				
				Integer memno = new Integer(req.getParameter("memno").trim());					
				String dpshow = req.getParameter("dpshow").trim();
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
					req.setAttribute("dpsOrdVO", dpsOrdVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/dpsOrd/update_dpsOrd_input.jsp");
					failureView.forward(req, res);
					return; // �{�����_
				}	
				
				DpsOrdService dpsSvc = new DpsOrdService();
				dpsOrdVO = dpsSvc.updateDpsOrdVO(dpsordno, dpsordt, dpsamnt,
						memno, dpshow, ordsts, atmac);

				req.setAttribute("dpsOrdVO", dpsOrdVO); // ��Ʈwupdate���\��,���T����empVO����,�s�Jreq

				if("COMPLETED".equals(ordsts)){
					int amount = dpsamnt.intValue();
					TinyMemberService memSvc = new TinyMemberService();
					memSvc.changeMoney(memno, amount);
				}
				
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // �ק令�\��,���listOneEmp.jsp
				successView.forward(req, res);				
				
			}catch(Exception e){
				errorMsgs.add("��s���A����:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/dpsOrd/update_dpsOrd_input.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
