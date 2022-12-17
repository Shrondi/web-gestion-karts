<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="business.reserva.*, java.util.List, java.util.ArrayList"%>
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
	
%>

<!DOCTYPE html>
<html>
		<head>
				<meta charset="UTF-8">
				<title>Consultar Reservas</title>
		</head>
		<body>
				<p id="message"><%= mensajeNextPage %></p>
						<p> Escriba la fecha de inicio y de fin para consultar reservas entre dichas fechas:</p>
						<form id="formReservasUsuario" method="post" action="/WebProyectoPW/ConsultarReservas">
								<div>
								
												<label for="fechaInicio">Fecha Inicio: </label>
												<input type="datetime-local" name="fechaInicio" id="fechaInicio" value="<%=request.getAttribute("fechaInicio")%>" required>
										
										
												<label for="fechaFin">Fecha Fin: </label>
												<input type="datetime-local" name="fechaFin" id="fechaFin" value="<%=request.getAttribute("fechaFin")%>" required>
										
										<div id="dateErrorText"></div>
								</div>	
								
								<input type="submit" value="Nueva busqueda">
						</form>
						<script type="text/javascript" src="/WebProyectoPW/js/ConsultarReservasRango.js"></script>		
						<form id="volver" method="post" action="/WebProyectoPW">
								<input type="submit" value="Volver">
						</form>
						
						<% if (request.getAttribute("reservasInfantilPasadas") != null){ %>
										<jsp:include page="ReservasRangoDisplay.jsp" />  
						<%} %>
		</body>
</html>

<%

}
%>