package com.tao.androiddpsOrd.controller;





import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.google.gson.Gson;
import com.tao.andriodhelp.ImageUtil;
import com.tao.androidcases.controller.AndroidCasesVO;
import com.tao.dpsOrd.model.DpsOrdService;
import com.tao.dpsOrd.model.DpsOrdVO;
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
public class DpsOrdServlet extends HttpServlet {
	public void doGet(HttpServletRequest rq, HttpServletResponse rp)
			throws ServletException, IOException {
		ServletContext context = getServletContext();
		String contentType = context.getInitParameter("contentType");
		String outStr = "";
		
		
		
		MemberService memberService = new MemberService(); 
		
		DpsOrdService dpsOrdService = new DpsOrdService();
	


		Gson gson = new Gson();
		String action = rq.getParameter("helpMoney");
		
		List<AndroidCasesVO> androidCaseVO = new ArrayList<AndroidCasesVO>();
		if (action.equals("getMoneyOrder")) {
			
			
			String memberMemId = rq.getParameter("member");
			MemberVO helpGetMemberData = memberService.findByMemberID(memberMemId);
			System.out.println("會員"+helpGetMemberData);
			List<DpsOrdVO> help999 = dpsOrdService.getAll();
			
		
			for(DpsOrdVO help888 : help999){
				if(help888.getMemno().intValue() == helpGetMemberData.getMemno().intValue()){
					
					
					AndroidCasesVO casetest = new AndroidCasesVO();

					casetest.setMembervo(helpGetMemberData);
					casetest.setDpsOrdVO(help888);
					
					
					
					androidCaseVO.add(casetest);
					
				}
			}
			
		}if (action.equals("addDpsord")){
			
			String memberMemId = rq.getParameter("memid");
			String dpshow = rq.getParameter("dpshow");
			String dpsamntString = rq.getParameter("dpsamnt");
			String atmac = rq.getParameter("atmac");
		
			System.out.println("不要阿"+dpsamntString);
			Double dpsamnt = Double.parseDouble(dpsamntString);
			Timestamp dpTime = new Timestamp(System.currentTimeMillis());
			MemberVO helpGetMemberData = memberService.findByMemberID(memberMemId);
			Integer memno = Integer.valueOf(helpGetMemberData.getMemno());
			
			String ordsts = new String("WAITING");
			
			
			DpsOrdVO addMoneyDp = new DpsOrdVO();
			
			addMoneyDp.setMemno(memno);
			addMoneyDp.setAtmac(atmac);
			addMoneyDp.setDpsamnt(dpsamnt);
			addMoneyDp.setDpshow(dpshow);
			addMoneyDp.setDpsordt(dpTime);
			addMoneyDp.setOrdsts(ordsts);
		      addMoneyDp = dpsOrdService.addDpsOrdVO(dpTime , dpsamnt ,  memno , dpshow , ordsts ,atmac);
		      AndroidCasesVO casetest = new AndroidCasesVO();
		      casetest.setDpsOrdVO(addMoneyDp);
		      androidCaseVO.add(casetest);
		      
		}
		
		Collections.sort(androidCaseVO , new Comparator<AndroidCasesVO>(){
			
			
			 public int compare(AndroidCasesVO o1, AndroidCasesVO o2) {
	                return o2.getDpsOrdVO().getDpsordno() - o1.getDpsOrdVO().getDpsordno();
	            }
		});
		

		outStr = gson.toJson(androidCaseVO);
		System.out.println(outStr);
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