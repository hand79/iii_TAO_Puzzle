<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tao.wtdReq.model.*"%>

<jsp:useBean id="listWtdReq_ByCompositeQuery" scope="request"
	type="java.util.List" />
<jsp:useBean id="wtdSvc" scope="page"
	class="com.tao.wtdReq.model.WtdReqService" />
<%
	String resPath = request.getContextPath() + "/b/res";
	pageContext.setAttribute("resPath", resPath);

	String contextPath = request.getContextPath() + "/wtdReq";
	pageContext.setAttribute("contextPath", contextPath);
%>

<jsp:include page="/b/frag/b_header1.jsp" flush="true" />

<title>兌換現金審核 | 後端管理系統</title>

<jsp:include page="/b/frag/b_header2AndMenu.jsp" />
<div id="page-wrapper">

	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">兌現紀錄列表</h1>
			<c:if test="${not empty errorMsgs}">
				<font color='red'>請修正以下錯誤:
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li>${message}</li>
						</c:forEach>
					</ul>
				</font>
			</c:if>
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->

	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 style="font-family: 微軟正黑體; margin: 0px">
						<b>搜尋</b>
					</h4>
				</div>
				<div class="panel-body">
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/back/wtdReq/wtdReq.do"
						name="form1" role="form">

						<div class="col-sm-2">
							<div class="form-group">
								<label>兌現申請編號</label> <input type="text" class="form-control"
									name="wtdreqno" value="">
							</div>
						</div>

						<div class="col-sm-2">
							<div class="form-group">
								<label>會員編號</label> <input type="text" class="form-control"
									name="memno" value="">
							</div>
						</div>

						<div class="col-sm-2">
							<div class="form-group">
								<label>金額</label> <input type="text" class="form-control"
									name="wtdamnt" value="">
							</div>
						</div>

						<div class="col-sm-2">
							<div class="form-group">
								<label>狀態</label> <select name="reqsts" class="form-control">
									<option value="">
									<option value="WAITING">待審核
									<option value="CANCELLED">取消
									<option value="COMPLETED">完成
								</select>
							</div>
						</div>

						<div class="col-sm-2">
							<div class="form-group">
								<label>&nbsp;</label>
								<button type="reset"
									class="form-control btn btn-outline btn-warning">清除</button>
							</div>
						</div>

						<div class="col-sm-2">
							<div class="form-group">
								<label>&nbsp;</label>
								<button class="form-control btn btn-outline btn-success">
									<i class="fa fa-search"></i> 搜尋
								</button>
								<input type="hidden" name="action"
									value="listWtdReq_ByCompositeQuery_B">
							</div>

						</div>

					</FORM>
				</div>

			</div>
			<!--/.panel .panel-info-->
		</div>
	</div>


	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">搜尋結果</div>
				<!-- /.panel-heading -->
				<div class="panel-body">
					<div class="table-responsive">
						<table class="table table-striped table-bordered table-hover"
							id="dataTables-example">
							<thead>
								<tr>
									<th>編號</th>
									<th>申請時間</th>
									<th>會員編號</th>
									<th>兌現金額</th>
									<th>銀行帳號</th>
									<th>申請狀態</th>
									<th>確認</th>
									<th>刪除</th>
								</tr>
							<thead>
							<tbody id="trchange">

								<c:if test="${empty listWtdReq_ByCompositeQuery}">
									<tr>
										<td colspan="7">無資料!</td>
									</tr>
								</c:if>

								<c:forEach var="wtdReqVO" items="${listWtdReq_ByCompositeQuery}">
									<tr align='left' class="odd gradeX">
										<td>${wtdReqVO.wtdreqno}</td>
										<td>${wtdReqVO.wtdreqt}</td>
										<td>${wtdReqVO.memno}</td>
										<td>${wtdReqVO.wtdamnt}</td>
										<td>${wtdReqVO.wtdac}</td>
										<td>${wtdReqVO.reqsts}</td>
										<td><FORM METHOD="post"
												ACTION="<%=request.getContextPath()%>/back/wtdReq/wtdReq.do">
												<c:if
													test="${wtdReqVO.reqsts == 'CANCELLED' or wtdReqVO.reqsts == 'COMPLETED'}">
													<input type="submit" class="btn btn-default disabled"
														value="已確認">
												</c:if>
												<c:if test="${wtdReqVO.reqsts == 'WAITING'}">
													<input type="submit" class="btn btn-success" value="確認">
												</c:if>
												<input type="hidden" name="wtdreqno"
													value="${wtdReqVO.wtdreqno}"> <input type="hidden"
													name="requestURL" value="<%=request.getServletPath()%>">
												<input type="hidden" name="action" value="getOne_For_Update">
											</FORM></td>
										<td><FORM METHOD="post"
												ACTION="<%=request.getContextPath()%>/back/wtdReq/wtdReq.do">
												<input type="submit" class="btn btn-danger" value="刪除">
												<input type="hidden" name="wtdreqno"
													value="${wtdReqVO.wtdreqno}"> <input type="hidden"
													name="requestURL" value="<%=request.getServletPath()%>">
												<input type="hidden" name="action" value="delete">
											</FORM></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<!-- /.panel panel-default -->
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
</div>
<!-- /#page-wrapper -->

<jsp:include page="/b/frag/b_footer1.jsp" />
<script>
	$(document).ready(function() {
		$('#dataTables-example').dataTable(

		{
			"language" : {
				"paginate" : {
					"next" : "下一頁",
					"last" : "最末頁",
					"previous" : "上一頁",
					"first" : "第一頁",
					"emptyTable" : "無資料"

				},
				"info" : "第  _PAGE_ 頁，共  _PAGES_ 頁  ( _TOTAL_ 筆資料 )",
				"lengthMenu" : "每頁顯示  _MENU_ 筆",
				"search" : "搜尋結果過濾: ",
				"infoEmpty" : "共 0 筆符合",
				"infoFiltered" : " (由  _MAX_ 筆過濾而來)",
				"zeroRecords" : "無符合之資料"
			}
		}

		);
	});
</script>
<jsp:include page="/b/frag/b_footer2.jsp" />