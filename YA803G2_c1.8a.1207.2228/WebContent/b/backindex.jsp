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

	<title>首頁 - 後端管理系統</title>
	<style>
		#page-wrapper {font-family:微軟正黑體;}
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
                    <h1 class="page-header">資訊面板</h1>
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
										<div>商店上架請求待處理</div>
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
										<div>商店檢舉待處理</div>
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
										<div>合購檢舉待處理</div>
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
										<div>店家會員申請待處理</div>
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
										<div>收款確認待處理</div>
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
									<div>兌換現金審核待處理</div>
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
							<h4>帳號資訊&nbsp;-&nbsp;${bAccount.acc}&nbsp;(${bAccount.nick})</h4>
						</div>
						<div class="panel-body" >
							<div class="col-lg-4" >
								<div class="panel panel-info" >
									<div class="panel-heading">
									<h4>管理權限</h4>
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
									<i > 訊息區</i>
								</div>
							<!-------更改密碼 -------->
								<div class="panel panel-warning" >
									<div class="panel-heading">
										更改密碼
									</div>
									<div class="panel-body">	
										
									
										<div class="form-group col-lg-5">
											<label class="control-label">新密碼：</label>
											<input class="form-control" name="newacppw" type="password">
										</div>
										<div class="form-group col-lg-5">
											<label class="control-label">確認密碼：</label>
											<input class="form-control" name="checkaccpw" type="password">
										</div>
										<div class="form-group col-lg-2">
											<label class="control-label">&nbsp;</label>
											<button id="update_accpw" class="btn btn-success btn-outline"><i class="fa fa-check"></i></button>
										</div>
									
									</div>
								</div>
								<!-- /.panel -->
								<!-------更改信箱-------->
								<div class="panel panel-success" >
									<div class="panel-heading">
										更改信箱
									</div>
									<div class="panel-body">							
										<div class="form-group col-lg-10">
											<label class="control-label" for="pwd">新E-mail：</label>
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
					$(".well").html("<span class='text-danger'>請輸入新密碼</span>");
					break;
				case "format":
					$(".well").html("<span class='text-danger'>請輸入英數混合格式 </span>");
					break;
				case "pwlong":
					$(".well").html("<span class='text-danger'>密碼過長</span>");
					break;
				case "check":
					$(".well").html("<span class='text-danger'>請再輸入一次新密碼進行確認</span> ");
					break;
				case "different":
					$(".well").html("<span class='text-danger'>確認密碼不一致，請再確認您的密碼</span> ");
					break;
				case "ok":
					$(".well").html("<span class='text-success'>密碼修改成功</span>");
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
					$(".well").html("<span class='text-success'>Email修改完成</span>");
					break;
				case "format":
					$(".well").html("<span class='text-danger'>Email格式錯誤</span>");
					break;
				case "null":
					$(".well").html("<span class='text-danger'>請輸入欲修改的 Email</span>");
					break;	
				default:
					break;
				}
			});
			
		});
	});


</script>

<jsp:include page="/b/frag/b_footer2.jsp"/>
    