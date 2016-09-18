<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tao.jimmy.location.*" %>

	
<c:forEach var="locationVO" items="${towns}" varStatus="sts">
	<c:if test="${sts.first}">
		<option value="">(½Ð¿ï¾Ü)</option>
	</c:if>
	<option value="${locationVO.locno}" ${requestScope.locvo.locno == locationVO.locno ? 'selected':''}>${locationVO.town}</option>
</c:forEach>
<c:if test="${empty towns}">
	<option value="" >-</option>
</c:if>