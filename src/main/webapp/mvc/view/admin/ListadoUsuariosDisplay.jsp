<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="business.usuario.UsuarioDTO, java.util.List, java.util.ArrayList"%>
<jsp:useBean id="userBean" scope="session" class="display.javabean.userBean"></jsp:useBean>

<%

//Caso 1: El usuario esta logado y es admin
 if (userBean.equals(null) || userBean.getCorreo() != null || userBean.getAdmin() == true){ 
	 
	 List<UsuarioDTO> usuarios = (List<UsuarioDTO>) request.getAttribute("usuarios");
	 
	//Caso 2: Si se accede de manera forzosa por la url
	if (usuarios == null ){
%>
		<jsp:forward page="../../../index.jsp" />
		
<%	}else{ %> 



<!DOCTYPE html>
<html>
		<head>
				<meta charset="UTF-8">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/table.css">
		</head>
		<body>
						
				<table>
					<caption> <strong> Usuarios Registrados </strong> </caption>
							<thead>
							  <tr>
							    <th>Correo</th>
							    <th>Nombre</th>
							    <th>Apellidos</th>
							    <th>Fecha Nacimiento</th>
							    <th>Fecha Inscripción</th>
							    <th>Antigüedad</th>
							    <th>Número de reservas completadas</th>
							    <th>¿Administrador?</th>
							  </tr>
							</thead>
							<tbody>
							
							<% for (UsuarioDTO usuario : usuarios){ %>
					
							  <tr>
							  	<td><%= usuario.getCorreo() %></td>
							  	<td><%= usuario.getNombre() %></td>
							  	<td><%= usuario.getApellidos() %></td>
							  	<td><%= usuario.getFechaNacimiento() %></td>
							  	<td><%= usuario.getFechaInscripcion() %></td>
							  	<td><%= usuario.getAntiguedad() %> meses</td>
							  	<td><%= usuario.getReservas() %></td>
							  	<% if (usuario.getAdmin()){ %>
							  		<td>Sí</td>
							  	<% }else{ %>
							  		<td>No</td>
							  	 <%} %>
							  </tr>
						<% } %>
							</tbody>
						</table>
					<% } %>
				
		</body>
</html>

<%
}
%>