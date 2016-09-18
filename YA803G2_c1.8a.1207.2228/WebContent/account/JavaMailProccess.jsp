<%@ page language="java" import="java.util.*,java.io.*"
	contentType="text/html;charset=UTF-8" pageEncoding="big5"%>
<%@ page import="javax.mail.*"%>
<%@ page import="javax.mail.internet.*"%>
<%@ page import="javax.activation.*"%>


<%!InternetAddress[] address = null;%>

<%
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");
	String ch_name = (String) request.getAttribute("ch_name");
	String passRandom = (String) request.getAttribute("passRandom");
	String nick = (String) request.getAttribute("nick");
	String mailserver = "140.115.236.9";
	String From = "tao@tao.com.tw";
	String to = (String) request.getAttribute("email");
	String Subject =(String)request.getAttribute("Subject");	//String Subject = "您的密碼";
	String messageText = "Hello! " + "<span style='color:green;'>"
			+ nick + "</span>" + "<br>" + "帳號:"
			+ "<span style='color:blue;'>" + ch_name + "</span>"
			+ "<br>" + " 請謹記此密碼: " + "<span style='color:red;'>"
			+ passRandom + "</span>" + " (已經啟用)";

	boolean sessionDebug = false;

 	try {

		// 設定所要用的Mail 伺服器和所使用的傳送協定
		java.util.Properties props = System.getProperties();
		props.put("mail.host", mailserver);
		props.put("mail.transport.protocol", "smtp");

		// 產生新的Session 服務
		javax.mail.Session mailSession = javax.mail.Session
				.getDefaultInstance(props, null);
		mailSession.setDebug(sessionDebug);

		Message msg = new MimeMessage(mailSession);

		// 設定傳送郵件的發信人
		msg.setFrom(new InternetAddress(From));

		// 設定傳送郵件至收信人的信箱
		address = InternetAddress.parse(to, false);
		msg.setRecipients(Message.RecipientType.TO, address);

		// 設定信中的主題 
		msg.setSubject(Subject);
		// 設定送信的時間
		msg.setSentDate(new Date());

		// 設定傳送信的MIME Type
		msg.setContent(messageText, "text/html; charset=UTF-8");

	//	msg.setText(messageText);

		// 送信
		Transport.send(msg);

		//response.sendRedirect("emp_select.jsp?msg=Y");
		System.out.println("傳送成功!");

 	} catch (MessagingException mex) {
 		//response.sendRedirect("emp_select.jsp?msg=N");
		System.out.println("傳送失敗!");
 	}
%>