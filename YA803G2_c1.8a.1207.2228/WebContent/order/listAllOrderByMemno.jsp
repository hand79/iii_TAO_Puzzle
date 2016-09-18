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
<title>會員合購紀錄 | 饕飽拼圖</title>
<%
	request.setAttribute("showBreadcrumb", new Object()); 
java.util.LinkedHashMap<String, String> map = new java.util.LinkedHashMap<String, String>();
map.put("會員中心","");
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
						type="button" class="btn btn-link">回電子錢包首頁</button></a>
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
			<div class="col-sm-9" style="height: 1000px">
				<h2 class="title text-center">
					參團紀錄
					<h2>
						<div class="search-zone" style="text-align: right;"></div>
						<!--class="search-zone"-->
						<div class="list-zone">
							<table class="table table-hover table-condensed table-striped"
								style="font-size: 0.5em; font-family: 微軟正黑體;">
								<tr class="list-header info">
									<th></th>
									<th>訂單編號</th>
									<th>訂單金額</th>
									<th>合購案名稱</th>
									<th id="case-status">訂單狀態</th>

									<th id='case-amount'>下單時間</th>
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
						contents : '<p><b>9-已取消：</b>此筆訂單已取消</p><p><b>0-待結案：</b>合購案尚未截止</p><p><b>1-已結案：</b>合購案截止</p><p><b>2-買方確認：</b></p><p><b>3-賣方確認：</b></p><p><b>4-糾紛發生：</b></p><p><b>5-已完成：</b>交易完了</p><p><b>6-雙方確認：</b></p>'
					});
	$('#case-amount').css('cursor', 'pointer').balloon({
		offsetX : -20,
		contents : '<p>訂單時間</p>'
	});
</script>
<jsp:include page="/f/frag/f_footer2.jsp" />