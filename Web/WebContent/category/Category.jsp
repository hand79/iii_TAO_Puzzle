<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.tao.category.model.*" %>
<%@ page import="java.util.*"%>

<jsp:include page="/b/frag/b_header1.jsp" />
<jsp:include page="/b/frag/b_header2AndMenu.jsp" />

<jsp:useBean id="categorySvc" 	 scope="page" class="com.tao.category.model.CategoryService"/>
<jsp:useBean id="subCategorySvc" scope="page" class="com.tao.category.model.SubCategoryService"/>
<jsp:useBean id="catgorylist" scope="request" class="com.tao.category.model.CategoryVO"/>

<c:set var="list" value="${catnolist}"/>


<c:if test="${empty catnolist}">
	<c:set var="list"  value="${subCategorySvc.all}"/>
</c:if>
	
	

<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<c:if test="${not empty errorMsgs}">
				<font color='red'>請修正以下錯誤:
				<ul>
					<c:forEach var="message" items="${errorMsgs}">
						<li>${message}</li>
					</c:forEach>
				</ul>
				</font>
			</c:if>			
			<h1 class="page-header">商品分類管理</h1>
		</div>
			<!-- /.col-lg-12 -->
	</div>
		<!-- /.row -->
		<div class="row">
			<div class="col-lg-12" >
				<div class="panel panel-default" >
					<div class="panel-heading">
					<form id="searchStatus" role="form" method="post"
						ACTION="<%=request.getContextPath()%>/back/Category.do">

						主分類列表 <select id="searchList" name="catnolistno" size="1">
							<option value="0">全部分類
								<c:forEach var="categoryVO" items="${categorySvc.all}">
									<option value="${categoryVO.catno}" ${(sessionScope.subListSatus == categoryVO.catno)? "selected":"" }>${categoryVO.catname}
								</c:forEach>
						</select> <input class="btn btn-success btn-sm" style="margin-top: -5px;"
							type="submit" value="查詢"> <input type="hidden"
							name="action" value="querycatlist"> <input type="hidden"
							name="requestURL" value="<%=request.getServletPath()%>">
						<!--送出本網頁的路徑給Controller-->
						<input id="ListStatus" name="subListSatus" type="hidden"
							value="${sessionScope.subListSatus==null? '0': sessionScope.subListSatus}">
					</form>
					<input class="btn btn-success btn-sm"	style="float:right;margin-top:-28px;" type="button" data-toggle="modal"	data-target="#addsubform" value="新增子分類">
						<input class="btn btn-success btn-sm"	style="float:right;margin-top:-28px; margin-right:5px;" type="button" data-toggle="modal"	data-target="#addform" value="新增主分類" >
						<input class="btn btn-danger btn-sm"	style="float:right;margin-top:-28px;margin-right:5px;" type="button" data-toggle="modal"	data-target="#deletmajorform" value="刪除主分類">
					</div>

					<!-- /.panel-heading -->
					<div  class="panel-body" id="tabelans">
						<div class="table-responsive" id="tabelans2">
							<table class="table table-striped table-bordered table-hover"
								id="dataTables-example">
								<thead>
									<tr>
										<th>分類</th>
										<th>子分類</th>
										<th>功能</th>
									</tr>
								</thead>
<%@ include file="page1.file" %>	 	 						
									<c:forEach var="subCategory" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>" varStatus="s" >
										<tr class="${(subCategory.subcatname==param.subcatname) ? 'success':''}" >
											<td >
												<c:if test="${subCategory.catno==categorySvc.getOneCategory(subCategory.catno).catno}">
													${ categorySvc.getOneCategory(subCategory.catno).catname}
												</c:if>
											</td>	
											<td>${subCategory.subcatname}</td>
											<td class="center" style="width: 150px;" >
												<input type="hidden" value="${subCategory.subcatname}">
												<input type="hidden" value="${subCategory.catno}">
												<input type="hidden" value="${subCategory.subcatno}">
												<input	id="updatebt_${s.index}"	class="btn btn-outline btn-success" type="button" value="修改"> 
												<input	id="deletebt_${s.index}"	class="btn btn-outline btn-danger" type="button" value="刪除">
												 
											</td>
										</tr>
									</c:forEach>
								
							</table>
