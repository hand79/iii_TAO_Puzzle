<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<option value="-1">(再選擇子分類)</option>
<c:forEach var="subcatvo" items="${SubCategorySet}">
<option value="${subcatvo.subcatno}" ${(requestScope.SelectedSubCat.subcatno==subcatvo.subcatno)? 'selected':'' }>${subcatvo.subcatname}</option>
</c:forEach>
