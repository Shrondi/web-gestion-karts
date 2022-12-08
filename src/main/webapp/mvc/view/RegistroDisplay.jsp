<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page errorPage="include/errorPage.jsp" %>
<jsp:useBean  id="userBean" scope="session" class="display.javabean.userBean"></jsp:useBean>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Registro</title>
	</head>
	<body>
<%
/* Posibles flujos:
	1) Procede del controlador con mensaje de que ya existe un usuario registrado -> Se vuelve al controlador
	2) Procede del controlador sin mensaje, es decir, es un nuevo registro -> Se vuelve al controlador
*/


//Caso 1: Solo pueden acceder a esta vista si no esta logado o si lo esta es admin
if (userBean == null || userBean.getCorreo().isEmpty() || (userBean != null && userBean.getAdmin() == true)) {
	
String mensajeNextPage = request.getParameter("mensaje");

if (mensajeNextPage == null) {
	mensajeNextPage = "";
}

String message = "Rellene los siguientes campos para registrarse";

if (userBean.getAdmin()){
	message = "AVISO: Se estan registrando usuarios administradores";
}


%>
		<p id="mensaje"><%= mensajeNextPage %></p>
			<p> NUEVO REGISTRO </p>
			<div>
			<form id="registroFormulario" class="registroFormulario" method="post" action="/WebProyectoPW/mvc/control/RegistroController.jsp">
			
			<p><font color ="blue"><%=message%></font></p>

				<div>
					<p>
						<label for="nombre">Nombre: </label>
						<input type="text" name="nombre" placeholder="Mi nombre" required>
					</p>
					<p>
						<label for="apellidos">Apellidos: </label>
						<input type="text" name="apellidos" placeholder="Mis apellidos" required>
					</p>
					<p>
						<label for="correo">Email: </label>
						<input type="email" name="correo" id="correo" placeholder="email@example.com" required>
					</p>
					<p>
						<label for="fechaNacimiento">Fecha Nacimiento: </label>
						<input type="text" name="fechaNacimiento" id="fechaNacimiento" placeholder="dd/mm/yyyy" required>
					</p>
					<p>
						<label for="passWord">Password: </label>
						<input type="password" name="passWord" id="passWord" placeholder="Password" required>
					</p>
				</div>
				<input type="submit" value="Aceptar">
			</form>
			<script type="text/javascript" src="/WebProyectoPW/js/RegisterValidation.js"></script>
			</div>
			<form method="post" action="../../index.jsp">
				<input type="submit" value="Volver">
			</form>
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
