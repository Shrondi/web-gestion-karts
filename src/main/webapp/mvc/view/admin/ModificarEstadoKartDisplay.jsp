<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="business.kart.*, java.util.List, java.util.ArrayList"%>
<%@ page errorPage="include/errorPage.jsp" %>
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
	<jsp:include page="/include/encabezado.jsp" />

		<head>
				<meta charset="UTF-8">
				<title>Modificar estado de los karts</title>
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/comun.css">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/footer_header.css">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/aceptar_boton.css">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/table.css">

		</head>
		<body>
			<h2>Modificar estado de Karts</h2>
			<p class="mensaje" id="message"><%= mensajeNextPage %> </p>
			<form id="formModificarEstadoKart" method="post" action="/WebProyectoPW/ModificarEstadoKart">
				<div>
					<p> 
						<label for="kart"></label>
							<%  if (ListaKarts.isEmpty()){ %>
						 		<p> No se han encontrado karts </p>
						  	<% }else{ %>
								<p> Seleccione los karts cuyo estados desee modificar</p>
									<table>
										<caption> <strong> Listado de Karts </strong> </caption>
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
						<p> Seleccione el nuevo estado para el kart </p>						
						<p>		
							<label for="estado"> Estado:  </label>
							<select name="estado" id="estado" required>
								<option value="">...</option>
								<option value="DISPONIBLE">DISPONIBLE</option>
								<option value="MANTENIMIENTO">MANTENIMIENTO</option>
							</select>
						</p>
				</div>
				
				<div class="aceptar">
					<input type="submit" value="Confirmar">	
				</div>
			</form>
			
	<jsp:include page="/include/volver_admin.jsp" />
	<jsp:include page="/include/footer.jsp" />
</body>
</html>

<%

	}
}
%>