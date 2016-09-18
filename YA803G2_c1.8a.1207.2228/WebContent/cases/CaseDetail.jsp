<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>
<%@page import="com.tao.cases.model.*"%>
<%@page import="com.tao.shopproduct.model.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:useBean id="cvo" type="com.tao.cases.model.CasesVO" scope="request"/>
<jsp:useBean id="catvo" type="com.tao.category.model.CategoryVO" scope="request"/>
<jsp:useBean id="subcatvo" type="com.tao.category.model.SubCategoryVO" scope="request"/>
<%	
	request.setAttribute("showBreadcrumb", new Boolean(true));
	java.util.LinkedHashMap<String, String> map = new java.util.LinkedHashMap<String, String>();
	map.put(catvo.getCatname(), "");
	map.put(subcatvo.getSubcatname(), "");
	request.setAttribute("breadcrumbMap", map);
	
	java.util.Map<Integer, Integer> hitsMap = (java.util.Map<Integer, Integer>) application.getAttribute("CaseHitsMap");
	Integer hits = hitsMap.get(cvo.getCaseno());
	if(hits == null){
		hits = 0; 
	}
	hitsMap.put(cvo.getCaseno(), ++hits);
	boolean caseAvaliable = cvo.getStatus()==CasesVO.STATUS_PRIVATE || cvo.getStatus()==CasesVO.STATUS_PUBLIC;
%>
<c:set var="caseAvaliable" value="<%=caseAvaliable %>"/>
<c:set var="resPath" value="<%=request.getContextPath() +\"/f/res\"%>" />
<c:set var="pageHome" value="<%=request.getContextPath() +\"/cases\"%>" />
<c:set var="picHome" value="<%=request.getContextPath() +\"/caseproduct\"%>"/>
<c:set var="spPicHome" value="<%=request.getContextPath() +\"/shopproduct\"%>"/>
<c:set var="askControllerHome" value="<%=request.getContextPath() +\"/caseqa\"%>"/>
<c:set var="orderControllerHome" value="<%=request.getContextPath() +\"/order\"%>"/>


<%-- Include Header1 --%>
<jsp:include page="/f/frag/f_header1.jsp" />
<link rel="stylesheet" href="${pageHome}/css/casedetail.css"/>

<title>合購詳情 ${cvo.title} | TAO Puzzle</title>


<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp" />

