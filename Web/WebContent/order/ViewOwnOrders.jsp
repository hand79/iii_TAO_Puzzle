<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<%--**** LOGIN REDIRECT ****--%>
<%@include file="/f/frag/f_login_redirect.file" %>
<%--**** LOGIN REDIRECT ****--%>
<%@ page import="com.tao.order.model.*"%>
<%@ page import="java.util.LinkedHashMap" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% pageContext.setAttribute("pageHome", request.getContextPath() + "/order"); %>
<% pageContext.setAttribute("casesController", request.getContextPath() + "/cases/cases.do"); %>
    
<jsp:include page="/f/frag/f_header1.jsp"/>
<title>�ѥ[���X�� | TAO Puzzle</title>	
  <STYLE>
    body { 
    	background-color:white; 
    } 
	.modal-title {
		font-weight:bold; 
		color: #FE980F; 
		font-family: �L�n������;
	}
	#detail-modal .modal-dialog{
		width: 20%;
	}
	#cancel-modal .modal-dialog{
		top: 100px;
	}
	.modal-body span, .modal-body h4, .modal-body p{
		font-family: �L�n������;
	}
	.modal-body p span {
		font-size: 1.2em;
	}
	.modal-body p .total {
		color:#FE980F; 
	}
	.modal-body div span{
		margin-right: 10px;
	}
	.modal-open{
		overflow: initial;
	}
	.viewOrdDetail{
		font-weight: bolder;
		text-decoration: underline;
	}
	.pointer{
		cursor: pointer;
	}
	#ajaxMsgModal .modal-body{
		text-align: center;
	}
  </STYLE>
<%
request.setAttribute("showBreadcrumb", new Object()); 
java.util.LinkedHashMap<String, String> map = new java.util.LinkedHashMap<String, String>();
map.put("�|������","");
request.setAttribute("breadcrumbMap", map); 
%>
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>
	
	<section>
		<div class="container">
			<div class="row">

