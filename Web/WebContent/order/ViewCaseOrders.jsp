<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<%--**** LOGIN REDIRECT ****--%>
<%@include file="/f/frag/f_login_redirect.file" %>
<%--**** LOGIN REDIRECT ****--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.tao.order.model.*" %>
<jsp:useBean id="tmemSvc" class="com.tao.jimmy.member.TinyMemberService"/>
<c:set var="tmemset" value="${tmemSvc.all}"/>
<% pageContext.setAttribute("pageHome", request.getContextPath() + "/order"); %>
<% pageContext.setAttribute("resPath", request.getContextPath() + "/f/res"); %>
<% pageContext.setAttribute("memInfoPage", request.getContextPath() + ""); %>
<%
request.setAttribute("showBreadcrumb", new Object()); 
java.util.LinkedHashMap<String, String> map = new java.util.LinkedHashMap<String, String>();
map.put("會員中心","");
request.setAttribute("breadcrumbMap", map); 
%>
<jsp:include page="/f/frag/f_header1.jsp"/>
<title>訂單管理 | TAO Puzzle</title>
<style>
	.qa {
		font-family: 微軟正黑體;
		font-size: 0.45em; 
		margin-left: 20px;
		margin-bottom: 10px; 
		width: 800px;
	}
	.modal-title {
		font-weight:bold; 
		color: #FE980F; 
		font-family: 微軟正黑體;
	}
	.tableHeader {
		font-weight: bold;
	}
	.modal-body p input {
		margin-left: 10px;
	}
	.modal-open{
		overflow: initial;
	}
	.pointer{
		cursor: pointer;
	}
	
	#ajaxMsgModal .modal-body{
		text-align: center;
	}
</style>

<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>

<section>
	<div class="container">
		<div class="row">
<jsp:include page="/f/frag/f_memCenterMenu.jsp"/>
			<div class="col-sm-9">
				<h2 class="title text-center">訂單管理</h2>
				<div class="list-zone">
					<div class="panel-group category-products" id="case-list"><!--category-productsr-->
					<c:if test="${empty caseMap}">
						<p>尚未有任何訂單</p>
					</c:if>
					<c:forEach var="entry" items="${caseMap}" varStatus="sts">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" data-parent="#case-list" href="#case${entry.key.caseno}">
										<span class="badge pull-right">(${entry.value.size()})</span>
										<i class="fa fa-list fa-fw"></i>&nbsp;(${entry.key.caseno}) ${entry.key.title}
									</a>
								</h4>
							</div>
							<div id="case${entry.key.caseno}" class="panel-collapse collapse">
								<div class="panel-body">
									<table class="table table-striped qa">
						<c:if test="${empty entry.value}">
										<tr>
											<td>尚未有任何訂單</td>
										</tr>
						</c:if>
						<c:if test="${not empty entry.value}">
										<tr class="tableHeader">
											<td>訂單編號</td>
											<td>訂購人</td>
											<td>時間</td>
											<td>數量</td>
											<td id="rate-bubble">評價</td>
											<td>交貨</td>
											<td>金額</td>
											<td>狀態</td>
											<td>交貨確認</td>
											<td>提報糾紛</td>
										</tr>
							<c:forEach var="ordvo" items="${entry.value}">
							<c:set var="ordvo" value="${ordvo}"/>
							<%OrderVO ordvo = (OrderVO) pageContext.getAttribute("ordvo"); %>
										<tr data-ordno="${ordvo.ordno}" data-caseno="${entry.key.caseno}">
											<td>${ordvo.ordno}</td>
											<td class="mem-info">
											<c:forEach var="tmemvo" items="${tmemset}">
											<c:if test="${tmemvo.memno == ordvo.bmemno}">
											<a href="<%=request.getContextPath() %>/SurfMemberServlet.do?action=memberView&memno=${tmemvo.memno}" class="member"><i class="fa fa-user"></i> ${tmemvo.memid}(${tmemvo.point})</a>
											</c:if>
											</c:forEach>
											</td>
											<td>${ordvo.formatedOrdtime}</td>
											<td>${ordvo.qty}</td>
											<td>
											<c:if test="${ordvo.crate == null}">
												<c:if test="${ordvo.status != 0}">
												<i>未獲得</i>
												</c:if>
												<c:if test="${ordvo.status == 0}">
												-
												</c:if>
											</c:if>
											<c:if test="${ordvo.crate != null}">
												<%=OrderRate.getRate(ordvo.getCrate()) %>
											</c:if> / <c:if test="${ordvo.brate == null}">
												<c:if test="${ordvo.status != 0}">
												<a class="pointer give-rate">給評</a>
												</c:if>
												<c:if test="${ordvo.status == 0}">
												-
												</c:if>
											</c:if>
											<c:if test="${ordvo.brate != null}">
												<%=OrderRate.getRate(ordvo.getBrate()) %>
											</c:if>
											</td>
											<td>
											<c:if test="${ordvo.ship == 1}">
											${entry.key.ship1}
											</c:if>
											<c:if test="${ordvo.ship == 2 }">
											${entry.key.ship2}
											</c:if>
											</td>
											<td>${ordvo.price}</td>
											<td><%=OrderStatus.getStatusName(ordvo.getStatus())%></td>
											<c:choose>
											<c:when test="${ordvo.status == 0}">
												<td>-</td>
												<td>-</td>
											</c:when>
											<c:when test="${ordvo.status == 1}">
												<td><a class="pointer send-product">已交貨</a></td>
												<td><a class="pointer report-conflic">提報</a></td>
											</c:when>
											<c:when test="${ordvo.status == 2}">
												<td><a class="pointer send-product">已交貨</a></td>
												<td><a class="pointer report-conflic">提報</a></td>
											</c:when>
											<c:when test="${ordvo.status == 3}">
												<td>-</td>
												<td><a class="pointer report-conflic">提報</a></td>
											</c:when>
											<c:when test="${ordvo.status == 6}">
												<td>-</td>
												<td><a class="pointer report-conflic">提報</a></td>
											</c:when>
											<c:otherwise>
												<td>-</td>
												<td>-</td>
											</c:otherwise>
											</c:choose>
										</tr>
							</c:forEach>
						</c:if>
									</table>
								</div>
							</div>
						</div>
					</c:forEach>
					</div>
				</div><!--class="list-zone"-->
			</div><!--class="col-sm-9"-->
		</div><!--class="row"-->
	</div><!--class="container"-->
