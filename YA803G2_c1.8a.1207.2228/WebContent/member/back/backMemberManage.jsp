<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="/b/frag/login_redirect.file" %>
<%@ page import="java.util.*"%>
<%@ page import="com.tao.member.model.*"%>
<%@ page import="com.tao.location.model.*"%>
<%-- �����m�߱ĥ� EL ���g�k���� --%>

<%
	List<MemberVO> list =null;
	if(request.getAttribute("list") == null){
		MemberService dao = new MemberService();
		list = dao.getAll();
	}else{
		list = (List<MemberVO>)request.getAttribute("list");
	}
	pageContext.setAttribute("list",list); 
    Integer oldType = (Integer) request.getAttribute("oldType");
	Integer oldStatus = (Integer) request.getAttribute("oldStatus");
	
	LocationService dao = new LocationService();
	List<LocationVO> townList = dao.getAll();
	pageContext.setAttribute("townList", townList);
%>
<jsp:useBean id="locSvc" scope="page" class="com.tao.location.model.LocationService" />

<jsp:include page="/b/frag/b_header1.jsp"/>
<style>
#dataTables-example tbody tr:nth-child(odd) {
     background-color: #F5F5F5;
}
</style>
<title> ��ݷ|���޲z </title>

<jsp:include page="/b/frag/b_header2AndMenu.jsp"/>
<script src="<%=request.getContextPath() %>/f/res/js/jquery.js"></script>
	<script type="text/javascript">

		$(document).ready(function (){
			$("#chgPage").find("a").each(function(){
				var hrefUrl = $(this).attr("href");
				var urlAry = hrefUrl.split("?");
				var whichPageAry = urlAry[1].split("=");
				$(this).attr("href","#");
				$(this).click(function(){
					$("#whichPage").val(whichPageAry[1]);
					$("#form1").submit();
				});
			})
		})

