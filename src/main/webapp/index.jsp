<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="display.javabean.userBean, java.util.Date"%>
<jsp:useBean id="userBean" class="display.javabean.userBean" scope = "session"/>


<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>UcoGestor</title>
	<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/comun.css">
	<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/index.css">
	<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/footer_header.css">
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
	
		<form action="/WebProyectoPW/mvc/control/LoginController.jsp" method="post">
	    	    	
	    	<label for="correo">Correo electrónico:</label>
	    	<input type="email" name="correo" placeholder="example@gmail.com" required><br><br/>
	    	
	    	<label for="passWord">Contraseña:</label>
	    	<input type="password" name="passWord" placeholder="Contraseña" required><br><br/>	
			
			<p><input type="submit" value="Iniciar sesión"></p>
		</form>
		
		<form action="/WebProyectoPW/mvc/control/RegistroController.jsp" method="post">
			<p><input type="submit" value="Registrarse"></p>
		</form>
	</fieldset>
	
	<p><font color ="red"><%=message%></font></p>
	
	
	<%}
	else{ %>
		<jsp:include page="/include/encabezado.jsp" />
		
		<h1>Bienvenido a UcoKarts</h1>
<%		
		if(userBean.getAdmin()){
		%>
			<jsp:include page="/mvc/control/PaginaPrincipalAdministradorController.jsp" />
					
			<form action="/WebProyectoPW/mvc/view/LoginAdminDisplay.jsp" method="post">
				<p><input type="submit" value="Acceso a operaciones"></p>
			</form>
				
		<%}else{
		%>
			<jsp:include page="/mvc/control/PaginaPrincipalUsuarioController.jsp" />
			
			<form action="/WebProyectoPW/mvc/view/LoginClientDisplay.jsp" method="post">
				<p><input type="submit" value="Acceso a operaciones"></p>
			</form>
		<%} %>
			<form action="/WebProyectoPW/mvc/control/ModificacionController.jsp" method="post">
				<input type="submit" value="Editar perfil">
			</form>
				
			<form action="/WebProyectoPW/mvc/control/LogoutController.jsp" method="post">
				<input type="submit" value="Cerrar sesión">
			</form>
			<p><font color ="red"><%=message%></font></p>
			<p><font color ="red"><%=mensaje%></font></p>
		
		<%
		}%>
		
	<jsp:include page="/include/footer.jsp" />

</body>
</html>