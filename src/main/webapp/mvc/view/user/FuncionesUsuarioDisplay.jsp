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
			<title>Funciones Usuario</title>
			<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/comun.css">
			<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/fieldset_funciones.css">
			<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/footer_header.css">	
	</head>
	<body>	
		<fieldset>
			<legend>Funciones disponibles</legend>
			
			<ul>
				<li>Reservas Individuales
					<ul>
						<li>
							<form method="post" action="/WebProyectoPW/ReservaIndividual">
								<input type="submit" name="option" class="option" value="Hacer reserva Individual">
							</form>
						</li>
						
						<li>
							<form method="post" action="/WebProyectoPW/CancelarReservaIndividual">
								<input type="submit" name="option" class="option" value="Cancelar reserva Individual">
							</form>
						</li>	
					</ul>
				</li>			
			
				<li>Reservas Bono
					<ul>
						<li>
							<form method="post" action="/WebProyectoPW/CrearBono">
								<input type="submit" name="option" class="option" value="Crear Bono">
							</form>
						</li>
						<li>
							<form method="post" action="/WebProyectoPW/ReservaBono">
								<input type="submit" name="option" class="option" value="Hacer reserva para un Bono">
							</form>
						</li>
						
						
						<li>
							<form method="post" action="/WebProyectoPW/CancelarBono">
								<input type="submit" name="option" class="option" value="Cancelar Bono">
							</form>
						</li>
						
						<li>
							<form method="post" action="/WebProyectoPW/CancelarReservaBono">
								<input type="submit" name="option" class="option" value="Cancelar reserva Bono">
							</form>
						</li>
							
					</ul>
				</li>
				
				<li>Consultas de reservas y pistas
					<ul>
						<li>
							<form method="post" action="/WebProyectoPW/ConsultarPistas">
								<input type="submit" name="option" class="option" value="Consultar pistas disponibles">
							</form>
						</li>
						
						<li>
							<form method="post" action="/WebProyectoPW/ConsultarReservas">
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