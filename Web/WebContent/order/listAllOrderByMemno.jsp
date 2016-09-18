<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%-- <%--**** LOGIN REDIRECT ****--%> --%>
<%-- <%@include file="/f/frag/f_login_redirect.file"%> --%>
<%-- <%--**** LOGIN REDIRECT ****--%> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tao.order.model.*"%>
<% 
	if(request.getAttribute("listAllOrderByMemno") ==null){
	response.sendRedirect(request.getContextPath() +
	"/select_page_wallet.jsp"); return; }
%>
<jsp:useBean id="listAllOrderByMemno" scope="request"
	type="java.util.List" />
<jsp:useBean id="orderSvc" scope="page"
	class="com.tao.order.model.OrderService" />
<jsp:useBean id="caseSvc" scope="page"
	class="com.tao.cases.model.CasesService" />
<%
	String resPath = request.getContextPath() + "/f/res";
	pageContext.setAttribute("resPath", resPath);

	String pageHome = request.getContextPath() + "/shop";
	pageContext.setAttribute("pageHome", pageHome);

		java.util.Collections.reverse(listAllOrderByMemno);
%>

<jsp:include page="/f/frag/f_header1.jsp" />
<title>�|���X�ʬ��� | Ź������</title>
<%
	request.setAttribute("showBreadcrumb", new Object()); 
java.util.LinkedHashMap<String, String> map = new java.util.LinkedHashMap<String, String>();
map.put("�|������","");
request.setAttribute("breadcrumbMap", map);
%>
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp" />
<head></head>
<section>
	<div class="container">
		<div class="row">
			<jsp:include page="/f/frag/f_memCenterMenu.jsp" />
			<div class="col-lg-9">
				<a href="<%=request.getContextPath()%>/select_page_wallet.jsp"><button
						type="button" class="btn btn-link">�^�q�l���]����</button></a>
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
			<div class="col-sm-9" style="height: 1000px">
				<h2 class="title text-center">
					�ѹά���
					<h2>
						<div class="search-zone" style="text-align: right;"></div>
						<!--class="search-zone"-->
						<div class="list-zone">
							<table class="table table-hover table-condensed table-striped"
								style="font-size: 0.5em; font-family: �L�n������;">
								<tr class="list-header info">
									<th></th>
									<th>�q��s��</th>
									<th>�q����B</th>
									<th>�X�ʮצW��</th>
									<th id="case-status">�q�檬�A</th>

									<th id='case-amount'>�U��ɶ�</th>
								</tr>
								<c:set var="caselist" value="${caseSvc.all}" />
								<c:forEach var="orderVO" items="${listAllOrderByMemno}">
									<c:set var="orderVO" value="${orderVO }" />
									<%
										com.tao.order.model.OrderVO ovo = (com.tao.order.model.OrderVO)pageContext.getAttribute("orderVO");
									%>
									<tr class="mainrow" id="case1">
										<td><a
											href="<%=request.getContextPath()%>/cases/cases.do?action=caseDetail&caseno=${orderVO.caseno}">
												<i class="fa fa-file-text-o"></i>
										</a></td>
										<td>${orderVO.ordno}</td>
										<td>${orderVO.price}</td>
										<td class="case-name"><c:forEach var="caseVO"
												items="${caselist}">
												<c:if test="${orderVO.caseno==caseVO.caseno}">
									${caseVO.shortenedTitle20}
									</c:if>
											</c:forEach></td>
										<td><%=OrderStatus.getStatusName(ovo.getStatus())%></td>
										<td>${orderVO.formatedOrdtime}</td>
									</tr>
								</c:forEach>
							</table>

						</div>
						<!--class="list-zone"-->
			</div>
			<!--class="col-sm-9"-->
		</div>
	</div>
</section>
<jsp:include page="/f/frag/f_footer1.jsp" />
<script src="<%=resPath%>/js/jquery.js"></script>
<script src="<%=resPath%>/js/js/bootstrap.min.js"></script>
<script src="<%=resPath%>/js/jquery.scrollUp.min.js"></script>
<script src="<%=resPath%>/js/price-range.js"></script>
<script src="<%=resPath%>/js/jquery.prettyPhoto.js"></script>
<script src="<%=resPath%>/js/main.js"></script>
<script src="<%=resPath%>/js/jquery.balloon.min.js"></script>
<script>
	$('.list-zone .subrow').hide();
	$('.list-zone .mainrow .case-name').css('cursor', 'pointer').click(
			function() {
				$('.list-zone .' + $(this).parent().attr('id')).toggle(300);
			});
	$('#status-bubble').hide();
	$('#case-status')
			.css('cursor', 'pointer')
			.balloon(
					{
						offsetX : -20,
						contents : '<p><b>9-�w�����G</b>�����q��w����</p><p><b>0-�ݵ��סG</b>�X�ʮש|���I��</p><p><b>1-�w���סG</b>�X�ʮ׺I��</p><p><b>2-�R��T�{�G</b></p><p><b>3-���T�{�G</b></p><p><b>4-�ȯɵo�͡G</b></p><p><b>5-�w�����G</b>������F</p><p><b>6-����T�{�G</b></p>'
					});
	$('#case-amount').css('cursor', 'pointer').balloon({
		offsetX : -20,
		contents : '<p>�q��ɶ�</p>'
	});
</script>
<jsp:include page="/f/frag/f_footer2.jsp" />