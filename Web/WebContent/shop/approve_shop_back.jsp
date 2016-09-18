<%@ page import="com.tao.cathy.util.DateFormater"%> 
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.tao.shop.model.*"%>
<%
    String resPath = request.getContextPath() + "/b/res";
    pageContext.setAttribute("resPath", resPath);

    String pageHome = request.getContextPath() + "/shop";
    pageContext.setAttribute("pageHome", pageHome);
%>

<%
	ShopService shopSvc = new ShopService();
	List<ShopVO> list = shopSvc.getWaitApproveShop();
	pageContext.setAttribute("list", list);
%>
<!-- head標頭放這裡 -->

<jsp:useBean id="memberSvc" scope="page" 	class="com.tao.member.model.MemberService" />
<jsp:useBean id="locationSvc" scope="page" 	class="com.tao.location.model.LocationService" />
<jsp:include page="/b/frag/b_header1.jsp"/>
<title>[饕飽地圖]審核上架</title>
<jsp:include page="/b/frag/b_header2AndMenu.jsp"/>

</head>

		
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">審核上架</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			
			
			<!-- Content -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
						<h4 style="font-family: 微軟正黑體; margin: 0px"><b>審核商店列表</b></h4>
							<c:if test="${not empty errorMsgs}">
								<font color='red'>請修正以下錯誤:
									<c:forEach var="message" items="${errorMsgs}">
										<li>${message}</li>
									</c:forEach>
								</font>
							</c:if>
						</div>
						
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="table-responsive">
								<table class="table table-striped table-bordered table-hover" id="dataTables-example">
									<thead>
										<tr>
											<th>商店編號</th>
											<th>名稱</th>
											<th>商店會員(編號)</th>
											<th>地區</th>
											<th>狀態</th>
											<th>通過</th>
											<th>未通過</th>

										</tr>
									</thead>
									<tbody id="trchange">
										
										 <c:if test="${empty list}">
										 <tr>
										 	<td colspan="7">無資料!</td>
										 </tr>
										 </c:if>
										<c:forEach var="shopVO" items="${list}">
											<tr align='left' data-newsno="${shopVO.shopno }"  ${(shopVO.shopno==param.shopno) ? 'class=\"success\"':''} "> 										
												<td>${shopVO.shopno}</td>
												<td><a href="<%=request.getContextPath()%>/shop/shop.do?shopno=${shopVO.shopno}&action=getTemp_Display">${shopVO.title}</a></td>
												<td>
													<c:forEach var="memberVO" items="${memberSvc.all}">
														<c:if test="${memberVO.memno == shopVO.memno}">
															<a href="<%=request.getContextPath() %>/SurfMemberServlet.do?action=memberView&memno=${shopVO.memno}" class="pointer"><i class="fa fa-fw fa-user"></i>${memberVO.memid}(${shopVO.memno})</a>
														</c:if>
													</c:forEach>
												</td>
													
												<td>
													<c:forEach var="locationVO" items="${locationSvc.all}">
														<c:if test="${shopVO.locno==locationVO.locno}">
										                   【${locationVO.county}】 - ${locationVO.town}
									                    </c:if>
												     </c:forEach>												
												</td>
												<td>
													<c:if test="${shopVO.status==0}" >已建立</c:if>
													<font style="color: orange;"><c:if test="${shopVO.status==1}">待審核</c:if></font>
													<font style="color: blue;"><c:if test="${shopVO.status==2}">上架中</c:if></font>
													<font style="color: green;"><c:if test="${shopVO.status==3}">已下架</c:if></font>
													<font style="color: gery;"><c:if test="${shopVO.status==4}">已刪除</c:if></font>
													<font style="color: red;"><c:if test="${shopVO.status==5}">未通過審核</c:if></font>
												</td>
										
												<td><span class="btn btn-outline btn-success"
													data-toggle="modal" data-target="#ok_shop_${shopVO.shopno}">通過</span> 
												</td>
												<td><span class="btn btn-outline btn-danger"
													data-toggle="modal" data-target="#ng_shop_${shopVO.shopno}">未通過</span></td>
											</tr>
										</c:forEach>

									</tbody>
								</table>
								<!-- 換頁放這裡 -->

							</div>
							<!-- /.table-responsive -->
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
				<!-- /.col-lg-12 -->
			</div>
		</div>
		<!-- /#page-wrapper -->
		

   
<jsp:include page="/b/frag/b_footer1.jsp"/>
<!-- 下架燈箱放這裡 -->
<c:forEach var="shopVO" items="${list}">
	<div class="modal fade" id="ok_shop_${shopVO.shopno}" role="dialog"
		aria-labelledby="AdModal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="AdModal" style="color: #5cb85c;">
						<i class="fa fa-warning fa-fw"></i>提醒訊息
					</h4>

				</div>
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back/shop/shop.do">
					<div class="modal-body">
						確定商店編號<b><font color=green class="delcomsg">${shopVO.shopno}</font></b>通過審核?
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<input type="submit" class="btn btn-success" value="通過"> 
						<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
						<input type="hidden" name="action" value="update_stauts">
						<input type="hidden"    name="shopno" value="${shopVO.shopno}"> 
						<input type="hidden" name="status" value="2">
					</div>
				</FORM>
			</div>
		</div>
	</div>
</c:forEach>
<!-- 刪除燈箱放這裡 -->
<c:forEach var="shopVO" items="${list}">
	<div class="modal fade" id="ng_shop_${shopVO.shopno}" role="dialog"
		aria-labelledby="AdModal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="AdModal" style="color: #a94442">
						<i class="fa fa-warning fa-fw"></i>提醒訊息
					</h4>

				</div>
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back/shop/shop.do">
					<div class="modal-body">
						確定商店編號<b><font color=red class="delcomsg">${shopVO.shopno}</font></b>未通過審核?
						<hr/><div>
							<b><i class="fa fa-fw fa-check-square"></i>請輸入未通過審核原因:<br/><br/>
							<textarea rows="5" cols="10" style="margin: 0px; width: 565px; height: 100px;" required="required"></textarea>
						</div>
					</div>
					<div class="modal-footer">
						<label class="pull-left"><font color="#d43f3a" ><i class="fa fa-fw fa-check-square"></i>號為必填欄位</font></label>
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<input type="submit" class="btn btn-danger" value="未通過"> 
						<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
						<input type="hidden" name="action" value="update_stauts">
						<input type="hidden"    name="shopno" value="${shopVO.shopno}"> 
						<input type="hidden" name="status" value="5">
					</div>
				</FORM>
			</div>
		</div>
	</div>
</c:forEach>
<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
<script>
menuTrigger(1);
	$(document).ready(function() {
		$('#dataTables-example').dataTable({
		bLengthChange:true,
		bPaginate:true,
		bInfo:true,
		bFilter:true,
		"language" :{
			"paginate" : {
				"next" : "&raquo;",
				"last": "最末頁",
				"previous" : "&laquo;",
				"first":"第一頁",
				"emptyTable": "無資料"
				
			},
			"info": "第  _PAGE_ 頁，共  _PAGES_ 頁  ( _TOTAL_ 筆資料 )",
			"lengthMenu": "每頁顯示  _MENU_ 筆",
			"search": "搜尋: ",
			"infoEmpty": "共 0 筆符合",
			"infoFiltered": " (由  _MAX_ 筆過濾而來)",
			"zeroRecords": "無符合之資料"
		}
		});
	});
</script>

<jsp:include page="/b/frag/b_footer2.jsp"/>