<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>Insert title here</title>
</head>
<body>
	<form action="<%=request.getContextPath() %>/cases/cases.do">
		<input name="caseno" value="5001">
		<input type="hidden" name="action" value="caseDetail">
		<input type="submit">
	</form>
</body>
</html>