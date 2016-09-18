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
					<p>${caseRepVO.crepno}</p>
					<label>���|�H�s��</label>
					<p>${caseRepVO.repno}</p>
					<label>�����|�D�ʽs��</label>
					<p>${caseRepVO.susno}</p>
					<label>�����|�X�ʮ׽s��</label>
					<p>${caseRepVO.repcaseno}</p>
					<label>���|��]</label>
					<p>${caseRepVO.creprsn}</p>
					<div class="panel-footer">
						<input type="submit" class="btn btn-warning" value="�U�[���X�ʮ�">
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