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
	
	List<ReservaInfantilDTO> reservasInfantilPasadas = (List<ReservaInfantilDTO>) request.getAttribute("reservasInfantilPasadas");
	List<ReservaInfantilDTO> reservasInfantilFuturas = (List<ReservaInfantilDTO>) request.getAttribute("reservasInfantilFuturas");
	
	List<ReservaFamiliarDTO> reservasFamiliarPasadas = (List<ReservaFamiliarDTO>) request.getAttribute("reservasFamiliarPasadas");
	List<ReservaFamiliarDTO> reservasFamiliarFuturas = (List<ReservaFamiliarDTO>) request.getAttribute("reservasFamiliarFuturas");
	
	List<ReservaAdultosDTO> reservasAdultosPasadas = (List<ReservaAdultosDTO>) request.getAttribute("reservasAdultosPasadas");
	List<ReservaAdultosDTO> reservasAdultosFuturas = (List<ReservaAdultosDTO>) request.getAttribute("reservasAdultosFuturas");
	
	if (reservasInfantilPasadas == null || reservasInfantilFuturas == null || reservasFamiliarPasadas == null || reservasFamiliarFuturas == null || reservasAdultosPasadas == null || reservasAdultosFuturas == null){
%>
		<jsp:forward page="../../../index.jsp" />
<%	}else{ %> 



<!DOCTYPE html>
<html>
		<head>
				<meta charset="UTF-8">
				<title>Consultar Reservas</title>
		</head>
		<body>
				<p>
					RESERVAS PASADAS:
				</p>
				
				 <%  if (reservasInfantilPasadas.isEmpty()){ %>
				 		<p> No se han encontrado reservas infantiles </p>
				  <% }else{ %>
				
				<table>
				<caption> <strong> Reservas Infantiles </strong> </caption>
							<thead>
							  <tr>
							    <th>Fecha y hora</th>
							    <th>Duracion</th>
							    <th>Participantes infantiles</th>
							    <th>Descuento</th>
							    <th>Precio</th>
							    <th>Pista</th>
							  </tr>
							</thead>
							<tbody>
							
							<% for (ReservaInfantilDTO reserva : reservasInfantilPasadas){ %>
					
							  <tr>
							    <td><%= reserva.getFecha() %></td>
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
				
				<%  if (reservasFamiliarPasadas.isEmpty()){ %>
				 		<p> No se han encontrado reservas familiares </p>
				  <% }else{ %>
				
				<table>
				<caption> <strong> Reservas Familiares </strong> </caption>
							<thead>
							  <tr>
							    <th>Fecha y hora</th>
							    <th>Duracion</th>
							    <th>Participantes infantiles</th>
							    <th>Participantes adultos</th>
							    <th>Descuento</th>
							    <th>Precio</th>
							    <th>Pista</th>
							  </tr>
							</thead>
							<tbody>
							
							<% for (ReservaFamiliarDTO reserva : reservasFamiliarPasadas){ %>
					
							  <tr>
							    <td><%= reserva.getFecha() %></td>
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
				
				<%  if (reservasAdultosPasadas.isEmpty()){ %>
				 		<p> No se han encontrado reservas de adultos </p>
				  <% }else{ %>
				
				<table>
				<caption> <strong> Reservas Adultos </strong> </caption>
							<thead>
							  <tr>
							    <th>Fecha y hora</th>
							    <th>Duracion</th>
							    <th>Participantes adultos</th>
							    <th>Descuento</th>
							    <th>Precio</th>
							    <th>Pista</th>
							  </tr>
							</thead>
							<tbody>
							
							<% for (ReservaAdultosDTO reserva : reservasAdultosPasadas){ %>
					
							  <tr>
							    <td><%= reserva.getFecha() %></td>
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
						
				<p>
					RESERVAS FUTURAS:
				</p>
				
				 <%  if (reservasInfantilFuturas.isEmpty()){ %>
				 		<p> No se han encontrado reservas infantiles </p>
				  <% }else{ %>
				
				<table>
				<caption> <strong> Reservas Infantiles </strong> </caption>
							<thead>
							  <tr>
							    <th>Fecha y hora</th>
							    <th>Duracion</th>
							    <th>Participantes infantiles</th>
							    <th>Descuento</th>
							    <th>Precio</th>
							    <th>Pista</th>
							  </tr>
							</thead>
							<tbody>
							
							<% for (ReservaInfantilDTO reserva : reservasInfantilFuturas){ %>
					
							  <tr>
							    <td><%= reserva.getFecha() %></td>
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
				
				<%  if (reservasFamiliarFuturas.isEmpty()){ %>
				 		<p> No se han encontrado reservas familiares </p>
				  <% }else{ %>
				
				<table>
				<caption> <strong> Reservas Familiares </strong> </caption>
							<thead>
							  <tr>
							    <th>Fecha y hora</th>
							    <th>Duracion</th>
							    <th>Participantes infantiles</th>
							    <th>Participantes adultos</th>
							    <th>Descuento</th>
							    <th>Precio</th>
							    <th>Pista</th>
							  </tr>
							</thead>
							<tbody>
							
							<% for (ReservaFamiliarDTO reserva : reservasFamiliarFuturas){ %>
					
							  <tr>
							    <td><%= reserva.getFecha() %></td>
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
				
				<%  if (reservasAdultosFuturas.isEmpty()){ %>
				 		<p> No se han encontrado reservas de adultos </p>
				  <% }else{ %>
				
				<table>
				<caption> <strong> Reservas Adultos </strong> </caption>
							<thead>
							  <tr>
							    <th>Fecha y hora</th>
							    <th>Duracion</th>
							    <th>Participantes adultos</th>
							    <th>Descuento</th>
							    <th>Precio</th>
							    <th>Pista</th>
							  </tr>
							</thead>
							<tbody>
							
							<% for (ReservaAdultosDTO reserva : reservasAdultosFuturas){ %>
					
							  <tr>
							    <td><%= reserva.getFecha() %></td>
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
		</body>
</html>

<%
	}
}
%>