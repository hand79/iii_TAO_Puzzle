<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<% if(request.getAttribute("i_am_admin") == null){%>
<%@ include file="/f/frag/f_login_redirect.file" %>
<%} %>

<%@ page import="com.tao.shopRep.model.*"%>
<%@ page import="com.tao.jimmy.member.*"%>
<%@ page import="com.tao.order.model.OrderVO"%>
<%@ page import="com.tao.cases.model.CasesVO"%>
<jsp:useBean id="categorySvc" scope="page" class="com.tao.category.model.CategoryService" />
<jsp:useBean id="locSvc" scope="page" class="com.tao.location.model.LocationService" />
<jsp:useBean id="subCategorySvc" scope="page" class="com.tao.category.model.SubCategoryService" />
<jsp:useBean id="shopVO" scope="request" type="com.tao.shop.model.ShopVO"/>

<%
    pageContext.setAttribute("imagePage", request.getContextPath() + "/shopproduct");
	java.util.Set<TinyMemberVO> tinyMemSet = new TinyMemberService().getAll();
    pageContext.setAttribute("tinyMemSet", tinyMemSet);
    ShopRepVO shopRepVO = (ShopRepVO) request.getAttribute("shopRepVO");

    //�I���v�p��
	java.util.Map<Integer, Integer> hitsMap = (java.util.Map<Integer, Integer>) application.getAttribute("ShopHitsMap");
	Integer hits = hitsMap.get(shopVO.getShopno());
	if(hits == null){
		hits = 0; 
	}
	hitsMap.put(shopVO.getShopno(), ++hits);
	    
%>
<c:set var="locList" value="${locSvc.all}" />
<jsp:include page="/f/frag/f_header1.jsp"/>
<title>�w���ө� | Ź���a��</title>
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
	font-family:'Roboto', sans-serif,�L�n������;
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

.prduct-meta li i:after{
	border-color:transparent;
}
.media-meta {
	background: white;
}
#asideTable tr,td{
	text-align: left;
	font-family: sans-serif, "�L�n������";
	padding:3px 7px;
}
.pointer{
cursor: pointer;
}

