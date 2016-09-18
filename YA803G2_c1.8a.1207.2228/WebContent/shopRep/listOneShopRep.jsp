<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tao.shopRep.model.*"%>
<%
	String resPath = request.getContextPath() + "/b/res";
	pageContext.setAttribute("resPath", resPath);

	String pageShopRep = request.getContextPath() + "/shopRep";
	pageContext.setAttribute("pageShopRep", pageShopRep);

	ShopRepVO shopRepVO = (ShopRepVO) request.getAttribute("shopRepVO");
%>

<jsp:include page="/b/frag/b_header1.jsp" flush="true" />
<title>���|�B�z | ��ݺ޲z�t��</title>
<jsp:include page="/b/frag/b_header2AndMenu.jsp" />
<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h3 class="page-header">�浧�ө����|����</h3>
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
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
	<div class="row">
		<div class="col-lg-6">
			<div class="panel panel-default">
				<div class="panel-heading">
					<a href="<%=request.getContextPath()%>/select_page_report.jsp"><button
					type="button" class="btn btn-link">�^���|��������</button></a>
				</div>
				<div class="panel-body">
					<label>���|�ץ�s��</label>
					<p>${shopRepVO.srepno}</p>
					<label>���|�H�s��</label>
					<p>${shopRepVO.repno}</p>
					<label>�����|�ө��s��</label>
					<p>${shopRepVO.shopno}</p>
					<label>���|��]</label>
					<p>${shopRepVO.sreprsn}</p>
				</div>
				<div class="panel-footer">
					<input type="submit" class="btn btn-warning" value="�U�[���ө�">
				</div>
			</div>
			<!-- /.panel -->
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
</div>
<!-- /#page-wrapper -->
<jsp:include page="/b/frag/b_footer1.jsp" />
<script>
	$(document).ready(function() {
		$('#dataTables-example').dataTable();
	});
</script>
<jsp:include page="/b/frag/b_footer2.jsp" />