package com.tao.acc.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import com.tao.acc.model.PermissionService;
import com.tao.acc.model.PermissionVO;

public class AccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		if ("default".equals(action)) {
			RequestDispatcher successView = req
					.getRequestDispatcher("/account/Account.jsp");
			successView.forward(req, res);
			return;
		}
		if ("insert".equals(action)) {
			String requestURL = req.getParameter("requestURL");

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.�����ШD�Ѽ�-���~�B�z *************************/
				String acc = req.getParameter("acc").trim();
				String nick = req.getParameter("nick").trim();
				String email = req.getParameter("email").trim();

				boolean okemail = MatcherData.matchEmail(email);
				boolean okaccount = MatcherData.matchAccount(acc);
				boolean oknick = MatcherData.matchNick(nick);

				AccountService accSvc = new AccountService();

				if (accSvc.getOneAccount(acc) != null) {
					errorMsgs.add("�w�إ߹L���b��");
				} else if (acc == null || acc.length() <= 0) {
					errorMsgs.add("�ж�b��");
				} else if (!okaccount) {
					errorMsgs.add("�п�J�^�ƲV�X���b���榡");
				} else if (acc.length() > 15 || acc.length() < 4) {
					errorMsgs.add("�b���^�ƲV�X���׽Ф���4~15");
				}

				if (nick == null || nick.length() <= 0) {
					errorMsgs.add("�ж�ʺ�");
				} else if (!oknick) {
					errorMsgs.add("�п�J�ŦX����έ^�媺�ʺ�");
				} else if (nick.length() < 1 || nick.length() > 8) {
					errorMsgs.add("�ʺٽФ���έ^���J���פ���1~8�r");
				}

				if (email == null || email.length() <= 0) {
					errorMsgs.add("�ж�E-mail");
				} else if (!okemail) {
					errorMsgs.add("�п�J���TE-mail�榡");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher(requestURL);
					failureView.forward(req, res);
					return;// �{�����_
				}
				/*********************** �@�նüƱK�X���� *************************/
				char[] ranpw = new char[8];
				for (int i = 0; i < 8; i++) {

					int j = (int) Math.floor((Math.random() * 75 + 48));
					if ((j >= 48 && j <= 57) || (j >= 64 && j <= 90)
							|| (i >= 97 && j <= 122)) {
						ranpw[i] = (char) j;
					} else {
						i--;
					}
				}

				String accpw = new String(ranpw);
				/********************** email�H�X ***********************************/
				req.setAttribute("ch_name", acc);
				req.setAttribute("passRandom", accpw);
				req.setAttribute("email", email);
				req.setAttribute("nick", nick);
				req.setAttribute("Subject", "Ź���a�ϫ�ݷ|���b���w�ҥ�");
				req.getRequestDispatcher("/account/JavaMailProccess.jsp")
						.include(req, res);

				/*********************** �s�W�b���ᤩ���v���N�X *************************/
				String[] pernoString = req.getParameterValues("perno");
				boolean haslist = pernoString != null;
				Integer[] perno = null;
				ArrayList<PerListVO> perListVOs = null;
				if (haslist) {
					perno = new Integer[pernoString.length];
					for (int i = 0; i < perno.length; i++) {
						perno[i] = Integer.parseInt(pernoString[i]);
					}
					perListVOs = new ArrayList<PerListVO>();
					for (int i = 0; i < perno.length; i++) {
						PerListVO perListVO = new PerListVO();
						perListVO.setAcc(acc);
						perListVO.setPerno(perno[i]);
						perListVOs.add(perListVO);
					}
				}

				AccountVO accountVO = new AccountVO();
				accountVO.setAcc(acc);
				accountVO.setNick(nick);
				accountVO.setAccpw(accpw);
				accountVO.setEmail(email);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("accountVO", accountVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher(requestURL);
					failureView.forward(req, res);

					return;
				}
				/*************************** 2.�}�l�s�W��� ***************************************/

				accountVO = accSvc.addAcount(acc, accpw, nick, email);// �K�X���üƲ���
				if (haslist) {

					PerListService perSvc = new PerListService();

					for (PerListVO perListVO2 : perListVOs) {
						perSvc.addAccountPermission(perListVO2.getPerno(),
								perListVO2.getAcc());
					}
				}
				/*************************** 3.�s�W�������(Send the Success view) ***********/
				if (requestURL.equals("/account/Account.jsp"))
					req.setAttribute("acc", acc);
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {

				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
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
				/*************************** 1.�����ШD�Ѽ�-���~�B�z **********************/
				String acc = req.getParameter("acc").trim();
				String nick = req.getParameter("nick").trim();
				String email = req.getParameter("email").trim();

				boolean okemail = MatcherData.matchEmail(email);
				boolean okaccount = MatcherData.matchAccount(acc);
				boolean oknick = MatcherData.matchNick(nick);

				AccountService accSvc = new AccountService();

				if (accSvc.getOneAccount(acc) != null) {

					if (acc == null || acc.length() <= 0) {
						errorMsgs.add("�ק���~�Э��s�ק�");
					} else if (!okaccount) {
						errorMsgs.add("���D�k�r���Э��s�ק�");
					}
				}

				if (nick == null || nick.length() <= 0) {
					errorMsgs.add("�ʺ٭ק藍�i���ť�");
				} else if (!oknick) {
					errorMsgs.add("�ʺ٭ק�п�J�ŦX����έ^��榡���ʺ�");
				} else if (nick.length() < 1 || nick.length() > 8) {
					errorMsgs.add("�ʺٽФ���έ^���J���פ���1~8�r");
				}

				if (email == null || email.length() <= 0) {
					errorMsgs.add("�ж�E-mail");
				} else if (!okemail) {
					errorMsgs.add("�п�J���TE-mail�榡");
				}

				String[] pernoString = req.getParameterValues("perno");

				boolean haslist = pernoString != null;
				Integer[] perno = null;
				ArrayList<PerListVO> perListVOs = null;

				if (haslist) {
					perno = new Integer[pernoString.length];
					for (int i = 0; i < perno.length; i++) {
						perno[i] = Integer.parseInt(pernoString[i]);
					}

					perListVOs = new ArrayList<PerListVO>();
					for (int i = 0; i < perno.length; i++) {
						PerListVO perListVO = new PerListVO();
						perListVO.setAcc(acc);
						perListVO.setPerno(perno[i]);
						perListVOs.add(perListVO);
					}
				}
				AccountVO accountVO = new AccountVO();
				accountVO.setAcc(acc);
				accountVO.setNick(nick);
				accountVO.setEmail(email);
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {

					RequestDispatcher failureView = req
							.getRequestDispatcher("/account/Account.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.�}�l�ק��� *****************************************/

				PermissionService permissionSvc = new PermissionService();
				List<PermissionVO> permissionVOs = permissionSvc.getAll();

				PerListService perListSvc = new PerListService();

				Set<PerListVO> oldPerListVOs = null;
				Integer[] oldperno = null;
				int[] OldPermissionState = null;
				int[] NewPermissionState = null;
				boolean oldhaslist = (oldPerListVOs = perListSvc
						.getAllOneAccountPermissionInfo(acc)) != null;

				if (oldhaslist) {
					int count = 0;
					oldPerListVOs = perListSvc
							.getAllOneAccountPermissionInfo(acc);
					oldperno = new Integer[oldPerListVOs.size()];
					for (PerListVO per : oldPerListVOs) {
						oldperno[count] = per.getPerno();
						count++;
					}
					OldPermissionState = new int[permissionVOs.size()];
					for (int i = 0; i < oldperno.length; i++) {
						OldPermissionState[oldperno[i] - 1] = 1;
					}

					if (haslist) {
						NewPermissionState = new int[permissionVOs.size()];
						for (int i = 0; i < perno.length; i++) {
							NewPermissionState[perno[i] - 1] = 1;
						}
						for (int i = 0; i < permissionVOs.size(); i++) {
							if (NewPermissionState[i] == OldPermissionState[i]) {
								continue;
							} else if (NewPermissionState[i] > OldPermissionState[i]) {
								perListSvc.addAccountPermission(i + 1, acc);
							} else if (NewPermissionState[i] < OldPermissionState[i]) {
								perListSvc.deleteAccountPermission(i + 1, acc);
							}
						}

					} else if (!haslist) {
						for (PerListVO list : oldPerListVOs) {
							perListSvc.deleteAccountPermission(list.getPerno(),
									list.getAcc());
						}
					}

				} else if (haslist) {
					for (PerListVO perListVO2 : perListVOs) {
						perListSvc.addAccountPermission(perListVO2.getPerno(),
								perListVO2.getAcc());
					}
				}

				accSvc.updateAcount(acc, nick, email);
				/*************************** 3.�ק粒�����(Send the Success view) *************/
				if (requestURL.equals("/account/Account.jsp"))
					req.setAttribute("acc", acc);
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** ��L���~�B�z *************************************/
			} catch (Exception e) {
				errorMsgs.add("�ק異��" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/account/Account.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			try {
				/*************************** 1.�������-���~�B�z ***************************************/
				String acc = new String(req.getParameter("acc").trim());
				AccountService accSvc = new AccountService();
				boolean okaccount = MatcherData.matchAccount(acc);
				if (accSvc.getOneAccount(acc) != null) {
					if (acc == null || acc.length() <= 0) {
						errorMsgs.add("�R�����~�Э��s���");
					} else if (!okaccount) {
						errorMsgs.add("���D�k�r���Э��s�ʧ@");
					}
				}

				PerListService perSvc = new PerListService();
				/*************************** 2.�}�l�R����� ***************************************/

				Set<PerListVO> perListVOs = null;
				if (perSvc.getAllOneAccountPermissionInfo(acc) != null) {
					perListVOs = perSvc.getAllOneAccountPermissionInfo(acc);
					for (PerListVO perListVO : perListVOs) {
						perSvc.deleteAccountPermission(perListVO.getPerno(),
								perListVO.getAcc());
					}
				}

				accSvc.deleteAccount(acc);
				/*************************** 3.�s�W�R�����(Send the Success view) ***********/

				if (requestURL.equals("/account/Account.jsp"))
					req.setAttribute("acc", acc);
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** ��L���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add("�R������" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/account/Account.jsp");
				failureView.forward(req, res);
			}
		}
		if ("updatePw".equals(action)) {

			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			StringBuffer stringBuffer=new StringBuffer();

			HttpSession session = req.getSession();
			
			AccountVO accountVO = (AccountVO) session.getAttribute("bAccount");
			/****************************** �ШD�Ѽ� ************************************/
			String checkaccpw = req.getParameter("checkaccpw").trim();
			String accpw = req.getParameter("newaccpw").trim();
			
			boolean okaccpw = MatcherData.matchAccpw(accpw);
				
			if (accpw == null || accpw.length() <= 0) {
				stringBuffer.append("new");//�п�J�s�K�X
			} else if (!okaccpw) {
				stringBuffer.append("format");//�п�J�^�ƲV�X�榡
			}else if(accpw.length()>12){
				stringBuffer.append("pwlong");//�K�X�L��
			}else if (checkaccpw == null || checkaccpw.length() <= 0) {
				stringBuffer.append("check");//�ЦA��J�@���s�K�X�i��T�{
			}else if (!accpw.equals(checkaccpw)) {
				stringBuffer.append("different");//�T�{�K�X���@�P�A�ЦA�T�{�z���K�X
			}
			
			
			if (stringBuffer.length()>0) {
				out.write(stringBuffer.toString());
				return;
			}
			/*******************************�T�{�L�~�����***********************************************/

			accountVO.setAccpw(accpw);
			AccountService accountSvc = new AccountService();
	
			
			accountSvc.updateByPw(accountVO);
			out.write("ok");

		}
		
		
		if ("updateEmail".equals(action)) {

			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			StringBuffer stringBuffer=new StringBuffer();

			HttpSession session = req.getSession();
			
			AccountVO accountVO = (AccountVO) session.getAttribute("bAccount");
			/****************************** �ШD�Ѽ� ************************************/
			String email= req.getParameter("email").trim();
			boolean okemail = MatcherData.matchEmail(email);
			
			if(email==null||email.length()<=0){
				stringBuffer.append("null");
			}else if(!okemail){
				stringBuffer.append("format");//�榡���~
			}
								
			if (stringBuffer.length()>0) {
				out.write(stringBuffer.toString());
				return;
			}
			/*******************************�T�{�L�~�����***********************************************/

			accountVO.setEmail(email);
			AccountService accountSvc = new AccountService();
				
			accountSvc.updateByEmail(accountVO);
			out.write("ok");

			}
		
		
		
		
		if ("restaccpw".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				String requestURL = req.getParameter("requestURL").trim();
				String acc = req.getParameter("acc").trim();
				AccountService accountSvc = new AccountService();

				if (acc == null) {
					errorMsgs.add("�b�����~");
				}

				char[] ranpw = new char[8];
				for (int i = 0; i < 8; i++) {

					int j = (int) Math.floor((Math.random() * 75 + 48));
					if ((j >= 48 && j <= 57) || (j >= 64 && j <= 90)
							|| (i >= 97 && j <= 122)) {
						ranpw[i] = (char) j;
					} else {
						i--;
					}
				}
				String accpw = null;

				if (ranpw != null) {
					accpw = new String(ranpw);
				}
				
				AccountVO accountVO = accountSvc.getOneAccount(acc);
				if(accountVO==null){
					errorMsgs.add("��L���b��");
				}
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {

					RequestDispatcher failureView = req
							.getRequestDispatcher("/account/Account.jsp");
					failureView.forward(req, res);
					return;
				}
				/***************************�o�eemail***************************************/
				req.setAttribute("ch_name", accountVO.getAcc());
				req.setAttribute("passRandom", accpw);
				req.setAttribute("email", accountVO.getEmail());
				req.setAttribute("nick", accountVO.getNick());
				req.setAttribute("Subject", "Ź���a�ϫ�ݷ|���b���K�X���]");
				req.getRequestDispatcher("/account/JavaMailProccess.jsp")
						.include(req, res);
				/**************************rest pw update****************************/
				
				accountVO.setAccpw(accpw);
				accountSvc.updateByPw(accountVO);  
				
				/*************************** 3.�ק粒�����(Send the Success view) *************/
				if (requestURL.equals("/account/Account.jsp"))
					req.setAttribute("acc", acc);
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);


				/*************************** ��L���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add("���]����" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/account/Account.jsp");
				failureView.forward(req, res);
			}

		}

	}

}
