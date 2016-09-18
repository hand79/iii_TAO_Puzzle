<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%--**** LOGIN REDIRECT ****--%>
<%@include file="/f/frag/f_login_redirect.file" %>
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
			<jsp:include page="/f/frag/f_memCenterMenu.jsp"/>
			<div class="col-lg-2"></div>
			<div class="col-lg-4">
			<div class="panel panel-default">
			<div class="panel-heading">信用卡儲值</div>
					<div class="panel-body">
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
							value="${CurrentUser.memno}"/>
					</div>
					<div class="form-group col-lg-8">
					<h5 id="grid-offsetting">請選擇金額</h5>
						<select class="form-control" name="dpsamnt" required="required">
							<c:forEach var="amnt" begin="200" end="1000" step="200">
								<option value="${amnt}">${amnt}</option>
							</c:forEach>
							<c:forEach var="amnt" begin="1500" end="5000" step="500">
								<option value="${amnt}">${amnt}</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group col-lg-8">
						<p>請輸入信用卡卡號</p>
						<input type="text" class="form-control" name="creditnum" required="required">

					</div>
					<div class="form-group col-lg-8">
						<p>選擇卡別</p>
						<select class="form-control" name="credittype" required="required">
							<option>Master Card</option>
							<option>VISA</option>
							<option>JCB</option>
						</select>
					</div>

					<div class="form-group col-lg-8">
					<p>有效期限</p>
					<input type="month" class="form-control" name="limitmonth" required="required">
					</div>

					<div class="form-group col-lg-12">
						<button type="reset" class="btn btn-primary">Reset</button>
						<input type="hidden" name="action" value="insert_C">
						<input type="hidden" name="dpshow" value="CREDIT">  
						<input type="hidden" name="ordsts" value="WAITING"> 
						<input type="hidden" name="atmac" value="NONE"> 
						<input type="submit" name="submit" class="btn btn-primary pull-right"
							value="Submit">
					</div>
				</form>
				</div>
				</div>
			</div>
			<div class="col-lg-3"></div>
		</div>
	</div><!-- /.container -->
</section>
<jsp:include page="/f/frag/f_footer2.jsp" />