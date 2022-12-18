<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="business.reserva.*, java.util.List, java.util.ArrayList"%>
<%@ page errorPage="include/errorPage.jsp" %>
<jsp:useBean id="userBean" scope="session" class="display.javabean.userBean"></jsp:useBean>
<jsp:useBean id="cancelarBean" scope="request" class="display.javabean.cancelarBean"></jsp:useBean>

<%

//Caso 1: No esta logado o si lo esta si es admin
if (userBean == null || userBean.getCorreo().isEmpty() || userBean.getAdmin() == false) {
	
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
				<title>Consultar reservas para eliminar</title>
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/comun.css">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/fieldset.css">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/footer_header.css">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/aceptar_boton.css">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/validacion.css">
		</head>
		<body>
			<h2> Eliminar Reserva</h2>
			<p class="mensaje" id="message"><%= mensajeNextPage %></p>
			<fieldset>
				<legend> Rellene los siguientes datos</legend>
				<form id="formReservasUsuario" method="post" action="/WebProyectoPW/BorrarReservaIndividual">
					<p>
						<label for="fechaInicio">Fecha Inicio: </label>
						<input type="datetime-local" name="fechaInicio" id="fechaInicio" value="<%=cancelarBean.getFechaInicio()%>" required>
					</p>
					<p>
						<label for="fechaFin">Fecha Fin: </label>
						<input type="datetime-local" name="fechaFin" id="fechaFin" value="<%=cancelarBean.getFechaFin()%>" required>
						<div class="validar" id="dateErrorText"></div>
					</p>
					
					<div id="popup">
						<p> ¿Listar reservas con menos de 24 horas?: </p>
						<input type="radio" name="restriccion" id="si" value="true">
						<label for="si">Sí</label> <br>
				
						<input type="radio" name="restriccion" id="no" value="false">
						<label for="no">No</label> <br>
					</div>

					<input type="submit" value="Buscar">
				</form>

			</fieldset>	
			
			<script type="text/javascript" src="/WebProyectoPW/js/RangoFechasBorrar.js"> </script>
			<% if (cancelarBean.getReservasInfantil() != null){ %>
				<jsp:include page="/mvc/view/common/ReservasCancelarDisplay.jsp" > 
					<jsp:param name="nextPage" value="/WebProyectoPW/BorrarReservaIndividual" /> 
				</jsp:include>
				
			<%} %>
					
		<br>
		
	<jsp:include page="/include/footer.jsp" />
</body>
</html>

<%

}
%>