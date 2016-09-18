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
    
    // �i���o�ϥΪ� �b��(account) �K�X(password)�j
    String account = req.getParameter("account");
    String password = req.getParameter("password");
    
    if (account!=null && account.length()!=0){
    	if (password!=null && password.length()!=0){
    		MemberService memSvc = new MemberService();
    		MemberVO memberVO = memSvc.findByMemberID(account);
    		if (memberVO != null) {
    			if ((memberVO.getMemid()).equals(account) && (memberVO.getMempw()).equals(password)) {
    				if(memberVO.getStatus()!=1){
    					errorMsgs.add("�b���w�g���U���|���Ұ�, �Х��Ұʱb��C");
    				} else {
    					session.setAttribute("account", account);   //*�u�@1: �~�bsession�����w�g�n�J�L������
      			      	session.setAttribute("CurrentUser", memberVO);   //\
      			      	session.setAttribute("userArea", LocnoAreaConverter.locnoToArea(memberVO.getLocno()));   //
    				}
    			} else {
    				errorMsgs.add("�b���P�K�X��J���~, �Э��s��J�C");
    			}
    		} else {
    			errorMsgs.add("�t�άd�L���b��, �Э��s�T�{�b���C");
    		}
    	}else{
    		errorMsgs.add("�п�J�K�X");
    	}
    	
    }else{
    	errorMsgs.add("�п�J�b��");
    }
  
    // �i�ˬd�ӱb�� , �K�X�O�_���ġj
    if (!errorMsgs.isEmpty()) {         //�i�b�� , �K�X�L�Įɡj
		RequestDispatcher failureView = req
				.getRequestDispatcher("/member/front/memberLogin.jsp");
		failureView.forward(req, res);
		return; //�{�����_
    }
	                                     //�i�b�� , �K�X���Į�, �~���H�U�u�@�j
     
      
   try {                                      //*�u�@2: �ݬݦ��L�ӷ����� (-�p��:�h���ɤ�)                  
     String location = (String) session.getAttribute("location");
     if (location != null) {
       session.removeAttribute("location");
       res.sendRedirect(location);            
       return;
     }
   }catch (Exception ignored) { }

  res.sendRedirect(req.getContextPath()+"/index.jsp");           //*�u�@3: (-�p�L: �L�ӷ�����, �h���ɦ�index.html����, �ثe���|���קﭶ��)

  }
}
