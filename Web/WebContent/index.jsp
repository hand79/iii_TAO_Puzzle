<%@ page import="com.tao.cathy.util.DateFormater"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.tao.news.model.*"%>
<jsp:useBean id="runAdSvc" class="com.tao.runningad.model.RunningAdService" scope="page" />
<c:set var="runAdList" value="${runAdSvc.allActiveAds}" />
<jsp:useBean id="hotCaseSvc" class="com.tao.hotcasesandshops.model.HotCaseService" />
<jsp:useBean id="hotShopSvc" class="com.tao.hotcasesandshops.model.HotShopService"/>
<c:set var="hotCaseList" value="${hotCaseSvc.getAllByHitsStatus()}" />
<c:set var="hotCaseListSize" value="${hotCaseList.size()}" />
<c:set var="hotShopList" value="${hotShopSvc.getAllByHitsStatus()}" />
<c:set var="hotShopListSize" value="${hotShopList.size()}" />
<%
	String path=request.getContextPath()+"/f/res";
	pageContext.setAttribute("res", path);
%>
<%
	NewsService newsSvc = new NewsService();
	List<NewsVO> list = newsSvc.getAll();
	pageContext.setAttribute("list", list);
%>
<jsp:include page="/f/frag/f_header1.jsp"/>
    <title>Home | 饕飽拼圖</title>

	
	<STYLE>
		/*----------- 最新消息燈箱-------------*/
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
		.index-tag li a {
			padding: 8px 44px;
			background-color:#F0F0E9;
			color:#363432;
		}
		.newslist a {
			color:#363432;
		}
		.alsidetag {
			font-size: 20px;
			font-family: 微軟正黑體;
		}
	</STYLE>
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>
	
	<section id="slider"><!--slider-->
		<div class="container">
			<div class="row">
				<!--廣告banner開始-->
				<div class="col-sm-12">
					<div id="slider-carousel" class="carousel slide" data-ride="carousel">
						<div class="carousel-inner" style="align-content:center;">
						
							<!--預設廣告檔案開始-->
							<div class="item active">
								<div class="col-sm-12">
									<img src="<%=request.getContextPath()%>/f/res/images/home/tao_puzzle.png" class="img-responsive" style="width:900;height:400px;" alt="" />
								</div>
							</div>							
							<!-- 預設廣告檔案結束 -->
							
							<!--其他廣告檔案開始-->
							<c:forEach var="runAd" items="${runAdList}" varStatus="runIndex">
							
								<div class="item">
									<div class="col-sm-12">
										<a href="<%=request.getContextPath()%>/cases/cases.do?action=caseDetail&caseno=${runAd.caseno}">
											<img src="<%=request.getContextPath()%>/DBRunningAdImgReader?adno=${runAd.adno}" 
											class="img-responsive" style="width:900px;height:400px;" alt="" />
										</a>
									</div>
								</div>
								
							</c:forEach>

							<!--其他廣告檔案結束-->														
							
						</div>
						
						<a href="#slider-carousel" class="left control-carousel hidden-xs" data-slide="prev">
							<i class="fa fa-angle-left"></i>
						</a>
						<a href="#slider-carousel" class="right control-carousel hidden-xs" data-slide="next">
							<i class="fa fa-angle-right"></i>
						</a>
					</div>
					
				</div>
				<!--廣告banner結束-->
			</div>
		</div>
	</section><!--/slider-->
	
	<section>
		<div class="container">
			<div class="row">
				<jsp:include page="/f/frag/f_catMenu.jsp"/>
				
				<div class="col-sm-9 padding-right">
					<div><!--最新消息-->
						<h2 class="title text-center">最新消息</h2>
						<div class="row">
							<div class="col-sm-12"  style="margin-bottom: 16px;">
								<div class="list-zone">
									<table class="table table-hover table-condensed newslist" style="font-size: 1em; font-family:微軟正黑體; margin-bottom: 6px;">
