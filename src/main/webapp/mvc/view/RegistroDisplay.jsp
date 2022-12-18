<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page errorPage="include/errorPage.jsp" %>
<jsp:useBean  id="userBean" scope="session" class="display.javabean.userBean"></jsp:useBean>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Registro</title>
		<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/comun.css">
		<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/fieldset.css">
		<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/validacion.css">
		<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/footer_header.css">
	</head>
	<body>
<%
/* Posibles flujos:
	1) Procede del controlador con mensaje de que ya existe un usuario registrado -> Se vuelve al controlador
	2) Procede del controlador sin mensaje, es decir, es un nuevo registro -> Se vuelve al controlador
*/


//Caso 1: Solo pueden acceder a esta vista si no esta logado o si lo esta es admin
if (userBean == null || userBean.getCorreo().isEmpty() || userBean.getAdmin() == true) {
	
String mensajeNextPage = request.getParameter("message");

if (mensajeNextPage == null) {
	mensajeNextPage = "";
}

String message = "Rellene los siguientes campos para registrarse";

if (userBean.getAdmin()){
	message = "AVISO: Se estan registrando usuarios administradores";
}


%>
	<p><font color ="red"> <%= mensajeNextPage %></font></p>
		<h2> Nuevo Registro </h2>

		<fieldset>
			<form id="registroFormulario" class="registroFormulario" method="post" action="/WebProyectoPW/mvc/control/RegistroController.jsp">
			<p><font color ="blue"><%=message%></font></p>
			
				<label for="nombre">Nombre: </label>
				<input type="text" name="nombre" placeholder="Nombre" required>
				<div class="validar" id="nameErrorText"></div>
				<br></br>
				
				<label for="apellidos">Apellidos: </label>
				<input type="text" name="apellidos" placeholder="Apellidos" required>
				<div class="validar" id="surnameErrorText"></div>
				<br></br>

				<label for="correo">Correo: </label>
				<input type="email" name="correo" id="correo" placeholder="email@example.com" required>
				<div class="validar" id="mailErrorText"></div>
				<br></br>
				
				<label for="fechaNacimiento">Fecha Nacimiento: </label>
				<input type="date" name="fechaNacimiento" id="fechaNacimiento" placeholder="dd/mm/yyyy" required>
				<div class="validar" id="dateErrorText"></div>
				<br></br>
				
				<label for="passWord">Contraseña: </label>
				<input type="password" name="passWord" id="passWord" placeholder="Contraseña" required>
				<div class="validar" id="passwordErrorText"></div>
				<br></br>
	
				<input type="submit" value="Aceptar">
			</form>
			<script type="text/javascript" src="/WebProyectoPW/js/RegisterValidation.js"></script>
		</fieldset>
		
	<jsp:include page="/include/volver.jsp" />
	<jsp:include page="/include/footer.jsp" />
	
</body>
</html>
<%

//Caso 2: El usuario esta logado
}else{
%>
	<jsp:forward page="../../index.jsp" />
<%
}
%>
