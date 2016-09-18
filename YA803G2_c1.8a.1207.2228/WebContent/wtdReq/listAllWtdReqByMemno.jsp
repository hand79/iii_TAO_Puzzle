<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tao.wtdReq.model.*"%>

<jsp:useBean id="listWtdReq_ByCompositeQuery" scope="request"
	type="java.util.List" />
<jsp:useBean id="wtdSvc" scope="page"
	class="com.tao.wtdReq.model.WtdReqService" />

<%
	String resPath = request.getContextPath() + "/f/res";
	pageContext.setAttribute("resPath", resPath);

	String contextPath = request.getContextPath() + "/wtdReq";
	pageContext.setAttribute("contextPath", contextPath);
%>

<jsp:include page="/f/frag/f_header1.jsp" flush="true" />

<title>�|���I�{���� | Ź������</title>

<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp" />

<section>
	<div class="container">
		<div class="row">
		<jsp:include page="/f/frag/f_memCenterMenu.jsp"/>
			<div class="col-lg-9">
				<h2 class="page-header">�I�{�����C��</h2>
				<a href="<%=request.getContextPath()%>/select_page_wallet.jsp"><button
						type="button" class="btn btn-link">�^�q�l���]����</button></a>
				<c:if test="${not empty errorMsgs}">
					<font color='red'>�Эץ��H�U���~:
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
					�I�{�ӽЬ���
					<h2>
					<div class="search-zone" style="text-align: right;"></div>
						<!--class="search-zone"-->
				<div class="list-zone">
							<table class="table table-hover table-condensed table-striped"
								style="font-size: 0.5em; font-family: �L�n������;">
								
									<tr class="list-header info">
										<th>�I�{�����s��</th>
										<th>�I�{�ӽЮɶ�</th>
										<th>�I�{���B</th>
										<th>�Ȧ�b��</th>
										<th>�ӽЪ��A</th>
									</tr>
									<c:forEach var="wtdReqVO"
										items="${listWtdReq_ByCompositeQuery}">
										<tr class="mainrow" id="case1">
											<td>${wtdReqVO.wtdreqno}</td>
											<td>${wtdReqVO.wtdreqt}</td>
											<td>${wtdReqVO.wtdamnt}</td>
											<td>${wtdReqVO.wtdac}</td>
											<td>${wtdReqVO.reqsts}</td>
										</tr>
									</c:forEach>
							</table>
				</div><!--class="list-zone"-->
			</div>
		</div>
		<!-- /.row -->
	</div>
	<!-- /.container -->
</section>

<script>
	$(document).ready(function() {
		$('#dataTables-example').dataTable();
	});
</script>
<jsp:include page="/f/frag/f_footer2.jsp" />