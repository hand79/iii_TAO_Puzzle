<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<%
	pageContext.setAttribute("resPath", request.getContextPath() + "/f/res");
%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link href="${resPath}/css/bootstrap.min.css" rel="stylesheet">
<link href="${resPath}/css/font-awesome.min.css" rel="stylesheet">
<link href="${resPath}/css/prettyPhoto.css" rel="stylesheet">
<link href="${resPath}/css/price-range.css" rel="stylesheet">
<link href="${resPath}/css/animate.css" rel="stylesheet">
<link href="${resPath}/css/main.css" rel="stylesheet">
<link href="${resPath}/css/responsive.css" rel="stylesheet">
<!--[if lt IE 9]>
    <script src="${resPath}/js/html5shiv.js"></script>
    <script src="${resPath}/js/respond.min.js"></script>
    <![endif]-->
<link rel="shortcut icon" href="${resPath}/images/ico/favicon.ico">
<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href="${resPath}/images/ico/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="${resPath}/images/ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="${resPath}/images/ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed"
	href="${resPath}/images/ico/apple-touch-icon-57-precomposed.png">
<style>
	#loginModal *{
		font-family: 微軟正黑體;
	}
	div>h2+ul.index-tag li a {
			padding: 8px 43px;
			background-color:#F0F0E9;
			color:#363432;
		}
	#areaShowing{
		font-family: 微軟正黑體;
	}
</style>