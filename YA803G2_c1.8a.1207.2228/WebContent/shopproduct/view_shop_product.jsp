<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="categorySvc" scope="page" class="com.tao.category.model.CategoryService" />
<jsp:useBean id="locSvc" scope="page" class="com.tao.location.model.LocationService" />
<jsp:useBean id="subCategorySvc" scope="page" class="com.tao.category.model.SubCategoryService" />
<jsp:useBean id="shopVO" scope="request" type="com.tao.shop.model.ShopVO"/>
<jsp:useBean id="spVO" scope="request" type="com.tao.shopproduct.model.ShopproductVO"/>
<%@ page import="com.tao.jimmy.member.*"%>
<%@ page import="com.tao.category.model.*"%>
<%
	java.util.Set<TinyMemberVO> tinyMemSet = new TinyMemberService().getAll();
	pageContext.setAttribute("tinyMemSet", tinyMemSet);   
	java.util.LinkedHashMap<String, String> map = new java.util.LinkedHashMap<String, String>();
	SubCategoryVO subcategory = subCategorySvc.getOneSubCategory(spVO.getSubcatno());
	map.put(categorySvc.getOneCategory(subcategory.getCatno()).getCatname() ,request.getContextPath()+"/search/search.do?action=searchMenuShop&subcatno="+subcategory.getCatno());
	map.put(subcategory.getSubcatname(),"" );
	request.setAttribute("breadcrumbMap", map);
	request.setAttribute("showBreadcrumb", new Boolean(true));
	String imagePage = request.getContextPath() + "/shopproduct";
    pageContext.setAttribute("imagePage", imagePage);
%>

<c:set var="Goodcrate" value="0" />
<c:set var="Normalcrate" value="0" />
<c:set var="BadCount" value="0" />
<c:set var="subCategoryList" value="${subCategorySvc.all}" /> 
<c:set var="locList" value="${locSvc.all}" />
<jsp:include page="/f/frag/f_header1.jsp"/>
<title>商品 | 饕飽地圖</title>
<style>
.columns{
	height:100px;
}
.shopbrief, .shopbrief *{
	padding:0px;
}
.shoptitle{
	font-size:40px;
	font-weight:bold;
	font-family:'Roboto', sans-serif,微軟正黑體;
	color:#FE980F;
}
.command{
	background-color:#DDDDDD;
	border:5px;
}
.navis{
	height:0px;
}
.join-buy button:hover{
  background: #2AB33A;
  border: 0 none;
  border-radius: 0;
  color: #FFFFFF;
}
.product-buy button:hover{
  background:#2BBDF6;
  border: 0 none;
  border-radius: 0;
  color: #FFFFFF;
}

.navigs .navig li a {
  background:#FFFFFF;
  color: #FE980F;
  padding: 3px 7px;
}

.navigs .navig li a:after {
  border-color:transparent;
}

.navigs .navig > li + li:before {
  content:">";
}
.single-products img{
	height:150px;
	weight:120px;
}
.prduct-meta li i:after{
	border-color:transparent;
}
.media-meta {
	background: white;
}
#asideTable tr,td{
	text-align: left;
	font-family: sans-serif, "微軟正黑體";
	padding:3px 7px;
}