<section>
	<div class="container">
		<div class="row">

			<jsp:include page="/f/frag/f_catMenu.jsp" />
			<div class="col-sm-9 padding-right">
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
									src="${spPicHome}/shopproduct.do?action=getPic1&spno=${spvo.spno}" alt="" />
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
										<c:if test="${not empty spvo.pmime1 }"><img src="${spPicHome }/shopproduct.do?action=getPic1&spno=${spvo.spno}" /></c:if>
										<c:if test="${not empty spvo.pmime2 }"><img src="${spPicHome }/shopproduct.do?action=getPic2&spno=${spvo.spno}" /></c:if>
										<c:if test="${not empty spvo.pmime3 }"><img src="${spPicHome }/shopproduct.do?action=getPic3&spno=${spvo.spno}" /></c:if>
									</c:if>
								</div>
							</div>
						</div>
					</div>
					<div class="col-sm-7" style="font-family: 微軟正黑體;">
						<div class="product-information"
							style="padding-top: 40px; padding-bottom: 5px; padding-left: 40px">
							<!--/product-information-->
							<c:if test="<%= System.currentTimeMillis() - cvo.getStime().getTime() < 1000*60*60*24*2%>">
							<img src="${resPath}/images/product-details/new.jpg"
								class="newarrival" />
							</c:if>
							<!--<div class="newarrival">募集中</div>-->
							<h2>
								<b>${cvo.title}</b>
							</h2>
							<p>
								合購案編號：${cvo.caseno}&nbsp;&nbsp;&nbsp;&nbsp;商品編號：<c:if test="${not empty cpvo}">C${cpvo.cpno}</c:if><c:if test="${not empty spvo}">S${spvo.spno}</c:if>
								&nbsp;&nbsp;&nbsp;&nbsp;原價：NT $${cpvo.unitprice}${spvo.unitprice}
							</p>
							<span><span id="dispPrice">NT $${(spvo != null) ? (spvo.unitprice - cvo.discount):""}${(not empty cpvo)?(cpvo.unitprice - cvo.discount):""}</span>
								<label>數量 :</label> <input type="text" value="1" />
								<c:if test="${CurrentUser.type != 1 }">
								<button type="button" class="btn btn-fefault cart" <%if(caseAvaliable){ %>id="joinCase" <%}else{ %> disabled <%} %>style="font-weight: bold; font-family: 微軟正黑體;">
									加入合購 <i class="fa fa-users"></i>
								</button>
								</c:if>
								<c:if test="${CurrentUser.type == 1 }">
								<button type="button" class="btn btn-fefault cart" disabled style="font-weight: bold; font-family: 微軟正黑體;">
									加入合購 <i class="fa fa-users"></i>
								</button>
								</c:if>
							</span>
							<br/>
		<%-- 加入追蹤按鈕 & 檢舉按鈕 --%>
					    <c:choose>
							<c:when test="${not empty WihsListBtnEnable && caseAvaliable}">
								<button type="button" class="btn btn-success" id="addToFollow" data-caseno="${cvo.caseno}">加入追蹤</button>
							</c:when>
							<c:otherwise>
								<button type="button" class="btn btn-success" disabled>加入追蹤</button>
							</c:otherwise>
						</c:choose>
						 <c:choose>
							<c:when test="${CurrentUser.memno != cvo.memno && caseAvaliable}">
								<button type="button" class="btn btn-danger" id="reportCase" data-caseno="${cvo.caseno}">檢舉</button>
							</c:when>
							<c:otherwise>
								<button type="button" class="btn btn-danger" disabled>檢舉</button>
							</c:otherwise>
						</c:choose>
						<c:if test="${spvo != null}">
							<a href="<%=request.getContextPath()%>/shop/shop.do?shopno=${spvo.shopno}&action=getOne_For_Display"><button type="button" class="btn btn-default" style="margin-left: 8px;"><i class="fa fa-fw fa-gift fa-lg" ></i>前往商店頁面</button></a>
						</c:if>	
		<%-- 加入追蹤按鈕 & 檢舉按鈕 --%>							
							<hr />
							<h3 style="font-family: 微軟正黑體;">
								<b>狀態：</b>
								<%=CasesStatus.getDisplayStatusName(cvo.getStatus())%>
							</h3>
							<h4>
								<b>截止時間：</b>${cvo.formatedEtime}
							</h4>
							<h4>
								<b>目前/成團/上限 數量：</b><b style="color:#5bc0de;" id="currentOrderAmount">${sum}</b> / <b style="color:#5cb85c;">${cvo.minqty}</b> / <b style="color:#d9534f">${cvo.maxqty}</b>
							</h4>
							<p>
								<b>開團時間：</b>${cvo.formatedStime }
							</p>
							<p></p>
							<h5>
								<b>主 購：</b> 
								<c:forEach var="tMemVO" items="${tmemset}">
									<c:if test="${tMemVO.memno == cvo.memno}">
								<a href="<%=request.getContextPath() %>/SurfMemberServlet.do?action=memberView&memno=${tMemVO.memno}"><i class="fa fa-user"></i> 
									${tMemVO.memid} (${tMemVO.point})
								</a>
									</c:if>
								</c:forEach>
							</h5>
							<h5>
								<b>地 區：</b> ${lvo.county}&nbsp;&gt;&nbsp;${lvo.town}
							</h5>
							<h5>
								<b>交貨方式：</b> ${cvo.ship1} ${cvo.shipcost1} 元&nbsp;&nbsp;&nbsp;
								<c:if test="${not empty cvo.ship2}">${cvo.ship2} ${cvo.shipcost2} 元</c:if>
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
							<li><a href="#case-details" data-toggle="tab">合購說明</a></li>
							<li><a href="#reviews" data-toggle="tab">問與答 (${qalist.size()})</a></li>
							<li><a href="#list" data-toggle="tab">參加成員 (${ordlist.size()})</a></li>
						</ul>
					</div>
					<div class="tab-content">
						<div class="tab-pane fade active in" id="details"
							style="padding: 10px 40px 20px 40px">
							<!--<div class="col-sm-1 "></div>-->
							<div class="col-sm-12 ">
								<c:if test="${cpvo!=null}">
									${cpvo.cpdesc}
								</c:if>
								<c:if test="${spvo!=null}">
									<% 
									String pro_desc = ((ShopproductVO) request.getAttribute("spvo")).getPro_desc();
									out.write(pro_desc == null ? "": pro_desc.replace("\n", "<br />"));
									%>
								</c:if>
							</div>
							<!--<div class="col-sm-1 "></div>-->
						</div>
						<div class="tab-pane fade" id="case-details">
							<div class="col-sm-12">
								<pre>${cvo.casedesc}</pre>
							</div>
						</div>
						<div class="tab-pane fade" id="reviews">
							<div class="col-sm-12">
								 <c:choose>
								 <c:when test="${empty qalist}">
								 	<p style="text-align: center; font-size: 1.1em"><i>尚無提問可顯示</i></p>
								 </c:when>
								 <c:otherwise>
								<!--列出問答--><c:forEach var="qavo" items="${qalist}" varStatus="sts">
								<c:set var="qavo" value="${qavo}"/>
								<%CaseQAVO qavo = (CaseQAVO) pageContext.getAttribute("qavo"); %>
								<div>
									<div style="background: #FFE;">
										<ul style="margin: 0 0 10px;">
											<li><a><i class="fa fa-question" style="font-size: 1.1em;"></i> <b>問題${qalist.size() - sts.index}</b></a></li>
											<!--Join--><c:forEach var="tmemVO" items="${tmemset}">
												<c:if test="${tmemVO.memno == qavo.memno}">
											<li><a style="text-transform: none;"><i class="fa fa-user"></i> ${tmemVO.memid} (${tmemVO.point})</a></li>
												</c:if>
											<!--Join--></c:forEach>
											<li><a><i class="fa fa-clock-o"></i>${qavo.formatedQtime}</a></li>
										</ul>
										<p style="margin-left: 50px;"><%=qavo.getQuestion().replace("\n", "<br/>") %></p>
									</div>
									<div>
										<ul style="margin: 0 0 10px;">
											<li><a><i class="fa fa-reply" style="font-size: 1.1em;"></i><b>回答</b></a></li>
										</ul>
										<p style="margin-left: 50px;"><%=qavo.getAnswer().replace("\n", "<br/>") %></p>
										<p style="margin-left: 50px;"><i>(${qavo.formatedAtime})</i></p>
									</div>
								</div>
								<hr/> 
								<!--列出問答--></c:forEach>
								</c:otherwise>
								</c:choose>
								<c:if test="${CurrentUser.type != 1 && CurrentUser.memno != cvo.memno && caseAvaliable}">
								<p><b>提問問題：</b></p>
								<form id="askForm">
									<textarea name="question" style="color:#333; font-size: 1.1em"></textarea>
									<input type="hidden" name="caseno" value="${cvo.caseno}"/>
									<input type="hidden" name="action" value="askQuestion"/>
									<button type="button" class="btn btn-default pull-right" id="submit-ask">送出</button>
								</form>
								</c:if>
							</div>
						</div>
						<div class="tab-pane fade" id="list">
							<div class="col-sm-12">
								<table class="table table-striped text-center"
									style="width: 800px; margin: auto auto 30px auto;">
									<tr>
										<th class="text-center">會員</th>
										<th class="text-center">數量</th>
										<th class="text-center">時間</th>
									</tr>
									<c:choose>
									<c:when test="${empty ordlist}">
									<tr>
										<td colspan="3" style="font-size: 1.1em;"><i>尚無任何訂購紀錄</i></td>
									</tr>
									</c:when>
									<c:otherwise>
									<c:forEach var="ordvo" items="${ordlist}">
									<c:forEach var="tmemvo" items="${tmemset}">
									<c:if test="${tmemvo.memno == ordvo.bmemno}">
									<tr>
										<td><a href="<%=request.getContextPath() %>/SurfMemberServlet.do?action=memberView&memno=${tmemvo.memno}"><i class="fa fa-user"></i> ${tmemvo.memid} (${tmemvo.point})</a></td>
										<td>${ordvo.qty}</td>
										<td>${ordvo.formatedOrdtime}</td>
									</tr>
									</c:if>
									</c:forEach><!-- for each tmemvo -->
									</c:forEach><!-- for each ordvo -->
									</c:otherwise>
									</c:choose>
								</table>
							</div>
						</div>
					</div>
				</div>
				<!--/category-tab-->
			</div>
		</div>
	</div>
