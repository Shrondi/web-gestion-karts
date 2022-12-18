<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="business.pista.*, java.util.List, java.util.ArrayList"%>
<jsp:useBean id="userBean" scope="session" class="display.javabean.userBean"></jsp:useBean>
<jsp:useBean id="asociarBean" scope="request" class="display.javabean.asociarBean"></jsp:useBean>

<%

//Caso 1: No esta logado o no es admin
if (userBean == null || userBean.getCorreo().isEmpty() || userBean.getAdmin() == false ) {
	
%>
	<jsp:forward page="../../../index.jsp" />
<%
//Caso 2: El usuario esta logado y no es admin
}else{
	
	String mensajeNextPage = (String) request.getAttribute("mensaje");
	
	if (mensajeNextPage == null) {
		mensajeNextPage = "";
	}
	
	List<PistaDTO> ListaPistas = asociarBean.getListadoPistas();
	
	//Caso 3: Si se accede de forma forzosa por url
	if (ListaPistas == null){
%>
		<jsp:forward page="../../../index.jsp" />
<%	}else{ %>

<!DOCTYPE html>
<html>
		<head>
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/table.css">
				<meta charset="UTF-8">
				<title>Modificar estado de las pistas</title>
		</head>
		<body>
				<p id="message"><%= mensajeNextPage %> </p>
				
				<div>
				<p>
					Elija la pista cuyo estado desea modificar:
				</p>
				
				<% if (ListaPistas.isEmpty()){ %>
				 		<p> No se han encontrado pistas </p>
				 		
				<% }else{ %>
				<form id="form1" method="post" action="/WebProyectoPW/ModificarEstadoPista">
					<table>
					<caption> <strong> Listado de pistas</strong> </caption>
								<thead>
								  <tr>
								    <th></th>
								    <th>Nombre</th>
								    <th>Estado</th>
								    <th>Dificultad</th>
								    <th>Maximo de karts</th>
								  </tr>
								</thead>
								<tbody>
								
								<% for (PistaDTO pista : ListaPistas){ %> 
								
								  <tr>
								    <td><input type="checkbox" name="pista" id="kart" value="<%= pista.getNombre() %>"></td>
								    <td><%= pista.getNombre() %></td>
								    <% if(pista.getEstado()){ %>
											<td>Disponible</td>
									<% }else{  %>
										<td>No disponible </td>
									<% } %>
								    <td><%= pista.getDificulty() %></td>
								    <td><%= pista.getMaxAmmount() %></td>				   	
								  </tr>
							
								<% } %>
							
								</tbody>
							</table>
							
						<input type="submit" value="Continuar">											
				</form>		
				<% } %>						
								
				</div>
				
				<form id="volver" method="post" action="/WebProyectoPW/FuncionesAdministrador">
						<input type="submit" value="Volver">
				</form>
		</body>
</html>

<%
	}
}
%>