<%-- <div data-source="${param.action}" style="display:none"></div> --%>
<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.tao.cases.model.*" %>
<% pageContext.setAttribute("pageHome", request.getContextPath() + "/cases"); %>
<jsp:useBean id="ordSvc" class="com.tao.order.model.OrderService" scope="request"/>
<c:if test="${not empty totalPageNums && totalPageNums > 1}">
	<div id="changePagePanel" data-pageNum="${pageNum}" style="height: 40px; padding: 10px;">
		<c:if test="${pageNum > 1}">
			<a id="prev-page" style="cursor: pointer; text-decoration: underline;">上一頁</a> 
		</c:if>
		<c:if test="${pageNum < totalPageNums}">
			<a class="pull-right" id="next-page" style="cursor: pointer; text-decoration: underline;">下一頁</a>
		</c:if>
	</div>
</c:if>
	<table class="table table-hover table-striped" style="font-family:微軟正黑體;" id="ajaxZone">
		<%-- HEADING --%>
		<tr class="list-header info">
			<th>合購編號</th>
			<th>名稱</th>
			<th class="pointer underline" id="case-status">狀態</th>
			<th class="pointer underline" id='case-amount'>數量</th>
			<th>檢視(預覽) / 編輯</th>
			<th>上架</th>
			<th>操作</th>
		</tr>
		<%-- IF CASELIST IS EMPTY --%>
		<c:if test="${empty caseList}">
		<tr>
			<td colspan="7" class="text-center"><i>尚未建立合購</i></td>
		</tr>
		</c:if>
		<%-- DO COMPLICATED STUFF --%>
		<c:if test="${not empty caseList}">
		<c:forEach var="cvo" items="${caseList}" varStatus="sts">
		<c:set var="cvo" value="${cvo}"/>
		<% CasesVO cvo = (CasesVO) pageContext.getAttribute("cvo"); %>
		<c:set var="currentQty" value="<%=ordSvc.getTotalOrderQty(cvo.getCaseno()) %>"/>
		
		<tr id="case${sts.count}" data-caseno="${cvo.caseno}">
			<td>${cvo.caseno}</td>
			<td class="viewCaseDetail pointer">${cvo.shortenedTitle20}</td>
			<td><%=CasesStatus.getDisplayStatusName(cvo.getStatus()) %></td>
			<td>${currentQty}/${cvo.minqty}/${cvo.maxqty}</td>
			<c:choose>
			<c:when test="<%=cvo.getStatus() == CasesVO.STATUS_CREATED %>">
				<td><a href="${pageHome}/cases.do?action=preview&caseno=${cvo.caseno}"><i class="fa fa-eye"></i> 預覽</a> / <a href="${pageHome}/cases.do?action=edit&caseno=${cvo.caseno}">編輯 <i class="fa fa-edit"></i></a></td>
				<td><a class="pointer open-case">上架</a></td>
				<td><a class="pointer delete-case"><i class="fa fa-fw fa-times"></i>刪除 </a></td>
			</c:when>
			<c:when test="<%=cvo.getStatus() == CasesVO.STATUS_PUBLIC || cvo.getStatus() == CasesVO.STATUS_PRIVATE%>">
				<td><a href="${pageHome}/cases.do?action=caseDetail&caseno=${cvo.caseno}" target="_blank"><i class="fa fa-eye"></i> 檢視</a> / -</td>
				<td><a class="pointer cancel-case">取消</a></td>
				<c:if test="${currentQty >= cvo.minqty}">
				<td><a class="pointer over-case">提早結團</a></td>
				</c:if>
				<c:if test="${currentQty < cvo.minqty}">
				<td>-</td>
				</c:if>
			</c:when>
			<c:when test="<%=cvo.getStatus() == CasesVO.STATUS_OVER %>">
				<td><a href="${pageHome}/cases.do?action=caseDetail&caseno=${cvo.caseno}" target="_blank"><i class="fa fa-eye"></i> 檢視</a> / -</td>
				<td><a class="pointer cancel-case">取消</a></td>
				<td>-</td>
			</c:when>
			<c:when test="<%=cvo.getStatus() == CasesVO.STATUS_COMPLETED %>">
				<td><a href="${pageHome}/cases.do?action=caseDetail&caseno=${cvo.caseno}" target="_blank"><i class="fa fa-eye"></i> 檢視</a> / -</td>
				<td>-</td>
				<td>-</td>
			</c:when>
			<c:when test="<%=cvo.getStatus() == CasesVO.STATUS_CANCELED %>">
				<td><a href="${pageHome}/cases.do?action=caseDetail&caseno=${cvo.caseno}" target="_blank"><i class="fa fa-eye"></i> 檢視</a> / -</td>
				<td>-</td>
				<td><a class="pointer hide-case"><i class="fa fa-fw fa-eye-slash"></i>隱藏</a></td>
			</c:when>
			</c:choose>
		</tr>
		</c:forEach>
		</c:if>
	</table>
	<div style="margin-top: 40px;">
		<input type="checkbox" name="hideCancel" id="hide-all" ${param.hide == 'hidden' ? 'selected':''}> 隱藏所有已取消的合購
		<a class="pull-right" href="<%=request.getContextPath() %>/cases/cases.do?action=add"><button class="btn btn-success" id="create-case"> 建立合購案 +</button></a>
	</div>