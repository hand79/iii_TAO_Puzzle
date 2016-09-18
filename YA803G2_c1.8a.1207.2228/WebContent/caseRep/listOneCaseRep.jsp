<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tao.caseRep.model.*"%>
<%
	String resPath = request.getContextPath() + "/b/res";
	pageContext.setAttribute("resPath", resPath);

	String pageShopRep = request.getContextPath() + "/shopRep";
	pageContext.setAttribute("pageShopRep", pageShopRep);

	CaseRepVO caseRepVO = (CaseRepVO) request.getAttribute("caseRepVO");
%>
<jsp:include page="/b/frag/b_header1.jsp" flush="true" />
<title>檢舉處理 | 後端管理系統</title>
<jsp:include page="/b/frag/b_header2AndMenu.jsp" />
<div id="page-wrapper">

	<div class="row">
		<div class="col-lg-12">
			<h3 class="page-header">單筆商店檢舉紀錄</h3>
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
		<div class="col-lg-6">
			<div class="panel panel-default">
				<div class="panel-heading">
					<a href="<%=request.getContextPath()%>/select_page_report.jsp"><button
							type="button" class="btn btn-link">回檢舉紀錄首頁</button></a>
				</div>
				<div class="panel-body">
					<label>檢舉案件編號</label>
					<p>${caseRepVO.crepno}</p>
					<label>檢舉人編號</label>
					<p>${caseRepVO.repno}</p>
					<label>受檢舉主購編號</label>
					<p>${caseRepVO.susno}</p>
					<label>受檢舉合購案編號</label>
					<p>${caseRepVO.repcaseno}</p>
					<label>檢舉原因</label>
					<p>${caseRepVO.creprsn}</p>
					<div class="panel-footer">
						<input type="submit" class="btn btn-warning" value="下架此合購案">
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="/b/frag/b_footer1.jsp" />
<script>
	$(document).ready(function() {
		$('#dataTables-example').dataTable();
	});
</script>
<jsp:include page="/b/frag/b_footer2.jsp" />