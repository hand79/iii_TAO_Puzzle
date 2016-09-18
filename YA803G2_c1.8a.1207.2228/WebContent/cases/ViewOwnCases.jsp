<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<%--**** LOGIN REDIRECT ****--%>
<%@include file="/f/frag/f_login_redirect.file" %>
<%--**** LOGIN REDIRECT ****--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <%@ page import="java.util.*" %> --%>
<%@ page import="com.tao.cases.model.*" %>
<jsp:useBean id="ordSvc" class="com.tao.order.model.OrderService" scope="request"/>
<% pageContext.setAttribute("pageHome", request.getContextPath() + "/cases"); %>
<% pageContext.setAttribute("resPath", request.getContextPath() + "/f/res"); %>
<% pageContext.setAttribute("caseDetailFullUrl", request.getScheme() + "://" + request.getServerName() + ":"+request.getServerPort() + request.getContextPath() + "/cases/cases.do?action=caseDetail&caseno="); %>
<%-- INCLUDE HEADER1 --%>
<jsp:include page="/f/frag/f_header1.jsp"/>
<%-- INCLUDE HEADER1 --%>
<title>管理合購 | TAO Puzzle</title>
<link rel="stylesheet" href="${resPath}/jquery-ui/jquery-ui.min.css"/>
<link rel="stylesheet" href="${resPath}/jquery-ui/jquery-ui.structure.min.css"/>
<link rel="stylesheet" href="${resPath}/jquery-ui/jquery-ui.theme.min.css"/>
<style>
	.modal-open{
		overflow: initial;
	}
	.pointer {
		cursor: pointer;		
	}
	.modal-title {
		font-weight:bold; 
		color: #FE980F; 
		font-family: 微軟正黑體;
		font-size: 1.5em;
	}
	.underline{
		text-decoration: underline;
	}
	#detail-modal{
		/*font-family: 微軟正黑體;*/
	}
	#detail-modal p {
		margin-bottom: 10px;
	}
	#detail-modal p span{
		margin-top:10px;
		font-size: 1.2em;
	}
	#detail-modal b b{
		font-size: 1.1em;
	}
	#detail-modal p b{
		/*color: #F08900;*/
		font-weight: bold;
		font-size: 1.2em;
	}
	.viewCaseDetail{
	font-weight: bolder;
	}
	.modal-dialog{
		font-family:  微軟正黑體;
	}
	#open-case-modal .modal-dialog{
		width: 28%;
	}
	#open-url-info{
		display:none; 
		font-size: 0.8em; 
		margin-top:20px;
	}
	#ajaxMsgModal{
		text-align: center;
	}
	.inline{
		display:inline;
	}
</style>
<%
request.setAttribute("showBreadcrumb", new Object()); 
java.util.LinkedHashMap<String, String> map = new java.util.LinkedHashMap<String, String>();
map.put("會員中心","");
request.setAttribute("breadcrumbMap", map); 
%>

<%-- INCLUDE HEADER2 --%>
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>
<%-- INCLUDE HEADER2 --%>
<section>
		<div class="container">
			<div class="row">
<%-- INCLUDE MENU --%>
<jsp:include page="/f/frag/f_memCenterMenu.jsp"/>
<%-- INCLUDE MENU --%>				
				<div class="col-sm-9">
					<h2 class="title text-center">合購案列表</h2>
					<div class="search-zone" style="text-align: right; display: none">
					
					</div><!--class="search-zone"-->
					<div class="list-zone">
<jsp:include page="/cases/Ajax_ownCases.jsp"/>
					</div><!--class="list-zone"-->
				</div><!--class="col-sm-9"-->
			</div>
		</div>
	</section>
<div style="display:none;" id="caseStatusBubble">
	<p><b>未上架：</b>尚未上架</p>
	<p><b>募集中：</b>已上架但尚未達到門檻</p>
	<p><b>已成團：</b>已上架中且達到門檻</p>
	<p><b>已結束：</b>已結束但尚未完成所有撥款</p>
	<p><b>已完成：</b>已完成所有流程且下架</p>
	<p><b>已取消：</b>時間結束未達門檻或使用者手動取消</p>
</div>
<div style="display:none;" id="caseAmountBubble">
	<p>目前 / 門檻 / 上限 數量</p>
