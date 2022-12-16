<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="business.reserva.BonoDTO, java.util.List, java.util.ArrayList"%>
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
	
	List<BonoDTO> bonos = (List<BonoDTO>) request.getAttribute("bonos");
	
	//Caso 3: Si se accede de forma forzosa por url
	if (bonos == null){ 
%>
		<jsp:forward page="../../../index.jsp" />
		
<% 	}else{ %>

<!DOCTYPE html>
<html>
		<head>
				<meta charset="UTF-8">
				<title>Listado Bonos</title>
		</head>
		<body>
				<p id="message"><%= mensajeNextPage %> </p>
				<form id="submit" method="post" action="/WebProyectoPW/ReservaBono">
				
				<%if (bonos.isEmpty()){ %>
					<p> No dispone de bonos </p>
				<% }else{%>
				<table>
							<thead>
							  <tr>
							    <th></th>
							    <th>Bono ID</th>
							    <th>Tipo Bono</th>
							    <th>Numero de sesiones</th>
							    <th>Fecha Caducidad</th>
							  </tr>
							</thead>
							<tbody>
							
							<% for (BonoDTO bono : bonos){ %>
							  <tr>
							    <td><input type="radio" name="bono" value="<%= bono.getBonoId()%>" required>
							    <input type="hidden" name="<%= bono.getBonoId()%>" value="<%= bono.getTipo()%>"></td>
							    <td><%= bono.getBonoId() %></td>
							    <td><%= bono.getTipo() %></td>
							    <td><%= bono.getSesiones() %></td>
							   	<%if (bono.getFechaCaducidad().contentEquals("01/01/1970")){%>
							   		 <td> <i>Sin reservas</i></td>
							   	<% }else{ %>
							   		<td><%= bono.getFechaCaducidad() %></td>
							   	<% } %>
							   
							  </tr>
						<% } %>
							</tbody>
						</table>
						<br>
								<input type="submit" value="Confirmar">
						</form>
						<br>
					<%} %>
						<form id="volver" method="post" action="/WebProyectoPW">
								<input type="submit" value="Volver">
						</form>
		</body>
</html>

<%
	}
}
%>