<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="business.kart.KartDTO, java.util.List, java.util.ArrayList"%>
<%@ page errorPage="include/errorPage.jsp" %>
<jsp:useBean id="userBean" scope="session" class="display.javabean.userBean"></jsp:useBean>
<jsp:useBean id="asociarBean" scope="request" class="display.javabean.asociarBean"></jsp:useBean>

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
	
	List<KartDTO> karts = asociarBean.getListadoKarts();
	
	//Caso 3: Si se accede de forma forzosa por url
	if (karts == null){ 
%>
		<jsp:forward page="../../../index.jsp" />
		
<% 	}else{ %>

<!DOCTYPE html>
<html>
	<jsp:include page="/include/encabezado.jsp" />

		<head>
				<meta charset="UTF-8">
				<title>Listado Karts</title>
				<link rel="stylesheet" type="text/css" href="/web-gestion-karts/css/comun.css">
				<link rel="stylesheet" type="text/css" href="/web-gestion-karts/css/footer_header.css">
				<link rel="stylesheet" type="text/css" href="/web-gestion-karts/css/aceptar_boton.css">
				<link rel="stylesheet" type="text/css" href="/web-gestion-karts/css/table.css">		
		
		</head>
		<body>
			<h2>Asociar Karts a Pistas</h2>
			<p class="mensaje" id="message"><%= mensajeNextPage %> </p>
			<p> Seleccione los karts a asociar a la pista seleccionada</p>
			<form id="submit" method="post" action="/web-gestion-karts/AsociarKartPista">
				
				<%if (karts.isEmpty()){ %>
					<p> No hay karts disponibles sin asignar </p>
				<%}else{ %>
				<table>
					<caption><strong>Listado de Karts </strong></caption>
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
				<div class="aceptar">
					<input type="submit" value="Confirmar">
				</div>
				</form>

			<div class="volver_asociar">
				<form id="volver" method="post" action="/web-gestion-karts/AsociarKartPista">
						<input type="submit" value="Volver">
				</form>
			</div>
			
		<jsp:include page="/include/footer.jsp" />				
</body>
</html>

<%
		}
	}
}
%>