<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page errorPage="include/errorPage.jsp" %>
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

	<jsp:include page="/include/desconectar.jsp" />

		<head>
				<meta charset="UTF-8">
				<title>Modificar datos del usuario</title>
				<link rel="stylesheet" type="text/css" href="/web-gestion-karts/css/comun.css">
				<link rel="stylesheet" type="text/css" href="/web-gestion-karts/css/footer_header.css">	
				<link rel="stylesheet" type="text/css" href="/web-gestion-karts/css/fieldset.css">							
				<link rel="stylesheet" type="text/css" href="/web-gestion-karts/css/boton_aceptar.css">
				<link rel="stylesheet" type="text/css" href="/web-gestion-karts/css/validacion.css">
		</head>
		<body>
			<h3>Modifique sus datos </h3>
			<p class="mensaje" id="message"><%= mensajeNextPage %></p>
			<fieldset>
				<form id="formModificarUsuario" method="post" action="/web-gestion-karts/mvc/control/ModificacionController.jsp">
						
					<label for="nombre">Nombre: </label>
					<input type="text" name="nombre" id="nombre" value="<%=userBean.getNombre()%>" required="required">
					<div class="validar" id="nameErrorText"></div>
					<br></br>
					
					<label for="apellidos">Apellidos: </label>
					<input type="text" name="apellidos" id="apellidos" value="<%=userBean.getApellidos()%>" required="required">
					<div class="validar" id="surnameErrorText"></div>
					<br></br>
	
					<label for="passWord">Contraseña: </label>
					<input type="password" name="passWord" id="passWord" required="required">
					<div class="validar" id="passwordErrorText"></div>
					<br></br>
	
					<label for="email">Correo: </label>
					<input type="email" id="email" value="<%= userBean.getCorreo() %>" readonly>
					<br></br>
					
					<div class="aceptar">
						<input type="submit" value="Aceptar">
					</div>
				</form>
			</fieldset>
				
			<script type="text/javascript" src="/web-gestion-karts/js/ModifyValidation.js"></script>
				
	<jsp:include page="/include/volver.jsp" />
	<jsp:include page="/include/footer.jsp" />
	
	</body>
</html>

<%
}
%>