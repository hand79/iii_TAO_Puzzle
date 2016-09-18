<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tao.wtdReq.model.*"%>
<%
	WtdReqVO wtdReqVO = (WtdReqVO) request.getAttribute("wtdReqVO");
%>
<jsp:useBean id="wtdSvc" scope="page"
	class="com.tao.wtdReq.model.WtdReqService" />

<%
	String resPath = request.getContextPath() + "/b/res";
	pageContext.setAttribute("resPath", resPath);

	String contextPath = request.getContextPath() + "/wtdReq";
	pageContext.setAttribute("contextPath", contextPath);
%>

<jsp:include page="/b/frag/b_header1.jsp" flush="true" />

<title>�I���{���f�� | ��ݺ޲z�t��</title>

<jsp:include page="/b/frag/b_header2AndMenu.jsp" />
<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h3>�I�{�ӽмf��- update_wtdReq_input.jsp</h3>
			<a href="<%=request.getContextPath()%>/wtdReq/select_page.jsp">�^����</a>
			<%-- ���~��C --%>
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
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back/wtdReq/updateMoney.do">
				<div class="panel panel-default">
					<div class="panel-heading">
						<label>�I�{�ӽнs��:</label><p>${wtdReqVO.wtdreqno}</p>
					</div>
					<div class="panel-body">
						<label>�ӽЮɶ�:</label><p>${wtdReqVO.wtdreqt}</p>
						<label>�|���s��:</label><p>${wtdreqVO.memno}</p>
						<label>�I�{���B:</label><p>${wtdReqVO.wtdamnt}</p>
						<label>�Ȧ�b��:</label><p>${wtdReqVO.wtdac}</p>
						<label>�ӽЪ��A:</label><p>${wtdReqVO.reqsts}</p>
						<label>�f�֡G</label>
						<input type="radio" name="reqsts" value="COMPLETED">�T�{
						<input type="radio" name="reqsts" value="CANCELLED">����						
					</div>
					<!-- /.panel-body -->
					<div class="panel-footer">
						<input type="hidden" name="action" value="UpdateMoney_When_update">						
						<input type="hidden" name="requestURL" value="<%=request.getParameter("requestURL")%>"> 
						<input type="hidden" name="wtdreqno" value="${wtdReqVO.wtdreqno}">
						<input type="hidden" name="wtdreqt" value="${wtdReqVO.wtdreqt}">
						<input type="hidden" name="memno" value="${wtdReqVO.memno}"> 
						<input type="hidden" name="wtdamnt" value="${wtdReqVO.wtdamnt}">
						<input type="hidden" name="wtdac" value="${wtdReqVO.wtdac}">
						<input type="submit" value="�e�X">
					</div>
					<!-- /.panel-footer -->
				</div>
				<!-- /.panel -->
			</FORM>
		</div>
		<!-- /.col-lg-4 -->
	</div>
	<!-- /.row -->
</div>
<!-- /#page-wrapper -->
<jsp:include page="/b/frag/b_footer1.jsp" />
<jsp:include page="/f/frag/f_footer2.jsp" />