<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="business.pista.*, java.util.List, java.util.ArrayList"%>
<%@ page errorPage="include/errorPage.jsp" %>
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
	String estado="";
	//Caso 3: Si se accede de forma forzosa por url
	if (ListaPistas == null){
%>
		<jsp:forward page="../../../index.jsp" />
<%	}else{ %>

<!DOCTYPE html>
<html>
	<jsp:include page="/include/encabezado.jsp" />

		<head>
				<meta charset="UTF-8">
				<title>Modificar estado de las pistas</title>
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/comun.css">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/footer_header.css">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/aceptar_boton.css">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/table.css">
		</head>
		<body>
			<h2>Modificar estado de Pistas</h2>
			<p class="mensaje" id="message"><%= mensajeNextPage %> </p>
						
			<div>
			<form id="form1" method="post" action="/WebProyectoPW/ModificarEstadoPista">
				<p>Seleccione la pista cuyo estado desee modificar</p>
				
				<% if (ListaPistas.isEmpty()){ %>
				 		<p> No se han encontrado pistas </p>
				<% }else{ %>
				
					<table>
					<caption> <strong> Listado de Pistas</strong> </caption>
							<thead>
							  <tr>
							    <th></th>
							    <th>Nombre</th>
							    <th>Estado</th>
							    <th>Dificultad</th>
							    <th>Máximo de karts</th>
							  </tr>
							</thead>
							<tbody>
								
							<% for (PistaDTO pista : ListaPistas){ %> 			
								<tr>
								    <td><input type="checkbox" name="pista" id="kart" value="<%= pista.getNombre() %>"></td>
								    <td><%= pista.getNombre() %></td>
								 <% if(pista.getEstado() == true){ %>
										<% estado = "Disponible"; %>
								<% }else{  %>
										<% estado = "No disponible"; %>
								<% } %>
									
								    <td><%= estado %></td>
								    <td><%= pista.getDificulty() %></td>
								    <td><%= pista.getMaxAmmount() %></td>
								  </tr>
							
							<% } %>
							
						</tbody>
					</table>
				<% } %>	
				
				<div class="aceptar">
					<input type="submit" value="Confirmar">											
				</div>	
			</form>													
		</div>
				
	<jsp:include page="/include/volver_admin.jsp" />
	<jsp:include page="/include/footer.jsp" />
</body>
</html>

<%

	}
}
%>