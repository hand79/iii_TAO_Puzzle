<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>�ө� | Ź���a��</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/shop/css/index_front.css"/>

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
								���a�|���G<a href="<%=request.getContextPath() %>/SurfMemberServlet.do?action=memberView&memno=${shopVO.memno}"><% for(TinyMemberVO tmemvo: tinyMemSet){ if(tmemvo.getMemno().intValue() == shopVO.getMemno().intValue()){out.write(tmemvo.getMemid());break;}}%></a>�@|�@
								�p���q�ܡG<a style="cursor: default;">${shopVO.phone}</a>�@|�@
								E-mail�G<a href="mailto:${shopVO.email}">${shopVO.email}</a>
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

										<td style="font-size:1.2em;"><b>�n��:<a class="pointer" onclick="toRateTab()">${Goodcrate}</a> ��</b></td>
									</tr>
									<tr>
										<td Class="evaluationScore" rowSpan="2" style="color:red; text-align:center; font-size:20px;"><b>${memberVO.point}</b><br/>��</td>
										<td style="font-size:1.2em;"><b>����:<a class="pointer" onclick="toRateTab()">${Normalcrate}</a> ��</b></td>
									</tr>
									<tr>
										<td style="font-size:1.2em;"><b>�t��:<a class="pointer" onclick="toRateTab()">${BadCount}</a> ��</b></td>
									</tr>
								</table><br><br><br>
						</div>

						<div class="brands_products"><!--brands_products-->
							<h2>�����ӫ~</h2>
							<div class="brands-name">
								<ul class="nav nav-pills nav-stacked">
								<c:forEach var="spVO" items="${spVOs}"  >
									<c:if test="${spVO.isrecomm==1}">
										<li><a href="<%=request.getContextPath() %>/shopproduct/shopproduct.do?spno=${spVO.spno}&shopno=${shopVO.shopno}&action=getOne_For_Display" style="font-size:1em; font-family:�L�n������;"><b><i class="fa fa-star pull-left fa-fw fa-lg"></i>${spVO.name}</b></a></li>	
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
					                <button class="btn btn-defult pull-right" id="i-want-to-report">���|</button></h4>
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
														<a href="<%=request.getContextPath()%>/shopproduct/shopproduct.do?spno=${spVO.spno}&shopno=${spVO.shopno}&action=getOne_For_Display"  >
															<img src="<%=request.getContextPath()%>/shopproduct/shopproduct.do?spno=${spVO.spno}&action=getPic1" style="weight:180px; height:180px;" />
															<h2>$ ${spVO.unitprice}</h2>
															<h4 style="height:40px;">${spVO.name}</h4>
														</a>
<%-- 														<form METHOD="get" ACTION="<%=request.getContextPath()%>/cases/cases.do"> --%>
														<a href="<%=request.getContextPath()%>/cases/cases.do?action=add&spno=${spVO.spno}" target="_blank"><button type="button" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>�o�_�X��</button></a>
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
														<a href="<%=request.getContextPath()%>/shopproduct/shopproduct.do?spno=${spVO.spno}&shopno=${spVO.shopno}&action=getOne_For_Display" >
															<img src="<%=request.getContextPath()%>/shopproduct/shopproduct.do?spno=${spVO.spno}&action=getPic1" style="weight:180px; height:180px;"/>
															<h2>$ ${spVO.unitprice}</h2>
															<h4 style="height:40px;">${spVO.name}</h4>
														</a>
<%-- 														<form METHOD="get" ACTION="<%=request.getContextPath()%>/cases/cases.do"> --%>
														<a href="<%=request.getContextPath()%>/cases/cases.do?action=add&spno=${spVO.spno}" target="_blank"><button type="button" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>�o�_�X��</button></a>
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
																	<a href="<%=request.getContextPath()%>/cases/cases.do?action=caseDetail&caseno=${casesVO.caseno}" style="margin-left: 20px;"><button type="button" class="btn btn-default add-to-cart" style="margin-bottom: 0px;"><i class="fa fa-users"></i>�e���X��</button></a>
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
											<li><b><a style="cursor: default;">�@�n�� (${Goodcrate})</a></b></li>�@�U �@
											<li><b><a style="cursor: default;">�@���� (${Normalcrate})</a></b></li>�@�U 
											<li><b><a style="cursor: default;">�@�t�� (${BadCount})</a></b></li>�@
										</ul>
									</div>
								
							<c:forEach var="orderVO" items="${orderList}" >
								<c:set var="orderVO" value="${orderVO}"/>
								<% OrderVO ovo = (OrderVO) pageContext.getAttribute("orderVO"); %>
									<div class="command">
										<ul>
											<li ><a href="<%=request.getContextPath() %>/SurfMemberServlet.do?action=memberView&memno=${orderVO.bmemno}" style="text-transform:none;"><i class="fa fa-user"></i><% for(TinyMemberVO tmemvo: tinyMemSet){ if(tmemvo.getMemno().intValue() == ovo.getBmemno().intValue()){out.write(tmemvo.getMemid());break;}}%></a></li>
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


<!-- ���|�O�c��o�� -->
<div class="modal fade" id="shoprep" role="dialog"
	aria-labelledby="AdModal" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="AdModal" style="color: red">
					<i class="fa fa-ban fa-fw"></i>���|�ө�
				</h4>

			</div>
			<div class="modal-body">
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/shop/shop.do" id="rep-form">
					
					<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller-->
					<input type="hidden" name="action" value="shoprepAdd">
					<input type="hidden" name="shopno" value="${shopVO.shopno}"> 
					<input type="hidden" name="repno"  id="repno" value="${CurrentUser.memno}"> 
					<font style="font-size:1.1em;"><i class="fa fa-check-square fa-fw"></i><b>�п�J���|��]:</b><br/><br/>
					<textarea rows="15" cols="10" style="margin:0px; width: 560px; height: 300px;padding:10; 
					background:#F5F5F5; border:1px solid #ccc; color:black;" name="sreprsn"  id="sreprsn" required="required"><%=(shopRepVO == null) ? "" : shopRepVO.getSreprsn()%></textarea></font>
					<hr/><i class="fa fa-check-square fa-fw"></i>���������
					
			</FORM>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">����</button>
				<input type="button" class="btn btn-danger"  data-dismiss="modal" id="rep-confirm" value="���|"></button>
			</div>
		</div>
	</div>
</div>
<!-- ���|�T�{�O�c��o�� -->
<div class="modal fade" id="confirm" role="dialog"
	aria-labelledby="AdModal" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="AdModal" style="color: red">
					<i class="fa fa-ban fa-fw"></i>���|�ө�
				</h4>
			</div>
				<div class="modal-body">
						�T�w���|<b><font color=red class="delcomsg">${shopVO.title}</font></b>�ө�?
				</div>
				<div class="modal-footer">
					<button class="btn btn-default" data-dismiss="modal">����</button>
					<button class="btn btn-danger" id="send-rep">�T�{</button>
				</div>
		</div>
	</div>
</div>

<jsp:include page="/f/frag/f_footer1.jsp"/>
<script>
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