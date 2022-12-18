<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>ERROR</title>
		<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/errorPage.css">
	</head>
	
	<body>
	<p>ERROR ${pageContext.errorData.statusCode}</p>
	<fieldset>
		<p>Se ha producido un error</p>
		<form id="volver" method="get" action="/WebProyectoPW/mvc/control/LogoutController.jsp">
			<input type="submit" value="De acuerdo"/>
		</form>
	</fieldset>
	</body>
</html>