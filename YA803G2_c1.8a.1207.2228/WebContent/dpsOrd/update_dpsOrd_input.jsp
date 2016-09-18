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

<title>兌換現金審核 | 後端管理系統</title>

<jsp:include page="/b/frag/b_header2AndMenu.jsp" />

<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-6">
			<h3>儲值訂單確認</h3>
			<a href="<%=request.getContextPath()%>/dpsOrd/select_page_dps.jsp">回首頁</a>
			<%-- 錯誤表列 --%>
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
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back/dpsOrd/UpdateMoneyDps.do">
				<div class="panel panel-default">
					<div class="panel-heading">
						<label>儲值訂單編號:</label><p>${dpsOrdVO.dpsordno}</p>
					</div>
					<div class="panel-body">
						<label>訂單時間:</label><p>${dpsOrdVO.dpsordt}</p>
						<label>儲值金額:</label><p>${dpsOrdVO.dpsamnt}</p>
						<label>會員編號:</label><p>${dpsOrdVO.memno}</p>
						<label>儲值方式:</label><p>${dpsOrdVO.dpshow}</p>
						<label>訂單狀態：</label><p>${dpsOrdVO.ordsts}</p>
						<label>轉帳帳號:</label><p>${dpsOrdVO.atmac}</p>
						<label>確認:</label> 
						<input type="radio" name="ordsts" value="COMPLETED">完成
						<input type="radio" name="ordsts" value="CANCELLED">取消
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
						<input type="submit" value="送出">
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