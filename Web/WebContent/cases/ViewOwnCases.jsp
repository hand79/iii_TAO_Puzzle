<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<%--**** LOGIN REDIRECT ****--%>
<%@include file="/f/frag/f_login_redirect.file" %>
<%--**** LOGIN REDIRECT ****--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <%@ page import="java.util.*" %> --%>
<%@ page import="com.tao.cases.model.*" %>
<jsp:useBean id="ordSvc" class="com.tao.order.model.OrderService" scope="request"/>
<% pageContext.setAttribute("pageHome", request.getContextPath() + "/cases"); %>
<% pageContext.setAttribute("resPath", request.getContextPath() + "/f/res"); %>
<% pageContext.setAttribute("caseDetailFullUrl", request.getScheme() + "://" + request.getServerName() + ":"+request.getServerPort() + request.getContextPath() + "/cases/cases.do?action=caseDetail&caseno="); %>
<%-- INCLUDE HEADER1 --%>
<jsp:include page="/f/frag/f_header1.jsp"/>
<%-- INCLUDE HEADER1 --%>
<title>�޲z�X�� | TAO Puzzle</title>
<link rel="stylesheet" href="${resPath}/jquery-ui/jquery-ui.min.css"/>
<link rel="stylesheet" href="${resPath}/jquery-ui/jquery-ui.structure.min.css"/>
<link rel="stylesheet" href="${resPath}/jquery-ui/jquery-ui.theme.min.css"/>
<style>
	.modal-open{
		overflow: initial;
	}
	.pointer {
		cursor: pointer;		
	}
	.modal-title {
		font-weight:bold; 
		color: #FE980F; 
		font-family: �L�n������;
		font-size: 1.5em;
	}
	.underline{
		text-decoration: underline;
	}
	#detail-modal{
		/*font-family: �L�n������;*/
	}
	#detail-modal p {
		margin-bottom: 10px;
	}
	#detail-modal p span{
		margin-top:10px;
		font-size: 1.2em;
	}
	#detail-modal b b{
		font-size: 1.1em;
	}
	#detail-modal p b{
		/*color: #F08900;*/
		font-weight: bold;
		font-size: 1.2em;
	}
	.viewCaseDetail{
	font-weight: bolder;
	}
	.modal-dialog{
		font-family:  �L�n������;
	}
	#open-case-modal .modal-dialog{
		width: 28%;
	}
	#open-url-info{
		display:none; 
		font-size: 0.8em; 
		margin-top:20px;
	}
	#ajaxMsgModal{
		text-align: center;
	}
	.inline{
		display:inline;
	}
</style>
<%
request.setAttribute("showBreadcrumb", new Object()); 
java.util.LinkedHashMap<String, String> map = new java.util.LinkedHashMap<String, String>();
map.put("�|������","");
request.setAttribute("breadcrumbMap", map); 
%>

<%-- INCLUDE HEADER2 --%>
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>
<%-- INCLUDE HEADER2 --%>
<section>
		<div class="container">
			<div class="row">
<%-- INCLUDE MENU --%>
<jsp:include page="/f/frag/f_memCenterMenu.jsp"/>
<%-- INCLUDE MENU --%>				
				<div class="col-sm-9">
					<h2 class="title text-center">�X�ʮצC��</h2>
					<div class="search-zone" style="text-align: right; display: none">
					
					</div><!--class="search-zone"-->
					<div class="list-zone">
<jsp:include page="/cases/Ajax_ownCases.jsp"/>
					</div><!--class="list-zone"-->
				</div><!--class="col-sm-9"-->
			</div>
		</div>
	</section>
