<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ include file="/f/frag/f_login_redirect.file" %>
<%@ page import="com.tao.shop.model.*"%>
<%
	ShopService shopSvc = new ShopService();
	List<ShopVO> list = shopSvc.getAll();
	pageContext.setAttribute("list", list);
%>
<%	pageContext.setAttribute("context", request.getContextPath()); %>
<%

    String pageHome = request.getContextPath() + "/shop";
    pageContext.setAttribute("pageHome", pageHome);
%>
<%-- <jsp:useBean id="memberSvc" scope="page" 	class="com.tao.member.model.MemberService" /> --%>
<jsp:useBean id="locationSvc" scope="page" 	class="com.tao.location.model.LocationService" />
<c:set var="locationList" value="${locationSvc.all}"/>
<jsp:include page="/f/frag/f_header1.jsp"/>
<title>�ө��޲z | Ź���a��</title>
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>
<head>
<STYLE>
body { background-color:white; } 
.modal-title {
font-weight:bold; 
color: #FE980F; 
font-family: �L�n������;
}
.modal-body p span {
	font-size: 1.5em;
}
.modal-body p .total {
	color:#FE980F; 
}
.modal-open{
	overflow: initial;
}
.modal-body b{
font-family: �L�n������;
font-size: 1.1em;
}
 </STYLE>
