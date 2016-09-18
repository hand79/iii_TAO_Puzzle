<%@page import="com.tao.member.model.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<%--**** LOGIN REDIRECT ****--%>
<%@include file="/f/frag/f_login_redirect.file" %>
<%--**** LOGIN REDIRECT ****--%>
<%@ page import="java.util.*" %>
<%@ page import="com.tao.jimmy.location.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% pageContext.setAttribute("pageHome", request.getContextPath() + "/cases");%>

<c:set var="hasCvo" value="${not empty cvo}"/>
<c:set var="Add_or_Edit" value="${not empty cvo ? '�s��':'�إ�'}"/>
<%	
	CountyService countyService = new CountyService();
	Set<CountyVO> counties = countyService.findCounties();
	pageContext.setAttribute("counties", counties);
	MemberVO memvo = (MemberVO)session.getAttribute("CurrentUser");
%>

<jsp:include page="/f/frag/f_header1.jsp"/>

<title>�إߦX�ʮ� | TAO Puzzle</title>
<STYLE>
	#product-price, #final-price{
		font-size:1.2em;
		margin-top: 5px;
	}
	#casedesc{
		height: 350px;
	}
	.confirm-addCP{
		margin-top: 0px;
	}
	.modal-title {
	font-weight:bold; 
	color: #FE980F; 
	font-family: �L�n������;
	}
	.preview{
		width: 320px;
	}
	#cp-desc{
		height: 200px;
	}
	.modal-open{
	overflow: initial;
	}
	.invalidText{
		border-color: red;
	}
	#main-contact-form{
	font-family: �L�n������;
	}
	.invalid{
		border-color: red;
	}
	#ajaxMsgModal{
		text-align: center;
	}
</STYLE>
<%
request.setAttribute("showBreadcrumb", new Object()); 
LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
map.put("�|������","");
request.setAttribute("breadcrumbMap", map); 
%>	
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>
	
	
<section>
	<div class="container">
		<div class="row">

