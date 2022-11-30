<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page errorPage="include/errorPage.jsp" %>
<jsp:useBean id="userBean" scope="session" class="display.javabean.userBean"></jsp:useBean>

<%
if(userBean == null || userBean.getCorreo().equals("")){
	
	request.setAttribute("message", "Debe acceder o registrarse");
	%>
	<jsp:forward page="../../index.jsp"/>
	<%	
}

%>
<!DOCTYPE html>
<html>
		<head>
				<meta charset="UTF-8">
				<title>Vista Administrador</title>
		</head>
		<body>
			<fieldset>
				<legend>Vista Administrador</legend>
				
				<p>
					¡Bienvenido Administrador <%=userBean.getNombre()%>!<br>
				</p>
				
				<ul>
					<li>
						<form method="get" action="/WebProyectoPW-main/AltaUsuario">
							<input type="submit" name="option" class="option" value="Alta de usuario">
						</form>
					</li>
				
					<li>
						<form method="get" action="/WebProyectoPW-main/ModificarUsuario">
							<input type="submit" name="option" class="option" value="Modificar todos los campos de un usuario">
						</form>
					</li>
					
					<li>
						<form method="get" action="/WebProyectoPW-main/ModificarNombreUsuario">
							<input type="submit" name="option" class="option" value="Modificar nombre de un usuario">
						</form>
					</li>
					
					<li>
						<form method="get" action="/WebProyectoPW-main/ModificarApellidosUsuario">
							<input type="submit" name="option" class="option" value="Modificar apellidos de un usuario">
						</form>
					</li>		
					
					
					<li>
						<form method="get" action="/WebProyectoPW-main/ModificarFechaNacimientoUsuario">
							<input type="submit" name="option" class="option" value="Modificar fecha de nacimiento de un usuario">
						</form>
					</li>						
											
					<li>
						<form method="get" action="/WebProyectoPW-main/ModificarFechaInscripcionUsuario">
							<input type="submit" name="option" class="option" value="Modificar fecha de inscripción de un usuario">
						</form>
					</li>									
							
					<li>
						<form method="get" action="/WebProyectoPW-main/EliminarUsuario">
							<input type="submit" name="option" class="option" value="Eliminar usuario">
						</form>
					</li>	

					<li>
						<form method="get" action="/WebProyectoPW-main/BuscarUsuario">
							<input type="submit" name="option" class="option" value="Búsqueda de un usuario por su correo">
						</form>
					</li>	

					<li>
						<form method="get" action="/WebProyectoPW-main/ListadoUsuarios">
							<input type="submit" name="option" class="option" value="Listado de usuarios registrados">
						</form>
					</li>										
				</ul>
				
				<form method="post" action="/WebProyectoPW/mvc/control/ModificacionController.jsp">
					<input type="submit" value="Modificar datos">
				</form>
				
				<form method="post" action="/WebProyectoPW/mvc/control/CerrarSesionController.jsp">
					<input type="submit" value="Desconectar">
				</form>
				
			</fieldset>
		</body>
</html>