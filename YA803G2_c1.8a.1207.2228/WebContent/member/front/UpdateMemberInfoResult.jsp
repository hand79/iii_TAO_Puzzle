<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tao.member.model.*"%>
<%@ page import="com.tao.location.model.*"%>
<%
	MemberVO memberVO = (MemberVO) request.getAttribute("memberVO");
%>

<jsp:include page="/f/frag/f_header1.jsp"/>
<title> 會員資料修改成功 </title>
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>
	
	<section id="form"><!--form-->
		<div class="container">
			<div class="row">
			
				<div class="">
					<h2 class="col-sm-offset-1">修改成功, 請按此回到 會員專區 或者回到 首頁 </h2>		
					
				</div>
			</div>
		</div>
	</section><!--/form-->
<jsp:include page="/f/frag/f_footer1.jsp"/>
</body>
</html>

