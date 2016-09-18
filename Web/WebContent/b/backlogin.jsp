<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="BIG5"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>

<head>

       <title>饕飽拼圖後端管理系統</title>
 
    <link href="res/css/bootstrap.min.css" rel="stylesheet">
    <link href="res/css/plugins/metisMenu/metisMenu.min.css" rel="stylesheet">
    <link href="res/css/sb-admin-2.css" rel="stylesheet">
    <link href="res/font-awesome-4.1.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<!-- 	<link href="res/css/login_1.css" rel="stylesheet"> -->
	<style>
	*{
	padding: 0px;
	margin:0px;
	/*border:0px;*/
	color:#0C3342;
	
}
body{
	background-color: #EcEcEc;
}

#out_wrapper{
	width: 620px;
	margin: 120px auto;
	/*border: 1px solid;*/
}

#title{
	height: 210px;
	/*border: 1px solid green;*/
}

#title img{
	width: 200px;
	height: 200px;
}

#title span{
	width: 400px;
	height: 200px;
	position:relative;
	top: 20px;
	left: 10px;
	font-family: 微軟正黑體;
	font-size: 4em;
	font-weight: bold;
	/*color: #2540F5;*/
	color: #FFF;
	text-shadow: 2px 2px #777;
	text-de
}

#login_window{
	border: 2px solid #2BBDF6;
	border-radius: 20px;
	margin: 0px auto;
	width: 600px;
	background-color: #DAEAF0;
}

#login_window form{
	border: 5px solid #A6DFF5;
	border-radius: 19px;
	padding: 50px;
}
#login_window table{
	margin: 0px auto;
	/*border: 1px dashed orange;*/
}
#login_window td{
	padding: 20px 0px 20px 0px;
	/*border: 1px dashed orange;*/
	text-align: center;
}


#login_window .font{
	font-family: 微軟正黑體;
	font-size: 1.6em;
	text-align: right;
	font-weight: bold;
}
#login_window .text_field{
	width: 320px;
	font-size: 1.6em;
	margin-left: 20px;
	font-family: 微軟正黑體;
	padding: 3px;
	border-radius: 7px;
	border: 2.5px solid #A6DFF5;
	
}
#login_window #login{
		font-size: 1.6em;
	font-family: 微軟正黑體;
		font-weight:bold;
		width: 180px;
		text-shadow: 1px 1px #AAA;
		height: 50px;
		border-radius: 5px;
	border:solid 2px #2BBDF6;
	background:#98DBF5;
}
#login_window #login:hover{
	/*background: #2BBDF6;*/
	/*background:-webkit-linear-gradient(top, #98DBF5, #2BBDF6 );*/
	/*border-radius: 5px;*/
	/*border:none;	*/
	/*border: solid 2px #A6DFF5;*/
	box-shadow: 2px 2px 10px #999;
	cursor: pointer;
}
	
	</style>
</head>

<body>

	<div id="out_wrapper">
		<header id="title">
			<img src="res/images/logo_gray_demo.jpg"/>
			<span>
				後端管理系統
			</span>
		</header>
		
		<div id="login_window">
			<form id="loginform" action="<%=request.getContextPath()%>/BackLoginServlet.do">
			<table>
			<tr>
				<td class="font">帳號</td>
				<td><input id="logacc" class="text_field" name="loginacc" placeholder="請輸入帳號" type="text"/></td>
			</tr>
			<tr>
				<td class="font">密碼</td>
				<td><input  id="logaccpw" class="text_field" placeholder="請輸入密碼" name="loginaccpw" type="password"/></td>
			</tr>
			<tr>
				<td colspan="2">
				<input type="hidden" name="action" value="login">
				<input id="login" type="button"  value="登入"></td>
			</tr>
			<tr>
				<td colspan="2"><p id="message" style="color:red;"></p></td>
			</tr>
			</table>
			
			</form>
			
		</div>
	</div>
	
    
    <script src="res/js/jquery-1.11.0.js"></script>
<!--     <script src="res/js/bootstrap.min.js"></script> -->
<!--     <script src="res/js/plugins/metisMenu/metisMenu.min.js"></script> -->
<!--     <script src="res/js/sb-admin-2.js"></script> -->
    
	<script>
	$(document).ready(function(){
		$('.text_field').on('keydown', function(e){
			if(parseInt(e.which) == 13){
				$('#login').click();
			}
		});
		$("#login").click(function(){
			var logacc=$("#logacc").val();
			var logaccp=$("#logaccpw").val();
			
			$.post("<%=request.getContextPath()%>/BackLoginServlet.do",{action:"loginCheck",loginacc:logacc,loginaccpw:logaccp},function(data){
				var mes=data;
				
				if(mes=="ok"){
					$("#loginform").submit();
				}else{
					$("#message").html("");
					$("#message").append(mes);
				}
				
			});
		});
	});
		
	
		
	</script>
</body>

</html>
