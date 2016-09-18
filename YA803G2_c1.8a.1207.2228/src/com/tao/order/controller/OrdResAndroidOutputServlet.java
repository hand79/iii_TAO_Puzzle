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
        case SUCCESS://���\
            Integer genKey = (Integer) request.getAttribute("genKey");// �s�إߪ��q�椧�q��s��
            String success = new String("1");
            PrintWriter out1 = response.getWriter();
    		out1.println(success);
    		out1.close();
            
    		 
            break;
        case ERROR://�o�Ϳ��~�A�i��O�ШD�ѼƤ����T�Ϊ̷s�W���ѩҾɭP
        	 String error = new String("2");
             PrintWriter out2 = response.getWriter();
     		out2.println(error);
     		out2.close();
        	
          
            break;
        case TO_DEPOSIT://������
        	
        	 String nomoney = new String("3");
             PrintWriter out3 = response.getWriter();
     		out3.println(nomoney);
     		out3.close();

      
 
            break;
        case CASE_NOT_AVALIBLE://�X�ʮפw�I��
        	String caseTimeEnd = new String("4");
            PrintWriter out4 = response.getWriter();
    		out4.println(caseTimeEnd);
    		out4.close();
            break;
        case TO_LOGIN://�S�n�J(session�䤣��CurrentUser��MemberVO)
        	
            break;
        case OUT_OF_STOCK://�i�ʶR�q����
            System.out.println(6);
            break;
        default:
        	
            break;
        }
 
    }
 
}