</section>
<div class="modal fade" id="rate-modal"  tabindex="-1" role="dialog" aria-labelledby="cancel-order" aria-hidden="true" >
	<div class="modal-dialog">
	    <div class="modal-content">
		 <div class="modal-header">
			<h4 class="modal-title" id="cancel-order">
			   評價團員
			</h4>
		 </div>
		 <div class="modal-body">
		 	<p>評價會員：<span id="rate-modal-mem"></span></p>
			<div id="rate-radio-select">
				<span style="font-size: 1.em; font-weight: bolder;">評價：</span>
			  	<span><input type="radio" name="rate" value="2" > 好評</span>
			  	<span><input type="radio" name="rate" value="1" > 普評</span>
			  	<span><input type="radio" name="rate" value="0" > 壞評</span>
			</div>
			<p>
  			<div>
				<p style="font-size: 1.em; font-weight: bolder;">評價內容：</p>
			  	<textarea rows="5" cols="30" id="ratedesc"></textarea>
		   </div>
		 </div>
		 <div class="modal-footer">
			<button class="btn btn-default" data-dismiss="modal">
			   取消
			</button>
			<button class="btn btn-fefault cart" data-ordno="" id="rate-confirm">
			   送出
			</button>
		 </div>
	    </div><!-- /.modal-content -->
	</div>
</div>
<div class="modal fade" id="send-modal"  tabindex="-1" role="dialog" aria-labelledby="send-good" aria-hidden="true" >
	<div class="modal-dialog">
	    <div class="modal-content">
		 <div class="modal-header">
			<h4 class="modal-title" id="send-good">
			   確認交貨
			</h4>
		 </div>
		 <div class="modal-body">
			<p>您已經將商品交給團員了嗎？</p>
		 </div>
		 <div class="modal-footer">
			<button class="btn btn-default" data-ordno="" id="send-confirm">
			   &nbsp;是&nbsp;
			</button>
			<button class="btn btn-success" data-dismiss="modal">
			  &nbsp;否&nbsp;
			</button>
		 </div>
	    </div><!-- /.modal-content -->
	</div>
