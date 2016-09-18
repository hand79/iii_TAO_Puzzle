<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>
<%@ page import="com.tao.category.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pageHome"
	value="<%=request.getContextPath() + \"/caseproduct\"%>" />

<table class="table table-hover table-striped"
	style="font-family: �L�n������;">
	<!-- TABLE HEADER -->
	<tr class="list-header info">
		<th>�ӫ~�s��</th>
		<th>�W��</th>
		<th>���</th>
		<th>����</th>
		<th>�ާ@</th>
		<th>�R��</th>
	</tr>
	<!-- TABLE BODY -->
	<c:choose>
		<c:when test="${not empty cplist}">
			<c:set var="subcatlist"
				value="<%=(new SubCategoryService()).getAll()%>" />
			<c:set var="catlist" value="<%=(new CategoryService()).getAll()%>" />
			<c:forEach var="cpvo" items="${cplist}">
				<tr>
					<td>C${cpvo.cpno }</td>
					<td>${cpvo.name }</td>
					<td>${cpvo.unitprice}</td>
					<c:forEach var="subcatvo" items="${subcatlist}">
						<c:if test="${subcatvo.subcatno == cpvo.subcatno }">
							<c:forEach var="catvo" items="${catlist }">
								<c:if test="${catvo.catno == subcatvo.catno }">
									<td>${catvo.catname}> ${subcatvo.subcatname }</td>
									<td><a href="${pageHome}/caseProduct.do?action=preview&cpno=${cpvo.cpno}"><i class="fa fa-eye"></i> �w��</a> / <a href="${pageHome}/caseProduct.do?action=viewOrEdit&cpno=${cpvo.cpno}" data-lock="${cpvo.lockflag}" class="editLink" data-cpno="${cpvo.cpno}">�s��&nbsp;<i class="fa fa-edit"></i></a></td>
									<c:choose>
									<c:when test="${cpvo.lockflag == 0 }">
									<td><a href="" class="delLink" data-cpno="${cpvo.cpno}">�R��</a></td>
									</c:when>
									<c:otherwise>
									<td><i class="fa fa-times" style="color:#BBB;"></i>�w��w</td>
									</c:otherwise>
									</c:choose>
								</c:if>
							</c:forEach>
						</c:if>
					</c:forEach>
				</tr>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<tr>
				<td colspan="6" style="text-align: center;"><i>�|���إߦX�ʰӫ~</i></td>
			</tr>
		</c:otherwise>
	</c:choose>
</table>
<!-- END OF TABLE -->

<div style="text-align: right; margin-top: 40px;">
	<a class="btn btn-success" href="${pageHome}/caseProduct.do?action=add">�s�W�ӫ~&nbsp;<i
		class="fa fa-plus"></i></a>
</div>