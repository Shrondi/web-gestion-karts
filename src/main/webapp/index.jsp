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
	<%usuario = new userBean();
	if(usuario == null || usuario.getNombre()==""){
		String message = request.getParameter("message");
		if(message == null){
			message = "";
		}
		%>
		<p><%=message%></p>
		<form action="login.jsp" method="post">
	    	Nombre:
	    	<input type="text" name="nombre" placeholder="Nombre">
	    	<br/>
	    	Contraseña:
	    	<input type="password" name="contraseña" placeholder="Contraseña">
	    	<br/>
	    	<p><input type="submit" value="Iniciar sesión"></p>
		</form>
	<%}
	else{
		%>
		<p>Usuario: <jsp:getProperty property="nombre" name="usuario"/></p>
		<form action="logout.jsp" method="post">
			<input type="submit" value="Cerrar sesión">
		</form>
	<%}%>
	<aside>
	<ul style="list-style-type:none;">
		<li>Pablo Roldan Puebla</li>
		<li>Paloma Romero Delgado</li>
		<li>Carlos Lucena Robles</li>
		<li>Miguel Raigon Jimenez</li>
		<li>Kamal Abdelkader</li>
	</ul>
</aside>
</body>

</html>