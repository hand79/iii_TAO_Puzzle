<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tao.member.model.*"%>
<%@ page import="java.util.*" %>

<jsp:include page="/f/frag/f_header1.jsp"/>
<title> �|���n�J </title>
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>
	
	<section id="form" style="margin-top: 80px;"><!--form-->
		<div class="container">
			<div class="row">
				<div class="col-sm-3"></div>
				<div class="col-sm-6" style="height: 460px">
					<h2 class="text-center title" style="font-size: 2em;">�n�J�|��</h2>
					<div class="error handling">
					<%-- ���~��C --%>
						<c:if test="${not empty errorMsgs}">
							<font color='red'>�Эץ��H�U���~:
							<ul>
								<c:forEach var="message" items="${errorMsgs}">
									<li>${message}</li>
								</c:forEach>
							</ul>
							</font>
						</c:if>
					</div>	
				
					<div class="login-form"><!--login form-->
						<form ACTION="<%=request.getContextPath()%>/LoginHandler" METHOD="POST">
							<input type="text" NAME="account" placeholder="�b���W��" />
							<input type="PASSWORD" NAME="password" placeholder="�K�X" />
							<div style="margin-top: 30px;"><span ><a href="<%=request.getContextPath() %>/member/front/mainMemberSignup.jsp" >���U�s�b��</a></span><button type="submit" class="btn btn-default pull-right" style="margin-top: 0px;">�n�J</button></div>
						</form>
					</div><!--/login form-->
					
				</div>
				<div class="col-sm-3"></div>
			</div>
		</div>
	</section><!--/form-->
<jsp:include page="/f/frag/f_footer1.jsp"/>
</body>
</html>
