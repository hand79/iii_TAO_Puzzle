package com.tao.androidorder.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.google.gson.Gson;
import com.tao.andriodhelp.ImageUtil;
import com.tao.androidcases.controller.AndroidCasesVO;
import com.tao.androidshopproduct.model.ShopproductService;
import com.tao.androidshopproduct.model.ShopproductVO;
import com.tao.caseRep.model.CaseRepService;
import com.tao.caseRep.model.CaseRepVO;
import com.tao.cases.model.CaseProductService;
import com.tao.cases.model.CaseProductVO;
import com.tao.cases.model.CasesService;
import com.tao.cases.model.CasesVO;
import com.tao.jimmy.util.model.ColumnValue;
import com.tao.member.model.MemberService;
import com.tao.member.model.MemberVO;
import com.tao.order.model.OrderService;
import com.tao.order.model.OrderVO;
import com.tao.wishList.model.WishListService;
import com.tao.wishList.model.WishListVO;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("serial")
public class OrderServlet extends HttpServlet {
	public void doGet(HttpServletRequest rq, HttpServletResponse rp)
			throws ServletException, IOException {
		ServletContext context = getServletContext();
		String contentType = context.getInitParameter("contentType");
		String outStr = "";

		MemberService memberService = new MemberService();
		OrderService orderService = new OrderService();
		CasesService casesService = new CasesService();
		CaseProductService caseProductService = new CaseProductService();
		
		CaseRepService caseRepService = new CaseRepService();

		Gson gson = new Gson();

		String action = rq.getParameter("helpForOrder");

		List<AndroidCasesVO> androidCaseVO = new ArrayList<AndroidCasesVO>();
		if (action.equals("inOrderPage")) {

			String memIDIDID = rq.getParameter("helpforId");
			String caseNONONO = rq.getParameter("helpforNo");
			int caseNooo = Integer.parseInt(caseNONONO);

			MemberVO memberVO = memberService.findByMemberID(memIDIDID);
			Integer oderTotalOty = orderService.getTotalOrderQty(caseNooo);

			AndroidCasesVO casetest = new AndroidCasesVO();
			casetest.setMembervo(memberVO);
			casetest.setTotalOty(oderTotalOty);
			androidCaseVO.add(casetest);
		}

		if (action.equals("getMyCase")) {

			String memid = rq.getParameter("member");
			MemberVO member = memberService.findByMemberID(memid);
			Set<CasesVO> mycase = casesService.getByCreator(member.getMemno(), true, false);

			for (CasesVO caseend : mycase) {

				Integer caseno = caseend.getCaseno();
				Integer oderTotalOty = orderService.getTotalOrderQty(caseno);

				AndroidCasesVO casetest = new AndroidCasesVO();

				casetest.setCasesvo(caseend);
				casetest.setTotalOty(oderTotalOty);

				androidCaseVO.add(casetest);

			}
			Collections.sort(androidCaseVO, new Comparator<AndroidCasesVO>() {

				public int compare(AndroidCasesVO o1, AndroidCasesVO o2) {
					return o2.getCasesvo().getCaseno()
							- o1.getCasesvo().getCaseno();
				}
			});
			

		}
		if (action.equals("getJoinCase")) {
			String memid = rq.getParameter("member");
			MemberVO member = memberService.findByMemberID(memid);
			List<OrderVO> helpGetOrder = orderService.findByBuyer(member
					.getMemno());

			for (OrderVO MyOrder : helpGetOrder) {

				Integer oderTotalOty = orderService.getTotalOrderQty(MyOrder
						.getCaseno());
				CasesVO casevo = casesService.getByPrimaryKey(MyOrder
						.getCaseno());
				AndroidCasesVO casetest = new AndroidCasesVO();

				casetest.setOrderVO(MyOrder);
				casetest.setCasesvo(casevo);
				casetest.setTotalOty(oderTotalOty);

				androidCaseVO.add(casetest);

			}
			Collections.sort(androidCaseVO, new Comparator<AndroidCasesVO>() {

				public int compare(AndroidCasesVO o1, AndroidCasesVO o2) {
					return ((o2.getOrderVO().getOrdtime().getTime()
							- o1.getOrderVO().getOrdtime().getTime()) > 0) ? 1 : -1;
				}
			});

		}
		if (action.equals("getWishCase")) {

			String memid = rq.getParameter("memid");
			MemberVO member = memberService.findByMemberID(memid);
			WishListService wishListService = new WishListService();
			Map<String, String[]> map = new HashMap<>();
			map.put("memno", new String[] { member.getMemno().toString() });
			List<WishListVO> wishlist = wishListService.getAll(map);
			for (WishListVO wishdata : wishlist) {

//				if (wishdata.getMemno().intValue() == member.getMemno()
//						.intValue()) {
				CaseProductVO caseProductVO = null;
				ShopproductVO  shopproductVO = null ;
					CasesVO casevo = casesService.getByPrimaryKey(wishdata
							.getCaseno());
					if(casevo.getSpno() == 0){
					 caseProductVO = caseProductService
							.getOneByPrimaryKeyNoPic(casevo.getCpno());
					}else{
						ShopproductService shopproductService = new ShopproductService();
						  shopproductVO  =  shopproductService.getOneShopproduct(casevo.getSpno());
					}
					
					
					
					
					Integer oderTotalOty = orderService.getTotalOrderQty(casevo
							.getCaseno());
					AndroidCasesVO casetest = new AndroidCasesVO();

					casetest.setTotalOty(oderTotalOty);
					casetest.setWishListVO(wishdata);
					casetest.setCaseproductvo(caseProductVO);
					casetest.setShopproductVO(shopproductVO);
					casetest.setCasesvo(casevo);
					androidCaseVO.add(casetest);

//				}
			}
			Collections.sort(androidCaseVO, new Comparator<AndroidCasesVO>() {

				public int compare(AndroidCasesVO o1, AndroidCasesVO o2) {
					return o2.getCasesvo().getCaseno()
							- o1.getCasesvo().getCaseno();
				}
			});

		}
		if (action.equals("addWish")) {

			String memid = rq.getParameter("memid");
			
			String casenoString = rq.getParameter("caseno");
			int caseno = Integer.parseInt(casenoString);

			MemberVO member = memberService.findByMemberID(memid);
			boolean hasRecord = false;
			WishListService wishListService = new WishListService();
			Map<String, String[]> map = new HashMap<>();
			map.put("memno", new String[] { member.getMemno().toString() });
			List<WishListVO> wishlist = wishListService.getAll(map);

			
			for(WishListVO wishvo : wishlist){
				
			if (wishvo.getCaseno().intValue() == caseno) {
				
				hasRecord = true;
			}

			}
			
			if(!hasRecord){
			wishListService.addWishList(member.getMemno(), caseno);
			
			String result = Boolean.toString(hasRecord);

			AndroidCasesVO casetest = new AndroidCasesVO();

			casetest.setResult(result);
			androidCaseVO.add(casetest);
			}
			
		
		}
		if (action.equals("delcase")) {

			String memid = rq.getParameter("memid");

			String casenoString = rq.getParameter("caseno").trim();
			String memnoString = rq.getParameter("memno").trim();
			rq.setCharacterEncoding("UTF-8");
			String caseQA = new String (rq.getParameter("caseQA").getBytes("ISO-8859-1"),"UTF-8");
		

			Integer caseno = Integer.parseInt(casenoString);
			Integer memno = Integer.parseInt(memnoString);

			MemberVO member = memberService.findByMemberID(memid);

			CaseRepVO addCaseRep = caseRepService.addCaseRep(member.getMemno(),
					memno, caseno, caseQA);
			boolean isMember = true;

			AndroidCasesVO casetest = new AndroidCasesVO();
			String result = Boolean.toString(isMember);
			casetest.setResult(result);
			androidCaseVO.add(casetest);

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