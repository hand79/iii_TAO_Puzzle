<%@page import="com.tao.member.model.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<%--**** LOGIN REDIRECT ****--%>
<%@include file="/f/frag/f_login_redirect.file" %>
<%--**** LOGIN REDIRECT ****--%>
<%@ page import="java.util.*" %>
<%@ page import="com.tao.jimmy.location.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% pageContext.setAttribute("pageHome", request.getContextPath() + "/cases");%>

<c:set var="hasCvo" value="${not empty cvo}"/>
<c:set var="Add_or_Edit" value="${not empty cvo ? '編輯':'建立'}"/>
<%	
	CountyService countyService = new CountyService();
	Set<CountyVO> counties = countyService.findCounties();
	pageContext.setAttribute("counties", counties);
	MemberVO memvo = (MemberVO)session.getAttribute("CurrentUser");
%>

<jsp:include page="/f/frag/f_header1.jsp"/>

<title>建立合購案 | TAO Puzzle</title>
<STYLE>
	#product-price, #final-price{
		font-size:1.2em;
		margin-top: 5px;
	}
	#casedesc{
		height: 350px;
	}
	.confirm-addCP{
		margin-top: 0px;
	}
	.modal-title {
	font-weight:bold; 
	color: #FE980F; 
	font-family: 微軟正黑體;
	}
	.preview{
		width: 320px;
	}
	#cp-desc{
		height: 200px;
	}
	.modal-open{
	overflow: initial;
	}
	.invalidText{
		border-color: red;
	}
	#main-contact-form{
	font-family: 微軟正黑體;
	}
	.invalid{
		border-color: red;
	}
	#ajaxMsgModal{
		text-align: center;
	}
</STYLE>
<%
request.setAttribute("showBreadcrumb", new Object()); 
LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
map.put("會員中心","");
request.setAttribute("breadcrumbMap", map); 
%>	
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>
	
	
<section>
	<div class="container">
		<div class="row">

