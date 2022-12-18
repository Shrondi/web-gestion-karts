<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="business.reserva.*, java.util.List, java.util.ArrayList"%>
<jsp:useBean id="userBean" scope="session" class="display.javabean.userBean"></jsp:useBean>
<jsp:useBean id="rangoBean" scope="request" class="display.javabean.rangoBean"></jsp:useBean>


<%
//Caso 1: No esta logado o si lo esta si es admin
if (userBean == null || userBean.getCorreo().isEmpty() || userBean.getAdmin() == true) {
	
%>
	<jsp:forward page="../../../index.jsp" />
<%
//Caso 2: El usuario esta logado y no es admin
}else{
	
	List<ReservaInfantilDTO> reservasInfantilPasadas = rangoBean.getReservasInfantilPasadas();
	List<ReservaInfantilDTO> reservasInfantilFuturas = rangoBean.getReservasInfantilFuturas();
	
	List<ReservaFamiliarDTO> reservasFamiliarPasadas = rangoBean.getReservasFamiliarPasadas();
	List<ReservaFamiliarDTO> reservasFamiliarFuturas = rangoBean.getReservasFamiliarFuturas();
	
	List<ReservaAdultosDTO> reservasAdultosPasadas = rangoBean.getReservasAdultosPasadas();
	List<ReservaAdultosDTO> reservasAdultosFuturas = rangoBean.getReservasAdultosPasadas();
	
	if (reservasInfantilPasadas == null || reservasInfantilFuturas == null || reservasFamiliarPasadas == null || reservasFamiliarFuturas == null || reservasAdultosPasadas == null || reservasAdultosFuturas == null){
%>
		<jsp:forward page="../../../index.jsp" />
<%	}else{ %> 



<!DOCTYPE html>
<html>
		<head>
				<meta charset="UTF-8">
				<title>Consultar Reservas</title>
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/table_reservas.css">
		</head>
		<body>
			
				<table>
				<caption> <strong> Reservas Infantiles </strong> </caption>
							<thead>
							  <tr>
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
						
							<% for (ReservaInfantilDTO reservaInfantilDTO : reservasInfantilFuturas){ %>
								  <tr>
								    <td><%= reservaInfantilDTO.getFecha() %></td>
								    <td><%= reservaInfantilDTO.getModalidad() %></td>
								    <td><%= reservaInfantilDTO.getDuracion() %> min</td>
								    <td><%= reservaInfantilDTO.getParticipantesInfantiles() %></td>
								    <td><%= reservaInfantilDTO.getDescuento() %> %</td>
								    <td><%= reservaInfantilDTO.getPrecio() %> €</td>
								    <td><%= reservaInfantilDTO.getIdPista() %></td>
								  </tr>
							<% } %>
						
							<% for (ReservaInfantilDTO reservaInfantilDTO : reservasInfantilPasadas){ %>
					
							  <tr class="pasadas">
							    <td><%= reservaInfantilDTO.getFecha() %></td>
							    <td><%= reservaInfantilDTO.getModalidad() %></td>
							    <td><%= reservaInfantilDTO.getDuracion() %> min</td>
							    <td><%= reservaInfantilDTO.getParticipantesInfantiles() %></td>
							    <td><%= reservaInfantilDTO.getDescuento() %> %</td>
							    <td><%= reservaInfantilDTO.getPrecio() %> €</td>
							    <td><%= reservaInfantilDTO.getIdPista() %></td>
							  </tr>
						<% } %>
							</tbody>
						</table>
				
				<table>
				<caption> <strong> Reservas Familiares </strong> </caption>
							<thead>
							  <tr>
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
							
							<% for (ReservaFamiliarDTO reservaFamiliarDTO : reservasFamiliarFuturas){ %>
								  <tr>
								    <td><%= reservaFamiliarDTO.getFecha() %></td>
								    <td><%= reservaFamiliarDTO.getModalidad() %></td>
								    <td><%= reservaFamiliarDTO.getDuracion() %> min</td>
								    <td><%= reservaFamiliarDTO.getParticipantesInfantiles() %></td>
								    <td><%= reservaFamiliarDTO.getParticipantesAdultos() %></td>
								    <td><%= reservaFamiliarDTO.getDescuento() %> %</td>
								    <td><%= reservaFamiliarDTO.getPrecio() %> €</td>
								    <td><%= reservaFamiliarDTO.getIdPista() %></td>
								  </tr>
					
							<%} %>
							
							<% for (ReservaFamiliarDTO reservafamiliarDTO : reservasFamiliarPasadas){ %>
					
							  <tr class="pasadas">
							    <td><%= reservafamiliarDTO.getFecha() %></td>
							    <td><%= reservafamiliarDTO.getModalidad() %></td>
							    <td><%= reservafamiliarDTO.getDuracion() %> min</td>
							    <td><%= reservafamiliarDTO.getParticipantesInfantiles() %></td>
							    <td><%= reservafamiliarDTO.getParticipantesAdultos() %></td>
							    <td><%= reservafamiliarDTO.getDescuento() %> %</td>
							    <td><%= reservafamiliarDTO.getPrecio() %> €</td>
							    <td><%= reservafamiliarDTO.getIdPista() %></td>
							  </tr>
						<% } %>
							</tbody>
				</table>


				<%if (reservasAdultosFuturas == null) %>
				<table>
				<caption> <strong> Reservas Adultos </strong> </caption>
							<thead>
							  <tr>
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
							
							<% for (ReservaAdultosDTO reservaAdultosDTO : reservasAdultosFuturas){ %>
								  <tr>
								    <td><%= reservaAdultosDTO.getFecha() %></td>
								    <td><%= reservaAdultosDTO.getModalidad() %></td>
								    <td><%= reservaAdultosDTO.getDuracion() %> min</td>
								    <td><%= reservaAdultosDTO.getParticipantesAdultos() %></td>
								    <td><%= reservaAdultosDTO.getDescuento() %> %</td>
								    <td><%= reservaAdultosDTO.getPrecio() %> €</td>
								    <td><%= reservaAdultosDTO.getIdPista() %></td>
								  </tr>
							<% } %>
						
							<% for (ReservaAdultosDTO reservaAdultosDTO : reservasAdultosPasadas){ %>
					
							  <tr class="pasadas">
							    <td><%= reservaAdultosDTO.getFecha() %></td>
							    <td><%= reservaAdultosDTO.getModalidad() %></td>
							    <td><%= reservaAdultosDTO.getDuracion() %> min</td>
							    <td><%= reservaAdultosDTO.getParticipantesAdultos() %></td>
							    <td><%= reservaAdultosDTO.getDescuento() %> %</td>
							    <td><%= reservaAdultosDTO.getPrecio() %> €</td>
							    <td><%= reservaAdultosDTO.getIdPista() %></td>
							  </tr>
							<% } %>
							</tbody>
				</table>
				<%} %>
			
		</body>
</html>

<%
}
%>