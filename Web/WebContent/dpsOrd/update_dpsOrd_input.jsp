<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tao.dpsOrd.model.*"%>
<%
	DpsOrdVO dpsOrdVO = (DpsOrdVO) request.getAttribute("dpsOrdVO");
%>

<%
	String resPath = request.getContextPath() + "/b/res";
	pageContext.setAttribute("resPath", resPath);

	String contextPath = request.getContextPath() + "/dpsOrd";
	pageContext.setAttribute("contextPath", contextPath);
%>

<jsp:include page="/b/frag/b_header1.jsp" flush="true" />

<title>�I���{���f�� | ��ݺ޲z�t��</title>

<jsp:include page="/b/frag/b_header2AndMenu.jsp" />

<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-6">
			<h3>�x�ȭq��T�{</h3>
			<a href="<%=request.getContextPath()%>/dpsOrd/select_page_dps.jsp">�^����</a>
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
		<div class="col-lg-12">
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back/dpsOrd/UpdateMoneyDps.do">
				<div class="panel panel-default">
					<div class="panel-heading">
						<label>�x�ȭq��s��:</label><p>${dpsOrdVO.dpsordno}</p>
					</div>
					<div class="panel-body">
						<label>�q��ɶ�:</label><p>${dpsOrdVO.dpsordt}</p>
						<label>�x�Ȫ��B:</label><p>${dpsOrdVO.dpsamnt}</p>
						<label>�|���s��:</label><p>${dpsOrdVO.memno}</p>
						<label>�x�Ȥ覡:</label><p>${dpsOrdVO.dpshow}</p>
						<label>�q�檬�A�G</label><p>${dpsOrdVO.ordsts}</p>
						<label>��b�b��:</label><p>${dpsOrdVO.atmac}</p>
						<label>�T�{:</label> 
						<input type="radio" name="ordsts" value="COMPLETED">����
						<input type="radio" name="ordsts" value="CANCELLED">����
					</div>
					<!-- /.panel-body -->
					<div class="panel-footer">
						<input type="hidden" name="action" value="UpdateMoney_When_UpdateDps"> 
						<input type="hidden" name="requestURL" value="<%=request.getParameter("requestURL")%>">
						<input type="hidden" name="dpsordno" value="${dpsOrdVO.dpsordno}"/>
						<input type="hidden" name="dpsordt" value="${dpsOrdVO.dpsordt}"/>
						<input type="hidden" name="dpsamnt" value="${dpsOrdVO.dpsamnt}"/>
						<input type="hidden" name="memno" value="${dpsOrdVO.memno}"/>
						<input type="hidden" name="dpshow"value="${dpsOrdVO.dpshow}"> 
						<input type="hidden" name="atmac" value="${dpsOrdVO.atmac}"/>
						<input type="submit" value="�e�X">
					</div>
					<!-- /.panel-footer -->
				</div>
				<!-- panel -->
			</FORM>
		</div>
		<!-- /.col-lg-6 -->
	</div>
	<!-- /.row -->
</div>
<!-- /#page-wrapper -->
<jsp:include page="/b/frag/b_footer1.jsp" />
<jsp:include page="/f/frag/f_footer2.jsp" />