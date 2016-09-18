<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%--**** LOGIN REDIRECT ****--%>
<%@include file="/f/frag/f_login_redirect.file" %>
<%--**** LOGIN REDIRECT ****--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageHome" value="<%=request.getContextPath() + \"/caseproduct\" %>"/>

<%-- INCLUDE HEADER1 --%>
<jsp:include page="/f/frag/f_header1.jsp"/>
<%-- INCLUDE HEADER1 --%>
<link rel="stylesheet" href="${pageHome}/css/owncp.css"/>
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
				<jsp:include page="/f/frag/f_memCenterMenu.jsp" /> 
				<div class="col-sm-9" >
					<h2 class="title text-center">�X�ʰӫ~�M��</h2>
	    		
					<div id="list-zone">
<jsp:include page="Ajax_caseProductList.jsp"/>
					</div><!--/#list-zone"-->
				</div><!--class="col-sm-9"-->
			</div>
		</div>
	</section>
	<!-- MODAL FOR DELETE -->
	<div class="modal fade " id="delCP"  tabindex="-1" role="dialog" aria-labelledby="add" aria-hidden="true">
		<div class="modal-dialog">
		    <div class="modal-content">
				 <div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="glyphicon glyphicon-remove"></i></button>
					<h4 class="modal-title" id="add">
					   �R���X�ʰӫ~
					</h4>
				 </div>
				 <div class="modal-body" >
				 	<p id="delMsg"></p>
					<p id="dia"></p>
				 </div>
				 <div class="modal-footer">
					<button class="btn btn-danger" style="margin-top: 0px;" id="sendDelRequest" data-delno="">
					   �T�w
					</button>
					<button class="btn btn-success" data-dismiss="modal">
						����
					</button>
					
				 </div>
		    </div><!-- /.modal-content -->
		</div>
	</div><!-- /.modal -->
	<div class="modal fade " id="notify-lock"  tabindex="-1" role="dialog" aria-labelledby="lock" aria-hidden="true">
		<div class="modal-dialog">
		    <div class="modal-content">
				 <div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="glyphicon glyphicon-remove"></i></button>
					<h4 class="modal-title" id="lock">
					  �ӫ~�w��w
					</h4>
				 </div>
				 <div class="modal-body" >
				 	<p>�z�ҿ�ܪ��ӫ~�]���g���Χ@�o�_�X�ʮסA���e�w�Q��w�L�k�ק�C</p>
				 	<p>�Y�z��ӫ~���e�������ܧ�A�N�۰ʷs�W�@���ӫ~�C</p>
				 	<p>�T�w�~��H</p>
				 </div>
				 <div class="modal-footer">
					<button class="btn btn-danger" style="margin-top: 0px;" id="iknowthat" data-cpno="">
					   �T�w
					</button>
					<button class="btn btn-success" data-dismiss="modal">
						����
					</button>
					
				 </div>
		    </div><!-- /.modal-content -->
		</div>
	</div><!-- /.modal -->
	
<%-- INCLUDE FOOTER1 --%>
<jsp:include page="/f/frag/f_footer1.jsp"/>
<%-- INCLUDE FOOTER1 --%>
	<script>
		var ViewCaseProductEnv = {
				msgHeading:'�X�ʰӫ~�s�� ',
				controller:'${pageHome}/caseProduct.do',
				progessMsg:'���椤...  <i class="fa fa-spinner fa-spin"></i>',
				delMsg:'�w�R��',
				askMsg:'�T�w�R���H'
		};
	</script>
	<script src="${pageHome}/js/viewcaseproduct.js"></script>
	<script> 
		menuTrigger(0); 
	</script>
</body>
</html>