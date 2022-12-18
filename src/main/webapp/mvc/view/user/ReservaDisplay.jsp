<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="business.reserva.*, java.util.List, java.util.ArrayList"%>
<%@ page errorPage="include/errorPage.jsp" %>
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
	
	ReservaInfantilDTO reservaInfantil = (ReservaInfantilDTO) request.getAttribute("reservaInfantil");
	ReservaFamiliarDTO reservaFamiliar = (ReservaFamiliarDTO) request.getAttribute("reservaFamiliar");
	ReservaAdultosDTO reservaAdultos = (ReservaAdultosDTO) request.getAttribute("reservaAdultos");
	
	//Caso 3: Si el usuario accede a traves de la url
	if (reservaInfantil == null && reservaFamiliar == null && reservaAdultos == null){ %>
		<jsp:forward page="../../../index.jsp" />
		
<%	}else{ %> 

<!DOCTYPE html>
<html>
	<jsp:include page="/include/encabezado.jsp" />

		<head>
				<meta charset="UTF-8">
				<title>Resumen Reserva</title>
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/comun.css">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/footer_header.css">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/table.css">					
		</head>
		<body>
			<h2>Resumen Reserva</h2>
			<p class="mensaje" id="message"><%= mensajeNextPage %> </p>
				<table class="vertical">
					<caption><strong>Reserva realizada</strong></caption>
				 <%  if (reservaInfantil != null){ %>
				 		<tr><th>Modalidad</th>				 			
				 			<td><%= request.getAttribute("modalidad") %></td></tr>
						<tr><th>Tipo</th>
							<td>Infantil</td></tr>
						<tr><th>Pista</th>
					 		<td><%= reservaInfantil.getIdPista() %></td></tr>
					 	<tr><th>Fecha</th>
					 		<td><%= reservaInfantil.getFecha() %></td></tr>
						<tr><th>Duración</th>
							<td><%= reservaInfantil.getDuracion() %> min</td></tr>
						 <tr><th>Participantes infantiles</th>
						 	<td><%= reservaInfantil.getParticipantesInfantiles() %></td> </tr>
						 <tr><th>Participantes adultos</th>
						 	<td>0</td></tr>
						 <tr><th>Descuento aplicado</th>
						 	<td><%= reservaInfantil.getDescuento() %> %</td></tr>
						 <tr><th>Precio final</th>
						 	<td><%= reservaInfantil.getPrecio() %> €</td></tr>
					<% }else if (reservaFamiliar != null){%>
				 		<tr><th>Modalidad</th>				 			
				 			<td><%= request.getAttribute("modalidad") %></td></tr>
						<tr><th>Tipo</th>
							<td>Familiar</td></tr>
						<tr><th>Pista</th>
					 		<td><%= reservaFamiliar.getIdPista() %></td></tr>
					 	<tr><th>Fecha</th>
					 		<td><%= reservaFamiliar.getFecha() %></td></tr>
						<tr><th>Duración</th>
							<td><%= reservaFamiliar.getDuracion() %> min</td></tr>
						 <tr><th>Participantes infantiles</th>
						 	<td><%= reservaFamiliar.getParticipantesInfantiles() %></td></tr>
						 <tr><th>Participantes adultos</th>
						 	<td><%= reservaFamiliar.getParticipantesAdultos() %></td></tr>
						 <tr><th>Descuento aplicado</th>
						 	<td><%= reservaFamiliar.getDescuento() %> %</td></tr>
						 <tr><th>Precio final</th>
						 	<td><%= reservaFamiliar.getPrecio() %> €</td></tr>				 	
					<% }else if (reservaAdultos != null){%>
				 		<tr><th>Modalidad</th>				 			
				 			<td><%= request.getAttribute("modalidad") %></td></tr>
						<tr><th>Tipo</th>
							<td>Adultos</td></tr>
						<tr><th>Pista</th>
					 		<td><%= reservaAdultos.getIdPista() %></td></tr>
					 	<tr><th>Fecha</th>
					 		<td><%= reservaAdultos.getFecha() %></td></tr>
						<tr><th>Duración</th>
							<td><%= reservaAdultos.getDuracion() %> min</td></tr>
						 <tr><th>Participantes infantiles</th>
						 	<td>0</td></tr>
						 <tr><th>Participantes adultos</th>
						 	<td><%= reservaAdultos.getParticipantesAdultos() %></td></tr>
						 <tr><th>Descuento aplicado</th>
						 	<td><%= reservaAdultos.getDescuento() %> %</td> </tr>
						 <tr><th>Precio final</th>
						 	<td><%= reservaAdultos.getPrecio() %> €</td></tr>							 		
					<% } %>					
					
				</table>			

	<jsp:include page="/include/volver_usuario.jsp" />						
	<jsp:include page="/include/footer.jsp" />
</body>
</html>

<%
	}
}
%>