</style>
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>
<head>
</head>
	<section>
		<div class="container"><!--container-header-->

			<div class="columns" >
					<div class="col-sm-3" >
							<img src="<%=request.getContextPath()%>/shop/shop.do?shopno=${shopVO.shopno}&action=getPic" width="150" /><p/>
					</div>
					<div class="col-sm-9 shopbrief">
						<ul>
							<li class="shoptitle">${shopVO.title}</li>
							<li>
								���a�|���G<a><% for(TinyMemberVO tmemvo: tinyMemSet){ if(tmemvo.getMemno().intValue() == shopVO.getMemno().intValue()){out.write(tmemvo.getMemid());break;}}%></a>�@|�@
								�p���q�ܡG<a>${shopVO.phone}</a>�@|�@
								E-mail�G<a>${shopVO.email}</a>
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
							<h2>�Ӯa����</h2>
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

										<td style="font-size:1.2em;"><b>�n��:<a>0</a>��</b></td>
									</tr>
									<tr>
										<td Class="evaluationScore" rowSpan="2" style="color:red; text-align:center; font-size:20px;"><b>${memberVO.point}</b><br/>��</td>
										<td style="font-size:1.2em;"><b>����:<a>0</a>��</b></td>
									</tr>
									<tr>
										<td style="font-size:1.2em;"><b>�t��:<a>0</a>��</b></td>
									</tr>
								</table><br><br><br>
						</div>

						<div class="brands_products"><!--brands_products-->
							<h2>�����ӫ~</h2>
							<div class="brands-name">
								<ul class="nav nav-pills nav-stacked">
								<c:forEach var="spVO" items="${spVOs}"  >
									<c:if test="${spVO.isrecomm==1}">
										<li><a style="font-size:1em; font-family:�L�n������;"><b><i class="fa fa-star pull-left fa-fw fa-lg"></i>${spVO.name}</b></a></li>	
									</c:if>
								 </c:forEach>
								</ul>
							</div>
						</div><!--/brands_products-->

						
					</div>
				</div>
				
				
					<div class="category-tab shop-details-tab" ><!--category-tab-->
						
						<div class="col-sm-12">
							<ul class="nav nav-tabs shopbar">
								<li class="active"><a href="#details" data-toggle="tab">���󥻩�</a></li>
								<li><a href="#companyprofile" data-toggle="tab">�����ӫ~</a></li>
								<li><a href="#tag" data-toggle="tab">�����X��</a></li>
								<li><a href="#reviews" data-toggle="tab" id="rateTab">�Ӯa����</a></li>
							</ul>
						</div>
						<div class="tab-content">
							<div class="tab-pane fade active in" id="details" >
								<div class="col-sm-12">
									<h2 class="title text-center">�ө����</h2>
								</div>
					            <div class="form-group col-md-12">
					                <h4><b style="color:#FE980F">���s�G</b> ${shopVO.shopno} 
					                <button class="btn btn-defult pull-right" id="i-want-to-report" disabled>���|</button></h4>
					                	<c:forEach var="locVO" items="${locList}">
											<c:if test="${shopVO.locno==locVO.locno}">
						                 		<h4><b style="color:#FE980F">�a�ϡG</b> �i${locVO.county}�j</h4>
						                 		<h4><b style="color:#FE980F">�a�}�G</b> ${locVO.county}${locVO.town}${shopVO.addr}</h4>
						                    </c:if>
								     	</c:forEach>
					                <h4><b style="color:#FE980F">�q�ܡG</b> ${shopVO.phone}</h4>
					                <h4><b style="color:#FE980F">�ǯu�G</b> ${shopVO.fax}</h4>
					                <h4><b style="color:#FE980F">�H�c�G</b> ${shopVO.email}</h4>
					               
					            </div>
					            <div class="col-sm-12">
									<h2 class="title text-center">�ө�����</h2>
								</div>
								<div class="form-group col-md-12">
					                <h4>${shopVO.shop_desc}</h4>
					            </div>
					            <div class="col-sm-12">
									<h2 class="title text-center">�B�O����</h2>
								</div>
								<div class="form-group col-md-12">
									<% if(shopVO.getShip_desc() != null){ %>
					                <h4><%=shopVO.getShip_desc().replace("\n", "<br>") %></h4>
					                <%} %>
					            </div>
					           <div class="col-sm-12">
									<h2 class="title text-center">��L����</h2>
								</div>
								<div class="form-group col-md-12">
									<% if(shopVO.getOther_desc() != null){ %>
					                <h4><%=shopVO.getOther_desc().replace("\n", "<br>") %></h4>
					                 <%} %>
					            </div>
							</div>
							
							<div class="tab-pane fade product-buy" id="companyprofile" >
								<div class="col-sm-12">
									<h2 class="title text-center">���˰ӫ~</h2>
								</div>
								
								<c:forEach var="spVO" items="${spVOs}">
									<c:if test="${spVO.isrecomm==1}">
										<div class="col-sm-3">
											<div class="product-image-wrapper">
												<div class="single-products " >
													<div class="productinfo text-center">
														<a>
															<img src="<%=request.getContextPath()%>/shopproduct/shopproduct.do?spno=${spVO.spno}&action=getPic1" style="weight:180px; height:180px;" />
															<h2>$ ${spVO.unitprice}</h2>
															<h4 style="height:40px;">${spVO.name}</h4>
														</a>
<%-- 														<form METHOD="get" ACTION="<%=request.getContextPath()%>/cases/cases.do"> --%>
														<a><button type="button" class="btn btn-default add-to-cart" disabled><i class="fa fa-shopping-cart"></i>�o�_�X��</button></a>