<div style="display:none;" id="caseStatusBubble">
	<p><b>���W�[�G</b>�|���W�[</p>
	<p><b>�Ҷ����G</b>�w�W�[���|���F����e</p>
	<p><b>�w���ΡG</b>�w�W�[���B�F����e</p>
	<p><b>�w�����G</b>�w�������|�������Ҧ�����</p>
	<p><b>�w�����G</b>�w�����Ҧ��y�{�B�U�[</p>
	<p><b>�w�����G</b>�ɶ��������F���e�ΨϥΪ̤�ʨ���</p>
</div>
<div style="display:none;" id="caseAmountBubble">
	<p>�ثe / ���e / �W�� �ƶq</p>
</div>
<div class="modal fade" id="detail-modal"  tabindex="-1" role="dialog" aria-labelledby="view-detail" aria-hidden="true" >
	<div class="modal-dialog">
	    <div class="modal-content">
		 <div class="modal-header">
		 	<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="glyphicon glyphicon-remove"></i></button>
			<h4 class="modal-title" id="view-detail">
			   �X�ʮ׸Ա�
			</h4>
		 </div>
		 <div class="modal-body" >
			<p>
				<b>�X�ʽs��  - ���A�G</b>
				<br>
				<span id="caseDeatil-caseno-status"></span>
			</p>
			<p>
				<b>�X�ʮצW�١G</b>
				<br>
				<span id="caseDeatil-title"></span>
			</p>
			<p>
				<b>�X�ʰӫ~�G</b>
				<br>
				<span><a href="" id="caseDeatil-cpsp"></a></span>
			</p>
			<p>
				<b>�_�l / �����ɶ��G</b>
				<br>	
				<span id="caseDeatil-time"></span>
			</p>
			<p>
				<b><b style="color:#5bc0de">�ثe</b> / <b style="color:#5cb85c">����</b> / <b style="color:#d9534f">�̤j</b> �X�ʼƶq�G</b>
				<br>
				<span id="caseDeatil-qtys"></span>
			</p>
			<p>
				<b>�X�ʳ�� (��� - �馩 = �X�ʻ�)�G</b>
				<br>	
				<span id="caseDeatil-price"></span>
			</p>
			<p>
				<b>�Ҧb�a�ϡG</b>
				<br>
				<span id="caseDeatil-loc"></span>
			</p>
			<p>
				<b>��f�覡�@�G</b>
				<br>
				<span id="caseDeatil-ship1">���� 60��</span>
			</p>
			
			<p>
				<b>��f�覡�G�G</b>
				<br>
				<span id="caseDeatil-ship2"></span>
			</p>
			<p>
				<b>�X�ʻ����G</b>
			</p>
			<pre id="caseDeatil-casedesc"></pre>
		 </div>
		 <div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">
			   ����
			</button>
		 </div>
	    </div><!-- /.modal-content -->
	</div>
</div>
<div class="modal fade" id="open-case-modal"  tabindex="-1" role="dialog" aria-labelledby="open-case-title" aria-hidden="true" >
	<div class="modal-dialog">
	    <div class="modal-content">
		 <div class="modal-header">
		 	<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="glyphicon glyphicon-remove"></i></button>
			<h4 class="modal-title" id="open-case-title">
			   �X�ʮפW�[
			</h4>
		 </div>
		 <div class="modal-body" style="font-size: 1.2em;" >
		 	<p><span style="background-color: #FE980F; color: white;">&nbsp;&nbsp;<i class="fa fa-fw fa-users"></i>�X�� &nbsp; <span class="cno"></span>&nbsp;&nbsp;</span></p>
	 		<p class="ctitle" ></p>
	 		<br>
	 		<div>
	 		<p style="font-size:0.9em;">�п�ܦX�ʵ����ɶ��G</p>
	 		<input type="text" id="open-date"  style="font-size: 0.8em;" placeholder="��ܤ��"> 
	 		<select class="inline" style="width: 25%; font-size: 0.8em;" id="open-time">
	 			<% for(int i = 0; i<24 ;i++){%>
	 			<option><%=String.format("%02d", i)%>:00</option>
	 			<% }%>
	 		</select>
	 		<p>
	 		<p>
	 		</div>
		 	<p style="font-size:0.9em;">�W�[�����G </p>
		 	<select id="open-type" style="width: 28%; font-size: 0.8em; display:inline;">
		 		<option value="public" selected>���}</option>
		 		<option value="private">�p�H</option>
		 	</select>
		 	<div id="open-url-info"><p>�X�ʺ��}�G</p><input style="width: 100%;" type="text" value=""/></div>
		 </div>
		 <div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">
			   ����
			</button>
			<button type="button" class="btn btn-success" id="open-case-confirmed" data-caseno="">
			   �T�w
			</button>
		 </div>
	    </div><!-- /.modal-content -->
	</div>
</div>
<div class="modal fade" id="cancel-case-modal"  tabindex="-1" role="dialog" aria-labelledby="cancel-case-title" aria-hidden="true" >
	<div class="modal-dialog">
	    <div class="modal-content">
		 <div class="modal-header">
		 	<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="glyphicon glyphicon-remove"></i></button>
			<h4 class="modal-title" id="cancel-case-title">
			   �����X�ʮ�
			</h4>
		 </div>
		 <div class="modal-body" >
		 	<p style="color: #d43f3a; font-size: 1.2em;"><i class="fa fa-exclamation-triangle"></i> �`�N�G�����X�ʮױN�|�s�a�����ӦX�ʮפU���Ҧ��q��</p>
		 	<p><span style="background-color: #FE980F; color: white;">&nbsp;&nbsp;<i class="fa fa-fw fa-users"></i>�X�� &nbsp; <span class="cno"></span>&nbsp;&nbsp;</span></p>
	 		<p class="ctitle" ></p>
	 		<p>
	 		<hr>
	 		<p>
	 		<p>�п�J������]�A�o�ӰT���N�|�e���ѻP�X�ʮת��Ҧ��έ��G</p>
		 	<textarea id="cancel-reason" style="height: 150px;"></textarea>
		 	</div>
		 <div class="modal-footer">
			<button type="button" class="btn btn-danger" id="cancel-case-confirmed" data-caseno="">
			   �T�w����
			</button>
			<button type="button" class="btn btn-success" data-dismiss="modal">
			   ��^
			</button>
		 </div>
	    </div><!-- /.modal-content -->
	</div>
</div>
<div class="modal fade" id="delete-case-modal"  tabindex="-1" role="dialog" aria-labelledby="delete-case-title" aria-hidden="true" >
	<div class="modal-dialog">
	    <div class="modal-content">
		 <div class="modal-header">
		 	<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="glyphicon glyphicon-remove"></i></button>
			<h4 class="modal-title" id="delete-case-title">
			   �����X�ʮ�
			</h4>
		 </div>
		 <div class="modal-body" style="font-size: 1.2em">
		 	<p><span style="background-color: #FE980F; color: white;">&nbsp;&nbsp;<i class="fa fa-fw fa-users"></i>�X�� &nbsp; <span class="cno"></span>&nbsp;&nbsp;</span></p>
	 		<p class="ctitle" ></p>
	 		<p>�T�w�R���H</p>
	 	</div>
		 <div class="modal-footer">
			<button type="button" class="btn btn-default" id="delete-case-confirmed" data-caseno="">
			   �T�w
			</button>
			<button type="button" class="btn btn-success" data-dismiss="modal">
			   ��^
			</button>
		 </div>
	    </div><!-- /.modal-content -->
	</div>
</div>
<div class="modal fade" id="over-case-modal"  tabindex="-1" role="dialog" aria-labelledby="over-case-title" aria-hidden="true" >
	<div class="modal-dialog">
	    <div class="modal-content">
		 <div class="modal-header">
		 	<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="glyphicon glyphicon-remove"></i></button>
			<h4 class="modal-title" id="over-case-title">
			   �����X�ʮ�
			</h4>
		 </div>
		 <div class="modal-body" style="font-size: 1.2em">
		 	<p><span style="background-color: #FE980F; color: white;">&nbsp;&nbsp;<i class="fa fa-fw fa-users"></i>�X�� &nbsp; <span class="cno"></span>&nbsp;&nbsp;</span></p>
	 		<p class="ctitle" ></p>
	 		<p>�T�w�n�������ΡH</p>
	 	</div>
		 <div class="modal-footer">
			<button type="button" class="btn btn-default" id="over-case-confirmed" data-caseno="">
			   �T�w
			</button>
			<button type="button" class="btn btn-success" data-dismiss="modal">
			   ��^
			</button>
		 </div>
	    </div><!-- /.modal-content -->
	</div>
</div>
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
<jsp:include page="/f/frag/f_footer1.jsp"/>
	<script src="${resPath}/js/jquery.balloon.min.js"></script>
	<script src="${resPath}/jquery-ui/jquery-ui.min.js"></script>
	<script>
		menuTrigger(0);
		var CurrentPageEnv = {
			controller:'${pageHome}/cases.do',
			caseDetailFullUrl:'${caseDetailFullUrl}'
		};
		$('#open-date').datepicker({
			changeYear : true,
			changeMonth : true,
			numberOfMonths : 1,
			dateFormat : 'yy-mm-dd',
			maxDate: '+8w',
			minDate:'+1d'
		});
		
		
	</script>
	<script src="${pageHome}/js/view_own_cases.js"></script>
</body>
</html>