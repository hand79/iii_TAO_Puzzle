<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<%--**** LOGIN REDIRECT ****--%>
<%@include file="/f/frag/f_login_redirect.file" %>
<%--**** LOGIN REDIRECT ****--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<jsp:include page="/f/frag/f_header1.jsp"/>

<title>合購日曆</title>
<link href='<%=request.getContextPath()%>/calendar/frontcss/jquery-ui.min.css' rel='stylesheet'>
<link href='<%=request.getContextPath()%>/calendar/frontcss/jquery-ui.theme.min.css' rel='stylesheet'>
<link href='<%=request.getContextPath()%>/calendar/css/fullcalendar.min.css' rel='stylesheet'>
<link href='<%=request.getContextPath()%>/calendar/css/fullcalendar.print.css' rel='stylesheet' media='print'>
<%
request.setAttribute("showBreadcrumb", new Object()); 
java.util.LinkedHashMap<String, String> map = new java.util.LinkedHashMap<String, String>();
map.put("會員中心","");
request.setAttribute("breadcrumbMap", map); 
%>
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>    	
	
	
<section>
	<div class="container">
		<div class="row">
<jsp:include page="/f/frag/f_memCenterMenu.jsp" />  
 			<div class="col-sm-9">
 			<h2 class="title text-center">合購日曆</h2> 
 				<div id='calendar'></div>
 			</div>
		</div>
	</div>
</section>


	
<jsp:include page="/f/frag/f_footer1.jsp"/>    		
<script src="<%=request.getContextPath()%>/calendar/frontjs/jquery-ui.custom.min.js"></script>

<script src="<%=request.getContextPath()%>/calendar/js/moment.min.js"></script>			
<script src="<%=request.getContextPath()%>/calendar/js/fullcalendar.min.js"></script>
<script src="<%=request.getContextPath()%>/calendar/js/lang-all.js"></script>

<script>
$(document).ready(function(){
	var currentLangCode = 'zh-tw';
	var time=new Date();
	
		
			$('#calendar').fullCalendar({
			theme: true,
			header: {
				left: 'prev,next',
				center: 'title',
				right: 'today'
			},
			aspectRatio: 1.7,
			defaultDate: time,
			lang: currentLangCode,
			buttonIcons: false, // show the prev/next text
			eventLimit: true, // allow "more" link when too many events
			events:"<%=request.getContextPath()%>/CalendarServlet.do",
			eventColor: '#d49768',
			timeFormat: 'H:mm' 	
			});
		

});

</script>

<jsp:include page="${contextPath}/f/frag/f_footer2.jsp"/>    				