</div>
<div class="modal fade" id="detail-modal"  tabindex="-1" role="dialog" aria-labelledby="view-detail" aria-hidden="true" >
	<div class="modal-dialog">
	    <div class="modal-content">
		 <div class="modal-header">
		 	<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="glyphicon glyphicon-remove"></i></button>
			<h4 class="modal-title" id="view-detail">
			   合購案詳情
			</h4>
		 </div>
		 <div class="modal-body" >
			<p>
				<b>合購編號  - 狀態：</b>
				<br>
				<span id="caseDeatil-caseno-status"></span>
			</p>
			<p>
				<b>合購案名稱：</b>
				<br>
				<span id="caseDeatil-title"></span>
			</p>
			<p>
				<b>合購商品：</b>
				<br>
				<span><a href="" id="caseDeatil-cpsp"></a></span>
			</p>
			<p>
				<b>起始 / 結束時間：</b>
				<br>	
				<span id="caseDeatil-time"></span>
			</p>
			<p>
				<b><b style="color:#5bc0de">目前</b> / <b style="color:#5cb85c">成團</b> / <b style="color:#d9534f">最大</b> 合購數量：</b>
				<br>
				<span id="caseDeatil-qtys"></span>
			</p>
			<p>
				<b>合購單價 (原價 - 折扣 = 合購價)：</b>
				<br>	
				<span id="caseDeatil-price"></span>
			</p>
			<p>
				<b>所在地區：</b>
				<br>
				<span id="caseDeatil-loc"></span>
			</p>
			<p>
				<b>交貨方式一：</b>
				<br>
				<span id="caseDeatil-ship1">面交 60元</span>
			</p>
			
			<p>
				<b>交貨方式二：</b>
				<br>
				<span id="caseDeatil-ship2"></span>
			</p>
			<p>
				<b>合購說明：</b>
			</p>
			<pre id="caseDeatil-casedesc"></pre>
		 </div>
		 <div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">
			   關閉
			</button>
		 </div>
	    </div><!-- /.modal-content -->
	</div>
</div>
<div class="modal fade" id="open-case-modal"  tabindex="-1" role="dialog" aria-labelledby="open-case-title" aria-hidden="true" >
	<div class="modal-dialog">
	    <div class="modal-content">
		 <div class="modal-header">
		 	<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="glyphicon glyphicon-remove"></i></button>
			<h4 class="modal-title" id="open-case-title">
			   合購案上架
			</h4>
		 </div>
		 <div class="modal-body" style="font-size: 1.2em;" >
		 	<p><span style="background-color: #FE980F; color: white;">&nbsp;&nbsp;<i class="fa fa-fw fa-users"></i>合購 &nbsp; <span class="cno"></span>&nbsp;&nbsp;</span></p>
	 		<p class="ctitle" ></p>
	 		<br>
	 		<div>
	 		<p style="font-size:0.9em;">請選擇合購結束時間：</p>
	 		<input type="text" id="open-date"  style="font-size: 0.8em;" placeholder="選擇日期"> 
	 		<select class="inline" style="width: 25%; font-size: 0.8em;" id="open-time">
	 			<% for(int i = 0; i<24 ;i++){%>
	 			<option><%=String.format("%02d", i)%>:00</option>
	 			<% }%>
	 		</select>
	 		<p>
	 		<p>
	 		</div>
		 	<p style="font-size:0.9em;">上架類型： </p>
		 	<select id="open-type" style="width: 28%; font-size: 0.8em; display:inline;">
		 		<option value="public" selected>公開</option>
		 		<option value="private">私人</option>
		 	</select>
		 	<div id="open-url-info"><p>合購網址：</p><input style="width: 100%;" type="text" value=""/></div>
		 </div>
		 <div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">
			   取消
			</button>
			<button type="button" class="btn btn-success" id="open-case-confirmed" data-caseno="">
			   確定
			</button>
		 </div>
	    </div><!-- /.modal-content -->
	</div>