</script>
	<div id="page-wrapper">
		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">�j�M�|��</h1>
			</div>
		<!-- /.col-lg-12 -->
		</div>
		<!-- /.row -->
		<div class="row">
			<div class="col-lg-12"  >
				<div class="panel panel-success" style="border-width:1px; border-color : #34B2E5;"> <!--  panel body color and settings, exclude header bar color -->
					<div class="panel-heading">
						<h4 style="font-family: �L�n������; margin: 0px"><b>�ƦX�j�M</b></h4>
					</div>
				<div class="panel-body">							
					<form id="form1" role="form" METHOD="post" ACTION="<%=request.getContextPath()%>/back/memberC" name="form1">
	
						<div class="col-sm-3">
							<div class="form-group">
								<label>�|������</label>
								<select class="form-control" name="type">
									<option value= "999"
										<c:if test="${oldType == 999}">
										selected
										</c:if>
									>�Ҧ��|�� </option>
									<option value="0"
										<c:if test="${oldType == 0}">
										selected
										</c:if>
									>�@��|��</option>
									<option value="1"
										<c:if test="${oldType == 1}">
										selected
										</c:if>
									>���a�|��</option>
								</select>
							</div>
						</div>
						<div class="col-sm-3">
							<div class="form-group">
								<label>�|�����A</label>
								<select class="form-control" name="status">
									<option value= "999" 
										<c:if test="${oldStatus == 999}">
										selected
										</c:if>
									>�Ҧ����A </option>
									<option value="0"
										<c:if test="${oldStatus == 0}">
										selected
										</c:if>
									>�f�֤�</option>
									<option value="1"
										<c:if test="${oldStatus == 1}">
										selected
										</c:if>
									>���`</option>
									<option value="2"
										<c:if test="${oldStatus == 2}">
										selected
										</c:if>
									>���v</option> 
								</select>
							</div>
						</div>
						
						<div class="col-sm-3">
							<div class="form-group">
								<label>�ϰ�</label>
								<select id="countySelectList" class="form-control" name="locno">	
								<option value="999">�����a��</option>					         
						         	<c:forEach var="town" items="${townList}">
					                   <option value="${town.locno}">[${town.county} - ${town.town}]</option>
					                </c:forEach>
								</select>
							</div>
						</div>
						
						
						<div class="col-sm-3">
							<div class="form-group">
								<label>&nbsp;</label>
								<input type="hidden" name="action" value="listMemRS_ByCompositeQuery">
								<input type="hidden" id="whichPage" name="whichPage" value="1">
								<button class="form-control btn btn-success"><i class="fa fa-search"></i> �j�M</button>
							</div>
						</div>	
						
						<div class="col-sm-3">
							<div class="form-group">
								<label>�|���b��</label>
								<input class="form-control" name="memid">
							</div>
						</div>
												
						<div class="col-sm-3">
							<div class="form-group">
								<label>�|���s��</label>
								<input class="form-control" name="memno">
							</div>
						</div>

						
						<div class="col-sm-3">
							<div class="form-group">
								<label>�|��email</label>
								<input class="form-control" name="email">
							</div>
						</div>
											
					</form>
					
					<%-- 
					<form role="form" METHOD="post" ACTION="<%=request.getContextPath()%>/back/memberC" name="form1">
	
						<div class="col-sm-offset-2 col-sm-3">
							<div class="form-group">
								<label>�|���s��</label>
								<input class="form-control">
							</div>
						</div>
						<div class="col-sm-3">
							<div class="form-group">
								<label>�|���b��</label>
								<input class="form-control">
							</div>
						</div>

						<div class="col-sm-3">
							<div class="form-group">
								<label>&nbsp;</label>
								<input type="hidden" name="action" value="listMemRS_ByCompositeQuery">
								<button class="form-control btn btn-success"><i class="fa fa-search"></i> �j�M</button>
							</div>
						</div>							
					</form> --%>
				</div>
                      
				</div><!--/.panel .panel-info-->
			</div>
		</div>
	

		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default" style="border-width:1px; border-color : #34B2E5;">
					<div class="panel-heading">
						�j�M���G
	                   </div>
	                   <!-- /.panel-heading -->
	                   <div class="panel-body">
	                   	<div class="table-responsive">
							<table class="table table-hover" id="dataTables-example" >
								<thead>
									<tr>
										<th>�|���s��</th>
										<th>�|���b��</th>
										<%-- <th>MEMPW</th> --%>
										<th>�m�W</th>
										<%-- <th>�����Ҹ��X�βνs</th> --%>
										<th>�ʧO</th>
										<th>�a��</th>
										<%-- <th>�a�}</th> --%>
										<th>�q��</th>
										<th>EMAIL</th>
										<th>���A</th>
										<th>����</th>
										<th>�b���Ӹ`�P�ק�</th>
									</tr>
								</thead>
								<tbody>	
								<%@ include file="/member/back/page1.file" %>
									
									<%if(request.getParameter("memno") != null && request.getParameter("memno").matches("\\d{4,}")){ pageContext.setAttribute("parammemno", new Integer(request.getParameter("memno"))); }%>
									<c:forEach var="dao" items="${list}"  begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>" >
									<tr align='center' valign='middle' style=${(dao.memno == parammemno) ? 'background-color:orange !important;':'background-color:;'}><!--�N�ק諸���@���[�J����Ӥw-->
										<td>${dao.memno}</td>
										<td>${dao.memid}</td>
										<%-- <td>${dao.mempw}</td> --%>
										<td>${dao.lname} ${dao.fname}</td>
										<%-- <td>${dao.idnum}</td> --%>
										<td>
											<c:if test="${dao.gender == 'F'}">
												<font color="#EC3AC8"><i class="fa fa-female"></font></i> �k��
											</c:if>
											<c:if test="${dao.gender == 'M'}">
												<font color="#3A81EC"><i class="fa fa-male"></font></i> �k��
											</c:if>
										</td>
										
										<!--<td>${dao.gender}</td>
										 <td>${dao.locno}</td> -->
										<td><c:forEach var="locVO" items="${locSvc.all}">
							                    <c:if test="${dao.locno==locVO.locno}">
								                    ${dao.locno}�i${locVO.county} - ${locVO.town}�j
							                    </c:if>
							                </c:forEach>
										</td>
										
										
										<td>${dao.tel}</td>
										<td>${dao.email}</td>
										
										<td>${dao.status}
											<c:if test="${dao.status == '0'}">
												- �f�֤�
											</c:if>
											<c:if test="${dao.status == '1'}">
												- ���`
											</c:if>
											<c:if test="${dao.status == '2'}">
												- ���v��
											</c:if>
										</td>
										
										<%-- <td>${dao.type}</td> --%>
										<td>${dao.type}
											<c:if test="${dao.type == '0'}">
												- �@��|��
											</c:if>
											<c:if test="${dao.type == '1'}">
												- ���a�|��
											</c:if>
										</td>												
										
										<td>
										  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/memberC">
										     <!-- <input type="submit" value=""> -->
										     <input	id="updatebt_${dao.memno}"	class="btn btn-outline btn-success" type="submit" value="�Ӹ`�P�ק�">
										     <input type="hidden" name="memno" value="${dao.memno}">
										      <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--�e�X�����������|��Controller-->
										    <input type="hidden" name="whichPage"	value="<%=whichPage%>">                <!--�e�X��e�O�ĴX����Controller-->
										     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<%@ include file="/member/back/page2.file" %>
	
	                             
						</div>
					</div>
					<!-- /.panel-body -->
				</div>
				<!-- /.panel -->
			</div>
			<!-- /.col-lg-12 -->
		</div>
		<!-- /.row -->
		
	
		           
	</div>
	<!-- /#page-wrapper -->

 <jsp:include page="/b/frag/b_footer1.jsp"/>
 <jsp:include page="/b/frag/b_footer2.jsp"/>
