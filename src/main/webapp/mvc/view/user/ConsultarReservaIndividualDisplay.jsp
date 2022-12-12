<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="userBean" scope="session" class="display.javabean.userBean"></jsp:useBean>

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
	
	if (request.getSession().getAttribute("fecha") != null){
		mensajeNextPage = "Se ha recuperado la informacion de una reserva sin realizar";
	}

%>

<!DOCTYPE html>
<html>
		<head>
				<meta charset="UTF-8">
				<title>Reserva Individual</title>
		</head>
		<body>
				<p id="message"><%= mensajeNextPage %></p>
						<form id="formReservaIndividual" method="post" action="/WebProyectoPW/ReservaIndividual">
								<div>
										<p>
												<label for="fecha">Fecha Reserva: </label>
												<input type="datetime-local" name="fecha" id="fecha" value="<%= request.getSession().getAttribute("fecha")%>" required>
										</p>
										
										 
										 		<input type="radio" name="tipoReserva" value="INFANTIL" required>Reserva Infantil <br>
												<input type="radio" name="tipoReserva" value="FAMILIAR" required>Reserva Familiar <br>
												<input type="radio" name="tipoReserva" value="ADULTOS" required>Reserva Adultos
										
										
										<p>
										Escribe el numero de participantes infantiles:
										<input type="number" name="numeroNinios" min="0" max="20" step="1" value="<%= request.getSession().getAttribute("numeroNinios")%>" required>
										</p>
										
										<p>
										Escribe el numero de participantes adultos:
										<input type="number" name="numeroAdultos" min="0" max="20" step="1" value="<%= request.getSession().getAttribute("numeroAdultos")%>" required>
										</p>
										
										<p>
										Elige la duracion de la reserva :
										<select name="duracion" required>
										  <option value="">...</option>
										  <option value="60">60 minutos</option>
										  <option value="90">90 minutos</option>
										  <option value="120">120 minutos</option>
										</select>
										</p>
										
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