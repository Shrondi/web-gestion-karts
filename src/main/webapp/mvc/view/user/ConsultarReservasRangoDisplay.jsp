<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="business.reserva.*, java.util.List, java.util.ArrayList"%>
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
	
%>

<!DOCTYPE html>
<html>
	<jsp:include page="/include/encabezado.jsp" />

		<head>
				<meta charset="UTF-8">
				<title>Consultar Reservas</title>
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/comun.css">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/footer_header.css">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/aceptar_boton.css">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/fieldset.css">				
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/validacion.css">	
		</head>
		<body>
			<h2>Consultar Reserva</h2>
				<p class="mensaje" id="message"><%= mensajeNextPage %></p>
					<p> Escriba la fecha de inicio y de fin para consultar reservas entre dichas fechas</p>
					<form id="formReservasUsuario" method="post" action="/WebProyectoPW/ConsultarReservas">
								<fieldset>
								
									<label for="fechaInicio">Fecha Inicio: </label>
									<input type="datetime-local" name="fechaInicio" id="fechaInicio" value="<%=request.getAttribute("fechaInicio")%>" required><br></br>
										
									<label for="fechaFin">Fecha Fin: </label>
									<input type="datetime-local" name="fechaFin" id="fechaFin" value="<%=request.getAttribute("fechaFin")%>" required><br></br>
									<div class="validar" id="dateErrorText"></div>
								
							<div class="aceptar">
								<input type="submit" value="Nueva busqueda">
							</div>
							</fieldset>
						</form>
				<script type="text/javascript" src="/WebProyectoPW/js/ConsultarReservasRango.js"></script>			
								
			<% if (request.getAttribute("reservasInfantilPasadas") != null){ %>
					<jsp:include page="ReservasRangoDisplay.jsp" />  
			<%} %>
			
				<jsp:include page="/include/volver_usuario.jsp" />
				<jsp:include page="/include/footer.jsp" />
		</body>
</html>

<%

}
%>