<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<%--**** LOGIN REDIRECT ****--%>
<%@include file="/f/frag/f_login_redirect.file" %>
<%--**** LOGIN REDIRECT ****--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.tao.order.model.*" %>
<jsp:useBean id="tmemSvc" class="com.tao.jimmy.member.TinyMemberService"/>
<c:set var="tmemset" value="${tmemSvc.all}"/>
<% pageContext.setAttribute("pageHome", request.getContextPath() + "/order"); %>
<% pageContext.setAttribute("resPath", request.getContextPath() + "/f/res"); %>
<% pageContext.setAttribute("memInfoPage", request.getContextPath() + ""); %>
<%
request.setAttribute("showBreadcrumb", new Object()); 
java.util.LinkedHashMap<String, String> map = new java.util.LinkedHashMap<String, String>();
map.put("�|������","");
request.setAttribute("breadcrumbMap", map); 
%>
<jsp:include page="/f/frag/f_header1.jsp"/>
<title>�q��޲z | TAO Puzzle</title>
<style>
	.qa {
		font-family: �L�n������;
		font-size: 0.45em; 
		margin-left: 20px;
		margin-bottom: 10px; 
		width: 800px;
	}
	.modal-title {
		font-weight:bold; 
		color: #FE980F; 
		font-family: �L�n������;
	}
	.tableHeader {
		font-weight: bold;
	}
	.modal-body p input {
		margin-left: 10px;
	}
	.modal-open{
		overflow: initial;
	}
	.pointer{
		cursor: pointer;
	}
	
	#ajaxMsgModal .modal-body{
		text-align: center;
	}
</style>

<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>

<section>
	<div class="container">
		<div class="row">
