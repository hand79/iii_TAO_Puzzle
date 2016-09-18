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
			<a id="prev-page" style="cursor: pointer; text-decoration: underline;">�W�@��</a> 
		</c:if>
		<c:if test="${pageNum < totalPageNums}">
			<a class="pull-right" id="next-page" style="cursor: pointer; text-decoration: underline;">�U�@��</a>
		</c:if>
	</div>
</c:if>
	<table class="table table-hover table-striped" style="font-family:�L�n������;" id="ajaxZone">
		<%-- HEADING --%>
		<tr class="list-header info">
			<th>�X�ʽs��</th>
			<th>�W��</th>
			<th class="pointer underline" id="case-status">���A</th>
			<th class="pointer underline" id='case-amount'>�ƶq</th>
			<th>�˵�(�w��) / �s��</th>
			<th>�W�[</th>
			<th>�ާ@</th>
		</tr>
		<%-- IF CASELIST IS EMPTY --%>
		<c:if test="${empty caseList}">
		<tr>
			<td colspan="7" class="text-center"><i>�|���إߦX��</i></td>
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
				<td><a href="${pageHome}/cases.do?action=preview&caseno=${cvo.caseno}"><i class="fa fa-eye"></i> �w��</a> / <a href="${pageHome}/cases.do?action=edit&caseno=${cvo.caseno}">�s�� <i class="fa fa-edit"></i></a></td>
				<td><a class="pointer open-case">�W�[</a></td>
				<td><a class="pointer delete-case"><i class="fa fa-fw fa-times"></i>�R�� </a></td>
			</c:when>
			<c:when test="<%=cvo.getStatus() == CasesVO.STATUS_PUBLIC || cvo.getStatus() == CasesVO.STATUS_PRIVATE%>">
				<td><a href="${pageHome}/cases.do?action=caseDetail&caseno=${cvo.caseno}" target="_blank"><i class="fa fa-eye"></i> �˵�</a> / -</td>
				<td><a class="pointer cancel-case">����</a></td>
				<c:if test="${currentQty >= cvo.minqty}">
				<td><a class="pointer over-case">��������</a></td>
				</c:if>
				<c:if test="${currentQty < cvo.minqty}">
				<td>-</td>
				</c:if>
			</c:when>
			<c:when test="<%=cvo.getStatus() == CasesVO.STATUS_OVER %>">
				<td><a href="${pageHome}/cases.do?action=caseDetail&caseno=${cvo.caseno}" target="_blank"><i class="fa fa-eye"></i> �˵�</a> / -</td>
				<td><a class="pointer cancel-case">����</a></td>
				<td>-</td>
			</c:when>
			<c:when test="<%=cvo.getStatus() == CasesVO.STATUS_COMPLETED %>">
				<td><a href="${pageHome}/cases.do?action=caseDetail&caseno=${cvo.caseno}" target="_blank"><i class="fa fa-eye"></i> �˵�</a> / -</td>
				<td>-</td>
				<td>-</td>
			</c:when>
			<c:when test="<%=cvo.getStatus() == CasesVO.STATUS_CANCELED %>">
				<td><a href="${pageHome}/cases.do?action=caseDetail&caseno=${cvo.caseno}" target="_blank"><i class="fa fa-eye"></i> �˵�</a> / -</td>
				<td>-</td>
				<td><a class="pointer hide-case"><i class="fa fa-fw fa-eye-slash"></i>����</a></td>
			</c:when>
			</c:choose>
		</tr>
		</c:forEach>
		</c:if>
	</table>
	<div style="margin-top: 40px;">
		<input type="checkbox" name="hideCancel" id="hide-all" ${param.hide == 'hidden' ? 'selected':''}> ���éҦ��w�������X��
		<a class="pull-right" href="<%=request.getContextPath() %>/cases/cases.do?action=add"><button class="btn btn-success" id="create-case"> �إߦX�ʮ� +</button></a>
	</div>