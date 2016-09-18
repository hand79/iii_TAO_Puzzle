<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tao.member.model.*"%>
<%@ page import="com.tao.location.model.*"%>
<%@ page import="java.util.*" %>
<%
	MemberVO memberVO = (MemberVO) request.getAttribute("memberVO");
%>

<jsp:include page="/f/frag/f_header1.jsp"/>
<meta http-equiv="Refresh" content="10;url=<%=request.getContextPath()%>/index.jsp">
<title> 註冊成功 </title>
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>
	
	<section style="margin-top: 100px;"><!--form-->
		<div class="container">
			<div class="row" style="height: 590px;">
				<div class="col-sm-3" ></div>
				<div class="col-sm-6" >
					<h2 class="text-center title" style="font-size: 2em; font-family: 微軟正黑體;">註冊成功</h2>
					<h2 class="text-center" style="font-family: 微軟正黑體; color: #777;">感謝您註冊成為商店會員。</h2>
					<p class="text-center" style="font-size: 1.4em; font-family: 微軟正黑體; color: #666;">
						管理人員將盡快為您審核帳戶資料。<br>
						審核結果將以<b>簡訊</b>的方式傳到您的手機。<br>
						本頁面將於10秒後轉跳回首頁。
					</p>	
				</div>
				<div class="col-sm-3" ></div>
			</div>
		</div>
		
	</section><!--/form-->
<jsp:include page="/f/frag/f_footer1.jsp"/>
</body>
</html>