<jsp:include page="/f/frag/f_memCenterMenu.jsp"/>
			<div class="col-sm-9">
				<h2 class="title text-center">�q��޲z</h2>
				<div class="list-zone">
					<div class="panel-group category-products" id="case-list"><!--category-productsr-->
					<c:if test="${empty caseMap}">
						<p>�|��������q��</p>
					</c:if>
					<c:forEach var="entry" items="${caseMap}" varStatus="sts">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" data-parent="#case-list" href="#case${entry.key.caseno}">
										<span class="badge pull-right">(${entry.value.size()})</span>
										<i class="fa fa-list fa-fw"></i>&nbsp;(${entry.key.caseno}) ${entry.key.title}
									</a>
								</h4>
							</div>
							<div id="case${entry.key.caseno}" class="panel-collapse collapse">
								<div class="panel-body">
									<table class="table table-striped qa">
						<c:if test="${empty entry.value}">
										<tr>
											<td>�|��������q��</td>
										</tr>
						</c:if>
						<c:if test="${not empty entry.value}">
										<tr class="tableHeader">
											<td>�q��s��</td>
											<td>�q�ʤH</td>
											<td>�ɶ�</td>
											<td>�ƶq</td>
											<td id="rate-bubble">����</td>
											<td>��f</td>
											<td>���B</td>
											<td>���A</td>
											<td>��f�T�{</td>
											<td>�����ȯ�</td>
										</tr>
							<c:forEach var="ordvo" items="${entry.value}">
							<c:set var="ordvo" value="${ordvo}"/>
							<%OrderVO ordvo = (OrderVO) pageContext.getAttribute("ordvo"); %>
										<tr data-ordno="${ordvo.ordno}" data-caseno="${entry.key.caseno}">
											<td>${ordvo.ordno}</td>
											<td class="mem-info">
											<c:forEach var="tmemvo" items="${tmemset}">
											<c:if test="${tmemvo.memno == ordvo.bmemno}">
											<a href="<%=request.getContextPath() %>/SurfMemberServlet.do?action=memberView&memno=${tmemvo.memno}" class="member"><i class="fa fa-user"></i> ${tmemvo.memid}(${tmemvo.point})</a>
											</c:if>
											</c:forEach>
											</td>
											<td>${ordvo.formatedOrdtime}</td>
											<td>${ordvo.qty}</td>
											<td>
											<c:if test="${ordvo.crate == null}">
												<c:if test="${ordvo.status != 0}">
												<i>����o</i>
												</c:if>
												<c:if test="${ordvo.status == 0}">
												-
												</c:if>
											</c:if>
											<c:if test="${ordvo.crate != null}">
												<%=OrderRate.getRate(ordvo.getCrate()) %>
											</c:if> / <c:if test="${ordvo.brate == null}">
												<c:if test="${ordvo.status != 0}">
												<a class="pointer give-rate">����</a>
												</c:if>
												<c:if test="${ordvo.status == 0}">
												-
												</c:if>
											</c:if>
											<c:if test="${ordvo.brate != null}">
												<%=OrderRate.getRate(ordvo.getBrate()) %>
											</c:if>
											</td>
											<td>
											<c:if test="${ordvo.ship == 1}">
											${entry.key.ship1}
											</c:if>
											<c:if test="${ordvo.ship == 2 }">
											${entry.key.ship2}
											</c:if>
											</td>
											<td>${ordvo.price}</td>
											<td><%=OrderStatus.getStatusName(ordvo.getStatus())%></td>
											<c:choose>
											<c:when test="${ordvo.status == 0}">
												<td>-</td>
												<td>-</td>
											</c:when>
											<c:when test="${ordvo.status == 1}">
												<td><a class="pointer send-product">�w��f</a></td>
												<td><a class="pointer report-conflic">����</a></td>
											</c:when>
											<c:when test="${ordvo.status == 2}">
												<td><a class="pointer send-product">�w��f</a></td>
												<td><a class="pointer report-conflic">����</a></td>
											</c:when>
											<c:when test="${ordvo.status == 3}">
												<td>-</td>
												<td><a class="pointer report-conflic">����</a></td>
											</c:when>
											<c:when test="${ordvo.status == 6}">
												<td>-</td>
												<td><a class="pointer report-conflic">����</a></td>
											</c:when>
											<c:otherwise>
												<td>-</td>
												<td>-</td>
											</c:otherwise>
											</c:choose>
										</tr>
							</c:forEach>
						</c:if>
									</table>
								</div>
							</div>
						</div>
					</c:forEach>
					</div>
				</div><!--class="list-zone"-->
			</div><!--class="col-sm-9"-->
		</div><!--class="row"-->
	</div><!--class="container"-->
</section>
<div class="modal fade" id="rate-modal"  tabindex="-1" role="dialog" aria-labelledby="cancel-order" aria-hidden="true" >
	<div class="modal-dialog">
	    <div class="modal-content">
		 <div class="modal-header">
			<h4 class="modal-title" id="cancel-order">
			   �����έ�
			</h4>
		 </div>
		 <div class="modal-body">
		 	<p>�����|���G<span id="rate-modal-mem"></span></p>
			<div id="rate-radio-select">
				<span style="font-size: 1.em; font-weight: bolder;">�����G</span>
			  	<span><input type="radio" name="rate" value="2" > �n��</span>
			  	<span><input type="radio" name="rate" value="1" > ����</span>
			  	<span><input type="radio" name="rate" value="0" > �a��</span>
			</div>
			<p>
  			<div>
				<p style="font-size: 1.em; font-weight: bolder;">�������e�G</p>
			  	<textarea rows="5" cols="30" id="ratedesc"></textarea>
		   </div>
		 </div>
		 <div class="modal-footer">
			<button class="btn btn-default" data-dismiss="modal">
			   ����
			</button>
			<button class="btn btn-fefault cart" data-ordno="" id="rate-confirm">
			   �e�X
			</button>
		 </div>
	    </div><!-- /.modal-content -->
	</div>
</div>
<div class="modal fade" id="send-modal"  tabindex="-1" role="dialog" aria-labelledby="send-good" aria-hidden="true" >
	<div class="modal-dialog">
	    <div class="modal-content">
		 <div class="modal-header">
			<h4 class="modal-title" id="send-good">
			   �T�{��f
			</h4>
		 </div>
		 <div class="modal-body">
			<p>�z�w�g�N�ӫ~�浹�έ��F�ܡH</p>
		 </div>
		 <div class="modal-footer">
			<button class="btn btn-default" data-ordno="" id="send-confirm">
			   &nbsp;�O&nbsp;
			</button>
			<button class="btn btn-success" data-dismiss="modal">
			  &nbsp;�_&nbsp;
			</button>
		 </div>
	    </div><!-- /.modal-content -->
	</div>
</div>
<div class="modal fade" id="rate-other"  tabindex="-1" role="dialog" aria-labelledby="rating" aria-hidden="true">
	<div class="modal-dialog">
	    <div class="modal-content" style="">
		 <div class="modal-header">
			
			<h4 class="modal-title" id="rating">
			   ��������
			</h4>
		 </div>
		 <div class="modal-body">
			<p>�����|���G<a href=""><i class="fa fa-user"></i> <span>emp7001(79)</span></a></p>
			<form role="form">
			
			<p>�����G<input type="radio" name="rate">�n�� <input type="radio" name="rate"> ���q<input type="radio" name="rate"> �a��</p>
			<p>�п�J�������e (120�r�H��)</p>
			<textarea class="answer-content"></textarea>
			</form>
		 </div>
		 <div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">
				����
			</button>
			<button type="button" class="btn btn-success">
			   �e�X
			</button>
		 </div>
	    </div><!-- /.modal-content -->
	</div>
</div>
<div class="modal fade" id="report-modal"  tabindex="-1" role="dialog" aria-labelledby="report-order" aria-hidden="true">
	<div class="modal-dialog">
		    <div class="modal-content">
			 <div class="modal-header">
				<h4 class="modal-title" id="report-order">
				  �����ȯ�
				</h4>
			 </div>
			 <div class="modal-body">
				<p style="color: #d9534f; font-size: 2.2em;"><i class="fa fa-exclamation-triangle"></i> �`�N</p>
				<p>�q��g�������i�ȯɵo�͡j��N�|�ᵲ�ӵ��q�椧���B�A<br>�����ݤH�u�B�z������~�i�N���B�����D�ʩΪ��ٹέ��C</p>
				<p>�O�_�n�����ȯɡH</p>
			 </div>
			 <div class="modal-footer">
				<button class="btn btn-danger" data-ordno="" data-caseno="" id="report-confirm" data-dismiss="modal">
				   �T�w
				</button>
				<button class="btn btn-success" data-dismiss="modal">
				   ����
				</button>
			 </div>
		    </div><!-- /.modal-content -->
		</div>
</div><!-- /.modal -->
<div class="modal fade" id="report-dbcheck-modal"  tabindex="1" role="dialog" aria-labelledby="report-order-dbcheck" aria-hidden="true">
	<div class="modal-dialog" style="top:80px; width: 300px; height:200px;">
	    <div class="modal-content">
			 <div class="modal-header" style="display:none">
				<h4 class="modal-title" id="report-order-dbcheck"></h4>
			 </div>
			 <div class="modal-body">
			 <p style="font-size: 2.2em;">
				<i class="fa fa-exclamation-triangle" style="color: #d9534f; "></i> �u���n�����ܡH
			 </p>
			 </div>
			 <div class="modal-footer">
				<button class="btn btn-default pull-left" data-ordno="" data-caseno="" id="report-insist">
				   �T�w
				</button>
				<button class="btn btn-success" data-dismiss="modal">
				   ����
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

<jsp:include page="/f/frag/f_footer1.jsp"/>

<script src="${resPath}/js/jquery.balloon.min.js"></script>
<script>
	
	/*** triggers ***/
	menuTrigger(0);
	
	$('a[href="#case${param.caseno}"]').trigger('click');
	/*** triggers ***/
	
	$('#rate-bubble').css('cursor','pointer').balloon({
		offsetX: -20,
		contents: '<p>�o�쪺���� / ����������</p>'
	});
	
	var CurrentPageEnv = {
		controller: '${pageHome}/order.do',
		caseno: '${param.caseno}',
		rateErr:'<p><i class=\"fa fa-exclamation-circle\" style=\"font-size:2em; color:#FE980F;\" ></i>&nbsp;&nbsp;�п�ܵ���</p>',
		descErr:'<p><i class=\"fa fa-exclamation-circle\" style=\"font-size:2em; color:#FE980F;\" ></i>&nbsp;&nbsp;�п�J���e</p>'
	};
</script>
<script src="${pageHome}/js/view_case_orders.js"></script>
<jsp:include page="/f/frag/f_footer2.jsp"/>