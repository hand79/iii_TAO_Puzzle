<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<%--**** LOGIN REDIRECT ****--%>
<%@include file="/f/frag/f_login_redirect.file" %>
<%--**** LOGIN REDIRECT ****--%>
<%@ page import="com.tao.cases.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% pageContext.setAttribute("context", request.getContextPath()); %>
<% pageContext.setAttribute("pageHome", request.getContextPath() + "/caseqa"); %>
<%-- 
 
 Request Scope Attribute Used In This Page: 
 key: qamap			value: Map<CasesVO, List<CasesQAVO>
 key: tmemset		value: Set<TinyMemberVO>
 
--%>
<%-- INCLUDE HEADER1 --%>
<jsp:include page="/f/frag/f_header1.jsp"/>
<%-- INCLUDE HEADER1 --%>

<style>
.qa {
	font-family: �L�n������;
}
.modal-title {
	font-weight:bold; 
	color: #FE980F; 
	font-family: �L�n������;
}
.modal textarea{
	height: 200px;
}
.modal-open{
	overflow:initial;
}
.pointer{
	cursor: pointer;
}

</style>
<%
request.setAttribute("showBreadcrumb", new Object()); 
java.util.LinkedHashMap<String, String> map = new java.util.LinkedHashMap<String, String>();
map.put("�|������","");
request.setAttribute("breadcrumbMap", map); 
%>
  
<!-- INCLUDE HEADER2 -->
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>
<!-- INCLUDE HEADER2 -->
	
	<section>
		<div class="container">
			<div class="row">
			
<!-- INCLUDE MENU -->
<jsp:include page="/f/frag/f_memCenterMenu.jsp" /> 
<!-- INCLUDE MENU -->

<div class="col-sm-9">
	<h2 class="title text-center">�^�����D</h2>
	<div>
		<div class="panel-group category-products" id="case-list-accordian">
<c:if test="${empty qamap}">		
				<h4 style="text-align: center;"><i>(�|���إߦX�ʮ�)</i></h4>
</c:if>		
<c:if test="${not empty qamap}">		
<c:forEach var="entry" items="${qamap}"><%-- Map<CasesVO, List<CasesQAVO> --%>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#case-list-accordian" href="#case${entry.key.caseno}" data-caseno="${entry.key.caseno}">
							<span class="badge pull-right">(${entry.value.size()})</span>
							<i class="fa fa-fw fa-tag${(empty entry.value) ? '':'s'}"></i> (${entry.key.caseno}) ${entry.key.title}
						</a>
					</h4>
				</div>
				<div id="case${entry.key.caseno}" class="panel-collapse collapse">
					<div class="panel-body">
						<table class="table table-striped qa" style="font-size: 0.45em; margin-left: 30px;margin-bottom: 10px; width: 780px; font-family:">
	<c:if test="${empty entry.value}">			
							<tr>
								<td><i>�|�������󴣰�</i></td>
							</tr>
	</c:if>
	<c:if test="${not empty entry.value}">			
							<tr>
								<th>#</th>
								<th>�o�ݪ�</th>
								<th>���D�K�n</th>
								<th>�o�ݮɶ�</th>
								<th>�^�� / �s��</th>
								<th>�R��</th>
							</tr>
		<c:forEach var="qavo" items="${entry.value}" varStatus="sts">
							<tr data-qano="${qavo.qano}">
								<td>${entry.value.size() - sts.index}</td>
			<c:forEach var="tmemvo" items="${tmemset}">
				<c:if test="${tmemvo.memno == qavo.memno}">
								<td><a href="<%=request.getContextPath()%>/SurfMemberServlet.do?action=memberView&memno=${tmemvo.memno}"><i class="fa fa-fw fa-user"></i> <span class="q-user">${tmemvo.memid}(${tmemvo.point})</span></a></td>
				</c:if>
			</c:forEach>
								<td class="pointer" style="font-weight: bolder">${qavo.shortenedQuestion}</td>
								<td class="q-time">${qavo.formatedQtime}</td>
								<td>
									<c:if test="${empty qavo.atime}">
									<a class="answer-qa pointer"><i class="fa fa-fw fa-reply"></i>�^��</a>
									</c:if>
									<c:if test="${not empty qavo.atime}">
									<a class="edit-qa pointer"><i class="fa fa-fw fa-edit"></i>�s��</a>
									</c:if>
								</td>
								<td><a class="delete-qa pointer"><i class="fa fa-fw fa-trash-o"></i>�R��</a></td>
							</tr>
		</c:forEach>
	</c:if>
						</table>
					</div>
				</div>
			</div>	
