package com.tao.androidshop.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.google.gson.Gson;
import com.tao.Androidsearchresult.model.shopResultService;
import com.tao.Androidsearchresult.model.shopResultVO;
import com.tao.andriodhelp.ImageUtil;
import com.tao.androidcases.controller.AndroidCasesVO;
import com.tao.androidshop.model.ShopService;
import com.tao.androidshop.model.ShopVO;
import com.tao.androidshopproduct.model.ShopproductService;
import com.tao.androidshopproduct.model.ShopproductVO;
import com.tao.cases.model.CaseProductService;
import com.tao.cases.model.CaseProductVO;
import com.tao.cases.model.CasesService;
import com.tao.cases.model.CasesVO;
import com.tao.jimmy.location.LocationService;
import com.tao.jimmy.location.LocationVO;
import com.tao.jimmy.util.model.ColumnValue;
import com.tao.member.model.MemberService;
import com.tao.member.model.MemberVO;
import com.tao.order.model.OrderService;
import com.tao.shopRep.model.ShopRepService;
import com.tao.shopRep.model.ShopRepVO;






import java.io.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@SuppressWarnings("serial")
public class ShopServletAndroid extends HttpServlet {
	public void doGet(HttpServletRequest rq, HttpServletResponse rp)
			throws ServletException, IOException {
		ServletContext context = getServletContext();
		String contentType = context.getInitParameter("contentType");
		String outStr = "";

		MemberService memberService = new MemberService();
		ShopService shopService = new ShopService();
		CasesService casesService = new CasesService();
		OrderService orderService = new OrderService();
		CaseProductService caseProductService = new CaseProductService();
		ShopproductService shopproductService = new ShopproductService();
		ShopRepService shopRepService = new ShopRepService();
		shopResultService shopkeywordService = new shopResultService();
		LocationService locationService = new LocationService();
		
		
		
		Gson gson = new Gson();
		String action = rq.getParameter("helpShopList");
		List<AndroidCasesVO> androidCaseVOList = new ArrayList<AndroidCasesVO>();

		if (action.equals("getShopList")) {
			Integer count = 0;
			List<ShopVO> shopgohelp = shopService.getAll();

			for (ShopVO shopend : shopgohelp) {
				if (shopend.getStatus() == 2) {
					count += 1;
					AndroidCasesVO casetest = new AndroidCasesVO();
					
					casetest.setShopvo(shopend);
					casetest.setCount(count);
					androidCaseVOList.add(casetest);
				}
			}
				
			Collections.sort(androidCaseVOList , new Comparator<AndroidCasesVO>(){
				
				
				 public int compare(AndroidCasesVO o1, AndroidCasesVO o2) {
		                return o2.getShopvo().getShopno() - o1.getShopvo().getShopno();
		            }
			});
			
		}
		if (action.equals("getShopno")) {

			String shopnoString = rq.getParameter("shopShopno");
			String helpmemno = rq.getParameter("helpforMember");
			
			Integer shopno = Integer.valueOf(shopnoString);

			Integer memno = Integer.valueOf(helpmemno);
			
			
			
			ShopVO shopDetailData = shopService.getOneShop(shopno);
			MemberVO memberImage = memberService.findByPrimaryKey(memno);
			
			LocationVO locationVO = locationService.findByPrimaryKey(shopDetailData.getLocno());
			
			AndroidCasesVO casetest = new AndroidCasesVO();
			casetest.setLocationVO(locationVO);
			casetest.setShopvo(shopDetailData);
			casetest.setMembervo(memberImage);
			androidCaseVOList.add(casetest);
		}

		if (action.equals("getShopProduct")) {

			String shopnoString = rq.getParameter("shopShopno");
			Integer shopno = Integer.valueOf(shopnoString);

			List<ShopproductVO> shopProductList = shopproductService.getAll();

			for (ShopproductVO shopProductend : shopProductList) {

				if (shopProductend.getShopno().intValue() == shopno) {

					AndroidCasesVO casetest = new AndroidCasesVO();

					casetest.setShopproductVO(shopProductend);
					androidCaseVOList.add(casetest);
				}

			}
			
			Collections.sort(androidCaseVOList , new Comparator<AndroidCasesVO>(){
				
				
				 public int compare(AndroidCasesVO o1, AndroidCasesVO o2) {
		                return o2.getShopproductVO().getSpno() - o1.getShopproductVO().getSpno();
		            }
			});
			
		}
		if (action.equals("getShopcase")) {
			String spnoString = rq.getParameter("spno");
			Integer spno = Integer.valueOf(spnoString);

//			List<CaseProductVO> caseProductVO = caseProductService
//					.getAllHasPic();
//			for (CaseProductVO casehelp : caseProductVO) {

				Set<CasesVO> casegohelp = casesService.compositeQuery(
						new ColumnValue("status",Integer.toString(CasesVO.STATUS_PUBLIC)), new ColumnValue("spno",
								spnoString));

				for (CasesVO shopCase : casegohelp) {
//					if (shopCase.getSpno().intValue() == spno.intValue()) {

						Integer oderTotalOty = orderService
								.getTotalOrderQty(shopCase.getCaseno());

						AndroidCasesVO casevo = new AndroidCasesVO();
//						casetest.setCaseproductvo(casehelp);
						casevo.setTotalOty(oderTotalOty);
						casevo.setCasesvo(shopCase);
						androidCaseVOList.add(casevo);

//					}
				}
//			}
			Collections.sort(androidCaseVOList , new Comparator<AndroidCasesVO>(){
				
				
				 public int compare(AndroidCasesVO o1, AndroidCasesVO o2) {
		                return o2.getCasesvo().getCaseno() - o1.getCasesvo().getCaseno();
		            }
			});
		}
		if (action.equals("delshop")){
			
			String memid = rq.getParameter("memid");
			
			String shopnoString = rq.getParameter("shopno").trim();
			String memnoString = rq.getParameter("memno").trim();
			
			rq.setCharacterEncoding("UTF-8");
			String shopQA = new String (rq.getParameter("shopQA").getBytes("ISO-8859-1"),"UTF-8");
			
			Integer shopno = Integer.parseInt(shopnoString);
			Integer memno = Integer.parseInt(memnoString);
		
			MemberVO member = memberService.findByMemberID(memid);
			
			
			ShopRepVO    addShop =   shopRepService.addShopRep(shopno, member.getMemno(), shopQA);
			
			boolean isMember = true;
			
			AndroidCasesVO casetest = new AndroidCasesVO();
			String result = Boolean.toString(isMember);
			casetest.setResult(result);
			androidCaseVOList.add(casetest);
			
			
			
		}
		if (action.equals("keywordsearch")){
			Integer count = 0;
			rq.setCharacterEncoding("UTF-8");
			String word = new String (rq.getParameter("word").getBytes("ISO-8859-1"),"UTF-8");
			System.out.println("???¤£·|§a"+word);
			List<ShopVO>  shoplist =  shopkeywordService.findShopByNameKey(word);
					
			for(ShopVO shopword : shoplist){
				
				ShopVO  truelist = shopService.getOneShop(shopword.getShopno());
				
				count += 1;
				AndroidCasesVO casetest = new AndroidCasesVO();
				
				casetest.setCount(count);
				casetest.setShopvo(truelist);
				androidCaseVOList.add(casetest);
			}
			Collections.sort(androidCaseVOList, new Comparator<AndroidCasesVO>() {

				public int compare(AndroidCasesVO o1, AndroidCasesVO o2) {
					return o2.getShopvo().getShopno()
							- o1.getShopvo().getShopno();
				}
			});
		}

		outStr = gson.toJson(androidCaseVOList);
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