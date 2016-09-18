<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tao.searchresult.model.*"%>
<%@ page import="com.tao.runningad.model.*"%>

<%@ page import="java.util.*" %>
<jsp:useBean id="runAdSvc" class="com.tao.runningad.model.RunningAdService" scope="page" />
<c:set var="runAdList" value="${runAdSvc.allActiveAds}" />

<jsp:include page="/f/frag/f_header1.jsp"/>
<title> �j�M�X�� </title>
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>

	<section id="slider"><!--slider-->
		<div class="container">
			<div class="row">
				<!--�s�ibanner�}�l-->
				<div class="col-sm-12">
					<div id="slider-carousel" class="carousel slide" data-ride="carousel">
						<div class="carousel-inner" style="align-content:center;">
						
							<!--�w�]�s�i�ɮ׶}�l-->
							<div class="item active">
								<div class="col-sm-12">
									<img src="<%=request.getContextPath()%>/f/res/images/home/default.jpg" class="img-responsive" style="width:900;height:400px;" alt="" />
								</div>
							</div>							
							<!-- �w�]�s�i�ɮ׵��� -->
							
							<!--��L�s�i�ɮ׶}�l-->
							<c:forEach var="runAd" items="${runAdList}" varStatus="runIndex">
							
								<div class="item">
									<div class="col-sm-12">
										<a href="${contextPath}/caseqa/caseQA.do?action=caseDetail&caseno=${runAd.caseno}">
											<img src="<%=request.getContextPath()%>/DBRunningAdImgReader?adno=${runAd.adno}" 
											class="img-responsive" style="width:900px;height:400px;" alt="" />
										</a>
									</div>
								</div>
								
							</c:forEach>

							<!--��L�s�i�ɮ׵���-->														
							
						</div>
						
						<a href="#slider-carousel" class="left control-carousel hidden-xs" data-slide="prev">
							<i class="fa fa-angle-left"></i>
						</a>
						<a href="#slider-carousel" class="right control-carousel hidden-xs" data-slide="next">
							<i class="fa fa-angle-right"></i>
						</a>
					</div>
					
				</div>
				<!--�s�ibanner����-->
			</div>
		</div>
	</section><!--/slider-->
	
	<section><!--form-->
		<div class="container">
			<div class="row">
			<jsp:include page="/f/frag/f_catMenu.jsp"/>
			
				<div class="col-sm-9 padding-right">
					<div><!--�̷s����-->
						<h2 class="title text-center">�̷s����</h2><a href="[cathy]news_detail.html" style="float:right;">more...</a>
						<div class="row">
							<div class="col-sm-12">
								<div class="list-zone">
									<table class="table table-hover table-condensed newslist" style="font-size: 1em; font-family:�L�n������;">
										<tr class="list-header info">
											<th>�s��</th>
											<th>���D</th>
											<th  class="text-center">�o�G�ɶ�</th>
										</tr>
										<tr class="mainrow" id="news">
											<td>1</td>
											<td><a data-toggle="modal" data-target="#detail-modal1" style="cursor: pointer;">[�t�Τ��i]-�̷s�����\��W�u</a></td>
											<td class="text-center">2014-11-07 12:00</td>
										</tr>
										<tr class="mainrow" id="news">
											<td>2</td>
											<td><a data-toggle="modal" data-target="#detail-modal2" style="cursor: pointer;">[�s�i���a]�ȥ��J�G�l�u��</a></td>
											<td class="text-center">2014-11-06 10:20</td>
										</tr>
										<tr class="mainrow" id="news">
											<td>3</td>
											<td><a data-toggle="modal" data-target="#detail-modal3" style="cursor: pointer;">�V�u�P���^�X ���ɷm��.</a></td>
											<td class="text-center">2014-11-06 10:20</td>
										</tr>
										<tr class="mainrow" id="news">
											<td>4</td>
											<td><a data-toggle="modal" data-target="#detail-modal4" style="cursor: pointer;">���Q��y �u�f�j�m��</a></td>
											<td class="text-center">2014-10-10 10:20</td>
										</tr>
										<tr class="mainrow" id="news">
											<td>5</td>
											<td><a data-toggle="modal" data-target="#detail-modal" style="cursor: pointer;">�զY���s����~�u��Ʀh�B�����B��Q�u~�������q�j�Q��</a></td>
											<td class="text-center">2014-08-09 10:20</td>
										</tr>
									</table>	
									
								</div><!--class="list-zone"-->
							</div>							
						</div>
					</div>

				
					
					<div class="recommended_items"><!--�����ӫ~-->
						<h2 class="title text-center">�����ӫ~</h2>
						
						<div id="recommended-item-carousel" class="carousel slide" data-ride="carousel">
							<div class="carousel-inner">
								<div class="item active">	
									<div class="col-sm-4">
										<div class="product-image-wrapper">
											<div class="single-products">
												<div class="productinfo text-center">
													<img src="<%=request.getContextPath()%>/f/res/images/home/recommend1.jpg" alt="" />
													<h2>$56</h2>
													<p>Easy Polo Black Edition</p>
													<a href="#" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</a>
												</div>
												
											</div>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="product-image-wrapper">
											<div class="single-products">
												<div class="productinfo text-center">
													<img src="<%=request.getContextPath()%>/f/res/images/home/recommend2.jpg" alt="" />
													<h2>$56</h2>
													<p>Easy Polo Black Edition</p>
													<a href="#" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</a>
												</div>
												
											</div>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="product-image-wrapper">
											<div class="single-products">
												<div class="productinfo text-center">
													<img src="<%=request.getContextPath()%>/f/res/images/home/recommend3.jpg" alt="" />
													<h2>$56</h2>
													<p>Easy Polo Black Edition</p>
													<a href="#" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</a>
												</div>
												
											</div>
										</div>
									</div>
								</div>
								<div class="item">	
									<div class="col-sm-4">
										<div class="product-image-wrapper">
											<div class="single-products">
												<div class="productinfo text-center">
													<img src="<%=request.getContextPath()%>/f/res/images/home/recommend1.jpg" alt="" />
													<h2>$56</h2>
													<p>Easy Polo Black Edition</p>
													<a href="#" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</a>
												</div>
												
											</div>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="product-image-wrapper">
											<div class="single-products">
												<div class="productinfo text-center">
													<img src="<%=request.getContextPath()%>/f/res/images/home/recommend2.jpg" alt="" />
													<h2>$56</h2>
													<p>Easy Polo Black Edition</p>
													<a href="#" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</a>
												</div>
												
											</div>
										</div>
									</div>
									<div class="col-sm-4">
										<div class="product-image-wrapper">
											<div class="single-products">
												<div class="productinfo text-center">
													<img src="<%=request.getContextPath()%>/f/res/images/home/recommend3.jpg" alt="" />
													<h2>$56</h2>
													<p>Easy Polo Black Edition</p>
													<a href="#" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</a>
												</div>
												
											</div>
										</div>
									</div>
								</div>
							</div>
							 <a class="left recommended-item-control" href="#recommended-item-carousel" data-slide="prev">
								<i class="fa fa-angle-left"></i>
							  </a>
							  <a class="right recommended-item-control" href="#recommended-item-carousel" data-slide="next">
								<i class="fa fa-angle-right"></i>
							  </a>			
						</div>
					</div><!--/recommended_items-->
					
					<div class="features_items"><!--features_items-->
						<h2 class="title text-center">�����ө�</h2>
						
						<div class="col-sm-4">
							<div class="product-image-wrapper">
								<div class="single-products">
										<div class="productinfo text-center">
											<img src="<%=request.getContextPath()%>/f/res/images/home/product1.jpg" alt="" />
											<h2>$56</h2>
											<p>Easy Polo Black Edition</p>
											<a href="#" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</a>
										</div>
										<div class="product-overlay">
											<div class="overlay-content">
												<h2>$56</h2>
												<p>Easy Polo Black Edition</p>
												<a href="#" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</a>
											</div>
										</div>
								</div>
								<div class="choose">
									<ul class="nav nav-pills nav-justified">
										<li><a href="#"><i class="fa fa-plus-square"></i>Add to wishlist</a></li>
										<li><a href="#"><i class="fa fa-plus-square"></i>Add to compare</a></li>
									</ul>
								</div>
							</div>
						</div>
						
						<div class="col-sm-4">
							<div class="product-image-wrapper">
								<div class="single-products">
									<div class="productinfo text-center">
										<img src="<%=request.getContextPath()%>/f/res/images/home/product2.jpg" alt="" />
										<h2>$56</h2>
										<p>Easy Polo Black Edition</p>
										<a href="#" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</a>
									</div>
									<div class="product-overlay">
										<div class="overlay-content">
											<h2>$56</h2>
											<p>Easy Polo Black Edition</p>
											<a href="#" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</a>
										</div>
									</div>
								</div>
								<div class="choose">
									<ul class="nav nav-pills nav-justified">
										<li><a href="#"><i class="fa fa-plus-square"></i>Add to wishlist</a></li>
										<li><a href="#"><i class="fa fa-plus-square"></i>Add to compare</a></li>
									</ul>
								</div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="product-image-wrapper">
								<div class="single-products">
									<div class="productinfo text-center">
										<img src="<%=request.getContextPath()%>/f/res/images/home/product3.jpg" alt="" />
										<h2>$56</h2>
										<p>Easy Polo Black Edition</p>
										<a href="#" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</a>
									</div>
									<div class="product-overlay">
										<div class="overlay-content">
											<h2>$56</h2>
											<p>Easy Polo Black Edition</p>
											<a href="#" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</a>
										</div>
									</div>
								</div>
								<div class="choose">
									<ul class="nav nav-pills nav-justified">
										<li><a href="#"><i class="fa fa-plus-square"></i>Add to wishlist</a></li>
										<li><a href="#"><i class="fa fa-plus-square"></i>Add to compare</a></li>
									</ul>
								</div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="product-image-wrapper">
								<div class="single-products">
									<div class="productinfo text-center">
										<img src="<%=request.getContextPath()%>/f/res/images/home/product4.jpg" alt="" />
										<h2>$56</h2>
										<p>Easy Polo Black Edition</p>
										<a href="#" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</a>
									</div>
									<div class="product-overlay">
										<div class="overlay-content">
											<h2>$56</h2>
											<p>Easy Polo Black Edition</p>
											<a href="#" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</a>
										</div>
									</div>
									<img src="<%=request.getContextPath()%>/f/res/images/home/new.png" class="new" alt="" />
								</div>
								<div class="choose">
									<ul class="nav nav-pills nav-justified">
										<li><a href="#"><i class="fa fa-plus-square"></i>Add to wishlist</a></li>
										<li><a href="#"><i class="fa fa-plus-square"></i>Add to compare</a></li>
									</ul>
								</div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="product-image-wrapper">
								<div class="single-products">
									<div class="productinfo text-center">
										<img src="<%=request.getContextPath()%>/f/res/images/home/product5.jpg" alt="" />
										<h2>$56</h2>
										<p>Easy Polo Black Edition</p>
										<a href="#" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</a>
									</div>
									<div class="product-overlay">
										<div class="overlay-content">
											<h2>$56</h2>
											<p>Easy Polo Black Edition</p>
											<a href="#" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</a>
										</div>
									</div>
									<img src="<%=request.getContextPath()%>/f/res/images/home/sale.png" class="new" alt="" />
								</div>
								<div class="choose">
									<ul class="nav nav-pills nav-justified">
										<li><a href="#"><i class="fa fa-plus-square"></i>Add to wishlist</a></li>
										<li><a href="#"><i class="fa fa-plus-square"></i>Add to compare</a></li>
									</ul>
								</div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="product-image-wrapper">
								<div class="single-products">
									<div class="productinfo text-center">
										<img src="<%=request.getContextPath()%>/f/res/images/home/product6.jpg" alt="" />
										<h2>$56</h2>
										<p>Easy Polo Black Edition</p>
										<a href="#" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</a>
									</div>
									<div class="product-overlay">
										<div class="overlay-content">
											<h2>$56</h2>
											<p>Easy Polo Black Edition</p>
											<a href="#" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</a>
										</div>
									</div>
								</div>
								<div class="choose">
									<ul class="nav nav-pills nav-justified">
										<li><a href="#"><i class="fa fa-plus-square"></i>Add to wishlist</a></li>
										<li><a href="#"><i class="fa fa-plus-square"></i>Add to compare</a></li>
									</ul>
								</div>
							</div>
						</div>
						
					</div><!--features_items-->
			</div>
		</div>
		</div>
	</section><!--/form-->
	
	
<jsp:include page="/f/frag/f_footer1.jsp"/>
</body>
</html>
