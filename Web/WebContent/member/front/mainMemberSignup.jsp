<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tao.member.model.*"%>
<%@ page import="java.util.*" %>

<jsp:include page="/f/frag/f_header1.jsp"/>
<title> 會員登入 </title>
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>
	
	<section id="form" style="margin-top: 80px;"><!--form-->
		<div class="container">
			<div class="row" style="height:  450px;">
				<div class="col-sm-4 col-sm-offset-1">
					
						<h2 class="text-center title" style="font-size: 2em;">一般會員註冊</h2>
						<div style="margin-left:30px;"> <a href="<%=request.getContextPath()%>/member/front/memberSignup.jsp"><img src="<%=request.getContextPath()%>/f/res/images/member/member.jpg" alt="一般會員" class="img-rounded"></a> </div>
					
				</div>
				<div class="col-sm-1">
					<h2 class="or">OR</h2>
				</div>
				<div class="col-sm-4">
					
						<h2 class="text-center title" style="font-size: 2em;">商店會員註冊</h2>
						<div  style="margin-left:30px;"> <a href="<%=request.getContextPath()%>/member/front/memberSignupForShop.jsp"> <img src="<%=request.getContextPath()%>/f/res/images/member/shopMember.jpg" alt="店家會員" class="img-rounded"> </a> </div>
					
				</div>
			</div>
		</div>
	</section><!--/form-->
<jsp:include page="/f/frag/f_footer1.jsp"/>
</body>
</html>