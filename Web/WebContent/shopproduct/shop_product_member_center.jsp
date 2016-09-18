<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/f/frag/f_login_redirect.file" %>
<%@ page import="java.util.*"%>
<%@ page import="com.tao.shopproduct.model.*"%>
<% 
   List<ShopproductVO> listShopproduct_ByCompositeQuery = (List<ShopproductVO>)request.getAttribute("listShopproduct_ByCompositeQuery");
   pageContext.setAttribute("listShopproduct_ByCompositeQuery", listShopproduct_ByCompositeQuery);
%>
<jsp:useBean id="subCategorySvc" scope="page" 	class="com.tao.category.model.SubCategoryService" />
<c:set var="subcatall" value="${subCategorySvc.all}"/>
<%-- <jsp:useBean id="shopSvc" scope="page" 	class="com.tao.shop.model.ShopService" /> --%>
<jsp:include page="/f/frag/f_header1.jsp"/>
<title>�ө��ӫ~�޲z| Ź���a��</title>
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
					<h2 class="title text-center">
                     	�y${shopVO.title}�z�ӫ~�C��
					</h2>
					<div class="list-zone">
						<table class="table table-hover table-condensed table-striped" style="font-size: 1em; font-family:�L�n������;">
							<tr class="list-header info">
								<th>�ӫ~�s��</th>
								<th>�ӫ~�W��</th>
								<th>�ӫ~�Ϥ�</th>
								<th>�W��</th>
								<th>���</th>
								<th>�ӫ~����</th>
								<th>�O�_����</th>
								<th>�s��</th>
								<th>�ާ@</th>
							</tr>
							<c:if test="${empty listShopproduct_ByCompositeQuery}">
								<tr>
									<th colspan="10" class="text-center">�|���إ߰ӫ~</th>
								</tr>				
							</c:if>			
							<c:forEach var="spVO" items="${listShopproduct_ByCompositeQuery}">
							<tr class="mainrow" id="order1">
									<c:if test="${shopVO.status==2}">
										<td><a href="<%=request.getContextPath()%>/shopproduct/shopproduct.do?spno=${spVO.spno}&shopno=${spVO.shopno}&action=getOne_For_Display" >S${spVO.spno}</td>	
									</c:if>
									<c:if test="${shopVO.status!=2}">
										<td><a href="<%=request.getContextPath()%>/shopproduct/shopproduct.do?spno=${spVO.spno}&shopno=${spVO.shopno}&action=getTemp_Display" >S${spVO.spno}</td>	
									</c:if>
								<td>${spVO.name}</td>
								<td><img src="<%=request.getContextPath()%>/shopproduct/shopproduct.do?spno=${spVO.spno}&action=getPic1" width="45" height="45"/></br>
								</td>
								<td>
									${spVO.spec1}<br/>
									${spVO.spec2}<br/>
									${spVO.spec3}
								</td>
								<td>${spVO.unitprice}</td>
								<td>
									<c:forEach var="subCategoryVO" items="${subcatall}">
										<c:if test="${subCategoryVO.subcatno==spVO.subcatno}">
						                     ${subCategoryVO.subcatname}
					                    </c:if>
									 </c:forEach>
								</td>
								<td>
									<c:if test="${spVO.isrecomm==null}">��</c:if> 
									<font 	style="color: red;"><c:if test="${spVO.isrecomm==1}">�O</c:if></font>
									<font style="color: blue;"><c:if test="${spVO.isrecomm==2}">�_</c:if></font>
								</td>
								<td>
<%-- 									<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/shopproduct/shopproduct.do"> --%>
<!-- 										<input type="submit" value="�s��">  -->
<%-- 										<input type="hidden" 	name="spno" value="${spVO.spno}"> --%>
<!-- 										<input 	type="hidden" name="action" value="getOne_For_Update">  -->
<%-- 										<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller--> --%>
<!-- 									</FORM> -->
									<a href="<%=request.getContextPath()%>/shopproduct/shopproduct.do?spno=${spVO.spno}&action=getOne_For_Update" ><i class="fa fa-edit"></i> �s�� </a>
								</td>
								<td>
									<a data-toggle="modal" data-target="#del_sp_${spVO.spno}" style="cursor: pointer; color:red;"><i class="fa fa-trash-o"></i> �R��</a>
								</td>
							</tr>
				</c:forEach>
						</table>
						<div style="text-align: right; margin-top: 20px;margin-bottom: 40px;">
						<a style=" margin-right: 20px;" class=" btn btn-default" href="<%=request.getContextPath()%>/shop/shop_member_center.jsp">��^</a>
							<a href="<%=request.getContextPath()%>/shopproduct/shopproduct.do?shopno=${shopVO.shopno}&action=getOne_For_Create" style="color:white"><button class="btn btn-success">�إ߰ӫ~ +</button></a>
						</div>
					</div><!--class="list-zone"-->
				</div><!--class="col-sm-9"-->
		</div>
	</div>
</section>

<jsp:include page="/f/frag/f_footer1.jsp"/>


<!-- �R���O�c��o�� -->
<c:forEach var="spVO" items="${listShopproduct_ByCompositeQuery}">
<div class="modal fade" id="del_sp_${spVO.spno}" role="dialog"
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
			<FORM METHOD="" ACTION="<%=request.getContextPath()%>/shopproduct/shopproduct.do">
				<div class="modal-body">
					�T�w�R���ӫ~�s�� <b><font color=#d9534f class="delcomsg">${spVO.spno}�y${spVO.name}�z</font></b> ����?
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">����</button>
					<input type="hidden"    name="spno" value="${spVO.spno}"> 
					<input type="hidden"    name="shopno" value="${spVO.shopno}"> 
					<input type="submit" value="�R��" class="btn btn-danger"> 
					<input type="hidden" name="action" value="delete">
				</div>
			</FORM>
		</div>
	</div>
</div>
</c:forEach>
<jsp:include page="/f/frag/f_footer2.jsp"/>