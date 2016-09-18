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
					/*********************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z *************************/
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
						errorMsgs.add("�w�إ߹L���b��");
					} else if (memid == null || memid.length() == 0) {
						errorMsgs.add("�п�J�b��");
					} else if (!okmemid) {
						errorMsgs.add("��J�b�����D�k�r��");
					} else if (memid.length() > 10 || memid.length() < 3) {
						errorMsgs.add("�b���^����׽Ф���3~10");
					}

					if (email == null || email.length() == 0) {
						errorMsgs.add("�ж�E-mail");
					} else if (!okemail) {
						errorMsgs.add("�п�J���TE-mail�榡");
					}

					if (idnum == null || idnum.length() == 0) {
						errorMsgs.add("�ж񨭤��Ҧr�� �� �Τ@�s��");
					} else if (!okIdNum) {
						errorMsgs.add("�п�J���T �����Ҧr�� �� �Τ@�s��  �榡");
					}

					if (fname == null || fname.length() == 0) {
						errorMsgs.add("�ж�W�r");
					} else if (!okfname) {
						errorMsgs.add("�п�J���T �W�r �榡");
					}

					if (lname == null || lname.length() == 0) {
						errorMsgs.add("�ж�W�r");
					} else if (!oklname) {
						errorMsgs.add("�п�J���T �m�� �榡");
					}

					if (mempw == null || mempw.length() == 0) {
						errorMsgs.add("�п�J�K�X");
					}

					if (tel == null || tel.length() == 0) {
						errorMsgs.add("�п�J�q�ܸ��X");
					} else if (!oktel) {
						errorMsgs
								.add("�п�J���T �q�ܸ��X �榡 �Ҧp09XXXXXXXX, ���n��  -,  ( , )  ���Ÿ�");
					}

					if (addr == null || addr.length() == 0) {
						errorMsgs.add("�п�J�a�}");
					} else if (!okaddr) {
						errorMsgs.add("�п�J���T �a�} �榡");
					}

					if (!oklocno) {
						errorMsgs.add("�п�J���T �ϰ� �榡: ����1-355�������Ʀr");
					}

					if (!okgender) {
						errorMsgs.add("�п�J���T �ʧO �榡: M, F, m, f �ܤ@");
					}

					if (!oktype) {
						errorMsgs.add("�п�J���T  �|������ �榡: 0, 1  �ܤ@");
					}

					/*
					 * if (terms[0] != "CHECKED"){ errorMsgs.add("�ФĿ�P�N�ϥα���"); }
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
						req.setAttribute("memberVO", memberVO); // �t����J�榡���~��memberVO����,�]�s�Jreq
						RequestDispatcher failureView = req
								.getRequestDispatcher("/member/front/memberSignup.jsp");
						failureView.forward(req, res);
						return;
					}

					/*************************** 2.�}�l�s�W��� ***************************************/
					// MemberService memSvc = new MemberService();
					memberVO = memSvc
							.addMember(memid, mempw, fname, lname, idnum,
									gender, locno, addr, tel, email, photo, mime,
									type);

					MemberVO memfindVO = memSvc.findByMemberID(memberVO
							.getMemid());

					/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
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

					/*************************** ��L�i�઺���~�B�z **********************************/
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
					/*********************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z *************************/
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
						errorMsgs.add("�w�إ߹L���b��");
					} else if (memid == null || memid.length() == 0) {
						errorMsgs.add("�п�J�b��");
					} else if (!okmemid) {
						errorMsgs.add("��J�b�����D�k�r��");
					} else if (memid.length() > 10 || memid.length() < 3) {
						errorMsgs.add("�b���^����׽Ф���3~10");
					}

					if (email == null || email.length() == 0) {
						errorMsgs.add("�ж�E-mail");
					} else if (!okemail) {
						errorMsgs.add("�п�J���TE-mail�榡");
					}

					if (idnum == null || idnum.length() == 0) {
						errorMsgs.add("�ж� �Τ@�s��");
					} else if (!okIdNum) {
						errorMsgs.add("�п�J���T �Τ@�s�� �榡");
					}

					if (fname == null || fname.length() == 0) {
						errorMsgs.add("�ж�W�r");
					} else if (!okfname) {
						errorMsgs.add("�п�J���T �W�r �榡");
					}

					if (lname == null || lname.length() == 0) {
						errorMsgs.add("�ж�W�r");
					} else if (!oklname) {
						errorMsgs.add("�п�J���T �m�� �榡");
					}

					if (mempw == null || mempw.length() == 0) {
						errorMsgs.add("�п�J�K�X");
					}

					if (tel == null || tel.length() == 0) {
						errorMsgs.add("�п�J�q�ܸ��X");
					} else if (!oktel) {
						errorMsgs
								.add("�п�J���T �q�ܸ��X �榡 �Ҧp09XXXXXXXX, ���n��  -,  ( , )  ���Ÿ�");
					}

					if (addr == null || addr.length() == 0) {
						errorMsgs.add("�п�J�a�}");
					} else if (!okaddr) {
						errorMsgs.add("�п�J���T �a�} �榡");
					}

					if (!oklocno) {
						errorMsgs.add("�п�J���T �ϰ� �榡: ����1-355�������Ʀr");
					}

					if (!okgender) {
						errorMsgs.add("�п�J���T �ʧO �榡: M, F, m, f �ܤ@");
					}

					if (!oktype) {
						errorMsgs.add("�п�J���T �ʧO �榡: 0, 1  �ܤ@");
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
						req.setAttribute("memberVO", memberVO); // �t����J�榡���~��memberVO����,�]�s�Jreq
						RequestDispatcher failureView = req
								.getRequestDispatcher("/member/front/memberSignupForShop.jsp");
						failureView.forward(req, res);
						return;
					}

					/*************************** 2.�}�l�s�W��� ***************************************/
					// MemberService memSvc = new MemberService();
					memberVO = memSvc
							.addMember(memid, mempw, fname, lname, idnum,
									gender, locno, addr, tel, email, photo, mime,
									type);

					/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
					if (memberVO != null) {
						String url = "/member/front/memberSignupResultForShop.jsp";
						RequestDispatcher successView = req
								.getRequestDispatcher(url);
						successView.forward(req, res);
					} else {
						errorMsgs.add("�s�|����Ʒs�W����, ���ˬd���e.");
						RequestDispatcher failureView = req
								.getRequestDispatcher("/member/front/memberSignupForShop.jsp");
						failureView.forward(req, res);
						return;
					}

					/*************************** ��L�i�઺���~�B�z **********************************/
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
					/*************************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z **********************/

					String memnoStr = multi.getParameter("memno");
					Integer memno = null;

					if (memnoStr == null || (memnoStr.trim()).length() == 0) {
						errorMsgs.add("�п�ܷ|��");
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
						errorMsgs.add("�ж�E-mail");
					} else if (!okemail) {
						errorMsgs.add("�п�J���TE-mail�榡");
					}

					if (fname == null || fname.length() == 0) {
						errorMsgs.add("�ж�W�r");
					} else if (!okfname) {
						errorMsgs.add("�п�J���T �W�r �榡");
					}

					if (lname == null || lname.length() == 0) {
						errorMsgs.add("�ж�W�r");
					} else if (!oklname) {
						errorMsgs.add("�п�J���T �m�� �榡");
					}

					if (mempw == null || mempw.length() == 0) {
						errorMsgs.add("�п�J�K�X");
					}

					if (tel == null || tel.length() == 0) {
						errorMsgs.add("�п�J�q�ܸ��X");
					} else if (!oktel) {
						errorMsgs
								.add("�п�J���T �q�ܸ��X �榡 �Ҧp09XXXXXXXX, ���n��  -,  ( , )  ���Ÿ�");
					}

					if (addr == null || addr.length() == 0) {
						errorMsgs.add("�п�J�a�}");
					} else if (!okaddr) {
						errorMsgs.add("�п�J���T �a�} �榡");
					}

					if (!oklocno) {
						errorMsgs.add("�п�J���T �ϰ� �榡: ����1-355�������Ʀr");
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
						req.setAttribute("memberVO", memberVO); // �t����J�榡���~��empVO����,�]�s�Jreq
						RequestDispatcher failureView = req
								.getRequestDispatcher("/member/front/UpdateMemberInfo.jsp");
						failureView.forward(req, res);
						return; // �{�����_
					}

					/*************************** 2.�}�l�ק��� *****************************************/

					MemberService memSvc = new MemberService();
					if (isPhotoOK) {
						memberVO = memSvc.updateFrontWithPhoto(mempw, fname,
								lname, locno, addr, tel, email, photo, mime,
								memno);
					} else {
						memberVO = memSvc.update_Front_Member(mempw, fname,
								lname, locno, addr, tel, email, memno);
					}

					/*************************** 3.�ק粒��,�ǳ����(Send the Success view) *************/
					if (memberVO != null) {
						HttpSession session = req.getSession();
						memberVO = memSvc.findByPrimaryKey(memno);
						session.setAttribute("CurrentUser", memberVO);
					} else {
						errorMsgs.add("�R����ƥ���");

					}
					RequestDispatcher failureView = req
							.getRequestDispatcher("/MemberCenter");
					failureView.forward(req, res);

					/*************************** ��L�i�઺���~�B�z *************************************/
				} catch (Exception e) {
					errorMsgs.add("�ק��ƥ���:" + e.getMessage());
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
					/*************************** 1.�����ШD�Ѽ� ****************************************/
					String memnoStr = req.getParameter("memno");
					Integer memno = null;

					if (memnoStr == null || (memnoStr.trim()).length() == 0) {
						errorMsgs.add("�п�ܷ|��");
					} else {
						memno = new Integer(memnoStr);
					}

					if (!errorMsgs.isEmpty()) {
						RequestDispatcher failureView = req
								.getRequestDispatcher("/member/back/backMemberUpdateInput.jsp");
						failureView.forward(req, res);
					}

					/*************************** 2.�}�l�d�߸�� ****************************************/
					MemberService memSvc = new MemberService();
					MemberVO memberVO = null;
					if (memSvc.findByPrimaryKey(memno) != null) {
						memberVO = memSvc.findByPrimaryKey(memno);
					} else {
						System.out.println("memno�S�����۹���memberVO");
					}

					/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) ************/
					if (memberVO != null) {
						req.setAttribute("memberVO", memberVO); // ��Ʈw���X��empVO����,�s�Jreq
						String url = "/member/back/backMemberUpdateInput.jsp";
						RequestDispatcher successView = req
								.getRequestDispatcher(url);
						successView.forward(req, res);
					} else {
						System.out.println("memno�S�����۹���memberVO");
					}

					/*************************** ��L�i�઺���~�B�z **********************************/
				} catch (Exception e) {
					errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
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
					/*************************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z **********************/
					String str = req.getParameter("memno");
					if (str == null || (str.trim()).length() == 0) {
						errorMsgs.add("�п�ܷ|��");
					}

					Integer memno = null;
					try {
						memno = new Integer(str);
					} catch (Exception e) {
						errorMsgs.add("���u�s���榡�����T");
					}
					// Send the use back to the form, if there were errors
					if (!errorMsgs.isEmpty()) {
						RequestDispatcher failureView = req
								.getRequestDispatcher("/member/back/backMemberManage.jsp");
						failureView.forward(req, res);
						return;// �{�����_
					}

					/*************************** 2.�}�l�d�߸�� *****************************************/
					MemberService memSvc = new MemberService();
					MemberVO memberVO = memSvc.findByPrimaryKey(memno);
					if (memberVO == null) {
						errorMsgs.add("�d�L���");
					}
					// Send the use back to the form, if there were errors
					if (!errorMsgs.isEmpty()) {
						RequestDispatcher failureView = req
								.getRequestDispatcher("/member/back/backMemberManage.jsp");
						failureView.forward(req, res);
						return;// �{�����_
					}

					/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) *************/
					req.setAttribute("memberVO", memberVO); // ��Ʈw���X��memberVO����,�s�Jreq
					String url = "listOneEmp.jsp";
					RequestDispatcher successView = req
							.getRequestDispatcher(url); // ���\��� listOneEmp.jsp
					successView.forward(req, res);

					/*************************** ��L�i�઺���~�B�z *************************************/
				} catch (Exception e) {
					errorMsgs.add("�L�k���o���:" + e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("select_page.jsp");
					failureView.forward(req, res);
				}
			} // end of getOne_For_Display statement

			if ("delete".equals(action)) { // �Ӧ�listAllMember.jsp

				List<String> errorMsgs = new LinkedList<String>();
				// Store this set in the request scope, in case we need to
				// send the ErrorPage view.
				req.setAttribute("errorMsgs", errorMsgs);

				try {
					/*************************** 1.�����ШD�Ѽ� ***************************************/
					String str = req.getParameter("memno");
					if (str == null || (str.trim()).length() == 0) {
						errorMsgs.add("�п�ܷ|��");
					}

					Integer memno = null;
					try {
						memno = new Integer(str);
					} catch (Exception e) {
						errorMsgs.add("���u�s���榡�����T");
					}
					// Send the use back to the form, if there were errors
					if (!errorMsgs.isEmpty()) {
						RequestDispatcher failureView = req
								.getRequestDispatcher("/member/back/backMemberManage.jsp");
						failureView.forward(req, res);
						return;// �{�����_
					}

					/*************************** 2.�}�l�R����� ***************************************/
					MemberService memSvc = new MemberService();
					memSvc.delete(memno);

					/*************************** 3.�R������,�ǳ����(Send the Success view) ***********/
					String url = "/member/back/backMemberListAll.jsp";
					RequestDispatcher successView = req
							.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
					successView.forward(req, res);

					/*************************** ��L�i�઺���~�B�z **********************************/
				} catch (Exception e) {
					errorMsgs.add("�R����ƥ���:" + e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/member/back/backMemberManage.jsp");
					failureView.forward(req, res);
				}
			} // end of delete statement

			if ("update_back".equals(action)) { // �Ӧ�update_mem_input.jsp���ШD

				List<String> errorMsgs = new LinkedList<String>();
				// Store this set in the request scope, in case we need to
				// send the ErrorPage view.
				req.setAttribute("errorMsgs", errorMsgs);

				try {
					/*************************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z **********************/

					String memnoStr = req.getParameter("memno");
					Integer memno = null;

					if (memnoStr == null || (memnoStr.trim()).length() == 0) {
						errorMsgs.add("�п�ܷ|��");
					} else {
						memno = new Integer(memnoStr);
					}

					String statusStr = req.getParameter("status");
					Integer status = null;

					if (statusStr == null || (statusStr.trim()).length() == 0) {
						errorMsgs.add("�п�J���A");
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
					 * errorMsgs.add("�п�J���A"); } else { requestURL = new
					 * Integer(requestURLStr); }
					 * 
					 * String whichPageStr = req.getParameter("whichPage");
					 * Integer whichPage = null;
					 * 
					 * if (whichPageStr == null ||
					 * (whichPageStr.trim()).length() == 0) {
					 * errorMsgs.add("�п�J���A"); } else { whichPage = new
					 * Integer(whichPageStr); }
					 */

					String requestURL = req.getParameter("requestURL").trim();
					String whichPage = req.getParameter("whichPage").trim();

					MemberVO memberVO = new MemberVO();
					memberVO.setMemno(memno);
					memberVO.setStatus(status);

					// Send the use back to the form, if there were errors
					if (!errorMsgs.isEmpty()) {
						req.setAttribute("memberVO", memberVO); // �t����J�榡���~��empVO����,�]�s�Jreq
						RequestDispatcher failureView = req
								.getRequestDispatcher("/member/back/backMemberManage.jsp");
						failureView.forward(req, res);
						return; // �{�����_
					}

					/*************************** 2.�}�l�ק��� *****************************************/

					MemberService memSvc = new MemberService();
					memberVO = memSvc.update_Back_Member(status, memno);

					/*************************** 3.�ק粒��,�ǳ����(Send the Success view) *************/

					if (memberVO != null) {

						MemberVO msgVO = memSvc.findByPrimaryKey(memno);
						Send se = new Send();
						String[] tel = { "0988133364" };
						// String[] tel = {msgVO.getTel()};
						if (status == 1) {
							String message = "�˷R��'Ź���a��'�|���z�n. �z���b���w�Q�ҥ�!";
							se.sendMessage(tel, message);
						} else if (status == 2) {
							String message = "�˷R��'Ź���a��'�|���z�n. �z���b���w�Q����!";
							se.sendMessage(tel, message);
						}

						Map<String, String[]> map = (Map<String, String[]>) req
								.getSession().getAttribute("map");
						if (map != null) {
							List<MemberVO> list = memSvc.getAll(map);
							String[] typeAry = map.get("type");
							String[] statusAry = map.get("status");
							req.setAttribute("oldType",
									Integer.parseInt(typeAry[0])); // ��Ʈw���X��list����,�s�Jrequest
							req.setAttribute("oldStatus",
									Integer.parseInt(statusAry[0])); // ��Ʈw���X��list����,�s�Jrequest
							req.setAttribute("list", list); // ��Ʈwupdate���\��,���T����memberVO����,�s�Jreq
						}

						String url = "/member/back/backMemberManage.jsp?whichPage="
								+ whichPage
								+ "&memno="
								+ memno
								+ "&requestURL=" + requestURL;
						RequestDispatcher successView = req
								.getRequestDispatcher(url); // �ק令�\��,���listOneEmp.jsp
						successView.forward(req, res);
					} else {
						errorMsgs.add("�ק��ƥ���");
						RequestDispatcher failureView = req
								.getRequestDispatcher("/member/back/backMemberUpdateInput.jsp");
						failureView.forward(req, res);
					}

					/*************************** ��L�i�઺���~�B�z *************************************/
				} catch (Exception e) {
					errorMsgs.add("�ק��ƥ���:" + e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/member/back/backMemberUpdateInput.jsp");
					failureView.forward(req, res);
				}
			} // end of update_back statement

			if ("listMemRS_ByCompositeQuery".equals(action)) { // �Ӧ�select_page.jsp���ƦX�d�߽ШD
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

					/*************************** 1.�N��J����ରMap **********************************/
					// �ĥ�Map<String,String[]> getParameterMap()����k
					// �`�N:an immutable java.util.Map
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

					/*************************** 2.�}�l�ƦX�d�� ***************************************/
					MemberService memSvc = new MemberService();
					List<MemberVO> list = memSvc.getAll(map);
					/*
					 * EmpService empSvc = new EmpService(); List<EmpVO> list =
					 * empSvc.getAll(map);
					 */

					/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) ************/
					req.setAttribute("list", list); // ��Ʈw���X��list����,�s�Jrequest
					if (type != null) {
						req.setAttribute("oldType", type); // ��Ʈw���X��list����,�s�Jrequest
					}
					if (status != null) {
						req.setAttribute("oldStatus", status); // ��Ʈw���X��list����,�s�Jrequest
					}
					/*
					 * if (memno!=null){ req.setAttribute("oldMemno", memno); //
					 * ��Ʈw���X��list����,�s�Jrequest }
					 */

					RequestDispatcher successView = req
							.getRequestDispatcher("/member/back/backMemberManage.jsp"); // ���\���listEmps_ByCompositeQuery.jsp
					successView.forward(req, res);
					return;
					/*************************** ��L�i�઺���~�B�z **********************************/
				} catch (Exception e) {
					errorMsgs.add(e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/member/back/backMemberManage.jsp");
					failureView.forward(req, res);
				}
			} // end of listMemRS_ByCompositeQuery statement

			if ("member_activate".equals(action)) { // �Ӧ�update_mem_input.jsp���ШD

				List<String> errorMsgs = new LinkedList<String>();
				// Store this set in the request scope, in case we need to
				// send the ErrorPage view.
				req.setAttribute("errorMsgs", errorMsgs);

				List<String> successMsgs = new LinkedList<String>();
				// Store this set in the request scope, in case we need to
				// send the ErrorPage view.
				req.setAttribute("successMsgs", successMsgs);

				try {
					/*************************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z **********************/

					String memnoStr = req.getParameter("memno");
					Integer memno = null;

					if (memnoStr == null || (memnoStr.trim()).length() == 0) {
						errorMsgs.add("�Ұʥ���, �Э��s���U�Ϊ��p���޲z�H��");
					} else {
						memno = new Integer(memnoStr);
					}

					String statusStr = req.getParameter("status");
					Integer status = null;

					if (statusStr == null || (statusStr.trim()).length() == 0) {
						errorMsgs.add("�Ұʥ���, �Э��s���U�Ϊ��p���޲z�H��");
					} else {
						status = new Integer(statusStr);
						if (status != 1) {
							errorMsgs.add("�Ұʥ���, �Э��s���U�Ϊ��p���޲z�H��");
						}
					}

					MemberService memSvc = new MemberService();
					MemberVO memFoundVO = memSvc.findByPrimaryKey(memno);

					if (memFoundVO.getStatus() != 0) {
						errorMsgs.add("���b���w�g�Ұ�, �еn�J�Ϊ̵��U�s�b��");
					}

					MemberVO memberVO = new MemberVO();
					memberVO.setMemno(memno);
					memberVO.setStatus(status);

					// Send the use back to the form, if there were errors
					if (!errorMsgs.isEmpty()) {
						RequestDispatcher failureView = req
								.getRequestDispatcher("/member/front/memberSignup.jsp");
						failureView.forward(req, res);
						return; // �{�����_
					}

					/*************************** 2.�}�l�ק��� *****************************************/

					// MemberService memSvc = new MemberService();
					memberVO = memSvc.update_Back_Member(status, memno);

					/*************************** 3.�ק粒��,�ǳ����(Send the Success view) *************/

					if (memberVO != null) {
						successMsgs.add("���߱Ұʦ��\, �еn�J�|���b���P�K�X");
						RequestDispatcher successView = req
								.getRequestDispatcher("/member/front/memberLogin.jsp");
						successView.forward(req, res);
					}

					/*************************** ��L�i�઺���~�B�z *************************************/
				} catch (Exception e) {
					errorMsgs.add("�ק��ƥ���:" + e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/member/back/backMemberUpdateInput.jsp");
					failureView.forward(req, res);
				}
			} // end of member_activate statement

		} // end of content if statement
	} // end of doPost statement

} // end of MemberServlet statement
