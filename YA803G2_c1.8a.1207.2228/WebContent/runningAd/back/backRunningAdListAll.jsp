<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.tao.runningad.model.*"%>
<%-- �����m�߱ĥ� EL ���g�k���� --%>

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

<title> �����s�i�޲z </title>

<jsp:include page="/b/frag/b_header2AndMenu.jsp"/>

      <div id="page-wrapper">
          <div class="row">
              <div class="col-lg-12">
                  <h1 class="page-header">���������s�i�޲z</h1>
              </div>
              <!-- /.col-lg-12 -->
          </div>
          <!-- /.row -->
          
          <div class="row">
			<div class="col-lg-12"  >
				<div class="panel panel-success" style="border-width:1px; border-color : #34B2E5;"> <!--  panel body color and settings, exclude header bar color -->
					<div class="panel-heading">
						<h4 style="font-family: �L�n������; margin: 0px"><b>�s�i�j�M</b></h4>
					</div>
				<div class="panel-body">							
					<form id="form1" role="form" METHOD="post" ACTION="<%=request.getContextPath()%>/back/runningAdC" name="form1">
	
						<div class="col-sm-3">
							<div class="form-group">
								<label>�s�i���A</label>
								<select class="form-control" name="tst">
									<option value= "999"
										<c:if test="${oldTst == 999}">
										selected
										</c:if>
									>�Ҧ����A </option>
									<option value="0"
										<c:if test="${oldTst == 0}">
										selected
										</c:if>
									>�f�֤�</option>
									<option value="1"
										<c:if test="${oldTst == 1}">
										selected
										</c:if>
									>�f�ֳq�L</option>
									<option value="2"
										<c:if test="${oldTst == 2}">
										selected
										</c:if>
									>�w�U�[</option>
									<option value="3"
										<c:if test="${oldTst == 3}">
										selected
										</c:if>
									>�f�֤��q�L</option>
								</select>
							</div>
						</div>
						
						<div class="col-sm-3">
							<div class="form-group">
								<label>&nbsp;</label>
								<input type="hidden" name="action" value="backSearchByTst">
								<input type="hidden" name="action" value="backSearchByTst">
								<button class="form-control btn btn-success"><i class="fa fa-search"></i> �j�M</button>
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
                                                                �s�i�ӽЩ���
                      </div>
                      <!-- /.panel-heading -->
                      <div class="panel-body">
                          <div class="table-responsive">
                              <table class="table table-bordered table-hover" id="dataTables-example">
                               <thead>
								<tr>
								    <td>�s�i�s��</td>
									<th>�s�i�Ϥ�</th>
									<th>�}�l���</th>
									<th>�������</th>
									<th>�ӽз|���s��</th>
									<th>�ӽФ��</th>
									<th>���A</th>
									<th>�W�u�Ѽ�</th>
									<th>�s�i�s��</th>
									<th>�ӽЮ֭�</th>
									<th>�ӽЧ_�M</th>
								</tr>
							</thead>
							<tbody>	
								<%@ include file="/runningAd/back/page1.file" %> 
								<c:forEach var="dao" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
									<tr align='center' valign='middle' ${(dao.adno==param.adno) ? 'bgcolor=#CCCCFF':''}><!--�N�ק諸���@���[�J����Ӥw-->
										<td>${dao.adno}</td>
										<td> 
											<input id="showPicbt" class="btn btn-success btn-sm" type="button" data-toggle="modal"	data-target="#adPicModal" value="�I���ݼs�i�Ӥ���">
										<%-- <a href="<%=request.getContextPath()%>/DBRunningAdImgReader?adno=${dao.adno}" target="_blank">�I���ݼs�i�Ӥ��� </a> --%>
										</td>
										<%--<td><img src="<%=request.getContextPath()%>/DBImgReader?memno=${dao.adno}" width="100px" height="100px"></td>
										 <td>${dao.adpic}</td> --%>
										<td>${dao.sdate}</td>
										<td>${dao.edate}</td>
										<td>${dao.memno}</td>
										<td>${dao.reqtime}</td>
										<td>${dao.tst}
											<c:if test="${dao.tst == '0'}">
												- �f�֤�
											</c:if>
											<c:if test="${dao.tst == '1'}">
												- �f�ֳq�L
											</c:if>
											<c:if test="${dao.tst == '2'}">
												- �w�U�[
											</c:if>
											<c:if test="${dao.tst == '3'}">
												- �f�֤��q�L
											</c:if>
										</td>
										<td>${dao.dtime}</td>
										<td>${dao.caseno}</td>
										
										<td>
										  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back/runningAdC">
										     
										      
										      <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller-->
										    <input type="hidden" name="whichPage"	value="<%=whichPage%>">               <!--�e�X��e�O�ĴX����Controller-->
										     <input type="hidden" name="action"	value="update_For_Approve">
										     <input type="hidden" class="adnoHolder" name="adno" value="${dao.adno}">
										     <input	class="btn btn-outline btn-success" type="button" 
										     data-toggle="modal" id="btnApprove" data-target="#approveModal" value="�֭�"
										     <c:if test="${dao.tst == '1' || dao.tst == '2'}">
												disabled
											</c:if>>
										     </FORM>
										</td>
										<td>
										  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back/runningAdC">
										      
										     
										      <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller-->
										    <input type="hidden" name="whichPage"	value="<%=whichPage%>">               <!--�e�X��e�O�ĴX����Controller-->
										     <input type="hidden" name="action"	value="update_For_Reject">
										     <input type="hidden" class="adnoHolder" name="adno" value="${dao.adno}">
										     <input	class="btn btn-outline btn-danger" type="button" 
										     data-toggle="modal" id="btnReject" data-target="#rejectModal" value="�_�M"
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
					<h4 class="modal-title" id="AdModal">�s�i�Ӥ�</h4>
				</div>
				<div class="modal-body">
					<p id="showPic">  </p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">����</button>
				</div>	
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="approveModal" role="dialog" aria-labelledby="apprModal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close"  data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="apprModal">�֭�ӽ�</h4>
				</div>
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back/runningAdC">
				<div class="modal-body">
					<p id="approveline"> �O�_�T�w�n�֭㥻�ӽ�? </p>
				</div>
				<div class="modal-footer">
					<input type="hidden" name="action"	value="update_For_Approve">
					<input type="hidden" id="ApproveModalAdno" name="adno" value="">	
					<button type="button" class="btn btn-default" data-dismiss="modal">����</button>
					<button type="submit" class="btn btn-success">�T�w</button>
					<input type="hidden" name="requestURL"	value="<%=request.getParameter("requestURL")%>"><!--�e�X�����������|��Controller-->
			   		<input type="hidden" name="whichPage"	value="<%=request.getParameter("whichPage")%>"><!--�e�X��e�O�ĴX����Controller-->
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
					<h4 class="modal-title" id="rejModal">�_�M�ӽ�</h4>
				</div>
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back/runningAdC">
				<div class="modal-body">
					<p id="rejectline"> �O�_�T�w�n�_�M���ӽ�? </p>
				</div>
				<div class="modal-footer">
					<input type="hidden" name="action"	value="update_For_Reject">
					<input type="hidden" id="RejectModalAdno" name="adno" value="">
					<button type="button" class="btn btn-default" data-dismiss="modal">����</button>
					<button type="submit" class="btn btn-success">�T�w</button>
					<input type="hidden" name="requestURL"	value="<%=request.getParameter("requestURL")%>"><!--�e�X�����������|��Controller-->
			   		<input type="hidden" name="whichPage"	value="<%=request.getParameter("whichPage")%>"><!--�e�X��e�O�ĴX����Controller-->
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