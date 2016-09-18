<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%
    String resPath = request.getContextPath() + "/b/res";
    pageContext.setAttribute("resPath", resPath);

    String pageHome = request.getContextPath() + "/shop";
    pageContext.setAttribute("pageHome", pageHome);
%>

<jsp:useBean id="listShop_ByCompositeQuery" scope="request" type="java.util.List" />
<jsp:useBean id="locationSvc" scope="page" class="com.tao.location.model.LocationService" />
<jsp:useBean id="memberSvc" scope="page" class="com.tao.member.model.MemberService" />
<!-- head標頭放這裡 -->
<jsp:include page="/b/frag/b_header1.jsp"/>
<title>[饕飽地圖]後端管理系統</title>
<jsp:include page="/b/frag/b_header2AndMenu.jsp"/>

</head>

		
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">搜尋商店</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			
			<!-- search畫面放這裡 -->
			<%@ include file="search_shop_include_file.jsp" %>
			
			<!-- Content -->
						<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
						<h4 style="font-family: 微軟正黑體; margin: 0px"><b>商店列表</b></h4>
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
							
								<table class="table table-striped table-bordered table-hover"  id="dataTables-example">
									<thead>
										<tr>
											<th>商店編號</th>
											<th>名稱</th>
											<th>商店會員(編號)</th>
											<th>地區</th>
											<th>狀態</th>
											<th>下架</th>
											<th>刪除</th>

										</tr>
									</thead>
									<tbody id="trchange">
										<!-- 畫面筆數放這裡 -->
										<%@ include file="page1_ByCompositeQuery.file"%>
										 <!--include file="context_back.jsp"-->
										 <c:if test="${empty listShop_ByCompositeQuery}">
										 <tr>
										 	<td>無資料!</td>
										 	<td></td>
										 	<td></td>
										 	<td></td>
										 	<td></td>
										 	<td></td>
										 	<td></td>
										 </tr>
										 </c:if>
										<c:forEach var="shopVO" items="${listShop_ByCompositeQuery}" begin="<%=pageIndex%>" 	end="<%=pageIndex+rowsPerPage-1%>">
											<tr align='left' data-newsno="${shopVO.shopno }"  ${(shopVO.shopno==param.shopno) ? 'class=\"success\"':''} "> 										
												<td>${shopVO.shopno}</td>
												<c:if test="${shopVO.status==2}">
													<td><a href="<%=request.getContextPath()%>/back/shop/shop.do?shopno=${shopVO.shopno}&action=getOne_For_Display">${shopVO.title}</a></td>
												</c:if>
												<c:if test="${shopVO.status!=2}">
													<td><a href="<%=request.getContextPath()%>/back/shop/shop.do?shopno=${shopVO.shopno}&action=getTemp_Display">${shopVO.title}</a></td>
												</c:if>
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
													<c:if test="${shopVO.status==0}" >－</c:if>
													<font style="color: orange;"><c:if test="${shopVO.status==1}">待審核</c:if></font>
													<font style="color: blue;"><c:if test="${shopVO.status==2}">上架中</c:if></font>
													<font style="color: green;"><c:if test="${shopVO.status==3}">已下架</c:if></font>
													<font style="color: gery;"><c:if test="${shopVO.status==4}">已刪除</c:if></font>
													<font style="color: red;"><c:if test="${shopVO.status==5}">未通過審核</c:if></font>
												</td>
										
												<td><span class="btn btn-outline btn-success"
													data-toggle="modal" data-target="#down_shop_${shopVO.shopno}">下架</span> 
												</td>
												<td><span class="btn btn-outline btn-danger"
													data-toggle="modal" data-target="#del_shop_${shopVO.shopno}">刪除</span></td>
											</tr>
										</c:forEach>

									</tbody>
								</table>
								<!-- 換頁放這裡 -->
								<%@ include file="page2_ByCompositeQuery.file"%>

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
		

   

<!-- 下架燈箱放這裡 -->
<c:forEach var="shopVO" items="${listShop_ByCompositeQuery}">
	<div class="modal fade" id="down_shop_${shopVO.shopno}" role="dialog"
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
						是否確定下架商店編號<b><font color=green class="delcomsg">${shopVO.shopno}</font></b>?
						<hr/><div>
						<b><i class="fa fa-fw fa-check-square"></i>請輸入下架商店原因:</b><br/><br/>
						<textarea rows="5" cols="10" style="margin: 0px; width: 565px; height: 100px;" required="required"></textarea>
					</div>
					</div>

					<div class="modal-footer">
						<label class="pull-left"><font color="#d43f3a" ><i class="fa fa-fw fa-check-square"></i>號為必填欄位</font></label>
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<input type="submit" class="btn btn-success" value="下架"> 
						<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
						<input type="hidden" name="whichPage"	value="<%=whichPage%>">               <!--送出當前是第幾頁給Controller-->
						<input type="hidden" name="action" value="update_stauts">
						<input type="hidden"    name="shopno" value="${shopVO.shopno}"> 
						<input type="hidden" name="status" value="3">
					</div>
				</FORM>
			</div>
		</div>
	</div>
</c:forEach>
<!-- 刪除燈箱放這裡 -->
<c:forEach var="shopVO" items="${listShop_ByCompositeQuery}">
	<div class="modal fade" id="del_shop_${shopVO.shopno}" role="dialog"
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
						是否確定刪除商店編號<b><font color=red class="delcomsg">${shopVO.shopno}</font></b>紀錄?
						<div>
						<hr/><b><i class="fa fa-fw fa-check-square"></i>請輸入刪除商店原因:</b><br/><br/>
						<textarea rows="5" cols="10" style="margin: 0px; width: 565px; height: 100px;" required="required"></textarea>
						</div>
					</div>

					<div class="modal-footer">
						<label class="pull-left"><font color="#d43f3a" ><i class="fa fa-fw fa-check-square"></i>號為必填欄位</font></label>
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<input type="submit" class="btn btn-danger" value="刪除"> 
						<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
						<input type="hidden" name="whichPage"	value="<%=whichPage%>">               <!--送出當前是第幾頁給Controller-->
						<input type="hidden" name="action" value="update_stauts">
						<input type="hidden"    name="shopno" value="${shopVO.shopno}"> 
						<input type="hidden" name="status" value="4">
					</div>
				</FORM>
			</div>
		</div>
	</div>
</c:forEach>
<jsp:include page="/b/frag/b_footer1.jsp"/>
<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
<script>
	$(document).ready(function() {
		$('#dataTables-example').dataTable(
		{bLengthChange:true,
		bPaginate:true,
		bInfo:true,
		bFilter:true
		});
	});
</script>
<script>
$('#county-list').on('change', function() {
	var index = this.selectedIndex;
	if (index != 0) {
		$(this).removeClass('invalidText');
		var val = $(this).children()[index].value;
		$('#town-list').load('<%= request.getContextPath()%>/cases/cases.do', {
			"county" : val,
			"action" : "ajax",
			"what" : "town"
		});
	}else{
		$(this).addClass('invalidText');
	}
});// end of #county-list
</script>
<script>
function numcheck(id,time){
 var re = /^[0-9]+$/;
 if (!re.test(time.value)){
  alert("只能輸入數字");
  document.getElementById("checknum").value="";
 }
}
</script>
<jsp:include page="/b/frag/b_footer2.jsp"/>