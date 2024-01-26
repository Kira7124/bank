<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 에러페이지 생성시 아래코드 무조건 넣기!! -->
<%@ page isErrorPage ="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>errorPage</title>
</head>
<body>
	<h1>에러 페이지</h1>
	<p>${statusCode}</p>
	<p>${message}</p>
</body>
</html>