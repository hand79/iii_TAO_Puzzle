package com.tao.wtdReq.controller;

import java.io.IOException;
import java.sql.Timestamp;
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
import com.tao.wtdReq.model.*;

@WebServlet("/WtdReqServlet")
public class WtdReqServlet extends HttpServlet {
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
			req.getRequestDispatcher("/wtdReq/listAllWtdReq.jsp").forward(req, res);
			return;
		}

		if ("getOne_For_Display".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z **********************/
				String str = req.getParameter("wtdreqno");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("�п�J�s��");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/select_page.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				Integer wtdreqno = null;
				try {
					wtdreqno = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("�s���榡�����T");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/wtdReq/select_page.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				WtdReqService wtdSvc = new WtdReqService();
				WtdReqVO wtdReqVO = wtdSvc.getOneWtdReq(wtdreqno);
				if (wtdReqVO == null) {
					errorMsgs.add("�d�L���");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/wtdReq/select_page.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				req.setAttribute("wtdReqVO", wtdReqVO); // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/wtdReq/listOneWtdReq.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/wtdReq/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				Integer wtdreqno = new Integer(req.getParameter("wtdreqno"));

				WtdReqService wtdSvc = new WtdReqService();
				WtdReqVO wtdReqVO = wtdSvc.getOneWtdReq(wtdreqno);

				req.setAttribute("wtdReqVO", wtdReqVO);
				String url = "/wtdReq/update_wtdReq_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/wtdReq/listAllWtdReq.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) { // �Ӧ�update_dpsOrd_input.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL");

			try {
				/*************************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z **********************/
				Integer wtdreqno = new Integer(req.getParameter("wtdreqno").trim());
				Timestamp wtdreqt = null;
				try{
				wtdreqt = Timestamp.valueOf(req.getParameter("wtdreqt").trim());
				}catch(IllegalArgumentException e){
					errorMsgs.add("�п�J���!");
				}
				Double wtdamnt = null;
				try {
					wtdamnt = new Double(req.getParameter("wtdamnt").trim());
				} catch (NumberFormatException e) {
					wtdamnt = 0.0;
					errorMsgs.add("�ж�Ʀr.");
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

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("wtdReqVO", wtdReqVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/wtdReq/update_wtdReq_input.jsp");
					failureView.forward(req, res);
					return; // �{�����_
				}

				/*************************** 2.�}�l�ק��� *****************************************/
				WtdReqService wtdSvc = new WtdReqService();
				wtdReqVO = wtdSvc.updateWtdReq(wtdreqno, wtdreqt, memno,
						wtdamnt, wtdac, reqsts);

				/*************************** 3.�ק粒��,�ǳ����(Send the Success view) *************/
				req.setAttribute("wtdReqVO", wtdReqVO);
				
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // �ק令�\��,���listOneEmp.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (Exception e) {
				errorMsgs.add("�ק��ƥ���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/wtdReq/update_wtdReq_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) { // �Ӧ�addEmp.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z *************************/
				Timestamp wtdreqt = new Timestamp(System.currentTimeMillis());
				Integer memno = new Integer(req.getParameter("memno").trim());
				Double wtdamnt = new Double(req.getParameter("wtdamnt").trim());
				String wtdac = req.getParameter("wtdac").trim();
				String reqsts = req.getParameter("reqsts").trim();

				WtdReqVO wtdReqVO = new WtdReqVO();
				wtdReqVO.setWtdreqt(wtdreqt);
				wtdReqVO.setMemno(memno);
				wtdReqVO.setWtdamnt(wtdamnt);
				wtdReqVO.setWtdac(wtdac);
				wtdReqVO.setReqsts(reqsts);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("wtdReqVO", wtdReqVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/wtdReq/addWtdReq.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.�}�l�s�W��� ***************************************/
				WtdReqService wtdSvc = new WtdReqService();
				wtdReqVO = wtdSvc.addWtdReq(wtdreqt, memno, wtdamnt, wtdac,
						reqsts);
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				String url = "/wtdReq/listAllWtdReq.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllEmp.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/wtdReq/addWtdReq.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert_F".equals(action)) { // �Ӧ�addEmp.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z *************************/
				Timestamp wtdreqt = new Timestamp(System.currentTimeMillis());
				Integer memno = new Integer(req.getParameter("memno").trim());
				Double wtdamnt = new Double(req.getParameter("wtdamnt").trim());
				String wtdac = req.getParameter("wtdac").trim();
				String reqsts = req.getParameter("reqsts").trim();

				WtdReqVO wtdReqVO = new WtdReqVO();
				wtdReqVO.setWtdreqt(wtdreqt);
				wtdReqVO.setMemno(memno);
				wtdReqVO.setWtdamnt(wtdamnt);
				wtdReqVO.setWtdac(wtdac);
				wtdReqVO.setReqsts(reqsts);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("wtdReqVO", wtdReqVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/select_page_wallet.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.�}�l�s�W��� ***************************************/
				WtdReqService wtdSvc = new WtdReqService();
				wtdReqVO = wtdSvc.addWtdReq(wtdreqt, memno, wtdamnt, wtdac,
						reqsts);
				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				String url = "/select_page_wallet.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllEmp.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/select_page_wallet.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) { // �Ӧ�listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL");

			try {
				/*************************** 1.�����ШD�Ѽ� ***************************************/
				Integer wtdReqno = new Integer(req.getParameter("wtdreqno"));

				/*************************** 2.�}�l�R����� ***************************************/
				WtdReqService wtdSvc = new WtdReqService();
				wtdSvc.deleteWtdReq(wtdReqno);

				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add("�R����ƥ���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/wtdReq/listAllWtdReq.jsp");
				failureView.forward(req, res);
			}

		}

		if ("listWtdReq_ByCompositeQuery".equals(action)) { // �Ӧ�select_page.jsp���ƦX�d�߽ШD
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {

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
				WtdReqService wtdSvc = new WtdReqService();
				List<WtdReqVO> list = wtdSvc.getAll(map);

				/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) ************/
				req.setAttribute("listWtdReq_ByCompositeQuery", list); // ��Ʈw���X��list����,�s�Jrequest
				RequestDispatcher successView = req
						.getRequestDispatcher("/wtdReq/listAllWtdReqByMemno.jsp"); // ���\���listEmps_ByCompositeQuery.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/select_page_wallet.jsp");
				failureView.forward(req, res);
			}
		}

		if ("listWtdReq_ByCompositeQuery_B".equals(action)) { // �Ӧ�select_page.jsp���ƦX�d�߽ШD
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {

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
				WtdReqService wtdSvc = new WtdReqService();
				List<WtdReqVO> list = wtdSvc.getAll(map);

				/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) ************/
				req.setAttribute("listWtdReq_ByCompositeQuery", list); // ��Ʈw���X��list����,�s�Jrequest
				RequestDispatcher successView = req
						.getRequestDispatcher("/wtdReq/AfterSearch_wtdReq.jsp"); // ���\���listEmps_ByCompositeQuery.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/wtdReq/AfterSearch_wtdReq.jsp");
				failureView.forward(req, res);
			}
		}
		
	}

}
