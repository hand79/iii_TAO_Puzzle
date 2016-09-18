<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.tao.order.model.*"%>


<c:set var="pageHome" value="<%=request.getContextPath() +\"/order\"%>"
	scope="page" />
<c:set var="resPath" value="<%=request.getContextPath() +\"/b/res\"%>"
	scope="page" />
<c:set var="controllerPath" value="<%=request.getContextPath() +\"/back/order/order.do\"%>"
	scope="page" />
	
<%-- include header1 --%>	
<jsp:include page="/b/frag/b_header1.jsp"/>
 
<link href="${resPath}/jquery-ui/jquery-ui.min.css"
	rel="stylesheet" type="text/css">
<link href="${resPath}/jquery-ui/jquery-ui.theme.min.css"
	rel="stylesheet" type="text/css">
<style>
.page-header {
	font-family: �L�n������;
}
.modal>*{
	font-family: �L�n������;
}

.modal-title{
	font-weight: bold;
}
.pointer{
	cursor: pointer;
}
</style>

<%-- include header2AndMenu --%>
<jsp:include page="/b/frag/b_header2AndMenu.jsp"/>

		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">�j�M�q�� <button class="btn btn-primary btn-outline pull-right" id="finishOrder">�ߧY����q��</button></h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-success">
						<div class="panel-heading">
							<h4 style="font-family: �L�n������; margin: 0px">
								<b>�ƦX�j�M</b>
							</h4>
						</div>
						<div class="panel-body">
							<form role="form" id="queryForm">

								<div class="col-sm-2">
									<div class="form-group">
										<label>�q��s��</label> <input class="form-control" name="ordno">
									</div>

								</div>
								<div class="col-sm-2">
									<div class="form-group">
										<label>�U��|���s�� �� �b������r</label> <input class="form-control"
											name="bmem">
									</div>
								</div>
								<div class="col-sm-2">
									<div class="form-group">
										<label>�q��ɶ�(�_)</label> <input type="text" class="form-control"
											name="osday">
									</div>

								</div>

								<div class="col-sm-1">
									<div class="form-group">
										<label>&nbsp;</label> <select class="form-control"
											name="ostime">
											<option value="">-</option>
											<%
												for(int i=0; i<24; i++){
											%>
											<%
												String h = String.format("%02d:00", i);
											%>
											<option value="<%=h%>"><%=h%></option>
											<%
												}
											%>
										</select>
									</div>
								</div>
								<div class="col-sm-1">
									<div class="form-group">
										<label>�q����B</label> <select class="form-control"
											name="pricefilter">
											<option value="none">-</option>
											<option value="eq">����</option>
											<option value="gt">�j�󵥩�</option>
											<option value="lt">�p�󵥩�</option>
										</select>
									</div>
								</div>
								<div class="col-sm-2">
									<div class="form-group">
										<label>&nbsp;</label> 
										<input type="number" min="0" class="form-control" name="price" placeholder="�п�J���B">
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
										<label>�X�ʮ׽s��</label> <input class="form-control" name="caseno">

									</div>
								</div>
								<div class="col-sm-2">
									<div class="form-group">
										<label>�D�ʷ|���s�� �� �b������r</label> <input class="form-control" name="cmem">
									</div>
								</div>
								<div class="col-sm-2">
									<div class="form-group">
										<label>�q��ɶ�(�W)</label> <input type="text" class="form-control"
											name="oeday">
									</div>
								</div>
								<div class="col-sm-1">
									<div class="form-group">
										<label>&nbsp;</label> <select class="form-control"
											name="oetime">
											<option value="">-</option>
											<%
												for(int i=0; i<24; i++){
											%>
											<%
												String h = String.format("%02d:00", i);
											%>
											<option value="<%=h%>"><%=h%></option>
											<%
												}
											%>
										</select>
									</div>
								</div>
								<div class="col-sm-3">
									<div class="form-group">
										<label>���A</label> <select class="form-control" name="status">
											<option value="">-</option>
											<%
												for(int i : OrderStatus.getAllStatusValues()) {
											%>
											<option value="<%=i%>"><%=OrderStatus.getStatusName(i)%></option>
											<%
												}
											%>
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
							</form>
						</div>

					</div>
					<!--/.panel .panel-info-->
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading" id="MsgTitle">�п�J�j�M����</div>
						<!-- /.panel-heading -->
						<div class="panel-body" id="resultDiv">