<!-- 										<tr class="list-header info"> -->
<!-- 											<th><i class="fa fa-list"></i></th> -->
<!-- 											<th>標題</th> -->
<!-- 											<th  class="text-center">發佈時間</th> -->
<!-- 										</tr> -->
										<c:forEach var="newsVO" items="${list}" begin="0" end="4">
											<tr class="mainrow" id="news" style="cursor: pointer;" data-toggle="modal" data-target="#detail-modal${newsVO.newsno}" >										
												<td><i class="fa fa-chevron-circle-right"></i></td>
												<td>${newsVO.title}</td>
												<td class="text-center">${newsVO.formatPubtime}</td>
											</tr>
										</c:forEach>
									</table>	
									<a href="<%=request.getContextPath() %>/news/news_more.jsp" class="pull-right">more...</a>
								</div><!--class="list-zone"-->
							</div>							
						</div>
					</div>

				
					
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
												<a href="${pageContext.request.contextPath}/cases/cases.do?action=caseDetail&caseno=${hotcaseVO.caseno}" >
												 	<c:if test="${hotcaseVO.spno == 0}">
													<img width="251px" height="251px" src="${pageContext.request.contextPath}/HotCaseImage.do?caseno=${hotcaseVO.caseno}" alt="${hotcaseVO.title}" />
													</c:if>
												 	<c:if test="${hotcaseVO.cpno == 0}">
													<img width="251px" height="251px" src="${pageContext.request.contextPath}/shopproduct/shopproduct.do?action=getPic1&spno=${hotcaseVO.spno}" alt="${hotcaseVO.title}" />
													</c:if>
												</a>
													<p style="height:30px;">${hotcaseVO.title}</p>
													<a href="${pageContext.request.contextPath}/cases/cases.do?action=caseDetail&caseno=${hotcaseVO.caseno}"  
											 		class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>前往合購</a>
												</div>
												
											</div>
										</div>
									</div>
								</c:forEach>	
									<!--合購初始輪播1-3結束-->	
								</div>
								<c:if test="${hotCaseListSize>3}">
									<div class="item">	
										<c:forEach var="hotcaseVO" items="${hotCaseList}" begin="3" end="5">
											<div class="col-sm-4">
												<div class="product-image-wrapper">
													<div class="single-products">
														<div class="productinfo text-center">
														<a href="${pageContext.request.contextPath}/cases/cases.do?action=caseDetail&caseno=${hotcaseVO.caseno}" > 
															<c:if test="${hotcaseVO.spno == 0}">
													<img width="251px" height="251px" src="${pageContext.request.contextPath}/HotCaseImage.do?caseno=${hotcaseVO.caseno}" alt="${hotcaseVO.title}" />
													</c:if>
												 	<c:if test="${hotcaseVO.cpno == 0}">
													<img width="251px" height="251px" src="${pageContext.request.contextPath}/shopproduct/shopproduct.do?action=getPic1&spno=${hotcaseVO.spno}" alt="${hotcaseVO.title}" />
													</c:if>
														</a>	
															<p style="height:30px;">${hotcaseVO.title}</p>
															<a href="${pageContext.request.contextPath}/cases/cases.do?action=caseDetail&caseno=${hotcaseVO.caseno}" 
															class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>前往商店</a>
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
												<a href="<%=request.getContextPath()%>/shop/shop.do?shopno=${hotshopVO.shopno}&action=getOne_For_Display" >

													<img width="251px" height="251px" src="${pageContext.request.contextPath}/HotShopImage.do?shopno=${hotshopVO.shopno}" alt="${hotshopVO.title}" />										
												</a>	
													<p style="height:30px;">${hotshopVO.title}</p>
													<a href="<%=request.getContextPath()%>/shop/shop.do?shopno=${hotshopVO.shopno}&action=getOne_For_Display" 
													
													class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>前往商店</a>
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
									<c:forEach var="hotshopVO" items="${hotShopList}" begin="3" end="5" >
									<div class="col-sm-4">
										<div class="product-image-wrapper">
											<div class="single-products">
												<div class="productinfo text-center">
												<a href="<%=request.getContextPath()%>/shop/shop.do?shopno=${hotshopVO.shopno}&action=getOne_For_Display" >
													<img width="251px" height="251px" src="${pageContext.request.contextPath}/HotShopImage.do?shopno=${hotshopVO.shopno}" alt="${hotshopVO.title}" />
												</a>	
													<p style="height:30px;">${hotshopVO.title}</p>
													<a href="<%=request.getContextPath()%>/shop/shop.do?shopno=${hotshopVO.shopno}&action=getOne_For_Display" 
													class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>前往合購</a>
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
	
	
	<c:forEach var="newsVO" items="${list}" begin="0" end="4">
	<div class="modal fade" id="detail-modal${newsVO.newsno}"  tabindex="-1" role="dialog" aria-labelledby="view-detail" aria-hidden="true" >
		<div class="modal-dialog">
		    <div class="modal-content">
			 <div class="modal-header">		
				<h4 class="modal-title title" id="view-detail"><i class="fa fa-bullhorn"></i>
				  ${newsVO.title}
				</h4>
			 </div>
			 <div class="modal-body">
				<p>
					<i class="fa fa-file-text-o"></i><b> 內容</b>
					<br>${newsVO.text}
					
			 </div>
			 <div class="modal-footer">
				<div class="text-left">
				<p>
					<i class="fa fa-calendar"></i><b> 發佈時間</b>
					<br>${newsVO.formatPubtime}
					
			 </div>
				<button type="button" class="btn btn-default" data-dismiss="modal">
				   確定
				</button>
			 </div>
		    </div><!-- /.modal-content -->
		</div>
	</div>
	</c:forEach>
	<jsp:include page="/f/frag/f_footer1.jsp"/>
		
</body>
</html>