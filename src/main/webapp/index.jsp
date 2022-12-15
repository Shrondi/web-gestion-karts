<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="display.javabean.userBean, java.util.Date"%>
<jsp:useBean id="userBean" class="display.javabean.userBean" scope = "session"/>


<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>UcoGestor</title>
</head>

<body>
<h1>Bienvenido a UcoKarts</h1>
<%	
	String message = request.getParameter("message");
	if(message == null){
		message = "";
	}
	
	String mensaje = "Rellene los siguientes campos para iniciar sesión";
	
	if(userBean == null || userBean.getCorreo().equals("")){
%>
	<fieldset>
		<form action="/WebProyectoPW/mvc/control/LoginController.jsp" method="post">
	    <p><font color ="blue"><%=mensaje%></font></p>
	    
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
			<p>¡Bienvenido Administrador <%=userBean.getNombre() + userBean.getApellidos()%>!</p>
			<jsp:include page="/mvc/control/ListadoUsuariosController.jsp" />
					
			<form action="/WebProyectoPW/mvc/view/LoginAdminDisplay.jsp" method="post">
				<p><input type="submit" value="Acceso a operaciones"></p>
			</form>
				
		<%}else{
		%>
			<p>¡Bienvenido usuario <%=userBean.getCorreo()%>!</p>
			<p>Son las <%= new java.util.Date() %></p>
			<p>Se registró <%= userBean.getFechaInscripcion()%></p>
			<p>Lleva con nosotros <%= userBean.getAntiguedad()%> meses</p>
			
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
		
		<%
		}%>
		
		<footer>
		  Programación Web - GM3
		</footer>

</body>
</html>