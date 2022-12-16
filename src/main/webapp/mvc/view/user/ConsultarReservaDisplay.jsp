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
	
	String mensaje = "";
	if (request.getSession().getAttribute("fecha") != null){
		mensaje = "Se ha recuperado la informacion de una reserva sin realizar";
	}
	
	String tipoBono = " ";
	
	//En caso de que se cambie forzadamente por url al servlet que comparte este mismo display
	if (request.getAttribute("nextPage") == "/WebProyectoPW/ReservaIndividual"){
		request.getSession().removeAttribute("tipoReserva");
		
	}else if(request.getAttribute("nextPage") == "/WebProyectoPW/ModificarReservaIndividual"){
				
	}else if ((String) request.getSession().getAttribute("tipoReserva") != null){
		tipoBono = (String) request.getSession().getAttribute("tipoReserva");
		
	}else{
%>
	<jsp:forward page="../../../index.jsp" />

<% } %>

<!DOCTYPE html>
<html>
		<head>
				<meta charset="UTF-8">
				<title>Reserva Individual</title>
		</head>
		<body>
				<p id="message"><%= mensajeNextPage %></p>
				<p id="mensaje"><%= mensaje %></p>
						<form id="formReserva" method="post" action="<%= request.getAttribute("nextPage")%>">
								<div>
										
										<label for="fecha">Fecha reserva: </label>
										<input type="datetime-local" name="fecha" id="fecha" value="<%= request.getSession().getAttribute("fecha")%>" required>
										 
										<% 
										if (tipoBono.contentEquals("INFANTIL") || tipoBono.contentEquals("FAMILIAR")){  %>
											<p> </p>
											<label for="numeroNinios"> Número de participantes infantiles:  </label>
											<input type="number" name="numeroNinios" id="numeroNinios" min="0" max="20" step="1" value="<%= request.getSession().getAttribute("numeroNinios")%>" required>
										<%}if (tipoBono.contentEquals("ADULTOS") || tipoBono.contentEquals("FAMILIAR")){ %>
											<p> </p>
											<label for="numeroAdultos">Número de participantes adultos: </label>
											<input type="number" name="numeroAdultos" min="0" max="20" step="1" value="<%= request.getSession().getAttribute("numeroAdultos")%>" required>
										<%}if (tipoBono.contentEquals(" ")){%>
											<p> Tipo reserva: </p>
											<input type="radio" name="tipoReserva" id="infantil" value="INFANTIL" "checked=\"checked\"" required>
											<label for="infantil">Reserva Infantil</label> <br>
											
											<input type="radio" name="tipoReserva" id="familiar" value="FAMILIAR" required>
											<label for="familiar">Reserva Familiar</label> <br>
											
											<input type="radio" name="tipoReserva" id="adultos" value="ADULTOS" required>
											<label for="adultos">Reserva Adulto</label> <br>
											
											<p> </p>
											<label for="numeroNinios"> Número de participantes infantiles:  </label>
											<input type="number" name="numeroNinios" id="numeroNinios" min="0" max="20" step="1" value="<%= request.getSession().getAttribute("numeroNinios")%>" placeholder="0" required>
											<p> </p>
											<label for="numeroAdultos">Número de participantes adultos: </label>
											<input type="number" name="numeroAdultos" min="0" max="20" step="1" value="<%= request.getSession().getAttribute("numeroAdultos")%>" placeholder="0" required>
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