<jsp:include page="/f/frag/f_memCenterMenu.jsp"/>
				<div class="col-sm-9" >
					<h2 class="title text-center">�ѥ[���X��</h2>
					<div class="list-zone">
						<table class="table table-hover table-condensed table-striped" style="font-size: 1em; font-family:�L�n������;">
							<tr class="list-header info">
								<th>�q��s��</th>
								<th>�X�ʮ�</th>
								<th>�U��ɶ�</th>
								<th>����</th>
								<th>���B</th>
								<th>���A</th>
								<th>�ާ@</th>
								<th>�ȯɴ���</th>
							</tr>
					<c:if test="${empty ordList}">
							<tr>
								<td colspan="8" style="text-align: center"><i>�L�q��i���</i></td>
							</tr>
					</c:if>
					<c:if test="${not empty ordList}">
						<c:forEach var="ordvo" items="${ordList }" varStatus="sts">
							<c:set var="ordvo" value="${ordvo}"/>
							<% OrderVO ordvo = (OrderVO) pageContext.getAttribute("ordvo"); %>
							<tr data-ordno="${ordvo.ordno}">
								<td>${ordvo.ordno}</td><!-- �q��s�� -->
								<td class="pointer viewOrdDetail">
							<c:forEach var="cvo" items="${casesSet}">
								<c:if test="${cvo.caseno == ordvo.caseno}">${cvo.shortenedTitle}</c:if><!-- �X�ʦW�� -->
							</c:forEach>
								</td>
								<td><i class="fa fa-clock-o"></i> ${ordvo.formatedOrdtime}</td><!-- �U��ɶ� -->
								<td>
								<c:if test="${ordvo.brate == null}">
									<c:if test="${ordvo.status != 0}">
									<i>����o</i>
									</c:if>
									<c:if test="${ordvo.status == 0}">
									-
									</c:if>
								</c:if>
								<c:if test="${ordvo.brate != null}">
									<%=OrderRate.getRate(ordvo.getBrate()) %>
								</c:if> / <c:if test="${ordvo.crate == null}">
									<c:if test="${ordvo.status != 0}">
									<a class="pointer give-rate">����</a>
									</c:if>
									<c:if test="${ordvo.status == 0}">
									-
									</c:if>
								</c:if>
								<c:if test="${ordvo.crate != null}">
									<%=OrderRate.getRate(ordvo.getCrate()) %>
								</c:if></td> <!-- �o�� / ���� -->
								<td>${ordvo.price}</td><!-- ���B -->
								<td><%=OrderStatus.getStatusName(ordvo.getStatus()) %></td><!-- ���A -->
								<!-- �ާ@1 & 2 -->
								<c:choose>
								<c:when test="${ordvo.status == 0}">
									<td><a class="pointer cancel-order">�h�X�X��</a></td>
									<td>-</td>
								</c:when>
								<c:when test="${ordvo.status == 1}">
									<td><a class="pointer recieve-product">�T�{�w���f</a></td>
									<td><a class="pointer report-conflic">����</a></td>
								</c:when>
								<c:when test="${ordvo.status == 2}">
									<td>-</td>
									<td><a class="pointer report-conflic">����</a></td>
								</c:when>
								<c:when test="${ordvo.status == 3}">
									<td><a class="pointer recieve-product">�T�{�w���f</a></td>
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
					</div><!--class="list-zone"-->
				</div><!--class="col-sm-9"-->
			</div>
		</div>
	</section>

<div class="modal fade" id="detail-modal"  tabindex="-1" role="dialog" aria-labelledby="view-detail" aria-hidden="true" >
	<div class="modal-dialog">
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
		 <div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">
			   �T�w
			</button>
		 </div>
	    </div><!-- /.modal-content -->
	</div>
</div>
<div class="modal fade" id="rate-modal"  tabindex="-1" role="dialog" aria-labelledby="cancel-order" aria-hidden="true" >
	<div class="modal-dialog">
	    <div class="modal-content">
		 <div class="modal-header">
			<h4 class="modal-title" id="cancel-order">
			   �����D��
			</h4>
		 </div>
		 <div class="modal-body">
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
<div class="modal fade" id="cancel-modal"  tabindex="-1" role="dialog" aria-labelledby="cancel-order" aria-hidden="true" >
	<div class="modal-dialog">
	    <div class="modal-content">
		 <div class="modal-header">
			<h4 class="modal-title" id="cancel-order">
			   �h�X�X��
			</h4>
		 </div>
		 <div class="modal-body">
			<p>�h�X�X�ʱN�|���������q��åB�h�٭q����B�A</p>
			<p>�z�T�w�h�X�X�ʡH</p>
		 </div>
		 <div class="modal-footer">
			<button class="btn btn-default" data-ordno="" id="cancel-confirm">
			   �T�w
			</button>
			<button class="btn btn-success" data-dismiss="modal">
			   ����
			</button>
		 </div>
	    </div><!-- /.modal-content -->
	</div>
</div>
<div class="modal fade" id="recieve-modal"  tabindex="-1" role="dialog" aria-labelledby="recieve-good" aria-hidden="true" >
	<div class="modal-dialog">
	    <div class="modal-content">
		 <div class="modal-header">
			<h4 class="modal-title" id="recieve-good">
			   �T�{���f
			</h4>
		 </div>
		 <div class="modal-body">
			<p>�z�w�g����z�ҦX�ʪ��ӫ~�ܡH</p>
		 </div>
		 <div class="modal-footer">
			<button class="btn btn-default" data-ordno="" id="recieve-confirm">
			   &nbsp;�O&nbsp;
			</button>
			<button class="btn btn-success" data-dismiss="modal">
			  &nbsp;�_&nbsp;
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
				<button class="btn btn-danger" data-ordno="" id="report-confirm" data-dismiss="modal">
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
				<button class="btn btn-default pull-left" data-ordno="" id="report-insist">
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
<!-- 	<script src="js/jquery.balloon.min.js"></script> -->
	<script>
// 		menuTrigger(0);
		var CurrentPageEnv ={
			controller: '${pageHome}/order.do',
			casesController: '${casesController}',
			errMsg:'<i class=\"fa fa-exclamation-circle\" style=\"font-size:2em; color:#FE980F;\" ></i> �o�Ϳ��~',
			rateErr:'<p><i class=\"fa fa-exclamation-circle\" style=\"font-size:2em; color:#FE980F;\" ></i>&nbsp;&nbsp;�п�ܵ���</p>',
			descErr:'<p><i class=\"fa fa-exclamation-circle\" style=\"font-size:2em; color:#FE980F;\" ></i>&nbsp;&nbsp;�п�J���e</p>'
		};
	</script>
	<script src="${pageHome}/js/view_own_orders.js"></script>
	
</body>
</html>