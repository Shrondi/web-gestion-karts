<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="business.kart.*, java.util.List, java.util.ArrayList"%>
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
	
	List<KartDTO> ListaKarts = asociarBean.getListadoKarts();
			
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
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/table.css">
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
						<p id="message"><%= mensajeNextPage %></p>
						<p> 
						
						<p> Seleccione el nuevo estado para los karts seleccionados: </p>						
						<p>		
								<label for="estado"> Estado:  </label>
								<select name="estado" id="estado" required>
									<option value="">...</option>
									<option value="DISPONIBLE">DISPONIBLE</option>
									<option value="RESERVADO">RESERVADO</option>
									<option value="MANTENIMIENTO">MANTENIMIENTO</option>
								</select>
						</p>
						
								<%  if (ListaKarts.isEmpty()){ %>
						 			<p> No se han encontrado karts </p>
						  		<% }else{ %>
						
										<table>
										<caption> <strong> Lista de Karts </strong> </caption>
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
														    <td><input type="checkbox" name="kart" value="<%= kart.getId()%>"></td>
														    <td><%= kart.getId() %></td>
														    <% if(kart.geType() == true){ %>
																<td> INFANTIL </td>
															<% }else{  %>
																<td> ADULTO </td>
															<% } %>
														    <td><%= kart.getStatus() %></td>
														    <% if(kart.getPista() == null){ %>
																<td> <i>Sin pista asociada </i> </td>
															<% }else{  %>
																<td> <%= kart.getPista() %></td>
															<% } %>
														  
														  </tr>
													<% } %>
														</tbody>
													</table>
													<% } %>			
				</div>	
				
				<input type="submit" value="Confirmar">
			</form>
			
	<jsp:include page="/include/volver_admin.jsp" />
	<jsp:include page="/include/footer.jsp" />
</body>
</html>

<%
	}
}
%>