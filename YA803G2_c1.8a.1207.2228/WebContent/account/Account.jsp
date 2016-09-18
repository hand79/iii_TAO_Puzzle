<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.tao.acc.model.*"%>
<jsp:include page="/b/frag/b_header1.jsp" />
<jsp:include page="/b/frag/b_header2AndMenu.jsp" />

<jsp:useBean id="accountSvc" scope="page" class="com.tao.acc.model.AccountService"/>
<jsp:useBean id="permissionSvc" scope="page" class="com.tao.acc.model.PermissionService"/>
<jsp:useBean id="PerListSvc" scope="page" class="com.tao.acc.model.PerListService"/>
<c:set var="list"  value="${accountSvc.all}"/>

<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<c:if test="${not empty errorMsgs}">
				<font color='red'>請修正以下錯誤:
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li>${message}</li>
						</c:forEach>
					</ul>
				</font>
			</c:if>
			<h1 class="page-header">後端帳號管理</h1>
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					後端帳號列表
					<button class="btn btn-success btn-sm"
						style="float: right; margin-top: -5px;" data-toggle="modal"
						data-target="#addform">新增帳號</button>
				</div>
				<!-- /.panel-heading -->
				<div class="panel-body">
					<div class="table-responsive">

						<table class="table table-striped table-bordered table-hover"
							id="dataTables-example">
							<thead>
								<tr>
									<th>帳號</th>
									<th>暱稱</th>
									<th>E-mail</th>
									<th>權限</th>
									<th>功能</th>
								</tr>
							</thead>

<%@ include file="/account/page1.file" %>
							<c:forEach var="account" items="${accountSvc.all}"
								begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>"
								varStatus="s">
								<tr
									class="gradeA center ${(account.acc==param.acc) ? 'success':''}">
									<form id="form_${s.index}" method="post" action="<%=request.getContextPath()%>/back/Account.do">

										<td>${account.acc}</td>
										<td><input type="text" name="nick"	value="${account.nick}"></td>
										<td><input type="text" name="email"	value="${account.email}"></td>
										<td class="center">
										<c:forEach var="permission"	items="${permissionSvc.all}">
											<input type="checkbox" name="perno"		value="${permission.perno}"
											<c:forEach var="perlist" items="${PerListSvc.getAllOneAccountPermissionInfo(account.acc)}">
											      <c:if test="${perlist.perno==permission.perno}">
														 checked
												  </c:if>
											</c:forEach>>${permission.perdesc}
										</c:forEach> 
											<input id="updateaDelete_${s.index}" type="hidden"	name="action" value=""> 
											<input type="hidden"	name="acc" value="${account.acc}"></td>
										<td class="center" data-url="${account.acc}">
										<input type="button" id="updatebt_${s.index}" class="btn btn-outline btn-success"value="修改">
										<input type="button" id="deletebt_${s.index}" class="btn btn-outline btn-danger"value="刪除" data-toggle="modal" data-target="#delform">
										<input type="button" id="restbt_${s.index}" class="btn btn-outline btn-warning"value="密碼重設" data-toggle="modal" data-target="#restform">
											<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">	<!--送出本網頁的路徑給Controller--> 
											<input type="hidden" name="whichPage" value="<%=whichPage%>">
										<!--送出當前是第幾頁給Controller--></td>
									</form>
								</tr>
							</c:forEach>
						</table>
<%@ include file="page2.file"%>
					</div>
				</div>
			</div>
			<!-- /.panel-body -->
		</div>
		<!-- /.panel -->
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<div class="modal fade" id="addform" role="dialog"
	aria-labelledby="AddModal" aria-hidden="true">
	<div class="modal-dialog" style="width: 400px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="AddModal">新增帳號</h4>
			</div>
			<form role="form" method="post" ACTION="<%=request.getContextPath()%>/back/Account.do" name="insertform">
				<div class="modal-body">
					<div class="form-group">
						<label>帳號名稱:</label> <input class="form-control" name="acc"
							placeholder="請輸入帳號名稱"><br> <label> 暱稱:</label> <input
							class="form-control" name="nick" placeholder="請輸入暱稱"><br>
						<label> E-mail:</label> <input class="form-control" name="email"
							placeholder="請輸入E-mail"> <label> 權限設置:</label><p>
						<c:forEach var="per" items="${permissionSvc.all}">
							<label class="checkbox-inline"><input type="checkbox"
								name="perno" value="${per.perno}">${per.perdesc}</label>
						</c:forEach>
						</p>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
					<button type="submit" class="btn btn-success">新增</button>
					<input type="hidden" name="action" value="insert"> <input
						type="hidden" name="requestURL"
						value="<%=request.getServletPath()%>">
					<!--送出本網頁的路徑給Controller-->
					<input type="hidden" name="whichPage" value="<%=whichPage%>">
					<!--送出當前是第幾頁給Controller-->
				</div>
			</form>
		</div>
	</div>
</div>
<div class="modal fade" id="delform" role="dialog"
	aria-labelledby="delModal" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="delModal">刪除帳號</h4>
			</div>
			<div class="modal-body">確定要刪除嗎</div>
			<div class="modal-footer">
				<input type="hidden" id="mainId" value="" />
				<button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
				<button id="bt1" type="button" class="btn btn-danger"	onclick="sendDeleteForm()">刪除</button>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="restform" role="dialog"
	aria-labelledby="restModal" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="restModal">密碼重設</h4>
			</div>
			<div class="modal-body">確定要<span id="restacc" style="color:red;"></span>重設密碼嗎?</div>
			<div class="modal-footer">
			<form id="restpwform" method="post" action="<%=request.getContextPath()%>/back/Account.do">
				<input type="hidden" id="restId" name="acc" value="" />
				<input type="hidden" name="action" value="restaccpw" >
				<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">	<!--送出本網頁的路徑給Controller--> 
				<input type="hidden" name="whichPage" value="<%=whichPage%>">				<!--送出當前是第幾頁給Controller-->
				<button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
				<button id="rest" type="button" class="btn btn-warning"	">重設</button>
			</form>
			</div>
		</div>
	</div>
</div>
<jsp:include page="/b/frag/b_footer1.jsp" />
<jsp:include page="/b/frag/b_footer2.jsp" />

<script>
	$(document).ready(function() {
		$('#dataTables-example').dataTable({
			bLengthChange : false,
			bPaginate : false,
			bInfo : false,
			bFilter : false
		});

		$("input[id^='update']").click(function() {
			var idStr = $(this).attr("id");
			var idAry = idStr.split("_");
			$("#updateaDelete_" + idAry[1]).val("update");
			var mainId = "#form_" + idAry[1];
			$(mainId).submit();
		});
		$("input[id^='delete']").click(function() {
			$("#delform").show();
			var idStr = $(this).attr("id");
			var idAry = idStr.split("_");
			$("#updateaDelete_" + idAry[1]).val("delete");
			var mainId = "#form_" + idAry[1];
			$("#mainId").val(mainId);
		});
		
		$("input[id^='rest']").click(function() {
			var restId=$(this).parent().attr('data-url');
			$("#restId").val(restId);
			$("#restacc").text(restId);		
			
		});
		$("#rest").click(function(){
			$("#restpwform").submit();
		});

	});
	function sendDeleteForm() {
		var mainId = $("#mainId").val();
		$(mainId).submit();
	}
</script>