<%@ include file="page2.file" %>							
						</div>
						
					</div>
				</div>
				<!-- /.panel-body -->
			</div>
			<!-- /.panel -->
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<div id="test"></div>
	<!-- /.row -->
	<div class="modal fade" id="addform" role="dialog"	aria-labelledby="AddModal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="AddModal">新增主分類</h4>
				</div>
				<form role="form" method="post" ACTION="<%=request.getContextPath()%>/back/Category.do" >
					<div class="modal-body">
						<div class="form-group">
							<label>新增主分類名稱</label> 
							<input class="form-control" name="catname" placeholder="請輸入主分類名稱"><br> 
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
						<button type="submit" class="btn btn-success">新增</button>
						<input type="hidden" name="action" value="addCategory">
												
						<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
			   			<input type="hidden" name="whichPage"	value="<%=whichPage%>"><!--送出當前是第幾頁給Controller-->
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="modal fade" id="addsubform" role="dialog"	aria-labelledby="AddSubModal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="AddSubModal">新增子分類</h4>
				</div>
				<form role="form" method="post" ACTION="<%=request.getContextPath()%>/back/Category.do" >
					<div class="modal-body">
						<div class="form-group">
							<label>子分類名稱:</label>
								 <input class="form-control" name="subcatname" placeholder="請輸入新增子分類名稱"><br> 
							<label>隸屬的主分類</label>
							<select class="form-control" size="1" name="catno" >
								<c:forEach var="categoryVO" items="${categorySvc.all}">
									<option  value="${categoryVO.catno}" ${(sessionScope.subListSatus == categoryVO.catno)? "selected":"" }>${categoryVO.catname}
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
						<button type="submit" class="btn btn-success">新增</button>
						<input type="hidden" name="action" value="addSubCategory">
						<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
			   			<input type="hidden" name="whichPage"	value="<%=whichPage%>"><!--送出當前是第幾頁給Controller-->
						
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="modal fade" id="updateform" role="dialog"	aria-labelledby="updateModal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="updateModal">修改子分類</h4>
				</div>
				<form role="form" method="post" ACTION="<%=request.getContextPath()%>/back/Category.do" >
					<div class="modal-body">
					<label>子分類名稱</label>
						<input id="changesubcatname" class="form-control"  type="text" name="subcatname" value=""><br>
						<label>隸屬的主分類</label>
						<select	class="form-control" size="1" name="catno" id="changecatno">
								<c:forEach var="categoryVO" items="${categorySvc.all}">
									<option  value="${categoryVO.catno}" >${categoryVO.catname}
								</c:forEach>
						</select>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
						<button type="submit" class="btn btn-success">修改</button>
						<input  id="changesubcatno"	type="hidden" name="subcatno" >
						<input type="hidden" name="action" value="updateSubCategory">
						<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>" ><!--送出本網頁的路徑給Controller-->
			   			<input type="hidden" name="whichPage"	value="<%=whichPage%>" ><!--送出當前是第幾頁給Controller-->
						<input id="changeListStatus" name="subListSatus" type="hidden" value="">
					</div>
				</form>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="deletform" role="dialog"	aria-labelledby="deletModal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="deletModal">刪除子分類</h4>
				</div>
				<form role="form" method="post" ACTION="<%=request.getContextPath()%>/back/Category.do" name="insertform">
					<div class="modal-body">
						<p id="deletmessage" ></p>
					</div>
					<div class="modal-footer">
					
						<button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
						<button type="submit" class="btn btn-success">刪除</button>
						<input id="deletsubcatno" type="hidden" name="subcatno" > 
						<input type="hidden" name="action" value="deleteSubCategory">
						<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
			   			<input type="hidden" name="whichPage"	value="<%=whichPage%>"><!--送出當前是第幾頁給Controller-->
					
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="modal fade" id="deletmajorform" role="dialog"	aria-labelledby="deletmajorModal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="deletmajorModal">刪除子分類</h4>
				</div>
				<form role="form" method="post" ACTION="<%=request.getContextPath()%>/back/Category.do" name="insertform">
					<div class="modal-body">
						<label>要刪除的主分類</label>
						<select class="form-control" size="1" name="catno" >
								<c:forEach var="categoryVO" items="${categorySvc.all}">
									<option  value="${categoryVO.catno}">${categoryVO.catname}
								</c:forEach><br>
						</select>
					</div>
					                               
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
						<button type="submit" class="btn btn-danger">刪除</button>
						
						<input type="hidden" name="action" value="deleteCategory">
						<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
			   			<input type="hidden" name="whichPage"	value="<%=whichPage%>"><!--送出當前是第幾頁給Controller-->
					</div>
				</form>
			</div>
		</div>
	</div>
<jsp:include page="/b/frag/b_footer1.jsp" />
<jsp:include page="/b/frag/b_footer2.jsp" />

<script>
		$(document).ready(function() {
			
				
			
			$('#dataTables-example').dataTable({
					bLengthChange:false,
					bPaginate:false,
					bInfo:false,
					bFilter:false});
					
			
			$("input[id^='delete']").click(function(){
				var subCatnoValue=$(this).prev().prev().val();
				var subCatnameValue=$(this).prev().prev().prev().prev().val();
				$("#deletsubcatno").attr("value",subCatnoValue);
				$("#deletmessage").html("確定要刪這項<strong style='color:red;'>"+subCatnameValue+"</strong>子分類嗎?");
				$("#deletform").modal();
				
			});
			
			$("input[id^='update']").click(function(){
				var subCatnoValue=$(this).prev().val();
				var catnoValue=$(this).prev().prev().val();
				var subCatnameValue=$(this).prev().prev().prev().val();
				$("#changesubcatno").val(subCatnoValue);
				$("#changesubcatname").val(subCatnameValue);
				$("#changecatno")[0].selectedIndex=catnoValue-1;
				$("#updateform").modal();
								
			});
			
		
			
		
		});
		
</script>
