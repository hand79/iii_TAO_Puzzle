<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%--**** LOGIN REDIRECT ****--%>
<%@include file="/f/frag/f_login_redirect.file"%>
<%--**** LOGIN REDIRECT ****--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tao.wtdReq.model.*"%>
<%
	WtdReqVO wtdReqVO = (WtdReqVO) request.getAttribute("wtdReqVO");
%>
<jsp:useBean id="memSvc" scope="page"
	class="com.tao.member.model.MemberService" />
<c:set value="${memSvc.findByPrimaryKeyNoPic(CurrentUser.memno)}"
	var="CurrentUser" scope="session" />
<%
	String pageDpsOrd = request.getContextPath() + "/dpsOrd";
	pageContext.setAttribute("pageDpsOrd", pageDpsOrd);

	String pageWtdReq = request.getContextPath() + "/wtdReq";
	pageContext.setAttribute("pageWtdReq", pageWtdReq);
%>
<jsp:include page="/f/frag/f_header1.jsp" flush="true" />
<title>電子錢包 | 饕飽拼圖</title>
<style>
.panel-heading,h2.title {
	/*font-family: 微軟正黑體;*/
	font-weight: bolder;
}

.btn.btn-primary {
	margin: 0px !important;
	margin-top: 20px !important;
	border-radius: 4px;
	font-size: 1.1em;
	font-family: 微軟正黑體;
}
</style>
<%
	request.setAttribute("showBreadcrumb", new Object()); 
