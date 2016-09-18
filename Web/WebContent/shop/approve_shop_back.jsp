<%@ page import="com.tao.cathy.util.DateFormater"%> 
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.tao.shop.model.*"%>
<%
    String resPath = request.getContextPath() + "/b/res";
    pageContext.setAttribute("resPath", resPath);

    String pageHome = request.getContextPath() + "/shop";
    pageContext.setAttribute("pageHome", pageHome);
%>

<%
	ShopService shopSvc = new ShopService();
	List<ShopVO> list = shopSvc.getWaitApproveShop();
	pageContext.setAttribute("list", list);
%>
<!-- head���Y��o�� -->

<jsp:useBean id="memberSvc" scope="page" 	class="com.tao.member.model.MemberService" />
<jsp:useBean id="locationSvc" scope="page" 	class="com.tao.location.model.LocationService" />
<jsp:include page="/b/frag/b_header1.jsp"/>
<title>[Ź���a��]�f�֤W�[</title>
<jsp:include page="/b/frag/b_header2AndMenu.jsp"/>

</head>

		
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">�f�֤W�[</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			
			
			<!-- Content -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
						<h4 style="font-family: �L�n������; margin: 0px"><b>�f�ְө��C��</b></h4>
							<c:if test="${not empty errorMsgs}">
								<font color='red'>�Эץ��H�U���~:
									<c:forEach var="message" items="${errorMsgs}">
										<li>${message}</li>
									</c:forEach>
								</font>
							</c:if>
						</div>
						
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="table-responsive">
								<table class="table table-striped table-bordered table-hover" id="dataTables-example">
									<thead>
										<tr>
											<th>�ө��s��</th>
											<th>�W��</th>
											<th>�ө��|��(�s��)</th>
											<th>�a��</th>
											<th>���A</th>
											<th>�q�L</th>
											<th>���q�L</th>

										</tr>
									</thead>
									<tbody id="trchange">
										
										 <c:if test="${empty list}">
										 <tr>
										 	<td colspan="7">�L���!</td>
										 </tr>
										 </c:if>
										<c:forEach var="shopVO" items="${list}">
											<tr align='left' data-newsno="${shopVO.shopno }"  ${(shopVO.shopno==param.shopno) ? 'class=\"success\"':''} "> 										
												<td>${shopVO.shopno}</td>
												<td><a href="<%=request.getContextPath()%>/shop/shop.do?shopno=${shopVO.shopno}&action=getTemp_Display">${shopVO.title}</a></td>
												<td>
													<c:forEach var="memberVO" items="${memberSvc.all}">
														<c:if test="${memberVO.memno == shopVO.memno}">
															<a href="<%=request.getContextPath() %>/SurfMemberServlet.do?action=memberView&memno=${shopVO.memno}" class="pointer"><i class="fa fa-fw fa-user"></i>${memberVO.memid}(${shopVO.memno})</a>
														</c:if>
													</c:forEach>
												</td>
													
												<td>
													<c:forEach var="locationVO" items="${locationSvc.all}">
														<c:if test="${shopVO.locno==locationVO.locno}">
										                   �i${locationVO.county}�j - ${locationVO.town}
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
										
												<td><span class="btn btn-outline btn-success"
													data-toggle="modal" data-target="#ok_shop_${shopVO.shopno}">�q�L</span> 
												</td>
												<td><span class="btn btn-outline btn-danger"
													data-toggle="modal" data-target="#ng_shop_${shopVO.shopno}">���q�L</span></td>
											</tr>
										</c:forEach>

									</tbody>
								</table>
								<!-- ������o�� -->

							</div>
							<!-- /.table-responsive -->
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
				<!-- /.col-lg-12 -->
			</div>
		</div>
		<!-- /#page-wrapper -->
		

   
<jsp:include page="/b/frag/b_footer1.jsp"/>
<!-- �U�[�O�c��o�� -->
<c:forEach var="shopVO" items="${list}">
	<div class="modal fade" id="ok_shop_${shopVO.shopno}" role="dialog"
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
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back/shop/shop.do">
					<div class="modal-body">
						�T�w�ө��s��<b><font color=green class="delcomsg">${shopVO.shopno}</font></b>�q�L�f��?
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">����</button>
						<input type="submit" class="btn btn-success" value="�q�L"> 
						<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller-->
						<input type="hidden" name="action" value="update_stauts">
						<input type="hidden"    name="shopno" value="${shopVO.shopno}"> 
						<input type="hidden" name="status" value="2">
					</div>
				</FORM>
			</div>
		</div>
	</div>
</c:forEach>
<!-- �R���O�c��o�� -->
<c:forEach var="shopVO" items="${list}">
	<div class="modal fade" id="ng_shop_${shopVO.shopno}" role="dialog"
		aria-labelledby="AdModal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="AdModal" style="color: #a94442">
						<i class="fa fa-warning fa-fw"></i>�����T��
					</h4>

				</div>
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back/shop/shop.do">
					<div class="modal-body">
						�T�w�ө��s��<b><font color=red class="delcomsg">${shopVO.shopno}</font></b>���q�L�f��?
						<hr/><div>
							<b><i class="fa fa-fw fa-check-square"></i>�п�J���q�L�f�֭�]:<br/><br/>
							<textarea rows="5" cols="10" style="margin: 0px; width: 565px; height: 100px;" required="required"></textarea>
						</div>
					</div>
					<div class="modal-footer">
						<label class="pull-left"><font color="#d43f3a" ><i class="fa fa-fw fa-check-square"></i>�����������</font></label>
						<button type="button" class="btn btn-default" data-dismiss="modal">����</button>
						<input type="submit" class="btn btn-danger" value="���q�L"> 
						<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller-->
						<input type="hidden" name="action" value="update_stauts">
						<input type="hidden"    name="shopno" value="${shopVO.shopno}"> 
						<input type="hidden" name="status" value="5">
					</div>
				</FORM>
			</div>
		</div>
	</div>
</c:forEach>
<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
<script>
menuTrigger(1);
	$(document).ready(function() {
		$('#dataTables-example').dataTable({
		bLengthChange:true,
		bPaginate:true,
		bInfo:true,
		bFilter:true,
		"language" :{
			"paginate" : {
				"next" : "&raquo;",
				"last": "�̥���",
				"previous" : "&laquo;",
				"first":"�Ĥ@��",
				"emptyTable": "�L���"
				
			},
			"info": "��  _PAGE_ ���A�@  _PAGES_ ��  ( _TOTAL_ ����� )",
			"lengthMenu": "�C�����  _MENU_ ��",
			"search": "�j�M: ",
			"infoEmpty": "�@ 0 ���ŦX",
			"infoFiltered": " (��  _MAX_ ���L�o�Ө�)",
			"zeroRecords": "�L�ŦX�����"
		}
		});
	});
</script>

<jsp:include page="/b/frag/b_footer2.jsp"/>