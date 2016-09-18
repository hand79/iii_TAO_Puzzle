<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.util.*" %>
<%
   String contextPath = request.getContextPath();
   pageContext.setAttribute("contextPath", contextPath);
   
	boolean[] permissionFlagArray = (boolean[]) session
			.getAttribute("permissionFlagArray");
%>

<%-- INCLUDE HEADER1 --%>
<jsp:include page="/b/frag/b_header1.jsp"/>
<%-- INCLUDE HEADER1 --%>

	<title>���� - ��ݺ޲z�t��</title>
	<style>
		#page-wrapper {font-family:�L�n������;}
		.fa-ul li{
			margin-bottom: 8px;
		}
	
	</style>

<%-- INCLUDE HEADER2 --%>
<jsp:include page="/b/frag/b_header2AndMenu.jsp"/>
<%-- INCLUDE HEADER2 --%>
<jsp:useBean id="perListSvc" class="com.tao.acc.model.PerListService" />
<jsp:useBean id="bAccount" scope="session" class="com.tao.acc.model.AccountVO"/>
<jsp:useBean id="permissionSvc" class="com.tao.acc.model.PermissionService"/>

<c:set var="perlist" scope="session" value="${sessionScope.bAccountPerlist}"/>

       
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">��T���O</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
			<div class="row">
				<div class="col-lg-6">
				<c:if test="<%=permissionFlagArray[3 - 1]%>">
				<jsp:useBean id="shopService" class="com.tao.shop.model.ShopService" />
				<c:set var="shopCount" value="${shopService.getWaitApproveShop().size()}"  />
					<div class="col-lg-6">
						<div class="panel panel-green">
							<div class="panel-heading">
								<div class="row">
									<div class="col-xs-3">
										<i class="fa fa-gift fa-5x"></i>
									</div>
									<div class="col-xs-9 text-right">
										<div class="huge">${shopCount}</div>
										<div>�ө��W�[�ШD�ݳB�z</div>
									</div>
								</div>
							</div>
							<a href="${contextPath}/back/shop/shop.do?action=approve">
								<div class="panel-footer">
									<span class="pull-left">View Details</span>
									<span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
									<div class="clearfix"></div>
								</div>
							</a>
						</div>
					</div>
					
					<!-- /.col-lg-6-->
					<jsp:useBean id="shopRepService" class="com.tao.shopRep.model.ShopRepService"/>
					<c:set var="shopRepCount" value="${shopRepService.all.size() }" />
					<div class="col-lg-6">
						<div class="panel panel-red">
							<div class="panel-heading">
								<div class="row">
									<div class="col-xs-3">
										<i class="fa fa-gift fa-5x"></i>
									</div>
									<div class="col-xs-9 text-right">
										<div class="huge">${shopRepCount}</div>
										<div>�ө����|�ݳB�z</div>
									</div>
								</div>
							</div>
							<a href="${contextPath}/back/shopRep/shopRep.do?action=default">
								<div class="panel-footer">
									<span class="pull-left">View Details</span>
									<span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
									<div class="clearfix"></div>
								</div>
							</a>
						</div>
					</div>
					</c:if>
					<!-- /.col-lg-6-->
					<c:if test="<%=permissionFlagArray[7 - 1]%>">
					<jsp:useBean id="caseRepService" class="com.tao.caseRep.model.CaseRepService"/>
					<c:set var="caseRepCount" value="${caseRepService.all.size()}"/>
					<div class="col-lg-6">
						<div class="panel panel-red">
							<div class="panel-heading">
								<div class="row">
									<div class="col-xs-3">
										<i class="fa fa-users fa-5x"></i>
									</div>
									<div class="col-xs-9 text-right">
										<div class="huge">${caseRepCount}</div>
										<div>�X�����|�ݳB�z</div>
									</div>
								</div>
							</div>
							<a href="${contextPath}/back/caseRep/caseRep.do?action=default">
								<div class="panel-footer">
									<span class="pull-left">View Details</span>
									<span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
									<div class="clearfix"></div>
								</div>
							</a>
						</div>
					</div>
					</c:if>
					<!-- /.col-lg-6-->
					<c:if test="<%=permissionFlagArray[2 - 1]%>">
					<jsp:useBean id="memberSvc" class="com.tao.member.model.MemberService"/>
					<c:set var="memberCount"  value="${memberSvc.getAllPendingShopMember().size() }"/>
					<div class="col-lg-6">
						<div class="panel panel-green">
							<div class="panel-heading">
								<div class="row">
									<div class="col-xs-3">
										<i class="fa fa-user fa-5x"></i>
									</div>
									<div class="col-xs-9 text-right">
										<div class="huge">${memberCount}</div>
										<div>���a�|���ӽЫݳB�z</div>
									</div>
								</div>
							</div>
							<a href="${contextPath}/member/back/backMemberManage.jsp">
								<div class="panel-footer">
									<span class="pull-left">View Details</span>
									<span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
									<div class="clearfix"></div>
								</div>
							</a>
						</div>
					</div>
					</c:if>
					<!-- /.col-lg-6-->
					<c:if test="<%=permissionFlagArray[5 - 1]%>">
					
					<jsp:useBean id="dpsOrdService" class="com.tao.dpsOrd.model.DpsOrdService" />
					<%	Map<String, String[]> dpsOrd = new HashMap<String, String[]>();
						String[] strings={"WAITING"};
						dpsOrd.put("ordsts",strings);
						request.setAttribute("dpsOrd",dpsOrd);
					%>
					<c:set var="dpsOrdCount" value="${dpsOrdService.getAll(dpsOrd).size()}" />
				
					<div class="col-lg-6">
						<div class="panel panel-primary">
							<div class="panel-heading">
								<div class="row">
									<div class="col-xs-3">
										<i class="fa fa-sign-in fa-5x"></i>
									</div>
									<div class="col-xs-9 text-right">
										<div class="huge">${dpsOrdCount}</div>
										<div>���ڽT�{�ݳB�z</div>
									</div>
								</div>
							</div>
							<a href="${contextPath}/back/dpsOrd/dpsOrd.do?action=default">
								<div class="panel-footer">
									<span class="pull-left">View Details</span>
									<span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
									<div class="clearfix"></div>
								</div>
							</a>
						</div>
					</div>
					
					<!-- /.col-lg-6-->
					<jsp:useBean id="wtdReqService" class="com.tao.wtdReq.model.WtdReqService" />
					<%	Map<String, String[]> wtdReq = new HashMap<String, String[]>();
						String[] whereArg={"WAITING"};
						wtdReq.put("reqsts",whereArg);
						request.setAttribute("wtdReq",wtdReq);
					%>
					<c:set var="wtdReqCount" value="${wtdReqService.getAll(wtdReq).size()}" />
					<div class="col-lg-6">
						<div class="panel panel-yellow">
							<div class="panel-heading">
								<div class="row">
									<div class="col-xs-3">
										<i class="fa fa-usd fa-5x"></i>
									</div>
									<div class="col-xs-9 text-right">
										<div class="huge">${wtdReqCount}</div>
									<div>�I���{���f�֫ݳB�z</div>
									</div>
								</div>
							</div>
							<a href="${contextPath}/back/wtdReq/wtdReq.do?action=default">
								<div class="panel-footer">
									<span class="pull-left">View Details</span>
									<span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
									<div class="clearfix"></div>
								</div>
							</a>
						</div>
					</div>
					</c:if>
					<!-- /.col-lg-6-->
				</div>
				<!-- /.col-lg-6-->
					
				<div class="col-lg-6">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<h4>�b����T&nbsp;-&nbsp;${bAccount.acc}&nbsp;(${bAccount.nick})</h4>
						</div>
						<div class="panel-body" >
							<div class="col-lg-4" >
								<div class="panel panel-info" >
									<div class="panel-heading">
									<h4>�޲z�v��</h4>
									</div>
									<div class="panel-body" style="height: 332px;">
									<ul  class="fa-ul" style="font-size: 1.2em;">
										<c:forEach var="permissionVO"	items="${permissionSvc.all}">
																
										<li><span class="text-primary"><i class="fa-li fa fa<c:forEach var="perlistvVO" items="${perlist}"><c:if test="${perlistvVO.perno==permissionVO.perno }">-check</c:if></c:forEach>-square-o fa-lg" ></i></span> ${permissionVO.perdesc}</li>
										
										</c:forEach>	
					
									</ul>
									</div>
								</div>
							</div>
							<!-- /.col-lg-4-->
							<div class="col-lg-8">
								<div class="well text-center">
									<i > �T����</i>
								</div>
							<!-------���K�X -------->
								<div class="panel panel-warning" >
									<div class="panel-heading">
										���K�X
									</div>
									<div class="panel-body">	
										
									
										<div class="form-group col-lg-5">
											<label class="control-label">�s�K�X�G</label>
											<input class="form-control" name="newacppw" type="password">
										</div>
										<div class="form-group col-lg-5">
											<label class="control-label">�T�{�K�X�G</label>
											<input class="form-control" name="checkaccpw" type="password">
										</div>
										<div class="form-group col-lg-2">
											<label class="control-label">&nbsp;</label>
											<button id="update_accpw" class="btn btn-success btn-outline"><i class="fa fa-check"></i></button>
										</div>
									
									</div>
								</div>
								<!-- /.panel -->
								<!-------���H�c-------->
								<div class="panel panel-success" >
									<div class="panel-heading">
										���H�c
									</div>
									<div class="panel-body">							
										<div class="form-group col-lg-10">
											<label class="control-label" for="pwd">�sE-mail�G</label>
											<input class="form-control" type="text" name="email" id="pwd">
										</div>
										<div class="form-group col-lg-2">
											<label class="control-label">&nbsp;</label>
											<button id="update_email" class="btn btn-success btn-outline"><i class="fa fa-check"></i></button>
										</div>
									</div>
								</div>
								
								
								<!-- /.panel -->
							</form>
							</div>
							<!-- /.col-lg-8-->
						</div>
						<!-- /.panel-body-->	
					</div>
					<!-- /.panel-->
				</div>
				<!-- /.col-lg-6-->
			</div>
            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->
    </div>
<jsp:include page="/b/frag/b_footer1.jsp"/>

<script>
	$(document).ready(function(){
					
		$("#update_accpw").click(function(){
			var newacppw=$("input[name='newacppw']").val();
			var checkaccpw=$("input[name='checkaccpw']").val();
			
			$.post("<%=request.getContextPath()%>/back/Account.do",{action:"updatePw",'newaccpw':newacppw,'checkaccpw':checkaccpw},function(data){
				switch (data) {
				case "new":
					$(".well").html("<span class='text-danger'>�п�J�s�K�X</span>");
					break;
				case "format":
					$(".well").html("<span class='text-danger'>�п�J�^�ƲV�X�榡 </span>");
					break;
				case "pwlong":
					$(".well").html("<span class='text-danger'>�K�X�L��</span>");
					break;
				case "check":
					$(".well").html("<span class='text-danger'>�ЦA��J�@���s�K�X�i��T�{</span> ");
					break;
				case "different":
					$(".well").html("<span class='text-danger'>�T�{�K�X���@�P�A�ЦA�T�{�z���K�X</span> ");
					break;
				case "ok":
					$(".well").html("<span class='text-success'>�K�X�ק令�\</span>");
					break;	
				default:
					break;
				}
			});
		});
		
		
		$("#update_email").click(function(){
			var email=$("input[name='email']").val();
			
			$.post("<%=request.getContextPath()%>/back/Account.do",{action:"updateEmail",'email':email },function(data){
				
				switch (data) {
				case "ok":
					$(".well").html("<span class='text-success'>Email�ק粒��</span>");
					break;
				case "format":
					$(".well").html("<span class='text-danger'>Email�榡���~</span>");
					break;
				case "null":
					$(".well").html("<span class='text-danger'>�п�J���ק諸 Email</span>");
					break;	
				default:
					break;
				}
			});
			
		});
	});


</script>

<jsp:include page="/b/frag/b_footer2.jsp"/>
    