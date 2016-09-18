<%--**** LOGIN REDIRECT ****--%>
<%@include file="/f/frag/f_login_redirect.file" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tao.dpsOrd.model.*"%>

<jsp:useBean id="listDpsOrd_ByCompositeQuery" scope="request" type="java.util.List"/>
<jsp:useBean id="dpsSvc" scope="page" class="com.tao.dpsOrd.model.DpsOrdService"/>

<%
	String resPath = request.getContextPath() + "/f/res";
	pageContext.setAttribute("resPath", resPath);

	String contextPath = request.getContextPath() + "/dpsOrd";
	pageContext.setAttribute("contextPath", contextPath);
%>

<jsp:include page="/f/frag/f_header1.jsp" flush="true" />

<title>電子錢包 | 饕飽拼圖</title>

<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp" />




<section>
<div class="container">
	<div class="row">
	<jsp:include page="/f/frag/f_memCenterMenu.jsp"/>
		<div class="col-lg-9">
			<h2 class="page-header">儲值紀錄列表</h2>
			<a href="<%=request.getContextPath()%>/select_page_wallet.jsp"><button type="button"
					class="btn btn-link">回電子錢包首頁</button></a>
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
		<!-- /.col-lg-9 -->
		<div class="col-sm-9" style="height: 1000px">
		<h2 class="title text-center">
					儲值紀錄
					<h2>
			<div class="search-zone" style="text-align: right;"></div>
						<!--class="search-zone"-->
				
				
					<div class="list-zone">
						<table class="table table-hover table-condensed table-striped"
								style="font-size: 0.5em; font-family: 微軟正黑體;">
								<tr class="list-header info">
									<th>編號</th>
									<th>儲值金額</th>
									<th>申請時間</th>
									<th>付款方式</th>
									<th>申請狀態</th>
									<th>轉帳帳號</th>
								</tr>
								<c:forEach var="dpsOrdVO" items="${listDpsOrd_ByCompositeQuery}">
									<tr class="odd gradeX">
										<td>${dpsOrdVO.dpsordno}</td>
										<td>${dpsOrdVO.dpsamnt}</td>
										<td>${dpsOrdVO.dpsordt}</td>
										<td>${dpsOrdVO.dpshow}</td>
										<td>${dpsOrdVO.ordsts}</td>
										<td>${dpsOrdVO.atmac}</td>
									</tr>
								</c:forEach>
							
						</table>
					</div>
					<!-- /.table-responsive -->
		</div>
	</div>
	<!-- /.row -->
</div>
<!-- /.container -->
</section>

<jsp:include page="/f/frag/f_footer1.jsp" />



<script>
	$(document).ready(function() {
		$('#dataTables-example').dataTable();
	});
</script>
<jsp:include page="/f/frag/f_footer2.jsp" />