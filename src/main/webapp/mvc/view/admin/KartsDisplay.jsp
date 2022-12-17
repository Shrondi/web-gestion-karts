<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="business.kart.KartDTO, java.util.List, java.util.ArrayList"%>
<jsp:useBean id="userBean" scope="session" class="display.javabean.userBean"></jsp:useBean>
<jsp:useBean id="adminBean" scope="request" class="display.javabean.adminBean"></jsp:useBean>

<%

//Caso 1: No esta logado o si lo esta no es admin
if (userBean == null || userBean.getCorreo().isEmpty() || userBean.getAdmin() == false) {
	
%>
	<jsp:forward page="../../../index.jsp" />
<%
//Caso 2: El usuario esta logado y es admin
}else{
	
	String mensajeNextPage = (String) request.getAttribute("mensaje");
	
	if (mensajeNextPage == null) {
		mensajeNextPage = "";
	}
	
	List<KartDTO> karts = adminBean.getListadoKarts();
	
	//Caso 3: Si se accede de forma forzosa por url
	if (karts == null){ 
%>
		<jsp:forward page="../../../index.jsp" />
		
<% 	}else{ %>

<!DOCTYPE html>
<html>
		<head>
				<meta charset="UTF-8">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/table.css">
				<title>Listado Karts</title>
		</head>
		<body>
				<p id="message"><%= mensajeNextPage %> </p>
				<form id="submit" method="post" action="/WebProyectoPW/AsociarKartPista">
				
				<%if (karts.isEmpty()){ %>
					<p> No hay karts disponibles sin asignar </p>
				<%}else{ %>
				<table>
							<thead>
							  <tr>
							    <th></th>
							    <th>ID Kart</th>
							    <th>Tipo de Kart</th>
							  </tr>
							</thead>
							<tbody>
							
							<% for (KartDTO kart : karts){ %>
					
							  <tr>
							    <td><input type="checkbox" name="ids" value="<%= kart.getId()%>" ></td>
							    <td><%= kart.getId() %></td>
							    <%if (kart.geType()){ %>
							    	<td>INFANTIL</td>
							    <%}else{ %>
							    		<td>ADULTO</td>
							    <% } %>
							  </tr>
						<% } %>
							</tbody>
						</table>
						<br>
								<input type="submit" value="Confirmar">
						</form>
						<br>
						<form id="volver" method="post" action="/WebProyectoPW/AsociarKartPista">
								<input type="submit" value="Volver">
						</form>
		</body>
</html>

<%
		}
	}
}
%>