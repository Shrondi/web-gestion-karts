<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="display.javabean.userBean"%>
<jsp:useBean id="userBean" class="display.javabean.userBean" scope = "session"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>UcoGestor</title>
</head>
<body>
<h2>Bienvenido a UcoGestor</h2>
<%
	String message = request.getParameter("message");
	if(message == null){
		message = "";
	}
	if(userBean == null || userBean.getNombre()==""){
%>
		<form action="/WebProyectoPW/mvc/control/LoginController.jsp" method="post">
	    	Email:
	    	<input type="email" name="correo" placeholder="example@gmail.com">
	    	<br/>
	    	Contraseña:
	    	<input type="password" name="passWord" placeholder="Contraseña">
	    	<br/>
	    	<p><input type="submit" value="Iniciar sesión"></p>
		</form>
		<form action="/WebProyectoPW/mvc/control/RegistroController.jsp" method="post">
			<p><input type="submit" value="Registrarse"></p>
		</form>
	<%}
	else{
		%>
		<p>Usuario: <%=userBean.getNombre() %></p>
		<form action="/WebProyectoPW/mvc/control/CerrarSesionController.jsp" method="post">
			<input type="submit" value="Cerrar sesión">
		</form>
		<form action="/WebProyectoPW/mvc/control/ModificacionController.jsp" method="post">
			<input type="submit" value="Editar perfil">
		</form>
	<%}%>
	<p><font color ="red"><%=message%></font></p>
	<footer>
</footer>
</body>

</html>