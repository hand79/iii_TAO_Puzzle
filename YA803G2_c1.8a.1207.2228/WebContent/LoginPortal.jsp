<%@ page language="java" contentType="text/html; charset=BIG5"
	pageEncoding="BIG5"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>Login Portal</title>
</head>
<body>
	<h1>Login Portal</h1>
	<h3>${CurrentUser.memid}</h3>
	<a href="Logout.jsp">登出</a>
	<form action="<%=request.getContextPath()%>/MemberLogin">
		ID: <input type="text" name="memid" id="id"><br> PWD: <input
			type="password" name="mempw" id="pw"><br> <input
			type="hidden" name="action" value="login"><br> <input
			type="submit">
	</form>
	<br>
	<form action="<%=request.getContextPath()%>/MemberLogin">
		<input type="hidden" name="memid" value="apple"> <input
			type="hidden" name="mempw" value="apple"> <input
			type="hidden" name="action" value="login"> <input
			type="submit" value="apple">
	</form>
	<form action="<%=request.getContextPath()%>/MemberLogin">
		<input type="hidden" name="memid" value="bird"> <input
			type="hidden" name="mempw" value="bird"> <input type="hidden"
			name="action" value="login"> <input type="submit"
			value="bird">
	</form>
	<form action="<%=request.getContextPath()%>/MemberLogin">
		<input type="hidden" name="memid" value="doggy"> <input
			type="hidden" name="mempw" value="doggy"> <input
			type="hidden" name="action" value="login"> <input
			type="submit" value="doggy">
	</form>
	<form action="<%=request.getContextPath()%>/MemberLogin">
		<input type="hidden" name="memid" value="egg"> <input
			type="hidden" name="mempw" value="egg"> <input
			type="hidden" name="action" value="login"> <input
			type="submit" value="egg">
	</form>
	<form action="<%=request.getContextPath()%>/MemberLogin">
		<input type="hidden" name="memid" value="fox"> <input
			type="hidden" name="mempw" value="fox"> <input type="hidden"
			name="action" value="login"> <input type="submit" value="fox">
	</form>
	<hr>
	<form action="<%=request.getContextPath()%>/MemberLogin">
		<input type="hidden" name="memid" value="nose"> <input
			type="hidden" name="mempw" value="nose"> <input type="hidden"
			name="action" value="login"> <input type="submit" value="nose">
	</form>
	<form action="<%=request.getContextPath()%>/MemberLogin">
		<input type="hidden" name="memid" value="piggy"> <input
			type="hidden" name="mempw" value="piggy"> <input type="hidden"
			name="action" value="login"> <input type="submit" value="piggy">
	</form>
	<form action="<%=request.getContextPath()%>/MemberLogin">
		<input type="hidden" name="memid" value="fox"> <input
			type="hidden" name="mempw" value="fox"> <input type="hidden"
			name="action" value="login"> <input type="submit" value="fox">
	</form>
	<form action="<%=request.getContextPath()%>/MemberLogin">
		<input type="hidden" name="memid" value="queen"> <input
			type="hidden" name="mempw" value="queen"> <input type="hidden"
			name="action" value="login"> <input type="submit" value="queen">
	</form>
	<form action="<%=request.getContextPath()%>/MemberLogin">
		<input type="hidden" name="memid" value="rabbit"> <input
			type="hidden" name="mempw" value="rabbit"> <input type="hidden"
			name="action" value="login"> <input type="submit" value="rabbit">
	</form>
	<form action="<%=request.getContextPath()%>/MemberLogin">
		<input type="hidden" name="memid" value="super"> <input
			type="hidden" name="mempw" value="super"> <input type="hidden"
			name="action" value="login"> <input type="submit" value="super">
	</form>
	<form action="<%=request.getContextPath()%>/MemberLogin">
		<input type="hidden" name="memid" value="tea"> <input
			type="hidden" name="mempw" value="tea"> <input type="hidden"
			name="action" value="login"> <input type="submit" value="tea">
	</form>
</body>
</html>