package com.tao.order.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.tao.cases.model.CaseProductService;
import com.tao.cases.model.CaseProductVO;
import com.tao.cases.model.CasesService;
import com.tao.cases.model.CasesVO;
import com.tao.jimmy.util.AjaxMsg;
import com.tao.jimmy.util.FrontSessionAttrUtil;
import com.tao.jimmy.util.Simulation;
import com.tao.member.model.MemberService;
import com.tao.member.model.MemberVO;
import com.tao.order.model.OrderDAO;
import com.tao.order.model.OrderService;
import com.tao.order.model.OrderVO;
import com.tao.shopproduct.model.ShopproductService;
import com.tao.shopproduct.model.ShopproductVO;

public class OrderFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String MEMBER_VO_ATTRIBUTE_KEY = FrontSessionAttrUtil.MEMBER_VO_ATTRIBUTE_KEY;
	private static final String LOCATION_ATTRIBUTE_KEY = FrontSessionAttrUtil.LOCATION_ATTRIBUTE_KEY;
	// For Redirect
	private static String login_page = FrontSessionAttrUtil.LOGIN_PAGE;
	private static String deposit_page = FrontSessionAttrUtil.DEPOSIT_MONEY_PAGE;
	private static String cases_front_controller = "/cases/cases.do";
	// For Forward
	private static final String ANDROID_OUTPUT = "/order/OrdResAndroidOutputServlet";
	private static final String WEB_OUTPUT = "/order/OrdResWebOutputServlet";
	private static final String VIEW_OWN_ORDERS = "/order/ViewOwnOrders.jsp";
	private static final String VIEW_CASE_ORDERS = "/order/ViewCaseOrders.jsp";

	private static final Object WRITE_ORDER_LOCK = new Object();
	private static boolean isSimulation = false;

	@Override
	public void init() throws ServletException {
		super.init();
		String contextPath = getServletContext().getContextPath();
		login_page = contextPath + login_page;
		deposit_page = contextPath + deposit_page;
		cases_front_controller = contextPath + cases_front_controller;
		isSimulation = Simulation.isSimulation();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// System.out.println("OrderFrontController: doGet()");

		String redir = request.getParameter("redir");
		if (redir != null && redir.trim().length() != 0) {
			String caseno = request.getParameter("caseno");
			if ("deposit".equals(redir)) {
				request.getSession().setAttribute(
						LOCATION_ATTRIBUTE_KEY,
						cases_front_controller + "?action=caseDetail&caseno="
								+ caseno);
				response.sendRedirect(deposit_page);
			}
			if ("login".equals(redir)) {
				request.getSession().setAttribute(
						LOCATION_ATTRIBUTE_KEY,
						cases_front_controller + "?action=caseDetail&caseno="
								+ caseno);
				response.sendRedirect(login_page);
			}
			return;
		}

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// System.out.println("OrderFrontController: doPost()");
		String action = request.getParameter("action");

		// action=ask, pre-check before pop-up order modal window
		if ("ask".equals(action)) {
			/** Filter out invalid caseno **/
			String casenoStr = request.getParameter("caseno");
			if (invalidCaseno(casenoStr)) {
				return;
			}

			MemberVO memvo = (MemberVO) request.getSession().getAttribute(
					MEMBER_VO_ATTRIBUTE_KEY);
			AjaxMsg msg = new AjaxMsg();

			if (memvo == null) { // redirect to login
				request.getSession().setAttribute(
						LOCATION_ATTRIBUTE_KEY,
						request.getContextPath()
								+ "/cases/cases.do?action=caseDetail&caseno="
								+ casenoStr);
				msg.setStatus(AjaxMsg.REDIRECT);
				msg.setResUrl(login_page);
			} else {
				CasesService cSvc = new CasesService();
				OrderService oSvc = new OrderService();

				Integer caseno = new Integer(casenoStr);
				CasesVO cvo = cSvc.getByPrimaryKey(caseno);
				List<OrderVO> ovolist = oSvc.findByCase(caseno,
						OrderDAO.STATUS_CREATED);
				int sum = 0;
				for (OrderVO o : ovolist) {
					sum += o.getQty();
				}
				int avaliableAmount = cvo.getMaxqty() - sum;
				Map<String, String> info = new HashMap<>();
				info.put("amount", Integer.toString(avaliableAmount));
				msg.setInfo(info);
				msg.setStatus(AjaxMsg.SUCCESS);
			}
			Gson gson = new Gson();
			String json = gson.toJson(msg);
			System.out.println("OrderFrontController::action=ask: " + json);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().println(json);
			return;
		}

		// action=order, make a new order
		if ("order".equals(action)) {
			/*** Simulation ***/
			if (isSimulation) {
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			/*** Simulation ***/
			String memnoStr = request.getParameter("memno");
			if (memnoStr != null && memnoStr.matches("\\d{4,}")) {
				Integer memno = new Integer(memnoStr);
				MemberVO memvo = (new MemberService()
						.findByPrimaryKeyNoPic(memno));
				request.getSession().setAttribute(MEMBER_VO_ATTRIBUTE_KEY,
						memvo);
			}
			OrderAjaxRes resStatus = doOrder(request, response);
			request.setAttribute("resStatus", resStatus);
			String from = request.getParameter("from");
			if ("android".equals(from)) {
				request.getRequestDispatcher(ANDROID_OUTPUT).forward(request,
						response);
				return;
			}
			request.getRequestDispatcher(WEB_OUTPUT).forward(request, response);
			return;
		}

		// action=viewOwnOrders, accessed from member center menu
		if ("viewOwnOrders".equals(action)) {
			doViewOwnOrders(request, response);
			return;
		}

		if ("viewCaseOrders".equals(action)) {
			doViewCaseOrders(request, response);
			return;
		}

		// action=ajax, support method of viewOwnOrders
		if ("ajax".equals(action)) {
			// System.out.println("action=ajax");
			String what = request.getParameter("what");
			// System.out.println("what=" + what);
			OrderAjaxRes res = null;
			if ("getOrdDetail".equals(what)) {
				res = ajaxGetOrdDetail(request, response);
			}

			if ("cancel".equals(what)) {
				/*** Simulation ***/
				if (isSimulation) {
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				/*** Simulation ***/
				res = ajaxDoCancel(request, response);
			}

			if ("rate".equals(what)) {
				/*** Simulation ***/
				if (isSimulation) {
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				/*** Simulation ***/
				res = ajaxDoRrate(request, response);
			}

			if ("confirmOrder".equals(what)) {
				/*** Simulation ***/
				if (isSimulation) {
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				/*** Simulation ***/
				res = ajaxDoConfirmOrder(request, response);
			}
			if ("report".equals(what)) {
				/*** Simulation ***/
				if (isSimulation) {
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				/*** Simulation ***/
				res = ajaxDoReportOrder(request, response);
			}
			request.setAttribute("AjaxRes", res);
			request.getRequestDispatcher(WEB_OUTPUT).forward(request, response);
			return;
		}

		System.out.println("OrderFrontController: END OF doPost()");
	}

	private OrderAjaxRes ajaxGetOrdDetail(HttpServletRequest request,
			HttpServletResponse response) {
		String ordnoStr = request.getParameter("ordno");
		if (invalidOrdno(ordnoStr)) {
			return OrderAjaxRes.ERROR;
		}
		Integer ordno = new Integer(ordnoStr);
		OrderService ordSvc = new OrderService();
		CasesService cSvc = new CasesService();
		OrderVO ordvo = ordSvc.findByPrimaryKey(ordno);
		CasesVO cvo = cSvc.getByPrimaryKey(ordvo.getCaseno());
		/** MAY CAUSE ERROR **/
		if (cvo == null || ordvo == null) {
			return OrderAjaxRes.ERROR;
		}
		/** MAY CAUSE ERROR **/
		request.setAttribute("ordvo", ordvo);
		request.setAttribute("cvo", cvo);
		return OrderAjaxRes.SUCCESS;

	}

	private OrderAjaxRes ajaxDoCancel(HttpServletRequest request,
			HttpServletResponse response) {
		String ordnoStr = request.getParameter("ordno");
		if (invalidOrdno(ordnoStr)) {
			return OrderAjaxRes.ERROR;
		}
		OrderService ordSvc = new OrderService();
		int updateCount = ordSvc.cancelOrder(new Integer(ordnoStr));
		if (updateCount == 0) {
			return OrderAjaxRes.ERROR;
		}
		return OrderAjaxRes.SUCCESS;
	}

	private OrderAjaxRes ajaxDoRrate(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8"); // accessed via POST request
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String ordnoStr = request.getParameter("ordno");
		String rateStr = request.getParameter("rate");
		String who = request.getParameter("who");// who's rate is going to be
													// update
		String ratedesc = request.getParameter("ratedesc");
		if (invalidOrdno(ordnoStr) || invalidWho(who) || invalidRate(rateStr)
				|| invalidDesc(ratedesc)) {
			return OrderAjaxRes.ERROR;
		}
		Integer ordno = new Integer(ordnoStr);
		Integer rate = new Integer(rateStr);
		OrderService ordSvc = new OrderService();
		int updateCount = 0;
		if ("creator".equals(who)) {
			updateCount = ordSvc.updateCreatorRate(rate, ratedesc, ordno);
		}
		if ("buyer".equals(who)) {
			updateCount = ordSvc.updateBuyerRate(rate, ratedesc, ordno);
		}
		if (updateCount != 1) {
			return OrderAjaxRes.ERROR;
		}
		return OrderAjaxRes.SUCCESS;
	}

	private OrderAjaxRes ajaxDoConfirmOrder(HttpServletRequest request,
			HttpServletResponse response) {
		String ordnoStr = request.getParameter("ordno");
		String who = request.getParameter("who");
		if (invalidOrdno(ordnoStr) || invalidWho(who)) {

			return OrderAjaxRes.ERROR;
		}
		Integer ordno = new Integer(ordnoStr);
		OrderService ordSvc = new OrderService();
		int updateCount = 0;
		if ("buyer".equals(who)) {
			updateCount = ordSvc.updateStatus(ordno,
					OrderDAO.STATUS_BUYER_COMFIRMED);
		}
		if ("creator".equals(who)) {
			updateCount = ordSvc.updateStatus(ordno,
					OrderDAO.STATUS_CREATOR_COMFIRMED);
		}
		if (updateCount != 1) {
			return OrderAjaxRes.ERROR;
		}
		return OrderAjaxRes.SUCCESS;
	}

	private OrderAjaxRes ajaxDoReportOrder(HttpServletRequest request,
			HttpServletResponse response) {
		String ordnoStr = request.getParameter("ordno");
		if (invalidOrdno(ordnoStr)) {
			return OrderAjaxRes.ERROR;
		}
		Integer ordno = new Integer(ordnoStr);
		OrderService ordSvc = new OrderService();
		int updateCount = ordSvc.updateStatus(ordno, OrderDAO.STATUS_CONFLICT);
		if (updateCount != 1) {
			return OrderAjaxRes.ERROR;
		}
		return OrderAjaxRes.SUCCESS;
	}

	private void doViewCaseOrders(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		System.out.println("OrderFrontController::doViewOwnOrders");
		MemberVO memvo = (MemberVO) request.getSession().getAttribute(
				MEMBER_VO_ATTRIBUTE_KEY);
		if (memvo == null) {
			redirectToLogin(request, response);
			return;
		}
		CasesService cSvc = new CasesService();
		Set<CasesVO> cvoSet = cSvc.getByCreator(memvo.getMemno(), false, false);
		List<CasesVO> cvolist = new ArrayList<>(cvoSet);
		Collections.reverse(cvolist);
		Map<CasesVO, List<OrderVO>> caseMap = new LinkedHashMap<>();
		OrderService ordSvc = new OrderService();
		for (CasesVO cvo : cvolist) {
			if (cvo.getStatus() == CasesVO.STATUS_CREATED) {
				continue;
			}
			List<OrderVO> ordvoList = ordSvc.findByCaseExcludeStatus(
					cvo.getCaseno(), OrderDAO.STATUS_CANCELED);
			caseMap.put(cvo, ordvoList);
		}
		request.setAttribute("caseMap", caseMap);
		request.getRequestDispatcher(VIEW_CASE_ORDERS).forward(request,
				response);
	}

	private void doViewOwnOrders(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		System.out.println("OrderFrontController::doViewOwnOrders");
		MemberVO memvo = (MemberVO) request.getSession().getAttribute(
				MEMBER_VO_ATTRIBUTE_KEY);
		if (memvo == null) {
			redirectToLogin(request, response);
			return;
		}
		// System.out.println("memno: " + memvo.getMemno());
		OrderService ordSvc = new OrderService();
		List<OrderVO> ordList = ordSvc.findByBuyerNotCanceled(memvo.getMemno());
		Collections.reverse(ordList);// ORDER BY ordno DESC
		// System.out.println("ordList: " + ordList.size());
		CasesService cSvc = new CasesService();
		Set<CasesVO> casesSet = cSvc.getAllByOrderList(ordList);
		request.setAttribute("ordList", ordList);
		request.setAttribute("casesSet", casesSet);
		request.getRequestDispatcher(VIEW_OWN_ORDERS)
				.forward(request, response);

	}

	private OrderAjaxRes doOrder(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		MemberVO memvo = null;
		try {
			memvo = (MemberVO) request.getSession().getAttribute(
					MEMBER_VO_ATTRIBUTE_KEY);
		} catch (ClassCastException e) {
			System.out.println(e);
		}
		if (memvo == null) {
			return OrderAjaxRes.TO_LOGIN;
		}

		String casenoStr = request.getParameter("caseno");
		String qtyStr = request.getParameter("qty");
		String shipStr = request.getParameter("ship");
		System.out.println("::doOrder: caseno=" + casenoStr);
		System.out.println("::doOrder: qty=" + qtyStr);
		System.out.println("::doOrder: ship=" + shipStr);
		if (invalidCaseno(casenoStr) || invalidQty(qtyStr)
				|| invalidShip(shipStr)) {
			return OrderAjaxRes.ERROR;
		}
		Integer qty = new Integer(qtyStr);
		Integer caseno = new Integer(casenoStr);
		Integer ship = new Integer(shipStr);
		CasesService cSvc = new CasesService();
		CasesVO cvo = cSvc.getByPrimaryKey(caseno);
		/** Input Validation **/
		if (cvo == null
				|| (cvo.getStatus() != CasesVO.STATUS_PUBLIC && cvo.getStatus() != CasesVO.STATUS_PRIVATE)) {
			return OrderAjaxRes.CASE_NOT_AVALIBLE;
		}

		/** Calculate Price **/
		Integer cpno = cvo.getCpno();
		Integer spno = cvo.getSpno();
		Integer price = 0;

		if (cpno > 0) {
			CaseProductService cpSvc = new CaseProductService();
			CaseProductVO cpvo = cpSvc.getOneByPrimaryKeyNoPic(cpno);
			price = (cpvo.getUnitprice() - cvo.getDiscount()) * qty;
		} else if (spno > 0) {
			ShopproductService spSvc = new ShopproductService();
			ShopproductVO spvo = spSvc.getOneShopproduct(spno);
			/****** CAUTION *******/
			price = (int) ((spvo.getUnitprice() - cvo.getDiscount()) * qty);
			/****** CAUTION *******/
		}

		if (price <= 0) {
			return OrderAjaxRes.ERROR;
		} else {
			if (ship == 1)
				price += cvo.getShipcost1();
			if (ship == 2)
				price += cvo.getShipcost2();
		}
		MemberService memSvc = new MemberService();
		memvo = memSvc.findByPrimaryKeyNoPic(memvo.getMemno());// get money
		request.getSession().setAttribute(MEMBER_VO_ATTRIBUTE_KEY, memvo);

		/** Check Avaliable Money **/
		int avaliableMoney = memvo.getMoney()/* - memvo.getWithhold()*/;
		if (avaliableMoney < price) {
			return OrderAjaxRes.TO_DEPOSIT;
		}

		OrderService ordSvc = new OrderService();

		/******* SYNCHRONIZED DATABASE ACCESS ********/

		synchronized (WRITE_ORDER_LOCK) {
			Integer sum = ordSvc.getTotalOrderQty(caseno);
			int avalibleStock = cvo.getMaxqty() - sum;

			if (avalibleStock < qty) {
				return OrderAjaxRes.OUT_OF_STOCK;
			}

			Integer genKey = ordSvc.addOrder(memvo.getMemno(), cvo.getMemno(),
					caseno, qty, price,
					new Timestamp(System.currentTimeMillis()), ship);
			System.out.println("OrderFrontController: order added, genKey="
					+ genKey + " (0 = operation failed)");
			if (genKey == 0) {
				return OrderAjaxRes.ERROR;
			}
			request.setAttribute("genKey", genKey);
		}

		/******* SYNCHRONIZED DATABASE ACCESS ********/

		return OrderAjaxRes.SUCCESS;
	}

	private void redirectToLogin(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.getSession().setAttribute("location",
				request.getRequestURI() + "?" + request.getQueryString());
		response.sendRedirect(login_page);
	}

	private boolean invalidShip(String shipStr) {
		return (shipStr == null || !shipStr.matches("[12]"));
	}

	private boolean invalidCaseno(String caseno) {
		return (caseno == null || !caseno.matches("\\d{4,}"));
	}

	private boolean invalidOrdno(String ordno) {
		return (ordno == null || !ordno.matches("\\d{4,}"));
	}

	private boolean invalidQty(String qty) {
		int i = 0;
		try {
			i = Integer.parseInt(qty);
		} catch (Exception e) {
			return true;
		}
		return i <= 0;
	}

	private boolean invalidWho(String who) {
		return (who == null || who.trim().length() == 0 || !("buyer"
				.equals(who) || "creator".equals(who)));
	}

	private boolean invalidRate(String rateStr) {
		return (rateStr == null || !rateStr.matches("[012]"));
	}

	private boolean invalidDesc(String ratedesc) {
		return (ratedesc == null || ratedesc.trim().length() == 0);
	}

}