<jsp:include page="Ajax_orderTable.jsp"/>
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
<div class="modal fade" id="detail-modal"  tabindex="-1" role="dialog" aria-labelledby="view-detail" aria-hidden="true" >
	<div class="modal-dialog" style="width: 400px;top: 100px;">
	    <div class="modal-content">
		 <div class="modal-header">
			<h4 class="modal-title" id="view-detail">
			   �X�ʭq��Ա�
			</h4>
		 </div>
		 <div class="modal-body" >
		 	<p>
				<b>�q��s��</b>
				<br>
				<span id="ordDeatil-ordno"></span>
			</p>
			<p>
				<b>�X�ʮצW��</b>
				<br>
				<a href="" id="ordDeatil-title" target="_blank"></a>
			</p>
			<p>
				<b>�ӫ~���</b>
				<br>
				<span id="ordDeatil-unitprice"></span>
			</p>
			<p>
				<b>�q�ʼƶq</b>
				<br>
				<span id="ordDeatil-qty"></span>
			</p>
			<p>
				<b>��f�覡</b>
				<br>
				<span id="ordDeatil-ship"></span>
			</p>
			<p>
				<b>�B�O</b>
				<br>
				<span id="ordDeatil-shipcost"></span>
			</p>
			<p>
				<b>�q����B (��� x �ƶq+�B�O=�`�B)</b>
				<br>
				<span><span class="total" id="ordDeatil-price"></span></span>
			</p>
			
			<p>
				<b>�U��ɶ�</b>
				<br>
				<span><i class="fa fa-clock-o"></i></span> <span id="ordDeatil-ordtime"></span>
			</p>
			<p>
				<b>�q�檬�A</b>
				<br>
				<span id="ordDeatil-status"></span>
			</p>
		 </div>
		 <div class="modal-footer" style="text-align: center;">
			<button type="button" class="btn btn-success btn-circle btn-lg" data-dismiss="modal">
			 <i class="fa fa-check"></i>
			</button>
		 </div>
	    </div><!-- /.modal-content -->
	</div>
</div>		
<div class="modal fade" id="conflic-modal"  tabindex="-1" role="dialog" aria-labelledby="conflic-window" aria-hidden="true" >
	<div class="modal-dialog" style="width: 400px; top: 300px;">
	    <div class="modal-content">
		 <div class="modal-header">
			<h4 class="modal-title" id="conflic-window">
			  �ȯɳB�z
			</h4>
		 </div>
		 <div class="modal-body" >
		 	<p>�Y�ȯɤw�B�z�����A�п�ܭq��n��s�������A</p>
		 	<select class="form-control status">
		 		<option value="<%=OrderDAO.STATUS_CANCELED%>"><%=OrderStatus.getStatusName(OrderDAO.STATUS_CANCELED)%> (�|�i��h��)</option>
		 		<option value="<%=OrderDAO.STATUS_BOTH_COMFIRMED%>"><%=OrderStatus.getStatusName(OrderDAO.STATUS_BOTH_COMFIRMED)%></option>
		 		<option value="<%=OrderDAO.STATUS_ACHIEVED%>"><%=OrderStatus.getStatusName(OrderDAO.STATUS_ACHIEVED)%></option>
		 		<option value="<%=OrderDAO.STATUS_CREATOR_COMFIRMED%>"><%=OrderStatus.getStatusName(OrderDAO.STATUS_CREATOR_COMFIRMED)%></option>
		 		<option value="<%=OrderDAO.STATUS_BUYER_COMFIRMED%>"><%=OrderStatus.getStatusName(OrderDAO.STATUS_BUYER_COMFIRMED)%></option>
		 	</select></div>
 		<div class="modal-footer">
			<button type="button" class="btn btn-success btn-circle btn-lg" id="modify-confirm" style="margin-right: 10px;">
			 <i class="fa fa-check"></i>
			</button>
			<button type="button" class="btn btn-danger btn-circle btn-lg" data-dismiss="modal">
			 <i class="fa fa-times"></i>
			</button>
		 </div>
	    </div><!-- /.modal-content -->
	</div>
</div>
<div class="modal fade" id="finishOrderModal"  tabindex="-1" role="dialog" aria-labelledby="finishOrderWindow" aria-hidden="true">
	<div class="modal-dialog" style="top: 300px; width: 300px; height:200px;">
	    <div class="modal-content">
			 <div class="modal-header">
				<h4 class="modal-title" id="finishOrderWindow" style="font-family: �L�n������; font-size:large;">����q��</h4>
			 </div>
			 <div class="modal-body">
			 <p>����q��i��|�Ϩt�μȮɩʮį�C��</p>
			 <p>�z�T�w�n�t�ΥߧY���浲��q�檺�@�~�ܡH</p>
		 	</div>
	 		<div class="modal-footer">
				<button type="button" class="btn btn-danger btn-circle btn-lg" id="finish-order-confirm" style="margin-right: 10px;">
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
	<div class="modal-dialog" style="top:360px; width: 300px; height:200px;">
	    <div class="modal-content">
			 <div class="modal-header" style="display:none">
				<h4 class="modal-title" id="msgWindow"></h4>
			 </div>
			 <div class="modal-body" style="text-align: center;">
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
	<script> 
		menuTrigger('#caseMenu');
		var OrderEnv = {
		controller : '${controllerPath}',
		frontController: '<%= request.getContextPath()%>/cases/cases.do',
		msg : ['�j�M���G','��J�榡���~','�j�M���G'],
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
	<script src="${pageHome}/js/manage_orders.js"></script>
<%-- include footer2 --%>
<jsp:include page="/b/frag/b_footer2.jsp"/>