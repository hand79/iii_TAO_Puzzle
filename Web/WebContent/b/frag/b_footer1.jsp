<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
   <%
   String resPath = request.getContextPath() +"/b/res";
   pageContext.setAttribute("resPath", resPath);
   %>
	<!-- /#wrapper -->

	<!-- jQuery Version 1.11.0 -->
	<script src="${resPath}/js/jquery-1.11.0.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="${resPath}/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script src="${resPath}/js/plugins/metisMenu/metisMenu.min.js"></script>

	<!-- DataTables JavaScript -->
	<script src="${resPath}/js/plugins/dataTables/jquery.dataTables.js"></script>
	<script src="${resPath}/js/plugins/dataTables/dataTables.bootstrap.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="${resPath}/js/sb-admin-2.js"></script>
	
	<!-- YA803G2 menu Trigger -->
	<script src="${resPath}/js/menuTrigger.js"></script>