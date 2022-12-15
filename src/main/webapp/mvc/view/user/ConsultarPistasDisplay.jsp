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
					
					<select id="filtro" name="filtro" multiple onChange="mostrar(this.value);">
						<option value="tipo"> Tipo de pista </option>
						<option value="min_karts">Número mínimo de karts</option>										
					</select>
					<div class="filtro"></div>		
				</form>
				
				<div id="tipo" class="tipo">
			    	<h2>Escriba el tipo de la pista</h2>
					<form id="formConsultaTipo" method="post" action="/WebProyectoPW/ConsultarPistas">
				        <p>Tipo:<br/>
						<input type="text" name="tipo" id="tipo" value= <%=request.getAttribute("tipo")%>required>
				        <input type="submit" name="send" value="Enviar" />
			   		</form>
				</div>
				
				<div id="min_karts" class="min_karts">
			    	<h2>Escriba el mínimo de karts infantiles</h2>
					<form id="formConsultaMinKarts" method="post" action="/WebProyectoPW/ConsultarPistas">
				        <p>Mínimo de karts infantiles:<br/>
						<input type="text" name="min_karts_inf" id="min_karts_inf" value= <%=request.getAttribute("min_karts_inf")%>required>
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