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

<title>�X�ʰӫ~�w�� | TAO Puzzle</title>
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
			<div class="col-sm-9 padding-right">
				<h2 class="text-center title">�X�ʰӫ~�w��</h2>
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
					<div class="col-sm-7" style="font-family: �L�n������;">
						<div class="product-information"
							style="padding-top: 40px; padding-bottom: 5px; padding-left: 40px">
							<!--/product-information-->
							<img src="${resPath}/images/product-details/new.jpg"
								class="newarrival" alt="" />
							<!--<div class="newarrival">�Ҷ���</div>-->
							<h2>
								<b>${cvo.title}</b>
							</h2>
							<p>
								�X�ʮ׽s���G-&nbsp;&nbsp;&nbsp;&nbsp;�ӫ~�s���G<c:if test="${not empty cpvo}">C${cpvo.cpno}</c:if><c:if test="${not empty spvo}">S${spvo.spno}</c:if>
								&nbsp;&nbsp;&nbsp;&nbsp;����GNT $${cpvo.unitprice}
							</p>
							<span><span id="dispPrice">NT $${(not empty spvo)?(spvo.unitprice
									- cvo.discount):""}${(not empty cpvo)?(cpvo.unitprice - cvo.discount):""}</span>
								<label>�ƶq :</label> <input type="text" value="1" />
								<button type="button" class="btn btn-fefault cart" style="font-weight: bold; font-family: �L�n������;">
									�[�J�X�� <i class="fa fa-users"></i>
								</button>
							</span>
							<br/>
							<button type="button" class="btn btn-success">�[�J�l��</button>
							<button type="button" class="btn btn-danger">���|</button>
							<hr />
							<h3 style="font-family: �L�n������;">
								<b>���A�G</b> -
							</h3>

							<h4>
								<b>�I��ɶ��G</b> -
							</h4>
							<h4>
								<b>�ثe/����/�W�� �ƶq�G</b><b style="color:#5bc0de;" id="currentOrderAmount">-</b> / <b style="color:#5cb85c;">-</b> / <b style="color:#d9534f">-</b>
							</h4>
							<p>
								<b>�}�ήɶ��G</b>-
							</p>
							<p></p>
							<h5>
								<b>�D �ʡG</b> 
								<a href="#"><i class="fa fa-user"></i> 
								${CurrentUser.memid} (${CurrentUser.point})
								</a>
							</h5>

							<h5>
								<b>�a �ϡG</b> -&nbsp;&gt;&nbsp;-
							</h5>


							<h5>
								<b>��f�覡�G</b> - - ��&nbsp;&nbsp;&nbsp; - - ��
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
							<li class="active"><a href="#details" data-toggle="tab">�ӫ~�Ա�</a></li>
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
				<a class="btn btn-primary pull-right" style="margin-bottom: 80px;" href="${pageHome}/caseProduct.do?action=viewOwnCPs">��^</a>
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