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
<h1>Bienvenido a UcoKarts</h1>
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
	else{
		
		if(userBean.getAdmin()){
		%>
			<p><strong>¡Bienvenido Administrador <%=userBean.getNombre() + userBean.getApellidos()%>!</strong></p>
			<jsp:include page="/mvc/control/ListadoUsuariosController.jsp" />
					
			<form action="/WebProyectoPW/mvc/view/LoginAdminDisplay.jsp" method="post">
				<p><input type="submit" value="Acceso a operaciones"></p>
			</form>
				
		<%}else{
		%>
			<p><strong>¡Bienvenido usuario <%=userBean.getCorreo()%>!</strong></p>
			<p>Hoy es <%= new java.util.Date() %></p>
			
			<%if (userBean.getAntiguedad() <= 0){ %>
				<p>Se registró hoy</p>
			<%}else{ %>
				<p>Se registró <%= userBean.getFechaInscripcion()%></p>
			<%} %>
			
			<%if (userBean.getAntiguedad() <= 0){ %>
				<p>Lleva con nosotros menos de un mes</p>
			<%}else{ %>
				<p>Lleva con nosotros <%= userBean.getAntiguedad()%> meses</p>
			<%} %>
			
			<%if (userBean.getFechaReserva().compareTo(new Date()) != 0){ %>
				<p>Su proxima reserva es el <%= userBean.getFechaReservaString()%></p>
			<%}else{ %>
				<p> No tiene reservas futuras </p>
			<%} %>
			
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