<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="business.reserva.BonoDTO, java.util.List, java.util.ArrayList"%>
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
	
	List<BonoDTO> bonos = (List<BonoDTO>) request.getAttribute("bonos");
	
	//Caso 3: Si se accede de forma forzosa por url
	if (bonos == null){ 
%>
		<jsp:forward page="../../../index.jsp" />
		
<% 	}else{ %>

<!DOCTYPE html>
<html>
	<jsp:include page="/include/encabezado.jsp" />

		<head>
				<meta charset="UTF-8">
				<title>Listado Bonos</title>
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/comun.css">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/footer_header.css">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/aceptar_boton.css">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/table.css">								
				
		</head>
		<body>
		<%String nextPage = (String)request.getAttribute("nextPage"); %>
		<%if (nextPage.contentEquals("/WebProyectoPW/ReservaBono")){ %>
			<h2>Hacer Reserva</h2>
		<% }else{%>
			<h2>Cancelar Bono</h2>
		<%}%>
				<p class="mensaje" id="message"><%= mensajeNextPage %> </p>
				<%if (bonos.isEmpty()){ %>
					<p> No dispone de bonos </p>
				<% }else{%>
					<form id="submit" method="post" action="<%= request.getAttribute("nextPage") %>">
					<table>
						<caption><strong>Listado de bonos creados</strong></caption>
								<thead>
								  <tr>
								    <th></th>
								    <th>Bono ID</th>
								    <th>Tipo Bono</th>
								    <th>Numero de sesiones</th>
								    <th>Fecha Caducidad</th>
								  </tr>
								</thead>
								<tbody>
								
								<% for (BonoDTO bono : bonos){ %>
								  <tr>
								    <td><input type="radio" name="bono" value="<%= bono.getBonoId()%>" required>
								    <td><%= bono.getBonoId() %></td>
								    <td><%= bono.getTipo() %></td>
								    <td><%= bono.getSesiones() %></td>
								   	<%if (bono.getFechaCaducidad().contentEquals("01/01/1970")){%>
								   		 <td> <i>Sin reservas</i></td>
								   	<% }else{ %>
								   		<td><%= bono.getFechaCaducidad() %></td>
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
						<br>
					<%} %>
					
	<jsp:include page="/include/volver_usuario.jsp" />
	<jsp:include page="/include/footer.jsp" />
	
	</body>
</html>

<%
	}
}
%>