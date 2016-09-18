package com.tao.member.controller;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import com.tao.member.model.MemberService;
import com.tao.member.model.MemberVO;

public class LoginHandler extends HttpServlet {

  @Override
public void doPost(HttpServletRequest req, HttpServletResponse res)
                                throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    res.setContentType("text/html; charset=UTF-8");
    //PrintWriter out = res.getWriter();
    
    HttpSession session = req.getSession();
    List<String> errorMsgs = new LinkedList<String>();
	req.setAttribute("errorMsgs", errorMsgs);
    
    // 【取得使用者 帳號(account) 密碼(password)】
    String account = req.getParameter("account");
    String password = req.getParameter("password");
    
    if (account!=null && account.length()!=0){
    	if (password!=null && password.length()!=0){
    		MemberService memSvc = new MemberService();
    		MemberVO memberVO = memSvc.findByMemberID(account);
    		if (memberVO != null) {
    			if ((memberVO.getMemid()).equals(account) && (memberVO.getMempw()).equals(password)) {
    				if(memberVO.getStatus()!=1){
    					errorMsgs.add("帳號已經註冊但尚未啟動, 請先啟動帳戶。");
    				} else {
    					session.setAttribute("account", account);   //*工作1: 才在session內做已經登入過的標識
      			      	session.setAttribute("CurrentUser", memberVO);   //\
      			      	session.setAttribute("userArea", LocnoAreaConverter.locnoToArea(memberVO.getLocno()));   //
    				}
    			} else {
    				errorMsgs.add("帳號與密碼輸入錯誤, 請重新輸入。");
    			}
    		} else {
    			errorMsgs.add("系統查無此帳號, 請重新確認帳號。");
    		}
    	}else{
    		errorMsgs.add("請輸入密碼");
    	}
    	
    }else{
    	errorMsgs.add("請輸入帳號");
    }
  
    // 【檢查該帳號 , 密碼是否有效】
    if (!errorMsgs.isEmpty()) {         //【帳號 , 密碼無效時】
		RequestDispatcher failureView = req
				.getRequestDispatcher("/member/front/memberLogin.jsp");
		failureView.forward(req, res);
		return; //程式中斷
    }
	                                     //【帳號 , 密碼有效時, 才做以下工作】
     
      
   try {                                      //*工作2: 看看有無來源網頁 (-如有:則重導之)                  
     String location = (String) session.getAttribute("location");
     if (location != null) {
       session.removeAttribute("location");
       res.sendRedirect(location);            
       return;
     }
   }catch (Exception ignored) { }

  res.sendRedirect(req.getContextPath()+"/index.jsp");           //*工作3: (-如無: 無來源網頁, 則重導至index.html網頁, 目前為會員修改頁面)

  }
}