</div>
<div class="modal fade" id="cancel-case-modal"  tabindex="-1" role="dialog" aria-labelledby="cancel-case-title" aria-hidden="true" >
	<div class="modal-dialog">
	    <div class="modal-content">
		 <div class="modal-header">
		 	<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="glyphicon glyphicon-remove"></i></button>
			<h4 class="modal-title" id="cancel-case-title">
			   取消合購案
			</h4>
		 </div>
		 <div class="modal-body" >
		 	<p style="color: #d43f3a; font-size: 1.2em;"><i class="fa fa-exclamation-triangle"></i> 注意：取消合購案將會連帶取消該合購案下的所有訂單</p>
		 	<p><span style="background-color: #FE980F; color: white;">&nbsp;&nbsp;<i class="fa fa-fw fa-users"></i>合購 &nbsp; <span class="cno"></span>&nbsp;&nbsp;</span></p>
	 		<p class="ctitle" ></p>
	 		<p>
	 		<hr>
	 		<p>
	 		<p>請輸入取消原因，這個訊息將會送給參與合購案的所有團員：</p>
		 	<textarea id="cancel-reason" style="height: 150px;"></textarea>
		 	</div>
		 <div class="modal-footer">
			<button type="button" class="btn btn-danger" id="cancel-case-confirmed" data-caseno="">
			   確定取消
			</button>
			<button type="button" class="btn btn-success" data-dismiss="modal">
			   返回
			</button>
		 </div>
	    </div><!-- /.modal-content -->
	</div>
</div>
<div class="modal fade" id="delete-case-modal"  tabindex="-1" role="dialog" aria-labelledby="delete-case-title" aria-hidden="true" >
	<div class="modal-dialog">
	    <div class="modal-content">
		 <div class="modal-header">
		 	<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="glyphicon glyphicon-remove"></i></button>
			<h4 class="modal-title" id="delete-case-title">
			   取消合購案
			</h4>
		 </div>
		 <div class="modal-body" style="font-size: 1.2em">
		 	<p><span style="background-color: #FE980F; color: white;">&nbsp;&nbsp;<i class="fa fa-fw fa-users"></i>合購 &nbsp; <span class="cno"></span>&nbsp;&nbsp;</span></p>
	 		<p class="ctitle" ></p>
	 		<p>確定刪除？</p>
	 	</div>
		 <div class="modal-footer">
			<button type="button" class="btn btn-default" id="delete-case-confirmed" data-caseno="">
			   確定
			</button>
			<button type="button" class="btn btn-success" data-dismiss="modal">
			   返回
			</button>
		 </div>
	    </div><!-- /.modal-content -->
	</div>
</div>
<div class="modal fade" id="over-case-modal"  tabindex="-1" role="dialog" aria-labelledby="over-case-title" aria-hidden="true" >
	<div class="modal-dialog">
	    <div class="modal-content">
		 <div class="modal-header">
		 	<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="glyphicon glyphicon-remove"></i></button>
			<h4 class="modal-title" id="over-case-title">
			   取消合購案
			</h4>
		 </div>
		 <div class="modal-body" style="font-size: 1.2em">
		 	<p><span style="background-color: #FE980F; color: white;">&nbsp;&nbsp;<i class="fa fa-fw fa-users"></i>合購 &nbsp; <span class="cno"></span>&nbsp;&nbsp;</span></p>
	 		<p class="ctitle" ></p>
	 		<p>確定要提早結團？</p>
	 	</div>
		 <div class="modal-footer">
			<button type="button" class="btn btn-default" id="over-case-confirmed" data-caseno="">
			   確定
			</button>
			<button type="button" class="btn btn-success" data-dismiss="modal">
			   返回
			</button>
		 </div>
	    </div><!-- /.modal-content -->
	</div>
</div>
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
	<script src="${resPath}/jquery-ui/jquery-ui.min.js"></script>
	<script>
		menuTrigger(0);
		var CurrentPageEnv = {
			controller:'${pageHome}/cases.do',
			caseDetailFullUrl:'${caseDetailFullUrl}'
		};
		$('#open-date').datepicker({
			changeYear : true,
			changeMonth : true,
			numberOfMonths : 1,
			dateFormat : 'yy-mm-dd',
			maxDate: '+8w',
			minDate:'+1d'
		});
		
		
	</script>
	<script src="${pageHome}/js/view_own_cases.js"></script>
</body>
</html>