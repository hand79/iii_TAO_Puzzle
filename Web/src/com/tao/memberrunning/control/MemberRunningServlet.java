package com.tao.memberrunning.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.tribes.Member;
import org.apache.jasper.tagplugins.jstl.core.ForEach;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.MultipartParser;
import com.tao.member.model.MemberService;
import com.tao.member.model.MemberVO;
import com.tao.runningad.model.RunningAdService;
import com.tao.runningad.model.RunningAdVO;

public class MemberRunningServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String TEMPDIR = "Adimages";

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		File file = new File(getServletContext().getRealPath(TEMPDIR));
		if (!file.exists()) {
			file.mkdirs();
		}

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");

		List<String> errorMsgs = new LinkedList<String>();

		try {

			MultipartRequest multi = new MultipartRequest(req, req
					.getServletContext().getRealPath(TEMPDIR), 5 * 1024 * 1024,
					"UTF-8");

			String cansenoST = multi.getParameter("caseno").trim();
			String addaysST = multi.getParameter("addays").trim();
			HttpSession session = req.getSession();
			MemberVO memberVO = (MemberVO) session.getAttribute("CurrentUser");

			Integer caseno = null;
			try {
				caseno = new Integer(cansenoST);
			} catch (Exception e) {
				errorMsgs.add("合購案格式有誤" + caseno);
			}

			Integer addays = null;
			try {
				addays = new Integer(addaysST);
			} catch (Exception e) {
				errorMsgs.add("天數格式有誤:" + addays);
			}
			/************* 剩餘天數計算 **********************/
			memberVO.setAddays(memberVO.getAddays() - addays);

			File photoFile = multi.getFile("adpic");
			String mime = multi.getContentType("adpic");

			byte[] photo = null;

			FileInputStream in = null;
			if (photoFile != null) {
				try {
					in = new FileInputStream(photoFile);
					photo = new byte[in.available()];
					in.read(photo);
					photoFile.delete();
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				errorMsgs.add("無廣告圖片:" + photo);
			}

			RunningAdVO runningAdVO = new RunningAdVO();
			runningAdVO.setMemno(memberVO.getMemno());
			runningAdVO.setCaseno(caseno);
			runningAdVO.setDtime(addays);
			runningAdVO.setMime(mime);
			System.out.println(runningAdVO.getMime());
			runningAdVO.setTst(0);
			runningAdVO.setAdpic(photo);

			if (!errorMsgs.isEmpty()) {
				for (String string : errorMsgs) {
					System.out.println(string);
				}
				return;
			}

			/*************************** 2.開始新增資料 ***************************************/
			RunningAdService runAdSvc = new RunningAdService();
			runAdSvc.insertFromUser(runningAdVO);
			MemberService memberSvc = new MemberService();
			memberSvc.updateMember(memberVO.getMemno(), memberVO.getMemid(),
					memberVO.getMempw(), memberVO.getFname(),
					memberVO.getLname(), memberVO.getIdnum(),
					memberVO.getGender(), memberVO.getLocno(),
					memberVO.getAddr(), memberVO.getTel(), memberVO.getEmail(),
					memberVO.getMime(), memberVO.getPoint(),
					memberVO.getMoney(), memberVO.getAddays(),
					memberVO.getStatus(), memberVO.getType(),
					memberVO.getWithhold());
			session.setAttribute("CurrentUser", memberVO);
			/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
			String url = "/memberrunning/memberrunning.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // **
																			// 須改為insert成功頁面
			successView.forward(req, res);

			/*************************** 其他可能的錯誤處理 **********************************/
		} catch (Exception e) {
			errorMsgs.add(e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/memberrunning/memberrunning.jsp"); // **
																				// 須改為insert頁面
			failureView.forward(req, res);
		}
	}

}
