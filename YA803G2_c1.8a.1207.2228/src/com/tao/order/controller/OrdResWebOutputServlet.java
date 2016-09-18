package com.tao.order.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.tao.cases.model.CasesVO;
import com.tao.jimmy.util.AjaxMsg;
import com.tao.order.model.OrderStatus;
import com.tao.order.model.OrderVO;

public class OrdResWebOutputServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// System.out.println("OrdResWebOutputServlet::doGet");
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("OrdResWebOutputServlet::doPost");
		// set Response Encoding
		response.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");

		if ("order".equals(action)) {
			OrderAjaxRes resStatus = (OrderAjaxRes) request
					.getAttribute("resStatus");
			switch (resStatus) {
			case SUCCESS:
				ajaxSendSuccessMsg(request, response);
				break;
			case ERROR:
				ajaxSendErrorMsg(request, response);
				break;
			case TO_LOGIN:
				ajaxSendRedirectToLogin(request, response);
				break;
			case TO_DEPOSIT:
				ajaxSendRedirectToDepositMoney(request, response);
				break;
			case CASE_NOT_AVALIBLE:
				ajaxSendCaseNotAvaliableMsg(request, response);
				break;
			case OUT_OF_STOCK:
				ajaxSendOutOfStockMsg(request, response);
				break;
			default:
				break;
			}
			return;
		}

		if ("ajax".equals(action)) {
			String what = request.getParameter("what");
			OrderAjaxRes res = (OrderAjaxRes) request.getAttribute("AjaxRes");
			if ("getOrdDetail".equals(what)) {
				responseGetOrdDetail(request, response, res);
			}
			if ("cancel".equals(what)) {
				responseAjax(response, res, "退出成功");
			}
			if ("rate".equals(what)) {
				responseAjax(response, res, "評價成功");
			}

			if ("confirmOrder".equals(what)) {
				responseAjax(response, res, "確認成功");
			}
			if ("report".equals(what)) {
				responseAjax(response, res, "已提報");
			}
			return;
		}

	}

	private void responseAjax(HttpServletResponse response, OrderAjaxRes res, String successMsg) throws IOException{
		AjaxMsg msg = new AjaxMsg();
		StringBuilder resHtml = new StringBuilder();
		switch (res) {
		case SUCCESS:
			msg.setStatus(AjaxMsg.SUCCESS);
			resHtml.append("<p>")
					.append("<i class=\"fa fa-check-circle-o \" style=\"font-size:2em; color:#FE980F;\"></i> ")
					.append(successMsg).append("</p>");
			msg.setResHtml(resHtml.toString());
			break;
		case ERROR:
			msg.setStatus(AjaxMsg.ERROR);
			resHtml.append("<p>")
					.append("<i class=\"fa fa-exclamation-circle\" style=\"font-size:2em; color:#FE980F;\" ></i> ")
					.append("發生錯誤").append("</p>");
			msg.setResHtml(resHtml.toString());
			break;
		default:
			return;
		}
		Gson gson = new Gson();
		String json = gson.toJson(msg);
		response.getWriter().println(json);
	}
	
	private void responseGetOrdDetail(HttpServletRequest request, HttpServletResponse response, OrderAjaxRes res) throws IOException {
		AjaxMsg msg = new AjaxMsg();
		switch (res) {
		case SUCCESS:
			CasesVO cvo = (CasesVO) request.getAttribute("cvo");
			OrderVO ordvo = (OrderVO) request.getAttribute("ordvo");
			Map<String, String> info = new HashMap<>();
			info.put("title", cvo.getTitle());
			info.put("qty", Integer.toString(ordvo.getQty()));
			info.put("shipcost", Integer.toString(ordvo.getShip() == 1 ? cvo
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
		response.getWriter().println(json);
	}

	private void ajaxSendOutOfStockMsg(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		AjaxMsg msg = new AjaxMsg();
		msg.setStatus(AjaxMsg.ERROR);
		StringBuilder resHtml = new StringBuilder();
		resHtml.append("<p>")
				.append("<i class=\"fa fa-times-circle-o \" style=\"font-size:2em; color:#FE980F;\"></i> ")
				.append("可購買數量不足").append("</p>");
		msg.setResHtml(resHtml.toString());
		Gson gson = new Gson();
		String json = gson.toJson(msg);
		System.out.println("OrdResWebOutputServlet::ajaxSendOutOfStockMsg:\n"
				+ json);
		response.getWriter().print(json);
	}

	private void ajaxSendRedirectToDepositMoney(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		AjaxMsg msg = new AjaxMsg();
		msg.setStatus(AjaxMsg.REDIRECT);
		msg.setResUrl(request.getContextPath()
				+ "/order/order.do?redir=deposit&caseno="
				+ request.getParameter("caseno"));
		StringBuilder resHtml = new StringBuilder();
		resHtml.append("<p>")
				.append("<i class=\"fa fa-exclamation-circle\" style=\"font-size:2em; color:#FE980F;\" ></i> ")
				.append("您的帳戶餘額不足").append("</p>");

		msg.setResHtml(resHtml.toString());
		Gson gson = new Gson();
		String json = gson.toJson(msg);
		System.out
				.println("OrdResWebOutputServlet::ajaxSendRedirectToDepositMoney:\n"
						+ json);

		response.getWriter().print(json);
	}

	private void ajaxSendRedirectToLogin(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		AjaxMsg msg = new AjaxMsg();
		msg.setStatus(AjaxMsg.REDIRECT);
		msg.setResUrl(request.getContextPath()
				+ "/order/order.do?redir=login&caseno="
				+ request.getParameter("caseno"));
		StringBuilder resHtml = new StringBuilder();
		resHtml.append("<p>")
				.append("<i class=\"fa fa-exclamation-circle \" style=\"font-size:2em; color:#FE980F;\"></i> ")
				.append("您的登入已逾時").append("</p>");
		msg.setResHtml(resHtml.toString());
		Gson gson = new Gson();
		String json = gson.toJson(msg);
		System.out.println("OrdResWebOutputServlet::ajaxSendRedirectToLogin:\n"
				+ json);

		response.getWriter().print(json);
	}

	private void ajaxSendSuccessMsg(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Integer genKey = (Integer) request.getAttribute("genKey");
		AjaxMsg msg = new AjaxMsg();
		msg.setStatus(AjaxMsg.SUCCESS);
		StringBuilder resHtml = new StringBuilder();
		resHtml.append("<p>")
				.append("<i class=\"fa fa-check-circle-o \" style=\"font-size:2em; color:#FE980F;\"></i> ")
				.append("加入成功！ 您的訂單編號為" + genKey + "，感謝您的參與！").append("</p>");
		msg.setResHtml(resHtml.toString());
		Gson gson = new Gson();
		String json = gson.toJson(msg);
		System.out.println("OrdResWebOutputServlet::ajaxSendSuccessMsg:\n"
				+ json);
		response.getWriter().print(json);
	}

	private void ajaxSendCaseNotAvaliableMsg(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		AjaxMsg msg = new AjaxMsg();
		msg.setStatus(AjaxMsg.ERROR);
		StringBuilder resHtml = new StringBuilder();
		resHtml.append("<p>")
				.append("<i class=\"fa fa-times-circle-o \" style=\"font-size:2em; color:#FE980F;\"></i> ")
				.append("合購案募集時間已截止").append("</p>");
		msg.setResHtml(resHtml.toString());
		Gson gson = new Gson();
		String json = gson.toJson(msg);
		System.out
				.println("OrdResWebOutputServlet::ajaxSendCaseNotAvaliableMsg:\n"
						+ json);
		response.getWriter().print(json);
	}

	private void ajaxSendErrorMsg(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		AjaxMsg msg = new AjaxMsg();
		msg.setStatus(AjaxMsg.ERROR);
		StringBuilder resHtml = new StringBuilder();
		resHtml.append("<p>")
				.append("<i class=\"fa fa-times-circle-o \" style=\"font-size:2em; color:#FE980F;\"></i> ")
				.append("發生錯誤").append("</p>");
		msg.setResHtml(resHtml.toString());
		Gson gson = new Gson();
		String json = gson.toJson(msg);
		System.out
				.println("OrdResWebOutputServlet::ajaxSendErrorMsg:\n" + json);
		response.getWriter().print(json);
	}

}