<%-- 														<input type="hidden"    name="spno" value="${spVO.spno}">  --%>
<!-- 														<input type="hidden" name="action" value="add"> -->
<!-- 														</form> -->
													</div>
												</div>
											</div>
										</div>
									</c:if>
								</c:forEach>
							
								<div class="col-sm-12">
									<h2 class="title text-center">���ӫ~</h2>
								</div>
								<c:forEach var="spVO" items="${spVOs}">
									<c:if test="${spVO.isrecomm==1||spVO.isrecomm==2}">
										<div class="col-sm-3">
											<div class="product-image-wrapper">
												<div class="single-products "  >
													<div class="productinfo text-center">
														<a>
															<img src="<%=request.getContextPath()%>/shopproduct/shopproduct.do?spno=${spVO.spno}&action=getPic1" style="weight:180px; height:180px;"/>
															<h2>$ ${spVO.unitprice}</h2>
															<h4 style="height:40px;">${spVO.name}</h4>
														</a>
<%-- 														<form METHOD="get" ACTION="<%=request.getContextPath()%>/cases/cases.do"> --%>
														<a><button type="button" class="btn btn-default add-to-cart" disabled><i class="fa fa-shopping-cart"></i>�o�_�X��</button></a>
<%-- 														<input type="hidden"    name="spno" value="${spVO.spno}">  --%>
<!-- 														<input type="hidden" name="action" value="add"> -->
<!-- 														</form> -->
													</div>
												</div>
											</div>
										</div>
									</c:if>
								</c:forEach>
							</div>
							
							<div class="tab-pane fade join-buy" id="tag" >
								<div class="col-sm-12">
									<div class="product-image-wrapper" >
				
									<c:forEach var="casesVO" items="${casesVOList}">
										<c:set var="casesVO" value="${casesVO }"/>
										<% CasesVO cvo = (CasesVO)pageContext.getAttribute("casesVO"); %>
										<div class="">
											<ul class="media-list"  style="border:1px solid lightgrey">
												<li class="media media-meta">
													<div class="media-body prduct-meta" style="padding-bottom:20px;">
														<ul class="sinlge-post-meta " style ="background: WHITE;border-bottom:0px; ">
															<li style="cursor: default; text-transform: none;"><i class="fa fa-user"> �D�ʡG</i><% for(TinyMemberVO tmemvo: tinyMemSet){ if(tmemvo.getMemno().intValue() == cvo.getMemno().intValue()){out.write(tmemvo.getMemid());break;}}%></li>
															<li><i class="fa fa-location-arrow"> �a�ϡG</i>
																<c:forEach var="locVO" items="${locList}">
																	<c:if test="${casesVO.locno==locVO.locno}">
																		${locVO.county} > ${locVO.town}
																	</c:if>
																</c:forEach>
															</li>
															<li><i class="fa fa-clock-o"> �}�ήɶ��G</i> ${casesVO.formatedStime}</li>
															<li><i class="fa fa-clock-o"> ���ήɶ��G</i> ${casesVO.formatedEtime}</li>
														</ul>
														<ul  style ="background: WHITE;border-bottom:0px;">
															<li>
															<c:forEach var="spVO" items="${spVOs}">
																	<c:if test="${spVO.spno == casesVO.spno}">
																<div class="col-sm-2">
																	<img class="media-object" src="<%=request.getContextPath()%>/shopproduct/shopproduct.do?spno=${spVO.spno}&action=getPic1" width="75" height="65" alt="">
																</div>
																<div class="col-sm-5" style="font-weight: bolder;">
																	�ӫ~�W��:<p style="font-size:22px;">${casesVO.title}</p>
																</div>
																
																		<div class="col-sm-2" style="font-weight: bolder;">���:<p style="font-size:22px;color:black;">${spVO.unitprice}</p></div>
																		<div class="col-sm-1" style="font-weight: bolder;">���:<p style="font-size:22px;color:#FE980F;">${spVO.unitprice - casesVO.discount}</p></div>
																	</c:if>
																</c:forEach>
																<div class="col-sm-1" style="vertical-align: bottom;">
																	<a style="margin-left: 20px;"><button type="button" class="btn btn-default add-to-cart" style="margin-bottom: 0px;" disabled><i class="fa fa-users"></i>�e���X��</button></a>
																</div>
															</li>
														</ul>
													</div>
												</li>
											</ul>
										</div>
									</c:forEach>	
										
									</div>
								</div>
								</div>
							<div class="tab-pane fade " id="reviews" >
								<div class="col-sm-12">							
									<div class="commtotal">
										<ul>
