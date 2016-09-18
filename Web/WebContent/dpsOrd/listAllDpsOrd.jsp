<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.tao.dpsOrd.model.*"%>
<jsp:useBean id="memSvc" scope="page" class="com.tao.member.model.MemberService"/>
<%
	if(request.getAttribute("listDpsOrd_ByCompositeQuery_B") == null){
		DpsOrdService dpsSvc = new DpsOrdService();
		List<DpsOrdVO> list = dpsSvc.getAll();
		pageContext.setAttribute("listDpsOrd_ByCompositeQuery_B", list);
	}
%>

<%
	String resPath = request.getContextPath() + "/b/res";
	pageContext.setAttribute("resPath", resPath);

	String contextPath = request.getContextPath() + "/dpsOrd";
	pageContext.setAttribute("contextPath", contextPath);
%>

<jsp:include page="/b/frag/b_header1.jsp" flush="true" />

<title>���ڽT�{ | ��ݺ޲z�t��</title>

<jsp:include page="/b/frag/b_header2AndMenu.jsp" />

<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">�x�Ȭ����C��</h1>
			<c:if test="${not empty errorMsgs}">
				<font color='red'>�Эץ��H�U���~:
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
							<h4 style="font-family: �L�n������; margin: 0px"><b>�j�M</b></h4>
                        </div>
                        <div class="panel-body">		
                         <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back/dpsOrd/dpsOrd.do" name="form1"  role="form">					
								
								<div class="col-sm-2">
									<div class="form-group">
										<label>�x�ȭq��s��</label>
										<input type="text"  class="form-control" name="dpsordno" value="">
									</div>
								</div>
								
								<div class="col-sm-2">
									<div class="form-group">
										<label>�|���s��</label>
										<input type="text"  class="form-control" name="memno" value="">
									</div>
								</div>
								
								<div class="col-sm-2">
									<div class="form-group">
										<label>���B</label>
										<input type="text"  class="form-control" name="dpsamnt" value="">
									</div>
								</div>
								
								<div class="col-sm-2">
									<div class="form-group">
										<label>���A</label>
										<select name="ordsts" class="form-control">
											<option value="">
											<option value="WAITING">�ݼf��
											<option value="CANCELLED">����
											<option value="COMPLETED">����
										</select>
									</div>
								</div>
								
								<div class="col-sm-2">
									<div class="form-group">
										<label>&nbsp;</label>
										<button type="reset" class="form-control btn-outline btn btn-warning">�M��</button>
									</div>
								</div>	
								
								<div class="col-sm-2">
									<div class="form-group">
										<label>&nbsp;</label>
										<button class="form-control btn btn-outline btn-success"><i class="fa fa-search"></i> �j�M</button>
										<input type="hidden" name="action" value="listDpsOrd_ByCompositeQuery_B">
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
				<div class="panel-heading">�Ҧ��x�Ȭ���</div>
				<!-- /.panel-heading -->
				<div class="panel-body">
					<div class="table-responsive">
						<table class="table table-striped table-bordered table-hover"
							id="dataTables-example">
							<thead>
								<tr>
									<th>�s��</th>
									<th>�ɶ�</th>
									<th>���B</th>
									<th>�|���s��</th>
									<th>�I�ڤ覡</th>
									<th>�ӽЪ��A</th>
									<th>��b�b��</th>
									<th>�T�{</th>
									<th>�R��</th>
								</tr>
							</thead>
							<tbody>
							<c:set var="memlist" value="${memSvc.all}"/>
								<c:forEach var="dpsOrdVO" items="${listDpsOrd_ByCompositeQuery_B}">
									<tr class="odd gradeX">
										<td>${dpsOrdVO.dpsordno}</td>
										<td>${dpsOrdVO.dpsordt}</td>
										<td>${dpsOrdVO.dpsamnt}</td>
										<td><c:forEach var="memVO" items="${memlist}">
										<c:if test="${memVO.memno == dpsOrdVO.memno}">
										<a class="pointer"><i class="fa fa-fw fa-user"></i>${memVO.memid}(${memVO.memno})</a>
										</c:if>
										</c:forEach></td>
										<td>${dpsOrdVO.dpshow}</td>
										<td>${dpsOrdVO.ordsts}</td>
										<td>${dpsOrdVO.atmac}</td>
										<td>
											<FORM METHOD="post"
												ACTION="<%=request.getContextPath()%>/dpsOrd/dpsOrd.do">
												
												<c:if test="${dpsOrdVO.ordsts =='CANCELLED' or dpsOrdVO.ordsts=='COMPLETED'}">
												<input type="submit" class="btn btn-default disabled" value="�w�T�{">
												</c:if>
												<c:if test="${dpsOrdVO.ordsts == 'WAITING'}">
												<input type = "submit" class="btn btn-warning" value="�T�{">
												</c:if>
												<input type="hidden" name="dpsordno" value="${dpsOrdVO.dpsordno}">
												<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
												<input type="hidden" name="action" value="getOne_For_Update">
											</FORM>
										</td>
										<td>
											<FORM METHOD="post"
												ACTION="<%=request.getContextPath()%>/dpsOrd/dpsOrd.do">
												<input type="submit" class="btn btn-danger" value="�R��">
												<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
												<input type="hidden" name="dpsordno" value="${dpsOrdVO.dpsordno}"> 
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
	</div>
	<!-- /.row -->
</div>
<!-- /#page-wrapper -->


<jsp:include page="/b/frag/b_footer1.jsp" />



<script>
menuTrigger(3);
	$(document).ready(function() {
		
		$('#dataTables-example').dataTable(
				{
					"language" : {
						"paginate" : {
							"next" : "�U�@��",
							"last": "�̥���",
							"previous" : "�W�@��",
							"first":"�Ĥ@��",
							"emptyTable": "�L���"
							
						},
						"info": "��  _PAGE_ ���A�@  _PAGES_ ��  ( _TOTAL_ ����� )",
						"lengthMenu": "�C�����  _MENU_ ��",
						"search": "�j�M���G�L�o: ",
						"infoEmpty": "�@ 0 ���ŦX",
						"infoFiltered": " (��  _MAX_ ���L�o�Ө�)",
						"zeroRecords": "�L�ŦX�����"
					}
				}
				
		);
	});
</script>
<jsp:include page="/b/frag/b_footer2.jsp" />