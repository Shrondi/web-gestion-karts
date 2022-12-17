<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="userBean" scope="session" class="display.javabean.userBean"></jsp:useBean>
<jsp:useBean id="reservaBean" scope="request" class="display.javabean.reservaBean"></jsp:useBean>

<%

//Caso 1: No esta logado o si lo esta si es admin
if (userBean == null || userBean.getCorreo().isEmpty() || userBean.getAdmin() == true) {
	
%>
	<jsp:forward page="../../../index.jsp" />
<%
//Caso 2: El usuario esta logado y no es admin
}else{
	
	String mensajeNextPage = (String) request.getAttribute("mensaje");
	
	if (mensajeNextPage == null) {
		mensajeNextPage = "";
	}
	
%>

<!DOCTYPE html>
<html>
		<head>
				<meta charset="UTF-8">
				<title>Reserva Bono</title>
		</head>
		<body>
				<p id="message"><%= mensajeNextPage %></p>
				
						<form id="formReserva" method="post" action="/WebProyectoPW/ReservaBono">
								<div>
										
										<label for="fecha">Fecha reserva: </label>
										<input type="datetime-local" name="fecha" id="fecha" required>
										 
										
										<% if (reservaBean.getTipoReserva().contentEquals("INFANTIL") || reservaBean.getTipoReserva().contentEquals("FAMILIAR")){  %>
											<p> </p>
											<label for="numeroNinios"> Número de participantes infantiles:  </label>
											<input type="number" name="numeroNinios" id="numeroNinios" min="0" max="20" step="1" required>
										<%}if (reservaBean.getTipoReserva().contentEquals("ADULTOS") || reservaBean.getTipoReserva().contentEquals("FAMILIAR")){ %>
											<p> </p>
											<label for="numeroAdultos">Número de participantes adultos: </label>
											<input type="number" name="numeroAdultos" min="0" max="20" step="1"  required>
										<%} %>
										
										
										<p> </p>
										<label for="duracion"> Duración:  </label>
										<select name="duracion" id="duracion" required>
										  <option value="">...</option>
										  <option value="60">60 minutos</option>
										  <option value="90">90 minutos</option>
										  <option value="120">120 minutos</option>
										</select>
										
								</div>	
						
								<input type="submit" value="Continuar">
						</form>
						<form id="volver" method="post" action="/WebProyectoPW">
								<input type="submit" value="Volver">
						</form>
		</body>
</html>


<%
}
%>