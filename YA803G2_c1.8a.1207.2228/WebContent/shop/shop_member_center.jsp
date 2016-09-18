<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ include file="/f/frag/f_login_redirect.file" %>
<%@ page import="com.tao.shop.model.*"%>
<%
	ShopService shopSvc = new ShopService();
	List<ShopVO> list = shopSvc.getAll();
	pageContext.setAttribute("list", list);
%>
<%	pageContext.setAttribute("context", request.getContextPath()); %>
<%

    String pageHome = request.getContextPath() + "/shop";
    pageContext.setAttribute("pageHome", pageHome);
%>
<%-- <jsp:useBean id="memberSvc" scope="page" 	class="com.tao.member.model.MemberService" /> --%>
<jsp:useBean id="locationSvc" scope="page" 	class="com.tao.location.model.LocationService" />
<c:set var="locationList" value="${locationSvc.all}"/>
<jsp:include page="/f/frag/f_header1.jsp"/>
<title>商店管理 | 饕飽地圖</title>
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>
<head>
<STYLE>
body { background-color:white; } 
.modal-title {
font-weight:bold; 
color: #FE980F; 
font-family: 微軟正黑體;
}
.modal-body p span {
	font-size: 1.5em;
}
.modal-body p .total {
	color:#FE980F; 
}
.modal-open{
	overflow: initial;
}
.modal-body b{
font-family: 微軟正黑體;
font-size: 1.1em;
}
 </STYLE>
</head>
<section>
	<div class="container">
		<div class="row">
			<jsp:include page="/f/frag/f_memCenterMenu.jsp"/>
				<div class="col-sm-9" >
					<h2 class="title text-center">商店列表<h2>
					<div class="list-zone">
						<table class="table table-hover table-condensed table-striped" style="font-size: 0.5em; font-family:微軟正黑體;">
							<tr class="list-header info">
								<th>商店編號</th>
								<th>名稱</th>
								<th>地區</th>
								<th>狀態</th>
								<th>編輯 / 檢視商品</th>
								<th colspan="3" text="center">操作</th>
							</tr>
							<c:forEach var="shopVO" items="${list}">
							<c:if test="${shopVO.memno==CurrentUser.memno}">
							<tr class="mainrow" id="order1">
								<td>${shopVO.shopno}</td>
								<c:if test="${shopVO.status==2}">
									<td><a href="<%=request.getContextPath()%>/shop/shop.do?shopno=${shopVO.shopno}&action=getOne_For_Display">${shopVO.title}</a></td>	
								</c:if>
								<c:if test="${shopVO.status!=2}">
									<td><a href="<%=request.getContextPath()%>/shop/shop.do?shopno=${shopVO.shopno}&action=getTemp_Display">${shopVO.title}</a></td>	
								</c:if>
								
								<td>
									<c:forEach var="locationVO" items="${locationList}">
										<c:if test="${shopVO.locno==locationVO.locno}">
						                    	${locationVO.county}
					                    </c:if>
								     </c:forEach>
								</td>
								<td>
									<c:if test="${shopVO.status==0}" >已建立</c:if>
									<font style="color: orange;"><c:if test="${shopVO.status==1}">待審核</c:if></font>
									<font style="color: blue;"><c:if test="${shopVO.status==2}">上架中</c:if></font>
									<font style="color: green;"><c:if test="${shopVO.status==3}">已下架</c:if></font>
									<font style="color: gery;"><c:if test="${shopVO.status==4}">已刪除</c:if></font>
									<font style="color: red;"><c:if test="${shopVO.status==5}">未通過審核</c:if></font>
								</td>
								<c:choose>
									<c:when test="${shopVO.status==0|| shopVO.status==3||shopVO.status==5}">
										<td>
											<a href="<%=request.getContextPath()%>/shop/shop.do?shopno=${shopVO.shopno}&action=getOne_For_Update" ><i class="fa fa-edit fa-fw"></i>編輯 </a>  / 
											<a href="<%=request.getContextPath()%>/shopproduct/shopproduct.do?shopno=${shopVO.shopno}&action=listShopproduct_ByCompositeQuery">檢視商品 <i class="fa fa-eye fa-fw"></i></a>
										</td>
										<td id="up"><a data-toggle="modal" data-target="#up_shop_${shopVO.shopno}" style="cursor: pointer; "><i class="fa fa-arrow-up fa-fw"></i>申請上架</a></td>
										<td>－</td>
										<td><a data-toggle="modal" data-target="#del_shop_${shopVO.shopno}" style="cursor: pointer; "><i class="fa fa-trash-o fa-fw"></i>刪除</a></td>
									</c:when>
									<c:when test="${shopVO.status==2}">
										<td>
											<a href="<%=request.getContextPath()%>/shop/shop.do?shopno=${shopVO.shopno}&action=getOne_For_Update" ><i class="fa fa-edit fa-fw"></i>編輯 </a>  / 
											<a href="<%=request.getContextPath()%>/shopproduct/shopproduct.do?shopno=${shopVO.shopno}&action=listShopproduct_ByCompositeQuery">檢視商品 <i class="fa fa-eye fa-fw"></i></a>
										</td>
										<td>－</td>
										<td id="down"><a data-toggle="modal" data-target="#down_shop_${shopVO.shopno}" style="cursor: pointer; "><i class="fa fa-arrow-down fa-fw"></i>下架</a></td>
										<td><a data-toggle="modal" data-target="#del_shop_${shopVO.shopno}" style="cursor: pointer; "><i class="fa fa-trash-o fa-fw"></i>刪除</a></td>
									</c:when>
									<c:when test="${shopVO.status==1}">
										<td>
											<a href="<%=request.getContextPath()%>/shop/shop.do?shopno=${shopVO.shopno}&action=getOne_For_Update" ><i class="fa fa-edit fa-fw"></i>編輯 </a>  / 
											<a href="<%=request.getContextPath()%>/shopproduct/shopproduct.do?shopno=${shopVO.shopno}&action=listShopproduct_ByCompositeQuery">檢視商品 <i class="fa fa-eye fa-fw"></i></a>
										</td>
										<td>－</td>
										<td id="down"><a data-toggle="modal" data-target="#cancel_shop_${shopVO.shopno}" style="cursor: pointer; "><i class="fa fa-times fa-fw"></i >取消</a></td>
										<td><a data-toggle="modal" data-target="#del_shop_${shopVO.shopno}" style="cursor: pointer; "><i class="fa fa-trash-o fa-fw"></i>刪除</a></td>
									</c:when>
									<c:otherwise>
										<td>－ </td>
										<td>－</td>
										<td>－</td>
										<td>－</td>
									</c:otherwise>
								</c:choose>
								
								
							</tr>
							</c:if>
							</c:forEach>		
						</table>
						
						<div style="text-align: right; margin-top: 40px;">
						<button class="btn btn-success"><a href='create_shop_front.jsp' style="color:white">建立商店 <i class="fa fa-plus"></i></a></button>
						</div>
					</div><!--class="list-zone"-->
				</div><!--class="col-sm-9"-->
		</div>
	</div>
