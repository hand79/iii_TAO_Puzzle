<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="/b/frag/login_redirect.file" %>
<%@ page import="java.util.*"%>
<%@ page import="com.tao.member.model.*"%>
<%@ page import="com.tao.location.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

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
<title> 後端會員管理 </title>

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
				<h1 class="page-header">搜尋會員</h1>
			</div>
		<!-- /.col-lg-12 -->
		</div>
		<!-- /.row -->
		<div class="row">
			<div class="col-lg-12"  >
				<div class="panel panel-success" style="border-width:1px; border-color : #34B2E5;"> <!--  panel body color and settings, exclude header bar color -->
					<div class="panel-heading">
						<h4 style="font-family: 微軟正黑體; margin: 0px"><b>複合搜尋</b></h4>
					</div>
				<div class="panel-body">							
					<form id="form1" role="form" METHOD="post" ACTION="<%=request.getContextPath()%>/back/memberC" name="form1">
	
						<div class="col-sm-3">
							<div class="form-group">
								<label>會員類型</label>
								<select class="form-control" name="type">
									<option value= "999"
										<c:if test="${oldType == 999}">
										selected
										</c:if>
									>所有會員 </option>
									<option value="0"
										<c:if test="${oldType == 0}">
										selected
										</c:if>
									>一般會員</option>
									<option value="1"
										<c:if test="${oldType == 1}">
										selected
										</c:if>
									>店家會員</option>
								</select>
							</div>
						</div>
						<div class="col-sm-3">
							<div class="form-group">
								<label>會員狀態</label>
								<select class="form-control" name="status">
									<option value= "999" 
										<c:if test="${oldStatus == 999}">
										selected
										</c:if>
									>所有狀態 </option>
									<option value="0"
										<c:if test="${oldStatus == 0}">
										selected
										</c:if>
									>審核中</option>
									<option value="1"
										<c:if test="${oldStatus == 1}">
										selected
										</c:if>
									>正常</option>
									<option value="2"
										<c:if test="${oldStatus == 2}">
										selected
										</c:if>
									>停權</option> 
								</select>
							</div>
						</div>
						
						<div class="col-sm-3">
							<div class="form-group">
								<label>區域</label>
								<select id="countySelectList" class="form-control" name="locno">	
								<option value="999">全部地區</option>					         
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
								<button class="form-control btn btn-success"><i class="fa fa-search"></i> 搜尋</button>
							</div>
						</div>	
						
						<div class="col-sm-3">
							<div class="form-group">
								<label>會員帳號</label>
								<input class="form-control" name="memid">
							</div>
						</div>
												
						<div class="col-sm-3">
							<div class="form-group">
								<label>會員編號</label>
								<input class="form-control" name="memno">
							</div>
						</div>

						
						<div class="col-sm-3">
							<div class="form-group">
								<label>會員email</label>
								<input class="form-control" name="email">
							</div>
						</div>
											
					</form>
					
					<%-- 
					<form role="form" METHOD="post" ACTION="<%=request.getContextPath()%>/back/memberC" name="form1">
	
						<div class="col-sm-offset-2 col-sm-3">
							<div class="form-group">
								<label>會員編號</label>
								<input class="form-control">
							</div>
						</div>
						<div class="col-sm-3">
							<div class="form-group">
								<label>會員帳號</label>
								<input class="form-control">
							</div>
						</div>

						<div class="col-sm-3">
							<div class="form-group">
								<label>&nbsp;</label>
								<input type="hidden" name="action" value="listMemRS_ByCompositeQuery">
								<button class="form-control btn btn-success"><i class="fa fa-search"></i> 搜尋</button>
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
						搜尋結果
	                   </div>
	                   <!-- /.panel-heading -->
	                   <div class="panel-body">
	                   	<div class="table-responsive">
							<table class="table table-hover" id="dataTables-example" >
								<thead>
									<tr>
										<th>會員編號</th>
										<th>會員帳號</th>
										<%-- <th>MEMPW</th> --%>
										<th>姓名</th>
										<%-- <th>身分證號碼或統編</th> --%>
										<th>性別</th>
										<th>地區</th>
										<%-- <th>地址</th> --%>
										<th>電話</th>
										<th>EMAIL</th>
										<th>狀態</th>
										<th>類型</th>
										<th>帳號細節與修改</th>
									</tr>
								</thead>
								<tbody>	
								<%@ include file="/member/back/page1.file" %>
									
									<%if(request.getParameter("memno") != null && request.getParameter("memno").matches("\\d{4,}")){ pageContext.setAttribute("parammemno", new Integer(request.getParameter("memno"))); }%>
									<c:forEach var="dao" items="${list}"  begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>" >
									<tr align='center' valign='middle' style=${(dao.memno == parammemno) ? 'background-color:orange !important;':'background-color:;'}><!--將修改的那一筆加入對比色而已-->
										<td>${dao.memno}</td>
										<td>${dao.memid}</td>
										<%-- <td>${dao.mempw}</td> --%>
										<td>${dao.lname} ${dao.fname}</td>
										<%-- <td>${dao.idnum}</td> --%>
										<td>
											<c:if test="${dao.gender == 'F'}">
												<font color="#EC3AC8"><i class="fa fa-female"></font></i> 女性
											</c:if>
											<c:if test="${dao.gender == 'M'}">
												<font color="#3A81EC"><i class="fa fa-male"></font></i> 男性
											</c:if>
										</td>
										
										<!--<td>${dao.gender}</td>
										 <td>${dao.locno}</td> -->
										<td><c:forEach var="locVO" items="${locSvc.all}">
							                    <c:if test="${dao.locno==locVO.locno}">
								                    ${dao.locno}【${locVO.county} - ${locVO.town}】
							                    </c:if>
							                </c:forEach>
										</td>
										
										
										<td>${dao.tel}</td>
										<td>${dao.email}</td>
										
										<td>${dao.status}
											<c:if test="${dao.status == '0'}">
												- 審核中
											</c:if>
											<c:if test="${dao.status == '1'}">
												- 正常
											</c:if>
											<c:if test="${dao.status == '2'}">
												- 停權中
											</c:if>
										</td>
										
										<%-- <td>${dao.type}</td> --%>
										<td>${dao.type}
											<c:if test="${dao.type == '0'}">
												- 一般會員
											</c:if>
											<c:if test="${dao.type == '1'}">
												- 店家會員
											</c:if>
										</td>												
										
										<td>
										  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/memberC">
										     <!-- <input type="submit" value=""> -->
										     <input	id="updatebt_${dao.memno}"	class="btn btn-outline btn-success" type="submit" value="細節與修改">
										     <input type="hidden" name="memno" value="${dao.memno}">
										      <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
										    <input type="hidden" name="whichPage"	value="<%=whichPage%>">                <!--送出當前是第幾頁給Controller-->
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