<jsp:include page="/f/frag/f_memCenterMenu.jsp" /> 
			
			<div class="col-sm-9">
				<div class="contact-form">
    				<h2 class="title text-center">${Add_or_Edit}合購案</h2>
    				<div class="status alert alert-success" style="display: none"></div>
			    	<form id="main-contact-form" class="contact-form row">
			    		<c:if test="${not empty cvo}">
			    		<div class="form-group col-md-1">
							<label for="case-title">編號</label>
			                <div style="margin-top: 8px;">${cvo.caseno}</div>
			            </div>
			            </c:if>
			            <div class="form-group col-md-${empty cvo ? '12':'11' }">
							<label for="case-title"><i class="fa fa-fw fa-check-square"></i>合購名稱</label>
			                <c:choose>
			                <c:when test="${not empty cvo}">
			               		<input type="text" name="title" class="form-control" required="required" placeholder="合購案名稱" id="case-title" value="${cvo.title}">
			                </c:when>
			                <c:otherwise>
			                	<input type="text" name="title" class="form-control" required="required" placeholder="合購案名稱" id="case-title" value="">
			                </c:otherwise>
			                </c:choose>
			            </div>
			            <div class="form-group col-md-5">
							<label for="case-product" ><i class="fa fa-fw fa-check-square"></i>所要合購的商品</label>
					<c:choose>				
					<c:when test="${spvo != null}">
						<select class="form-control" name="spno" id="case-product" required="required">
							<option value="${spvo.spno}" data-price="${spvo.unitprice}"  selected>S${spvo.spno} - ${spvo.name}</option>
						</select>
					</c:when>
					<c:when test="${CurrentUser.type == 0 }">
							<select class="form-control" name="cpno" id="case-product" required="required">
								<c:if test="${empty cpList}">
									<option value="-1" data-price="">(尚未建立合購商品)</option>
								</c:if>
								<c:if test="${not empty cpList}">
								<c:forEach var="cpvo"  items="${cpList}" varStatus="sts">
								<c:if test="${sts.first}">
									<option value="-1" data-price="">(請選擇商品)</option>
								</c:if>
									<option value="${cpvo.cpno}" data-price="${cpvo.unitprice}" ${(cvo.cpno == cpvo.cpno) ? 'selected':''}>C${cpvo.cpno} - ${cpvo.name}</option>
								</c:forEach>
								</c:if>
							</select>
					</c:when>
					<c:when test="${CurrentUser.type == 1 }">
							<select class="form-control" name="spno" id="case-product" required="required">
								<c:if test="${empty spList}">
									<option value="-1" data-price="">${NoActiveShop != null ? '(尚未有上架中的商店)' : '(上架中商店尚未建立商品)'}</option>
								</c:if>
								<c:if test="${not empty spList}">
								<c:forEach var="spvo"  items="${spList}" varStatus="sts">
								<c:if test="${sts.first}">
									<option value="-1" data-price="">(請選擇商品)</option>
								</c:if>
									<option value="${spvo.spno}" data-price="${spvo.unitprice}" ${(param.spno == spvo.spno) ? 'selected':''} ${(cvo.spno == spvo.spno) ? 'selected':''}>S${spvo.spno} - ${spvo.name}</option>
								</c:forEach>
								</c:if>
							</select>
					</c:when>
					</c:choose>
			            </div>
						<div class="form-group col-md-1 cp-price">
							<label for="product-price">原價</label>
							<div id="product-price">-</div>
						</div>
						<div class="form-group col-md-4 cp-price">
							<label for="discount"><i class="fa fa-fw fa-check-square"></i>折扣金額</label>
							<input type="number" class="form-control" id="discount" name="discount" min="0" placeholder="請先選擇商品" value="${cvo.discount}"/>
						</div>
						<div class="form-group col-md-2 cp-price">
							<label for="final-price">合購價</label>
							<div id="final-price">-</div>
						</div>
						
			            <div class="form-group col-md-3">
							<label for="county"><i class="fa fa-fw fa-check-square"></i>所在地區</label>
			                <select class="form-control" id="county-list" required="required">
									<option value="">-</option>
								<c:forEach var="countyVO" items="${counties}">
									<option value="${countyVO.value}" ${requestScope.countyVO.name == countyVO.name ? 'selected':'' }>${countyVO.name}</option>
								</c:forEach>
							</select>
			            </div>
						<div class="form-group col-md-3">
							<label for="town">&nbsp;</label>
			                <select class="form-control" name="locno" id="town-list" required="required">
								<c:if test="${empty towns}">
								<option value="">-</option>
								</c:if>
								<c:if test="${not empty towns }">
									<jsp:include page="Ajax_findTown.jsp"/>
								</c:if>
							</select>
			            </div>
						<div class="form-group col-md-3">
							<label for="ship1"><i class="fa fa-fw fa-check-square"></i>交貨方式一</label>	
			                <input type="text" name="ship1" class="form-control" id="ship1" required="required" placeholder="例如：面交" value="${cvo.ship1}">
			            </div> 
						<div class="form-group col-md-3">
							<label for="shipcost1"><i class="fa fa-fw fa-check-square"></i>費用一</label>	
			                <input type="number" name="shipcost1" class="form-control" id="shipcost1" min="0" placeholder="若不用運費請輸入0" value="${cvo.shipcost1}">
			            </div> 
			            <div class="form-group col-md-3">
							<label for="minqty"><i class="fa fa-fw fa-check-square"></i>成團門檻數量</label>
			                <input type="number" name="minqty" class="form-control" id="minqty" min="0" max="999" placeholder="若不設門檻請輸入0" value="${cvo.minqty}">
			            </div>   
			            <div class="form-group col-md-3">
							<label for="maxqty"><i class="fa fa-fw fa-check-square"></i>最大合購數量</label>	
			                <input type="number" name="maxqty" class="form-control" id="maxqty" min="0" max="999" placeholder="最大數量需大於0" value="${cvo.maxqty}">
			            </div> 				
						<div class="form-group col-md-3">
							<label for="ship2">交貨方式二 </label>	
			                <input type="text" name="ship2" class="form-control" id="ship2" placeholder="例如：郵寄 " value="${cvo.ship2}">
			            </div> 
						<div class="form-group col-md-3">
							<label for="shipcost2">費用二 </label>	
			                <input type="number" name="shipcost2" class="form-control" id="shipcost2" placeholder="若不用費用請輸入0" value="${cvo.ship2 == null ? '': cvo.shipcost2}">
			            </div> 
						<div class="form-group col-md-12">
							<label for="casedesc"><i class="fa fa-fw fa-check-square"></i>合購說明</label>	
			                <textarea class="form-control" name="casedesc" id="casedesc">${cvo.casedesc}</textarea>
			            </div> 
			            <div class="form-group col-md-12">
			               	<a style="cursor: help;" id="magicBtn"><span style="color: #d43f3a; font-weight: bolder;"><i class="fa fa-fw fa-check-square"></i>為必填</span></a>
							<span class="pull-right"><a href="<%=request.getContextPath() %>/cases/cases.do?action=viewOwnCases" class="btn btn-default" style="margin-right: 20px;" >取消${Add_or_Edit}</a><button class="btn btn-primary" style="margin: 0px;" id="submitForm">${Add_or_Edit}</button></span>
			            </div>
			        </form>
    			</div>
			</div><!--class="col-sm-9"-->
		</div>
	</div>
