<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page errorPage="include/errorPage.jsp" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Registro</title>
	</head>
	<body>
<%
/* Posibles flujos:
	1) Hay parámetros en el request  -> viene del RegistroControlador con el mensaje de que ya existe un usuario registrado -> Volvemos al RegistroController
	2) No hay parámetros en el request -> procede del controlador sin mensaje, es decir, es un nuevo registro -> Volvemos a registroController
	3) No hay parámetros en el request -> procede del controlador sin mensaje, es decir, es un nuevo registro -> Volvemos a index.jsp
*/

String messageNextPage = request.getParameter("message");

if (messageNextPage == null) messageNextPage = "";
%>
		<p id="message"><%= messageNextPage %></p>
		<fieldset>
			<legend>Registro</legend>
			<form id="form" method="post" action="../control/RegistroController.jsp">
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
						<input type="correo" name="correo" id="correo" placeholder="email@example.com" required>
					</p>
					<p>
						<label for="fechaNacimiento">Fecha Nacimiento: </label>
						<input type="fechaNacimiento" name="fechaNacimiento" id="fechaNacimiento" placeholder="dd/mm/yyyy" required>
					</p>
					<p>
						<label for="passWord">Password: </label>
						<input type="passWord" name="passWord" id="passWord" placeholder="Password" required>
					</p>
				</div>
				<input type="submit" value="Submit">
			</form>
			<form method="post" action="../../index.jsp">
				<input type="submit" value="Volver">
			</form>
		</fieldset>
	</body>
</html>
