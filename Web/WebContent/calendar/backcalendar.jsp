<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="/b/frag/b_header1.jsp" />
<link href='<%=request.getContextPath()%>/calendar/css/jquery-ui.min.css' rel='stylesheet'>
<link href='<%=request.getContextPath()%>/calendar/css/fullcalendar.min.css' rel='stylesheet'>
<link href='<%=request.getContextPath()%>/calendar/css/fullcalendar.print.css' rel='stylesheet' media='print'>
<jsp:include page="/b/frag/b_header2AndMenu.jsp" />

<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">合購截止日程表</h1>
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->

	<div class="row">
		<div class="col-lg-12"style="">
<!-- 			<div class="panel panel-default"> -->
<!-- 				<div class="panel-heading"> -->
<!-- 					截止日程表 -->
<!-- 				</div> -->
<!-- 				/.panel-heading -->
<!-- 				<div class="panel-body"> -->
					<div id='calendar' style="width: 60%;"></div>
<!-- 				</div> -->
<!-- 			</div> -->
			<!-- /.panel-body -->
		</div>
		<!-- /.panel -->
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->



<jsp:include page="/b/frag/b_footer1.jsp" />
<script src="<%=request.getContextPath()%>/calendar/js/jquery-ui.custom.min.js"></script>
<script src="<%=request.getContextPath()%>/calendar/js/moment.min.js"></script>			
<script src="<%=request.getContextPath()%>/calendar/js/fullcalendar.min.js"></script>
<script src="<%=request.getContextPath()%>/calendar/js/lang-all.js"></script>

<script>
menuTrigger(2);
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
			defaultDate: time,
			lang: currentLangCode,
			buttonIcons: false, // show the prev/next text
			
			eventLimit: true, // allow "more" link when too many events
			events:"<%=request.getContextPath()%>/back/BackCalendarServlet.do",
			timeFormat: 'H(:mm)' 	
			});
		

});

</script>

<jsp:include page="/b/frag/b_footer2.jsp" />

