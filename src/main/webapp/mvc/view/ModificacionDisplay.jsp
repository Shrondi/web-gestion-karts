<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="userBean" scope="session" class="display.javabean.userBean"></jsp:useBean>

<%

//Caso 1: No esta logado
if (userBean == null || userBean.getCorreo().isEmpty() ) {
	
%>
	<jsp:forward page="../../index.jsp" />
<%
//Caso 2: El usuario esta logado
}else{
	
	String mensajeNextPage = request.getParameter("mensaje");
	
	if (mensajeNextPage == null) {
		mensajeNextPage = "";
	}

%>

<!DOCTYPE html>
<html>
		<head>
				<meta charset="UTF-8">
				<title>Modificar datos del usuario</title>
		</head>
		<body>
				<p id="message"><%= mensajeNextPage %></p>
						<legend> Modificar datos</legend>
						<form id="formModificarUsuario" method="post" action="/WebProyectoPW/mvc/control/ModificacionController.jsp">
								<div>
										<p>
												<label for="nombre">Nombre: </label>
												<input type="text" name="nombre" id="nombre" value="<%=userBean.getNombre()%>" required="required">
										</p>
										<p>
												<label for="apellidos">Apellidos: </label>
												<input type="text" name="apellidos" id="apellidos" value="<%=userBean.getApellidos()%>" required="required">
										</p>
										<p>
												<label for="passWord">Contraseña: </label>
												<input type="password" name="passWord" id="passWord" required="required">
										</p>
										<p>
												<label for="email">Email: </label>
												<input type="email" id="email" value="<%= userBean.getCorreo() %>" readonly>
										</p>
								</div>	
								<input type="submit" value="Modificar datos">
						</form>
						<form id="volver" method="post" action="/WebProyectoPW">
								<input type="submit" value="Volver">
						</form>
		</body>
</html>

<%
}
%>