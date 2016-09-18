<%@page import="com.tao.member.model.MemberVO"%>
<%@page import="com.tao.member.model.MemberService"%>
<%@page import="com.tao.order.model.*"%>
<%@page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<%--**** LOGIN REDIRECT ****--%>
<%@include file="/f/frag/f_login_redirect.file" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--**** LOGIN REDIRECT ****--%>
<jsp:include page="/f/frag/f_header1.jsp"/>
<%
request.setAttribute("showBreadcrumb", new Object()); 
java.util.LinkedHashMap<String, String> map = new java.util.LinkedHashMap<String, String>();
map.put("會員中心","");
request.setAttribute("breadcrumbMap", map); 
%>
<title>會員中心 | 饕飽拼圖</title>
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>
<jsp:useBean id="orderSvc"  class="com.tao.order.model.OrderService"/>
<jsp:useBean id="casesSvc"  class="com.tao.cases.model.CasesService"/>

<c:set var="CurrentUser" value="<%=new MemberService().findByPrimaryKeyNoPic(((MemberVO)session.getAttribute(\"CurrentUser\")).getMemno()) %>" scope="session"/>
<c:set var="memberVO" value="${CurrentUser }" scope="page"/>

<section>
<div class="container">
<div class="row">
<jsp:include page="/f/frag/f_memCenterMenu.jsp"/>
<div class="col-sm-9">
	<h2 class="text-center title">會員中心首頁</h2>
	<div class="panel panel-default">
				<div class="panel-body">
					<div class="row">
 						<div  class="col-sm-2">
 							<img src="${pageContext.request.contextPath}/MemberInfoImage.do?memno=${memberVO.memno}" style="width: 120px; height: 120px;"/>
 						</div>
						<div class="col-sm-10">				
							<p><i class="fa fa-user fa-fw "></i>帳號：<a href="${pageContext.request.contextPath}//SurfMemberServlet.do?action=memberView&memno=${memberVO.memno}">${memberVO.memid}</a></p>
							<p><i class="fa fa-user fa-fw "></i>會員編號：${memberVO.memno}</p>
							<p><i class="fa fa-usd fa-fw"></i>帳戶餘額：${memberVO.money}</p>
							<p><i class="fa fa-usd fa-fw"></i>預扣款：${memberVO.withhold}</p>
						</div> 
					</div>
				
				</div></div> 
				<div class="row" style="margin-top: 60px;">
			<div class="col-sm-6">
				<h2 class="text-center title">近期參加的合購</h2>
				<table class="table table-hover table-condensed table-striped" style="font-size: 0.5em; font-family:微軟正黑體;">
					<tr> 
						<th>合購案名稱 </th>
						<th>金額</th>
						<th>下單時間 </th>
					</tr>
					<c:set var="buyerList" value="${orderSvc.findByBuyerNotCanceled(memberVO.memno)}" />
					<% List<OrderVO> buyerList=(List<OrderVO>)pageContext.getAttribute("buyerList"); 
						Collections.reverse(buyerList);
					%>
					<c:forEach var="BuyerVO" items="${buyerList}" begin="0" end="4" >
						<tr> 
						<th>${casesSvc.getByPrimaryKey(BuyerVO.caseno).getShortenedTitle()}</th>
						<th>$ ${BuyerVO.price}</th>
						<th><i class="fa fa-fw fa-clock-o"></i>${BuyerVO.getFormatedOrdtime()}</th>
						</tr>
					</c:forEach>
					
				</table>
			</div>	
			
			<div class="col-sm-6">
				<h2 class="text-center title">近期發起的合購</h2>
				<table class="table table-hover table-condensed table-striped" style="font-size: 0.5em; font-family:微軟正黑體;">
					<tr>
						<th>合購案名稱</th>
						<th>目前/門檻/上限</th>
						<th>截止時間</th>
						
					</tr>
					<c:set var="creatorList" value="${casesSvc.getByCreator(memberVO.memno,false,false)}" />
					<% List<OrderVO> creatorList=new ArrayList<OrderVO>((Set<OrderVO>)pageContext.getAttribute("creatorList")); 
						Collections.reverse(creatorList);
					%>
					
					<c:forEach var="CreatorVO" items="${creatorList}" begin="0" end="4">
						<tr>
							<th>${CreatorVO.shortenedTitle}</th>
							<th class="text-center">${orderSvc.getTotalOrderQty(CreatorVO.caseno)} / ${CreatorVO.minqty } / ${CreatorVO.maxqty }</th>
							<th><i class="fa fa-fw fa-clock-o"></i>${CreatorVO.formatedEtime}</th>
						</tr>
					</c:forEach>
					</table>		
			
				</div>	
			</div>
				
	
</div>

</div>
</div>
</section>
<jsp:include page="/f/frag/f_footer1.jsp"/>
<jsp:include page="/f/frag/f_footer2.jsp"/>
