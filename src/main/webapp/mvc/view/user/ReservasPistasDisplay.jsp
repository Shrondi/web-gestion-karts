<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="business.pista.PistaDTO, java.util.List, java.util.ArrayList"%>
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
	
	List<PistaDTO> pistas = (List<PistaDTO>) request.getSession().getAttribute("ListaPistas");
	
	//Caso 3: Si se accede de forma forzosa por url
	if (pistas == null){ 
%>
		<jsp:forward page="../../../index.jsp" />
		
<% 	}else{ %>

<!DOCTYPE html>
<html>
		<head>
				<meta charset="UTF-8">
				<title>Elegir Pista Reserva</title>
		</head>
		<body>
				<p id="message"><%= mensajeNextPage %> </p>
				<form id="submit" method="post" action="<%= request.getAttribute("nextPage")%>">
				<table>
							<thead>
							  <tr>
							    <th></th>
							    <th>Nombre de la Pista</th>
							    <th>Capacidad maxima</th>
							    <th>Numero de karts infantiles</th>
							    <th>Numero de karts adultos</th>
							  </tr>
							</thead>
							<tbody>
							
							<% for (PistaDTO pista : pistas){ %>
					
							  <tr>
							    <td><input type="radio" name="pista" value="<%= pista.getNombre()%>" required></td>
							    <td><%= pista.getNombre() %></td>
							    <td><%= pista.getMaxAmmount() %></td>
							    <td><%= pista.getAsocAmmountInf() %></td>
							    <td><%= pista.getAsocAmmountAdult() %></td>
							  </tr>
						<% } %>
							</tbody>
						</table>
						<br>
								<input type="submit" value="Confirmar">
						</form>
						<br>
						<form id="volver" method="post" action="/WebProyectoPW/ReservaIndividual">
								<input type="submit" value="Volver">
						</form>
		</body>
</html>

<%
	}
}
%>