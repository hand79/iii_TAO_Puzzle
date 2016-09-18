<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%--**** LOGIN REDIRECT ****--%>
<%@include file="/f/frag/f_login_redirect.file" %>
<%--**** LOGIN REDIRECT ****--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageHome" value="<%=request.getContextPath() + \"/caseproduct\" %>"/>

<%-- INCLUDE HEADER1 --%>
<jsp:include page="/f/frag/f_header1.jsp"/>
<%-- INCLUDE HEADER1 --%>
<link rel="stylesheet" href="${pageHome}/css/owncp.css"/>
<%
request.setAttribute("showBreadcrumb", new Object()); 
java.util.LinkedHashMap<String, String> map = new java.util.LinkedHashMap<String, String>();
map.put("會員中心","");
request.setAttribute("breadcrumbMap", map); 
%>

<%-- INCLUDE HEADER2 --%>
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>
<%-- INCLUDE HEADER2 --%>	
	<section>
		<div class="container">
			<div class="row">
				<%-- INCLUDE MENU --%>
				<jsp:include page="/f/frag/f_memCenterMenu.jsp" /> 
				<div class="col-sm-9" >
					<h2 class="title text-center">合購商品清單</h2>
	    		
					<div id="list-zone">
<jsp:include page="Ajax_caseProductList.jsp"/>
					</div><!--/#list-zone"-->
				</div><!--class="col-sm-9"-->
			</div>
		</div>
	</section>
	<!-- MODAL FOR DELETE -->
	<div class="modal fade " id="delCP"  tabindex="-1" role="dialog" aria-labelledby="add" aria-hidden="true">
		<div class="modal-dialog">
		    <div class="modal-content">
				 <div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="glyphicon glyphicon-remove"></i></button>
					<h4 class="modal-title" id="add">
					   刪除合購商品
					</h4>
				 </div>
				 <div class="modal-body" >
				 	<p id="delMsg"></p>
					<p id="dia"></p>
				 </div>
				 <div class="modal-footer">
					<button class="btn btn-danger" style="margin-top: 0px;" id="sendDelRequest" data-delno="">
					   確定
					</button>
					<button class="btn btn-success" data-dismiss="modal">
						取消
					</button>
					
				 </div>
		    </div><!-- /.modal-content -->
		</div>
	</div><!-- /.modal -->
	<div class="modal fade " id="notify-lock"  tabindex="-1" role="dialog" aria-labelledby="lock" aria-hidden="true">
		<div class="modal-dialog">
		    <div class="modal-content">
				 <div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="glyphicon glyphicon-remove"></i></button>
					<h4 class="modal-title" id="lock">
					  商品已鎖定
					</h4>
				 </div>
				 <div class="modal-body" >
				 	<p>您所選擇的商品因曾經有用作發起合購案，內容已被鎖定無法修改。</p>
				 	<p>若您對商品內容做任何變更，將自動新增一筆商品。</p>
				 	<p>確定繼續？</p>
				 </div>
				 <div class="modal-footer">
					<button class="btn btn-danger" style="margin-top: 0px;" id="iknowthat" data-cpno="">
					   確定
					</button>
					<button class="btn btn-success" data-dismiss="modal">
						取消
					</button>
					
				 </div>
		    </div><!-- /.modal-content -->
		</div>
	</div><!-- /.modal -->
	
<%-- INCLUDE FOOTER1 --%>
<jsp:include page="/f/frag/f_footer1.jsp"/>
<%-- INCLUDE FOOTER1 --%>
	<script>
		var ViewCaseProductEnv = {
				msgHeading:'合購商品編號 ',
				controller:'${pageHome}/caseProduct.do',
				progessMsg:'執行中...  <i class="fa fa-spinner fa-spin"></i>',
				delMsg:'已刪除',
				askMsg:'確定刪除？'
		};
	</script>
	<script src="${pageHome}/js/viewcaseproduct.js"></script>
	<script> 
		menuTrigger(0); 
	</script>
</body>
</html>