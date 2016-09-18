package com.tao.order.controller;
 
import java.io.IOException;
 
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class OrdResAndroidOutputServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
 
        doPost(request, response);
    }
 
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        OrderAjaxRes resStatus = (OrderAjaxRes) request.getAttribute("resStatus");
        switch (resStatus) {
        case SUCCESS://成功
            Integer genKey = (Integer) request.getAttribute("genKey");// 新建立的訂單之訂單編號
            String success = new String("1");
            PrintWriter out1 = response.getWriter();
    		out1.println(success);
    		out1.close();
            
    		 
            break;
        case ERROR://發生錯誤，可能是請求參數不正確或者新增失敗所導致
        	 String error = new String("2");
             PrintWriter out2 = response.getWriter();
     		out2.println(error);
     		out2.close();
        	
          
            break;
        case TO_DEPOSIT://錢不夠
        	
        	 String nomoney = new String("3");
             PrintWriter out3 = response.getWriter();
     		out3.println(nomoney);
     		out3.close();

      
 
            break;
        case CASE_NOT_AVALIBLE://合購案已截止
        	String caseTimeEnd = new String("4");
            PrintWriter out4 = response.getWriter();
    		out4.println(caseTimeEnd);
    		out4.close();
            break;
        case TO_LOGIN://沒登入(session找不到CurrentUser的MemberVO)
        	
            break;
        case OUT_OF_STOCK://可購買量不夠
            System.out.println(6);
            break;
        default:
        	
            break;
        }
 
    }
 
}