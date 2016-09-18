<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@page import="com.tao.cases.model.*"%>
<%@page import="com.tao.jimmy.util.model.*"%>
<%@page import="com.tao.jimmy.location.*"%>
<% pageContext.setAttribute("contextPath", request.getContextPath());%>
<% pageContext.setAttribute("resPath", request.getContextPath() + "/b/res");%>
<% pageContext.setAttribute("pageHome", request.getContextPath() + "/cases");%>
<% pageContext.setAttribute("controllerPath", request.getContextPath()  + "/back/cases/cases.do");%>
<%	
	CountyService countyService = new CountyService();
	Set<CountyVO> counties = countyService.findCounties();
	pageContext.setAttribute("counties", counties);
%>
<jsp:useBean id="locSvc" scope="page" class="com.tao.jimmy.location.LocationService"/>
<jsp:useBean id="tinyMemSvc" scope="page" class="com.tao.jimmy.member.TinyMemberService"/>
<jsp:useBean id="cpSvc" scope="page" class="com.tao.cases.model.CaseProductService"/>

<%-- include header1 --%>	
<jsp:include page="/b/frag/b_header1.jsp"/>
<%-- <%@ include file="/b/frag/b_header1.jsp" %> --%>

 <link href="${resPath}/jquery-ui/jquery-ui.min.css"
	rel="stylesheet" type="text/css">
<link href="${resPath}/jquery-ui/jquery-ui.theme.min.css"
	rel="stylesheet" type="text/css">

<style>
.page-header {
	font-family: 微軟正黑體;
}
.pointer{
	cursor: pointer;
}
.modal>*{
	font-family: 微軟正黑體;
}

.modal-title{
	font-weight: bold;
}
</style>

<%-- include header2AndMenu --%>
<jsp:include page="/b/frag/b_header2AndMenu.jsp"/>

		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">搜尋合購案 <button class="btn btn-primary btn-outline pull-right" id="overCase">立即結案合購</button></h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-info">
						<div class="panel-heading">
							<h4 style="font-family: 微軟正黑體; margin: 0px">
								<b>複合搜尋</b>
							</h4>
						</div>
						<div class="panel-body">
							<form role="form" id="queryForm">

								<div class="col-sm-2">
									<div class="form-group">
										<label>合購案編號</label> <input class="form-control" name="caseno" />
									</div>

								</div>
								<div class="col-sm-2">
									<div class="form-group">
										<label>發案會員編號</label> <input class="form-control" name="memno" />
									</div>
								</div>
								<div class="col-sm-2">
									<div class="form-group">
										<label>上架日期(起)</label> <input type="text" class="form-control"
											name="stimefrom">
									</div>

								</div>

								<div class="col-sm-2">
									<div class="form-group">
										<label>上架日期(訖)</label> <input type="text" class="form-control" 
											name="stimeto">
									</div>

								</div>

								<div class="col-sm-1">
									<div class="form-group">
										<label>縣市</label> <select class="form-control" name="county"
											id="county-list">
											<option value="">-</option>
											<c:forEach var="countyVO" items="${counties}">
												<option value="${countyVO.value}">${countyVO.name}</option>
											</c:forEach>
										</select>
									</div>

								</div>
								<div class="col-sm-1">
									<div class="form-group">
										<label>鄉鎮市區</label> <select class="form-control" name="locno"
											id="town-list">
											<option value="">-</option>
										</select>
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
										<label>商品編號</label> <input class="form-control" name="cspno"/>

									</div>
								</div>
								<div class="col-sm-2">
									<div class="form-group">
										<label>商店編號</label> <input class="form-control" name="shopno"/>
									</div>
								</div>
								<div class="col-sm-2">
									<div class="form-group">
										<label>結束日期(起)</label> <input type="text" class="form-control"
											name="etimefrom" />
									</div>
								</div>
								<div class="col-sm-2">
									<div class="form-group">
										<label>結束日期(訖)</label> <input type="text" class="form-control"
											name="etimeto" />
									</div>
								</div>
								<div class="col-sm-2">
									<div class="form-group">
										<label>狀態</label> <select class="form-control" name="status">
											<option value="">-</option>
											<% for(int i : CasesStatus.getAllStatusValues()){ %>
											<option value="<%=i%>"><%=CasesStatus.getStatusName(i)%></option>
											<% }%>
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
								<input type="hidden" name="action" value="compositeQuery">
							</form>
						</div>

					</div>
					<!--/.panel .panel-info-->
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading" id="MsgTitle" style="font-weight: bold">請輸入搜尋條件</div>
						<!-- /.panel-heading -->
						<div class="panel-body" id="resultDiv">
