<%@ page import="com.tao.cathy.util.DateFormater"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.tao.news.model.*"%> 

<%
    String resPath = request.getContextPath() + "/b/res";
    pageContext.setAttribute("resPath", resPath);

    String pageHome = request.getContextPath() + "/news";
    pageContext.setAttribute("pageHome", pageHome);
%>

<% 
   List<NewsVO> listNews_ByCompositeQuery = (List<NewsVO>)request.getAttribute("listNews_ByCompositeQuery");
   if(listNews_ByCompositeQuery == null){
	   request.getRequestDispatcher("/news/index_back.jsp").forward(request, response);
	   return;
   }
	pageContext.setAttribute("listNews_ByCompositeQuery", listNews_ByCompositeQuery);
   
%>
<!-- head標頭放這裡 -->

<jsp:include page="/b/frag/b_header1.jsp"/>
<link href="${resPath}/jquery-ui/jquery-ui.min.css" 	rel="stylesheet" type="text/css">
<title>[饕飽地圖]最新消息管理</title>
<jsp:include page="/b/frag/b_header2AndMenu.jsp"/>



<script>
	$(function() {
		var opt = {
			//以下為日期選擇器部分
			dayNames : [ "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" ],
			dayNamesMin : [ "日", "一", "二", "三", "四", "五", "六" ],
			monthNames : [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月",
					"九月", "十月", "十一月", "十二月" ],
			monthNamesShort : [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月",
					"九月", "十月", "十一月", "十二月" ],
			prevText : "上月",
			nextText : "次月",
			weekHeader : "週",
			showMonthAfterYear : true,
			dateFormat : "yy-mm-dd",
		};
		$(".datepicker").datepicker(opt);
	});
</script>

</head>

		
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">最新消息管理</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			
			<!-- search畫面放這裡 -->
			<%@ include file="search_news_include_file.jsp"%>
			
			<!-- Content -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back/news/news.do">
								<span class="btn btn-success new" data-toggle="modal"
									data-target="#add_news">新增</span> 
									 <input type="hidden" name="action" value="insert">
							</FORM>
							<c:if test="${not empty errorMsgs}">
								<font color='red'>請修正以下錯誤:
									<c:forEach var="message" items="${errorMsgs}">
										<li>${message}</li>
									</c:forEach>
								</font>
							</c:if>
						</div>
						
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="table-responsive">
								<table class="table table-striped table-bordered table-hover"  id="dataTables-example">
									<thead>
										<tr>
											<th>編號</th>
											<th>標題</th>
											<th>內容</th>
											<th>發布時間</th>
											<th>修改</th>
											<th>刪除</th>

										</tr>
									</thead>
									<tbody id="trchange">
										<!-- 畫面筆數放這裡 -->
										 <%@ include file="page1_ByCompositeQuery.file"%>
										<c:forEach var="newsVO" items="${listNews_ByCompositeQuery}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
											<tr align='left' data-newsno="${newsVO.newsno }"  ${(newsVO.newsno==param.newsno) ? 'class=\"success\"':''} "> 										
												<td>${newsVO.newsno}</td>
												<td>${newsVO.title}</td>
												<td>${newsVO.text}</td>
												<td>${newsVO.formatPubtime}</td>
										
												<td><span class="btn btn-outline btn-warning edit"
													data-toggle="modal" data-target="#edit_news">編輯</span> 
													<input type="hidden" name="action" value="getOne_For_Update">
													<input type="hidden" name="newsno" value="${newsVO.newsno}"> 
												</td>
												<td><span class="btn btn-outline btn-danger remove"
													data-toggle="modal" data-target="#del_news">刪除</span></td>
											</tr>
										</c:forEach>
		
									</tbody>
								</table>
						<%@ include file="page2_ByCompositeQuery.file"%>
							</div>
							<!-- /.table-responsive -->
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
				<!-- /.col-lg-12 -->
					<%
						java.sql.Timestamp ptime= new java.sql.Timestamp(System.currentTimeMillis()) ;
					%>
					<!-- 新增燈箱放這裡 -->
						<div class="modal fade" id="add_news" role="dialog"
							aria-labelledby="AdModal" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-hidden="true">&times;</button>
										<h4 class="modal-title" id="AdModal" style="color: #5cb85c">
											<i class="fa fa-pencil fa-fw"></i>新增最新消息
										</h4>
									</div>
									<form role="form" METHOD="post" ACTION="<%=request.getContextPath()%>/back/news/news.do"  name="form1">
										<div class="modal-body">
											<div class="form-group">
												標題: <input type="TEXT" name="title" size="45" 
													value="" /></br>
												</br> 
												內容: <input type="TEXT" name="text" size="45"
													value="" /></br>
												</br>
												發布時間: <input class="cal-TextBox datepicker" onFocus="this.blur()"
													size="20" readonly type="text" id="" name="pubtime1"
													value="<%=DateFormater.formatTimestamp2(ptime)%>" /> 
													<select	style="width: auto; height: 26px;" name="pubtime2" >
													<%
														for (int i=0;i<24;i++) {	
															String s=String.format("%02d", i)+":00";
																out.println("<option value='"+s+"'>"+s+"</option>");
														}
													%>
													</select>
											</div>
										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
											<input type="submit" class="btn btn-success" value="新增" /> 
											<input type="hidden" name="action" value="insert">
											<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
										</div>
									</form>
								</div>
							</div>
						</div>
						
						<!-- 編輯燈箱放這裡 -->
							<div class="modal fade" id="edit_news" role="dialog"
							aria-labelledby="AdModal" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-hidden="true">&times;</button>
										<h4 class="modal-title" id="AdModal" style="color: #f0ad4e">
											<i class="fa fa-edit fa-fw"></i>編輯最新消息
										</h4>
									</div>
									<form role="form" METHOD="" ACTION="<%=request.getContextPath()%>/back/news/news.do" name="form1">
										<div class="modal-body">
											<div class="form-group">
												<div class="form-group">
													
													編號: <input type="TEXT" name="newsno" size="45" readonly  value="" /></br> 
													</br>
													標題: <input type="TEXT" name="title" size="45" value="" /></br> 
													</br>
													內容: <input type="TEXT" name="text" size="45" value="" /></br> 
													</br>
													發布時間: <input class="cal-TextBox datepicker" onFocus="this.blur()"
													size="20" readonly type="text" id="datepicker" name="pubtime1"
													value="" /> 
													<select	style="width: auto; height: 26px;" name="pubtime2">
													<%
														for (int i=0;i<24;i++) {	
															String s=String.format("%02d", i)+":00";
																out.println("<option value='"+s+"'>"+s+"</option>");
														}
													%>
													
													</select>
												</div>
											</div>
										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-default " data-dismiss="modal">取消</button>
											<input type="submit" class="btn btn-warning upd" value="修改" /> 
											<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
											
											<input type="hidden" name="action" value="queryupdate">
											<input type="hidden" name="newsno" id="upd-btn">
											
										</div>
									</form>
								</div>
							</div>
						</div>
					
						<!-- 刪除燈箱放這裡 -->
						<div class="modal fade" id="del_news" role="dialog"
							aria-labelledby="AdModal" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-hidden="true">&times;</button>
										<h4 class="modal-title" id="AdModal" style="color: #a94442;">
											<i class="fa fa-warning fa-fw"></i>提醒訊息
										</h4>
					
									</div>
									<form role="form" METHOD="post"
										ACTION="<%=request.getContextPath()%>/back/news/news.do" name="form1">
										<div class="modal-body">
											是否確定刪除編號<b><font color=red class="delcomsg"></font></b>紀錄?
										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
											<input type="submit" class="btn btn-danger" value="刪除"> 
											<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
											<input type="hidden" name="action" value="delete">
											<input type="hidden" name="newsno" id="del-btn">
										</div>
									</FORM>
								</div>
							</div>
						</div>
			</div>
		</div>
		<!-- /#page-wrapper -->

   
