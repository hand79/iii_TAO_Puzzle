package com.tao.order.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.tao.cases.model.CasesService;
import com.tao.cases.model.CasesStatus;
import com.tao.cases.model.CasesVO;
import com.tao.jimmy.util.AjaxMsg;
import com.tao.jimmy.util.model.ColumnValueBundle;
import com.tao.order.model.OrderDAO;
import com.tao.order.model.OrderParser;
import com.tao.order.model.OrderService;
import com.tao.order.model.OrderStatus;
import com.tao.order.model.OrderVO;
import com.tao.task.OrderFinishTask;

public class OrderBackController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrderService orderSvc = new OrderService();
	private OrderParser parser = new OrderParser();
	private static String default_view = "";// Initialize later
	private static final String AJAX_QUERY = "/order/Ajax_orderTable.jsp";
	private static final String MANAGE_ORDERS = "/order/ManageOrders.jsp";

	@Override
	public void init() throws ServletException {
		super.init();
		default_view = getServletContext().getContextPath() + default_view;
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String view = request.getParameter("view");
		if ("default".equals(view)) {
			request.getRequestDispatcher(MANAGE_ORDERS).forward(request,
					response);
			return;
		}

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		switch (action) {
		case "ajax":
			doAjax(request, response);
			break;
		case "compositeQuery":
			doCompositeQuery(request, response);
			break;
		case "handleConflic":
			doHandleConflic(request, response);
			break;
		case "triggerFinishOrder":
			doFinishOrder(request, response);
			break;
		default:
			response.sendRedirect(default_view);
			break;
		}

	}

	private void doFinishOrder(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		OrderFinishTask task= (OrderFinishTask) getServletContext().getAttribute("OrderFinishTask");
		boolean success = true;
		try{
			task.run();
		}catch(Throwable t){
			t.printStackTrace();
			success = false;
		}
		AjaxMsg msg = new AjaxMsg();
		StringBuilder sb = new StringBuilder();
		if(success){
		msg.setStatus(AjaxMsg.SUCCESS);
			sb.append("<p>")
			.append("<i class=\"fa fa-check-circle-o \" style=\"font-size:2em; color:#FE980F;\"></i> ")
			.append("操作完成").append("</p>");
		}else{
			msg.setStatus(AjaxMsg.ERROR);
			sb.append("<p>")
			.append("<i class=\"fa fa-times-circle-o \" style=\"font-size:2em; color:#FE980F;\"></i> ")
			.append("發生錯誤").append("</p>");
		}
		msg.setResHtml(sb.toString());
		String json = new Gson().toJson(msg);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json);
	}

	private void doHandleConflic(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		boolean success = true;
		String ordnoStr = request.getParameter("ordno");
		String statusStr = request.getParameter("status");
		if (invalidOrdno(ordnoStr) || invalidStatus(statusStr)) {
			success = false;
		}
		if (success) {
			OrderService ordSvc = new OrderService();
			Integer ordno = new Integer(ordnoStr);
			int status = Integer.parseInt(statusStr);
			if (ordSvc.findByPrimaryKey(ordno).getStatus() != OrderDAO.STATUS_CONFLICT) {
				success = false;
			} else {
				int updateCount =0;
				switch(status){
				case OrderDAO.STATUS_ACHIEVED:
				case OrderDAO.STATUS_BOTH_COMFIRMED:
				case OrderDAO.STATUS_BUYER_COMFIRMED:
				case OrderDAO.STATUS_CREATOR_COMFIRMED:
					 updateCount = ordSvc.updateStatus(ordno, status);
					break;
				case OrderDAO.STATUS_CANCELED:
					updateCount = ordSvc.cancelOrder(ordno);
					break;
				default:
					success = false;
				}
				if (updateCount != 1) {
					success = false;
				}
			}
		}
		AjaxMsg msg = new AjaxMsg();
		StringBuilder sb = new StringBuilder();
		if (success) {
			msg.setStatus(AjaxMsg.SUCCESS);
			sb.append("<p>")
			.append("<i class=\"fa fa-check-circle-o \" style=\"font-size:2em; color:#FE980F;\"></i> ")
			.append("操作成功").append("</p>");
			
		}else{
			msg.setStatus(AjaxMsg.ERROR);
			sb.append("<p>")
			.append("<i class=\"fa fa-times-circle-o \" style=\"font-size:2em; color:#FE980F;\"></i> ")
			.append("發生錯誤").append("</p>");
		}
		msg.setResHtml(sb.toString());
		String json = new Gson().toJson(msg);
//		System.out.println("::doHandleConflic: response json="+json);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json);
	}

	private boolean invalidStatus(String statusStr) {
		if (statusStr == null || !statusStr.matches("\\d{1,1}")) {
			return true;
		}
		int s = Integer.parseInt(statusStr);
		for (int i : OrderStatus.getAllStatusValues()) {
			if (i == s) {
				return false;
			}
		}
		return true;
	}

	private void doAjax(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doAjax!!");
		String what = request.getParameter("what");
		if ("getAll".equals(what)) {

			List<OrderVO> list = orderSvc.getAll();
			request.setAttribute("Ajax_orderTable", list);
			request.getRequestDispatcher(AJAX_QUERY).forward(request, response);
		}
		if ("getOrdDetail".equals(what)) {
			OrderAjaxRes res = doGetOrdDetail(request, response);
			AjaxMsg msg = new AjaxMsg();
			switch (res) {
			case SUCCESS:
				CasesVO cvo = (CasesVO) request.getAttribute("cvo");
				OrderVO ordvo = (OrderVO) request.getAttribute("ordvo");
				Map<String, String> info = new HashMap<>();
				info.put("title", cvo.getTitle());
				info.put("qty", Integer.toString(ordvo.getQty()));
				info.put("shipcost",
						Integer.toString(ordvo.getShip() == 1 ? cvo
								.getShipcost1() : cvo.getShipcost2()));
				info.put("ship",
						ordvo.getShip() == 1 ? cvo.getShip1() : cvo.getShip2());
				info.put("price", Integer.toString(ordvo.getPrice()));
				info.put("ordtime", ordvo.getFormatedOrdtime());
				info.put("status", OrderStatus.getStatusName(ordvo.getStatus()));
				info.put("caseno", Integer.toString(ordvo.getCaseno()));
				msg.setStatus(AjaxMsg.SUCCESS);
				msg.setInfo(info);
				break;
			case ERROR:
				msg.setStatus(AjaxMsg.ERROR);
				break;
			default:
				return;
			}
			Gson gson = new Gson();
			String json = gson.toJson(msg);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().println(json);
		}
	}

	private OrderAjaxRes doGetOrdDetail(HttpServletRequest request,
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
		request.setAttribute("ordvo", ordvo);
		request.setAttribute("cvo", cvo);
		return OrderAjaxRes.SUCCESS;
	}

	private void doCompositeQuery(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doCompositeQuery");
		Map<String, String[]> paramMap = request.getParameterMap();
		Map<String, String[]> outputMap = new HashMap<>();
		Set<String> keySet = paramMap.keySet();
		String stime = " ";
		String etime = " ";
		List<String> errMsg = new ArrayList<>();
		boolean inputError = false;
		for (String key : keySet) {
			String value = paramMap.get(key)[0];
			// System.out.println("param: " + key + "=" + value);
			if (value == null || (value = value.trim()).length() == 0) {
				continue;
			}
			switch (key) {
			case "osday":
				if (checkDateFormat(value)) {
					stime = value + stime;
				} else {
					inputError = true;
				}
				break;
			case "oeday":
				if (checkDateFormat(value)) {
					etime = value + etime;
				} else {
					inputError = true;
				}
				break;
			case "ostime":
				if (checkTimeFormat(value)) {
					stime += value + ":00";
				} else {
					inputError = true;
				}
				break;
			case "oetime":
				if (checkTimeFormat(value)) {
					etime += value + ":00";
				} else {
					inputError = true;
				}
				break;
			case "ordno":
			case "caseno":
				if (value.matches("\\d+")) {
					outputMap.put(key, paramMap.get(key));
				} else {
					inputError = true;
					errMsg.add(key.equals("ordno") ? "訂單編號格式錯誤" : "合購案編號格式錯誤");
				}
				break;
			case "bmem":
			case "cmem":
				if (value.matches("[a-zA-Z_0-9]+")) {
					System.out.println("memmatchs");
					outputMap.put(key, paramMap.get(key));
				} else {
					inputError = true;
					errMsg.add(key.equals("bmem") ? "下單會員編號或帳號格式錯誤"
							: "主購會員編號或帳號格式錯誤");
				}
				break;
			case "pricefilter":
				if ("eq".equals(value) || "gt".equals(value)
						|| "lt".equals(value)) {
					String price = request.getParameter("price");
					if (price != null && price.trim().length() != 0) {
						value = value + "@" + price;
						outputMap.put("price", new String[] { value });
					}
				}
			case "status":
				if (value.matches("[0-59]")) {
					outputMap.put(key, paramMap.get(key));
				}
				break;
			case "price":
			default:
				break;
			}

		}// End of for loop

		if (stime.length() == 19) {
			outputMap.put("stime", new String[] { stime });
		}
		if (etime.length() == 19) {
			outputMap.put("etime", new String[] { etime });
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(AJAX_QUERY);
		request.setAttribute("fromSearch", new Boolean(true));

		if (inputError) {

			request.setAttribute("invalidInput", new Boolean(true));
			request.setAttribute("errMsg", errMsg);

		} else {

			ColumnValueBundle[] parsedParam = parser
					.doComplicateMaping(outputMap);
			OrderService orderSvc = new OrderService();
			List<OrderVO> list = null;
			try {
				boolean matchCondition = !"yes".equals(request
						.getParameter("notMatch"));
				list = orderSvc.findByCompositeQuery(matchCondition,
						parsedParam);
			} catch (Exception e) {
				System.out.println("Composite Query FAILED :-(");
				e.printStackTrace();
			}
			request.setAttribute("Ajax_orderTable", list);

		}

		dispatcher.forward(request, response);
	}

	private boolean invalidOrdno(String ordnoStr) {
		return (ordnoStr == null || !ordnoStr.matches("\\d{4,}"));
	}

	private boolean checkTimeFormat(String time) {
		try {
			Timestamp.valueOf("2001-01-01 " + time + ":00");
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private boolean checkDateFormat(String date) {
		try {
			Date.valueOf(date);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
