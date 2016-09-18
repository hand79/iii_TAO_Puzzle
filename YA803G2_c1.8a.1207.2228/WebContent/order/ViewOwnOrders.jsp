<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<%--**** LOGIN REDIRECT ****--%>
<%@include file="/f/frag/f_login_redirect.file" %>
<%--**** LOGIN REDIRECT ****--%>
<%@ page import="com.tao.order.model.*"%>
<%@ page import="java.util.LinkedHashMap" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% pageContext.setAttribute("pageHome", request.getContextPath() + "/order"); %>
<% pageContext.setAttribute("casesController", request.getContextPath() + "/cases/cases.do"); %>
    
<jsp:include page="/f/frag/f_header1.jsp"/>
<title>參加的合購 | TAO Puzzle</title>	
  <STYLE>
    body { 
    	background-color:white; 
    } 
	.modal-title {
		font-weight:bold; 
		color: #FE980F; 
		font-family: 微軟正黑體;
	}
	#detail-modal .modal-dialog{
		width: 20%;
	}
	#cancel-modal .modal-dialog{
		top: 100px;
	}
	.modal-body span, .modal-body h4, .modal-body p{
		font-family: 微軟正黑體;
	}
	.modal-body p span {
		font-size: 1.2em;
	}
	.modal-body p .total {
		color:#FE980F; 
	}
	.modal-body div span{
		margin-right: 10px;
	}
	.modal-open{
		overflow: initial;
	}
	.viewOrdDetail{
		font-weight: bolder;
		text-decoration: underline;
	}
	.pointer{
		cursor: pointer;
	}
	#ajaxMsgModal .modal-body{
		text-align: center;
	}
  </STYLE>
<%
request.setAttribute("showBreadcrumb", new Object()); 
java.util.LinkedHashMap<String, String> map = new java.util.LinkedHashMap<String, String>();
map.put("會員中心","");
request.setAttribute("breadcrumbMap", map); 
%>
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>
	
	<section>
		<div class="container">
			<div class="row">

<jsp:include page="/f/frag/f_memCenterMenu.jsp"/>
				<div class="col-sm-9" >
					<h2 class="title text-center">參加的合購</h2>
					<div class="list-zone">
						<table class="table table-hover table-condensed table-striped" style="font-size: 1em; font-family:微軟正黑體;">
							<tr class="list-header info">
								<th>訂單編號</th>
								<th>合購案</th>
								<th>下單時間</th>
								<th>評價</th>
								<th>金額</th>
								<th>狀態</th>
								<th>操作</th>
								<th>糾紛提報</th>
							</tr>
					<c:if test="${empty ordList}">
							<tr>
								<td colspan="8" style="text-align: center"><i>無訂單可顯示</i></td>
							</tr>
					</c:if>
					<c:if test="${not empty ordList}">
						<c:forEach var="ordvo" items="${ordList }" varStatus="sts">
							<c:set var="ordvo" value="${ordvo}"/>
							<% OrderVO ordvo = (OrderVO) pageContext.getAttribute("ordvo"); %>
							<tr data-ordno="${ordvo.ordno}">
								<td>${ordvo.ordno}</td><!-- 訂單編號 -->
								<td class="pointer viewOrdDetail">
							<c:forEach var="cvo" items="${casesSet}">
								<c:if test="${cvo.caseno == ordvo.caseno}">${cvo.shortenedTitle}</c:if><!-- 合購名稱 -->
							</c:forEach>
								</td>
								<td><i class="fa fa-clock-o"></i> ${ordvo.formatedOrdtime}</td><!-- 下單時間 -->
								<td>
								<c:if test="${ordvo.brate == null}">
									<c:if test="${ordvo.status != 0}">
									<i>未獲得</i>
									</c:if>
									<c:if test="${ordvo.status == 0}">
									-
									</c:if>
								</c:if>
								<c:if test="${ordvo.brate != null}">
									<%=OrderRate.getRate(ordvo.getBrate()) %>
								</c:if> / <c:if test="${ordvo.crate == null}">
									<c:if test="${ordvo.status != 0}">
									<a class="pointer give-rate">給評</a>
									</c:if>
									<c:if test="${ordvo.status == 0}">
									-
									</c:if>
								</c:if>
								<c:if test="${ordvo.crate != null}">
									<%=OrderRate.getRate(ordvo.getCrate()) %>
								</c:if></td> <!-- 得到 / 給予 -->
								<td>${ordvo.price}</td><!-- 金額 -->
								<td><%=OrderStatus.getStatusName(ordvo.getStatus()) %></td><!-- 狀態 -->
								<!-- 操作1 & 2 -->
								<c:choose>
								<c:when test="${ordvo.status == 0}">
									<td><a class="pointer cancel-order">退出合購</a></td>
									<td>-</td>
								</c:when>
								<c:when test="${ordvo.status == 1}">
									<td><a class="pointer recieve-product">確認已收貨</a></td>
									<td><a class="pointer report-conflic">提報</a></td>
								</c:when>
								<c:when test="${ordvo.status == 2}">
									<td>-</td>
									<td><a class="pointer report-conflic">提報</a></td>
								</c:when>
								<c:when test="${ordvo.status == 3}">
									<td><a class="pointer recieve-product">確認已收貨</a></td>
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
					</div><!--class="list-zone"-->
				</div><!--class="col-sm-9"-->
			</div>
		</div>
	</section>

