<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="business.reserva.*, java.util.List, java.util.ArrayList"%>
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
	
%>

<!DOCTYPE html>
<html>
		<head>
				<meta charset="UTF-8">
				<title>Consultar Pistas</title>
		</head>
		<body>
			<p id="message"><%= mensajeNextPage %></p>
			
				<form action="/index.jsp" method="post">
					<p>
						Elija el filtro de su consulta:
					</p>
					
					<select id="filtro" name="filtro" onChange="mostrar(this.value);">
					
					<option value="tipo"> Tipo de pista </option>
					
					<option value="fecha">Fecha de reserva</option>
					
					<option value="karts_inf">Número mínimo de karts infantiles</option>
					
					<option value="karts_adult">Número mínimo de karts adultos</option>
					
					</select>
				</form>
						
				
				<div id="tipo" style="display: none;">
			    	<h2>Escriba el tipo de la pista</h2>
					<form id="formConsultaTipo" method="post" action="/WebProyectoPW/ConsultarPistas">
				        <p>Tipo:<br/>
						<input type="text" name="tipo" id="tipo" value= <%=request.getAttribute("tipo")%>required>
				        <input type="submit" name="send" value="Enviar" />
			   		</form>
				</div>
				
				<div id="fecha" style="display: none;">
			    	<h2>Escriba la fecha de la reserva</h2>
					<form id="formConsultaFecha" method="post" action="/WebProyectoPW/ConsultarPistas">
				        <p>Fecha:<br/>
						<input type="datetime-local" name="fecha" id="fecha" value= <%=request.getAttribute("fecha")%>required>
				        <input type="submit" name="send" value="Enviar" />
			   		</form>
				</div>
				
				<div id="karts_inf" style="display: none;">
			    	<h2>Escriba el mínimo de karts infantiles</h2>
					<form id="formConsultaMinKartsInf" method="post" action="/WebProyectoPW/ConsultarPistas">
				        <p>Mínimo de karts infantiles:<br/>
						<input type="text" name="min_karts_inf" id="min_karts_inf" value= <%=request.getAttribute("min_karts_inf")%>required>
				        <input type="submit" name="send" value="Enviar" />
			   		</form>
				</div>												

				<div id="karts_adult" style="display: none;">
			    	<h2>Escriba el mínimo de karts adultos</h2>
					<form id="formConsultaMinKartsAdult" method="post" action="/WebProyectoPW/ConsultarPistas">
				        <p>Mínimo de karts adultos:<br/>
						<input type="text" name="min_karts_adult" id="min_karts_adult" value= <%=request.getAttribute("min_karts_adult")%>required>
				        <input type="submit" name="send" value="Enviar" />
			   		</form>
				</div>				

				<form id="volver" method="post" action="/WebProyectoPW">
					<input type="submit" value="Volver">
				</form>
		</body>
</html>

<%

}
%>