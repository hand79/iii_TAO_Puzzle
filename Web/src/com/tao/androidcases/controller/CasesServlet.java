package com.tao.androidcases.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.apache.catalina.connector.Request;

import com.google.gson.Gson;
import com.tao.andriodhelp.ImageUtil;
import com.tao.androidshop.model.ShopService;
import com.tao.androidshopproduct.model.ShopproductService;
import com.tao.androidshopproduct.model.ShopproductVO;
import com.tao.cases.model.CaseProductService;
import com.tao.cases.model.CaseProductVO;
import com.tao.cases.model.CaseQAService;
import com.tao.cases.model.CasesService;
import com.tao.cases.model.CasesVO;
import com.tao.jimmy.util.model.ColumnValue;
import com.tao.member.model.MemberService;
import com.tao.member.model.MemberVO;
import com.tao.order.model.OrderService;

import java.io.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@SuppressWarnings("serial")
public class CasesServlet extends HttpServlet {
	public void doGet(HttpServletRequest rq, HttpServletResponse rp)
			throws ServletException, IOException {
		ServletContext context = getServletContext();
		String contentType = context.getInitParameter("contentType");
		String outStr = "";

		CaseQAService caseQAService = new CaseQAService();
		MemberService memberService = new MemberService();
		CasesService casesService = new CasesService();
		CaseProductService caseProductService = new CaseProductService();
		OrderService orderService = new OrderService();

		Gson gson = new Gson();
		String action = rq.getParameter("helpList");
		List<AndroidCasesVO> androidCaseVO = new ArrayList<AndroidCasesVO>();

		List<CaseProductVO> caseProductVO = caseProductService.getAllHasPic();

		if (action.equals("getCaseList")) {
			Integer count = 0;
			// for (CaseProductVO casehelp : caseProductVO) {

			Set<CasesVO> casegohelp = casesService
					.compositeQuery(new ColumnValue("status", "1"));

			for (CasesVO caseend : casegohelp) {
				Integer price = 0;

				byte[] pic = null;

				if (caseend.getCpno() == 0) {
					ShopproductService shopproductService = new ShopproductService();
					ShopproductVO shopproductVO = shopproductService
							.getOneShopproduct(caseend.getSpno());

					price = shopproductVO.getUnitprice().intValue();

					pic = shopproductVO.getPic1();

					count += 1;
				} else {

					CaseProductVO caseProduct = caseProductService
							.getOneByPrimaryKeyHasPic(caseend.getCpno());
					price = caseProduct.getUnitprice();
					pic = caseProduct.getPic1();
					count += 1;
				}

				AndroidCasesVO casetest = new AndroidCasesVO();

				casetest.setCasesvo(caseend);
				casetest.setUnitprice(price);
				casetest.setPic1(ImageUtil.shrink(pic, 50));
				casetest.setCount(count);
				androidCaseVO.add(casetest);
			}
			// }

			Collections.sort(androidCaseVO, new Comparator<AndroidCasesVO>() {

				public int compare(AndroidCasesVO o1, AndroidCasesVO o2) {
					return o2.getCasesvo().getCaseno()
							- o1.getCasesvo().getCaseno();
				}
			});

		}

		if (action.equals("getCaseno")) {

			String caseCaseno = rq.getParameter("caseCaseno");
			String helpmemno = rq.getParameter("helpforMember");
			int caseno = Integer.parseInt(caseCaseno);
			int memno = Integer.parseInt(helpmemno);
			System.out.println(caseno);
			MemberVO memberImage = memberService.findByPrimaryKey(memno);
			CasesVO caseDetail = casesService.getByPrimaryKey(caseno);
			Integer oderTotalOty = orderService.getTotalOrderQty(caseno);

			int helpCpno = caseDetail.getCpno();
			int helpSpno = caseDetail.getSpno();
			CaseProductVO caseProductDetail = null;
			ShopproductVO shopproductVO = null;
			if (helpCpno != 0) {
				caseProductDetail = caseProductService
						.getOneByPrimaryKeyHasPic(helpCpno);
			} else {
				ShopproductService shopproductService = new ShopproductService();
				shopproductVO = shopproductService.getOneShopproduct(helpSpno);
			}
			// caseProductDetail(ImageUtil.shrink(caseProductDetail.getPic1(),
			// 50));

			AndroidCasesVO casetest = new AndroidCasesVO();

			casetest.setCaseproductvo(caseProductDetail);
			casetest.setShopproductVO(shopproductVO);
			casetest.setCasesvo(caseDetail);
			casetest.setTotalOty(oderTotalOty);
			casetest.setMembervo(memberImage);
			androidCaseVO.add(casetest);

		}
		if (action.equals("goCaseQA")) {
			String memnoString = rq.getParameter("memno");
			String casenoString = rq.getParameter("caseno");
			rq.setCharacterEncoding("UTF-8");
			String caseQAString = new String (rq.getParameter("caseQA").getBytes("ISO-8859-1"),"UTF-8");
			
			String helpTimeString = rq.getParameter("helpTime");

			int memno = Integer.parseInt(memnoString);
			int caseno = Integer.parseInt(casenoString);
			String question = String.valueOf(caseQAString).trim();

			Timestamp qtime = new Timestamp(System.currentTimeMillis());

			Integer askQA = caseQAService.askQuestion(memno, caseno, question,
					qtime);

			AndroidCasesVO casetest = new AndroidCasesVO();
			casetest.setHelpCaseQA(askQA);
			androidCaseVO.add(casetest);
		}
		if (action.equals("keywordsearch")) {
			Integer count = 0;
			rq.setCharacterEncoding("UTF-8");
			String word = new String (rq.getParameter("word").getBytes("ISO-8859-1"),"UTF-8");
			
			
			// for (CaseProductVO casehelp : caseProductVO) {

			Set<CasesVO> casevo = casesService.getByTitleKeyword(word, 1, 355);
			for (CasesVO wordcase : casevo) {
						
				Integer price = 0;

				byte[] pic = null;

				if (wordcase.getCpno() == 0) {
					ShopproductService shopproductService = new ShopproductService();
					ShopproductVO shopproductVO = shopproductService
							.getOneShopproduct(wordcase.getSpno());

					price = shopproductVO.getUnitprice().intValue();

					pic = shopproductVO.getPic1();

					count += 1;
				}else{
					
					CaseProductVO caseProduct = caseProductService
							.getOneByPrimaryKeyHasPic(wordcase.getCpno());
					price = caseProduct.getUnitprice();
					pic = caseProduct.getPic1();
					count += 1;
					
					
				}
				// if (wordcase.getStatus().intValue() == 1
				// && casehelp.getCpno().intValue() == wordcase
				// .getCpno().intValue()) {
			
				AndroidCasesVO casetest = new AndroidCasesVO();

				 casetest.setCasesvo(wordcase);
				 casetest.setUnitprice(price);
				 casetest.setPic1(ImageUtil.shrink(pic,
				 50));
				casetest.setCount(count);
				androidCaseVO.add(casetest);

				// }

			}

			// }
			Collections.sort(androidCaseVO, new Comparator<AndroidCasesVO>() {

				public int compare(AndroidCasesVO o1, AndroidCasesVO o2) {
					return o2.getCasesvo().getCaseno()
							- o1.getCasesvo().getCaseno();
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

// for(CasesVO aaa:CaseImage){
// for(CaseProductVO XXX:caseProductVO){
//
// if(aaa.getCpno()==XXX.getCpno()){
// aaa XXX ==VO
// LIST.Add
// }
// }
// }