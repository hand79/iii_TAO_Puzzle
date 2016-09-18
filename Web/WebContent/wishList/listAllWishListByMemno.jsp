<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%--**** LOGIN REDIRECT ****--%>
<%@include file="/f/frag/f_login_redirect.file" %>
<%--**** LOGIN REDIRECT ****--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tao.wishList.model.*"%>
<%@ page import="com.tao.cases.model.*"%>

<jsp:useBean id="listWishList_ByCompositeQuery" scope="request"
	type="java.util.List" />
<jsp:useBean id="wishSvc" scope="page"
	class="com.tao.wishList.model.WishListService" />
<jsp:useBean id="caseSvc" scope="page" class="com.tao.cases.model.CasesService" />

<%
	String resPath = request.getContextPath() + "/f/res/";
	pageContext.setAttribute("resPath", resPath);

	String contextPath = request.getContextPath() + "/wishList";
	pageContext.setAttribute("contextPath", contextPath);
%>

<jsp:include page="/f/frag/f_header1.jsp" flush="true" />
<title>�l�ܲM�� | Ź������</title>
<style>
	input.btn-primary{
		margin-top: 0px;
	}
</style>
<%
request.setAttribute("showBreadcrumb", new Object()); 
java.util.LinkedHashMap<String, String> map = new java.util.LinkedHashMap<String, String>();
map.put("�|������","");
request.setAttribute("breadcrumbMap", map); 
%>
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp" />
<section>
	<div class="container">
		<div class="row">
			<jsp:include page="/f/frag/f_memCenterMenu.jsp" />
			<div class="col-lg-9" style="color: red;">
				<c:if test="${not empty errorMsgs}">
					�Эץ��H�U���~:
						<ul>
							<c:forEach var="message" items="${errorMsgs}">
								<li>${message}</li>
							</c:forEach>
						</ul>
					
				</c:if>
			</div>
			<!-- /.col-lg-12 -->
			<div class="col-sm-9" style="height: 1000px">
				<h2 class="title text-center">�l�ܲM��</h2>
				<div class="search-zone" style="text-align: right;"></div>
				<!--class="search-zone"-->
				<div class="list-zone">
					<table class="table table-hover table-condensed table-striped">
						<tr class="list-header info">
							<th></th>
							<th>�X�ʦW��</th>
							<th id="case-status">���A</th>
							<th id='case-amount'>�I��ɶ�</th>
							<th></th>
						</tr>
						<c:forEach var="wishListVO"	items="${listWishList_ByCompositeQuery}">
							<%-- prepare data --%>
							<c:set var="wishListVO" value="${wishListVO}"/>
							<% WishListVO wlvo = (WishListVO) pageContext.getAttribute("wishListVO"); %>
							<% CasesVO caseVO = caseSvc.getByPrimaryKey(wlvo.getCaseno()); %>
							<c:set var="caseVO" value="<%=caseVO%>"/>
							<%-- prepare data --%>
							<tr>
								<td><a href="<%=request.getContextPath()%>/cases/cases.do?action=caseDetail&caseno=${caseVO.caseno}">
								<i class="fa fa-bookmark-o"></i></a></td>
								<td class="case-name">${caseVO.title}</td>
								<td><%=CasesStatus.getDisplayStatusName(caseVO.getStatus()) %></td>
								<td>${caseVO.formatedEtime}</td>
								<td>
									<form method="post"	action="<%=request.getContextPath()%>/wishList/wishList.do">
										<input type="hidden" name="wlno" value="${wishListVO.wlno}">
										<input type="hidden" name="action" value="delete"> 
										<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
										<input type="submit" class="btn btn-primary" value="�����l��" style="margin-top: 0px;">
									</form>
								</td>
							</tr>
						</c:forEach>
					</table>

				</div>
				<!--class="list-zone"-->
			</div>
			<!--class="col-sm-9"-->
		</div>
		<!-- /.row -->
	</div>
	<!-- /.container -->
</section>
<jsp:include page="/f/frag/f_footer1.jsp" />
<script src="${resPath}js/jquery.balloon.min.js"></script> 
<script>
// 	$('.list-zone .subrow').hide();
// 	$('.list-zone .mainrow .case-name').css('cursor', 'pointer').click(
// 			function() {
// 				$('.list-zone .' + $(this).parent().attr('id')).toggle(300);
// 			});
// 	$('#status-bubble').hide();
// 	$('#case-status')
// 			.css('cursor', 'pointer')
// 			.balloon(
// 					{
// 						offsetX : -20,
// 						contents : '<p><b>0-���W�[�G</b>�|���W�[</p><p><b>1-�Ҷ����G</b>�w�W�[���|���F����e</p><p><b>2-�w���ΡG</b>�w�W�[���B�F����e</p><p><b>3-�w�����G</b>�w�������|�������Ҧ�����</p><p><b>4-�w�����G</b>�w�����Ҧ��y�{�B�U�[</p><p><b>5-�w�����G</b>�ɶ��������F���e�ΨϥΪ̤�ʨ���</p>'
// 					});
// 	$('#case-amount').css('cursor', 'pointer').balloon({
// 		offsetX : -100,
// 		contents : '<p>�I��ɶ�</p>'
// 	});
</script>
<jsp:include page="/f/frag/f_footer2.jsp" />