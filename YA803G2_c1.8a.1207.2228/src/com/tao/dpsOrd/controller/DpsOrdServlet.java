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
		
		if("default".equals(action)){//�ɹL��
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
					errorMsgs.add("�п�J�s��");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/dpsOrd/select_page_dps.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				Integer dpsordno = null;
				try {
					dpsordno = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("�s���榡�����T");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/dpsOrd/select_page_dps.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				DpsOrdService dpsSvc = new DpsOrdService();
				DpsOrdVO dpsOrdVO = dpsSvc.getOneDpsOrd(dpsordno);
				if (dpsOrdVO == null) {
					errorMsgs.add("�d�L���");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/dpsOrd/select_page_dps.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				req.setAttribute("dpsOrdVO", dpsOrdVO);
				String url = "/dpsOrd/listOneDpsOrd.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
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
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
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
					errorMsgs.add("�ж�Ʀr.");
				}
						
				Timestamp dpsordt = null;
				try{
					dpsordt = Timestamp.valueOf(req.getParameter("dpsordt").trim());
				}catch(IllegalArgumentException e){
					dpsordt = new Timestamp(System.currentTimeMillis());
					errorMsgs.add("�п�J�x�Ȯɶ�");
				}
				
				Double dpsamnt = null;
				try {
					dpsamnt = new Double(req.getParameter("dpsamnt").trim());
				} catch (NumberFormatException e) {
					dpsamnt = 0.0;
					errorMsgs.add("�ж�Ʀr.");
				}
				Integer memno = null;
				try {
					memno = new Integer(req.getParameter("memno").trim());
				} catch (NumberFormatException e) {
					memno = 1001;
					errorMsgs.add("�ж�|���s��");
				}
						
				String dpshow = req.getParameter("dpshow").trim();
				if(dpshow == null || dpshow.length() ==0){
					errorMsgs.add("�п�J�x�Ȥ覡");
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

				String url = requestURL;				
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("�ק��ƥ���:" + e.getMessage());
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
					errorMsgs.add("�п�J�Ʀr");
				}
				Integer memno = null;
				try {
					memno = new Integer(req.getParameter("memno").trim());
				} catch (NumberFormatException e) {
					memno = 1002;
					errorMsgs.add("�п�J�|���s��");
				}
				String dpshow = req.getParameter("dpshow").trim();
				if(dpshow == null || dpshow.length() ==0){
					errorMsgs.add("�п���x�Ȥ覡");
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
					req.setAttribute("dpsOrdVO", dpsOrdVO); // �t����J�榡���~��empVO����,�]�s�Jreq
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
					errorMsgs.add("�п�J�Ʀr");
				}
				Integer memno = null;
				try {
					memno = new Integer(req.getParameter("memno").trim());
				} catch (NumberFormatException e) {
					memno = 1002;
					errorMsgs.add("�п�J�|���s��");
				}
				String dpshow = req.getParameter("dpshow").trim();
				if(dpshow == null || dpshow.length() ==0){
					errorMsgs.add("�п���x�Ȥ覡");
				}
				String ordsts = req.getParameter("ordsts").trim();
				if(ordsts == null || ordsts.length() ==0){
					errorMsgs.add("�п�J�x�ȭq�檬�A");
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
					req.setAttribute("dpsOrdVO", dpsOrdVO); // �t����J�榡���~��empVO����,�]�s�Jreq
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
					errorMsgs.add("�п�J�Ʀr");
				}
				Integer memno = null;
				try {
					memno = new Integer(req.getParameter("memno").trim());
				} catch (NumberFormatException e) {
					memno = 1002;
					errorMsgs.add("�п�J�|���s��");
				}
				String dpshow = req.getParameter("dpshow").trim();
				if(dpshow == null || dpshow.length() ==0){
					errorMsgs.add("�п���x�Ȥ覡");
				}
				String ordsts = req.getParameter("ordsts").trim();
				if(ordsts == null || ordsts.length() ==0){
					errorMsgs.add("�п�J�x�ȭq�檬�A");
				}
				String atmac = req.getParameter("atmac").trim();
				if(atmac == null || atmac.length() ==0){
					errorMsgs.add("�п�Jatm");
				}

				DpsOrdVO dpsOrdVO = new DpsOrdVO();

				dpsOrdVO.setDpsordt(dpsordt);
				dpsOrdVO.setDpsamnt(dpsamnt);
				dpsOrdVO.setMemno(memno);
				dpsOrdVO.setDpshow(dpshow);
				dpsOrdVO.setOrdsts(ordsts);
				dpsOrdVO.setAtmac(atmac);

				if (!errorMsgs.isEmpty()) {
					
					req.setAttribute("dpsOrdVO", dpsOrdVO); // �t����J�榡���~��empVO����,�]�s�Jreq
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
				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("�R����ƥ���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/dpsOrd/listAllDpsOrd.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("listDpsOrd_ByCompositeQuery".equals(action)) { // �Ӧ�select_page.jsp���ƦX�d�߽ШD
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				
				/***************************1.�N��J����ରMap**********************************/ 
				//�ĥ�Map<String,String[]> getParameterMap()����k 
				//�`�N:an immutable java.util.Map 
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
				
				/***************************2.�}�l�ƦX�d��***************************************/
				DpsOrdService dpsSvc = new DpsOrdService();
				List<DpsOrdVO> list  = dpsSvc.getAll(map);
				Collections.reverse(list);
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
				req.setAttribute("listDpsOrd_ByCompositeQuery", list); // ��Ʈw���X��list����,�s�Jrequest
				RequestDispatcher successView = req.getRequestDispatcher("/dpsOrd/listAllDpsOrdByMemno.jsp"); // ���\���listEmps_ByCompositeQuery.jsp
				successView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/select_page_wallet.jsp");
				failureView.forward(req, res);
			}
		}
		if ("listDpsOrd_ByCompositeQuery_B".equals(action)) { // �Ӧ�select_page.jsp���ƦX�d�߽ШD
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				
				/***************************1.�N��J����ରMap**********************************/ 
				//�ĥ�Map<String,String[]> getParameterMap()����k 
				//�`�N:an immutable java.util.Map 
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
				
				/***************************2.�}�l�ƦX�d��***************************************/
				DpsOrdService dpsSvc = new DpsOrdService();
				List<DpsOrdVO> list  = dpsSvc.getAll(map);
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
				req.setAttribute("listDpsOrd_ByCompositeQuery", list); // ��Ʈw���X��list����,�s�Jrequest
				RequestDispatcher successView = req.getRequestDispatcher("/dpsOrd/AfterSearch_dpsOrd.jsp"); // ���\���listEmps_ByCompositeQuery.jsp
				successView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/dpsOrd/AfterSearch_dpsOrd.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
