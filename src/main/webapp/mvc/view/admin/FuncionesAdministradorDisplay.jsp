<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page errorPage="include/errorPage.jsp" %>
<jsp:useBean id="userBean" scope="session" class="display.javabean.userBean"></jsp:useBean>

<%
if(userBean == null || userBean.getCorreo().equals("") || userBean.getAdmin() == false){
	
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
				<title>Funciones Administrador</title>
				<link rel="stylesheet" type="text/css" href="/web-gestion-karts/css/comun.css">
				<link rel="stylesheet" type="text/css" href="/web-gestion-karts/css/fieldset_funciones.css">
				<link rel="stylesheet" type="text/css" href="/web-gestion-karts/css/footer_header.css">
		</head>
		<body>
			<fieldset>
				<legend>Funciones disponibles</legend>
				
				<ul>
					<li>Operaciones con usuarios
						<ul>
							<li>
								<form method="post" action="/web-gestion-karts/mvc/control/RegistroController.jsp">
									<input type="submit" name="option" class="option" value="Alta de usuario administrador">
								</form>
							</li>
							
						</ul>
					</li>
					<li>Operaciones con pistas y karts
						<ul>
							<li>
								<form method="post" action="/web-gestion-karts/CrearPista">
									<input type="submit" name="option" class="option" value="Crear Pista">
								</form>
							</li>
						
							<li>
								<form method="post" action="/web-gestion-karts/CrearKart">
									<input type="submit" name="option" class="option" value="Crear Kart">
								</form>
							</li>	
											
							<li>
								<form method="post" action="/web-gestion-karts/AsociarKartPista">
									<input type="submit" name="option" class="option" value="Asociar kart a pista">
								</form>
							</li>
							
							<li>
								<form method="post" action="/web-gestion-karts/ModificarEstadoPista">
									<input type="submit" name="option" class="option" value="Modificar estado de una pista">
								</form>
							</li>
							
							<li>
								<form method="post" action="/web-gestion-karts/ModificarEstadoKart">
									<input type="submit" name="option" class="option" value="Modificar estado de un kart">
								</form>
							</li>													
						</ul>
					</li>
					<li>Operaciones con reservas
						<ul>
							<li>
								<form method="post" action="/web-gestion-karts/BorrarReservaIndividual">
									<input type="submit" name="option" class="option" value="Eliminar Reserva Individual">
								</form>
							</li>
							
							<li>
								<form method="post" action="/web-gestion-karts/BorrarReservaBono">
									<input type="submit" name="option" class="option" value="Eliminar Reserva Bono">
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