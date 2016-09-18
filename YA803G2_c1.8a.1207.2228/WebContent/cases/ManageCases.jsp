<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@page import="com.tao.cases.model.*"%>
<%@page import="com.tao.jimmy.util.model.*"%>
<%@page import="com.tao.jimmy.location.*"%>
<% pageContext.setAttribute("contextPath", request.getContextPath());%>
<% pageContext.setAttribute("resPath", request.getContextPath() + "/b/res");%>
<% pageContext.setAttribute("pageHome", request.getContextPath() + "/cases");%>
<% pageContext.setAttribute("controllerPath", request.getContextPath()  + "/back/cases/cases.do");%>
<%	
	CountyService countyService = new CountyService();
	Set<CountyVO> counties = countyService.findCounties();
	pageContext.setAttribute("counties", counties);
%>
<jsp:useBean id="locSvc" scope="page" class="com.tao.jimmy.location.LocationService"/>
<jsp:useBean id="tinyMemSvc" scope="page" class="com.tao.jimmy.member.TinyMemberService"/>
<jsp:useBean id="cpSvc" scope="page" class="com.tao.cases.model.CaseProductService"/>

<%-- include header1 --%>	
<jsp:include page="/b/frag/b_header1.jsp"/>
<%-- <%@ include file="/b/frag/b_header1.jsp" %> --%>

 <link href="${resPath}/jquery-ui/jquery-ui.min.css"
	rel="stylesheet" type="text/css">
<link href="${resPath}/jquery-ui/jquery-ui.theme.min.css"
	rel="stylesheet" type="text/css">

<style>
.page-header {
	font-family: �L�n������;
}
.pointer{
	cursor: pointer;
}
.modal>*{
	font-family: �L�n������;
}

.modal-title{
	font-weight: bold;
}
</style>

