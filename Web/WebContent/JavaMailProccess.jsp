<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>    
<%@ page import="java.util.*,java.io.*"%>
<%@ page import="javax.mail.*" %>
<%@ page import="javax.mail.internet.*" %>
<%@ page import="javax.activation.*" %>


<%!
	InternetAddress[] address = null ;
%>

<%

	String user_name = (String) request.getAttribute("user_name");
	Integer user_memno = (Integer)request.getAttribute("user_memno");
	String user_email = (String) request.getAttribute("user_email");
	String user_password = (String) request.getAttribute("user_password");
	String ServerName = request.getServerName();
	String ContextPath = request.getContextPath();
	
	
	String mailserver   = "140.115.236.9";
	String From         = "admin@taobao.com.tw";
	String to           = user_email;
	String Subject      = "�b���w�g�ҥ�";
		String messageText  = "���±z�����U�C  ���I���p�U�s���w�ҥαz���b��: http://"+ServerName+ ":8081" + ContextPath+"/memberC?action=member_activate&memno="+user_memno+"&status=1"; 
		System.out.println(messageText);
	    boolean sessionDebug = false;

try {

  // �]�w�ҭn�Ϊ�Mail ���A���M�ҨϥΪ��ǰe��w
  java.util.Properties props = System.getProperties();
  props.put("mail.host",mailserver);
  props.put("mail.transport.protocol","smtp");
  
  // ���ͷs��Session �A��
  javax.mail.Session mailSession = javax.mail.Session.getDefaultInstance(props,null);
  mailSession.setDebug(sessionDebug);
	
  Message msg = new MimeMessage(mailSession);
  
  // �]�w�ǰe�l�󪺵o�H�H
  msg.setFrom(new InternetAddress(From));
  
  // �]�w�ǰe�l��ܦ��H�H���H�c
  address = InternetAddress.parse(to,false);
  msg.setRecipients(Message.RecipientType.TO, address);
  
  // �]�w�H�����D�D 
  msg.setSubject(Subject);
  // �]�w�e�H���ɶ�
  msg.setSentDate(new Date());
  
  // �]�w�ǰe�H��MIME Type
  msg.setText(messageText);
  
  // �e�H
  Transport.send(msg);

      //response.sendRedirect("emp_select.jsp?msg=Y");
    System.out.println("�ǰe���\!");
    out.println("<script >document.open('mail_ok.jsp', '' ,'height=110,width=390,left=200,top=120,resizable=no')</script>");	
}
    catch (MessagingException mex) {
      //response.sendRedirect("emp_select.jsp?msg=N");
    System.out.println("�ǰe����!");
    out.println("<script >document.open('mail_error.jsp', '' ,'height=110,width=390,left=200,top=120,resizable=no')</script>");  
      //mex.printStackTrace();
    }
 
%>