</section>
<div class="modal fade" id="ajaxMsgModal"  tabindex="-1" role="dialog" aria-labelledby="msgWindow" aria-hidden="true">
	<div class="modal-dialog" style="top:160px; width: 300px; height:200px;">
	    <div class="modal-content">
			 <div class="modal-header" style="display:none">
				<h4 class="modal-title" id="msgWindow"></h4>
			 </div>
			 <div class="modal-body">
			 <p>
				 執行中...&nbsp;&nbsp;<i class="fa fa-spin fa-spinner" style="font-size:2em; color:#FE980F"></i>
			 </p>
			 </div>
	    </div><!-- /.modal-content -->
	</div>
</div><!-- /.modal -->	
	<jsp:include page="/f/frag/f_footer1.jsp"/>
	<script >
	menuTrigger(0);
	var CurrentPageEnv = {
			controller: '${pageHome}/cases.do',
			errltzero:'折扣金額過大',
			errNaN:'折扣格式錯誤',
			discountPlaceholder:'請輸入折扣金額，最小值為0',
			caseno:'${param.caseno}',
			spvo:'${spvo != null || param.spno != null}'
	};
	$(function(){
		$('#magicBtn').click(function(){
			<% if(memvo.getType() == 0){%>
			$('input[name="title"]').val('粵利粵巧克力餅乾牛奶夾心 10入包裝');
			$('#ship1').val('面交含雜費');
			$('#shipcost1').val('5');
			$('#ship2').val('郵寄');
			$('#shipcost2').val('60');
			$('#minqty').val('10');
			$('#maxqty').val('100');
			<%}else{%>
			$('input[name="title"]').val('[限時搶購]YA803第二組慶祝結訓『超級爆漿起士饅頭』跳樓大拍賣1顆只要5塊');
			$('#ship1').val('展示現場面交');
			$('#shipcost1').val('0');
			$('#ship2').val('郵寄');
			$('#shipcost2').val('60');
			$('#minqty').val('20');
			$('#maxqty').val('100');
			$('#casedesc').val(
					'四平手工饅頭...一家每天自產自銷、現做現賣的手工饅頭店\n'+
					'[YA803 班代]強力推薦超~~~~~~~~級爆漿起士饅頭!!\n'+
					'好吃到腳拇指10隻都比讚!!');
			<%}%>
		});
	});
	</script>
	<script src="${pageHome}/js/add_or_edit_case.js"></script>
</body>
</html>