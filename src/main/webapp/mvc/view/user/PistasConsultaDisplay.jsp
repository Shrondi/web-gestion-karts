<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="business.pista.PistaDTO, java.util.List, java.util.ArrayList"%>
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
	
	List<PistaDTO> pistas = (List<PistaDTO>) request.getAttribute("ListaPistas");
	
	//Caso 3: Si se accede de forma forzosa por url
	if (pistas == null){ 
%>
		<jsp:forward page="../../../index.jsp" />
		
<% 	}else{ %>

<!DOCTYPE html>
<html>
	<jsp:include page="/include/encabezado.jsp" />

		<head>
				<meta charset="UTF-8">
				<title>Listado Pistas</title>
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/comun.css">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/footer_header.css">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/aceptar_boton.css">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/table.css">
			
		</head>
		<body>
			<h2>Consultar Pistas</h2>
			<p class="mensaje" id="message"><%= mensajeNextPage %> </p>
				<table>
					<caption><strong>Listado de Pistas</strong></caption>
					<thead>
					  <tr>
					    <th>Nombre de la Pista</th>
					    <th>Capacidad máxima</th>
					    <th>Número de karts infantiles disponibles</th>
					    <th>Número de karts adultos disponibles</th>
					  </tr>
					</thead>
					<tbody>
					
					<% for (PistaDTO pista : pistas){ %>
			
					  <tr>
					    <td><%= pista.getNombre() %></td>
					    <td><%= pista.getMaxAmmount() %></td>
					    <td><%= pista.getAsocAmmountInf() %></td>
					    <td><%= pista.getAsocAmmountAdult() %></td>
					  </tr>
					<% } %>
					</tbody>
				</table>
				<br>
				</div>
			<br>
			
			<div class="volver">		
				<form id="volver" method="post" action="/WebProyectoPW/ConsultarPistas">
					<input type="submit" value="Volver">
				</form>
			</div>
	<jsp:include page="/include/footer.jsp" />
</body>
</html>

<%
	}
}
%>