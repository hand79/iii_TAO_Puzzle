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
<title>�X�ʮץ����|�C��</title>
<jsp:include page="/b/frag/b_header2AndMenu.jsp" />

<div id="page-wrapper">
	<div class="row">
		<div class="col-lg-12">
			<h3 class="page-header">�X�ʮץ����|�C��</h3>
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
                         <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back/caseRep/caseRep.do" name="form1"  role="form">					
								
								<div class="col-sm-2">
									<div class="form-group">
										<label>���|�רҽs��</label>
										<input type="text"  class="form-control" name="crepno" value="">
									</div>
								</div>
								
								<div class="col-sm-2">
									<div class="form-group">
										<label>�|���s��</label>
										<input type="text"  class="form-control" name="repno" value="">
									</div>
								</div>
								
								<div class="col-sm-2">
									<div class="form-group">
										<label>�����|�X�ʽs��</label>
										<input type="text"  class="form-control" name="susno" value="">
									</div>
								</div>
								
								<div class="col-sm-2">
									<div class="form-group">
										<label>�X�ʮ׽s��</label>
										<input type="text" name="repcaseno" value="" class="form-control" >
									</div>
								</div>
								
								<div class="col-sm-2">
									<div class="form-group">
										<label>&nbsp;</label>
										<button type="reset" class="form-control btn btn-warning">�M��</button>
									</div>
								</div>
								
								<div class="col-sm-2">
									<div class="form-group">
										<label>&nbsp;</label>
										<button class="form-control btn btn-success"><i class="fa fa-search"></i> �j�M</button>
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
				<div class="panel-heading">�Ҧ�����</div>
				<!-- /.panel-heading -->
				<div class="panel-body">
					<div class="table-responsive">
						<table class="table table-striped table-bordered table-hover"
							id="dataTables-example">
							<thead>
								<tr>
									<th>����</th>
									<th>���|�H</th>
									<th>�D��</th>
									<th>�X�ʮ�</th>
									<th>��]</th>
									<th>�U�[</th>
									<th>�R��</th>
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
										<input type="submit" class="btn btn-primary" value="�U�[">
										</c:if>
										<c:if test="${caseVO.status == 0 or caseVO.status == 4 or caseVO.status == 5 or caseVO.status == 6}">
										<input type="button" class="btn btn-default disabled" value="�U�[">
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
												<input type="submit" class="btn btn-danger" value="�R��">
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