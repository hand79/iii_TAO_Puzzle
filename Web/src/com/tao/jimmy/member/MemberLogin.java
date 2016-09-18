package com.tao.jimmy.member;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.tao.jimmy.util.AjaxMsg;
import com.tao.jimmy.util.FrontSessionAttrUtil;
import com.tao.member.model.MemberService;
import com.tao.member.model.MemberVO;

public class MemberLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String LOCATION_ATTRIBUTE_KEY = FrontSessionAttrUtil.LOCATION_ATTRIBUTE_KEY;
	private static final String MEMBER_VO_ATTRIBUTE_KEY = FrontSessionAttrUtil.MEMBER_VO_ATTRIBUTE_KEY;
	private static final String MEMBER_CENTER_PAGE = "/MemberCenter.jsp";
	private static String homepage = FrontSessionAttrUtil.HOMEPAGE;
	private static String login_page = FrontSessionAttrUtil.LOGIN_PAGE;

	@Override
	public void init() throws ServletException {
		super.init();
		login_page = getServletContext().getContextPath() + login_page;
		homepage = getServletContext().getContextPath()+homepage;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action == null) {
			HttpSession session = request.getSession();
			if (session.getAttribute(MEMBER_VO_ATTRIBUTE_KEY) == null) {
				session.setAttribute("location", request.getRequestURI());
				response.sendRedirect(login_page);
				return;
			}
			request.getRequestDispatcher(MEMBER_CENTER_PAGE).forward(request,
					response);
			return;
		}

		if ("changeArea".equals(action)) {
			// System.out.println("changeArea");
			String area = request.getParameter("area");
			if (area == null || !area.matches("\\d{1,2}")) {
				return;
			}
			request.getSession().setAttribute("area", new Integer(area));
			response.getWriter().print("success");
			return;
		}

		if ("login".equals(action)) {
			String memid = request.getParameter("memid");
			String mempw = request.getParameter("mempw");
			MemberService service = new MemberService();
			MemberVO vo = service.findByMemberID(memid);

			if (vo != null) {
				String pwd = vo.getMempw();
				if (pwd.equals(mempw)) {
					HttpSession session = request.getSession();
					session.setAttribute(MEMBER_VO_ATTRIBUTE_KEY, vo);

					String oriLocation = (String) session
							.getAttribute(LOCATION_ATTRIBUTE_KEY);
					if (oriLocation != null && oriLocation.trim().length() != 0) {
						session.removeAttribute(LOCATION_ATTRIBUTE_KEY);
						response.sendRedirect(oriLocation);
					} else {
						response.sendRedirect(request.getContextPath()
								+ "/LoginPortal.jsp");
					}
				}
			} else {
				response.getWriter().println("login failed");
			}
			return;
		}
		// if ("logout".equals(action)) {
		// request.getSession().invalidate();
		// PrintWriter out = response.getWriter();
		// out.println("<h1>You have logged out</h1>");
		// out.println("<a href=\"" + request.getContextPath()
		// + "/LoginPortal.jsp\">Back to Login Portal</a>");
		// return;
		// }
		if ("logout".equals(action)) {
			request.getSession().removeAttribute(MEMBER_VO_ATTRIBUTE_KEY);
			// String page = request.getParameter("page");
			// if(page != null && page.trim().length() > 0){
			// response.sendRedirect(page);
			// }else{
			response.sendRedirect(homepage);
			// }
			return;
		}

		if ("ajax".equals(action)) {
			AjaxMsg msg = new AjaxMsg();
			String name = request.getParameter("name");
			String pwd = request.getParameter("pwd");
			MemberService service = new MemberService();
			MemberVO mem = service.findByMemberID(name);
			if (mem == null || !(mem.getMempw().equals(pwd))) {
				msg.setStatus(AjaxMsg.ERROR);
				msg.setResHtml("帳號或密碼錯誤");
			} else {
				switch (mem.getStatus()) {
				case 0:
					msg.setStatus(AjaxMsg.ERROR);
					msg.setResHtml("您的帳號尚未啟用");
					break;
				case 1:
					request.getSession().setAttribute(MEMBER_VO_ATTRIBUTE_KEY,
							mem);
					msg.setStatus(AjaxMsg.SUCCESS);
					break;
				case 2:
					msg.setStatus(AjaxMsg.ERROR);
					msg.setResHtml("您的帳號已被停權，請聯絡客服人員");
					break;
				}
			}
			String json = new Gson().toJson(msg);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().println(json);
			return;
		}
	}
}
