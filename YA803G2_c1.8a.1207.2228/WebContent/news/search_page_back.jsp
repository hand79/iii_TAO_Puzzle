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
<!-- head���Y��o�� -->

<jsp:include page="/b/frag/b_header1.jsp"/>
<link href="${resPath}/jquery-ui/jquery-ui.min.css" 	rel="stylesheet" type="text/css">
<title>[Ź���a��]�̷s�����޲z</title>
<jsp:include page="/b/frag/b_header2AndMenu.jsp"/>



<script>
	$(function() {
		var opt = {
			//�H�U�������ܾ�����
			dayNames : [ "�P����", "�P���@", "�P���G", "�P���T", "�P���|", "�P����", "�P����" ],
			dayNamesMin : [ "��", "�@", "�G", "�T", "�|", "��", "��" ],
			monthNames : [ "�@��", "�G��", "�T��", "�|��", "����", "����", "�C��", "�K��",
					"�E��", "�Q��", "�Q�@��", "�Q�G��" ],
			monthNamesShort : [ "�@��", "�G��", "�T��", "�|��", "����", "����", "�C��", "�K��",
					"�E��", "�Q��", "�Q�@��", "�Q�G��" ],
			prevText : "�W��",
			nextText : "����",
			weekHeader : "�g",
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
					<h1 class="page-header">�̷s�����޲z</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			
			<!-- search�e����o�� -->
			<%@ include file="search_news_include_file.jsp"%>
			
			<!-- Content -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back/news/news.do">
								<span class="btn btn-success new" data-toggle="modal"
									data-target="#add_news">�s�W</span> 
									 <input type="hidden" name="action" value="insert">
							</FORM>
							<c:if test="${not empty errorMsgs}">
								<font color='red'>�Эץ��H�U���~:
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
											<th>�s��</th>
											<th>���D</th>
											<th>���e</th>
											<th>�o���ɶ�</th>
											<th>�ק�</th>
											<th>�R��</th>

										</tr>
									</thead>
									<tbody id="trchange">
										<!-- �e�����Ʃ�o�� -->
										 <%@ include file="page1_ByCompositeQuery.file"%>
										<c:forEach var="newsVO" items="${listNews_ByCompositeQuery}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
											<tr align='left' data-newsno="${newsVO.newsno }"  ${(newsVO.newsno==param.newsno) ? 'class=\"success\"':''} "> 										
												<td>${newsVO.newsno}</td>
												<td>${newsVO.title}</td>
												<td>${newsVO.text}</td>
												<td>${newsVO.formatPubtime}</td>
										
												<td><span class="btn btn-outline btn-warning edit"
													data-toggle="modal" data-target="#edit_news">�s��</span> 
													<input type="hidden" name="action" value="getOne_For_Update">
													<input type="hidden" name="newsno" value="${newsVO.newsno}"> 
												</td>
												<td><span class="btn btn-outline btn-danger remove"
													data-toggle="modal" data-target="#del_news">�R��</span></td>
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
					<!-- �s�W�O�c��o�� -->
						<div class="modal fade" id="add_news" role="dialog"
							aria-labelledby="AdModal" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-hidden="true">&times;</button>
										<h4 class="modal-title" id="AdModal" style="color: #5cb85c">
											<i class="fa fa-pencil fa-fw"></i>�s�W�̷s����
										</h4>
									</div>
									<form role="form" METHOD="post" ACTION="<%=request.getContextPath()%>/back/news/news.do"  name="form1">
										<div class="modal-body">
											<div class="form-group">
												���D: <input type="TEXT" name="title" size="45" 
													value="" /></br>
												</br> 
												���e: <input type="TEXT" name="text" size="45"
													value="" /></br>
												</br>
												�o���ɶ�: <input class="cal-TextBox datepicker" onFocus="this.blur()"
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
											<button type="button" class="btn btn-default" data-dismiss="modal">����</button>
											<input type="submit" class="btn btn-success" value="�s�W" /> 
											<input type="hidden" name="action" value="insert">
											<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller-->
										</div>
									</form>
								</div>
							</div>
						</div>
						
						<!-- �s��O�c��o�� -->
							<div class="modal fade" id="edit_news" role="dialog"
							aria-labelledby="AdModal" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-hidden="true">&times;</button>
										<h4 class="modal-title" id="AdModal" style="color: #f0ad4e">
											<i class="fa fa-edit fa-fw"></i>�s��̷s����
										</h4>
									</div>
									<form role="form" METHOD="" ACTION="<%=request.getContextPath()%>/back/news/news.do" name="form1">
										<div class="modal-body">
											<div class="form-group">
												<div class="form-group">
													
													�s��: <input type="TEXT" name="newsno" size="45" readonly  value="" /></br> 
													</br>
													���D: <input type="TEXT" name="title" size="45" value="" /></br> 
													</br>
													���e: <input type="TEXT" name="text" size="45" value="" /></br> 
													</br>
													�o���ɶ�: <input class="cal-TextBox datepicker" onFocus="this.blur()"
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
											<button type="button" class="btn btn-default " data-dismiss="modal">����</button>
											<input type="submit" class="btn btn-warning upd" value="�ק�" /> 
											<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller-->
											
											<input type="hidden" name="action" value="queryupdate">
											<input type="hidden" name="newsno" id="upd-btn">
											
										</div>
									</form>
								</div>
							</div>
						</div>
					
						<!-- �R���O�c��o�� -->
						<div class="modal fade" id="del_news" role="dialog"
							aria-labelledby="AdModal" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-hidden="true">&times;</button>
										<h4 class="modal-title" id="AdModal" style="color: #a94442;">
											<i class="fa fa-warning fa-fw"></i>�����T��
										</h4>
					
									</div>
									<form role="form" METHOD="post"
										ACTION="<%=request.getContextPath()%>/back/news/news.do" name="form1">
										<div class="modal-body">
											�O�_�T�w�R���s��<b><font color=red class="delcomsg"></font></b>����?
										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-default" data-dismiss="modal">����</button>
											<input type="submit" class="btn btn-danger" value="�R��"> 
											<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller-->
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
			//�H�U�������ܾ�����
			dayNames : [ "�P����", "�P���@", "�P���G", "�P���T", "�P���|", "�P����", "�P����" ],
			dayNamesMin : [ "��", "�@", "�G", "�T", "�|", "��", "��" ],
			monthNames : [ "�@��", "�G��", "�T��", "�|��", "����", "����", "�C��", "�K��",
					"�E��", "�Q��", "�Q�@��", "�Q�G��" ],
			monthNamesShort : [ "�@��", "�G��", "�T��", "�|��", "����", "����", "�C��", "�K��",
					"�E��", "�Q��", "�Q�@��", "�Q�G��" ],
			prevText : "�W��",
			nextText : "����",
			weekHeader : "�g",
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
