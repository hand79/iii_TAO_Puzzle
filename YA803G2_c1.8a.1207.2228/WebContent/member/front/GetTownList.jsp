<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.tao.member.model.*"%>
<%@ page import="com.tao.location.model.*"%>
<%
   String countyrange = request.getParameter("countyrange");

   LocationService dao = new LocationService();
   List<String[]> townsList = dao.getMatchedTowns(countyrange);
   pageContext.setAttribute("townsList",townsList);
   
   String lastLocno = null;
   String lastTown = null;
   StringBuilder builder =  new StringBuilder(""); 
   int lastRun = townsList.size()-1;
   int runner = 1;
		   
   
   builder.append(" {\"townsList\":[ ");
   for (String[] data : townsList) {
	   if (runner <= lastRun){
		   builder.append(" {\"locno\":\""+data[0]+"\",\"town\":\""+data[1]+"\"}, ");		   		      
	   }
	   runner++;
	   lastLocno = data[0];
	   lastTown = data[1];
   } 
   builder.append(" {\"locno\":\""+lastLocno+"\",\"town\":\""+lastTown+"\"} ");
   builder.append(" ]} "); 
   
%>	 
<%=builder%>
