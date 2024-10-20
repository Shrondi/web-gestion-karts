<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page errorPage="include/errorPage.jsp" %>
<jsp:useBean id="userBean" scope="session" class="display.javabean.userBean"></jsp:useBean>

<%
if(userBean == null || userBean.getCorreo().equals("") || userBean.getAdmin() == true){
	
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
			<title>Funciones Usuario</title>
			<link rel="stylesheet" type="text/css" href="/web-gestion-karts/css/comun.css">
			<link rel="stylesheet" type="text/css" href="/web-gestion-karts/css/fieldset_funciones.css">
			<link rel="stylesheet" type="text/css" href="/web-gestion-karts/css/footer_header.css">	
	</head>
	<body>	
		<fieldset>
			<legend>Funciones disponibles</legend>
			
			<ul>
				<li>Reservas Individuales
					<ul>
						<li>
							<form method="post" action="/web-gestion-karts/ReservaIndividual">
								<input type="submit" name="option" class="option" value="Hacer reserva Individual">
							</form>
						</li>
						
						<li>
							<form method="post" action="/web-gestion-karts/CancelarReservaIndividual">
								<input type="submit" name="option" class="option" value="Cancelar reserva Individual">
							</form>
						</li>	
					</ul>
				</li>			
			
				<li>Reservas Bono
					<ul>
						<li>
							<form method="post" action="/web-gestion-karts/CrearBono">
								<input type="submit" name="option" class="option" value="Crear Bono">
							</form>
						</li>
						<li>
							<form method="post" action="/web-gestion-karts/ReservaBono">
								<input type="submit" name="option" class="option" value="Hacer reserva para un Bono">
							</form>
						</li>
						
						
						<li>
							<form method="post" action="/web-gestion-karts/CancelarBono">
								<input type="submit" name="option" class="option" value="Cancelar Bono">
							</form>
						</li>
						
						<li>
							<form method="post" action="/web-gestion-karts/CancelarReservaBono">
								<input type="submit" name="option" class="option" value="Cancelar reserva Bono">
							</form>
						</li>
							
					</ul>
				</li>
				
				<li>Consultas de reservas y pistas
					<ul>
						<li>
							<form method="post" action="/web-gestion-karts/ConsultarPistas">
								<input type="submit" name="option" class="option" value="Consultar pistas disponibles">
							</form>
						</li>
						
						<li>
							<form method="post" action="/web-gestion-karts/ConsultarReservas">
								<input type="submit" name="option" class="option" value="Consultar reservas por fecha de inicio y fin">
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