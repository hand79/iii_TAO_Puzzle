<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.tao.caseRep.model.*"%>
<jsp:useBean id="caseSvc" scope="page" class="com.tao.cases.model.CasesService" />
<jsp:useBean id="memSvc" scope="page" class="com.tao.member.model.MemberService"/>
<%
	CaseRepService crepSvc = new CaseRepService();
	List<CaseRepVO> list = crepSvc.getAll();
	pageContext.setAttribute("list", list);
%>
<%
	String resPath = request.getContextPath() + "/b/res";
	pageContext.setAttribute("resPath", resPath);

	String contextPath = request.getContextPath() + "/shopRep";
	pageContext.setAttribute("contextPath", contextPath);
%>

<jsp:include page="/b/frag/b_header1.jsp" flush="true" />
<title>合購案件檢舉列表</title>
<jsp:include page="/b/frag/b_header2AndMenu.jsp" />

<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h3 class="page-header">合購案件檢舉列表</h3>
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
							<h4 style="font-family: 微軟正黑體; margin: 0px"><b>搜尋</b></h4>
                        </div>
                        <div class="panel-body">		
                         <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back/caseRep/caseRep.do" name="form1"  role="form">					
								
								<div class="col-sm-2">
									<div class="form-group">
										<label>檢舉案例編號</label>
										<input type="text"  class="form-control" name="crepno" value="">
									</div>
								</div>
								
								<div class="col-sm-2">
									<div class="form-group">
										<label>會員編號</label>
										<input type="text"  class="form-control" name="repno" value="">
									</div>
								</div>
								
								<div class="col-sm-2">
									<div class="form-group">
										<label>受檢舉合購編號</label>
										<input type="text"  class="form-control" name="susno" value="">
									</div>
								</div>
								
								<div class="col-sm-2">
									<div class="form-group">
										<label>合購案編號</label>
										<input type="text" name="repcaseno" value="" class="form-control" >
									</div>
								</div>
								
								<div class="col-sm-2">
									<div class="form-group">
										<label>&nbsp;</label>
										<button type="reset" class="form-control btn btn-warning">清除</button>
									</div>
								</div>
								
								<div class="col-sm-2">
									<div class="form-group">
										<label>&nbsp;</label>
										<button class="form-control btn btn-success"><i class="fa fa-search"></i> 搜尋</button>
										<input type="hidden" name="action" value="listCaseRep_ByCompositeQuery">
									</div>
								
								</div>	
									
							</FORM>
						</div>
                      
                    </div><!--/.panel .panel-info-->
				</div>
			</div>
	
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
				<div class="panel-heading">所有紀錄</div>
				<!-- /.panel-heading -->
				<div class="panel-body">
					<div class="table-responsive">
						<table class="table table-striped table-bordered table-hover"
							id="dataTables-example">
							<thead>
								<tr>
									<th>條目</th>
									<th>檢舉人</th>
									<th>主購</th>
									<th>合購案</th>
									<th>原因</th>
									<th>下架</th>
									<th>刪除</th>
								</tr>
							</thead>
							<tbody>
							<c:set var="caselist" value="${caseSvc.all}"/>
							<c:set var="memlist" value="${memSvc.all}"/>
								<c:forEach var="caseRepVO" items="${list}">
									<tr class="odd gradeX">
										<td>${caseRepVO.crepno}</td>
										<td><c:forEach var="memVO" items="${memlist}">
										<c:if test="${memVO.memno == caseRepVO.repno}">
										<a class="pointer"><i class="fa fa-fw fa-user"></i>${memVO.memid}(${memVO.memno})</a>
										</c:if>
										</c:forEach>
										</td>
										<td><c:forEach var="memVO" items="${memlist}">
										<c:if test="${memVO.memno == caseRepVO.susno}">
										<a class="pointer"><i class="fa fa-fw fa-user"></i>${memVO.memid}(${memVO.memno})</a>
										</c:if>
										</c:forEach></td>
										<td><c:forEach var="caseVO" items="${caselist}">
										<c:if test="${caseVO.caseno == caseRepVO.repcaseno}">
										<a href="<%=request.getContextPath()%>/cases/cases.do?action=caseDetail&caseno=${caseVO.caseno}
										">${caseVO.title}(${caseVO.caseno})</a>
										</c:if>
										</c:forEach></td>
										<td>${caseRepVO.creprsn}</td>
										<td>
										<FORM METHOD="post" action="<%=request.getContextPath()%>/back/cases/CancelCase.do">
										<c:forEach var="caseVO" items="${caselist}">
										<c:if test="${caseVO.caseno == caseRepVO.repcaseno}">
										<c:if test="${caseVO.status == 1 or caseVO.status == 2 or caseVO.status == 3}">
										<input type="submit" class="btn btn-primary" value="下架">
										</c:if>
										<c:if test="${caseVO.status == 0 or caseVO.status == 4 or caseVO.status == 5 or caseVO.status == 6}">
										<input type="button" class="btn btn-default disabled" value="下架">
										</c:if>
										</c:if>
										</c:forEach>
										<input type="hidden" name="action" value="CancelCase_from_back">
										<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
										<input type="hidden" name="repcaseno" value="${caseRepVO.repcaseno}">
										</FORM>
										</td>
										<td>
										<FORM METHOD="post"
												ACTION="<%=request.getContextPath()%>/back/caseRep/caseRep.do">
												<input type="submit" class="btn btn-danger" value="刪除">
												<input type="hidden" name="crepno" value="${caseRepVO.crepno}">
												<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
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
menuTrigger(2);
	$(document).ready(function() {
		$('#dataTables-example').dataTable(
				{
					"language" : {
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
				}		
		);
	});
</script>
<jsp:include page="/b/frag/b_footer2.jsp" />