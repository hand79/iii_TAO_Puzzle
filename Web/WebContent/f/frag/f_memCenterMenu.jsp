<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>
<%@ page import="com.tao.member.model.*"%>
<%
if(true){
	MemberVO memvo = (MemberVO) session.getAttribute("CurrentUser");
%>
<div class="col-sm-3" style="height: 800px;" >
	<div class="left-sidebar">
		<a href="<%=request.getContextPath()%>/MemberCenter"><h2>�|������</h2></a>
		<div class="panel-group category-products" id="accordian">
			<!--category-productsr-->
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a href="<%=request.getContextPath() %>/member/front/UpdateMemberInfo.jsp">�s��򥻸��</a>
					</h4>
				</div>
			</div>
			<%
				if (memvo.getType() == 0) {
			%>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a href="<%=request.getContextPath() %>/order/order.do?action=viewOwnOrders">�ѥ[���X��</a>
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
						<a href="<%=request.getContextPath() %>/shop/shop_member_center.jsp" id="enter_shop_member_center">�ө��޲z</a>
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
							�o�_���X��
						</a>
					</h4>
				</div>
				<div id="cases" class="panel-collapse collapse">
					<div class="panel-body">
						<ul>
							<% if (memvo.getType() == 0) {	%>
							<li><a href="<%=request.getContextPath() %>/caseproduct/caseProduct.do?action=viewOwnCPs">�X�ʰӫ~�޲z</a></li>
							<% } %>
							<li><a href="<%=request.getContextPath() %>/cases/cases.do?action=viewOwnCases">�X�ʮ׺޲z</a></li>
							<li><a href="<%=request.getContextPath() %>/caseqa/caseQA.do?action=viewCaseQAs">�^�����D</a></li>
							<li><a href="<%=request.getContextPath() %>/order/order.do?action=viewCaseOrders">�q��޲z</a></li>

						</ul>
					</div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a href="<%=request.getContextPath() %>/select_page_wallet.jsp">�q�l���]</a>
					</h4>
				</div>
			</div>
			<%
				if (memvo.getType() == 0) {
			%>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a href="<%=request.getContextPath() %>/wishList/wishList.do?action=listWishList">�l�ܲM��</a>
					</h4>
				</div>
			</div>
			<%
				}
			%>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a href="<%=request.getContextPath() %>/calendar/calendar.jsp">�X�ʤ��</a>
					</h4>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a href="<%=request.getContextPath() %>/SurfMemberServlet.do?action=memberView&memno=${CurrentUser.memno}&from=memcenter">��������</a>
					</h4>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a href="<%=request.getContextPath() %>/memberrunning/memberrunning.jsp">�n�����y</a>
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