<%-- include header2AndMenu --%>
<jsp:include page="/b/frag/b_header2AndMenu.jsp"/>

		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">�j�M�X�ʮ� <button class="btn btn-primary btn-outline pull-right" id="overCase">�ߧY���צX��</button></h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-info">
						<div class="panel-heading">
							<h4 style="font-family: �L�n������; margin: 0px">
								<b>�ƦX�j�M</b>
							</h4>
						</div>
						<div class="panel-body">
							<form role="form" id="queryForm">

								<div class="col-sm-2">
									<div class="form-group">
										<label>�X�ʮ׽s��</label> <input class="form-control" name="caseno" />
									</div>

								</div>
								<div class="col-sm-2">
									<div class="form-group">
										<label>�o�׷|���s��</label> <input class="form-control" name="memno" />
									</div>
								</div>
								<div class="col-sm-2">
									<div class="form-group">
										<label>�W�[���(�_)</label> <input type="text" class="form-control"
											name="stimefrom">
									</div>

								</div>

								<div class="col-sm-2">
									<div class="form-group">
										<label>�W�[���(�W)</label> <input type="text" class="form-control" 
											name="stimeto">
									</div>

								</div>

								<div class="col-sm-1">
									<div class="form-group">
										<label>����</label> <select class="form-control" name="county"
											id="county-list">
											<option value="">-</option>
											<c:forEach var="countyVO" items="${counties}">
												<option value="${countyVO.value}">${countyVO.name}</option>
											</c:forEach>
										</select>
									</div>

								</div>
								<div class="col-sm-1">
									<div class="form-group">
										<label>�m����</label> <select class="form-control" name="locno"
											id="town-list">
											<option value="">-</option>
										</select>
									</div>

								</div>
								<div class="col-sm-1">

									<div class="form-group">
										<label>&nbsp;</label>
										<button type="reset" class="form-control btn btn-info">���m</button>
									</div>
								</div>
								
								<div class="col-sm-1">
									<div class="form-group">
										<label style="margin-left:-10px">�j�M�覡</label>
										<select name="notMatch" class="pull-right" style="height:34px; ">
											<option value="">�ŦX��J����</option>
											<option value="yes">�ư���J����</option>
										</select>
									</div>
								</div>
<!-- 								<div class="col-sm-1"> -->
<!-- 									<div class="form-group"> -->
<!-- 										<label>&nbsp;</label> -->
<!-- 										<button class="form-control btn btn-info" id="listAll">�C�X����</button> -->
<!-- 									</div> -->
<!-- 								</div> -->
								<!--Second Row-->
								<div class="col-sm-2">
									<div class="form-group">
										<label>�ӫ~�s��</label> <input class="form-control" name="cspno"/>

									</div>
								</div>
								<div class="col-sm-2">
									<div class="form-group">
										<label>�ө��s��</label> <input class="form-control" name="shopno"/>
									</div>
								</div>
								<div class="col-sm-2">
									<div class="form-group">
										<label>�������(�_)</label> <input type="text" class="form-control"
											name="etimefrom" />
									</div>
								</div>
								<div class="col-sm-2">
									<div class="form-group">
										<label>�������(�W)</label> <input type="text" class="form-control"
											name="etimeto" />
									</div>
								</div>
								<div class="col-sm-2">
									<div class="form-group">
										<label>���A</label> <select class="form-control" name="status">
											<option value="">-</option>
											<% for(int i : CasesStatus.getAllStatusValues()){ %>
											<option value="<%=i%>"><%=CasesStatus.getStatusName(i)%></option>
											<% }%>
										</select>
									</div>
								</div>
								<div class="col-sm-2">
									<div class="form-group">
										<label>&nbsp;</label>
										<button class="form-control btn btn-success" id="doSearch">
											<i class="fa fa-search"></i> �j�M
										</button>
									</div>
								</div>
								<input type="hidden" name="action" value="compositeQuery">
							</form>
						</div>

					</div>
					<!--/.panel .panel-info-->
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading" id="MsgTitle" style="font-weight: bold">�п�J�j�M����</div>
						<!-- /.panel-heading -->
						<div class="panel-body" id="resultDiv">
<jsp:include page="Ajax_casesTable.jsp"/>
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
<div class="modal fade" id="cancel-modal"  tabindex="-1" role="dialog" aria-labelledby="cancel-detail" aria-hidden="true" >
	<div class="modal-dialog" style="width: 400px;top: 100px;">
	    <div class="modal-content">
		 <div class="modal-header">
			<h4 class="modal-title" id="cancel-detail">
			   �����X�ʮ�
			</h4>
		 </div>
		 <div class="modal-body" >
		 	<p class="text-danger" style="font-size: 1.3em;"><i class="fa fa-fw fa-exclamation-circle"></i>�`�N�G<br>�����X�ʮױN�|�s�a�����ӦX�ʮת��Ҧ��q��</p>
		 	<p><br></p>
		 	<p>�п�J������]�G</p>
		 	<textarea  style="height: 200px;"id="cancel-reason" class="form-control"></textarea>
		 </div>
		 <div class="modal-footer">
			<button type="button" class="btn btn-success btn-circle btn-lg" id="cancel-confirm" style="margin-right: 10px;">
			 <i class="fa fa-check"></i>
			</button>
			<button type="button" class="btn btn-danger btn-circle btn-lg" data-dismiss="modal">
			 <i class="fa fa-times"></i>
			</button>
		 </div>
	    </div><!-- /.modal-content -->
	</div>
</div>
<div class="modal fade" id="delete-modal"  tabindex="-1" role="dialog" aria-labelledby="delete-detail" aria-hidden="true" >
	<div class="modal-dialog" style="width: 400px;top: 100px;">
	    <div class="modal-content">
		 <div class="modal-header">
			<h4 class="modal-title" id="delete-detail">
			   �R���X�ʮ�
			</h4>
		 </div>
		 <div class="modal-body" >
		 	<p class="text-danger" style="font-size: 1.3em;"><i class="fa fa-fw fa-exclamation-circle"></i>�`�N�G<br>�R��<b>�u�|</b>�s�a�����ӦX�ʮץ��������q��</p>
		 	<p><br></p>
		 	<p>�п�J�R����]�G</p>
		 	<textarea  style="height: 200px;"id="delete-reason" class="form-control"></textarea>
		 </div>
		 <div class="modal-footer">
			<button type="button" class="btn btn-success btn-circle btn-lg" id="delete-confirm" style="margin-right: 10px;">
			 <i class="fa fa-check"></i>
			</button>
			<button type="button" class="btn btn-danger btn-circle btn-lg" data-dismiss="modal">
			 <i class="fa fa-times"></i>
			</button>
		 </div>
	    </div><!-- /.modal-content -->
	</div>
</div>
<div class="modal fade" id="overCaseModal"  tabindex="-1" role="dialog" aria-labelledby="overCaseWindow" aria-hidden="true">
	<div class="modal-dialog" style="top:110px; width: 300px; height:200px;">
	    <div class="modal-content">
			 <div class="modal-header">
				<h4 class="modal-title" id="overCaseWindow" style="font-family: �L�n������; font-size:large;">���צX��</h4>
			 </div>
			 <div class="modal-body">
			 	<p>���צX�ʥi��|�Ϩt�μȮɩʮį�C���C</p>
			 	<p>�z�T�w�n�t�ΥߧY���浲�צX�ʪ��@�~�ܡH</p>
			 </div>
			 <div class="modal-footer">
				<button type="button" class="btn btn-danger btn-circle btn-lg" id="over-case-confirm" style="margin-right: 10px;">
				 <i class="fa fa-check"></i>
				</button>
				<button type="button" class="btn btn-success btn-circle btn-lg" data-dismiss="modal">
				 <i class="fa fa-times"></i>
				</button>
		 	</div>
	    </div><!-- /.modal-content -->
	</div>
</div><!-- /.modal -->		
<div class="modal fade" id="ajaxMsgModal"  tabindex="-1" role="dialog" aria-labelledby="msgWindow" aria-hidden="true">
	<div class="modal-dialog" style="top:160px; width: 300px; height:200px;">
	    <div class="modal-content">
			 <div class="modal-header" style="display:none">
				<h4 class="modal-title" id="msgWindow"></h4>
			 </div>
			 <div class="modal-body">
			 <p>
				 ���椤...&nbsp;&nbsp;<i class="fa fa-spin fa-spinner" style="font-size:2em; color:#FE980F"></i>
			 </p>
			 </div>
	    </div><!-- /.modal-content -->
	</div>
</div><!-- /.modal -->		
<%-- include footer1 --%>
<jsp:include page="/b/frag/b_footer1.jsp"/>

	<script src="${resPath}/jquery-ui/jquery-ui.min.js"></script>

	<!-- Page Specific Js source -->
	<script> 
		menuTrigger('#caseMenu');
		var CasesEnv = {
		controller : '${controllerPath}',
		msg : ['�j�M���G','��J�榡���~','�j�M���G'],
		errMsg:'<i class=\"fa fa-times-circle-o \" style=\"font-size:2em; color:#FE980F;\"></i> �s�u���~',
		lang :{
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
		};
	</script>
	<script src="${pageHome}/js/manage_cases.js"></script>
	
<%-- include footer2 --%>
<jsp:include page="/b/frag/b_footer2.jsp"/>
