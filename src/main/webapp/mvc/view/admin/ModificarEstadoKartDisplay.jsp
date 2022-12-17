<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="business.kart.*, java.util.List, java.util.ArrayList"%>
<jsp:useBean id="userBean" scope="session" class="display.javabean.userBean"></jsp:useBean>

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
	
	List<KartDTO> ListaKarts = (List<KartDTO>) request.getAttribute("ListaKarts");
	
	//Caso 3: Si se accede de forma forzosa por url
	if (ListaKarts == null){
%>
		<jsp:forward page="../../../index.jsp" />
<%	}else{ %>

<!DOCTYPE html>
<html>
		<head>
				<meta charset="UTF-8">
				<title>Modificar estado de los karts</title>
		</head>
		<body>
				<p id="message"><%= mensajeNextPage %> </p>
				
				<div>
				<form id="form1" method="post" action="/WebProyectoPW/ModificarEstadoKart">
				<p>
					Elija el kart cuyo estado desea modificar.
				</p>
				
				<% if (ListaKarts.isEmpty()){ %>
				 		<p> No se han encontrado karts </p>
				 		
				<% }else{ %>
				
					<table>
					<caption> <strong> Karts:</strong> </caption>
								<thead>
								  <tr>
								  	<th></th>
								    <th>ID del Kart</th>
								    <th>Tipo de kart</th>
								    <th>Estado</th>
								    <th>Pista asociada</th>
								  </tr>
								</thead>
								<tbody>
								
								<% for (KartDTO kart : ListaKarts){ %> 			
								 
								  <tr>
								    <td><input type="radio" name="kart" id="kart" value="<%= kart.getId() %>" required></td>
								    <td><%= kart.getId() %></td>
								    <td><%= kart.geType() %></td>
								    <td><%= kart.getStatus() %></td>
								    <td><%= kart.getPista() %></td>
								    				   	
								  </tr>
							
								<% } %>
							
								</tbody>
							</table>
						<% } %>											
				</form>					
							
				<form id="form2" method="post" action="/WebProyectoPW/ModificarEstadoKart">
				<p>
					Elija el estado al que pasa el kart
				</p>
					<select name="estado">
						<option value="DISPONIBLE">DISPONIBLE</option>
						<option value="RESERVADO">RESERVADO</option>
						<option value="MANTENIMIENTO">MANTENIMIENTO</option>									
					</select> 
						
				</form>
				
				<button id="submit">Confirmar</button>		
						
				<script type="text/javascript" src="/WebProyectoPW/js/SubmitForms.js"></script>
								
				<%--  <input type="submit" value="Confirmar" onclick="submitForms()" /> --%>
				
				<br>
				</div>
				
				<form id="volver" method="post" action="/WebProyectoPW">
						<input type="submit" value="Volver">
				</form>
		</body>
</html>

<%

	}
}
%>