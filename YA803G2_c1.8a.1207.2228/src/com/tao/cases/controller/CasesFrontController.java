package com.tao.cases.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tao.caseRep.model.CaseRepService;
import com.tao.cases.model.CaseProductService;
import com.tao.cases.model.CaseProductVO;
import com.tao.cases.model.CaseQADAO;
import com.tao.cases.model.CaseQAService;
import com.tao.cases.model.CaseQAVO;
import com.tao.cases.model.CasesService;
import com.tao.cases.model.CasesVO;
import com.tao.category.model.CategoryService;
import com.tao.category.model.CategoryVO;
import com.tao.category.model.SubCategoryService;
import com.tao.category.model.SubCategoryVO;
import com.tao.jimmy.casesupport.WishListService;
import com.tao.jimmy.location.CountyService;
import com.tao.jimmy.location.CountyVO;
import com.tao.jimmy.location.LocationService;
import com.tao.jimmy.location.LocationVO;
import com.tao.jimmy.member.TinyMemberService;
import com.tao.jimmy.member.TinyMemberVO;
import com.tao.jimmy.util.AjaxMsg;
import com.tao.jimmy.util.FrontSessionAttrUtil;
import com.tao.jimmy.util.Simulation;
import com.tao.jimmy.util.model.ColumnValue;
import com.tao.member.model.MemberVO;
import com.tao.order.model.OrderDAO;
import com.tao.order.model.OrderService;
import com.tao.order.model.OrderVO;
import com.tao.shop.model.ShopService;
import com.tao.shop.model.ShopVO;
import com.tao.shopproduct.model.ShopproductService;
import com.tao.shopproduct.model.ShopproductVO;

