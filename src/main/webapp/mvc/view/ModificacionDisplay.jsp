<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="userBean" scope="session" class="display.javabean.userBean"></jsp:useBean>

<%

if(userBean == null || userBean.getCorreo().equals("")){
	
%>

		<jsp:forward page="/">
				<jsp:param value="Debe acceder o registrarse" name="message"/>
		</jsp:forward>

<%
}
%>

<!DOCTYPE html>
<html>
		<head>
				<meta charset="UTF-8">
				<title>Modificar datos del usuario</title>
		</head>
		<body>

<%

String nextPage = "/WebProyectoPW/mvc/control/RegistroController.jsp";
String mensajeNextPage = request.getParameter("message");

if(mensajeNextPage == null) mensajeNextPage = "";

String password = request.getParameter("password");

%>

				<p id="message"><%= mensajeNextPage %></p>
				<fieldset>
						<legend> Modificar datos</legend>
						<form id="formModificarUsuario" method="post" action="./WebProyectoPW/mvc/control/ModificacionController.jsp">
								<div>
										<p>
												<label for="name">Nombre: </label>
												<input type="text" name="name" placeholder="<%= userBean.getNombre() %>">
										</p>
										<p>
												<label for="surname">Apellidos: </label>
												<input type="text" name="surname" placeholder="<%= userBean.getApellidos() %>">
										</p>
										<p>
												<label for="password">Password: </label>
												<input type="password" id="password" name="password">
										</p>
										<p>
												<label for="email">Email: </label>
												<input type="text" name="email" value="<%= userBean.getCorreo() %>" readonly>
										</p>
								</div>	
								<input type="submit" value="Modificar">
						</form>
						<form id="volver" method="post" action="/WebProyectoPW">
								<input type="submit" value="Volver">
						</form>
				</fieldset>
		</body>
</html>