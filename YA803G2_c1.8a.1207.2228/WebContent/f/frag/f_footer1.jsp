<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>
<%
	pageContext.setAttribute("resPath", request.getContextPath()
			+ "/f/res");
%>
<footer id="footer">
	<!--Footer-->

	<div class="footer-bottom">
		<div class="container">
			<div class="row">
				<p class="pull-left">Copyright &copy; 2014 TaoBao Puzzle Inc.
					All rights reserved.</p>
				<p class="pull-right">
					Designed by <span>YA803_G2</span>
				</p>
			</div>
		</div>
	</div>

</footer>
<!--/Footer-->


<script src="${resPath}/js/jquery.js"></script>
<script src="${resPath}/js/price-range.js"></script>
<script src="${resPath}/js/jquery.scrollUp.min.js"></script>
<script src="${resPath}/js/bootstrap.min.js"></script>
<script src="${resPath}/js/jquery.prettyPhoto.js"></script>
<script src="${resPath}/js/main.js"></script>
<script src="${resPath}/js/tao-header-login.js"></script>
<script src="${resPath}/js/change_area.js"></script>
<script>
	$('#enter_shop_member_center').click(function(){
		$('body *').css('cursor', 'progress');
	});
</script>