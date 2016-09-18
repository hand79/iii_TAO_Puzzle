
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="resPath" value="<%=request.getContextPath() +\"/b/res\"%>" scope="page" />
<!DOCTYPE html>
<html> 

<head>
<%if(session.getAttribute("bAccount")==null){%>
	<script type="text/javascript">window.location.href = '<%=request.getContextPath()%>/b/backlogin.jsp'</script>
<%}%>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Bootstrap Core CSS -->
<link href="${resPath}/css/bootstrap.min.css" rel="stylesheet">

<!-- MetisMenu CSS -->
<link href="${resPath}/css/plugins/metisMenu/metisMenu.min.css"
	rel="stylesheet">

<!-- DataTables CSS -->
<link href="${resPath}/css/plugins/dataTables.bootstrap.css"
	rel="stylesheet">

<!-- Custom CSS -->
<link href="${resPath}/css/sb-admin-2.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="${resPath}/font-awesome-4.1.0/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
	
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->