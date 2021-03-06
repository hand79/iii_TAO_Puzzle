<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.tao.shopRep.model.*"%>
<%@ page import="com.tao.shop.model.*"%>
<jsp:useBean id="srepSvc" scope="page"
	class="com.tao.shopRep.model.ShopRepService" />
<jsp:useBean id="shopSvc" scope="page"
	class="com.tao.shop.model.ShopService" />
<jsp:useBean id="listShopRep_ByCompositeQuery" scope="request"
	type="java.util.List" />
<jsp:useBean id="memSvc" scope="page"
	class="com.tao.member.model.MemberService" />
<%
	String resPath = request.getContextPath() + "/b/res";
	pageContext.setAttribute("resPath", resPath);

	String contextPath = request.getContextPath() + "/shopRep";
	pageContext.setAttribute("contextPath", contextPath);
%>
<jsp:include page="/b/frag/b_header1.jsp" flush="true" />

<title>商店檢舉列表</title>

<jsp:include page="/b/frag/b_header2AndMenu.jsp" />

<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h3 class="page-header">商店檢舉紀錄列表</h3>
			<c:if test="${not empty errorMsgs}">
				<font color='red'>請修正以下錯誤:
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li>${message}</li>
						</c:forEach>
					</ul>
				</font>
			</c:if>
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->

	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 style="font-family: 微軟正黑體; margin: 0px">
						<b>搜尋</b>
					</h4>
				</div>
				<div class="panel-body">
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/back/shopRep/shopRep.do"
						name="form1" role="form">

						<div class="col-sm-2">
							<div class="form-group">
								<label>檢舉編號</label> <input type="text" class="form-control"
									name="srepno" value="">
							</div>
						</div>

						<div class="col-sm-2">
							<div class="form-group">
								<label>會員編號</label> <input type="text" class="form-control"
									name="repno" value="">
							</div>
						</div>

						<div class="col-sm-2">
							<div class="form-group">
								<label>商店編號</label> <input type="text" class="form-control"
									name="shopno" value="">
							</div>
						</div>

						<div class="col-sm-3">
							<div class="form-group">
								<label>&nbsp;</label>
								<button type="reset"
									class="form-control btn btn-outline btn-warning">清除</button>
							</div>
						</div>

						<div class="col-sm-3">
							<div class="form-group">
								<label>&nbsp;</label>
								<button class="form-control btn btn-outline btn-success">
									<i class="fa fa-search"></i> 搜尋
								</button>
								<input type="hidden" name="action"
									value="listShopRep_ByCompositeQuery">
							</div>

						</div>

					</FORM>
				</div>

			</div>
			<!--/.panel .panel-info-->
		</div>
	</div>

	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">搜尋結果</div>
				<!-- /.panel-heading -->
				<div class="panel-body">
					<c:set var="shoplist" value="${shopSvc.all}" />
					<c:set var="memlist" value="${memSvc.all}" />
					<div class="table-responsive">
						<table class="table table-striped table-bordered table-hover"
							id="dataTables-example">
							<thead>
								<tr>
									<th>條目</th>
									<th>商店</th>
									<th>會員</th>
									<th>原因</th>
									<th>下架</th>
									<th>刪除</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="shopRepVO"
									items="${listShopRep_ByCompositeQuery}">
									<tr class="odd gradeX">
										<td>${shopRepVO.srepno}</td>
										<td><c:forEach var="shopVO" items="${shoplist}">
												<c:if test="${shopRepVO.shopno == shopVO.shopno}">
													<a
														href="<%=request.getContextPath()%>/shop/shop.do?shopno=${shopVO.shopno}&action=getOne_For_Display">${shopVO.title}(${shopRepVO.shopno})</a>
												</c:if>
											</c:forEach></td>
										<td><c:forEach var="memVO" items="${memlist}">
										<c:if test="${shopRepVO.repno == memVO.memno}">
										<a class="pointer"><i class="fa fa-fw fa-user"></i>${memVO.memid}(${memVO.memno})</a>
										</c:if>
										</c:forEach></td>
										<td>${shopRepVO.sreprsn}</td>
										<td>
											<FORM METHOD="post"
												ACTION="<%=request.getContextPath()%>/shop/shop.do">
												<c:forEach var="shopVO" items="${shoplist}">
													<c:if test="${shopRepVO.shopno == shopVO.shopno}">
														<c:if
															test="${shopVO.status == 1 or shopVO.status == 2 or shopVO.status == 4 or shopVO.status == 5}">
															<input type="submit" class="btn btn-warning" value="下架">
														</c:if>
														<c:if test="${shop.status == 3}">
															<input type="button" class="btn btn-default disabled"
																value="下架">
														</c:if>
													</c:if>

												</c:forEach>
												<input type="hidden" name="shopno"
													value="${shopRepVO.shopno}"> <input type="hidden"
													name="status" value="3"> <input type="hidden"
													name="whichPage" value="?"> <input type="hidden"
													name="requestURL" value="<%=request.getServletPath()%>">
												<input type="hidden" name="action" value="update_stauts">
											</FORM></td>
										<td>
											<FORM METHOD="post"
												ACTION="<%=request.getContextPath()%>/shopRep/shopRep.do">
												<input type="submit" class="btn btn-danger" value="刪除">
												<input type="hidden" name="srepno"
													value="${shopRepVO.srepno}"> <input type="hidden"
													name="requestURL" value="<%=request.getServletPath()%>">
												<input type="hidden" name="action" value="delete">
											</FORM>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<!-- /.table-responsive -->
				</div>
				<!-- /.panel-body -->
			</div>
			<!-- /.panel -->
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
</div>
<!-- /#page-wrapper -->

<jsp:include page="/b/frag/b_footer1.jsp" />
<script>
	$(document).ready(function() {
		$('#dataTables-example').dataTable({
			"language" : {
				"paginate" : {
					"next" : "下一頁",
					"last" : "最末頁",
					"previous" : "上一頁",
					"first" : "第一頁",
					"emptyTable" : "無資料"

				},
				"info" : "第  _PAGE_ 頁，共  _PAGES_ 頁  ( _TOTAL_ 筆資料 )",
				"lengthMenu" : "每頁顯示  _MENU_ 筆",
				"search" : "搜尋結果過濾: ",
				"infoEmpty" : "共 0 筆符合",
				"infoFiltered" : " (由  _MAX_ 筆過濾而來)",
				"zeroRecords" : "無符合之資料"
			}
		}

		);
	});
</script>
<jsp:include page="/b/frag/b_footer2.jsp" />