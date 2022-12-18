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
	
	ReservaInfantilDTO reservaInfantil = (ReservaInfantilDTO) request.getAttribute("reservaInfantil");
	ReservaFamiliarDTO reservaFamiliar = (ReservaFamiliarDTO) request.getAttribute("reservaFamiliar");
	ReservaAdultosDTO reservaAdultos = (ReservaAdultosDTO) request.getAttribute("reservaAdultos");
	
	//Caso 3: Si el usuario accede a traves de la url
	if (reservaInfantil == null && reservaFamiliar == null && reservaAdultos == null){ %>
		<jsp:forward page="../../../index.jsp" />
		
<%	}else{ %> 

<!DOCTYPE html>
<html>
		<head>
				<meta charset="UTF-8">
				<title>Resumen Reserva</title>
		</head>
		<body>
				<p id="message"><%= mensajeNextPage %> </p>
				<form id="volver" method="post" action="/WebProyectoPW">
				
				<p>
					RESUMEN RESERVA <br>
				</p>
				
				 <%  if (reservaInfantil != null){ %>
				 		Modalidad: Individual <br>
				 		Tipo: Infantil <br>
					 	Fecha y hora: <%= reservaInfantil.getFecha() %> <br>
					 	Duracion: <%= reservaInfantil.getDuracion() %> <br>
					 	Participantes Infantiles: <%= reservaInfantil.getParticipantesInfantiles() %> <br>
					 	Descuento: <%= reservaInfantil.getDescuento() %> % <br>
					 	Precio: <%= reservaInfantil.getPrecio() %> € <br>
					 	Pista: <%= reservaInfantil.getIdPista() %> <br>
					 	
					 	<! –– Se puede hacer con el metodo toString <%= reservaInfantil.toString() %> ––>
					 	
					<% }else if (reservaFamiliar != null){%>
			
						Modalidad: Individual <br>
				 		Tipo: Familiar <br>
					 	Fecha y hora: <%= reservaFamiliar.getFecha() %> <br>
					 	Duracion: <%= reservaFamiliar.getDuracion() %> <br>
					 	Participantes Infantiles: <%= reservaFamiliar.getParticipantesInfantiles() %> <br>
					 	Participantes Adultos: <%= reservaFamiliar.getParticipantesAdultos() %> <br>
					 	Descuento: <%= reservaFamiliar.getDescuento() %> % <br>
					 	Precio: <%= reservaFamiliar.getPrecio() %> € <br>
					 	Pista: <%= reservaFamiliar.getIdPista() %> <br>
					 	
					<% }else if (reservaAdultos != null){%>
			
						Modalidad: Individual <br>
				 		Tipo: Adulto <br>
					 	Fecha y hora: <%= reservaAdultos.getFecha() %> <br>
					 	Duracion: <%= reservaAdultos.getDuracion() %> <br>
					 	Participantes Adultos: <%= reservaAdultos.getParticipantesAdultos() %> <br>
					 	Descuento: <%= reservaAdultos.getDescuento() %> % <br>
					 	Precio: <%= reservaAdultos.getPrecio() %> € <br>
					 	Pista: <%= reservaAdultos.getIdPista() %> <br>
					<% } %>
						
						
								<input type="submit" value="Volver">
						</form>
		</body>
</html>

<%
	}
}
%>