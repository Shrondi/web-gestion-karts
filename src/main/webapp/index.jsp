<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page errorPage="include/errorPage.jsp" %>
<%@page import="display.javabean.userBean, java.util.Date"%>
<jsp:useBean id="userBean" class="display.javabean.userBean" scope = "session"/>


<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>UcoGestor</title>
	<link rel="stylesheet" type="text/css" href="/web-gestion-karts/css/comun.css">
	<link rel="stylesheet" type="text/css" href="/web-gestion-karts/css/fieldset.css">
	<link rel="stylesheet" type="text/css" href="/web-gestion-karts/css/footer_header.css">
	<link rel="stylesheet" type="text/css" href="/web-gestion-karts/css/boton_operaciones.css">
</head>

<body>

<%	
	String message = request.getParameter("message");
	String mensaje = (String) request.getAttribute("mensaje");
	if(message == null){
		message = "";
	}
	
	if(mensaje == null){
		mensaje = "";
	}
	
	if(userBean == null || userBean.getCorreo().equals("")){
%>
	<h1>Bienvenido a UcoKarts</h1>
	<fieldset>
			<form action="/web-gestion-karts/mvc/control/LoginController.jsp" method="post">  	
		    	<label for="correo">Correo electrónico:</label><br><br/>
		    	<input type="email" name="correo" placeholder="example@gmail.com" required><br><br/>
		    	
		    	<label for="passWord">Contraseña:</label><br><br/>
		    	<input type="password" name="passWord" placeholder="contraseña" required><br><br/>	
				<p><input type="submit" value="Iniciar sesi&oacute;n"></p>
			</form>
			<form action="/web-gestion-karts/mvc/control/RegistroController.jsp" method="post">
				<p><input type="submit" value="Registrarse"></p>
			</form>
	</fieldset>
	
	<p class="mensaje"><%=message%></p>
	
	
	<%}
	else{ %>
		<jsp:include page="/include/encabezado.jsp" />
		
		<h1>Bienvenido a UcoKarts</h1>
<%		
		if(userBean.getAdmin()){
		%>
			<jsp:include page="/mvc/control/PaginaPrincipalAdministradorController.jsp" />		
				
		<%}else{
		%>
			<jsp:include page="/mvc/control/PaginaPrincipalUsuarioController.jsp" />

		<%} %>
		<p class="mensaje"><%=message%></p>
		<p class="mensaje"><%=mensaje%></p>
	
		<%
		}%>
		
	<jsp:include page="/include/footer.jsp" />

</body>
</html>