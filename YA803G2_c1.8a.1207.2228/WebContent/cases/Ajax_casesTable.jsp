<%@ page language="java" contentType="text/html; charset=BIG5"
	pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.tao.cases.model.*"%>
<%@ page import="com.tao.jimmy.util.model.*"%>
<jsp:useBean id="locSvc" class="com.tao.jimmy.location.LocationService" />
<jsp:useBean id="tinyMemSvc" class="com.tao.jimmy.member.TinyMemberService" />
<jsp:useBean id="cpSvc" class="com.tao.cases.model.CaseProductService" />

<c:set var="tmemset" value="${tinyMemSvc.all}" />
<c:set var="locList" value="${locSvc.all}" />

<div class="table-responsive">
	<table class="table table-striped table-bordered table-hover"
		id="cases-table">
		<thead>
			<tr>
				<th>合購編號</th>
				<th>合購名稱</th>
				<th>發起會員 (編號)</th>
				<th>商品編號</th>
				<th>地區</th>
				<th>起始時間</th>
				<th>結束時間</th>
				<th>狀態</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="casesVO" items="${Ajax_casesTable}">
				<c:set var="casesVO" scope="page" value="${casesVO}" />
				<%
					CasesVO casesVO = (CasesVO) pageContext.getAttribute("casesVO");
				%>
				<tr>
					<td>${casesVO.caseno}</td>
					<c:if test="<%=casesVO.getStatus() == CasesVO.STATUS_CREATED %>">
					<td><a href="<%=request.getContextPath() + "/cases/cases.do?action=preview&caseno=" + casesVO.getCaseno() %>" target="_blank">${casesVO.title} </a></td>
					</c:if>
					<c:if test="<%=casesVO.getStatus() != CasesVO.STATUS_CREATED %>">
					<td><a href="<%=request.getContextPath() + "/cases/cases.do?action=caseDetail&caseno=" + casesVO.getCaseno() %>" target="_blank">${casesVO.title}</a></td>
					</c:if>
					<c:forEach var="tMemVO" items="${tmemset}">
						<c:if test="${tMemVO.memno == casesVO.memno}">
							<td><a class="pointer"><i class="fa fa-fw fa-user"></i>${tMemVO.memid}(${casesVO.memno})</a></td>
						</c:if>
					</c:forEach>
					<td>
						<%
							if (casesVO.getCpno() == null || casesVO.getCpno() == 0) {
									out.write("S" + casesVO.getSpno());
								} else {
									out.write("C" + casesVO.getCpno());
								}
						%>
					</td>
					<c:forEach var="locVO" items="${locList}">
						<c:if test="${locVO.locno == casesVO.locno}">
							<td class="center">${locVO.county} ${locVO.town}</td>
						</c:if>
					</c:forEach>
					<td class="center"><c:out value="${casesVO.formatedStime}" default="-"/></td>
					<td class="center"><c:out value="${casesVO.formatedEtime}" default="-"/></td>
					<td class="center"><%=CasesStatus.getStatusName(casesVO.getStatus())%></td>
					<td data-caseno="${casesVO.caseno}" style="font-weight: bolder;">
					<c:choose>
					<c:when test="<%=casesVO.getStatus() == CasesVO.STATUS_DELETED%>">
					<span style="cursor: default;margin-right: 18px;color:#AAA;"><i class="fa fa-fw fa-times"></i>取消</span>
					<span style="cursor: default;color:#AAA;"><i class="fa fa-fw fa-trash-o" ></i>刪除</span>
					</c:when>
					<c:when test="<%=casesVO.getStatus() == CasesVO.STATUS_CANCELED || casesVO.getStatus() == CasesVO.STATUS_COMPLETED || casesVO.getStatus() == CasesVO.STATUS_CREATED%>">
					<span style="cursor: default;margin-right: 18px;color:#AAA;"><i class="fa fa-fw fa-times"></i>取消</span>
					<a class="delete-case pointer"><i class="fa fa-fw fa-trash-o" ></i>刪除</a>
					</c:when>
					<c:otherwise>
					<a style="margin-right: 18px;"  class="cancel-case pointer"><i class="fa fa-fw fa-times"></i>取消</a>
					<a class="delete-case pointer"><i class="fa fa-fw fa-trash-o" ></i>刪除</a>
					</c:otherwise>
					</c:choose>
					</td>
				</tr>
			</c:forEach>
			<c:if test="${empty Ajax_casesTable }">

				<tr>
					<c:choose>
					<c:when test="${empty fromSearch}">
						<td>請輸入搜尋條件</td>
					</c:when>
					<c:otherwise>
						<td class="text-danger" style="font-weight: bold;">查無資料</td>
					</c:otherwise>
					</c:choose>
					<td></td>
					<td></td>
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
<script id="ajaxMsg">
var ajaxMsg = { "msg":"${invalidInput != null ? 1 : ((empty Ajax_casesTable) ? 2 : 0) }"};
</script>