</section>
<c:if test="${CurrentUser.memno != cvo.memno && caseAvaliable}">
<div class="modal fade " id="sureToAsk-modal"  tabindex="-1" role="dialog" aria-labelledby="sureToAsk" aria-hidden="true">
	<div class="modal-dialog">
	    <div class="modal-content">
			 <div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="glyphicon glyphicon-remove"></i></button>
				<h4 class="modal-title" id="sureToAsk">
				  提問問題
				</h4>
			 </div>
			 <div class="modal-body" id="resArea">
			 <p style="font-size: 1.5em;">確定提問？</p>
			 </div>
			 <div class="modal-footer">
				<button class="btn btn-default" style="margin-top: 0px;" id="askConfirmed" data-cpno="">
				   確定
				</button>
				<button class="btn btn-success" data-dismiss="modal">
					取消
				</button>
			 </div>
	    </div><!-- /.modal-content -->
	</div>
</div><!-- /.modal -->
</c:if>
<c:if test="${caseAvaliable}">
<div class="modal fade" id="report-modal"  tabindex="-1" role="dialog" aria-labelledby="report-window" aria-hidden="true">
	<div class="modal-dialog">
	    <div class="modal-content">
			 <div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="glyphicon glyphicon-remove"></i></button>
				<h4 class="modal-title" id="report-window">
				  檢舉合購
				</h4>
			 </div>
			 <div class="modal-body" id="resArea">
			 <p style="font-weight: bolder;">請輸入檢舉原因：</p>
			 <textarea id="report-content" style="height: 140px;"></textarea>
			 </div>
			 <div class="modal-footer">
				<button class="btn btn-default" style="margin-top: 0px;" id="report-confirm">
				   確定
				</button>
				<button class="btn btn-success" data-dismiss="modal" data-caseno="">
					取消
				</button>
			 </div>
	    </div><!-- /.modal-content -->
	</div>
