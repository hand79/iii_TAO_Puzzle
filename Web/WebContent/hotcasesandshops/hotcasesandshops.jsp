<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<jsp:include page="/f/frag/f_header1.jsp"/>

<title>熱門瀏覽</title>
<link href='<%=request.getContextPath()%>/f/calendar/css/jquery-ui.min.css' rel='stylesheet'>
<link href='<%=request.getContextPath()%>/f/calendar/css/fullcalendar.min.css' rel='stylesheet'>
<link href='<%=request.getContextPath()%>/f/calendar/css/fullcalendar.print.css' rel='stylesheet' media='print'>
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/> 
   	
<jsp:useBean id="hotCaseSvc" class="com.tao.hotcasesandshops.model.HotCaseService" />
<jsp:useBean id="hotShopSvc" class="com.tao.hotcasesandshops.model.HotShopService"/>
<c:set var="hotCaseList" value="${hotCaseSvc.getAllByHitsStatus()}" />
<c:set var="hotCaseListSize" value="${hotCaseList.size()}" />
<c:set var="hotShopList" value="${hotShopSvc.getAllByHitsStatus()}" />
<c:set var="hotShopListSize" value="${hotShopList.size()}" />

<section>
	<div class="container">
		<div class="row">
<jsp:include page="/f/frag/f_catMenu.jsp"/>  

 			<div class="col-sm-9">
 				<div class="recommended_items"><!--recommended_items-->
						<h2 class="title text-center">熱門合購案</h2>
						
						<div id="recommended-item-carousel" class="carousel slide" data-ride="carousel">
							<div class="carousel-inner">
								<div class="item active">	
									<!--合購初始輪播1-3開始-->
								<c:forEach var="hotcaseVO" items="${hotCaseList}" begin="0" end="2">
									<div class="col-sm-4">
										<div class="product-image-wrapper">
											<div class="single-products">
												<div class="productinfo text-center">
													<img width="251px" height="251px" src="${pageContext.request.contextPath}/HotCaseImage.do?caseno=${hotcaseVO.caseno}" alt="${hotcaseVO.title}" />
													<p>${hotcaseVO.title}</p>
													<a href="${pageContext.request.contextPath}/cases/cases.do?action=caseDetail&caseno=${hotcaseVO.caseno}"  
													class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>GO!!</a>
												</div>
												
											</div>
										</div>
									</div>
								</c:forEach>	
									<!--合購初始輪播1-3結束-->	
								</div>
								<c:if test="${hotCaseListSize>3}">
									<div class="item">	
										<c:forEach var="hotcaseVO" items="${hotCaseList}" begin="3" >
											<div class="col-sm-4">
												<div class="product-image-wrapper">
													<div class="single-products">
														<div class="productinfo text-center">
															<img width="251px" height="251px" src="${pageContext.request.contextPath}/HotCaseImage.do?caseno=${hotcaseVO.caseno}" alt="${hotcaseVO.title}" />
															<p>${hotcaseVO.title}</p>
															<a href="${pageContext.request.contextPath}/cases/cases.do?action=caseDetail&caseno=${hotcaseVO.caseno}" 
															class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>GO!!</a>
														</div>
													</div>
												</div>
											</div>
										</c:forEach>	
									</div>
								</c:if>
							</div>
							<c:if test="${hotCaseListSize>3}">
								 <a class="left recommended-item-control" href="#recommended-item-carousel" data-slide="prev">
									<i class="fa fa-angle-left"></i>
								  </a>
								  <a class="right recommended-item-control" href="#recommended-item-carousel" data-slide="next">
									<i class="fa fa-angle-right"></i>
								  </a>
							 </c:if> 			
						</div>
					</div><!--/recommended_items-->
					
					<div class="recommended_items"><!--recommended_items-->
						<h2 class="title text-center">熱門商店</h2>
						
						<div id="recommended-item-carousel2" class="carousel slide" data-ride="carousel">
							<div class="carousel-inner">
								<div class="item active" style="padding-left:0;">	
								<!--商店初始輪播1-3開始-->
								<c:forEach var="hotshopVO" items="${hotShopList}" begin="0" end="2">
									<div class="col-sm-4">
										<div class="product-image-wrapper">
											<div class="single-products">
												<div class="productinfo text-center">
													<img width="251px" height="251px" src="${pageContext.request.contextPath}/HotShopImage.do?shopno=${hotshopVO.shopno}" alt="${hotshopVO.title}" />
													<p>${hotshopVO.title}</p>
													<a href="${pageContext.request.contextPath}/shop/index_front.jsp?shopno=${hotshopVO.shopno}" 
													class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>GO!!</a>
												</div>
											</div>
										</div>
									</div>
								</c:forEach>
									<!--商店初始輪播1-3結束-->
								</div>
								<c:if test="${hotShopListSize>3}">
								<div class="item" style="padding-left:0;">	
									<!--商店初始輪播剩下開始-->
									<c:forEach var="hotcaseVO" items="${hotShopList}" begin="3" end="11" >
									<div class="col-sm-4">
										<div class="product-image-wrapper">
											<div class="single-products">
												<div class="productinfo text-center">
													<img width="251px" height="251px" src="${pageContext.request.contextPath}/HotShopImage.do?shopno=${hotshopVO.shopno}" alt="${hotshopVO.title}" />
													<p>${hotshopVO.title}</p>
													<a href="${pageContext.request.contextPath}/shop/index_front.jsp?shopno=${hotshopVO.shopno}" 
													class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>GO!!</a>
												</div>
											</div>
										</div>
									</div>
									</c:forEach>
									<!--商店初始輪播剩下結束-->
								</div>
								</c:if>
							</div>
							<c:if test="${hotShopListSize>3}">
							 <a class="left recommended-item-control" href="#recommended-item-carousel2" data-slide="prev">
								<i class="fa fa-angle-left"></i>
							  </a>
							  <a class="right recommended-item-control" href="#recommended-item-carousel2" data-slide="next">
								<i class="fa fa-angle-right"></i>
							  </a>
							  </c:if>			
						</div>
					</div><!--/recommended_items-->
 			</div>
		</div>
	</div>
</section>


	
<jsp:include page="/f/frag/f_footer1.jsp"/>    		



<jsp:include page="${contextPath}/f/frag/f_footer2.jsp"/>    				
