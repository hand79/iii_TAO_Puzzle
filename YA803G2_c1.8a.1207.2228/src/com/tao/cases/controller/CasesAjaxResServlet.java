package com.tao.cases.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.tao.cases.model.CaseProductVO;
import com.tao.cases.model.CasesStatus;
import com.tao.cases.model.CasesVO;
import com.tao.jimmy.location.LocationVO;
import com.tao.jimmy.util.AjaxMsg;
import com.tao.shopproduct.model.ShopproductVO;

public class CasesAjaxResServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		System.out.println("CasesAjaxResServlet::doPost");

		/************* action = ajax ****************/
		if ("ajax".equals(action)) {
			String what = request.getParameter("what");
			CaseAjaxRes res = (CaseAjaxRes) request.getAttribute("AjaxRes");
			AjaxMsg msg = new AjaxMsg();
			if ("getCaseDetail".equals(what)) {
				switch (res) {
				case SUCCESS:
					msg.setStatus(AjaxMsg.SUCCESS);
					responseCaseDetail(request, response, msg);
					break;
				default:
					return;
				}
			}
			if ("addOrEdit".equals(what)) {
				switch (res) {
				case ERROR:
					ajaxSendErrorMsg(request, response, msg, "�ާ@���ѡA��J��Ʀ��~");
					break;
				case SUCCESS:
					// Integer genKey = (Integer)request.getAttribute("genKey");
					msg.setResUrl(request.getContextPath()
							+ "/cases/cases.do?action=viewOwnCases");
					ajaxSendSuccessMsg(request, response, msg, "�s�W / �s�覨�\");
					break;
				case UPDATE_FAILED:
					ajaxSendErrorMsg(request, response, msg, "�o�Ϳ��~");
					break;
				case TO_LOGIN:
					System.out.println('a');
					ajaxSendRedirectToLogin(request, response, msg);
					break;
				default:
					break;
				}
				return;
			}
			if ("open".equals(what)) {
				switch (res) {
				case SUCCESS:
					ajaxSendSuccessMsg(request, response, msg, "�W�[����");
					break;
				case ERROR:
					ajaxSendErrorMsg(request, response, msg, "�o�Ϳ��~");
					break;
				case TO_LOGIN:
					ajaxSendRedirectToLogin(request, response, msg);
					break;
				case CASE_STATUS_ERROR:
					ajaxSendErrorMsg(request, response, msg, "�X�ʮת��A�ëD���W�[");
					break;
				default:
				}
				return;
			}
			if ("over".equals(what)) {
				switch (res) {
				case SUCCESS:
					ajaxSendSuccessMsg(request, response, msg, "���\���׵���");
					break;
				case ERROR:
					ajaxSendErrorMsg(request, response, msg, "�o�Ϳ��~");
					break;
				case TO_LOGIN:
					ajaxSendRedirectToLogin(request, response, msg);
					break;
				case CASE_STATUS_ERROR:
					ajaxSendErrorMsg(request, response, msg, "�X�ʮת��A�ëD���W�[��");
					break;
				case CASE_ORDER_QTY_INSUFFICIENT:
					ajaxSendErrorMsg(request, response, msg, "�X�ʮ׭q�ʼƶq���F���e");
					break;
				default:
				}
				return;
			}
			if ("delete".equals(what)) {
				switch (res) {
				case SUCCESS:
					ajaxSendSuccessMsg(request, response, msg, "�R�����\");
					break;
				case ERROR:
					ajaxSendErrorMsg(request, response, msg, "�o�Ϳ��~");
					break;
				case TO_LOGIN:
					ajaxSendRedirectToLogin(request, response, msg);
					break;
				case CASE_STATUS_ERROR:
					ajaxSendErrorMsg(request, response, msg, "���~�A�w�W�[�L���X�ʮ׵L�k�R��");
					break;
				default:
				}
				return;
			}
			if ("cancel".equals(what)) {
				switch (res) {
				case SUCCESS:
					ajaxSendSuccessMsg(request, response, msg, "�����X�ʮצ��\");
					break;
				case ERROR:
					ajaxSendErrorMsg(request, response, msg, "�o�Ϳ��~");
					break;
				case TO_LOGIN:
					ajaxSendRedirectToLogin(request, response, msg);
					break;
				case CASE_STATUS_ERROR:
					ajaxSendErrorMsg(request, response, msg, "�ӦX�ʮפw�L�k�Q����");
					break;
				default:
				}
				return;
			}
			if ("addToWishlist".equals(what)) {
				System.out.println("addToWishlist Out");
				switch (res) {
				case SUCCESS:
					ajaxSendSuccessMsg(request, response, msg, "�[�J���\");
					break;
				case ERROR:
				case CASE_STATUS_ERROR:
					ajaxSendErrorMsg(request, response, msg, "�o�Ϳ��~");
					break;
				case TO_LOGIN:
					ajaxSendRedirectToLogin(request, response, msg, "caseDetail&caseno=" + request.getParameter("caseno"));
					break;
				case UPDATE_FAILED:
					ajaxSendErrorMsg(request, response, msg, "����ɵo�Ϳ��~");
					break;
				default:
				}
				return;
			}
			if ("report".equals(what)) {
				System.out.println("report Out");
				switch (res) {
				case SUCCESS:
					ajaxSendSuccessMsg(request, response, msg, "�w���|");
					break;
				case ERROR:
				case CASE_STATUS_ERROR:
					ajaxSendErrorMsg(request, response, msg, "�o�Ϳ��~");
					break;
				case TO_LOGIN:
					ajaxSendRedirectToLogin(request, response, msg, "caseDetail&caseno=" + request.getParameter("caseno"));
					break;
				case UPDATE_FAILED:
					ajaxSendErrorMsg(request, response, msg, "�п�J���|��]");
					break;
				default:
				}
				return;
			}
		}
		/************* action = ajax ****************/
	}

	private void responseCaseDetail(HttpServletRequest request,
			HttpServletResponse response, AjaxMsg msg) throws IOException {
		CasesVO cvo = (CasesVO) request.getAttribute("cvo");
		CaseProductVO cpvo = null;
		ShopproductVO spvo = null;
		boolean useCp = false;
		if (cvo.getCpno() != 0) {
			cpvo = (CaseProductVO) request.getAttribute("cpvo");
			useCp = true;
		} else if (cvo.getSpno() != 0) {
			spvo = (ShopproductVO) request.getAttribute("spvo");
		} else {
			System.out.println("CasesVO does not contain valid cpno or spno.");
			return;
		}
		Map<String, String> info = new HashMap<>();
		String context = request.getContextPath();
		Integer currentqty = (Integer) request.getAttribute("currentqty");
		LocationVO locvo = (LocationVO) request.getAttribute("locvo");
		info.put("status", CasesStatus.getDisplayStatusName(cvo.getStatus()));
		info.put("title", cvo.getTitle());
		info.put("cpsp",
				useCp ? cpvo.getCpno() + " " + cpvo.getName() : spvo.getSpno()
						+ " " + spvo.getName());
		info.put("cpspUrl", useCp ? context
				+ "/caseproduct/caseProduct.do?action=viewOwnCPs"
				: "#");
		info.put("stime",
				cvo.getStime() == null ? "-" : "�_�l " + cvo.getFormatedStime());
		info.put("etime",
				cvo.getEtime() == null ? "-" : "���� " + cvo.getFormatedEtime());
		info.put("currentqty", currentqty.toString());
		info.put("minqty", cvo.getMinqty().toString());
		info.put("maxqty", cvo.getMaxqty().toString());
		info.put(
				"unitprice",
				useCp ? cpvo.getUnitprice().toString() : Integer
						.toString(((int) spvo.getUnitprice().doubleValue())));
		info.put("discount", cvo.getDiscount().toString());
		info.put("loc", locvo.getCounty() + " > " + locvo.getTown());
		info.put("ship1", cvo.getShip1() + " " + cvo.getShipcost1() + "��");
		info.put("ship2", (cvo.getShip2() == null) ? "-" : cvo.getShip2() + " "
				+ cvo.getShipcost2() + "��");
		info.put("casedesc", cvo.getCasedesc());
		msg.setInfo(info);
		System.out.println("responseCaseDetail");
		sendAjaxResponse(msg, response);
	}

	private void ajaxSendRedirectToLogin(HttpServletRequest request,
			HttpServletResponse response, AjaxMsg msg) throws ServletException,
			IOException {
		ajaxSendRedirectToLogin(request, response, msg, "viewOwnCases");
	}
	private void ajaxSendRedirectToLogin(HttpServletRequest request,
			HttpServletResponse response, AjaxMsg msg, String redirString) throws ServletException,
			IOException {
		
		msg.setStatus(AjaxMsg.REDIRECT);
		msg.setResUrl(request.getContextPath()
				+ "/cases/cases.do?redir=login&page=" + redirString);
		StringBuilder resHtml = new StringBuilder();
		resHtml.append("<p>")
				.append("<i class=\"fa fa-exclamation-circle \" style=\"font-size:2em; color:#FE980F;\"></i> ")
				.append("�z���n�J�w�O��").append("</p>");
		msg.setResHtml(resHtml.toString());
		System.out.println("ajaxSendRedirectToLogin");
		sendAjaxResponse(msg, response);
	}

	private void ajaxSendErrorMsg(HttpServletRequest request,
			HttpServletResponse response, AjaxMsg msg, String text)
			throws IOException {
		msg.setStatus(AjaxMsg.ERROR);
		StringBuilder resHtml = new StringBuilder();
		resHtml.append("<p>")
				.append("<i class=\"fa fa-times-circle-o \" style=\"font-size:2em; color:#FE980F;\"></i> ")
				.append(text).append("</p>");
		msg.setResHtml(resHtml.toString());
		System.out.println("ajaxSendErrorMsg");
		sendAjaxResponse(msg, response);
	}

	private void ajaxSendSuccessMsg(HttpServletRequest request,
			HttpServletResponse response, AjaxMsg msg, String text)
			throws IOException {
		msg.setStatus(AjaxMsg.SUCCESS);
		StringBuilder resHtml = new StringBuilder();
		resHtml.append("<p>")
				.append("<i class=\"fa fa-check-circle-o \" style=\"font-size:2em; color:#FE980F;\"></i> ")
				.append(text).append("</p>");
		msg.setResHtml(resHtml.toString());
		System.out.println("ajaxSendSuccessMsg");
		sendAjaxResponse(msg, response);
	}

	private void sendAjaxResponse(AjaxMsg msg, HttpServletResponse response)
			throws IOException {
		response.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		String json = gson.toJson(msg);
		System.out.println(json);
		System.out.println();
		response.getWriter().println(json);
	}

}