</div><!-- /.modal -->

<div class="modal fade" id="orderPopup"  tabindex="-1" role="dialog" aria-labelledby="orderModal" aria-hidden="true">
	<div class="modal-dialog" id="makeOrderModal" style="top:120px;">
	    <div class="modal-content">
			 <div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="glyphicon glyphicon-remove"></i></button>
				<h4 class="modal-title" id="orderModal" style="font-weight: bold; font-size: 2em; font-family: 微軟正黑體;">
				  加入合購
				</h4>
			 </div>
			 <div class="modal-body" id="orderWindow">
			 	<table class="table table-striped">
			 	<thead>
			 		<tr>
			 			<th>合購案</th>
<!-- 			 			<th></th> -->
			 			<th>數量</th>
			 			<th>交貨方式</th>
			 			<th>金額</th>
		 			</tr>
	 			</thead>
			 	<tbody>
					<tr>
						<td style="vertical-align: top;"><img src="" style="height: 85px; height: 85px;">&nbsp;<span>${cvo.title}</span></td>
						<td>
							<select id="orderSelectAmount">				
							</select>
						</td>
						<td >
							<select id="shipment">
							<option value="${cvo.shipcost1}">${cvo.ship1} ${cvo.shipcost1}元</option>
							<c:if test="${not empty cvo.ship2}">
							<option value="${cvo.shipcost2}">${cvo.ship2} ${cvo.shipcost2}元</option>
							</c:if>	
							</select>
						</td>
						<td id="orderComputedPrice"></td>
					</tr>
				</tbody>
				</table>
			 </div>
			 <div class="modal-footer">
				<button class="btn btn-default" data-dismiss="modal">
					取消
				</button>
				<button class="btn btn-fefault cart" style="margin-top: 0px; border-radius:4px" id="orderConfirm">
				   確定
				</button>
			 </div>
	    </div><!-- /.modal-content -->
	</div>
</div><!-- /.modal -->
<div class="modal fade" id="msgModal"  tabindex="-1" role="dialog" aria-labelledby="msgWindow" aria-hidden="true">
	<div class="modal-dialog" id="msgWindow" style="top:160px; width: 500px; height:200px;">
	    <div class="modal-content">
			 <div class="modal-header" style="display: none;">
				<h4 class="modal-title" id="msgWindow" style="font-weight: bold; font-size: 2em; font-family: 微軟正黑體;">
	
				</h4>
			 </div>
			 <div class="modal-body" id="msgModalWindow">
			 <p>
				 執行中...&nbsp;&nbsp;<i class="fa fa-spin fa-spinner" style="font-size:2em; color:#FE980F"></i>
			 </p>
			 </div>
	    </div><!-- /.modal-content -->
	</div>
</div><!-- /.modal -->
<div class="modal fade" id="ajaxMsgModal"  tabindex="-1" role="dialog" aria-labelledby="msgWindow2" aria-hidden="true">
	<div class="modal-dialog" style="top:160px; width: 300px; height:200px;">
	    <div class="modal-content">
			 <div class="modal-header" style="display:none">
				<h4 class="modal-title" id="msgWindow2"></h4>
			 </div>
			 <div class="modal-body" style="text-align: center;">
			 <p>
				 執行中...&nbsp;&nbsp;<i class="fa fa-spin fa-spinner" style="font-size:2em; color:#FE980F"></i>
			 </p>
			 </div>
	    </div><!-- /.modal-content -->
	</div>
</div><!-- /.modal -->	
</c:if>



<%-- Include Footer1 --%>
<jsp:include page="/f/frag/f_footer1.jsp"></jsp:include>
<script>
	var CurrentPageEnv = {
		controller: "${askControllerHome}/caseQA.do",
		process: '執行中...  <i class="fa fa-spinner fa-spin fa-2x"></i>',
		caseController: '${pageHome}/cases.do'
	};
	var OrderEnv = {
		controller: "${orderControllerHome}/order.do",
		caseno: "${cvo.caseno}"
	};
	
</script>
<script src="${pageHome}/js/case_detail.js"></script>
<script src="${pageHome}/js/ask_question.js"></script>
<script src="${pageHome}/js/make_order.js"></script>

<jsp:include page="/f/frag/f_footer2.jsp" />