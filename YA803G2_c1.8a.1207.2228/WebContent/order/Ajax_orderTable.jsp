<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>
<%@ page import="java.util.*"%>
<%@ page import="com.tao.order.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="tinyMemSvc" class="com.tao.jimmy.member.TinyMemberService" />
<c:set var="tinyMemAll" value="${tinyMemSvc.all}" />

<div class="table-responsive">
	<table class="table table-striped table-bordered table-hover"
		id="order-table">
		<thead>
			<tr>
				<th>訂單編號</th>
				<th>下單會員 (編號)</th>
				<th>主購會員 (編號)</th>
				<th>合購案編號</th>
				<th>訂單金額</th>
				<th>訂單時間</th>
				<th>狀態</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="orderVO" items="${Ajax_orderTable}" varStatus="sts">
				<c:set var="orderVO" scope="page" value="${orderVO}" />
				<%
					OrderVO orderVO = (OrderVO) pageContext.getAttribute("orderVO");
				%>
				<tr class="gradeA">
					<td><a class="ordno pointer">${orderVO.ordno}</a></td>
					<td><a class="pointer"><c:forEach var="tMemVO" items="${tinyMemAll}"><c:if test="${tMemVO.memno == orderVO.bmemno}"><i class="fa fa-fw fa-user"></i>${tMemVO.memid} (${tMemVO.memno})</c:if></c:forEach></a></td>
					<td><a class="pointer"><c:forEach var="tMemVO" items="${tinyMemAll}"><c:if test="${tMemVO.memno == orderVO.cmemno}"><i class="fa fa-fw fa-user"></i>${tMemVO.memid} (${tMemVO.memno})</c:if></c:forEach></a></td>
					<td>${orderVO.caseno}</td>
					<td class="center">${orderVO.price}</td>
					<td class="center">${orderVO.formatedOrdtime}</td>
					<c:if test="<%=orderVO.getStatus() == OrderDAO.STATUS_CONFLICT %>">
					<td class="center"><a class="conflic pointer" style="font-weight: bolder;"><%=OrderStatus.getStatusName(orderVO.getStatus())%> <i class="fa fa-retweet"></i></a></td>
					</c:if>
					<c:if test="<%=orderVO.getStatus() != OrderDAO.STATUS_CONFLICT %>">
					<td class="center"><%=OrderStatus.getStatusName(orderVO.getStatus())%></td>
					</c:if>
				</tr>
			</c:forEach>
			<c:if test="${empty Ajax_orderTable }">
				
				<tr>
				<c:if test="${empty fromSearch}">
				<td>請輸入搜尋條件</td>
				</c:if>
				<c:if test="${not empty fromSearch}">
					<td class="text-danger" style="font-weight: bold;">查無資料</td>
				</c:if>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				</tr>
			</c:if>
		</tbody>
	</table>
</div>
<script id="ajaxMsg" style="display: none;">
var ajaxMsg = { "msg":"${invalidInput != null ? 1 : ((empty Ajax_orderTable) ? 2 : 0) }"};
</script>
