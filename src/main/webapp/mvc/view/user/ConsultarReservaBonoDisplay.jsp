<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page errorPage="include/errorPage.jsp" %>
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
	<jsp:include page="/include/encabezado.jsp" />

		<head>
				<meta charset="UTF-8">
				<title>Reserva Bono</title>
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/comun.css">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/footer_header.css">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/aceptar_boton.css">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/fieldset_funciones.css">				
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/reservas.css">												
		</head>
		<body>
			<h2>Hacer Reserva Bono</h2>
				<p class="mensaje" id="message"><%= mensajeNextPage %></p>
					<fieldset>
						<legend>Rellene los siguientes campos</legend>	

						<form id="formReserva" method="post" action="/WebProyectoPW/ReservaBono">
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
										
								<div class="aceptar">
									<input type="submit" value="Continuar">
								</div>
						</form>
					</fieldset>
	<jsp:include page="/include/volver_usuario.jsp" />
	<jsp:include page="/include/footer.jsp" />
	
	</body>
</html>


<%
}
%>