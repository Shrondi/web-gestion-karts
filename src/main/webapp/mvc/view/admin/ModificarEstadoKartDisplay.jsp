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
	String tipo="";
	String pista_asociada = "";
			
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
			<form id="formModificarEstadoKart" method="post" action="/WebProyectoPW/ModificarEstadoKart">
				<div>
						<p id="message"><%= mensajeNextPage %></p>
						<p> 
						
							<label for="kart"></label>
								<%  if (ListaKarts.isEmpty()){ %>
						 			<p> No se han encontrado karts </p>
						  		<% }else{ %>
						
										<p> Seleccione el nuevo estado para el kart </p>
										<table>
										<caption> <strong> Karts </strong> </caption>
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
														
														 <% if(kart.geType() == true){ %>
																<% tipo = "Niños"; %>
														<% }else{  %>
																<% tipo = "Adultos"; %>
										
														<% } %>
														
														
														<% if(kart.getPista() == null){ %>
																<% pista_asociada = "Sin pista asociada"; %>
														<% }else{  %>
																<% pista_asociada= kart.getPista(); %>
										
														<% } %>
														
														  <tr>
														    <td><input type="radio" name="kart" value="<%= kart.getId()%>" required></td>
														    <td><%= kart.getId() %></td>
														    <td><%= tipo %></td>
														    <td><%= kart.getStatus() %></td>
														    <td><%= pista_asociada %></td>
														  
														  </tr>
													<% } %>
														</tbody>
													</table>
													<% } %>
													<br>
						</p>				
						<p> Seleccione el nuevo estado para el kart </p>						
						<p>		
								<label for="estado"> Estado:  </label>
								<select name="estado" id="estado" required>
									<option value="">...</option>
									<option value="DISPONIBLE">DISPONIBLE</option>
									<option value="RESERVADO">RESERVADO</option>
									<option value="MANTENIMIENTO">MANTENIMIENTO</option>
								</select>
						</p>
				</div>	
				<input type="submit" value="Confirmar">
			</form>
			
			<form id="volver" method="post" action="/WebProyectoPW">
					<input type="submit" value="Volver">
			</form>
		</body>
</html>

<%

	}
}
%>