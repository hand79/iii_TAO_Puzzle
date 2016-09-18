<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.tao.runningad.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<jsp:useBean id="runSvc" scope="page" class="com.tao.runningad.model.RunningAdService" />

<c:set var="list" value="${runlist}" />

<c:if test="${empty list}" >
	<c:set var="list" value="${runSvc.all}" />
</c:if>

<%
	Integer oldTst = (Integer) request.getAttribute("oldTst");
%>
<jsp:include page="/b/frag/b_header1.jsp"/>
<style>
.modal .modal-dialog { width: 950px; }

#dataTables-example tbody tr:nth-child(odd) {
     background-color: #F5F5F5;
     }
</style>

<title> 輪播廣告管理 </title>

<jsp:include page="/b/frag/b_header2AndMenu.jsp"/>

      <div id="page-wrapper">
          <div class="row">
              <div class="col-lg-12">
                  <h1 class="page-header">首頁輪播廣告管理</h1>
              </div>
              <!-- /.col-lg-12 -->
          </div>
          <!-- /.row -->
          
          <div class="row">
			<div class="col-lg-12"  >
				<div class="panel panel-success" style="border-width:1px; border-color : #34B2E5;"> <!--  panel body color and settings, exclude header bar color -->
					<div class="panel-heading">
						<h4 style="font-family: 微軟正黑體; margin: 0px"><b>廣告搜尋</b></h4>
					</div>
				<div class="panel-body">							
					<form id="form1" role="form" METHOD="post" ACTION="<%=request.getContextPath()%>/back/runningAdC" name="form1">
	
						<div class="col-sm-3">
							<div class="form-group">
								<label>廣告狀態</label>
								<select class="form-control" name="tst">
									<option value= "999"
										<c:if test="${oldTst == 999}">
										selected
										</c:if>
									>所有狀態 </option>
									<option value="0"
										<c:if test="${oldTst == 0}">
										selected
										</c:if>
									>審核中</option>
									<option value="1"
										<c:if test="${oldTst == 1}">
										selected
										</c:if>
									>審核通過</option>
									<option value="2"
										<c:if test="${oldTst == 2}">
										selected
										</c:if>
									>已下架</option>
									<option value="3"
										<c:if test="${oldTst == 3}">
										selected
										</c:if>
									>審核不通過</option>
								</select>
							</div>
						</div>
						
						<div class="col-sm-3">
							<div class="form-group">
								<label>&nbsp;</label>
								<input type="hidden" name="action" value="backSearchByTst">
								<input type="hidden" name="action" value="backSearchByTst">
								<button class="form-control btn btn-success"><i class="fa fa-search"></i> 搜尋</button>
							</div>
						</div>	
									
					</form>
					
					
				</div>
                      
				</div><!--/.panel .panel-info-->
			</div>
		</div>
          
          <div class="row">
              <div class="col-lg-12">
                  <div class="panel panel-default" style="border-width:1px; border-color : #34B2E5;">
                      <div class="panel-heading">
                                                                廣告申請明細
                      </div>
                      <!-- /.panel-heading -->
                      <div class="panel-body">
                          <div class="table-responsive">
                              <table class="table table-bordered table-hover" id="dataTables-example">
                               <thead>
								<tr>
								    <td>廣告編號</td>
									<th>廣告圖片</th>
									<th>開始日期</th>
									<th>結束日期</th>
									<th>申請會員編號</th>
									<th>申請日期</th>
									<th>狀態</th>
									<th>上線天數</th>
									<th>廣告編號</th>
									<th>申請核准</th>
									<th>申請否決</th>
								</tr>
							</thead>
							<tbody>	
								<%@ include file="/runningAd/back/page1.file" %> 
								<c:forEach var="dao" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
									<tr align='center' valign='middle' ${(dao.adno==param.adno) ? 'bgcolor=#CCCCFF':''}><!--將修改的那一筆加入對比色而已-->
										<td>${dao.adno}</td>
										<td> 
											<input id="showPicbt" class="btn btn-success btn-sm" type="button" data-toggle="modal"	data-target="#adPicModal" value="點此看廣告照片檔">
										<%-- <a href="<%=request.getContextPath()%>/DBRunningAdImgReader?adno=${dao.adno}" target="_blank">點此看廣告照片檔 </a> --%>
										</td>
										<%--<td><img src="<%=request.getContextPath()%>/DBImgReader?memno=${dao.adno}" width="100px" height="100px"></td>
										 <td>${dao.adpic}</td> --%>
										<td>${dao.sdate}</td>
										<td>${dao.edate}</td>
										<td>${dao.memno}</td>
										<td>${dao.reqtime}</td>
										<td>${dao.tst}
											<c:if test="${dao.tst == '0'}">
												- 審核中
											</c:if>
											<c:if test="${dao.tst == '1'}">
												- 審核通過
											</c:if>
											<c:if test="${dao.tst == '2'}">
												- 已下架
											</c:if>
											<c:if test="${dao.tst == '3'}">
												- 審核不通過
											</c:if>
										</td>
										<td>${dao.dtime}</td>
										<td>${dao.caseno}</td>
										
										<td>
										  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back/runningAdC">
										     
										      
										      <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
										    <input type="hidden" name="whichPage"	value="<%=whichPage%>">               <!--送出當前是第幾頁給Controller-->
										     <input type="hidden" name="action"	value="update_For_Approve">
										     <input type="hidden" class="adnoHolder" name="adno" value="${dao.adno}">
										     <input	class="btn btn-outline btn-success" type="button" 
										     data-toggle="modal" id="btnApprove" data-target="#approveModal" value="核准"
										     <c:if test="${dao.tst == '1' || dao.tst == '2'}">
												disabled
											</c:if>>
										     </FORM>
										</td>
										<td>
										  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back/runningAdC">
										      
										     
										      <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
										    <input type="hidden" name="whichPage"	value="<%=whichPage%>">               <!--送出當前是第幾頁給Controller-->
										     <input type="hidden" name="action"	value="update_For_Reject">
										     <input type="hidden" class="adnoHolder" name="adno" value="${dao.adno}">
										     <input	class="btn btn-outline btn-danger" type="button" 
										     data-toggle="modal" id="btnReject" data-target="#rejectModal" value="否決"
										     <c:if test="${dao.tst == '3' || dao.tst == '2'}">
												disabled
											</c:if>
										     >
										     </FORM>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<%@ include file="/member/back/page2.file" %>

                              
                          </div>
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

	<div class="modal fade" id="adPicModal" role="dialog" aria-labelledby="AdModal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close"  data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="AdModal">廣告照片</h4>
				</div>
				<div class="modal-body">
					<p id="showPic">  </p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
				</div>	
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="approveModal" role="dialog" aria-labelledby="apprModal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close"  data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="apprModal">核准申請</h4>
				</div>
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back/runningAdC">
				<div class="modal-body">
					<p id="approveline"> 是否確定要核准本申請? </p>
				</div>
				<div class="modal-footer">
					<input type="hidden" name="action"	value="update_For_Approve">
					<input type="hidden" id="ApproveModalAdno" name="adno" value="">	
					<button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
					<button type="submit" class="btn btn-success">確定</button>
					<input type="hidden" name="requestURL"	value="<%=request.getParameter("requestURL")%>"><!--送出本網頁的路徑給Controller-->
			   		<input type="hidden" name="whichPage"	value="<%=request.getParameter("whichPage")%>"><!--送出當前是第幾頁給Controller-->
				</div>	
				</form>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="rejectModal" role="dialog" aria-labelledby="rejModal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close"  data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="rejModal">否決申請</h4>
				</div>
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back/runningAdC">
				<div class="modal-body">
					<p id="rejectline"> 是否確定要否決本申請? </p>
				</div>
				<div class="modal-footer">
					<input type="hidden" name="action"	value="update_For_Reject">
					<input type="hidden" id="RejectModalAdno" name="adno" value="">
					<button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
					<button type="submit" class="btn btn-success">確定</button>
					<input type="hidden" name="requestURL"	value="<%=request.getParameter("requestURL")%>"><!--送出本網頁的路徑給Controller-->
			   		<input type="hidden" name="whichPage"	value="<%=request.getParameter("whichPage")%>"><!--送出當前是第幾頁給Controller-->
				</div>	
				</form>
			</div>
		</div>
	</div>
	
 <jsp:include page="/b/frag/b_footer1.jsp"/>
 <script>menuTrigger(0);</script>
 <jsp:include page="/b/frag/b_footer2.jsp"/>
 
 <script>
		$(document).ready(function() {
			
		
			$("input[id^='showPicbt']").click(function(){
				var adnoValue=$(this).parent().parent().find("input.adnoHolder").val();
				$("#showPic").html("<img src='<%=request.getContextPath()%>/DBRunningAdImgReader?adno=" + adnoValue + "' />");
			});
			
			$("input[id^='btnApp']").click(function(){
				var adnoValue=$(this).prev().val();
				$("#ApproveModalAdno").attr("value", adnoValue);
			});
			
			$("input[id^='btnRej']").click(function(){
				var adnoValue=$(this).prev().val();
				$("#RejectModalAdno").attr("value", adnoValue);
			});
		
		});
		
</script>