</head>
<section>
	<div class="container">
		<div class="row">
			<jsp:include page="/f/frag/f_memCenterMenu.jsp"/>
				<div class="col-sm-9" >
					<h2 class="title text-center">�ө��C��<h2>
					<div class="list-zone">
						<table class="table table-hover table-condensed table-striped" style="font-size: 0.5em; font-family:�L�n������;">
							<tr class="list-header info">
								<th>�ө��s��</th>
								<th>�W��</th>
								<th>�a��</th>
								<th>���A</th>
								<th>�s�� / �˵��ӫ~</th>
								<th colspan="3" text="center">�ާ@</th>
							</tr>
							<c:forEach var="shopVO" items="${list}">
							<c:if test="${shopVO.memno==CurrentUser.memno}">
							<tr class="mainrow" id="order1">
								<td>${shopVO.shopno}</td>
								<c:if test="${shopVO.status==2}">
									<td><a href="<%=request.getContextPath()%>/shop/shop.do?shopno=${shopVO.shopno}&action=getOne_For_Display">${shopVO.title}</a></td>	
								</c:if>
								<c:if test="${shopVO.status!=2}">
									<td><a href="<%=request.getContextPath()%>/shop/shop.do?shopno=${shopVO.shopno}&action=getTemp_Display">${shopVO.title}</a></td>	
								</c:if>
								
								<td>
									<c:forEach var="locationVO" items="${locationList}">
										<c:if test="${shopVO.locno==locationVO.locno}">
						                    	${locationVO.county}
					                    </c:if>
								     </c:forEach>
								</td>
								<td>
									<c:if test="${shopVO.status==0}" >�w�إ�</c:if>
									<font style="color: orange;"><c:if test="${shopVO.status==1}">�ݼf��</c:if></font>
									<font style="color: blue;"><c:if test="${shopVO.status==2}">�W�[��</c:if></font>
									<font style="color: green;"><c:if test="${shopVO.status==3}">�w�U�[</c:if></font>
									<font style="color: gery;"><c:if test="${shopVO.status==4}">�w�R��</c:if></font>
									<font style="color: red;"><c:if test="${shopVO.status==5}">���q�L�f��</c:if></font>
								</td>
								<c:choose>
									<c:when test="${shopVO.status==0|| shopVO.status==3||shopVO.status==5}">
										<td>
											<a href="<%=request.getContextPath()%>/shop/shop.do?shopno=${shopVO.shopno}&action=getOne_For_Update" ><i class="fa fa-edit fa-fw"></i>�s�� </a>  / 
											<a href="<%=request.getContextPath()%>/shopproduct/shopproduct.do?shopno=${shopVO.shopno}&action=listShopproduct_ByCompositeQuery">�˵��ӫ~ <i class="fa fa-eye fa-fw"></i></a>
										</td>
										<td id="up"><a data-toggle="modal" data-target="#up_shop_${shopVO.shopno}" style="cursor: pointer; "><i class="fa fa-arrow-up fa-fw"></i>�ӽФW�[</a></td>
										<td>��</td>
										<td><a data-toggle="modal" data-target="#del_shop_${shopVO.shopno}" style="cursor: pointer; "><i class="fa fa-trash-o fa-fw"></i>�R��</a></td>
									</c:when>
									<c:when test="${shopVO.status==2}">
										<td>
											<a href="<%=request.getContextPath()%>/shop/shop.do?shopno=${shopVO.shopno}&action=getOne_For_Update" ><i class="fa fa-edit fa-fw"></i>�s�� </a>  / 
											<a href="<%=request.getContextPath()%>/shopproduct/shopproduct.do?shopno=${shopVO.shopno}&action=listShopproduct_ByCompositeQuery">�˵��ӫ~ <i class="fa fa-eye fa-fw"></i></a>
										</td>
										<td>��</td>
										<td id="down"><a data-toggle="modal" data-target="#down_shop_${shopVO.shopno}" style="cursor: pointer; "><i class="fa fa-arrow-down fa-fw"></i>�U�[</a></td>
										<td><a data-toggle="modal" data-target="#del_shop_${shopVO.shopno}" style="cursor: pointer; "><i class="fa fa-trash-o fa-fw"></i>�R��</a></td>
									</c:when>
									<c:when test="${shopVO.status==1}">
										<td>
											<a href="<%=request.getContextPath()%>/shop/shop.do?shopno=${shopVO.shopno}&action=getOne_For_Update" ><i class="fa fa-edit fa-fw"></i>�s�� </a>  / 
											<a href="<%=request.getContextPath()%>/shopproduct/shopproduct.do?shopno=${shopVO.shopno}&action=listShopproduct_ByCompositeQuery">�˵��ӫ~ <i class="fa fa-eye fa-fw"></i></a>
										</td>
										<td>��</td>
										<td id="down"><a data-toggle="modal" data-target="#cancel_shop_${shopVO.shopno}" style="cursor: pointer; "><i class="fa fa-times fa-fw"></i >����</a></td>
										<td><a data-toggle="modal" data-target="#del_shop_${shopVO.shopno}" style="cursor: pointer; "><i class="fa fa-trash-o fa-fw"></i>�R��</a></td>
									</c:when>
									<c:otherwise>
										<td>�� </td>
										<td>��</td>
										<td>��</td>
										<td>��</td>
									</c:otherwise>
								</c:choose>
								
								
							</tr>
							</c:if>
							</c:forEach>		
						</table>
						
						<div style="text-align: right; margin-top: 40px;">
						<button class="btn btn-success"><a href='create_shop_front.jsp' style="color:white">�إ߰ө� <i class="fa fa-plus"></i></a></button>
						</div>
					</div><!--class="list-zone"-->
				</div><!--class="col-sm-9"-->
		</div>
	</div>
</section>

<!-- �R���O�c��o�� -->
<c:forEach var="shopVO" items="${list}">
<div class="modal fade" id="del_shop_${shopVO.shopno}" role="dialog"
	aria-labelledby="AdModal" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="AdModal" style="color: #d9534f;">
					<i class="fa fa-warning fa-fw"></i>�����T��
				</h4>

			</div>
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/shop/shop.do">
				<div class="modal-body">
					�T�w�R���ө��s�� <b><font color=#d9534f class="delcomsg">${shopVO.shopno}�y${shopVO.title}�z</font></b> ����?
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">����</button>
					<input type="submit" value="�R��" class="btn btn-danger"> 
					<input type="hidden" name="shopno" value="${shopVO.shopno}"> 
					<input type="hidden" name="action" value="update_stauts">
					<input type="hidden" name="status" value="4">
					<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller-->
				</div>
			</FORM>
		</div>
	</div>
</div>
</c:forEach>
<!-- �ӽФW�[�O�c��o�� -->
<c:forEach var="shopVO" items="${list}">
<div class="modal fade" id="up_shop_${shopVO.shopno}" role="dialog"
	aria-labelledby="AdModal" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="AdModal" style="color: #f0ad4e;">
					<i class="fa fa-warning fa-fw"></i>�����T��
				</h4>

			</div>
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/shop/shop.do">
				<div class="modal-body">
					�ө��s�� <b><font color="#f0ad4e" class="delcomsg">${shopVO.shopno}�y${shopVO.title}�z</font></b> �T�w�ӽФW�[?
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">����</button>
					<input type="submit" value="�ӽФW�[" class="btn btn-warning"> 
					<input type="hidden"    name="shopno" value="${shopVO.shopno}"> 
					<input type="hidden" name="action" value="update_stauts">
					<input type="hidden" name="status" value="1">
					<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller-->
				</div>
			</FORM>
		</div>
	</div>
</div>
</c:forEach>
<!-- �U�[�O�c��o�� -->
<c:forEach var="shopVO" items="${list}">
<div class="modal fade" id="down_shop_${shopVO.shopno}" role="dialog"
	aria-labelledby="AdModal" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="AdModal" style="color: #5cb85c;">
					<i class="fa fa-warning fa-fw"></i>�����T��
				</h4>

			</div>
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/shop/shop.do">
				<div class="modal-body">
					�ө��s�� <b><font color="#5cb85c" class="delcomsg">${shopVO.shopno}�y${shopVO.title}�z</font></b> �T�w�U�[?
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">����</button>
					<input type="submit" value="�U�[" class="btn btn-success">
					<input type="hidden"    name="shopno" value="${shopVO.shopno}"> 
					<input type="hidden" name="action" value="update_stauts">
					<input type="hidden" name="status" value="3">
					<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller-->
				</div>
			</FORM>
		</div>
	</div>
</div>
</c:forEach>
<!-- �����f�֤W�[�O�c��o�� -->
<c:forEach var="shopVO" items="${list}">
<div class="modal fade" id="cancel_shop_${shopVO.shopno}" role="dialog"
	aria-labelledby="AdModal" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="AdModal" style="color: orange;">
					<i class="fa fa-warning fa-fw"></i>�����T��
				</h4>

			</div>
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/shop/shop.do">
				<div class="modal-body">
					�T�w�����f�ְө��s�� <b><font color="orange" class="delcomsg">${shopVO.shopno}�y${shopVO.title}�z</font></b> ?
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">����</button>
					<input type="submit" value="�T�w" class="btn btn-warning">
					<input type="hidden"    name="shopno" value="${shopVO.shopno}"> 
					<input type="hidden" name="action" value="update_stauts">
					<input type="hidden" name="status" value="0">
					<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller-->
				</div>
			</FORM>
		</div>
	</div>
</div>
</c:forEach>
<jsp:include page="/f/frag/f_footer1.jsp"/>
	<script>
		var push = function(){
			$.post('<%= request.getContextPath()%>/shop/shop.do', 'action=push', function(data){
				console.log('gotResponse');
				if(data == 'success'){
				console.log('success');
					window.location.href = '<%= request.getContextPath()%>/shop/shop_member_center.jsp';
				}
			});	
		};
		$(function(){
			push();
		});
	</script>
<jsp:include page="/f/frag/f_footer2.jsp"/>