<jsp:include page="/f/frag/f_memCenterMenu.jsp" /> 
			
			<div class="col-sm-9">
				<div class="contact-form">
    				<h2 class="title text-center">${Add_or_Edit}�X�ʮ�</h2>
    				<div class="status alert alert-success" style="display: none"></div>
			    	<form id="main-contact-form" class="contact-form row">
			    		<c:if test="${not empty cvo}">
			    		<div class="form-group col-md-1">
							<label for="case-title">�s��</label>
			                <div style="margin-top: 8px;">${cvo.caseno}</div>
			            </div>
			            </c:if>
			            <div class="form-group col-md-${empty cvo ? '12':'11' }">
							<label for="case-title"><i class="fa fa-fw fa-check-square"></i>�X�ʦW��</label>
			                <c:choose>
			                <c:when test="${not empty cvo}">
			               		<input type="text" name="title" class="form-control" required="required" placeholder="�X�ʮצW��" id="case-title" value="${cvo.title}">
			                </c:when>
			                <c:otherwise>
			                	<input type="text" name="title" class="form-control" required="required" placeholder="�X�ʮצW��" id="case-title" value="">
			                </c:otherwise>
			                </c:choose>
			            </div>
			            <div class="form-group col-md-5">
							<label for="case-product" ><i class="fa fa-fw fa-check-square"></i>�ҭn�X�ʪ��ӫ~</label>
					<c:choose>				
					<c:when test="${spvo != null}">
						<select class="form-control" name="spno" id="case-product" required="required">
							<option value="${spvo.spno}" data-price="${spvo.unitprice}"  selected>S${spvo.spno} - ${spvo.name}</option>
						</select>
					</c:when>
					<c:when test="${CurrentUser.type == 0 }">
							<select class="form-control" name="cpno" id="case-product" required="required">
								<c:if test="${empty cpList}">
									<option value="-1" data-price="">(�|���إߦX�ʰӫ~)</option>
								</c:if>
								<c:if test="${not empty cpList}">
								<c:forEach var="cpvo"  items="${cpList}" varStatus="sts">
								<c:if test="${sts.first}">
									<option value="-1" data-price="">(�п�ܰӫ~)</option>
								</c:if>
									<option value="${cpvo.cpno}" data-price="${cpvo.unitprice}" ${(cvo.cpno == cpvo.cpno) ? 'selected':''}>C${cpvo.cpno} - ${cpvo.name}</option>
								</c:forEach>
								</c:if>
							</select>
					</c:when>
					<c:when test="${CurrentUser.type == 1 }">
							<select class="form-control" name="spno" id="case-product" required="required">
								<c:if test="${empty spList}">
									<option value="-1" data-price="">${NoActiveShop != null ? '(�|�����W�[�����ө�)' : '(�W�[���ө��|���إ߰ӫ~)'}</option>
								</c:if>
								<c:if test="${not empty spList}">
								<c:forEach var="spvo"  items="${spList}" varStatus="sts">
								<c:if test="${sts.first}">
									<option value="-1" data-price="">(�п�ܰӫ~)</option>
								</c:if>
									<option value="${spvo.spno}" data-price="${spvo.unitprice}" ${(param.spno == spvo.spno) ? 'selected':''} ${(cvo.spno == spvo.spno) ? 'selected':''}>S${spvo.spno} - ${spvo.name}</option>
								</c:forEach>
								</c:if>
							</select>
					</c:when>
					</c:choose>
			            </div>
						<div class="form-group col-md-1 cp-price">
							<label for="product-price">���</label>
							<div id="product-price">-</div>
						</div>
						<div class="form-group col-md-4 cp-price">
							<label for="discount"><i class="fa fa-fw fa-check-square"></i>�馩���B</label>
							<input type="number" class="form-control" id="discount" name="discount" min="0" placeholder="�Х���ܰӫ~" value="${cvo.discount}"/>
						</div>
						<div class="form-group col-md-2 cp-price">
							<label for="final-price">�X�ʻ�</label>
							<div id="final-price">-</div>
						</div>
						
			            <div class="form-group col-md-3">
							<label for="county"><i class="fa fa-fw fa-check-square"></i>�Ҧb�a��</label>
			                <select class="form-control" id="county-list" required="required">
									<option value="">-</option>
								<c:forEach var="countyVO" items="${counties}">
									<option value="${countyVO.value}" ${requestScope.countyVO.name == countyVO.name ? 'selected':'' }>${countyVO.name}</option>
								</c:forEach>
							</select>
			            </div>
						<div class="form-group col-md-3">
							<label for="town">&nbsp;</label>
			                <select class="form-control" name="locno" id="town-list" required="required">
								<c:if test="${empty towns}">
								<option value="">-</option>
								</c:if>
								<c:if test="${not empty towns }">
									<jsp:include page="Ajax_findTown.jsp"/>
								</c:if>
							</select>
			            </div>
						<div class="form-group col-md-3">
							<label for="ship1"><i class="fa fa-fw fa-check-square"></i>��f�覡�@</label>	
			                <input type="text" name="ship1" class="form-control" id="ship1" required="required" placeholder="�Ҧp�G����" value="${cvo.ship1}">
			            </div> 
						<div class="form-group col-md-3">
							<label for="shipcost1"><i class="fa fa-fw fa-check-square"></i>�O�Τ@</label>	
			                <input type="number" name="shipcost1" class="form-control" id="shipcost1" min="0" placeholder="�Y���ιB�O�п�J0" value="${cvo.shipcost1}">
			            </div> 
			            <div class="form-group col-md-3">
							<label for="minqty"><i class="fa fa-fw fa-check-square"></i>���Ϊ��e�ƶq</label>
			                <input type="number" name="minqty" class="form-control" id="minqty" min="0" max="999" placeholder="�Y���]���e�п�J0" value="${cvo.minqty}">
			            </div>   
			            <div class="form-group col-md-3">
							<label for="maxqty"><i class="fa fa-fw fa-check-square"></i>�̤j�X�ʼƶq</label>	
			                <input type="number" name="maxqty" class="form-control" id="maxqty" min="0" max="999" placeholder="�̤j�ƶq�ݤj��0" value="${cvo.maxqty}">
			            </div> 				
						<div class="form-group col-md-3">
							<label for="ship2">��f�覡�G </label>	
			                <input type="text" name="ship2" class="form-control" id="ship2" placeholder="�Ҧp�G�l�H " value="${cvo.ship2}">
			            </div> 
						<div class="form-group col-md-3">
							<label for="shipcost2">�O�ΤG </label>	
			                <input type="number" name="shipcost2" class="form-control" id="shipcost2" placeholder="�Y���ζO�νп�J0" value="${cvo.ship2 == null ? '': cvo.shipcost2}">
			            </div> 
						<div class="form-group col-md-12">
							<label for="casedesc"><i class="fa fa-fw fa-check-square"></i>�X�ʻ���</label>	
			                <textarea class="form-control" name="casedesc" id="casedesc">${cvo.casedesc}</textarea>
			            </div> 
			            <div class="form-group col-md-12">
			               	<a style="cursor: help;" id="magicBtn"><span style="color: #d43f3a; font-weight: bolder;"><i class="fa fa-fw fa-check-square"></i>������</span></a>
							<span class="pull-right"><a href="<%=request.getContextPath() %>/cases/cases.do?action=viewOwnCases" class="btn btn-default" style="margin-right: 20px;" >����${Add_or_Edit}</a><button class="btn btn-primary" style="margin: 0px;" id="submitForm">${Add_or_Edit}</button></span>
			            </div>
			        </form>
    			</div>
			</div><!--class="col-sm-9"-->
		</div>
	</div>
</section>
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
	<script >
	menuTrigger(0);
	var CurrentPageEnv = {
			controller: '${pageHome}/cases.do',
			errltzero:'�馩���B�L�j',
			errNaN:'�馩�榡���~',
			discountPlaceholder:'�п�J�馩���B�A�̤p�Ȭ�0',
			caseno:'${param.caseno}',
			spvo:'${spvo != null || param.spno != null}'
	};
	$(function(){
		$('#magicBtn').click(function(){
			<% if(memvo.getType() == 0){%>
			$('input[name="title"]').val('�f�Q�f���J�O�氮�������� 10�J�]��');
			$('#ship1').val('����t���O');
			$('#shipcost1').val('5');
			$('#ship2').val('�l�H');
			$('#shipcost2').val('60');
			$('#minqty').val('10');
			$('#maxqty').val('100');
			<%}else{%>
			$('input[name="title"]').val('[���ɷm��]YA803�ĤG�ռy�����V�y�W���z�߰_�h�C�Y�z���Ӥj���1���u�n5��');
			$('#ship1').val('�i�ܲ{������');
			$('#shipcost1').val('0');
			$('#ship2').val('�l�H');
			$('#shipcost2').val('60');
			$('#minqty').val('20');
			$('#maxqty').val('100');
			$('#casedesc').val(
					'�|����u�C�Y...�@�a�C�Ѧ۲��۾P�B�{���{�檺��u�C�Y��\n'+
					'[YA803 �Z�N]�j�O���˶W~~~~~~~~���z�߰_�h�C�Y!!\n'+
					'�n�Y��}���10�������g!!');
			<%}%>
		});
	});
	</script>
	<script src="${pageHome}/js/add_or_edit_case.js"></script>
</body>
</html>