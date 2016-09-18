<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<jsp:include page="/f/frag/f_header1.jsp" flush="true" />
<title>兌現申請 | 饕飽拼圖</title>
<jsp:include page="/f/frag/f_header2_with_breadcrumb.jsp" />

<div class="container text-center">
	<div class="logo-404">
		<a href=""><img src="" alt="" /></a>
	</div>
	<div class="content-404">
		<h1>
			<b>OPPS!</b>申請失敗
		</h1>
		<p>有任何問題歡迎聯繫客服</p>
		<h2>
			<a href="<%=request.getContextPath()%>/select_page_wallet.jsp">由此回電子錢包</a>
		</h2>
	</div>
</div>


<jsp:include page="/f/frag/f_footer2.jsp" />