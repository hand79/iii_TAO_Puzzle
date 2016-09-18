<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="catSvc" class="com.tao.category.model.CategoryService"
	scope="page" />
<jsp:useBean id="subcatSvc"
	class="com.tao.category.model.SubCategoryService" scope="page" />
<c:set var="resPath" value="<%=request.getContextPath() +\"/f/res\"%>" />
<c:set var="contextPath" value="<%=request.getContextPath()%>" />

<c:set var="catList" value="${catSvc.all}" />
<c:set var="subcatlist" value="${subcatSvc.all}"/>

<div class="col-sm-3" style="height:1000px">
	<div class="left-sidebar">
		<h2>分類選單</h2>
		<ul class="nav nav-tabs index-tag">
			<li class="active"><a href="#byCase" data-toggle="tab">找合購</a></li>
			<li><a href="#byShop" data-toggle="tab">找商店</a></li>
		</ul>
		<div class="tab-content">
			<div class="tab-pane fade active in" id="byCase">
				<div class="panel-group category-products" id="accordian">
					<!--category-products-->
					<c:forEach var="catVO" items="${catList}" varStatus="sts">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordian"
										href="#category${catVO.catno}"> <span
										class="badge pull-right"><i class="fa fa-plus"></i></span>
										${catVO.catname}
									</a>
								</h4>
							</div>
							<div id="category${catVO.catno}" class="panel-collapse collapse">
								<div class="panel-body">
									<ul>
										<c:forEach var="subcatVO"
											items="${subcatlist}">
											<c:if test="${subcatVO.catno == catVO.catno}">
											<li><a href="${contextPath}/search/search.do?action=searchMenuCase&subcatno=${subcatVO.subcatno}">${subcatVO.subcatname}</a></li>
											</c:if>
										</c:forEach>
									</ul>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
			<div class="tab-pane fade" id="byShop">
				<div class="panel-group category-products" id="accordian">
					<!--category-products-->
					<c:forEach var="catVO" items="${catList}" varStatus="sts">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordian"
										href="#category${catVO.catno}s"> <span
										class="badge pull-right"><i class="fa fa-plus"></i></span>
										${catVO.catname}
									</a>
								</h4>
							</div>
							<div id="category${catVO.catno}s" class="panel-collapse collapse">
								<div class="panel-body">
									<ul>
										<c:forEach var="subcatVO" items="${subcatlist}">
											<c:if test="${subcatVO.catno == catVO.catno}">
											<li><a href="${contextPath}/search/search.do?action=searchMenuShop&subcatno=${subcatVO.subcatno}">${subcatVO.subcatname}</a></li>
											</c:if>
										</c:forEach>
									</ul>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>

		</div>
		<!--/category-products-->
		<div class="brands_products">
			<!--brands_products-->
			<h2>教學說明</h2>
			<div class="brands-name">
				<ul class="nav nav-pills nav-stacked">
					<li><a href="${contextPath}/document.jsp#openShop"> 如何開商店</a></li>
					<li><a href="${contextPath}/document.jsp#openCase"> 如何發起合購</a></li>
					<li><a href="${contextPath}/document.jsp#joinCase"> 如何參加合購</a></li>
					<li><a href="${contextPath}/document.jsp#store"> 如何儲值</a></li>
					<li><a href="${contextPath}/document.jsp#bonus"> 積分獎勵</a></li>
					<li><a href="${contextPath}/document.jsp#question"> 常見問題</a></li>

				</ul>
			</div>
		</div>
	</div>
</div>
<!--.col-sm-3  -->