<jsp:include page="/b/frag/b_footer1.jsp"/>

<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>


<script>
	$(document).ready(function() {

	});
</script>

<script>
menuTrigger(0);
	$(function() {
		var opt = {
			//以下為日期選擇器部分
			dayNames : [ "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" ],
			dayNamesMin : [ "日", "一", "二", "三", "四", "五", "六" ],
			monthNames : [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月",
					"九月", "十月", "十一月", "十二月" ],
			monthNamesShort : [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月",
					"九月", "十月", "十一月", "十二月" ],
			prevText : "上月",
			nextText : "次月",
			weekHeader : "週",
			showMonthAfterYear : true,
			dateFormat : "yy-mm-dd",
		};
		$(".datepicker").datepicker(opt);
// 		$('#dataTables-example').dataTable({
// 			bLengthChange:false,
// 			bPaginate:false,
// 			bInfo:false,
// 			bFilter:false
// 		});
	});
</script>
<script>
	$('.remove').click(function() {
		var no = $(this).parent().parent().attr('data-newsno');
		$('#del-btn').val(no);
		$('.delcomsg').text(no);
		
	});
	$('.edit').click(function() {
		var tr = $(this).parent().parent();
		var no = tr.attr('data-newsno');
		$('#upd-btn').val(no);
		var title =tr.children().first().next();
		var text = title.next();
		var pubtime1 = text.next();		
		var pubtime2=pubtime1.text().split(" ");
		var pubtime3=pubtime2[1].split(":");		
		$('#edit_news input[name=newsno]').val(no);
		$('#edit_news input[name=title]').val(title.text());
		$('#edit_news input[name=text]').val(text.text());
		$('#edit_news input[name=pubtime1]').val(pubtime2[0]);
		$('#edit_news select')[0].selectedIndex = Number(pubtime3[0]);
		
	});
</script>
