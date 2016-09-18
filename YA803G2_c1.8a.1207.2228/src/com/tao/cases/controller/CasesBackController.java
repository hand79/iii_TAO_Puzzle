package com.tao.cases.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.tao.cases.model.CasesParser;
import com.tao.cases.model.CasesService;
import com.tao.cases.model.CasesVO;
import com.tao.jimmy.location.LocationService;
import com.tao.jimmy.location.LocationVO;
import com.tao.jimmy.util.AjaxMsg;
import com.tao.jimmy.util.model.ColumnValueBundle;
import com.tao.task.CaseOverTask;

public class CasesBackController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String AJAX_TOWN_SELECT_LIST = "/cases/Ajax_findTown.jsp";
	private static final String AJAX_SEARCH_RESULT_TABLE = "/cases/Ajax_casesTable.jsp";

	// private static final String DEFAULT_REDIRECT = "/index.jsp";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("CasesController: doGet()");

		String view = request.getParameter("view");
		if ("default".equals(view)) {
			request.getRequestDispatcher("/cases/ManageCases.jsp").forward(request,
					response);
			return;
		}

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		System.out.println("CasesController: doPost()");


		if ("compositeQuery".equals(action)) {
			doCompositeQuery(request, response);
			return;
		}
		if ("ajax".equals(action)) {
			doAjax(request, response);
			return;
		}
		if("cancel".equals(action)){
			doCancelCase(request, response);
			return;
		}
		
		if("delete".equals(action)){
			doDeleteCase(request, response);
			return;
		}
		if("triggerOverCase".equals(action)){
			doOverCase(request, response);
		}
	

	}
	private void doOverCase(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		CaseOverTask task= (CaseOverTask) getServletContext().getAttribute("CaseOverTask");
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

	private void doDeleteCase(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AjaxMsg msg = new AjaxMsg();
		String casenoStr = request.getParameter("caseno");
		if(invalidCaseno(casenoStr)){
			ajaxSendErrorMsg(request, response, msg, "發生錯誤");
			return;
		}
		Integer caseno = new Integer(casenoStr);
		CasesService cSvc = new CasesService();
		int updateCount = cSvc.updateCaseStatus(caseno, CasesVO.STATUS_DELETED);
		//TODO 
		if(updateCount != 1){
			ajaxSendErrorMsg(request, response, msg, "執行時發生錯誤");
		}else{
			ajaxSendSuccessMsg(request, response, msg, "刪除成功");
		}
	}

	private boolean invalidCaseno(String casenoStr) {
		return (casenoStr == null || !casenoStr.matches("\\d{4,}"));
	}

	private void doCancelCase(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AjaxMsg msg = new AjaxMsg();
		String casenoStr = request.getParameter("caseno");
		if(invalidCaseno(casenoStr)){
			ajaxSendErrorMsg(request, response, msg, "發生錯誤");
			return;
		}
		Integer caseno = new Integer(casenoStr);
		CasesService cSvc = new CasesService();
		boolean success = cSvc.cancelCase(caseno);
		//TODO 
		if(!success){
			ajaxSendErrorMsg(request, response, msg, "執行時發生錯誤");
		}else{
			ajaxSendSuccessMsg(request, response, msg, "取消成功");
		}
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


	private void doAjax(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		System.out.println("doAjax()");

		String what = req.getParameter("what");

		if ("town".equals(what)) {
			String countyVal = req.getParameter("county");
			if (countyVal != null && countyVal.trim().length() != 0) {
				String[] vals = countyVal.split("-");
				try {
					Integer from = new Integer(vals[0]);
					Integer to = new Integer(vals[1]);

					LocationService locSvc = new LocationService();
					Set<LocationVO> towns = locSvc.findByCounty(from, to);
					req.setAttribute("towns", towns);

					req.getRequestDispatcher(AJAX_TOWN_SELECT_LIST).forward(
							req, res);
				} catch (NumberFormatException e) {
					System.out.println(e);
				}
			}
			return;
		}

		if ("getAll".equals(what)) {
			CasesService casesSvc = new CasesService();
			Set<CasesVO> set = casesSvc.getAll();
			req.setAttribute("Ajax_casesTable", set);
			req.getRequestDispatcher(AJAX_SEARCH_RESULT_TABLE)
					.forward(req, res);
		}

	}

	private void doCompositeQuery(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("CasesController: doCompositeQuery()");
		Map<String, String[]> paramMap = req.getParameterMap();
		Set<String> keyset = paramMap.keySet();

		Map<String, String[]> outputMap = new HashMap<>();
		boolean inputError = false;
		
		for (String key : keyset) {
			System.out.println("param: " + key + "=" + paramMap.get(key)[0]);
			String value = paramMap.get(key)[0];
			if (value == null || (value = value.trim()).length() == 0) {
				continue;
			}
			switch (key) {
			case "caseno":
			case "memno":
			case "shopno":
				if (value.matches("\\d+")) {
					outputMap.put(key, paramMap.get(key));
				} else {
					inputError = true;
				}
				break;
			case "cspno":
				value = value.toUpperCase();
				if (value.matches("C\\d+")) {
					value = value.substring(1);
					outputMap.put("cpno", new String[] { value });
				} else if (value.matches("S\\d+")) {
					value = value.substring(1);
					outputMap.put("spno", new String[] { value });
				} else {
					inputError = true;
				}
				break;
			case "stimefrom":
			case "stimeto":
			case "etimefrom":
			case "etimeto":
				if (checkDateFormat(value)) {
					outputMap.put(key, new String[] { value + " 00:00:00" });
				} else {
					inputError = true;
				}
				break;
			case "locno":
				if (value != null && value.matches("\\d+")) {
					outputMap.put(key, paramMap.get(key));
				}
				break;
			case "county":
				String locno = req.getParameter("locno");
				if (locno != null && locno.matches("\\d+")) {
					System.out
							.println("Controller param check: locno has valid value, escape county");
					break;
				}
				if (value != null && value.matches("\\d+-\\d+")) {
					outputMap.put(key, paramMap.get(key));
				}
				break;

			default:
				outputMap.put(key, paramMap.get(key));
				break;
			}
		}// End of iterating paramMap

		RequestDispatcher dispatcher = req
				.getRequestDispatcher(AJAX_SEARCH_RESULT_TABLE);
		req.setAttribute("fromSearch", new Boolean(true));
		if (inputError) {
			req.setAttribute("invalidInput", new Boolean(true));
		} else {
			CasesParser parser = new CasesParser();
			ColumnValueBundle[] parsedParam = parser
					.doComplicateMaping(outputMap);
			CasesService casesSvc = new CasesService();
			Set<CasesVO> set = null;
			boolean matchCondition = !("yes".equals(req
					.getParameter("notMatch")));
			set = casesSvc.compositeQuery(matchCondition, parsedParam);
			req.setAttribute("Ajax_casesTable", set);
		}
		dispatcher.forward(req, resp);
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
