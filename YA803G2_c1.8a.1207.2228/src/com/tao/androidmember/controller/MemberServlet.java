package com.tao.androidmember.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.google.gson.Gson;
import com.tao.androidcases.controller.AndroidCasesVO;
import com.tao.cases.model.CasesService;
import com.tao.cases.model.CasesVO;
import com.tao.category.model.CategoryService;
import com.tao.category.model.CategoryVO;
import com.tao.jimmy.util.model.ColumnValue;
import com.tao.member.model.MemberService;
import com.tao.member.model.MemberVO;
import com.tao.news.model.NewsService;
import com.tao.news.model.NewsVO;
import com.tao.order.model.OrderService;
import com.tao.order.model.OrderVO;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class MemberServlet extends HttpServlet {
	public void doGet(HttpServletRequest rq, HttpServletResponse rp)
			throws ServletException, IOException {
		ServletContext context = getServletContext();
		String contentType = context.getInitParameter("contentType");
		String outStr = "";
		MemberService memberService = new MemberService();
		com.tao.androidmember.model.MemberService androidmemberService = new com.tao.androidmember.model.MemberService();
		OrderService orderService = new OrderService();
		CasesService casesService = new CasesService();
		NewsService newsService = new NewsService();
		CategoryService categoryService = new CategoryService();
		Gson gson = new Gson();
		List<AndroidCasesVO> androidCaseVO = new ArrayList<AndroidCasesVO>();
		String action = rq.getParameter("helpForMember");

		if (action.equals("getLoginData")) {

			String memberMemId = rq.getParameter("helpforid");
			String memberMemPw = rq.getParameter("helpforpw");
			String idid = new String(memberMemId);
			String pwpw = new String(memberMemPw);
			System.out.println(idid);
			System.out.println(pwpw);
			boolean isMember = false;
			MemberVO memvo = memberService.findByMemberID(idid);
			
				
			List<CategoryVO>  categorylist = categoryService.getAll();
			
			for(CategoryVO categoryVO: categorylist){
				
				if (memvo != null && memvo.getMempw().equals(pwpw)) {
					if (memvo.getStatus() == 1) {

						isMember = true;
					}
				}
				
				
				
				String result = Boolean.toString(isMember);
				AndroidCasesVO casetest = new AndroidCasesVO();
				casetest.setCategoryVO(categoryVO);
				casetest.setResult(result);
				androidCaseVO.add(casetest);
				
			}
			
			

		}
		if (action.equals("getMyMemberData")) {

			String memid = rq.getParameter("member");

			MemberVO memvo = memberService.findByMemberID(memid);

			List<OrderVO> orderBuyData = orderService
					.compositeQuery(new ColumnValue("bmemno", memvo.getMemno()
							.toString()));

			Integer buy = 0;
			for (OrderVO buydata : orderBuyData) {
				if (buydata.getBrate() != null) {
					buy += buydata.getBrate();
					// AndroidCasesVO casetest = new AndroidCasesVO();
					// casetest.setOrderVO(buydata);
					//
					// androidCaseVO.add(casetest);
				}
			}

			Integer sell = 0;
			List<OrderVO> orderSellData = orderService
					.compositeQuery(new ColumnValue("cmemno", memvo.getMemno()
							.toString()));
			for (OrderVO selldata : orderSellData) {
				if (selldata.getCrate() != null) {

					// orderBuyData.add(selldata);

					sell += selldata.getCrate();
					// AndroidCasesVO casetest = new AndroidCasesVO();
					// casetest.setOrderVO(selldata);

					// androidCaseVO.add(casetest);
				}
			}

			List<OrderVO> all = orderService.getAll();

			for (OrderVO rateList : all) {
				if (rateList.getBmemno().intValue() == memvo.getMemno()
						.intValue()
						|| rateList.getCmemno().intValue() == memvo.getMemno()
								.intValue()) {
					if (rateList.getCrate() != null
							&& rateList.getBrate() != null) {
						Integer finalRate = buy + sell;
						CasesVO forOrderCase = casesService
								.getByPrimaryKey(rateList.getCaseno());

						AndroidCasesVO casetest = new AndroidCasesVO();

						casetest.setFinalRate(finalRate);
						casetest.setCasesvo(forOrderCase);
						casetest.setOrderVO(rateList);
						casetest.setMembervo(memvo);

						androidCaseVO.add(casetest);
					}
				}

			}
			Collections.sort(androidCaseVO, new Comparator<AndroidCasesVO>() {

				public int compare(AndroidCasesVO o1, AndroidCasesVO o2) {
					return o2.getOrderVO().getOrdno()
							- o1.getOrderVO().getOrdno();
				}
			});

		}

		if (action.equals("getOnlyMember")) {

			String memid = rq.getParameter("member");

			MemberVO memvo = memberService.findByMemberID(memid);
			AndroidCasesVO casetest = new AndroidCasesVO();

			casetest.setMembervo(memvo);
			androidCaseVO.add(casetest);
		}
		if (action.equals("changeImage")) {

			String memid = rq.getParameter("memid");
			com.tao.androidmember.model.MemberVO user = gson.fromJson(memid,
					com.tao.androidmember.model.MemberVO.class);

			MemberVO memvo = memberService.findByMemberID(user.getMemid());

			String mime = new String("image/jpeg");
			System.out.println("¨ì©³?" + user.getPhoto());

			int updateImage = androidmemberService.updatePic(memvo.getMemno(),
					user.getPhoto(), mime);

			boolean isMember = true;

			String result = Boolean.toString(isMember);
			AndroidCasesVO casetest = new AndroidCasesVO();

			casetest.setResult(result);
			androidCaseVO.add(casetest);

		}
		if (action.equals("getNews")) {

			String memid = rq.getParameter("memid");

			MemberVO memvo = memberService.findByMemberID(memid);

			System.out.println("?????" + memvo.getType());
			List<NewsVO> listnewsVO = newsService.getAll();
			AndroidCasesVO casetest = new AndroidCasesVO();
			String addNewsTitle = "";

			for (NewsVO newsVO : listnewsVO) {

				addNewsTitle += newsVO.getTitle() + "    ";

			}
			casetest.setMembervo(memvo);
			casetest.setNewstitle(addNewsTitle);
			androidCaseVO.add(casetest);

			Collections.sort(androidCaseVO, new Comparator<AndroidCasesVO>() {

				public int compare(AndroidCasesVO o1, AndroidCasesVO o2) {
					return o2.getNewsVO().getNewsno()
							- o1.getNewsVO().getNewsno();
				}
			});

		}

		outStr = gson.toJson(androidCaseVO);
		rp.setContentType(contentType);
		PrintWriter out = rp.getWriter();
		out.println(outStr);
		out.close();

	}

	public void doPost(HttpServletRequest rq, HttpServletResponse rp)
			throws ServletException, IOException {
		doGet(rq, rp);
	}
}