java.util.LinkedHashMap<String, String> map = new java.util.LinkedHashMap<String, String>();
map.put("會員中心","");
request.setAttribute("breadcrumbMap", map);
%>
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp" />
<section>
	<div class="container">
		<div class="row">
			<%@ include file="/f/frag/f_memCenterMenu.jsp"%>
			<div class="col-lg-9">
				<h2 class="title text-center">電子錢包</h2>
				<c:if test="${not empty errorMsgs}">
					<font color='red'>請修正以下錯誤:
						<ul>
							<c:forEach var="message" items="${errorMsgs}">
								<li>${message}</li>
							</c:forEach>
						</ul>
					</font>
				</c:if>
			</div>
			<div class="col-lg-9">
			<c:set var="memlist" value="${memSvc.all}" />
				<h3 class="text-center">
				<label>目前帳戶餘額</label>
					<code>
						<i class="fa fa-usd"></i>
						<c:forEach var="memVO" items="${memlist}">
							<c:if test="${CurrentUser.memno == memVO.memno}">${memVO.money}</c:if>
						</c:forEach>
					</code>
					</h3 >
					<h4 class="text-center">
					<label>預扣金額</label>
					<code>
					<i class="fa fa-usd"></i>
					<c:forEach var="memVO" items="${memlist}">
							<c:if test="${CurrentUser.memno == memVO.memno}">${memVO.withhold}</c:if>
						</c:forEach>
					</code>
				</h4>
			</div>
			<div class="col-lg-9"><br></div>
			<!-- /.col-lg-12 -->
			<div class="col-lg-3">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h5 id="grid-offsetting">查詢</h5>
					</div>
					<div class="panel-body">
						<div class="row show-grid">
							<div class="col-sm-12" style="text-align: center;">
								<form id="main-contact-form" class="contact-form row"
									name="contact-form" method="post"
									action="<%=request.getContextPath()%>/dpsOrd/dpsOrd.do">
									<!-- 									<div class="col-sm-8"> -->
									<!-- 										<p>請輸入會員編號</p> -->
									<input type="hidden" name="memno" class="form-control"
										value="${CurrentUser.memno}" required="required">
									<!-- 									</div> -->
									<div class="col-sm-12">
										<input type="hidden" name="action"
											value="listDpsOrd_ByCompositeQuery"> <input
											type="submit" class="btn btn-primary btn-sm" value="儲值紀錄"
											style="width: 60%">
									</div>
								</form>
							</div>
							<div class="col-sm-12" style="text-align: center;">
								<form id="main-contact-form" class="contact-form row"
									name="contact-form" method="post"
									action="<%=request.getContextPath()%>/wtdReq/wtdReq.do">
									<!-- 									<div class="col-sm-8"> -->
									<!-- 										<p>請輸入會員編號</p> -->
									<input type="hidden" name="memno" class="form-control"
										value="${CurrentUser.memno}" required="required">
									<!-- 									</div> -->
									<div class="col-sm-12">
										<input type="hidden" name="action"
											value="listWtdReq_ByCompositeQuery"> <input
											type="submit" class="btn btn-primary btn-sm" value="兌現紀錄"
											style="width: 60%">
									</div>
								</form>
							</div>
							<div class="col-sm-12" style="text-align: center;">
								<form id="main-contact-form" class="contact-form row"
									name="contact-form" method="post"
									action="<%=request.getContextPath()%>/order/listOrder.do">
									<input type="hidden" name="memno" class="form-control"
										value="${CurrentUser.memno}">
									<div class="col-sm-12">
										<input type="hidden" name="action" value="listAllOrderByMemno">
										<input type="submit" class="btn btn-primary btn-sm"
											value="參團紀錄" style="width: 60%">
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-lg-3">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h5 id="grid-offsetting">儲值</h5>
					</div>
					<div class="panel-body">
						<div class="row show-grid">
							<div class="col-lg-12">
								<h4>請選擇儲值方式</h4>
								<div class="col-sm-6">
									<a class="btn btn-primary"
										href='<%=request.getContextPath()%>/dpsOrd/insert_dpsOrd_credit.jsp'>信用卡</a>
								</div>
								<div class="col-sm-6">
									<a class="btn btn-primary"
										href='<%=request.getContextPath()%>/dpsOrd/insert_dpsOrd_atm.jsp'>ATM轉帳</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div class="col-lg-3">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h5 id="grid-offsetting">申請兌現</h5>
					</div>
					<div class="panel-body">
						<div class="row show-grid">
							<div class="col-lg-12">
								<form id="main-contact-form" class="contact-form row"
									name="contact-form" METHOD="post"
									ACTION="<%=request.getContextPath()%>/wtdReq/updateMoney.do">
									<div class="form-group col-lg-12">

										<!-- 										<p>請輸入會員編號：</p> -->
										<input type="hidden" name="memno" value="${CurrentUser.memno}">

									</div>
									<c:forEach var="memVO" items="${memlist}">
										<c:if test="${CurrentUser.memno == memVO.memno}">
											<c:if test="${memVO.money < 1000}">
												<div class="form-group col-lg-12">
													<i>餘額不足1000元，無法使用此功能。</i>
												</div>
											</c:if>
											<c:if test="${memVO.money >= 1000}">
												<div class="form-group col-lg-10">
													<input type="text" name="wtdamnt" class="form-control"
														required="required" placeholder="請輸入金額">
												</div>
												<div class="form-group col-lg-12">
													<p>
														僅接受
														<code>
															<i class="fa fa-usd"></i>1000
														</code>
														以上的申請
													</p>
												</div>
												<div class="form-group col-lg-10">
													<input type="text" name="wtdac" class="form-control"
														required="required" placeholder="銀行帳戶">

												</div>
												<div class="form-group col-lg-12">
													<p>
														請填寫
														<code>本人</code>
														帳戶
													</p>
												</div>
												<div class="form-group col-lg-12">
													<input type="hidden" name="reqsts" value="WAITING">
													<input type="hidden" name="action"
														value="UpdateMoney_When_insert"> <input
														type="submit" name="submit"
														class="btn btn-primary pull-right" value="Submit">
												</div>
											</c:if>
										</c:if>
									</c:forEach>
								</form>
							</div>
						</div>
					</div>
					<!-- /.col-lg-3 -->
				</div>
			</div>
		</div>
	</div>
</section>
<jsp:include page="/f/frag/f_footer1.jsp" />

<jsp:include page="/f/frag/f_footer2.jsp" />