</c:forEach>
</c:if>
		</div>
	</div><!--class="list-zone"-->
</div><!--class="col-sm-9"-->
			</div>
		</div>
	</section>
<div class="modal fade" id="answer-modal"  tabindex="-1" role="dialog" aria-labelledby="answer-window" aria-hidden="true">
	<div class="modal-dialog">
	    <div class="modal-content" style="">
		 <div class="modal-header">
			<h4 class="modal-title" id="answer-window">
			   �^�����D
			</h4>
		 </div>
		 <div class="modal-body">
			<p><i class="fa fa-fw fa-user"></i><span class="q-user"></span><span class="pull-right"><i class="fa fa-clock-o"></i> <span class="q-time">2014-09-15 12:20</span></span></p>
			<pre class="question-content"></pre>
			<p>�^�����e (120�r�H��)</p>
			<textarea class="answer-content"></textarea>
		 </div>
		 <div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">
				����
			</button>
			<button type="button" class="btn btn-success" id="answer-confirm" data-qano="">
			   �e�X
			</button>
		 </div>
	    </div><!-- /.modal-content -->
	</div>
</div>

<div class="modal fade" id="edit-modal"  tabindex="-1" role="dialog" aria-labelledby="edit-window" aria-hidden="true">
	<div class="modal-dialog">
	    <div class="modal-content" style="">
			 <div class="modal-header">
				<h4 class="modal-title" id="edit-window">
				   ���^��
				</h4>
			 </div>
			 <div class="modal-body">
				<p><i class="fa fa-fw fa-user"></i><span class="q-user"></span><span class="pull-right"><i class="fa fa-clock-o"></i> 2014-09-15 12:20</span></p>
				<pre class="question-content"></pre>
				<p>�^�����e (120�r�H��)<span class="pull-right"><i class="fa fa-fw fa-clock-o"></i><span class="answer-time"></span></span></p>
				<textarea class="answer-content"></textarea>
			 </div>
			 <div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">����</button>
				<button type="button" class="btn btn-success" id="edit-confirm" data-qano="">�ק�</button>
			 </div>
		    </div><!-- /.modal-content -->
	</div>
</div><div class="modal fade" id="delete-modal" tabindex="-1" role="dialog" aria-labelledby="delete" aria-hidden="true">
	<div class="modal-dialog">
	    <div class="modal-content">
		 <div class="modal-header">
			
			<h4 class="modal-title" id="delete">
			   �R��
			</h4>
		 </div>
		 <div class="modal-body">
			<p>���D�R����N�L�k�_��C</p>
			<p>�T�w�R���o�h���D�H</p>
		 </div>
		 <div class="modal-footer">
			<button type="button" class="btn btn-default" id="delete-confirm">
			   �T�w
			</button>
			<button type="button" class="btn btn-success" data-dismiss="modal">
				����
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
			 <div class="modal-body" style="text-align: center;">
			 <p>
				 ���椤...&nbsp;&nbsp;<i class="fa fa-spin fa-spinner" style="font-size:2em; color:#FE980F"></i>
			 </p>
			 </div>
	    </div><!-- /.modal-content -->
	</div>
</div><!-- /.modal -->		

<jsp:include page="/f/frag/f_footer1.jsp"/>
<script>
	menuTrigger(0);
	$('a[href="#case${param.caseno}"]').click();
	var currentCase = '${param.caseno}';
	var CurrentPageEnv = {
		controller: '${pageHome}/caseQA.do',
		errMsg:'�o�Ϳ��~'
	};
</script>
<script src="${pageHome}/js/view_case_questions.js"></script>
</body>
</html>