</div>
<div class="modal fade" id="rate-other"  tabindex="-1" role="dialog" aria-labelledby="rating" aria-hidden="true">
	<div class="modal-dialog">
	    <div class="modal-content" style="">
		 <div class="modal-header">
			
			<h4 class="modal-title" id="rating">
			   給予評價
			</h4>
		 </div>
		 <div class="modal-body">
			<p>評價會員：<a href=""><i class="fa fa-user"></i> <span>emp7001(79)</span></a></p>
			<form role="form">
			
			<p>評價：<input type="radio" name="rate">好評 <input type="radio" name="rate"> 普通<input type="radio" name="rate"> 壞評</p>
			<p>請輸入評價內容 (120字以內)</p>
			<textarea class="answer-content"></textarea>
			</form>
		 </div>
		 <div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">
				取消
			</button>
			<button type="button" class="btn btn-success">
			   送出
			</button>
		 </div>
	    </div><!-- /.modal-content -->
	</div>
</div>
<div class="modal fade" id="report-modal"  tabindex="-1" role="dialog" aria-labelledby="report-order" aria-hidden="true">
	<div class="modal-dialog">
		    <div class="modal-content">
			 <div class="modal-header">
				<h4 class="modal-title" id="report-order">
				  提報糾紛
				</h4>
			 </div>
			 <div class="modal-body">
				<p style="color: #d9534f; font-size: 2.2em;"><i class="fa fa-exclamation-triangle"></i> 注意</p>
				<p>訂單經提報為【糾紛發生】後將會凍結該筆訂單之金額，<br>須等待人工處理完成後才可將金額撥給主購或返還團員。</p>
				<p>是否要提報糾紛？</p>
			 </div>
			 <div class="modal-footer">
				<button class="btn btn-danger" data-ordno="" data-caseno="" id="report-confirm" data-dismiss="modal">
				   確定
				</button>
				<button class="btn btn-success" data-dismiss="modal">
				   取消
				</button>
			 </div>
		    </div><!-- /.modal-content -->
		</div>
</div><!-- /.modal -->
<div class="modal fade" id="report-dbcheck-modal"  tabindex="1" role="dialog" aria-labelledby="report-order-dbcheck" aria-hidden="true">
	<div class="modal-dialog" style="top:80px; width: 300px; height:200px;">
	    <div class="modal-content">
			 <div class="modal-header" style="display:none">
				<h4 class="modal-title" id="report-order-dbcheck"></h4>
			 </div>
			 <div class="modal-body">
			 <p style="font-size: 2.2em;">
				<i class="fa fa-exclamation-triangle" style="color: #d9534f; "></i> 真的要提報嗎？
			 </p>
			 </div>
			 <div class="modal-footer">
				<button class="btn btn-default pull-left" data-ordno="" data-caseno="" id="report-insist">
				   確定
				</button>
				<button class="btn btn-success" data-dismiss="modal">
				   取消
				</button>
			 </div>
	    </div><!-- /.modal-content -->
	</div>
</div><!-- /.modal -->
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

<script src="${resPath}/js/jquery.balloon.min.js"></script>
<script>
	
	/*** triggers ***/
	menuTrigger(0);
	
	$('a[href="#case${param.caseno}"]').trigger('click');
	/*** triggers ***/
	
	$('#rate-bubble').css('cursor','pointer').balloon({
		offsetX: -20,
		contents: '<p>得到的評價 / 給予的評價</p>'
	});
	
	var CurrentPageEnv = {
		controller: '${pageHome}/order.do',
		caseno: '${param.caseno}',
		rateErr:'<p><i class=\"fa fa-exclamation-circle\" style=\"font-size:2em; color:#FE980F;\" ></i>&nbsp;&nbsp;請選擇評價</p>',
		descErr:'<p><i class=\"fa fa-exclamation-circle\" style=\"font-size:2em; color:#FE980F;\" ></i>&nbsp;&nbsp;請輸入內容</p>'
	};
</script>
<script src="${pageHome}/js/view_case_orders.js"></script>
<jsp:include page="/f/frag/f_footer2.jsp"/>