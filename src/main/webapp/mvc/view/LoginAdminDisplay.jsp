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

	<jsp:include page="/include/encabezado.jsp" />

		<head>
				<meta charset="UTF-8">
				<title>Vista Administrador</title>
		</head>
		<body>
			<fieldset>
				<legend>Vista Administrador</legend>
				
				<ul>
					<li>Operaciones con usuarios
						<ul>
							<li>
								<form method="get" action="/WebProyectoPW-main/AltaUsuario">
									<input type="submit" name="option" class="option" value="Alta de usuario administrador">
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
					</li>
					<li>Operaciones con pistas y karts
						<ul>
							<li>
								<form method="get" action="/CrearPista">
									<input type="submit" name="option" class="option" value="Crear Pista">
								</form>
							</li>
						
							<li>
								<form method="get" action="/CrearKart">
									<input type="submit" name="option" class="option" value="Crear Kart">
								</form>
							</li>	
											
							<li>
								<form method="get" action="/AsociarKartPista">
									<input type="submit" name="option" class="option" value="Asociar kart a pista">
								</form>
							</li>
							
							<li>
								<form method="get" action="/ModificarEstadoPista">
									<input type="submit" name="option" class="option" value="Modificar estado de una pista">
								</form>
							</li>
							
							<li>
								<form method="get" action="/ModificarEstadoKart">
									<input type="submit" name="option" class="option" value="Modificar estado de un kart">
								</form>
							</li>													
						</ul>
					</li>
					<li>Operaciones con reservas
						<ul>
							<li>
								<form method="get" action="/CancelarReserva">
									<input type="submit" name="option" class="option" value="Eliminar Reserva">
								</form>
							</li>							
						</ul>
					</li>										
				</ul>
			</fieldset>
			
	<jsp:include page="/include/volver.jsp" />
	<jsp:include page="/include/footer.jsp" />
						
	</body>
</html>