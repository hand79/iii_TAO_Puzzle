<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tao.wtdReq.model.*"%>

<jsp:useBean id="listWtdReq_ByCompositeQuery" scope="request"
	type="java.util.List" />
<jsp:useBean id="wtdSvc" scope="page"
	class="com.tao.wtdReq.model.WtdReqService" />
<%
	String resPath = request.getContextPath() + "/b/res";
	pageContext.setAttribute("resPath", resPath);

	String contextPath = request.getContextPath() + "/wtdReq";
	pageContext.setAttribute("contextPath", contextPath);
%>

<jsp:include page="/b/frag/b_header1.jsp" flush="true" />

<title>�I���{���f�� | ��ݺ޲z�t��</title>

<jsp:include page="/b/frag/b_header2AndMenu.jsp" />
<div id="page-wrapper">

	<div class="row">
		<div class="col-lg-12">
			<h1 class="page-header">�I�{�����C��</h1>
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
					<h4 style="font-family: �L�n������; margin: 0px">
						<b>�j�M</b>
					</h4>
				</div>
				<div class="panel-body">
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/back/wtdReq/wtdReq.do"
						name="form1" role="form">

						<div class="col-sm-2">
							<div class="form-group">
								<label>�I�{�ӽнs��</label> <input type="text" class="form-control"
									name="wtdreqno" value="">
							</div>
						</div>

						<div class="col-sm-2">
							<div class="form-group">
								<label>�|���s��</label> <input type="text" class="form-control"
									name="memno" value="">
							</div>
						</div>

						<div class="col-sm-2">
							<div class="form-group">
								<label>���B</label> <input type="text" class="form-control"
									name="wtdamnt" value="">
							</div>
						</div>

						<div class="col-sm-2">
							<div class="form-group">
								<label>���A</label> <select name="reqsts" class="form-control">
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
								<button type="reset"
									class="form-control btn btn-outline btn-warning">�M��</button>
							</div>
						</div>

						<div class="col-sm-2">
							<div class="form-group">
								<label>&nbsp;</label>
								<button class="form-control btn btn-outline btn-success">
									<i class="fa fa-search"></i> �j�M
								</button>
								<input type="hidden" name="action"
									value="listWtdReq_ByCompositeQuery_B">
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
				<div class="panel-heading">�j�M���G</div>
				<!-- /.panel-heading -->
				<div class="panel-body">
					<div class="table-responsive">
						<table class="table table-striped table-bordered table-hover"
							id="dataTables-example">
							<thead>
								<tr>
									<th>�s��</th>
									<th>�ӽЮɶ�</th>
									<th>�|���s��</th>
									<th>�I�{���B</th>
									<th>�Ȧ�b��</th>
									<th>�ӽЪ��A</th>
									<th>�T�{</th>
									<th>�R��</th>
								</tr>
							<thead>
							<tbody id="trchange">

								<c:if test="${empty listWtdReq_ByCompositeQuery}">
									<tr>
										<td colspan="7">�L���!</td>
									</tr>
								</c:if>

								<c:forEach var="wtdReqVO" items="${listWtdReq_ByCompositeQuery}">
									<tr align='left' class="odd gradeX">
										<td>${wtdReqVO.wtdreqno}</td>
										<td>${wtdReqVO.wtdreqt}</td>
										<td>${wtdReqVO.memno}</td>
										<td>${wtdReqVO.wtdamnt}</td>
										<td>${wtdReqVO.wtdac}</td>
										<td>${wtdReqVO.reqsts}</td>
										<td><FORM METHOD="post"
												ACTION="<%=request.getContextPath()%>/back/wtdReq/wtdReq.do">
												<c:if
													test="${wtdReqVO.reqsts == 'CANCELLED' or wtdReqVO.reqsts == 'COMPLETED'}">
													<input type="submit" class="btn btn-default disabled"
														value="�w�T�{">
												</c:if>
												<c:if test="${wtdReqVO.reqsts == 'WAITING'}">
													<input type="submit" class="btn btn-success" value="�T�{">
												</c:if>
												<input type="hidden" name="wtdreqno"
													value="${wtdReqVO.wtdreqno}"> <input type="hidden"
													name="requestURL" value="<%=request.getServletPath()%>">
												<input type="hidden" name="action" value="getOne_For_Update">
											</FORM></td>
										<td><FORM METHOD="post"
												ACTION="<%=request.getContextPath()%>/back/wtdReq/wtdReq.do">
												<input type="submit" class="btn btn-danger" value="�R��">
												<input type="hidden" name="wtdreqno"
													value="${wtdReqVO.wtdreqno}"> <input type="hidden"
													name="requestURL" value="<%=request.getServletPath()%>">
												<input type="hidden" name="action" value="delete">
											</FORM></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<!-- /.panel panel-default -->
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
</div>
<!-- /#page-wrapper -->

<jsp:include page="/b/frag/b_footer1.jsp" />
<script>
	$(document).ready(function() {
		$('#dataTables-example').dataTable(

		{
			"language" : {
				"paginate" : {
					"next" : "�U�@��",
					"last" : "�̥���",
					"previous" : "�W�@��",
					"first" : "�Ĥ@��",
					"emptyTable" : "�L���"

				},
				"info" : "��  _PAGE_ ���A�@  _PAGES_ ��  ( _TOTAL_ ����� )",
				"lengthMenu" : "�C�����  _MENU_ ��",
				"search" : "�j�M���G�L�o: ",
				"infoEmpty" : "�@ 0 ���ŦX",
				"infoFiltered" : " (��  _MAX_ ���L�o�Ө�)",
				"zeroRecords" : "�L�ŦX�����"
			}
		}

		);
	});
</script>
<jsp:include page="/b/frag/b_footer2.jsp" />