</style>
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>
<head>
</head>
	<section>
		<div class="container"><!--container-header-->

			<div class="columns" >
					<div class="col-sm-3" >
							<a href="<%=request.getContextPath()%>/shop/shop.do?shopno=${shopVO.shopno}&action=getOne_For_Display"><img src="<%=request.getContextPath()%>/shop/shop.do?shopno=${shopVO.shopno}&action=getPic" width="150" /><p/></a>
					</div>
					<div class="col-sm-9 shopbrief">
						<ul>
							<li class="shoptitle">${shopVO.title}</li>
							<li>
								店家會員：<a href="<%=request.getContextPath() %>/SurfMemberServlet.do?action=memberView&memno=${shopVO.memno}"><% for(TinyMemberVO tmemvo: tinyMemSet){ if(tmemvo.getMemno().intValue() == shopVO.getMemno().intValue()){out.write(tmemvo.getMemid());break;}}%></a>　|　
								聯絡電話：<a style="cursor: default;">${shopVO.phone}</a>　|　
								E-mail：<a href="mailto:${shopVO.email}">${shopVO.email}</a>
							</li>
						</ul>
					</div>
						
			</div>
		</div><!--/container-header-->
		
		
		
		<div class="container">

			<div class="row">
				<div class="col-sm-3" style="height: 1000px;">
					<div class="left-sidebar">
						<div id="evaluation" >
							<h2>商家評價</h2>
								<table id="asideTable">
									<tr>
										<td Class="evaluationPic" style=" width:90px; text-align:center;">
						          			<c:if test="${memberVO.point>50}">
						          				<i class="fa fa-smile-o fa-5x" ></i>
						          			</c:if>
						          			<c:if test="${memberVO.point>0}">
						          				<i class="fa fa-meh-o fa-5x"></i>
						          			</c:if>
						          			<c:if test="${memberVO.point==0}">
						          				<i class="fa fa-frown-o fa-5x"></i>
						          			</c:if>
										</td>
										<c:forEach var="orderVO" items="${orderList}">
											<c:if test="${orderVO.crate==2}">
												<c:set var="Goodcrate" value="${Goodcrate+1}" />
											</c:if>
											<c:if test="${orderVO.crate==1}">
												<c:set var="Normalcrate" value="${Normalcrate+1}" />
											</c:if>
											<c:if test="${orderVO.crate==0}">
												<c:set var="BadCount" value="${BadCount+1}" />
											</c:if>
										</c:forEach>
										<td style="font-size:1.2em;"><b>好評:<a href="<%=request.getContextPath()%>/shop/shop.do?shopno=${shopVO.shopno}&action=getOne_For_Display&tab=rateTab" class="pointer" >${Goodcrate}</a> 次</b></td>
									</tr>
									<tr>
										<td Class="evaluationScore" rowSpan="2" style="color:red; text-align:center; font-size:20px;"><b>${memberVO.point}</b><br/>分</td>
										<td style="font-size:1.2em;"><b>普評:<a href="<%=request.getContextPath()%>/shop/shop.do?shopno=${shopVO.shopno}&action=getOne_For_Display&tab=rateTab" class="pointer" >${Normalcrate}</a> 次</b></td>
									</tr>
									<tr>
										<td style="font-size:1.2em;"><b>差評:<a href="<%=request.getContextPath()%>/shop/shop.do?shopno=${shopVO.shopno}&action=getOne_For_Display&tab=rateTab" class="pointer" >${BadCount}</a> 次</b></td>
									</tr>
								</table><br><br><br>
						</div>

						<div class="brands_products"><!--brands_products-->
							<h2>熱門商品</h2>
							<div class="brands-name">
								<ul class="nav nav-pills nav-stacked">
								<c:forEach var="spVO" items="${spVOs}" >
									<c:if test="${spVO.isrecomm==1}">
										<li><a href="<%=request.getContextPath() %>/shopproduct/shopproduct.do?spno=${spVO.spno}&shopno=${shopVO.shopno}&action=getOne_For_Display" style="font-size:1em; font-family:微軟正黑體;"><b><i class="fa fa-star pull-left fa-fw fa-lg"></i>${spVO.name}</b></a></li>	
									</c:if>
								 </c:forEach>
								</ul>
							</div>
						</div><!--/brands_products-->

						
					</div>
				</div>
		<div class="category-tab shop-details-tab"><!--category-tab-->
		<div class="col-sm-9 padding-right" style="font-family: 微軟正黑體;  color:#696763; background-color:#DDDDDD; text">
			<h4><b>商店商品介紹</b></h4>
		</div>
		<div class="col-sm-9 padding-right">
			<div class="product-details"><!--product-details-->
				<div class="col-sm-5">
					<div class="view-product">
						<img src="<%=request.getContextPath()%>/shopproduct/shopproduct.do?spno=${spVO.spno}&action=getPic1" alt="" style="height:310px; width:260px"/>
					</div>
					<br/>
					<div class="view-product2">
						<img src="<%=request.getContextPath()%>/shopproduct/shopproduct.do?spno=${spVO.spno}&action=getPic1" width="75"  height="80"/>
						<img src="<%=request.getContextPath()%>/shopproduct/shopproduct.do?spno=${spVO.spno}&action=getPic2"  width="75"  height="80" />
						<img src="<%=request.getContextPath()%>/shopproduct/shopproduct.do?spno=${spVO.spno}&action=getPic3" width="75"  height="80" />
					</div>
				</div>
				<div class="col-sm-7" style="font-family: 微軟正黑體;">
					<div class="product-information" style="padding-top: 40px; padding-bottom: 5px; padding-left: 40px;"><!--/product-information-->
						<div class="isRecomm">
							<c:if test="${spVO.isrecomm==1}">
							<img src="images/recomm.jpg" class="newarrival"  />
							</c:if>
						</div>
						<h2><b>${spVO.name}</b></h2>
						<p>商品編號：S${spVO.spno}</p>
						<span>
							<label>售價	:</label><label style="font-size: 26px; color: #FE980F;">${spVO.unitprice}</label>
						</span>
						<div>
							<label>規格:</label>
							<p>${spVO.spec1}</p>
							<p>${spVO.spec2}</p>
							<p>${spVO.spec3}</p>
						</div>
						<span>
							<label>類別	:</label>
								
									<c:forEach var="subCategoryVO" items="${subCategoryList}" > 
							         	<c:if test="${spVO.subcatno==subCategoryVO.subcatno}">
							         		<c:forEach var="categoryVO" items="${categorySvc.all}" > 
							         			<c:if test="${subCategoryVO.catno==categoryVO.catno}">
							          				${categoryVO.catname}
							          			</c:if>
							          		</c:forEach>   
							          	</c:if>
							         </c:forEach>   
								 &gt; 
								
									<c:forEach var="subCategoryVO" items="${subCategoryList}" > 
							         	<c:if test="${spVO.subcatno==subCategoryVO.subcatno}">
							          			${subCategoryVO.subcatname}
							          	</c:if>
							         </c:forEach>      
								<br/>
						</span><hr/>
