package com.tao.cases.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.tao.cases.model.CaseQAService;
import com.tao.cases.model.CaseQAVO;
import com.tao.cases.model.CasesService;
import com.tao.cases.model.CasesVO;
import com.tao.jimmy.member.TinyMemberService;
import com.tao.jimmy.member.TinyMemberVO;
import com.tao.jimmy.util.AjaxMsg;
import com.tao.jimmy.util.FrontSessionAttrUtil;
import com.tao.jimmy.util.Simulation;
import com.tao.member.model.MemberVO;

public class CaseQAController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// enviroment
	private static final String MEMBER_VO_ATTRIBUTE_KEY = FrontSessionAttrUtil.MEMBER_VO_ATTRIBUTE_KEY;
	private static final String LOCATION_ATTRIBUTE_KEY = FrontSessionAttrUtil.LOCATION_ATTRIBUTE_KEY;
	// for redirect
	private String login_page = FrontSessionAttrUtil.LOGIN_PAGE;
	private String cases_controller = "/cases/cases.do";
	// for forward
	private static final String VIEW_CASEQA = "/caseqa/ViewCaseQuestions.jsp";
	// ajax res status
	private static final int NOT_LOGGINED = 0;
	private static final int PARAM_ERROR = 1;
	private static final int QA_NOT_FOUND = 2;
	private static final int QA_NOT_BELONGED = 3;
	private static final int UPDATE_FAILED = 4;
	private static final int SUCCESS = 5;
	private static final boolean isSimulation = Simulation.isSimulation();

	@Override
	public void init() throws ServletException {
		super.init();
		login_page = getServletContext().getContextPath() + login_page;
		cases_controller = getServletContext().getContextPath()
				+ cases_controller;
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String redir = request.getParameter("redir");
		if ("login".equals(redir)) {
			request.getSession().setAttribute(
					"location",
					request.getContextPath() + "/caseqa/caseQA.do?action="
							+ request.getParameter("page"));
			response.sendRedirect(login_page);
			return;
		}

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");
		System.out.println("CaseQAController: doPost, action=(s)" + action
				+ "(e)");
		MemberVO memvo = (MemberVO) request.getSession().getAttribute(
				MEMBER_VO_ATTRIBUTE_KEY);
		if ("viewCaseQAs".equals(action)) {
			if (memvo == null) {
				request.getSession().setAttribute(
						"location",
						request.getRequestURI() + "?"
								+ request.getQueryString());
				response.sendRedirect(login_page);
			} else {
				doViewCaseQAs(request, response, memvo);
			}
			return;
		}

		if ("askQuestion".equals(action)) {
			if (memvo == null) {
				sendRedirectLoginAjaxMsg(request, response,
						request.getParameter("caseno"));
			} else {
				doAskQuestion(request, response, memvo);
			}
			return;
		}

		if ("toLogin".equals(action)) {
			redirectToLoginFromCaseDetailPage(request, response,
					request.getParameter("caseno"));
			return;
		}

		if ("detail".equals(action)) {
			AjaxMsg msg = new AjaxMsg();
			int res;
			if (memvo == null) {
				res = NOT_LOGGINED;
			} else {
				res = doGetDetail(request, response, memvo);
			}
			switch (res) {
			case NOT_LOGGINED:
				ajaxSendRedirectToLogin(request, response, msg);
				break;
			case PARAM_ERROR:
			case QA_NOT_FOUND:
			case QA_NOT_BELONGED:
				ajaxSendErrorMsg(request, response, msg, "該問答不存在");
				break;
			case SUCCESS:
				msg.setStatus(AjaxMsg.SUCCESS);
				CaseQAVO qavo = (CaseQAVO) request.getAttribute("qavo");
				Map<String, String> info = new HashMap<>();
				info.put("question", qavo.getQuestion());
				info.put("answer", qavo.getAnswer());
				info.put("atime", qavo.getFormatedAtime());
				msg.setInfo(info);
				sendAjaxResponse(msg, response);
				break;
			}
			return;
		}
		/******* SIMULATION *******/
		if (isSimulation) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/******* SIMULATION *******/
		if ("answerQuestion".equals(action)) {
			AjaxMsg msg = new AjaxMsg();
			int res;
			if (memvo == null) {
				res = NOT_LOGGINED;
			} else {
				res = doAnswerQuestion(request, response, memvo);
			}
			switch (res) {
			case NOT_LOGGINED:
				ajaxSendRedirectToLogin(request, response, msg);
				break;
			case PARAM_ERROR:
				ajaxSendErrorMsg(request, response, msg, "發生錯誤");
				break;
			case QA_NOT_FOUND:
			case QA_NOT_BELONGED:
				ajaxSendErrorMsg(request, response, msg, "該問答不存在");
				break;
			case UPDATE_FAILED:
				ajaxSendErrorMsg(request, response, msg, "執行時發生錯誤");
				break;
			case SUCCESS:
				ajaxSendSuccessMsg(request, response, msg, "操作成功");
				break;
			}
			return;
		}
		
		if("delete".equals(action)){
			AjaxMsg msg = new AjaxMsg();
			int res;
			if (memvo == null) {
				res = NOT_LOGGINED;
			} else {
				res = doDeleteQuestion(request, response, memvo);
			}
			switch (res) {
			case NOT_LOGGINED:
				ajaxSendRedirectToLogin(request, response, msg);
				break;
			case PARAM_ERROR:
				ajaxSendErrorMsg(request, response, msg, "發生錯誤");
				break;
			case QA_NOT_FOUND:
			case QA_NOT_BELONGED:
				ajaxSendErrorMsg(request, response, msg, "該問答不存在");
				break;
			case UPDATE_FAILED:
				ajaxSendErrorMsg(request, response, msg, "執行時發生錯誤");
				break;
			case SUCCESS:
				ajaxSendSuccessMsg(request, response, msg, "刪除成功");
				break;
			}
			return;
		}

	}

	private int doDeleteQuestion(HttpServletRequest request,
			HttpServletResponse response, MemberVO memvo) {
		String qanoStr = request.getParameter("qano");
		System.out.println("qanoStr = " + qanoStr);
		if (invalidQano(qanoStr)) {
			return PARAM_ERROR;
		}
		Integer qano = new Integer(qanoStr);
		System.out.println("qano=" + qano);
		CaseQAService qaSvc = new CaseQAService();
		CaseQAVO qavo = qaSvc.findByPrimaryKey(qano);
		if (qavo == null) {
			return QA_NOT_FOUND;
		}
		CasesService cSvc = new CasesService();
		CasesVO cvo = cSvc.getByPrimaryKey(qavo.getCaseno());
		if (cvo.getMemno().intValue() != memvo.getMemno().intValue()) {
			return QA_NOT_BELONGED;
		}
		int updateCount = qaSvc.deleteQA(qano);
		if(updateCount == 0){
			return UPDATE_FAILED;
		}
		return SUCCESS;
	}

	private int doGetDetail(HttpServletRequest request,
			HttpServletResponse response, MemberVO memvo) {
		String qanoStr = request.getParameter("qano");
		if (invalidQano(qanoStr)) {
			return PARAM_ERROR;
		}
		Integer qano = new Integer(qanoStr);
		System.out.println("qano=" + qano);
		CaseQAService qaSvc = new CaseQAService();
		CaseQAVO qavo = qaSvc.findByPrimaryKey(qano);
		if (qavo == null) {
			return QA_NOT_FOUND;
		}
		CasesService cSvc = new CasesService();
		CasesVO cvo = cSvc.getByPrimaryKey(qavo.getCaseno());
		if (cvo.getMemno().intValue() != memvo.getMemno().intValue()) {
			return QA_NOT_BELONGED;
		}
		request.setAttribute("qavo", qavo);
		return SUCCESS;
	}

	private int doAnswerQuestion(HttpServletRequest request,
			HttpServletResponse response, MemberVO memvo) throws IOException {
		request.setCharacterEncoding("UTF-8");
		String qanoStr = request.getParameter("qano");
		String answer = request.getParameter("answer");
		answer = new String(answer.getBytes("ISO-8859-1"), "UTF-8");
		if (invalidQano(qanoStr) || invalidAnswer(answer)) {
			return PARAM_ERROR;
		}
		Integer qano = new Integer(qanoStr);
		CaseQAService qaSvc = new CaseQAService();
		CaseQAVO qavo = qaSvc.findByPrimaryKey(qano);
		if (qavo == null) {
			return QA_NOT_FOUND;
		}
		CasesService cSvc = new CasesService();
		CasesVO cvo = cSvc.getByPrimaryKey(qavo.getCaseno());
		if (cvo.getMemno().intValue() != memvo.getMemno().intValue()) {
			return QA_NOT_BELONGED;
		}
		int updateCount = qaSvc.updateAnswer(new Integer(qanoStr), answer);
		if (updateCount == 0) {
			return UPDATE_FAILED;
		}
		return SUCCESS;
	}

	private void doViewCaseQAs(HttpServletRequest request,
			HttpServletResponse response, MemberVO memvo)
			throws ServletException, IOException {
		CasesService cSvc = new CasesService();
		Set<CasesVO> cSet = cSvc.getByCreator(memvo.getMemno(), false, false);
		List<CasesVO> clist = new ArrayList<>(cSet);
		Collections.reverse(clist);
		CaseQAService qaSvc = new CaseQAService();
		Map<CasesVO, List<CaseQAVO>> qamap = new LinkedHashMap<>();
		for (CasesVO cvo : clist) {
			List<CaseQAVO> list = qaSvc.findByCaseNo(cvo.getCaseno());
			Collections.reverse(list);
			qamap.put(cvo, list);
		}
		request.setAttribute("qamap", qamap);

		Set<TinyMemberVO> tmemset = new TinyMemberService().getAll();
		request.setAttribute("tmemset", tmemset);
		request.getRequestDispatcher(VIEW_CASEQA).forward(request, response);
	}

	private void doAskQuestion(HttpServletRequest request,
			HttpServletResponse response, MemberVO memvo) throws IOException {
		request.setCharacterEncoding("UTF-8");
		/**************** Validation *******************/
		boolean inputErr = false;

		String caseno = request.getParameter("caseno");
		if (invalidCaseno(caseno)) {
			inputErr = true;
		}

		String question = request.getParameter("question");

		if (question == null || question.trim().length() == 0) {
			inputErr = true;
		}

		if (inputErr) {
			sendErrorAjaxMsg(request, response);
			return;
		}

		/**************** Access Database *******************/
		CaseQAService qaSvc = new CaseQAService();
		Integer genKey = qaSvc.askQuestion(memvo.getMemno(),
				new Integer(caseno), question,
				new Timestamp(System.currentTimeMillis()));
		System.out.println("CaseQAController:askQuestion: genKey: " + genKey);

		/**************** Response *******************/
		sendSuccessAjaxMsg(request, response);

	}

	private void sendSuccessAjaxMsg(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		System.out.println("sendSuccessAjaxMsg");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		StringBuilder sb = new StringBuilder();
		sb.append("{")
				.append("\"status\":\"success\"")
				.append(",")
				.append("\"resHtml\":\"<p style='font-size:1.5em'>"
						+ "<i class='fa fa-check' style='color:#1d9d74'></i> 發問成功！"
						+ "</p>\"").append("}");
		out.print(sb.toString());
		out.flush();
	}

	private void sendErrorAjaxMsg(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		System.out.println("sendErrorAjaxMsg");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		StringBuilder sb = new StringBuilder();
		sb.append("{")
				.append("\"status\":\"error\"")
				.append(",")
				.append("\"resHtml\":\"<p style='font-size:1.5em'>"
						+ "<i class='fa fa-times' style='color:#d9534f'></i> 發生錯誤，發問失敗"
						+ "</p>\"").append("}");
		out.print(sb.toString());
		out.flush();
	}

	private void sendRedirectLoginAjaxMsg(HttpServletRequest request,
			HttpServletResponse response, String caseno) throws IOException {
		System.out.println("sendRedirectLoginAjaxMsg");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		StringBuilder sb = new StringBuilder();
		sb.append("{")
				.append("\"status\":\"redirect\"")
				.append(",")
				.append("\"resLink\":\"" + request.getContextPath()
						+ request.getServletPath() + "?action=toLogin&caseno="
						+ caseno + "\"")
				.append(",")
				.append("\"resHtml\":\"<p style='font-size:1.5em'>"
						+ "<i class='fa fa-times' style='color:#f0ad4e'></i> 請登入後再發問"
						+ "</p>\"").append("}");
		System.out.println(sb);
		out.print(sb.toString());
		out.flush();

	}

	private void redirectToLoginFromCaseDetailPage(HttpServletRequest request,
			HttpServletResponse response, String caseno) throws IOException {
		request.getSession().setAttribute(LOCATION_ATTRIBUTE_KEY,
				cases_controller + "?action=caseDetail&caseno=" + caseno);
		response.sendRedirect(login_page);
	}

	private void ajaxSendRedirectToLogin(HttpServletRequest request,
			HttpServletResponse response, AjaxMsg msg) throws ServletException,
			IOException {

		msg.setStatus(AjaxMsg.REDIRECT);
		msg.setResUrl(request.getContextPath()
				+ "/caseqa/caseQA.do?redir=login&page=viewCaseQAs");
		StringBuilder resHtml = new StringBuilder();
		resHtml.append("<p>")
				.append("<i class=\"fa fa-exclamation-circle \" style=\"font-size:2em; color:#FE980F;\"></i> ")
				.append("您的登入已逾時").append("</p>");
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

	private boolean invalidAnswer(String answer) {

		return (answer == null || answer.trim().length() == 0);
	}

	private boolean invalidQano(String qanoStr) {
		return (qanoStr == null || !qanoStr.matches("\\d+"));
	}

	private boolean invalidCaseno(String caseno) {
		return (caseno == null || !caseno.matches("\\d{4,}"));
	}
}
