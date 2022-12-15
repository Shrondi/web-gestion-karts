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
			<title>Vista Cliente</title>
	</head>
	<body>	
		<fieldset>
			<legend>Vista Cliente</legend>
			
			<ul>
				<li>Reservas Individuales
					<ul>
						<li>
							<form method="post" action="/WebProyectoPW-main/CrearReservaIndividual">
								<input type="submit" name="option" class="option" value="Hacer reserva Individual">
							</form>
						</li>
						
						<li>
							<form method="post" action="/WebProyectoPW-main/ModificarReservaIndividual">
								<input type="submit" name="option" class="option" value="Modificar reserva Individual">
							</form>
						</li>
						
						<li>
							<form method="post" action="/WebProyectoPW-main/EliminarReservaIndividual">
								<input type="submit" name="option" class="option" value="Cancelar reserva Individual">
							</form>
						</li>	
					</ul>
				</li>			
			
				<li>Reservas Bono
					<ul>
						<li>
							<form method="post" action="/WebProyectoPW-main/CrearReservaBono">
								<input type="submit" name="option" class="option" value="Hacer reserva Bono">
							</form>
						</li>
						
						<li>
							<form method="post" action="/WebProyectoPW-main/ModificarReservaBono">
								<input type="submit" name="option" class="option" value="Modificar reserva Bono">
							</form>
						</li>
						
						<li>
							<form method="post" action="/WebProyectoPW-main/EliminarReservaBono">
								<input type="submit" name="option" class="option" value="Cancelar reserva Bono">
							</form>
						</li>	
					</ul>
				</li>
				
				<li>Consultas de reservas y pistas
					<ul>
						<li>
							<form method="post" action="/WebProyectoPW-main/ConsultarPistas">
								<input type="submit" name="option" class="option" value="Consultar pistas disponibles">
							</form>
						</li>
						
						<li>
							<form method="post" action="/WebProyectoPW-main/ConsultarReservas">
								<input type="submit" name="option" class="option" value="Consultar reservas">
							</form>
						</li>
						
						<li>
							<form method="post" action="/WebProyectoPW-main/ConsultarReservasRango">
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