<%-- 											<c:forEach var="orderVO" items="${orderList}"> --%>
<%-- 												<c:if test="${orderVO.crate==2}"> --%>
<%-- 													<c:set var="Goodcrate" value="${Goodcrate+1}" /> --%>
<%-- 												</c:if> --%>
<%-- 												<c:if test="${orderVO.crate==1}"> --%>
<%-- 													<c:set var="Normalcrate" value="${Normalcrate+1}" /> --%>
<%-- 												</c:if> --%>
<%-- 												<c:if test="${orderVO.crate==0}"> --%>
<%-- 													<c:set var="BadCount" value="${BadCount+1}" /> --%>
<%-- 												</c:if> --%>
<%-- 											</c:forEach> --%>
											<li><b><a style="cursor: default;">�@�n�� (0)</a></b></li>�@�U �@
											<li><b><a style="cursor: default;">�@���� (0)</a></b></li>�@�U 
											<li><b><a style="cursor: default;">�@�t�� (0)</a></b></li>�@
										</ul>
									</div>
								
							<c:forEach var="orderVO" items="${orderList}" >
								<c:set var="orderVO" value="${orderVO}"/>
								<% OrderVO ovo = (OrderVO) pageContext.getAttribute("orderVO"); %>
									<div class="command">
										<ul>
											<li ><a style="text-transform:none;"><i class="fa fa-user"></i><% for(TinyMemberVO tmemvo: tinyMemSet){ if(tmemvo.getMemno().intValue() == ovo.getCmemno().intValue()){out.write(tmemvo.getMemid());break;}}%></a></li>
											<li><a style="cursor: default;"><i class="fa fa-clock-o"></i>${orderVO.formatedOrdtime}</a></li>
											<c:if test="${orderVO.crate==2}">
												���N�סG<i class="fa fa-smile-o fa-2x" ></i> �n��
											</c:if>
											<c:if test="${orderVO.crate==1}">
												���N�סG<i class="fa fa-meh-o fa-2x"></i> ����
											</c:if>
											<c:if test="${orderVO.crate==0}">
												���N�סG<i class="fa fa-frown-o fa-2x"></i> �t��
											</c:if>
										</ul>
										<p>�ʶR�ӫ~�G
											<c:forEach var="casesVO" items="${overedCasesVOList}">
												<c:if test="${orderVO.caseno == casesVO.caseno}">
													${casesVO.title}
												</c:if>
											</c:forEach>
										</p>
										<p>�����G${orderVO.crateDesc}</p>
									</div>
									<br>
							</c:forEach>
								</div>
							</div>
							
						</div>
					</div><!--/category-tab-->
				</div>
			</div>
	</section>


<jsp:include page="/f/frag/f_footer1.jsp"/>
<script>
console.log('adasdas');
	$(function() {
		$('#i-want-to-report').click(function(){
			if($("#repno").val() == ""){
				$('#loginModal').modal(); 
			}else{
				$('#shoprep').modal();
			}
			return false;
		});
		
		$('#confirm').on('show.bs.modal', function(){
			$('#shoprep').fadeOut(200);
		});
		$('#confirm').on('hidden.bs.modal', function(){
			$('#shoprep').fadeIn(100);
		});
		$('#send-rep').click(function(){
			 if($("#repno").val() != ""){
				$('#rep-form').submit();
				return false; 
			}
			 return true;     
		});
		$("#rep-confirm").click(function(){
		    if($("#sreprsn").val() == "") {
		      $("#sreprsn").focus();
		      alert("���e�S��g");
		      return false; 
		    }
		      else if($("#sreprsn").val() != ""){
		    	$('#confirm').modal();
		    	return false; 
		    }
		 	return true;      
		});
	});
	var toRateTab = function(){
		$('#rateTab').click();
	}
</script>
<c:if test="${not empty param.tab }">
	<script>
		toRateTab();
	</script>
</c:if>


<jsp:include page="/f/frag/f_footer2.jsp"/>