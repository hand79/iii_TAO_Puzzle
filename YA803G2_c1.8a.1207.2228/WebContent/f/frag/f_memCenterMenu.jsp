<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>
<%@ page import="com.tao.member.model.*"%>
<%
if(true){
	MemberVO memvo = (MemberVO) session.getAttribute("CurrentUser");
%>
<div class="col-sm-3" style="height: 800px;" >
	<div class="left-sidebar">
		<a href="<%=request.getContextPath()%>/MemberCenter"><h2>會員中心</h2></a>
		<div class="panel-group category-products" id="accordian">
			<!--category-productsr-->
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a href="<%=request.getContextPath() %>/member/front/UpdateMemberInfo.jsp">編輯基本資料</a>
					</h4>
				</div>
			</div>
			<%
				if (memvo.getType() == 0) {
			%>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a href="<%=request.getContextPath() %>/order/order.do?action=viewOwnOrders">參加的合購</a>
					</h4>
				</div>
			</div>
			<%
				}
			%>
			<%
				if (memvo.getType() == 1) {
			%>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a href="<%=request.getContextPath() %>/shop/shop_member_center.jsp" id="enter_shop_member_center">商店管理</a>
					</h4>
				</div>
			</div>
			<%
				}
			%>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordian" href="#cases">
							<span class="badge pull-right"><i class="fa fa-plus"></i></span>
							發起的合購
						</a>
					</h4>
				</div>
				<div id="cases" class="panel-collapse collapse">
					<div class="panel-body">
						<ul>
							<% if (memvo.getType() == 0) {	%>
							<li><a href="<%=request.getContextPath() %>/caseproduct/caseProduct.do?action=viewOwnCPs">合購商品管理</a></li>
							<% } %>
							<li><a href="<%=request.getContextPath() %>/cases/cases.do?action=viewOwnCases">合購案管理</a></li>
							<li><a href="<%=request.getContextPath() %>/caseqa/caseQA.do?action=viewCaseQAs">回答問題</a></li>
							<li><a href="<%=request.getContextPath() %>/order/order.do?action=viewCaseOrders">訂單管理</a></li>

						</ul>
					</div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a href="<%=request.getContextPath() %>/select_page_wallet.jsp">電子錢包</a>
					</h4>
				</div>
			</div>
			<%
				if (memvo.getType() == 0) {
			%>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a href="<%=request.getContextPath() %>/wishList/wishList.do?action=listWishList">追蹤清單</a>
					</h4>
				</div>
			</div>
			<%
				}
			%>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a href="<%=request.getContextPath() %>/calendar/calendar.jsp">合購日曆</a>
					</h4>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a href="<%=request.getContextPath() %>/SurfMemberServlet.do?action=memberView&memno=${CurrentUser.memno}&from=memcenter">評價中心</a>
					</h4>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a href="<%=request.getContextPath() %>/memberrunning/memberrunning.jsp">積分獎勵</a>
					</h4>
				</div>
			</div>
		</div>
		<!--/category-products-->
	</div>
	<!--class="left-sidebar"-->
</div>
<!--class="col-sm-3"-->
<script>
var menuTrigger = function(idx){
	var list = $('div.panel-heading > h4.panel-title > a[data-toggle="collapse"]');
		$(list[idx]).trigger('click');
	};
</script>
<% }%>