<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="business.reserva.*, java.util.List, java.util.ArrayList"%>
<jsp:useBean id="userBean" scope="session" class="display.javabean.userBean"></jsp:useBean>
<jsp:useBean id="cancelarBean" scope="request" class="display.javabean.cancelarBean"></jsp:useBean>

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
	
	List<ReservaInfantilDTO> reservasInfantil = cancelarBean.getReservasInfantil();
	List<ReservaFamiliarDTO> reservasFamiliar = cancelarBean.getReservasFamiliar();
	List<ReservaAdultosDTO> reservasAdultos = cancelarBean.getReservasAdultos();
	
	if (reservasInfantil == null || reservasFamiliar == null || reservasAdultos == null){
%>
		<jsp:forward page="../../../index.jsp" />
<%	}else{ %> 

<!DOCTYPE html>
<html>
		<head>
				<meta charset="UTF-8">
				<title>Cancelar Reserva</title>
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/comun.css">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/table.css">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/footer_header.css">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/aceptar_boton.css">
		</head>
		<body>
			<h2>Cancelar Reserva</h2>
			<p class="mensaje" id="message"><%= mensajeNextPage %> </p>				
			<form id="submit" method="post" action="<%= request.getAttribute("nextPage")%>">
				<p>
					Seleccione una de las siguientes reservas:
				</p>
				
				 <%  if (reservasInfantil.isEmpty()){ %>
				 		<p> No se han encontrado reservas infantiles </p>
				  <% }else{ %>
				
				<table>
				<caption> <strong> Reservas Infantiles </strong> </caption>
							<thead>
							  <tr>
							    <th></th>
							    <th>Fecha y hora</th>
							    <th>Modalidad</th>
							    <th>Duración</th>
							    <th>Participantes infantiles</th>
							    <th>Descuento</th>
							    <th>Precio</th>
							    <th>Pista</th>
							  </tr>
							</thead>
							<tbody>
							
							<% for (ReservaInfantilDTO reserva : reservasInfantil){ %>
					
							  <tr>
							    <td><input type="radio" name="reserva" value="<%= reserva.getIdReserva()%>" required></td>
							    <td><%= reserva.getFecha() %></td>
							    <td><%= reserva.getModalidad() %></td>
							    <td><%= reserva.getDuracion() %> minutos</td>
							    <td><%= reserva.getParticipantesInfantiles() %></td>
							    <td><%= reserva.getDescuento() %> %</td>
							    <td><%= reserva.getPrecio() %> €</td>
							    <td><%= reserva.getIdPista() %></td>
							  </tr>
						<% } %>
							</tbody>
						</table>
					<% } %>
				
				<%  if (reservasFamiliar.isEmpty()){ %>
				 		<p> No se han encontrado reservas familiares </p>
				  <% }else{ %>
				
				<table>
				<caption> <strong> Reservas Familiares </strong> </caption>
							<thead>
							  <tr>
							    <th></th>
							    <th>Fecha y hora</th>
							    <th>Modalidad</th>
							    <th>Duración</th>
							    <th>Participantes infantiles</th>
							    <th>Participantes adultos</th>
							    <th>Descuento</th>
							    <th>Precio</th>
							    <th>Pista</th>
							  </tr>
							</thead>
							<tbody>
							
							<% for (ReservaFamiliarDTO reserva : reservasFamiliar){ %>
					
							  <tr>
							    <td><input type="radio" name="reserva" value="<%= reserva.getIdReserva()%>" required></td>
							    <td><%= reserva.getFecha() %></td>
							    <td><%= reserva.getModalidad() %></td>
							    <td><%= reserva.getDuracion() %> minutos</td>
							    <td><%= reserva.getParticipantesInfantiles() %></td>
							    <td><%= reserva.getParticipantesAdultos() %></td>
							    <td><%= reserva.getDescuento() %> %</td>
							    <td><%= reserva.getPrecio() %> €</td>
							    <td><%= reserva.getIdPista() %></td>
							  </tr>
						<% } %>
							</tbody>
						</table>
					<% } %>
				
				<%  if (reservasAdultos.isEmpty()){ %>
				 		<p> No se han encontrado reservas adultos </p>
				  <% }else{ %>
				
				<table>
				<caption> <strong> Reservas Adultos </strong> </caption>
							<thead>
							  <tr>
							    <th></th>
							    <th>Fecha y hora</th>
							    <th>Modalidad</th>
							    <th>Duración</th>
							    <th>Participantes adultos</th>
							    <th>Descuento</th>
							    <th>Precio</th>
							    <th>Pista</th>
							  </tr>
							</thead>
							<tbody>
							
							<% for (ReservaAdultosDTO reserva : reservasAdultos){ %>
					
							  <tr>
							    <td><input type="radio" name="reserva" value="<%= reserva.getIdReserva()%>" required></td>
							    <td><%= reserva.getFecha() %></td>
							    <td><%= reserva.getModalidad() %></td>
							    <td><%= reserva.getDuracion() %> minutos</td>
							    <td><%= reserva.getParticipantesAdultos() %></td>
							    <td><%= reserva.getDescuento() %> %</td>
							    <td><%= reserva.getPrecio() %> €</td>
							    <td><%= reserva.getIdPista() %></td>
							  </tr>
						<% } %>
							</tbody>
						</table>
						<% } %>
						
						
						<% if (!(reservasInfantil.isEmpty() && reservasFamiliar.isEmpty() && reservasAdultos.isEmpty())){ %>
							
							<div class="aceptar">
								<input type="submit" value="Confirmar">
							</div>						
						<%} %>
						</form>
						
	<jsp:include page="/include/volver.jsp" />
	<jsp:include page="/include/footer.jsp" />
</body>
</html>

<%
	}
}
%>