<jsp:include page="Ajax_casesTable.jsp"/>
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
<div class="modal fade" id="cancel-modal"  tabindex="-1" role="dialog" aria-labelledby="cancel-detail" aria-hidden="true" >
	<div class="modal-dialog" style="width: 400px;top: 100px;">
	    <div class="modal-content">
		 <div class="modal-header">
			<h4 class="modal-title" id="cancel-detail">
			   取消合購案
			</h4>
		 </div>
		 <div class="modal-body" >
		 	<p class="text-danger" style="font-size: 1.3em;"><i class="fa fa-fw fa-exclamation-circle"></i>注意：<br>取消合購案將會連帶取消該合購案的所有訂單</p>
		 	<p><br></p>
		 	<p>請輸入取消原因：</p>
		 	<textarea  style="height: 200px;"id="cancel-reason" class="form-control"></textarea>
		 </div>
		 <div class="modal-footer">
			<button type="button" class="btn btn-success btn-circle btn-lg" id="cancel-confirm" style="margin-right: 10px;">
			 <i class="fa fa-check"></i>
			</button>
			<button type="button" class="btn btn-danger btn-circle btn-lg" data-dismiss="modal">
			 <i class="fa fa-times"></i>
			</button>
		 </div>
	    </div><!-- /.modal-content -->
	</div>
</div>
<div class="modal fade" id="delete-modal"  tabindex="-1" role="dialog" aria-labelledby="delete-detail" aria-hidden="true" >
	<div class="modal-dialog" style="width: 400px;top: 100px;">
	    <div class="modal-content">
		 <div class="modal-header">
			<h4 class="modal-title" id="delete-detail">
			   刪除合購案
			</h4>
		 </div>
		 <div class="modal-body" >
		 	<p class="text-danger" style="font-size: 1.3em;"><i class="fa fa-fw fa-exclamation-circle"></i>注意：<br>刪除<b>只會</b>連帶取消該合購案未完成的訂單</p>
		 	<p><br></p>
		 	<p>請輸入刪除原因：</p>
		 	<textarea  style="height: 200px;"id="delete-reason" class="form-control"></textarea>
		 </div>
		 <div class="modal-footer">
			<button type="button" class="btn btn-success btn-circle btn-lg" id="delete-confirm" style="margin-right: 10px;">
			 <i class="fa fa-check"></i>
			</button>
			<button type="button" class="btn btn-danger btn-circle btn-lg" data-dismiss="modal">
			 <i class="fa fa-times"></i>
			</button>
		 </div>
	    </div><!-- /.modal-content -->
	</div>
</div>
<div class="modal fade" id="overCaseModal"  tabindex="-1" role="dialog" aria-labelledby="overCaseWindow" aria-hidden="true">
	<div class="modal-dialog" style="top:110px; width: 300px; height:200px;">
	    <div class="modal-content">
			 <div class="modal-header">
				<h4 class="modal-title" id="overCaseWindow" style="font-family: 微軟正黑體; font-size:large;">結案合購</h4>
			 </div>
			 <div class="modal-body">
			 	<p>結案合購可能會使系統暫時性效能低落。</p>
			 	<p>您確定要系統立即執行結案合購的作業嗎？</p>
			 </div>
			 <div class="modal-footer">
				<button type="button" class="btn btn-danger btn-circle btn-lg" id="over-case-confirm" style="margin-right: 10px;">
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
<%-- include footer1 --%>
<jsp:include page="/b/frag/b_footer1.jsp"/>

	<script src="${resPath}/jquery-ui/jquery-ui.min.js"></script>

	<!-- Page Specific Js source -->
	<script> 
		menuTrigger('#caseMenu');
		var CasesEnv = {
		controller : '${controllerPath}',
		msg : ['搜尋結果','輸入格式有誤','搜尋結果'],
		errMsg:'<i class=\"fa fa-times-circle-o \" style=\"font-size:2em; color:#FE980F;\"></i> 連線錯誤',
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
	<script src="${pageHome}/js/manage_cases.js"></script>
	
<%-- include footer2 --%>
<jsp:include page="/b/frag/b_footer2.jsp"/>
