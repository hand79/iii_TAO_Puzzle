<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tao.member.model.*"%>
<%@ page import="com.tao.location.model.*"%>
<%@ page import="java.util.*" %>
<%
	MemberVO memberVO = (MemberVO) request.getAttribute("memberVO");
%>

<jsp:include page="/f/frag/f_header1.jsp"/>
<meta http-equiv="Refresh" content="5;url=<%=request.getContextPath()%>/">
<title> 註冊結果 </title>
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>
	
	<section style="margin-top: 100px;"><!--form-->
		<div class="container">
			<div class="row" style="height: 590px;">
				<div class="col-sm-3"></div>
				<div class="col-sm-6" style="height: 460px">
					<h2 class="text-center title" style="font-size: 2em; font-family: 微軟正黑體;">註冊成功</h2>
					<h2 class="text-center" style="font-family: 微軟正黑體; color: #777;">感謝您註冊成為一般會員。</h2>
					<p class="text-center" style="font-size: 1.4em; font-family: 微軟正黑體; color: #666;">
						請至<b>email</b>帳戶查收您的帳號與密碼資訊。<br>
						本頁面將於5秒後跳轉至首頁。
					</p>	
				</div>
				<div class="col-sm-3"></div>
			
				</div>
			</div>
		</div>
	</section><!--/form-->
<jsp:include page="/f/frag/f_footer1.jsp"/>
</body>
</html>