<!-- 						<form METHOD="" ACTION=""> -->
							<a href="<%=request.getContextPath()%>/cases/cases.do?action=add&spno=${spVO.spno}" target="_blank"><button type="button" class="btn btn-info "><i class="fa fa-shopping-cart"></i> 發起合購</button></a>
<%-- 							<input type="hidden"    name="spno" value="${spVO.spno}">  --%>
<!-- 							<input type="hidden" name="action" value=""> -->
<!-- 						</form>					 -->
					</div><!--/product-information-->
				</div>
			</div><!--/product-details-->
			<div class="col-sm-12 padding-right" style="font-family: 微軟正黑體;  color:#696763; background-color:#DDDDDD;">
				<h4><b>商品說明</b></h4>
			</div>
			<div class="category-tab shop-details-tab"><!--category-tab-->
				<div class="tab-content">
					<div class="tab-pane fade active in" id="details" style="padding: 10px 40px 20px 40px">
						<div class="col-sm-12 " style="font-size:1.1em;">
								<%=spVO.getPro_desc().replace("\n", "<br>") %>
						</div>
						<!--<div class="col-sm-1 "></div>-->
					</div>
				</div>
			</div>
			</div><!--/category-tab-->
		</div>				
				

				</div>
			</div>
<!-- 		</div> -->
<!-- 	</div> -->
	</section>

<jsp:include page="/f/frag/f_footer1.jsp"/>
<script>
$(function() {
	$('.view-product2 img').click(function(){
		var src = $(this).attr('src');
		var target = $('.view-product img');
		if (target.attr('src') != src) {
			target.fadeOut(300, function() {
				$(this).attr('src', src).fadeIn(300);
			});
		}
		return false;
	}).css('cursor', 'pointer');
	
});
</script>
<jsp:include page="/f/frag/f_footer2.jsp"/>