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
	String Subject =(String)request.getAttribute("Subject");	//String Subject = "�z���K�X";
	String messageText = "Hello! " + "<span style='color:green;'>"
			+ nick + "</span>" + "<br>" + "�b��:"
			+ "<span style='color:blue;'>" + ch_name + "</span>"
			+ "<br>" + " ���԰O���K�X: " + "<span style='color:red;'>"
			+ passRandom + "</span>" + " (�w�g�ҥ�)";

	boolean sessionDebug = false;

 	try {

		// �]�w�ҭn�Ϊ�Mail ���A���M�ҨϥΪ��ǰe��w
		java.util.Properties props = System.getProperties();
		props.put("mail.host", mailserver);
		props.put("mail.transport.protocol", "smtp");

		// ���ͷs��Session �A��
		javax.mail.Session mailSession = javax.mail.Session
				.getDefaultInstance(props, null);
		mailSession.setDebug(sessionDebug);

		Message msg = new MimeMessage(mailSession);

		// �]�w�ǰe�l�󪺵o�H�H
		msg.setFrom(new InternetAddress(From));

		// �]�w�ǰe�l��ܦ��H�H���H�c
		address = InternetAddress.parse(to, false);
		msg.setRecipients(Message.RecipientType.TO, address);

		// �]�w�H�����D�D 
		msg.setSubject(Subject);
		// �]�w�e�H���ɶ�
		msg.setSentDate(new Date());

		// �]�w�ǰe�H��MIME Type
		msg.setContent(messageText, "text/html; charset=UTF-8");

	//	msg.setText(messageText);

		// �e�H
		Transport.send(msg);

		//response.sendRedirect("emp_select.jsp?msg=Y");
		System.out.println("�ǰe���\!");

 	} catch (MessagingException mex) {
 		//response.sendRedirect("emp_select.jsp?msg=N");
		System.out.println("�ǰe����!");
 	}
%>