public class CasesFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String MEMBER_VO_ATTRIBUTE_KEY = FrontSessionAttrUtil.MEMBER_VO_ATTRIBUTE_KEY;
	private static final String LOCATION_ATTRIBUTE_KEY = FrontSessionAttrUtil.LOCATION_ATTRIBUTE_KEY;
	private static final boolean IS_SIMULATION = Simulation.isSimulation();
	// forward
	private static final String VIEW_CASE = "/cases/CaseDetail.jsp";
	private static final String VIEW_OWN_CASES = "/cases/ViewOwnCases.jsp";
	private static final String ADD_OR_EDIT_CASE = "/cases/AddOrEditCase.jsp";
	private static final String PREVIEW_CASE = "/cases/PreviewCase.jsp";
	private static final String AJAX_TOWN_SELECT_LIST = "/cases/Ajax_findTown.jsp";
	private static final String AJAX_OUTPUT = "/cases/CasesAjaxResServlet";
	private static final String AJAX_OWN_CASES = "/cases/Ajax_ownCases.jsp";
	// redirect
	private static String default_redirect = FrontSessionAttrUtil.HOMEPAGE;
	private static String login_page = FrontSessionAttrUtil.LOGIN_PAGE;

	@Override
	public void init() throws ServletException {
		super.init();
		String context = getServletContext().getContextPath();
		default_redirect = context + default_redirect;
		login_page = context + login_page;
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// System.out.println("CasesFrontController: doGet()");
		String redir = request.getParameter("redir");

		if ("login".equals(redir)) {
			String location = request.getContextPath()
					+ "/cases/cases.do?action=";
			String page = request.getParameter("page");
			// location += (page == null ? "" : page);
			// System.out.println(location);
			if ("viewOwnCases".equals(page)) {
				location += "viewOwnCases";
			}
			if ("caseDetail".equals(page)) {
				location += "caseDetail&caseno="
						+ request.getParameter("caseno");
			}
			request.getSession().setAttribute(LOCATION_ATTRIBUTE_KEY, location);
			response.sendRedirect(login_page);
			return;
		}
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// System.out.println("CasesFrontController: doPost()");
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");

		if ("caseDetail".equals(action)) {
			doShowCaseDetail(request, response);
			return;
		}

		if ("ajax".equals(action)) {
			doAjax(request, response);
			return;
		}
		if ("viewOwnCases".equals(action)) {
			doViewOwnCases(request, response);
			return;
		}
		if(action==null || "".equals(action)){
			System.out.print("action parameter missed");
			return;
		}
		// Check Login state (request from GET & not ajax)
		MemberVO memvo = (MemberVO) request.getSession().getAttribute(
				MEMBER_VO_ATTRIBUTE_KEY);
		if (memvo == null) {
			redirectToLogin(request, response);
			return;
		}
		if ("edit".equals(action) || "add".equals(action)) {
			doPrepareAddOrEdit(request, response);
			return;
		}
		if ("preview".equals(action)) {
			doPreview(request, response);
			return;
		}

		System.out.print("CasesFrontController: ");
		System.out
				.println("END OF doPost() REACHED, NO PROPER ACTION HANDLER FOUND.");
		System.out.println("CasesFrontController: action = (start)" + action
				+ "(end)");
	}

	private void doAjax(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String what = request.getParameter("what");
		// System.out.println(what);
		if ("town".equals(what)) {
			String countyVal = request.getParameter("county");
			if (countyVal != null && countyVal.trim().length() != 0) {
				String[] vals = countyVal.split("-");
				try {
					Integer from = new Integer(vals[0]);
					Integer to = new Integer(vals[1]);

					LocationService locSvc = new LocationService();
					Set<LocationVO> towns = locSvc.findByCounty(from, to);
					request.setAttribute("towns", towns);

					request.getRequestDispatcher(AJAX_TOWN_SELECT_LIST)
							.forward(request, response);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
			return;
		}

		MemberVO memvo = (MemberVO) request.getSession().getAttribute(
				MEMBER_VO_ATTRIBUTE_KEY);
		CaseAjaxRes res = null;
		if ("addOrEdit".equals(what)) {
			if (memvo == null) {
				res = CaseAjaxRes.TO_LOGIN;
			} else {
				res = ajaxAddOrEditCase(request, response);
			}
			request.setAttribute("AjaxRes", res);
			request.getRequestDispatcher(AJAX_OUTPUT)
					.forward(request, response);
			return;
		}

		if ("changePage".equals(what)) {
			if (memvo == null) {
				request.getSession().setAttribute(
						"location",
						request.getContextPath()
								+ "/cases/cases.do?action=viewOwnCases");
				response.setContentType("text/plain; charset=UTF-8");
				response.getWriter().println(login_page);
			} else {
				ajaxChangeCaseDetailPage(request, response, memvo);
			}
			return;
		}

		String casenoStr = request.getParameter("caseno");
		if (memvo == null) {
			res = CaseAjaxRes.TO_LOGIN;
		} else if (invalidCaseno(casenoStr)) {
			res = CaseAjaxRes.ERROR;
		} else {
			Integer caseno = new Integer(casenoStr);
			if ("getCaseDetail".equals(what)) {
				res = ajaxGetCaseDetail(request, response, caseno);
				request.setAttribute("AjaxRes", res);
				request.getRequestDispatcher(AJAX_OUTPUT).forward(request,
						response);
				return;
			}
			if ("addToWishlist".equals(what)) {
				System.out.println("addToWishlist");
				res = ajaxAddToWishlist(request, response, caseno, memvo);
				request.setAttribute("AjaxRes", res);
				request.getRequestDispatcher(AJAX_OUTPUT).forward(request,
						response);
				return;
			}

			/********* SIMULATION *********/
			if (IS_SIMULATION) {
				try {
					Thread.sleep(1200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			/********* SIMULATION *********/

			if ("open".equals(what)) {
				res = ajaxOpenCase(request, response, caseno);
			}
			if ("over".equals(what)) {
				res = ajaxOverCase(request, response, caseno);
			}
			if ("delete".equals(what)) {
				res = ajaxDeleteCase(request, response, caseno);
			}
			if ("cancel".equals(what)) {
				res = ajaxCancelCase(request, response, caseno);
			}
			if ("addToWishlist".equals(what)) {
				res = ajaxAddToWishlist(request, response, caseno, memvo);
			}
			if ("report".equals(what)) {
				res = ajaxReport(request, response, caseno, memvo);
			}
		}
		request.setAttribute("AjaxRes", res);
		request.getRequestDispatcher(AJAX_OUTPUT).forward(request, response);
	}

	private CaseAjaxRes ajaxReport(HttpServletRequest request,
			HttpServletResponse response, Integer caseno, MemberVO memvo)
			throws UnsupportedEncodingException {
		CasesService cSvc = new CasesService();
		CasesVO cvo = cSvc.getByPrimaryKey(caseno);
		if (cvo == null) {
			return CaseAjaxRes.ERROR;
		}
		if (cvo.getStatus() != CasesVO.STATUS_PRIVATE
				&& cvo.getStatus() != CasesVO.STATUS_PUBLIC) {
			return CaseAjaxRes.CASE_STATUS_ERROR;
		}
		String reason = request.getParameter("reason");
		reason = new String(reason.getBytes("ISO-8859-1"), "UTF-8");
		if (reason == null || reason.trim().length() == 0) {
			return CaseAjaxRes.UPDATE_FAILED;
		}
		CaseRepService crSvc = new CaseRepService();
		crSvc.addCaseRep(memvo.getMemno(), cvo.getMemno(), caseno, reason);
		return CaseAjaxRes.SUCCESS;
	}

	private CaseAjaxRes ajaxAddToWishlist(HttpServletRequest request,
			HttpServletResponse response, Integer caseno, MemberVO memvo) {
		CasesService cSvc = new CasesService();
		CasesVO cvo = cSvc.getByPrimaryKey(caseno);
		if (cvo == null) {
			return CaseAjaxRes.ERROR;
		}
		if (cvo.getStatus() != CasesVO.STATUS_PRIVATE
				&& cvo.getStatus() != CasesVO.STATUS_PUBLIC) {
			return CaseAjaxRes.CASE_STATUS_ERROR;
		}
		WishListService wlSvc = new WishListService();
		Integer genKey = wlSvc.insert(memvo.getMemno(), caseno);
		if (genKey == 0) {
			return CaseAjaxRes.UPDATE_FAILED;
		}
		return CaseAjaxRes.SUCCESS;
	}

	private CaseAjaxRes ajaxDeleteCase(HttpServletRequest request,
			HttpServletResponse response, Integer caseno) {
		CasesService cSvc = new CasesService();
		CasesVO cvo = cSvc.getByPrimaryKey(caseno);
		if (cvo.getStatus() != CasesVO.STATUS_CREATED) {
			return CaseAjaxRes.CASE_STATUS_ERROR;
		}
		int updateCount = cSvc.updateCaseStatus(caseno, CasesVO.STATUS_DELETED);
		if (updateCount != 1) {
			return CaseAjaxRes.ERROR;
		}
		return CaseAjaxRes.SUCCESS;
	}

	private CaseAjaxRes ajaxCancelCase(HttpServletRequest request,
			HttpServletResponse response, Integer caseno) {
		CasesService cSvc = new CasesService();
		CasesVO cvo = cSvc.getByPrimaryKey(caseno);
		if (cvo == null) {
			return CaseAjaxRes.ERROR;
		}
		int status = cvo.getStatus();
		if (status != CasesVO.STATUS_PRIVATE && status != CasesVO.STATUS_PUBLIC
				&& status != CasesVO.STATUS_OVER) {
			return CaseAjaxRes.CASE_STATUS_ERROR;
		}
		boolean success = cSvc.cancelCase(caseno);
		if (!success) {
			return CaseAjaxRes.ERROR;
		}
		return CaseAjaxRes.SUCCESS;
	}

	private CaseAjaxRes ajaxOverCase(HttpServletRequest request,
			HttpServletResponse response, Integer caseno) {
		CasesService cSvc = new CasesService();
		CasesVO cvo = cSvc.getByPrimaryKey(caseno);
		if (cvo.getStatus() != CasesVO.STATUS_PRIVATE
				&& cvo.getStatus() != CasesVO.STATUS_PUBLIC) {
			return CaseAjaxRes.CASE_STATUS_ERROR;
		}
		OrderService ordSvc = new OrderService();
		int currentQty = ordSvc.getTotalOrderQty(caseno);
		if (currentQty < cvo.getMinqty()) {
			return CaseAjaxRes.CASE_ORDER_QTY_INSUFFICIENT;
		}
		boolean success = cSvc.overCase(caseno);
		if (!success) {
			return CaseAjaxRes.ERROR;
		}
		return CaseAjaxRes.SUCCESS;
	}

	private CaseAjaxRes ajaxOpenCase(HttpServletRequest request,
			HttpServletResponse response, Integer caseno) {
		CasesService cSvc = new CasesService();
		String type = request.getParameter("type");
		int updateCount = 0;
		String edate = request.getParameter("edate");
		String etime = request.getParameter("etime");
		System.out.println("edate=" + edate);
		System.out.println("etime=" + etime);

		if (invalidTimeParam(edate, etime)) {
			return CaseAjaxRes.ERROR;
		}
		Timestamp etimestamp = Timestamp.valueOf(edate + " " + etime + ":00");

		if ("private".equals(type)) {
			updateCount = cSvc.openCases(caseno, etimestamp, true);
		} else {
			updateCount = cSvc.openCases(caseno, etimestamp, false);
		}
		if (updateCount != 1) {
			return CaseAjaxRes.ERROR;
		}
		return CaseAjaxRes.SUCCESS;
	}

	private CaseAjaxRes ajaxGetCaseDetail(HttpServletRequest request,
			HttpServletResponse response, Integer caseno) {

		CasesVO cvo = new CasesService().getByPrimaryKey(caseno);
		request.setAttribute("cvo", cvo);

		if (cvo.getCpno() != 0) {
			CaseProductVO cpvo = new CaseProductService()
					.getOneByPrimaryKeyNoPic(cvo.getCpno());
			request.setAttribute("cpvo", cpvo);
		} else if (cvo.getSpno() != 0) {
			ShopproductVO spvo = new ShopproductService().getOneShopproduct(cvo
					.getSpno());
			request.setAttribute("spvo", spvo);
		} else {
			System.out.println("CasesVO does not contain valid cpno or spno.");
			return CaseAjaxRes.ERROR;
		}

		Integer currentQty = new OrderService().getTotalOrderQty(caseno);
		request.setAttribute("currentqty", currentQty);

		LocationVO locvo = new LocationService().findByPrimaryKey(cvo
				.getLocno());
		request.setAttribute("locvo", locvo);
		return CaseAjaxRes.SUCCESS;
	}

	private CaseAjaxRes ajaxAddOrEditCase(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		MemberVO memvo = (MemberVO) request.getSession().getAttribute(
				MEMBER_VO_ATTRIBUTE_KEY);
		if (memvo == null) {
			request.setAttribute("AjaxRes", CaseAjaxRes.TO_LOGIN);
			request.getRequestDispatcher(AJAX_OUTPUT)
					.forward(request, response);
		}
		String casenoStr = request.getParameter("caseno");
		boolean addNew = true;
		Integer caseno = null;
		if (!invalidCaseno(casenoStr)) {
			CasesService cSvc = new CasesService();
			Set<CasesVO> set = cSvc.compositeQuery(new ColumnValue("memno",
					memvo.getMemno().toString()), new ColumnValue("caseno",
					casenoStr));
			if (set.size() == 1) {
				System.out.println("Valid Caseno, addNew = false");
				addNew = false;
				caseno = new Integer(casenoStr);
			}
		}
		String cpsp = request.getParameter("cpsp");
		boolean useCP;
		if ("cp".equals(cpsp)) {
			useCP = true;
		} else if ("sp".equals(cpsp)) {
			useCP = false;
		} else {
			System.out.println("invalid param: cpsp");
			return CaseAjaxRes.ERROR;
		}

		boolean valid = true;
		String title = request.getParameter("title");
		if (title == null || title.trim().length() == 0) {
			valid = false;
		}
		String cpnoStr = request.getParameter("cpno");
		String spnoStr = request.getParameter("spno");
		Integer cpno = null;
		Integer spno = null;
		if (useCP) {
			if (cpnoStr == null || !cpnoStr.matches("\\d{4,}")) {
				valid = false;
			} else {
				cpno = new Integer(cpnoStr);
			}
		} else {
			if (spnoStr == null || !spnoStr.matches("\\d{4,}")) {
				valid = false;
			} else {
				spno = new Integer(spnoStr);
			}
		}

		String discountStr = request.getParameter("discount");
		if (discountStr == null || !discountStr.matches("\\d+")) {
			valid = false;
		}

		String locnoStr = request.getParameter("locno");
		if (locnoStr == null || !locnoStr.matches("\\d+")
				|| Integer.parseInt(locnoStr) > 360) {
			valid = false;
		}

		String ship1 = request.getParameter("ship1");
		if (ship1 == null || ship1.trim().length() == 0) {
			valid = false;
		}

		String shipcost1Str = request.getParameter("shipcost1");
		if (shipcost1Str == null || !shipcost1Str.matches("\\d+")) {
			shipcost1Str = "0";
		}

		String minqtyStr = request.getParameter("minqty");
		if (minqtyStr == null || !minqtyStr.matches("\\d+")
				|| Integer.parseInt(minqtyStr) > 999) {
			valid = false;
		}

		String maxqtyStr = request.getParameter("maxqty");
		if (maxqtyStr == null || !maxqtyStr.matches("\\d+")
				|| Integer.parseInt(maxqtyStr) > 999) {
			valid = false;
		}

		String ship2 = request.getParameter("ship2");
		if (ship2 == null || ship2.trim().length() == 0) {
			ship2 = null;
		}

		String shipcost2Str = request.getParameter("shipcost2");
		if (shipcost2Str == null || !shipcost2Str.matches("\\d+")) {
			shipcost2Str = "0";
		}

		String casedesc = request.getParameter("casedesc");
		if (casedesc == null || casedesc.trim().length() == 0) {
			valid = false;
		}

		if (!valid) {
			System.out.println("Parameter validation failed.");
			return CaseAjaxRes.ERROR;
		}
		// CasesVO cvo = new CasesVO();
		// cvo.setMemno(memvo.getMemno());
		// cvo.setTitle(title);
		// cvo.setCpno(cpno);
		// cvo.setSpno(spno);
		// cvo.setLocno(new Integer(locnoStr));
		// cvo.setDiscount(new Integer(discountStr));
		// cvo.setMinqty(new Integer(minqtyStr));
		// cvo.setMaxqty(new Integer(maxqtyStr));
		// cvo.setShip1(ship1);
		// cvo.setShipcost1(new Integer(shipcost1Str));
		// if (hasShip2) {
		// cvo.setShip2(ship2);
		// cvo.setShipcost2(new Integer(shipcost2Str));
		// }
		// if (!addNew) {
		// cvo.setCaseno(caseno);
		// }
		// cvo.setCasedesc(casedesc);
		CasesService cSvc = new CasesService();
		Integer locno = new Integer(locnoStr);
		Integer discount = new Integer(discountStr);
		Integer minqty = new Integer(minqtyStr);
		Integer maxqty = new Integer(maxqtyStr);
		Integer shipcost1 = new Integer(shipcost1Str);
		Integer shipcost2 = new Integer(shipcost2Str);
		Integer threshold = 0;

		if (addNew) {
			Integer genKey = cSvc.addCase(title, memvo.getMemno(), cpno, spno,
					locno, discount, minqty, maxqty, casedesc, ship1,
					shipcost1, ship2, shipcost2, threshold);
			if (genKey == 0) {
				return CaseAjaxRes.UPDATE_FAILED;
			} else {
				request.setAttribute("genKey", genKey);
			}
		} else {
			int updateCount = cSvc.editCase(caseno, title, locno, discount,
					minqty, maxqty, casedesc, ship1, shipcost1, ship2,
					shipcost2, threshold);
			if (updateCount != 1) {
				return CaseAjaxRes.UPDATE_FAILED;
			}
		}

		return CaseAjaxRes.SUCCESS;
	}

	private void ajaxChangeCaseDetailPage(HttpServletRequest request,
			HttpServletResponse response, MemberVO memvo)
			throws ServletException, IOException {
		// MemberVO memvo = (MemberVO) request.getSession().getAttribute(
		// MEMBER_VO_ATTRIBUTE_KEY);

		// param: hide = canceled
		String hide = request.getParameter("hide");
		boolean includeCanceled = true;
		if ("canceled".equals(hide)) {
			includeCanceled = false;
		}

		CasesService cSvc = new CasesService();
		Set<CasesVO> cSet = cSvc.getByCreator(memvo.getMemno(),
				includeCanceled, false);
		List<CasesVO> cList = new ArrayList<>(cSet);
		Collections.reverse(cList);

		if (cList.size() > 10) {
			Integer totalPageNums = cList.size() / 10
					+ (cList.size() % 10 == 0 ? 0 : 1);
			request.setAttribute("totalPageNums", totalPageNums);

			// param: pageNum = XX
			String pageNumStr = request.getParameter("pageNum");
			int pageNum = 1;
			if (pageNumStr != null && pageNumStr.matches("\\d+")) {
				int itemp = Integer.parseInt(pageNumStr);
				System.out.println("pageNum=" + itemp);
				if (itemp > 1) {
					pageNum = itemp;
				}
				if (pageNum > totalPageNums) {
					pageNum = totalPageNums;
				}
			}
			request.setAttribute("pageNum", new Integer(pageNum));
			int fromIndex = (pageNum - 1) * 10;
			int toIndex = (pageNum - 1) * 10 + 10;
			if (cList.size() < toIndex) {
				toIndex = cList.size();
			}
			cList = cList.subList(fromIndex, toIndex);
		}
		request.setAttribute("caseList", cList);
		request.getRequestDispatcher(AJAX_OWN_CASES).forward(request, response);
	}

	private void doPrepareAddOrEdit(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		MemberVO memvo = (MemberVO) session
				.getAttribute(MEMBER_VO_ATTRIBUTE_KEY);
		CasesVO cvo = null;
		String casenoStr = request.getParameter("caseno");

		if (!invalidCaseno(casenoStr)) {
			Integer caseno = new Integer(casenoStr);
			CasesService cSvc = new CasesService();
			cvo = cSvc.getByPrimaryKey(caseno);
			if (cvo == null) {
				System.out.println("doEdit: could not find case");
				doViewOwnCases(request, response);
				return;
			}
			if (cvo.getMemno().intValue() != memvo.getMemno().intValue()) {
				System.out.println(cvo.getMemno());
				System.out.println(memvo.getMemno());
				System.out
						.println("doEdit: case does not belong to current user");
				doViewOwnCases(request, response);
				return;
			}
			if (cvo.getStatus() != CasesVO.STATUS_CREATED) {
				System.out.println("doEdit: case status is not STATUS_CREATED");
				doViewOwnCases(request, response);
				return;
			}
		}

		if (cvo != null) {
			int locno = cvo.getLocno();
			CountyService countyService = new CountyService();
			Set<CountyVO> countSet = countyService.findCounties();
			CountyVO countyVO = null;
			for (CountyVO vo : countSet) {
				if (vo.getFrom() <= locno && vo.getTo() >= locno) {
					countyVO = vo;
					break;
				}
			}
			LocationService locationService = new LocationService();
			Set<LocationVO> towns = locationService.findByCounty(
					countyVO.getFrom(), countyVO.getTo());
			LocationVO locvo = new LocationVO();
			locvo.setLocno(cvo.getLocno());
			request.setAttribute("countyVO", countyVO);
			request.setAttribute("towns", towns);// for select drop-down town
													// list
			request.setAttribute("locvo", locvo);// for select drop-down town
													// list
			if (cvo.getSpno().intValue() > 2001
					&& memvo.getType().intValue() == 0) {
				request.setAttribute("spvo", new ShopproductService()
						.getOneShopproduct(cvo.getSpno()));
			}
			request.setAttribute("cvo", cvo);
		}

		String spnoStr = request.getParameter("spno");
		if (memvo.getType() == 0) {
			if (spnoStr != null && spnoStr.matches("\\d{4,}")) {// add or create
				ShopproductVO spvo = new ShopproductService()
						.getOneShopproduct(new Integer(spnoStr));
				if (spvo != null) {
					request.setAttribute("spvo", spvo);
				}
			}
			List<CaseProductVO> cpList = new CaseProductService().getByOwnerNo(
					memvo.getMemno(), false);
			request.setAttribute("cpList", cpList);
		} else if (memvo.getType() == 1) {
			ShopproductService spSvc = new ShopproductService();
			Map<String, String[]> map = new HashMap<>();
			map.put("memno", new String[] { memvo.getMemno().toString() });
			map.put("status", new String[] { "2" });
			List<ShopVO> shopList = new ShopService().getAll(map);
			if (shopList.size() != 0) {
				List<ShopproductVO> spList = spSvc.getByshop(shopList.get(0)
						.getShopno());
				for (int i = 1; i < shopList.size(); i++) {
					spList.addAll(spSvc.getByshop(shopList.get(i).getShopno()));
				}
				request.setAttribute("spList", spList);
			} else {
				request.setAttribute("NoActiveShop", "NoActiveShop");
			}

		} else {
			doViewOwnCases(request, response);
			return;
		}
		request.getRequestDispatcher(ADD_OR_EDIT_CASE).forward(request,
				response);
	}

	private void doPreview(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String casenoStr = request.getParameter("caseno");
		if (invalidCaseno(casenoStr)) {
			System.out.println("doPreview: invalid caseno");
			return;
		}
		Integer caseno = new Integer(casenoStr);
		CasesService cSvc = new CasesService();
		CasesVO cvo = cSvc.getByPrimaryKey(caseno);
		if (cvo == null) {
			System.out.println("doPreview: could not find case");
			return;
		}
		if (cvo.getStatus() != CasesVO.STATUS_CREATED) {
			System.out.println("doPreview: case status is not STATUS_CREATED");
			return;
		}

		if (cvo.getCpno() != 0) {
			CaseProductService cpSvc = new CaseProductService();
			CaseProductVO cpvo = cpSvc.getOneByPrimaryKeyNoPic(cvo.getCpno());
			request.setAttribute("cpvo", cpvo);
		} else if (cvo.getSpno() != 0) {
			ShopproductService spSvc = new ShopproductService();
			ShopproductVO spvo = spSvc.getOneShopproduct(cvo.getSpno());
			request.setAttribute("spvo", spvo);
		} else {
			System.out
					.println("doPreview: CasesVO does not contain valid cpno or spno.");
			return;
		}
		TinyMemberService tmemSvc = new TinyMemberService();
		LocationService locSvc = new LocationService();
		Set<TinyMemberVO> tmemSet = tmemSvc.getAll();
		LocationVO lvo = locSvc.findByPrimaryKey(cvo.getLocno());
		request.setAttribute("tmemset", tmemSet);
		request.setAttribute("lvo", lvo);
		request.setAttribute("cvo", cvo);
		request.getRequestDispatcher(PREVIEW_CASE).forward(request, response);
	}

	private void doViewOwnCases(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		MemberVO memvo = (MemberVO) request.getSession().getAttribute(
				MEMBER_VO_ATTRIBUTE_KEY);
		if (memvo == null) {
			redirectToLogin(request, response);
			return;
		}
		String hide = request.getParameter("hide");
		boolean includeCanceled = true;
		if ("canceled".equals(hide)) {
			includeCanceled = false;
		}

		CasesService cSvc = new CasesService();
		Set<CasesVO> cSet = cSvc.getByCreator(memvo.getMemno(),
				includeCanceled, false/* includeDeleted */);
		List<CasesVO> cList = new ArrayList<>(cSet);
		Collections.reverse(cList);

		if (cList.size() > 10) {
			Integer totalPageNums = cList.size() / 10
					+ (cList.size() % 10 == 0 ? 0 : 1);
			request.setAttribute("totalPageNums", totalPageNums);

			// param: pageNum = XX
			String pageNumStr = request.getParameter("pageNum");
			int pageNum = 1;
			if (pageNumStr != null && pageNumStr.matches("\\d+")) {
				int itemp = Integer.parseInt(pageNumStr);
				System.out.println("pageNum=" + itemp);
				if (itemp > 1) {
					pageNum = itemp;
				}
				if (pageNum > totalPageNums) {
					pageNum = totalPageNums;
				}
			}
			request.setAttribute("pageNum", new Integer(pageNum));
			int fromIndex = (pageNum - 1) * 10;
			int toIndex = (pageNum - 1) * 10 + 10;
			if (cList.size() < toIndex) {
				toIndex = cList.size();
			}
			cList = cList.subList(fromIndex, toIndex);
		}
		request.setAttribute("caseList", cList);
		// if ("ajax".equals(request.getParameter("from"))) {
		// request.getRequestDispatcher(AJAX_OWN_CASES).forward(request,
		// response);
		// } else {
		request.getRequestDispatcher(VIEW_OWN_CASES).forward(request, response);
		// }
	}

	private void doShowCaseDetail(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		String param = req.getParameter("caseno");
		if (param == null || !param.matches("\\d{4,}")) {
			resp.sendRedirect(default_redirect);
			return;
		}
		// System.out.println("Parameter caseno Validated");
		Integer caseno = new Integer(param);

		/********** Prepare Services **********/
		CasesService cSvc = new CasesService();
		TinyMemberService tmemSvc = new TinyMemberService();
		OrderService ordSvc = new OrderService();
		CaseQAService qaSvc = new CaseQAService();
		LocationService locSvc = new LocationService();
		WishListService wlSvc = new WishListService();
		SubCategoryService subcatSvc = new SubCategoryService();
		CategoryService catSvc = new CategoryService();

		/********** Acquire Data **********/
		CasesVO cvo = cSvc.getByPrimaryKey(caseno);
		if (cvo == null) {
			return;
		}
		// get case product vo
		CaseProductVO cpvo = null;
		if (cvo.getCpno() > 0) {
			CaseProductService cpSvc = new CaseProductService();
			cpvo = cpSvc.getOneByPrimaryKey(cvo.getCpno(), false);
			req.setAttribute("cpvo", cpvo);
			SubCategoryVO subcatvo = subcatSvc.getOneSubCategory(cpvo
					.getSubcatno());
			req.setAttribute("subcatvo", subcatvo);
			CategoryVO catvo = catSvc.getOneCategory(subcatvo.getCatno());
			req.setAttribute("catvo", catvo);
			subcatSvc = null;
			catSvc = null;
		}
		// or get shop product vo
		ShopproductVO spvo = null;
		if (cvo.getSpno() > 0) {
			ShopproductService spSvc = new ShopproductService();
			spvo = spSvc.getOneShopproduct(cvo.getSpno());
			req.setAttribute("spvo", spvo);
			SubCategoryVO subcatvo = subcatSvc.getOneSubCategory(spvo
					.getSubcatno());
			req.setAttribute("subcatvo", subcatvo);
			CategoryVO catvo = catSvc.getOneCategory(subcatvo.getCatno());
			req.setAttribute("catvo", catvo);
			subcatSvc = null;
			catSvc = null;
		}
		// acquire memvo
		MemberVO memvo = (MemberVO) req.getSession().getAttribute(
				MEMBER_VO_ATTRIBUTE_KEY);
		// decide if "add to list" btn should be enabled
		if (memvo == null
				|| (memvo.getType() != 1 && !wlSvc.hasWish(memvo.getMemno(), caseno) && cvo.getMemno()
						.intValue() != memvo.getMemno().intValue())) {
			req.setAttribute("WihsListBtnEnable", new Object());
		}

		// get TinyMemberVOs
		Set<TinyMemberVO> tmemSet = tmemSvc.getAll();
		// get order list
		List<OrderVO> ordlist = ordSvc.findByCaseExcludeStatus(caseno,
				OrderDAO.STATUS_CANCELED);
		Collections.reverse(ordlist);
		// get case QAs
		List<CaseQAVO> qalist = qaSvc.findByCaseNo(caseno, CaseQADAO.ANSWERED);
		Collections.reverse(qalist);// reverse order
		// get locations
		LocationVO lvo = locSvc.findByPrimaryKey(cvo.getLocno());

		/********** Process Data ********/

		int sum = 0;
		for (OrderVO ovo : ordlist) {
			sum += ovo.getQty();
		}

		/********** Set Attributes **********/
		req.setAttribute("cvo", cvo);
		req.setAttribute("tmemset", tmemSet);
		req.setAttribute("ordlist", ordlist);
		req.setAttribute("qalist", qalist);
		req.setAttribute("lvo", lvo);
		req.setAttribute("sum", new Integer(sum));
		/********** Forward to view **********/
		// System.out.println("Before Dispatch");
		req.getRequestDispatcher(VIEW_CASE).forward(req, resp);
	}

	private void redirectToLogin(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.getSession().setAttribute("location",
				request.getRequestURI() + "?" + request.getQueryString());
		response.sendRedirect(login_page);
	}

	private boolean invalidCaseno(String casenoStr) {
		return (casenoStr == null || !casenoStr.matches("\\d{4,}"));
	}

	private boolean invalidTimeParam(String edate, String etime) {
		try {
			Timestamp.valueOf(edate + " " + etime + ":00");
		} catch (Exception e) {
			return true;
		}
		return false;
	}

}
