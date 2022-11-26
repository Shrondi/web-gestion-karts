<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="display.javabean.userBean"%>
<jsp:useBean id="usuario" class="display.javabean.userBean" scope = "session"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>UcoGestor</title>
</head>
<body>
	<%
	if(usuario == null || usuario.getNombre()==""){
		String message = request.getParameter("message");
		if(message == null){
			message = "";
		}
		%>
		<p1><%=message%></p1>
		<%
		%>
		<form action="login.jsp" method="post">
	    	Nombre:
	    	<input type="text" name="nombre" placeholder="Nombre">
	    	<br/>
	    	Contraseña:
	    	<input type="text" name="contraseña" placeholder="Contraseña">
	    	<br/>
	    	<p><input type="submit" value="Iniciar sesión"></p>
		</form>
	<%}
	else{
		%>
		<p2>Usuario: <jsp:getProperty property="nombre" name="usuario"/></p2>
		<form action="logout.jsp" method="post">
			<input type="submit" value="Cerrar sesión">
		</form>
	<%}%>
</body>
</html>