<div class="modal fade" id="detail-modal"  tabindex="-1" role="dialog" aria-labelledby="view-detail" aria-hidden="true" >
	<div class="modal-dialog">
	    <div class="modal-content">
		 <div class="modal-header">
			
			<h4 class="modal-title" id="view-detail">
			   合購訂單詳情
			</h4>
		 </div>
		 <div class="modal-body" >
		 	<p>
				<b>訂單編號</b>
				<br>
				<span id="ordDeatil-ordno"></span>
			</p>
			<p>
				<b>合購案名稱</b>
				<br>
				<a href="" id="ordDeatil-title" target="_blank"></a>
			</p>
			<p>
				<b>商品單價</b>
				<br>
				<span id="ordDeatil-unitprice"></span>
			</p>
			<p>
				<b>訂購數量</b>
				<br>
				<span id="ordDeatil-qty"></span>
			</p>
			<p>
				<b>交貨方式</b>
				<br>
				<span id="ordDeatil-ship"></span>
			</p>
			<p>
				<b>運費</b>
				<br>
				<span id="ordDeatil-shipcost"></span>
			</p>
			<p>
				<b>訂單金額 (單價 x 數量+運費=總額)</b>
				<br>
				<span><span class="total" id="ordDeatil-price"></span></span>
			</p>
			
			<p>
				<b>下單時間</b>
				<br>
				<span><i class="fa fa-clock-o"></i></span> <span id="ordDeatil-ordtime"></span>
			</p>
			<p>
				<b>訂單狀態</b>
				<br>
				<span id="ordDeatil-status"></span>
			</p>
		 </div>
		 <div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">
			   確定
			</button>
		 </div>
	    </div><!-- /.modal-content -->
	</div>
</div>
<div class="modal fade" id="rate-modal"  tabindex="-1" role="dialog" aria-labelledby="cancel-order" aria-hidden="true" >
	<div class="modal-dialog">
	    <div class="modal-content">
		 <div class="modal-header">
			<h4 class="modal-title" id="cancel-order">
			   評價主購
			</h4>
		 </div>
		 <div class="modal-body">
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
<div class="modal fade" id="cancel-modal"  tabindex="-1" role="dialog" aria-labelledby="cancel-order" aria-hidden="true" >
	<div class="modal-dialog">
	    <div class="modal-content">
		 <div class="modal-header">
			<h4 class="modal-title" id="cancel-order">
			   退出合購
			</h4>
		 </div>
		 <div class="modal-body">
			<p>退出合購將會取消此筆訂單並且退還訂單金額，</p>
			<p>您確定退出合購？</p>
		 </div>
		 <div class="modal-footer">
			<button class="btn btn-default" data-ordno="" id="cancel-confirm">
			   確定
			</button>
			<button class="btn btn-success" data-dismiss="modal">
			   取消
			</button>
		 </div>
	    </div><!-- /.modal-content -->
	</div>
</div>
<div class="modal fade" id="recieve-modal"  tabindex="-1" role="dialog" aria-labelledby="recieve-good" aria-hidden="true" >
	<div class="modal-dialog">
	    <div class="modal-content">
		 <div class="modal-header">
			<h4 class="modal-title" id="recieve-good">
			   確認收貨
			</h4>
		 </div>
		 <div class="modal-body">
			<p>您已經收到您所合購的商品嗎？</p>
		 </div>
		 <div class="modal-footer">
			<button class="btn btn-default" data-ordno="" id="recieve-confirm">
			   &nbsp;是&nbsp;
			</button>
			<button class="btn btn-success" data-dismiss="modal">
			  &nbsp;否&nbsp;
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
				<button class="btn btn-danger" data-ordno="" id="report-confirm" data-dismiss="modal">
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
				<button class="btn btn-default pull-left" data-ordno="" id="report-insist">
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
<!-- 	<script src="js/jquery.balloon.min.js"></script> -->
	<script>
// 		menuTrigger(0);
		var CurrentPageEnv ={
			controller: '${pageHome}/order.do',
			casesController: '${casesController}',
			errMsg:'<i class=\"fa fa-exclamation-circle\" style=\"font-size:2em; color:#FE980F;\" ></i> 發生錯誤',
			rateErr:'<p><i class=\"fa fa-exclamation-circle\" style=\"font-size:2em; color:#FE980F;\" ></i>&nbsp;&nbsp;請選擇評價</p>',
			descErr:'<p><i class=\"fa fa-exclamation-circle\" style=\"font-size:2em; color:#FE980F;\" ></i>&nbsp;&nbsp;請輸入內容</p>'
		};
	</script>
	<script src="${pageHome}/js/view_own_orders.js"></script>
	
</body>
</html>