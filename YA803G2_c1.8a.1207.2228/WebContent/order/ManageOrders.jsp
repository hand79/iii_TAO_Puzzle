<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.tao.order.model.*"%>


<c:set var="pageHome" value="<%=request.getContextPath() +\"/order\"%>"
	scope="page" />
<c:set var="resPath" value="<%=request.getContextPath() +\"/b/res\"%>"
	scope="page" />
<c:set var="controllerPath" value="<%=request.getContextPath() +\"/back/order/order.do\"%>"
	scope="page" />
	
<%-- include header1 --%>	
<jsp:include page="/b/frag/b_header1.jsp"/>
 
<link href="${resPath}/jquery-ui/jquery-ui.min.css"
	rel="stylesheet" type="text/css">
<link href="${resPath}/jquery-ui/jquery-ui.theme.min.css"
	rel="stylesheet" type="text/css">
<style>
.page-header {
	font-family: 微軟正黑體;
}
.modal>*{
	font-family: 微軟正黑體;
}

.modal-title{
	font-weight: bold;
}
.pointer{
	cursor: pointer;
}
</style>

<%-- include header2AndMenu --%>
<jsp:include page="/b/frag/b_header2AndMenu.jsp"/>

		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">搜尋訂單 <button class="btn btn-primary btn-outline pull-right" id="finishOrder">立即結算訂單</button></h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-success">
						<div class="panel-heading">
							<h4 style="font-family: 微軟正黑體; margin: 0px">
								<b>複合搜尋</b>
							</h4>
						</div>
						<div class="panel-body">
							<form role="form" id="queryForm">

								<div class="col-sm-2">
									<div class="form-group">
										<label>訂單編號</label> <input class="form-control" name="ordno">
									</div>

								</div>
								<div class="col-sm-2">
									<div class="form-group">
										<label>下單會員編號 或 帳號關鍵字</label> <input class="form-control"
											name="bmem">
									</div>
								</div>
								<div class="col-sm-2">
									<div class="form-group">
										<label>訂單時間(起)</label> <input type="text" class="form-control"
											name="osday">
									</div>

								</div>

								<div class="col-sm-1">
									<div class="form-group">
										<label>&nbsp;</label> <select class="form-control"
											name="ostime">
											<option value="">-</option>
											<%
												for(int i=0; i<24; i++){
											%>
											<%
												String h = String.format("%02d:00", i);
											%>
											<option value="<%=h%>"><%=h%></option>
											<%
												}
											%>
										</select>
									</div>
								</div>
								<div class="col-sm-1">
									<div class="form-group">
										<label>訂單金額</label> <select class="form-control"
											name="pricefilter">
											<option value="none">-</option>
											<option value="eq">等於</option>
											<option value="gt">大於等於</option>
											<option value="lt">小於等於</option>
										</select>
									</div>
								</div>
								<div class="col-sm-2">
									<div class="form-group">
										<label>&nbsp;</label> 
										<input type="number" min="0" class="form-control" name="price" placeholder="請輸入金額">
									</div>
								</div>

								<div class="col-sm-1">
									<div class="form-group">
										<label>&nbsp;</label>
										<button type="reset" class="form-control btn btn-info">重置</button>
									</div>
								</div>
								<div class="col-sm-1">
									<div class="form-group">
										<label style="margin-left:-10px">搜尋方式</label>
										<select name="notMatch" class="pull-right" style="height:34px; ">
											<option value="">符合輸入條件</option>
											<option value="yes">排除輸入條件</option>
										</select>
									</div>
								</div>
<!-- 								<div class="col-sm-1"> -->
<!-- 									<div class="form-group"> -->
<!-- 										<label>&nbsp;</label> -->
<!-- 										<button class="form-control btn btn-info" id="listAll">列出全部</button> -->
<!-- 									</div> -->
<!-- 								</div> -->

								<!--Second Row-->
								<div class="col-sm-2">
									<div class="form-group">
										<label>合購案編號</label> <input class="form-control" name="caseno">

									</div>
								</div>
								<div class="col-sm-2">
									<div class="form-group">
										<label>主購會員編號 或 帳號關鍵字</label> <input class="form-control" name="cmem">
									</div>
								</div>
								<div class="col-sm-2">
									<div class="form-group">
										<label>訂單時間(訖)</label> <input type="text" class="form-control"
											name="oeday">
									</div>
								</div>
								<div class="col-sm-1">
									<div class="form-group">
										<label>&nbsp;</label> <select class="form-control"
											name="oetime">
											<option value="">-</option>
											<%
												for(int i=0; i<24; i++){
											%>
											<%
												String h = String.format("%02d:00", i);
											%>
											<option value="<%=h%>"><%=h%></option>
											<%
												}
											%>
										</select>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label>狀態</label> <select class="form-control" name="status">
											<option value="">-</option>
											<%
												for(int i : OrderStatus.getAllStatusValues()) {
											%>
											<option value="<%=i%>"><%=OrderStatus.getStatusName(i)%></option>
											<%
												}
											%>
										</select>
									</div>
								</div>

								<div class="col-sm-2">
									<div class="form-group">
										<label>&nbsp;</label>
										<button class="form-control btn btn-success" id="doSearch">
											<i class="fa fa-search"></i> 搜尋
										</button>
									</div>
								</div>
							</form>
						</div>

					</div>
					<!--/.panel .panel-info-->
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading" id="MsgTitle">請輸入搜尋條件</div>
						<!-- /.panel-heading -->
						<div class="panel-body" id="resultDiv">
<jsp:include page="Ajax_orderTable.jsp"/>
							<!-- /.table-responsive -->
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
			</div>
			<!-- /.row -->
		</div>
		<!-- /#page-wrapper -->
