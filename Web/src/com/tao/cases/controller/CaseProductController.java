package com.tao.cases.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.tao.cases.model.CaseProductService;
import com.tao.cases.model.CaseProductVO;
import com.tao.category.model.SubCategoryService;
import com.tao.category.model.SubCategoryVO;
import com.tao.jimmy.util.FrontSessionAttrUtil;
import com.tao.member.model.MemberVO;

public class CaseProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String LIST_CPS_VIEW = "/caseproduct/ViewOwnCaseProduct.jsp";
	private static final String ADD_AND_EDIT_VIEW = "/caseproduct/AddOrEditCaseProduct.jsp";
	private static final String AJAX_CP_LIST_TABLE = "/caseproduct/Ajax_caseProductList.jsp";
	private static final String AJAX_SUBCAT_LIST_OPTIONS = "/caseproduct/Ajax_findSubcategory.jsp";
	private static final String TEMP_DIR = "cptemp";
	private static final String MEMBER_VO_ATTRIBUTE_KEY = FrontSessionAttrUtil.MEMBER_VO_ATTRIBUTE_KEY;
	private static final String LOCATION_ATTRIBUTE_KEY = FrontSessionAttrUtil.LOCATION_ATTRIBUTE_KEY;
	private static final String PREVIEW_CASEPRODUCT = "/caseproduct/PreviewCaseProduct.jsp";
	private static String default_redirect = "/index.jsp";

	@Override
	public void init() throws ServletException {
		super.init();
		default_redirect = getServletContext().getContextPath()
				+ default_redirect;
		File tempDir = new File(getServletContext().getRealPath(TEMP_DIR));
		if (!tempDir.exists() || !tempDir.isDirectory()) {
			System.out.println("CaseProductController: temp folder \""
					+ TEMP_DIR + "\" does not exist, mkdirs()");
			tempDir.mkdirs();
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String contentType = request.getContentType();
		System.out.println("CaseProductController: doPost, contentType=" + contentType);
		if (!invalidString(contentType)
				&& contentType.startsWith("multipart/form-data")) {
			doMultipartRequest(request, response);
			return;
		}
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		System.out.println("CaseProductController: doGet, action=" + action);
		
		if (invalidString(action)) {
			System.out
					.println("CaseProductController: invalid action parameter.");
			response.sendRedirect(default_redirect);
			return;
		}
		if ("preview".equals(action)) {
			doPreview(request, response);
			return;
		}

		if ("viewOwnCPs".equals(action)) {
			// System.out.println("choose viewOwnCPs");
			if (isLoggedIn(request)) {
				doShowOwnCPs(request, response);
			} else {
				redirectToLogin(request, response);
			}
			return;
		}
		if ("add".equals(action) || "viewOrEdit".equals(action)) {
			// System.out.println("choose add & viewOrEdit");
			if (isLoggedIn(request)) {
				doViewOrEdit(request, response);
			} else {
				redirectToLogin(request, response);
			}
			return;
		}

		if ("del".equals(action)) {
			// System.out.println("choose del");
			if (isLoggedIn(request)) {
				doDelRequest(request, response);
			} else {
				redirectToLogin(request, response);
			}
			return;
		}

		if ("ajax".equals(action)) {
			// System.out.println("choose ajax");
			doAjax(request, response);
			return;
		}
		System.out
				.println("CaseProductController: no proper action handler found, do nothing.");
	}

	private void doPreview(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		String cpno = request.getParameter("cpno");
		if (invalidCpno(cpno)) {
			return;
		}
		MemberVO memvo = (MemberVO) request.getSession().getAttribute(
				MEMBER_VO_ATTRIBUTE_KEY);
		if (memvo == null) {
			request.getSession().setAttribute("location",
					request.getRequestURI() + "?" + request.getQueryString());
			response.sendRedirect(request.getContextPath() + "/Login.jsp");
			return;
		}
		CaseProductService cpSvc = new CaseProductService();
		CaseProductVO cpvo = cpSvc.getOneByPrimaryKeyNoPic(new Integer(cpno));
		if (cpvo == null
				|| cpvo.getMemno().intValue() != memvo.getMemno().intValue()) {
			response.sendRedirect(request.getContextPath() + "/caseproduct/caseProduct.do?action=viewOwnCPs");
			return;
		}
		request.setAttribute("cpvo",cpvo);
		request.getRequestDispatcher(PREVIEW_CASEPRODUCT).forward(request, response);
		
	}

	private void doAjax(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("CaseProductController: doAjax");
		String what = request.getParameter("what");
		if (what == null || what.length() == 0) {
			return;
		}

		if ("subcatList".equals(what)) {
			String catno = request.getParameter("catno");
			if (invalidCatno(catno)) {
				return;
			}
			SubCategoryService subcatSvc = new SubCategoryService();
			Set<SubCategoryVO> subcatset = subcatSvc
					.getAllByCategory(new Integer(catno));
			// The Set may be empty but not null
			request.setAttribute("SubCategorySet", subcatset);
			request.getRequestDispatcher(AJAX_SUBCAT_LIST_OPTIONS).forward(
					request, response);
		}
	}

	private void doMultipartRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		System.out.println("CaseProductController: doMultipartRequest()");

		MultipartRequest multi = new MultipartRequest(request,
				getTempDirPath(), 5 * 1024 * 1024, "UTF-8");

		String action = multi.getParameter("action");
		if (invalidString(action)) {
			System.out.println("multiplepart: invalid action String");
			return;
		}
		if ("AddOrUpdate".equals(action)) {
			doAddOrUpdate(request, response, multi);
		}

		return;
	}

	private void doAddOrUpdate(HttpServletRequest request,
			HttpServletResponse response, MultipartRequest multi)
			throws IOException {

		MemberVO memvo = (MemberVO) request.getSession().getAttribute(
				MEMBER_VO_ATTRIBUTE_KEY);
		List<String> errMsg = new ArrayList<>();
		if (memvo == null) {
			ajaxRedirectToLogin(request, response);
			return;
		}

		String cpno = multi.getParameter("cpno");
		boolean addFlag = true;
		CaseProductService cpSvc = new CaseProductService();
		CaseProductVO cpvo = null;
		if (!invalidCpno(cpno)) {
			cpvo = cpSvc.getOneByPrimaryKeyHasPic(new Integer(cpno));
			if (cpvo != null) {
				addFlag = cpvo.getLockflag() == CaseProductVO.LOCKED;
			}
		}

		String name = multi.getParameter("cp-name");
		String price = multi.getParameter("cp-price");
		String subcatno = multi.getParameter("cp-subcatno");

		if (invalidString(name)) {
			errMsg.add("未輸入名稱");
		}
		if (price == null || !price.matches("\\d+")) {
			errMsg.add("未輸入價格或格式不正確");
		}
		if (subcatno == null || !subcatno.matches("\\d{1,3}")) {
			System.out.println(subcatno);
			errMsg.add("分類錯誤");
		}
		if (errMsg.size() != 0) {
			for (String s : errMsg) {
				System.out.println(s);
			}
			return;
		}
		String cpdesc = multi.getParameter("cp-cpdesc");
		byte[][] pics = new byte[3][];
		String[] pmimes = new String[3];
		for (int i = 1; i <= 3; i++) {
			String type = multi.getContentType("pic" + i);
			File picFile = multi.getFile("pic" + i);
			if (picFile == null || !picFile.exists()) {
				continue;
			}
			FileInputStream in = new FileInputStream(picFile);
			pics[i - 1] = new byte[in.available()];
			in.read(pics[i - 1]);
			in.close();
			picFile.delete();
			pmimes[i - 1] = type;
		}

		/************** Access Database ***************/
		boolean success = false;
		if (cpvo != null) {
			pics[0] = pics[0] == null ? cpvo.getPic1() : pics[0];
			pics[1] = pics[1] == null ? cpvo.getPic2() : pics[1];
			pics[2] = pics[2] == null ? cpvo.getPic3() : pics[2];
			pmimes[0] = pmimes[0] == null ? cpvo.getPmime1() : pmimes[0];
			pmimes[1] = pmimes[1] == null ? cpvo.getPmime2() : pmimes[1];
			pmimes[2] = pmimes[2] == null ? cpvo.getPmime3() : pmimes[2];
		}
		if (addFlag) {

			Integer genKey = cpSvc.addCaseProduct(memvo.getMemno(), name,
					new Integer(price), pics[0], pics[1], pics[2], pmimes[0],
					pmimes[1], pmimes[2], cpdesc, new Integer(subcatno));
			if (genKey != null && genKey > 4000) {
				success = true;
				System.out.println("CaseProductController: genKey=" + genKey);
			}
		} else {

			int updateCount = cpSvc.updateCaseProduct(new Integer(cpno), name,
					new Integer(price), pics[0], pmimes[0], pics[1], pmimes[1],
					pics[2], pmimes[2], cpdesc, new Integer(subcatno));
			if (updateCount > 0) {
				success = true;
				System.out.println("CaseProductController: updateCount"
						+ updateCount);
			}
		}

		/***** Response ******/
		if (success) {
			StringBuilder sb = new StringBuilder("{ \"url\":\""
					+ request.getScheme() + "://");
			sb.append(request.getServerName()).append(":")
					.append(request.getServerPort())
					.append(request.getContextPath())
					.append("/caseproduct/caseProduct.do?action=viewOwnCPs\"")
					.append("}");
			System.out.println(sb);
			response.getWriter().println(sb.toString());
		}
	}

	private void doViewOrEdit(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String cpno = request.getParameter("cpno");
		if (!invalidCpno(cpno)) {
			CaseProductService cpSvc = new CaseProductService();
			CaseProductVO cpvo = cpSvc
					.getOneByPrimaryKeyNoPic(new Integer(cpno));
			if (cpvo != null) {
				request.setAttribute("CaseProductToEdit", cpvo);
			}
		}
		response.setBufferSize(1024 * 1024);
		request.getRequestDispatcher(ADD_AND_EDIT_VIEW).forward(request,
				response);
	}

	private void doDelRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		MemberVO memberVO = (MemberVO) request.getSession().getAttribute(
				MEMBER_VO_ATTRIBUTE_KEY);

		CaseProductService cpSvc = new CaseProductService();
		String cpno = request.getParameter("cpno");
		if (invalidCpno(cpno)) {
			return;
		}

		int updateCount = cpSvc.deleteCaseProduct(new Integer(cpno));
		System.out.println("updateCount: " + updateCount);
		Integer memno = memberVO.getMemno();

		List<CaseProductVO> cplist = cpSvc.getByOwnerNo(memno, false);
		Collections.reverse(cplist);
		request.setAttribute("cplist", cplist);
		request.getRequestDispatcher(AJAX_CP_LIST_TABLE).forward(request,
				response);
	}

	private void doShowOwnCPs(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		MemberVO memberVO = (MemberVO) request.getSession().getAttribute(
				MEMBER_VO_ATTRIBUTE_KEY);
		Integer memno = memberVO.getMemno();
		/** access data **/
		CaseProductService cpSvc = new CaseProductService();
		List<CaseProductVO> cplist = cpSvc.getByOwnerNo(memno, false);
		Collections.reverse(cplist);
		/** forward **/
		request.setAttribute("cplist", cplist);
		request.getRequestDispatcher(LIST_CPS_VIEW).forward(request, response);
	}

	private String getTempDirPath() throws IOException {
		return getServletContext().getRealPath(TEMP_DIR);
	}

	private boolean invalidCpno(String cpno) {
		return !(cpno != null && cpno.matches("\\d{4,}"));
	}

	private boolean invalidCatno(String catno) {
		return !(catno != null && catno.matches("\\d+"));
	}

	private boolean invalidString(String str) {
		return (str == null || str.trim().length() == 0);
	}

	private void ajaxRedirectToLogin(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.getWriter().println("{\"relogin\":\"true\"}");
	}

	private void redirectToLogin(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.getSession().setAttribute(LOCATION_ATTRIBUTE_KEY,
				request.getRequestURI() + "?" + request.getQueryString());
		response.sendRedirect(request.getContextPath() + "/Login.jsp");
	}

	private boolean isLoggedIn(HttpServletRequest request) {
		return request.getSession().getAttribute(MEMBER_VO_ATTRIBUTE_KEY) != null;
	}

}
