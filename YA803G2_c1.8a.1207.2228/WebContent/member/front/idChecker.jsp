<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.tao.member.model.*"%>
<%@ page import="com.tao.location.model.*"%>
<%
   String userInput = request.getParameter("userInput"); 

   MemberService dao = new MemberService();
   List<String> idList = dao.getAllID();
   StringBuilder builder =  new StringBuilder(""); 
   int lastRun = idList.size()-1;
   int runner = 1;
   String lastId = null;

   
   builder.append(" {\"idList\": [ ");
   for (String data : idList) {
	   if (runner <= lastRun){
		   builder.append(" {\"memid\":\""+data+"\"}, ");		   		      
	   }
	   runner++;
	   lastId = data;
   } 
   builder.append(" {\"memid\":\""+lastId+"\"} ");
   builder.append(" ]} "); 
   
%>	 
<%=builder%>