<div class="modal fade" id="detail-modal"  tabindex="-1" role="dialog" aria-labelledby="view-detail" aria-hidden="true" >
	<div class="modal-dialog" style="width: 400px;top: 100px;">
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
		 <div class="modal-footer" style="text-align: center;">
			<button type="button" class="btn btn-success btn-circle btn-lg" data-dismiss="modal">
			 <i class="fa fa-check"></i>
			</button>
		 </div>
	    </div><!-- /.modal-content -->
	</div>
</div>		
<div class="modal fade" id="conflic-modal"  tabindex="-1" role="dialog" aria-labelledby="conflic-window" aria-hidden="true" >
	<div class="modal-dialog" style="width: 400px; top: 300px;">
	    <div class="modal-content">
		 <div class="modal-header">
			<h4 class="modal-title" id="conflic-window">
			  糾紛處理
			</h4>
		 </div>
		 <div class="modal-body" >
		 	<p>若糾紛已處理完畢，請選擇訂單要更新為的狀態</p>
		 	<select class="form-control status">
		 		<option value="<%=OrderDAO.STATUS_CANCELED%>"><%=OrderStatus.getStatusName(OrderDAO.STATUS_CANCELED)%> (會進行退款)</option>
		 		<option value="<%=OrderDAO.STATUS_BOTH_COMFIRMED%>"><%=OrderStatus.getStatusName(OrderDAO.STATUS_BOTH_COMFIRMED)%></option>
		 		<option value="<%=OrderDAO.STATUS_ACHIEVED%>"><%=OrderStatus.getStatusName(OrderDAO.STATUS_ACHIEVED)%></option>
		 		<option value="<%=OrderDAO.STATUS_CREATOR_COMFIRMED%>"><%=OrderStatus.getStatusName(OrderDAO.STATUS_CREATOR_COMFIRMED)%></option>
		 		<option value="<%=OrderDAO.STATUS_BUYER_COMFIRMED%>"><%=OrderStatus.getStatusName(OrderDAO.STATUS_BUYER_COMFIRMED)%></option>
		 	</select></div>
 		<div class="modal-footer">
			<button type="button" class="btn btn-success btn-circle btn-lg" id="modify-confirm" style="margin-right: 10px;">
			 <i class="fa fa-check"></i>
			</button>
			<button type="button" class="btn btn-danger btn-circle btn-lg" data-dismiss="modal">
			 <i class="fa fa-times"></i>
			</button>
		 </div>
	    </div><!-- /.modal-content -->
	</div>
</div>
<div class="modal fade" id="finishOrderModal"  tabindex="-1" role="dialog" aria-labelledby="finishOrderWindow" aria-hidden="true">
	<div class="modal-dialog" style="top: 300px; width: 300px; height:200px;">
	    <div class="modal-content">
			 <div class="modal-header">
				<h4 class="modal-title" id="finishOrderWindow" style="font-family: 微軟正黑體; font-size:large;">結算訂單</h4>
			 </div>
			 <div class="modal-body">
			 <p>結算訂單可能會使系統暫時性效能低落</p>
			 <p>您確定要系統立即執行結算訂單的作業嗎？</p>
		 	</div>
	 		<div class="modal-footer">
				<button type="button" class="btn btn-danger btn-circle btn-lg" id="finish-order-confirm" style="margin-right: 10px;">
				 <i class="fa fa-check"></i>
				</button>
				<button type="button" class="btn btn-success btn-circle btn-lg" data-dismiss="modal">
				 <i class="fa fa-times"></i>
				</button>
		 	</div>
	    </div><!-- /.modal-content -->
	</div>
</div><!-- /.modal -->
<div class="modal fade" id="ajaxMsgModal"  tabindex="-1" role="dialog" aria-labelledby="msgWindow" aria-hidden="true">
	<div class="modal-dialog" style="top:360px; width: 300px; height:200px;">
	    <div class="modal-content">
			 <div class="modal-header" style="display:none">
				<h4 class="modal-title" id="msgWindow"></h4>
			 </div>
			 <div class="modal-body" style="text-align: center;">
			 <p>
				 執行中...&nbsp;&nbsp;<i class="fa fa-spin fa-spinner" style="font-size:2em; color:#FE980F"></i>
			 </p>
			 </div>
	    </div><!-- /.modal-content -->
	</div>
</div><!-- /.modal -->
		
<%-- include footer1 --%>
<jsp:include page="/b/frag/b_footer1.jsp"/>
	<script src="${resPath}/jquery-ui/jquery-ui.min.js"></script>
	<script> 
		menuTrigger('#caseMenu');
		var OrderEnv = {
		controller : '${controllerPath}',
		frontController: '<%= request.getContextPath()%>/cases/cases.do',
		msg : ['搜尋結果','輸入格式有誤','搜尋結果'],
		lang :{
			"paginate" : {
				"next" : "下一頁",
				"last": "最末頁",
				"previous" : "上一頁",
				"first":"第一頁",
				"emptyTable": "無資料"
				
			},
			"info": "第  _PAGE_ 頁，共  _PAGES_ 頁  ( _TOTAL_ 筆資料 )",
			"lengthMenu": "每頁顯示  _MENU_ 筆",
			"search": "搜尋結果過濾: ",
			"infoEmpty": "共 0 筆符合",
			"infoFiltered": " (由  _MAX_ 筆過濾而來)",
			"zeroRecords": "無符合之資料"
		}
		};
	</script>
	<script src="${pageHome}/js/manage_orders.js"></script>
<%-- include footer2 --%>
<jsp:include page="/b/frag/b_footer2.jsp"/>