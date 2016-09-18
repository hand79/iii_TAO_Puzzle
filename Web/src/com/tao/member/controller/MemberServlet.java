package com.tao.member.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tao.member.controller.InputChecker;
import com.tao.member.model.MemberService;
import com.tao.member.model.MemberVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.Part;

public class MemberServlet extends HttpServlet {

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		File tempDir = new File(getServletContext().getRealPath("/images"));
		if (!tempDir.exists() || !tempDir.isDirectory()) {
			tempDir.mkdirs();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		String contentType = req.getContentType();

		if (contentType != null && contentType.startsWith("multipart")) {
			MultipartRequest multi = new MultipartRequest(req,
					getServletContext().getRealPath("/images"),
					5 * 1024 * 1024, "UTF-8");
			String action = multi.getParameter("action");

			if ("insert".equals(action)) {
				List<String> errorMsgs = new LinkedList<String>();
				req.setAttribute("errorMsgs", errorMsgs);

				try {
					/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
					String memid = multi.getParameter("memid").trim();
					String mempw = multi.getParameter("mempw").trim();
					String fname = multi.getParameter("fname").trim();
					String lname = multi.getParameter("lname").trim();
					String idnum = multi.getParameter("idnum").trim();
					String gender = multi.getParameter("gender").trim();
					Integer locno = new Integer(multi.getParameter("locno")
							.trim());
					String addr = multi.getParameter("addr").trim();
					String tel = multi.getParameter("tel").trim();
					String email = multi.getParameter("email").trim();
					Integer type = new Integer(multi.getParameter("type")
							.trim());

					// String[] terms = multi.getParameterValues("TERMS");

					File photoFile = multi.getFile("photo");
					FileInputStream in = null;
					byte[] photo = null;
					String mime = null;
					if (photoFile != null) {
						mime = multi.getContentType("photo");
						try {
							in = new FileInputStream(photoFile);
							photo = new byte[in.available()];
							in.read(photo);
							in.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					boolean okmemid = InputChecker.checkId(memid);
					boolean okemail = InputChecker.checkEmail(email);
					boolean okIdNum = InputChecker.checkIdNum(type, idnum);
					boolean okfname = InputChecker.checkNames(fname);
					boolean oklname = InputChecker.checkNames(lname);
					boolean oklocno = InputChecker.checklocno(locno);
					boolean okgender = InputChecker.checkgender(gender);
					boolean oktel = InputChecker.checktel(tel);
					boolean okaddr = InputChecker.checkAddr(addr);
					boolean oktype = InputChecker.checktype(type);

					MemberService memSvc = new MemberService();

					if (memSvc.findByMemberID(memid) != null) {
						errorMsgs.add("已建立過此帳號");
					} else if (memid == null || memid.length() == 0) {
						errorMsgs.add("請輸入帳號");
					} else if (!okmemid) {
						errorMsgs.add("輸入帳號有非法字元");
					} else if (memid.length() > 10 || memid.length() < 3) {
						errorMsgs.add("帳號英文長度請介於3~10");
					}

					if (email == null || email.length() == 0) {
						errorMsgs.add("請填E-mail");
					} else if (!okemail) {
						errorMsgs.add("請輸入正確E-mail格式");
					}

					if (idnum == null || idnum.length() == 0) {
						errorMsgs.add("請填身分證字號 或 統一編號");
					} else if (!okIdNum) {
						errorMsgs.add("請輸入正確 身分證字號 或 統一編號  格式");
					}

					if (fname == null || fname.length() == 0) {
						errorMsgs.add("請填名字");
					} else if (!okfname) {
						errorMsgs.add("請輸入正確 名字 格式");
					}

					if (lname == null || lname.length() == 0) {
						errorMsgs.add("請填名字");
					} else if (!oklname) {
						errorMsgs.add("請輸入正確 姓氏 格式");
					}

					if (mempw == null || mempw.length() == 0) {
						errorMsgs.add("請輸入密碼");
					}

					if (tel == null || tel.length() == 0) {
						errorMsgs.add("請輸入電話號碼");
					} else if (!oktel) {
						errorMsgs
								.add("請輸入正確 電話號碼 格式 例如09XXXXXXXX, 不要放  -,  ( , )  等符號");
					}

					if (addr == null || addr.length() == 0) {
						errorMsgs.add("請輸入地址");
					} else if (!okaddr) {
						errorMsgs.add("請輸入正確 地址 格式");
					}

					if (!oklocno) {
						errorMsgs.add("請輸入正確 區域 格式: 介於1-355之間的數字");
					}

					if (!okgender) {
						errorMsgs.add("請輸入正確 性別 格式: M, F, m, f 擇一");
					}

					if (!oktype) {
						errorMsgs.add("請輸入正確  會元類型 格式: 0, 1  擇一");
					}

					/*
					 * if (terms[0] != "CHECKED"){ errorMsgs.add("請勾選同意使用條款"); }
					 */

					MemberVO memberVO = new MemberVO();
					memberVO.setMemid(memid);
					memberVO.setMempw(mempw);
					memberVO.setFname(fname);
					memberVO.setLname(lname);
					memberVO.setIdnum(idnum);
					memberVO.setGender(gender);
					memberVO.setLocno(locno);
					memberVO.setAddr(addr);
					memberVO.setTel(tel);
					memberVO.setEmail(email);
					memberVO.setPhoto(photo);
					memberVO.setType(type);

					// Send the use back to the form, if there were errors
					if (!errorMsgs.isEmpty()) {
						req.setAttribute("memberVO", memberVO); // 含有輸入格式錯誤的memberVO物件,也存入req
						RequestDispatcher failureView = req
								.getRequestDispatcher("/member/front/memberSignup.jsp");
						failureView.forward(req, res);
						return;
					}

					/*************************** 2.開始新增資料 ***************************************/
					// MemberService memSvc = new MemberService();
					memberVO = memSvc
							.addMember(memid, mempw, fname, lname, idnum,
									gender, locno, addr, tel, email, photo, mime,
									type);

					MemberVO memfindVO = memSvc.findByMemberID(memberVO
							.getMemid());

					/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
						req.setAttribute("user_name", memberVO.getMemid());
						req.setAttribute("user_email", memberVO.getEmail());
						req.setAttribute("user_password", memberVO.getMempw());
						req.setAttribute("user_memno",
								new Integer(memfindVO.getMemno()));
						req.setCharacterEncoding("UTF-8");
						res.setCharacterEncoding("UTF-8");
						RequestDispatcher emaildispatch = req
								.getRequestDispatcher("/JavaMailProccess.jsp");
						emaildispatch.include(req, res);

						RequestDispatcher successView = req
								.getRequestDispatcher("/member/front/memberSignupResult.jsp");
						successView.forward(req, res);

					/*************************** 其他可能的錯誤處理 **********************************/
				} catch (Exception e) {
					errorMsgs.add(e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/member/front/memberSignup.jsp");
					failureView.forward(req, res);
				}
			} // end of insert statement

			if ("insert_by_shop".equals(action)) {
				List<String> errorMsgs = new LinkedList<String>();
				req.setAttribute("errorMsgs", errorMsgs);

				try {
					/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
					String memid = multi.getParameter("memid").trim();
					String mempw = multi.getParameter("mempw").trim();
					String fname = multi.getParameter("fname").trim();
					String lname = multi.getParameter("lname").trim();
					String idnum = multi.getParameter("idnum").trim();
					String gender = multi.getParameter("gender").trim();
					Integer locno = new Integer(multi.getParameter("locno")
							.trim());
					String addr = multi.getParameter("addr").trim();
					String tel = multi.getParameter("tel").trim();
					String email = multi.getParameter("email").trim();
					Integer type = new Integer(multi.getParameter("type")
							.trim());

					// String[] terms = multi.getParameterValues("TERMS");

					File photoFile = multi.getFile("photo");
					FileInputStream in = null;
					byte[] photo = null;
					String mime = null;
					if(photoFile != null){
						mime = multi.getContentType("photo");
						try {
							in = new FileInputStream(photoFile);
							photo = new byte[in.available()];
							in.read(photo);
							photoFile.delete();
							in.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					boolean okmemid = InputChecker.checkId(memid);
					boolean okemail = InputChecker.checkEmail(email);
					boolean okIdNum = InputChecker.checkIdNum(type, idnum);
					boolean okfname = InputChecker.checkNames(fname);
					boolean oklname = InputChecker.checkNames(lname);
					boolean oklocno = InputChecker.checklocno(locno);
					boolean okgender = InputChecker.checkgender(gender);
					boolean oktel = InputChecker.checktel(tel);
					boolean okaddr = InputChecker.checkAddr(addr);
					boolean oktype = InputChecker.checktype(type);

					MemberService memSvc = new MemberService();

					if (memSvc.findByMemberID(memid) != null) {
						errorMsgs.add("已建立過此帳號");
					} else if (memid == null || memid.length() == 0) {
						errorMsgs.add("請輸入帳號");
					} else if (!okmemid) {
						errorMsgs.add("輸入帳號有非法字元");
					} else if (memid.length() > 10 || memid.length() < 3) {
						errorMsgs.add("帳號英文長度請介於3~10");
					}

					if (email == null || email.length() == 0) {
						errorMsgs.add("請填E-mail");
					} else if (!okemail) {
						errorMsgs.add("請輸入正確E-mail格式");
					}

					if (idnum == null || idnum.length() == 0) {
						errorMsgs.add("請填 統一編號");
					} else if (!okIdNum) {
						errorMsgs.add("請輸入正確 統一編號 格式");
					}

					if (fname == null || fname.length() == 0) {
						errorMsgs.add("請填名字");
					} else if (!okfname) {
						errorMsgs.add("請輸入正確 名字 格式");
					}

					if (lname == null || lname.length() == 0) {
						errorMsgs.add("請填名字");
					} else if (!oklname) {
						errorMsgs.add("請輸入正確 姓氏 格式");
					}

					if (mempw == null || mempw.length() == 0) {
						errorMsgs.add("請輸入密碼");
					}

					if (tel == null || tel.length() == 0) {
						errorMsgs.add("請輸入電話號碼");
					} else if (!oktel) {
						errorMsgs
								.add("請輸入正確 電話號碼 格式 例如09XXXXXXXX, 不要放  -,  ( , )  等符號");
					}

					if (addr == null || addr.length() == 0) {
						errorMsgs.add("請輸入地址");
					} else if (!okaddr) {
						errorMsgs.add("請輸入正確 地址 格式");
					}

					if (!oklocno) {
						errorMsgs.add("請輸入正確 區域 格式: 介於1-355之間的數字");
					}

					if (!okgender) {
						errorMsgs.add("請輸入正確 性別 格式: M, F, m, f 擇一");
					}

					if (!oktype) {
						errorMsgs.add("請輸入正確 性別 格式: 0, 1  擇一");
					}

					MemberVO memberVO = new MemberVO();
					memberVO.setMemid(memid);
					memberVO.setMempw(mempw);
					memberVO.setFname(fname);
					memberVO.setLname(lname);
					memberVO.setIdnum(idnum);
					memberVO.setGender(gender);
					memberVO.setLocno(locno);
					memberVO.setAddr(addr);
					memberVO.setTel(tel);
					memberVO.setEmail(email);
					memberVO.setPhoto(photo);
					memberVO.setType(type);

					// Send the use back to the form, if there were errors
					if (!errorMsgs.isEmpty()) {
						req.setAttribute("memberVO", memberVO); // 含有輸入格式錯誤的memberVO物件,也存入req
						RequestDispatcher failureView = req
								.getRequestDispatcher("/member/front/memberSignupForShop.jsp");
						failureView.forward(req, res);
						return;
					}

					/*************************** 2.開始新增資料 ***************************************/
					// MemberService memSvc = new MemberService();
					memberVO = memSvc
							.addMember(memid, mempw, fname, lname, idnum,
									gender, locno, addr, tel, email, photo, mime,
									type);

					/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
					if (memberVO != null) {
						String url = "/member/front/memberSignupResultForShop.jsp";
						RequestDispatcher successView = req
								.getRequestDispatcher(url);
						successView.forward(req, res);
					} else {
						errorMsgs.add("新會員資料新增失敗, 請檢查內容.");
						RequestDispatcher failureView = req
								.getRequestDispatcher("/member/front/memberSignupForShop.jsp");
						failureView.forward(req, res);
						return;
					}

					/*************************** 其他可能的錯誤處理 **********************************/
				} catch (Exception e) {
					errorMsgs.add(e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/member/front/memberSignupForShop.jsp");
					failureView.forward(req, res);
				}
			} // end of insert_by_shop statement

			if ("update_front".equals(action)) {

				List<String> errorMsgs = new LinkedList<String>();
				// Store this set in the request scope, in case we need to
				// send the ErrorPage view.
				req.setAttribute("errorMsgs", errorMsgs);
				boolean isPhotoOK;
				FileInputStream in = null;
				byte[] photo = null;

				try {
					/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/

					String memnoStr = multi.getParameter("memno");
					Integer memno = null;

					if (memnoStr == null || (memnoStr.trim()).length() == 0) {
						errorMsgs.add("請選擇會員");
					} else {
						memno = new Integer(memnoStr);
					}

					String mempw = multi.getParameter("mempw").trim();
					String fname = multi.getParameter("fname").trim();
					String lname = multi.getParameter("lname").trim();
					Integer locno = new Integer(multi.getParameter("locno")
							.trim());
					String addr = multi.getParameter("addr").trim();
					String tel = multi.getParameter("tel").trim();
					String email = multi.getParameter("email").trim();

					File photoFile = multi.getFile("photo");
					if (photoFile == null || (photoFile.length() == 0)) {
						isPhotoOK = false;
					} else {
						isPhotoOK = true;

						try {
							in = new FileInputStream(photoFile);
							photo = new byte[in.available()];
							in.read(photo);
							photoFile.delete();
							in.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					String mime = multi.getContentType("photo");

					boolean okemail = InputChecker.checkEmail(email);
					boolean okfname = InputChecker.checkNames(fname);
					boolean oklname = InputChecker.checkNames(lname);

					boolean oklocno = InputChecker.checklocno(locno);
					boolean okaddr = InputChecker.checkAddr(addr);
					boolean oktel = InputChecker.checktel(tel);

					if (email == null || email.length() == 0) {
						errorMsgs.add("請填E-mail");
					} else if (!okemail) {
						errorMsgs.add("請輸入正確E-mail格式");
					}

					if (fname == null || fname.length() == 0) {
						errorMsgs.add("請填名字");
					} else if (!okfname) {
						errorMsgs.add("請輸入正確 名字 格式");
					}

					if (lname == null || lname.length() == 0) {
						errorMsgs.add("請填名字");
					} else if (!oklname) {
						errorMsgs.add("請輸入正確 姓氏 格式");
					}

					if (mempw == null || mempw.length() == 0) {
						errorMsgs.add("請輸入密碼");
					}

					if (tel == null || tel.length() == 0) {
						errorMsgs.add("請輸入電話號碼");
					} else if (!oktel) {
						errorMsgs
								.add("請輸入正確 電話號碼 格式 例如09XXXXXXXX, 不要放  -,  ( , )  等符號");
					}

					if (addr == null || addr.length() == 0) {
						errorMsgs.add("請輸入地址");
					} else if (!okaddr) {
						errorMsgs.add("請輸入正確 地址 格式");
					}

					if (!oklocno) {
						errorMsgs.add("請輸入正確 區域 格式: 介於1-355之間的數字");
					}

					MemberVO memberVO = new MemberVO();
					memberVO.setMemno(memno);
					memberVO.setMempw(mempw);
					memberVO.setFname(fname);
					memberVO.setLname(lname);
					memberVO.setLocno(locno);
					memberVO.setAddr(addr);
					memberVO.setTel(tel);
					memberVO.setEmail(email);
					memberVO.setPhoto(photo);

					// Send the use back to the form, if there were errors
					if (!errorMsgs.isEmpty()) {
						req.setAttribute("memberVO", memberVO); // 含有輸入格式錯誤的empVO物件,也存入req
						RequestDispatcher failureView = req
								.getRequestDispatcher("/member/front/UpdateMemberInfo.jsp");
						failureView.forward(req, res);
						return; // 程式中斷
					}

					/*************************** 2.開始修改資料 *****************************************/

					MemberService memSvc = new MemberService();
					if (isPhotoOK) {
						memberVO = memSvc.updateFrontWithPhoto(mempw, fname,
								lname, locno, addr, tel, email, photo, mime,
								memno);
					} else {
						memberVO = memSvc.update_Front_Member(mempw, fname,
								lname, locno, addr, tel, email, memno);
					}

					/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
					if (memberVO != null) {
						HttpSession session = req.getSession();
						memberVO = memSvc.findByPrimaryKey(memno);
						session.setAttribute("CurrentUser", memberVO);
					} else {
						errorMsgs.add("刪除資料失敗");

					}
					RequestDispatcher failureView = req
							.getRequestDispatcher("/MemberCenter");
					failureView.forward(req, res);

					/*************************** 其他可能的錯誤處理 *************************************/
				} catch (Exception e) {
					errorMsgs.add("修改資料失敗:" + e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/member/front/UpdateMemberInfo.jsp");
					failureView.forward(req, res);
				}
			} // end of update statement

		} else {

			req.setCharacterEncoding("UTF-8");
			String action = req.getParameter("action");

			if ("getOne_For_Update".equals(action)) {

				List<String> errorMsgs = new LinkedList<String>();
				// Store this set in the request scope, in case we need to
				// send the ErrorPage view.
				req.setAttribute("errorMsgs", errorMsgs);

				try {
					/*************************** 1.接收請求參數 ****************************************/
					String memnoStr = req.getParameter("memno");
					Integer memno = null;

					if (memnoStr == null || (memnoStr.trim()).length() == 0) {
						errorMsgs.add("請選擇會員");
					} else {
						memno = new Integer(memnoStr);
					}

					if (!errorMsgs.isEmpty()) {
						RequestDispatcher failureView = req
								.getRequestDispatcher("/member/back/backMemberUpdateInput.jsp");
						failureView.forward(req, res);
					}

					/*************************** 2.開始查詢資料 ****************************************/
					MemberService memSvc = new MemberService();
					MemberVO memberVO = null;
					if (memSvc.findByPrimaryKey(memno) != null) {
						memberVO = memSvc.findByPrimaryKey(memno);
					} else {
						System.out.println("memno沒有找到相對應memberVO");
					}

					/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
					if (memberVO != null) {
						req.setAttribute("memberVO", memberVO); // 資料庫取出的empVO物件,存入req
						String url = "/member/back/backMemberUpdateInput.jsp";
						RequestDispatcher successView = req
								.getRequestDispatcher(url);
						successView.forward(req, res);
					} else {
						System.out.println("memno沒有找到相對應memberVO");
					}

					/*************************** 其他可能的錯誤處理 **********************************/
				} catch (Exception e) {
					errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/member/back/backMemberUpdateInput.jsp");
					failureView.forward(req, res);
				}
			} // end of getOne_For_Update statement

			if ("getOne_For_Display".equals(action)) {

				List<String> errorMsgs = new LinkedList<String>();
				// Store this set in the request scope, in case we need to
				// send the ErrorPage view.
				req.setAttribute("errorMsgs", errorMsgs);

				try {
					/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
					String str = req.getParameter("memno");
					if (str == null || (str.trim()).length() == 0) {
						errorMsgs.add("請選擇會員");
					}

					Integer memno = null;
					try {
						memno = new Integer(str);
					} catch (Exception e) {
						errorMsgs.add("員工編號格式不正確");
					}
					// Send the use back to the form, if there were errors
					if (!errorMsgs.isEmpty()) {
						RequestDispatcher failureView = req
								.getRequestDispatcher("/member/back/backMemberManage.jsp");
						failureView.forward(req, res);
						return;// 程式中斷
					}

					/*************************** 2.開始查詢資料 *****************************************/
					MemberService memSvc = new MemberService();
					MemberVO memberVO = memSvc.findByPrimaryKey(memno);
					if (memberVO == null) {
						errorMsgs.add("查無資料");
					}
					// Send the use back to the form, if there were errors
					if (!errorMsgs.isEmpty()) {
						RequestDispatcher failureView = req
								.getRequestDispatcher("/member/back/backMemberManage.jsp");
						failureView.forward(req, res);
						return;// 程式中斷
					}

					/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
					req.setAttribute("memberVO", memberVO); // 資料庫取出的memberVO物件,存入req
					String url = "listOneEmp.jsp";
					RequestDispatcher successView = req
							.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
					successView.forward(req, res);

					/*************************** 其他可能的錯誤處理 *************************************/
				} catch (Exception e) {
					errorMsgs.add("無法取得資料:" + e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("select_page.jsp");
					failureView.forward(req, res);
				}
			} // end of getOne_For_Display statement

			if ("delete".equals(action)) { // 來自listAllMember.jsp

				List<String> errorMsgs = new LinkedList<String>();
				// Store this set in the request scope, in case we need to
				// send the ErrorPage view.
				req.setAttribute("errorMsgs", errorMsgs);

				try {
					/*************************** 1.接收請求參數 ***************************************/
					String str = req.getParameter("memno");
					if (str == null || (str.trim()).length() == 0) {
						errorMsgs.add("請選擇會員");
					}

					Integer memno = null;
					try {
						memno = new Integer(str);
					} catch (Exception e) {
						errorMsgs.add("員工編號格式不正確");
					}
					// Send the use back to the form, if there were errors
					if (!errorMsgs.isEmpty()) {
						RequestDispatcher failureView = req
								.getRequestDispatcher("/member/back/backMemberManage.jsp");
						failureView.forward(req, res);
						return;// 程式中斷
					}

					/*************************** 2.開始刪除資料 ***************************************/
					MemberService memSvc = new MemberService();
					memSvc.delete(memno);

					/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
					String url = "/member/back/backMemberListAll.jsp";
					RequestDispatcher successView = req
							.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
					successView.forward(req, res);

					/*************************** 其他可能的錯誤處理 **********************************/
				} catch (Exception e) {
					errorMsgs.add("刪除資料失敗:" + e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/member/back/backMemberManage.jsp");
					failureView.forward(req, res);
				}
			} // end of delete statement

			if ("update_back".equals(action)) { // 來自update_mem_input.jsp的請求

				List<String> errorMsgs = new LinkedList<String>();
				// Store this set in the request scope, in case we need to
				// send the ErrorPage view.
				req.setAttribute("errorMsgs", errorMsgs);

				try {
					/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/

					String memnoStr = req.getParameter("memno");
					Integer memno = null;

					if (memnoStr == null || (memnoStr.trim()).length() == 0) {
						errorMsgs.add("請選擇會員");
					} else {
						memno = new Integer(memnoStr);
					}

					String statusStr = req.getParameter("status");
					Integer status = null;

					if (statusStr == null || (statusStr.trim()).length() == 0) {
						errorMsgs.add("請輸入狀態");
					} else {
						status = new Integer(statusStr);
					}

					// Integer status = new
					// Integer(req.getParameter("status").trim());

					/*
					 * String requestURLStr = req.getParameter("requestURL");
					 * Integer requestURL = null;
					 * 
					 * if (requestURLStr == null ||
					 * (requestURLStr.trim()).length() == 0) {
					 * errorMsgs.add("請輸入狀態"); } else { requestURL = new
					 * Integer(requestURLStr); }
					 * 
					 * String whichPageStr = req.getParameter("whichPage");
					 * Integer whichPage = null;
					 * 
					 * if (whichPageStr == null ||
					 * (whichPageStr.trim()).length() == 0) {
					 * errorMsgs.add("請輸入狀態"); } else { whichPage = new
					 * Integer(whichPageStr); }
					 */

					String requestURL = req.getParameter("requestURL").trim();
					String whichPage = req.getParameter("whichPage").trim();

					MemberVO memberVO = new MemberVO();
					memberVO.setMemno(memno);
					memberVO.setStatus(status);

					// Send the use back to the form, if there were errors
					if (!errorMsgs.isEmpty()) {
						req.setAttribute("memberVO", memberVO); // 含有輸入格式錯誤的empVO物件,也存入req
						RequestDispatcher failureView = req
								.getRequestDispatcher("/member/back/backMemberManage.jsp");
						failureView.forward(req, res);
						return; // 程式中斷
					}

					/*************************** 2.開始修改資料 *****************************************/

					MemberService memSvc = new MemberService();
					memberVO = memSvc.update_Back_Member(status, memno);

					/*************************** 3.修改完成,準備轉交(Send the Success view) *************/

					if (memberVO != null) {

						MemberVO msgVO = memSvc.findByPrimaryKey(memno);
						Send se = new Send();
						String[] tel = { "0988133364" };
						// String[] tel = {msgVO.getTel()};
						if (status == 1) {
							String message = "親愛的'饕飽地圖'會員您好. 您的帳號已被啟用!";
							se.sendMessage(tel, message);
						} else if (status == 2) {
							String message = "親愛的'饕飽地圖'會員您好. 您的帳號已被停用!";
							se.sendMessage(tel, message);
						}

						Map<String, String[]> map = (Map<String, String[]>) req
								.getSession().getAttribute("map");
						if (map != null) {
							List<MemberVO> list = memSvc.getAll(map);
							String[] typeAry = map.get("type");
							String[] statusAry = map.get("status");
							req.setAttribute("oldType",
									Integer.parseInt(typeAry[0])); // 資料庫取出的list物件,存入request
							req.setAttribute("oldStatus",
									Integer.parseInt(statusAry[0])); // 資料庫取出的list物件,存入request
							req.setAttribute("list", list); // 資料庫update成功後,正確的的memberVO物件,存入req
						}

						String url = "/member/back/backMemberManage.jsp?whichPage="
								+ whichPage
								+ "&memno="
								+ memno
								+ "&requestURL=" + requestURL;
						RequestDispatcher successView = req
								.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
						successView.forward(req, res);
					} else {
						errorMsgs.add("修改資料失敗");
						RequestDispatcher failureView = req
								.getRequestDispatcher("/member/back/backMemberUpdateInput.jsp");
						failureView.forward(req, res);
					}

					/*************************** 其他可能的錯誤處理 *************************************/
				} catch (Exception e) {
					errorMsgs.add("修改資料失敗:" + e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/member/back/backMemberUpdateInput.jsp");
					failureView.forward(req, res);
				}
			} // end of update_back statement

			if ("listMemRS_ByCompositeQuery".equals(action)) { // 來自select_page.jsp的複合查詢請求
				List<String> errorMsgs = new LinkedList<String>();
				// Store this set in the request scope, in case we need to
				// send the ErrorPage view.
				req.setAttribute("errorMsgs", errorMsgs);

				try {

					String typeStr = req.getParameter("type");
					Integer type = null;
					if (typeStr == null || (typeStr.trim()).length() == 0) {
						System.out.println("type not choosen, select all type");
					} else {
						type = new Integer(typeStr);
					}

					String statusStr = req.getParameter("status");
					Integer status = null;
					if (statusStr == null || (statusStr.trim()).length() == 0) {
						System.out
								.println("status not choosen, select all status");
					} else {
						status = new Integer(statusStr);
					}

					/*
					 * String memnoStr = req.getParameter("memno"); Integer
					 * memno = null; if (statusStr == null ||
					 * (statusStr.trim()).length() == 0) {
					 * System.out.println("status not choosen, select all status"
					 * ); } else { memno = new Integer(memnoStr); }
					 */

					// Integer type = new
					// Integer(req.getParameter("type").trim());
					// Integer status = new
					// Integer(req.getParameter("status").trim());
					String whichPage = req.getParameter("whichPage");

					/*************************** 1.將輸入資料轉為Map **********************************/
					// 採用Map<String,String[]> getParameterMap()的方法
					// 注意:an immutable java.util.Map
					// Map<String, String[]> map = req.getParameterMap();
					HttpSession session = req.getSession();
					Map<String, String[]> map = (Map<String, String[]>) session
							.getAttribute("map");
					if (req.getParameter("whichPage") == null
							|| "1".equals(req.getParameter("whichPage"))) {
						HashMap<String, String[]> map1 = (HashMap<String, String[]>) req
								.getParameterMap();
						HashMap<String, String[]> map2 = new HashMap<String, String[]>();
						map2 = (HashMap<String, String[]>) map1.clone();
						session.setAttribute("map", map2);
						map = (HashMap<String, String[]>) req.getParameterMap();
					}

					/*************************** 2.開始複合查詢 ***************************************/
					MemberService memSvc = new MemberService();
					List<MemberVO> list = memSvc.getAll(map);
					/*
					 * EmpService empSvc = new EmpService(); List<EmpVO> list =
					 * empSvc.getAll(map);
					 */

					/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
					req.setAttribute("list", list); // 資料庫取出的list物件,存入request
					if (type != null) {
						req.setAttribute("oldType", type); // 資料庫取出的list物件,存入request
					}
					if (status != null) {
						req.setAttribute("oldStatus", status); // 資料庫取出的list物件,存入request
					}
					/*
					 * if (memno!=null){ req.setAttribute("oldMemno", memno); //
					 * 資料庫取出的list物件,存入request }
					 */

					RequestDispatcher successView = req
							.getRequestDispatcher("/member/back/backMemberManage.jsp"); // 成功轉交listEmps_ByCompositeQuery.jsp
					successView.forward(req, res);
					return;
					/*************************** 其他可能的錯誤處理 **********************************/
				} catch (Exception e) {
					errorMsgs.add(e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/member/back/backMemberManage.jsp");
					failureView.forward(req, res);
				}
			} // end of listMemRS_ByCompositeQuery statement

			if ("member_activate".equals(action)) { // 來自update_mem_input.jsp的請求

				List<String> errorMsgs = new LinkedList<String>();
				// Store this set in the request scope, in case we need to
				// send the ErrorPage view.
				req.setAttribute("errorMsgs", errorMsgs);

				List<String> successMsgs = new LinkedList<String>();
				// Store this set in the request scope, in case we need to
				// send the ErrorPage view.
				req.setAttribute("successMsgs", successMsgs);

				try {
					/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/

					String memnoStr = req.getParameter("memno");
					Integer memno = null;

					if (memnoStr == null || (memnoStr.trim()).length() == 0) {
						errorMsgs.add("啟動失敗, 請重新註冊或者聯絡管理人員");
					} else {
						memno = new Integer(memnoStr);
					}

					String statusStr = req.getParameter("status");
					Integer status = null;

					if (statusStr == null || (statusStr.trim()).length() == 0) {
						errorMsgs.add("啟動失敗, 請重新註冊或者聯絡管理人員");
					} else {
						status = new Integer(statusStr);
						if (status != 1) {
							errorMsgs.add("啟動失敗, 請重新註冊或者聯絡管理人員");
						}
					}

					MemberService memSvc = new MemberService();
					MemberVO memFoundVO = memSvc.findByPrimaryKey(memno);

					if (memFoundVO.getStatus() != 0) {
						errorMsgs.add("本帳號已經啟動, 請登入或者註冊新帳號");
					}

					MemberVO memberVO = new MemberVO();
					memberVO.setMemno(memno);
					memberVO.setStatus(status);

					// Send the use back to the form, if there were errors
					if (!errorMsgs.isEmpty()) {
						RequestDispatcher failureView = req
								.getRequestDispatcher("/member/front/memberSignup.jsp");
						failureView.forward(req, res);
						return; // 程式中斷
					}

					/*************************** 2.開始修改資料 *****************************************/

					// MemberService memSvc = new MemberService();
					memberVO = memSvc.update_Back_Member(status, memno);

					/*************************** 3.修改完成,準備轉交(Send the Success view) *************/

					if (memberVO != null) {
						successMsgs.add("恭喜啟動成功, 請登入會員帳號與密碼");
						RequestDispatcher successView = req
								.getRequestDispatcher("/member/front/memberLogin.jsp");
						successView.forward(req, res);
					}

					/*************************** 其他可能的錯誤處理 *************************************/
				} catch (Exception e) {
					errorMsgs.add("修改資料失敗:" + e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/member/back/backMemberUpdateInput.jsp");
					failureView.forward(req, res);
				}
			} // end of member_activate statement

		} // end of content if statement
	} // end of doPost statement

} // end of MemberServlet statement
