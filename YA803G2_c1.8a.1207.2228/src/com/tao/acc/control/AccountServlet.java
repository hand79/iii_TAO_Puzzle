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
				/*********************** 1.接收請求參數-錯誤處理 *************************/
				String acc = req.getParameter("acc").trim();
				String nick = req.getParameter("nick").trim();
				String email = req.getParameter("email").trim();

				boolean okemail = MatcherData.matchEmail(email);
				boolean okaccount = MatcherData.matchAccount(acc);
				boolean oknick = MatcherData.matchNick(nick);

				AccountService accSvc = new AccountService();

				if (accSvc.getOneAccount(acc) != null) {
					errorMsgs.add("已建立過此帳號");
				} else if (acc == null || acc.length() <= 0) {
					errorMsgs.add("請填帳號");
				} else if (!okaccount) {
					errorMsgs.add("請輸入英數混合的帳號格式");
				} else if (acc.length() > 15 || acc.length() < 4) {
					errorMsgs.add("帳號英數混合長度請介於4~15");
				}

				if (nick == null || nick.length() <= 0) {
					errorMsgs.add("請填暱稱");
				} else if (!oknick) {
					errorMsgs.add("請輸入符合中文或英文的暱稱");
				} else if (nick.length() < 1 || nick.length() > 8) {
					errorMsgs.add("暱稱請中文或英文輸入長度介於1~8字");
				}

				if (email == null || email.length() <= 0) {
					errorMsgs.add("請填E-mail");
				} else if (!okemail) {
					errorMsgs.add("請輸入正確E-mail格式");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher(requestURL);
					failureView.forward(req, res);
					return;// 程式中斷
				}
				/*********************** 一組亂數密碼產生 *************************/
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
				/********************** email寄出 ***********************************/
				req.setAttribute("ch_name", acc);
				req.setAttribute("passRandom", accpw);
				req.setAttribute("email", email);
				req.setAttribute("nick", nick);
				req.setAttribute("Subject", "饕飽地圖後端會員帳號已啟用");
				req.getRequestDispatcher("/account/JavaMailProccess.jsp")
						.include(req, res);

				/*********************** 新增帳號賦予的權限代碼 *************************/
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
				/*************************** 2.開始新增資料 ***************************************/

				accountVO = accSvc.addAcount(acc, accpw, nick, email);// 密碼為亂數產生
				if (haslist) {

					PerListService perSvc = new PerListService();

					for (PerListVO perListVO2 : perListVOs) {
						perSvc.addAccountPermission(perListVO2.getPerno(),
								perListVO2.getAcc());
					}
				}
				/*************************** 3.新增完成轉交(Send the Success view) ***********/
				if (requestURL.equals("/account/Account.jsp"))
					req.setAttribute("acc", acc);
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
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
				/*************************** 1.接收請求參數-錯誤處理 **********************/
				String acc = req.getParameter("acc").trim();
				String nick = req.getParameter("nick").trim();
				String email = req.getParameter("email").trim();

				boolean okemail = MatcherData.matchEmail(email);
				boolean okaccount = MatcherData.matchAccount(acc);
				boolean oknick = MatcherData.matchNick(nick);

				AccountService accSvc = new AccountService();

				if (accSvc.getOneAccount(acc) != null) {

					if (acc == null || acc.length() <= 0) {
						errorMsgs.add("修改錯誤請重新修改");
					} else if (!okaccount) {
						errorMsgs.add("有非法字元請重新修改");
					}
				}

				if (nick == null || nick.length() <= 0) {
					errorMsgs.add("暱稱修改不可為空白");
				} else if (!oknick) {
					errorMsgs.add("暱稱修改請輸入符合中文或英文格式的暱稱");
				} else if (nick.length() < 1 || nick.length() > 8) {
					errorMsgs.add("暱稱請中文或英文輸入長度介於1~8字");
				}

				if (email == null || email.length() <= 0) {
					errorMsgs.add("請填E-mail");
				} else if (!okemail) {
					errorMsgs.add("請輸入正確E-mail格式");
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

				/*************************** 2.開始修改資料 *****************************************/

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
				/*************************** 3.修改完成轉交(Send the Success view) *************/
				if (requestURL.equals("/account/Account.jsp"))
					req.setAttribute("acc", acc);
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改失敗" + e.getMessage());
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
				/*************************** 1.接收資料-錯誤處理 ***************************************/
				String acc = new String(req.getParameter("acc").trim());
				AccountService accSvc = new AccountService();
				boolean okaccount = MatcherData.matchAccount(acc);
				if (accSvc.getOneAccount(acc) != null) {
					if (acc == null || acc.length() <= 0) {
						errorMsgs.add("刪除錯誤請重新選擇");
					} else if (!okaccount) {
						errorMsgs.add("有非法字元請重新動作");
					}
				}

				PerListService perSvc = new PerListService();
				/*************************** 2.開始刪除資料 ***************************************/

				Set<PerListVO> perListVOs = null;
				if (perSvc.getAllOneAccountPermissionInfo(acc) != null) {
					perListVOs = perSvc.getAllOneAccountPermissionInfo(acc);
					for (PerListVO perListVO : perListVOs) {
						perSvc.deleteAccountPermission(perListVO.getPerno(),
								perListVO.getAcc());
					}
				}

				accSvc.deleteAccount(acc);
				/*************************** 3.新增刪除轉交(Send the Success view) ***********/

				if (requestURL.equals("/account/Account.jsp"))
					req.setAttribute("acc", acc);
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除失敗" + e.getMessage());
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
			/****************************** 請求參數 ************************************/
			String checkaccpw = req.getParameter("checkaccpw").trim();
			String accpw = req.getParameter("newaccpw").trim();
			
			boolean okaccpw = MatcherData.matchAccpw(accpw);
				
			if (accpw == null || accpw.length() <= 0) {
				stringBuffer.append("new");//請輸入新密碼
			} else if (!okaccpw) {
				stringBuffer.append("format");//請輸入英數混合格式
			}else if(accpw.length()>12){
				stringBuffer.append("pwlong");//密碼過長
			}else if (checkaccpw == null || checkaccpw.length() <= 0) {
				stringBuffer.append("check");//請再輸入一次新密碼進行確認
			}else if (!accpw.equals(checkaccpw)) {
				stringBuffer.append("different");//確認密碼不一致，請再確認您的密碼
			}
			
			
			if (stringBuffer.length()>0) {
				out.write(stringBuffer.toString());
				return;
			}
			/*******************************確認無誤後執行***********************************************/

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
			/****************************** 請求參數 ************************************/
			String email= req.getParameter("email").trim();
			boolean okemail = MatcherData.matchEmail(email);
			
			if(email==null||email.length()<=0){
				stringBuffer.append("null");
			}else if(!okemail){
				stringBuffer.append("format");//格式錯誤
			}
								
			if (stringBuffer.length()>0) {
				out.write(stringBuffer.toString());
				return;
			}
			/*******************************確認無誤後執行***********************************************/

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
					errorMsgs.add("帳號錯誤");
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
					errorMsgs.add("找無此帳號");
				}
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {

					RequestDispatcher failureView = req
							.getRequestDispatcher("/account/Account.jsp");
					failureView.forward(req, res);
					return;
				}
				/***************************發送email***************************************/
				req.setAttribute("ch_name", accountVO.getAcc());
				req.setAttribute("passRandom", accpw);
				req.setAttribute("email", accountVO.getEmail());
				req.setAttribute("nick", accountVO.getNick());
				req.setAttribute("Subject", "饕飽地圖後端會員帳號密碼重設");
				req.getRequestDispatcher("/account/JavaMailProccess.jsp")
						.include(req, res);
				/**************************rest pw update****************************/
				
				accountVO.setAccpw(accpw);
				accountSvc.updateByPw(accountVO);  
				
				/*************************** 3.修改完成轉交(Send the Success view) *************/
				if (requestURL.equals("/account/Account.jsp"))
					req.setAttribute("acc", acc);
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);


				/*************************** 其他錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("重設失敗" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/account/Account.jsp");
				failureView.forward(req, res);
			}

		}

	}

}
