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
<title> ���U���\ </title>
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp"/>
	
	<section style="margin-top: 100px;"><!--form-->
		<div class="container">
			<div class="row" style="height: 590px;">
				<div class="col-sm-3" ></div>
				<div class="col-sm-6" >
					<h2 class="text-center title" style="font-size: 2em; font-family: �L�n������;">���U���\</h2>
					<h2 class="text-center" style="font-family: �L�n������; color: #777;">�P�±z���U�����ө��|���C</h2>
					<p class="text-center" style="font-size: 1.4em; font-family: �L�n������; color: #666;">
						�޲z�H���N�ɧ֬��z�f�ֱb���ơC<br>
						�f�ֵ��G�N�H<b>²�T</b>���覡�Ǩ�z������C<br>
						�������N��10�������^�����C
					</p>	
				</div>
				<div class="col-sm-3" ></div>
			</div>
		</div>
		
	</section><!--/form-->
<jsp:include page="/f/frag/f_footer1.jsp"/>
</body>
</html>

