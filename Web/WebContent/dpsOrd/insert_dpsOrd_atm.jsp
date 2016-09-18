<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%--**** LOGIN REDIRECT ****--%>
<%@include file="/f/frag/f_login_redirect.file"%>
<%--**** LOGIN REDIRECT ****--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tao.dpsOrd.model.*"%>
<%
	DpsOrdVO dpsOrdVO = (DpsOrdVO) request.getAttribute("dpsOrdVO");
%>
<jsp:useBean id="memSvc" scope="page"
	class="com.tao.member.model.MemberService" />
<c:set value="${memSvc.findByPrimaryKeyNoPic(CurrentUser.memno)}"
	var="CurrentUser" scope="session" />
<%
	String resPath = request.getContextPath() + "/f/res";
	pageContext.setAttribute("resPath", resPath);

	String contextPath = request.getContextPath() + "/";
	pageContext.setAttribute("contextPath", contextPath);
%>
<jsp:include page="/f/frag/f_header1.jsp" flush="true" />
<title>儲值 | 饕飽拼圖</title>
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp" />


<section>
	<div class="container">
		<div class="row">
			<jsp:include page="/f/frag/f_memCenterMenu.jsp" />
			<div class="col-lg-2"></div>
			<div class="col-lg-4">
				<div class="panel panel-default">
					<div class="panel-heading">ATM儲值</div>
					<div class="panel-body">
						<div class="row show-grid">
							<div class="col-lg-12">
								<form ACTION="dpsOrd.do" id="main-contact-form"
									class="contact-form row" name="contact-form" method="post">
									<div class="form-group col-lg-8">
										<c:set var="memlist" value="${memSvc.all}" />
										<label>目前帳戶餘額</label>
										<code>
											<i class="fa fa-usd"></i>
											<c:forEach var="memVO" items="${memlist}">
												<c:if test="${CurrentUser.memno == memVO.memno}">${memVO.money}</c:if>
											</c:forEach>
										</code>
										<label>預扣金額</label>
										<code>
											<i class="fa fa-usd"></i>
											<c:forEach var="memVO" items="${memlist}">
												<c:if test="${CurrentUser.memno == memVO.memno}">${memVO.withhold}</c:if>
											</c:forEach>
										</code>
										<input type="hidden" class="form-control" name="memno"
											value="${CurrentUser.memno}" />
									</div>
									<div class="form-group col-lg-6">
										<p>請選擇金額</p>
										<select class="form-control" name="dpsamnt"
											required="required">
											<c:forEach var="amnt" begin="200" end="1000" step="200">
												<option value="${amnt}">${amnt}</option>
											</c:forEach>
											<c:forEach var="amnt" begin="1500" end="5000" step="500">
												<option value="${amnt}">${amnt}</option>
											</c:forEach>
										</select>
									</div>
									<div class="form-group col-lg-10">
										<p>轉帳帳戶號碼</p>
										<input type="text" class="form-control" name="atmac"
											id="atmacc" value="" readonly>

									</div>

									<div class="form-group col-lg-12">
										<button type="reset" class="btn btn-primary">Reset</button>
										<input type="hidden" name="action" value="insert_A"> <input
											type="hidden" name="ordsts" value="WAITING"> <input
											type="hidden" name="dpshow" value="ATM"><input
											type="submit" name="submit"
											class="btn btn-primary pull-right" value="Submit">
									</div>
								</form>
							</div>
						</div>
						<!-- /.row show-grid -->
					</div>
					<!-- /.panel-body -->
				</div>
				<!-- /.panel panel-default -->
			</div>
			<!-- /.col-lg-4 -->
			<div class="col-lg-2"></div>
		</div>
	</div>
</section>
<jsp:include page="/f/frag/f_footer1.jsp" />
<script type="text/javascript">
	$(function() {
		var max = 9;
		var min = 0;
		var atmac = "";
		for (var i = 0; i <= 12; i++) {
			atmac += Math.floor(Math.random() * (max - min + 1));
		}
		$("#atmacc").val(atmac);
	});
</script>
<jsp:include page="/f/frag/f_footer2.jsp" />