<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>
<%@page import="com.tao.cases.model.*"%>
<%@page import="com.tao.jimmy.shop.*"%>
<%-- <%@page import="com.tao.shopproduct.model.ShopproductVO"%> --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="resPath" value="<%=request.getContextPath() +\"/f/res\"%>" />
<c:set var="pageHome" value="<%=request.getContextPath() +\"/caseproduct\"%>" />
<c:set var="picHome" value="<%=request.getContextPath() +\"/caseproduct\"%>"/>

<%-- Include Header1 --%>
<jsp:include page="/f/frag/f_header1.jsp" />
<link rel="stylesheet" href="${pageHome}/css/casedetail.css"/>

<title>合購商品預覽 | TAO Puzzle</title>
<%
request.setAttribute("showBreadcrumb", new Object()); 
java.util.LinkedHashMap<String, String> map = new java.util.LinkedHashMap<String, String>();
map.put("會員中心","");
request.setAttribute("breadcrumbMap", map); 
%>

<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp" />

<section>
	<div class="container">
		<div class="row">
			<jsp:include page="/f/frag/f_memCenterMenu.jsp" />
			<div class="col-sm-9 padding-right">
				<h2 class="text-center title">合購商品預覽</h2>
				<div class="product-details">
					<!--product-details-->
					<div class="col-sm-5">
						<div class="view-product">
							<c:if test="${not empty cpvo}">
								<img id="currentImg"
									src="${picHome }/Picture?pic=1&cpno=${cpvo.cpno}" alt="" />
							</c:if>
							<c:if test="${not empty spvo}">
								<img id="currentImg"
									src="${picHome }/Picture?pic=1&spno=${spvo.spno}" alt="" />
							</c:if>
						</div>
						<div id="similar-product" class="carousel slide"
							data-ride="carousel">
							<!-- Wrapper for slides -->
							<div class="carousel-inner">
								<div class="item active">
									<c:if test="${not empty cpvo}">
										<c:if test="${not empty cpvo.pmime1 }"><img src="${picHome }/Picture?pic=1&cpno=${cpvo.cpno}" /></c:if>
										<c:if test="${not empty cpvo.pmime2 }"><img src="${picHome }/Picture?pic=2&cpno=${cpvo.cpno}" /></c:if>
										<c:if test="${not empty cpvo.pmime3 }"><img src="${picHome }/Picture?pic=3&cpno=${cpvo.cpno}" /></c:if>
									</c:if>
									<c:if test="${not empty spvo}">
										<c:if test="${not empty spvo.pmime1 }"><img src="${picHome }/Picture?pic=1&spno=${spvo.spno}" /></c:if>
										<c:if test="${not empty spvo.pmime2 }"><img src="${picHome }/Picture?pic=2&spno=${spvo.spno}" /></c:if>
										<c:if test="${not empty spvo.pmime3 }"><img src="${picHome }/Picture?pic=3&spno=${spvo.spno}" /></c:if>
									</c:if>
								</div>
							</div>
						</div>
					</div>
					<div class="col-sm-7" style="font-family: 微軟正黑體;">
						<div class="product-information"
							style="padding-top: 40px; padding-bottom: 5px; padding-left: 40px">
							<!--/product-information-->
							<img src="${resPath}/images/product-details/new.jpg"
								class="newarrival" alt="" />
							<!--<div class="newarrival">募集中</div>-->
							<h2>
								<b>${cvo.title}</b>
							</h2>
							<p>
								合購案編號：-&nbsp;&nbsp;&nbsp;&nbsp;商品編號：<c:if test="${not empty cpvo}">C${cpvo.cpno}</c:if><c:if test="${not empty spvo}">S${spvo.spno}</c:if>
								&nbsp;&nbsp;&nbsp;&nbsp;原價：NT $${cpvo.unitprice}
							</p>
							<span><span id="dispPrice">NT $${(not empty spvo)?(spvo.unitprice
									- cvo.discount):""}${(not empty cpvo)?(cpvo.unitprice - cvo.discount):""}</span>
								<label>數量 :</label> <input type="text" value="1" />
								<button type="button" class="btn btn-fefault cart" style="font-weight: bold; font-family: 微軟正黑體;">
									加入合購 <i class="fa fa-users"></i>
								</button>
							</span>
							<br/>
							<button type="button" class="btn btn-success">加入追蹤</button>
							<button type="button" class="btn btn-danger">檢舉</button>
							<hr />
							<h3 style="font-family: 微軟正黑體;">
								<b>狀態：</b> -
							</h3>

							<h4>
								<b>截止時間：</b> -
							</h4>
							<h4>
								<b>目前/成團/上限 數量：</b><b style="color:#5bc0de;" id="currentOrderAmount">-</b> / <b style="color:#5cb85c;">-</b> / <b style="color:#d9534f">-</b>
							</h4>
							<p>
								<b>開團時間：</b>-
							</p>
							<p></p>
							<h5>
								<b>主 購：</b> 
								<a href="#"><i class="fa fa-user"></i> 
								${CurrentUser.memid} (${CurrentUser.point})
								</a>
							</h5>

							<h5>
								<b>地 區：</b> -&nbsp;&gt;&nbsp;-
							</h5>


							<h5>
								<b>交貨方式：</b> - - 元&nbsp;&nbsp;&nbsp; - - 元
							</h5>
							<h5>
								<b> </b>
							</h5>


						</div>
						<!--/product-information-->
					</div>
				</div>
				<!--/product-details-->

				<div class="category-tab shop-details-tab">
					<!--category-tab-->
					<div class="col-sm-12">
						<ul class="nav nav-tabs">
							<li class="active"><a href="#details" data-toggle="tab">商品詳情</a></li>
						</ul>
					</div>
					<div class="tab-content">
						<div class="tab-pane fade active in" id="details"
							style="padding: 10px 40px 20px 40px">
							<!--<div class="col-sm-1 "></div>-->
							<div class="col-sm-12 ">
								${cpvo.cpdesc}${spvo.pro_desc}
							</div>
							<!--<div class="col-sm-1 "></div>-->
						</div>
					</div>
				</div>
				<!--/category-tab-->
				<a class="btn btn-primary pull-right" style="margin-bottom: 80px;" href="${pageHome}/caseProduct.do?action=viewOwnCPs">返回</a>
			</div>
		</div>
	</div>
</section>

<%-- Include Footer1 --%>
<jsp:include page="/f/frag/f_footer1.jsp"></jsp:include>
<script>
menuTrigger(0);
$(".carousel-inner > div > img").click(function() {
	var src = $(this).attr('src');
	var target = $('#currentImg');
	if (target.attr('src') != src) {
		target.fadeOut(300, function() {
			$(this).attr('src', src).fadeIn(300);
		});
	}
	return false;
});
</script>
<jsp:include page="/f/frag/f_footer2.jsp" />