</section>

<!-- 刪除燈箱放這裡 -->
<c:forEach var="shopVO" items="${list}">
<div class="modal fade" id="del_shop_${shopVO.shopno}" role="dialog"
	aria-labelledby="AdModal" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="AdModal" style="color: #d9534f;">
					<i class="fa fa-warning fa-fw"></i>提醒訊息
				</h4>

			</div>
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/shop/shop.do">
				<div class="modal-body">
					確定刪除商店編號 <b><font color=#d9534f class="delcomsg">${shopVO.shopno}『${shopVO.title}』</font></b> 紀錄?
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<input type="submit" value="刪除" class="btn btn-danger"> 
					<input type="hidden" name="shopno" value="${shopVO.shopno}"> 
					<input type="hidden" name="action" value="update_stauts">
					<input type="hidden" name="status" value="4">
					<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
				</div>
			</FORM>
		</div>
	</div>
</div>
</c:forEach>
<!-- 申請上架燈箱放這裡 -->
<c:forEach var="shopVO" items="${list}">
<div class="modal fade" id="up_shop_${shopVO.shopno}" role="dialog"
	aria-labelledby="AdModal" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="AdModal" style="color: #f0ad4e;">
					<i class="fa fa-warning fa-fw"></i>提醒訊息
				</h4>

			</div>
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/shop/shop.do">
				<div class="modal-body">
					商店編號 <b><font color="#f0ad4e" class="delcomsg">${shopVO.shopno}『${shopVO.title}』</font></b> 確定申請上架?
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<input type="submit" value="申請上架" class="btn btn-warning"> 
					<input type="hidden"    name="shopno" value="${shopVO.shopno}"> 
					<input type="hidden" name="action" value="update_stauts">
					<input type="hidden" name="status" value="1">
					<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
				</div>
			</FORM>
		</div>
	</div>
</div>
</c:forEach>
<!-- 下架燈箱放這裡 -->
<c:forEach var="shopVO" items="${list}">
<div class="modal fade" id="down_shop_${shopVO.shopno}" role="dialog"
	aria-labelledby="AdModal" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="AdModal" style="color: #5cb85c;">
					<i class="fa fa-warning fa-fw"></i>提醒訊息
				</h4>

			</div>
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/shop/shop.do">
				<div class="modal-body">
					商店編號 <b><font color="#5cb85c" class="delcomsg">${shopVO.shopno}『${shopVO.title}』</font></b> 確定下架?
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<input type="submit" value="下架" class="btn btn-success">
					<input type="hidden"    name="shopno" value="${shopVO.shopno}"> 
					<input type="hidden" name="action" value="update_stauts">
					<input type="hidden" name="status" value="3">
					<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
				</div>
			</FORM>
		</div>
	</div>
</div>
</c:forEach>
<!-- 取消審核上架燈箱放這裡 -->
<c:forEach var="shopVO" items="${list}">
<div class="modal fade" id="cancel_shop_${shopVO.shopno}" role="dialog"
	aria-labelledby="AdModal" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="AdModal" style="color: orange;">
					<i class="fa fa-warning fa-fw"></i>提醒訊息
				</h4>

			</div>
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/shop/shop.do">
				<div class="modal-body">
					確定取消審核商店編號 <b><font color="orange" class="delcomsg">${shopVO.shopno}『${shopVO.title}』</font></b> ?
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<input type="submit" value="確定" class="btn btn-warning">
					<input type="hidden"    name="shopno" value="${shopVO.shopno}"> 
					<input type="hidden" name="action" value="update_stauts">
					<input type="hidden" name="status" value="0">
					<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
				</div>
			</FORM>
		</div>
	</div>
</div>
</c:forEach>
<jsp:include page="/f/frag/f_footer1.jsp"/>
	<script>
		var push = function(){
			$.post('<%= request.getContextPath()%>/shop/shop.do', 'action=push', function(data){
				console.log('gotResponse');
				if(data == 'success'){
				console.log('success');
					window.location.href = '<%= request.getContextPath()%>/shop/shop_member_center.jsp';
				}
			});	
		};
		$(function(){
			push();
		});
	</script>
<jsp:include page="/f/frag/f_footer2.jsp"/>