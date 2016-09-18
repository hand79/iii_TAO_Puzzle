package com.tao.androidnews.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.google.gson.Gson;
import com.tao.andriodhelp.ImageUtil;
import com.tao.androidcases.controller.AndroidCasesVO;
import com.tao.cases.model.CaseProductService;
import com.tao.cases.model.CaseProductVO;
import com.tao.cases.model.CasesService;
import com.tao.cases.model.CasesVO;
import com.tao.jimmy.util.model.ColumnValue;
import com.tao.member.model.MemberService;
import com.tao.member.model.MemberVO;
import com.tao.news.model.NewsService;
import com.tao.news.model.NewsVO;
import com.tao.order.model.OrderService;
import com.tao.shopproduct.model.ShopproductService;
import com.tao.shopproduct.model.ShopproductVO;

import java.io.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("serial")
public class NewsServlet extends HttpServlet {
	public void doGet(HttpServletRequest rq, HttpServletResponse rp)
			throws ServletException, IOException {
		ServletContext context = getServletContext();
		String contentType = context.getInitParameter("contentType");
		String outStr = "";

		NewsService newsService = new NewsService();

		
		Gson gson = new Gson();
		String action = rq.getParameter("helpNews");
		List<AndroidCasesVO> androidCaseVO = new ArrayList<AndroidCasesVO>();

	
		if(action.equals("getNews")){
		List<NewsVO> newlist = newsService.getAll();

			for (NewsVO newsData : newlist) {
					
				System.out.println("TMD:"+newsData.getPubtime());
			
				
					AndroidCasesVO casetest = new AndroidCasesVO();

					casetest.setNewsVO(newsData);

					androidCaseVO.add(casetest);
				
			}
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

