<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="business.reserva.*, java.util.List, java.util.ArrayList"%>
<jsp:useBean id="userBean" scope="session" class="display.javabean.userBean"></jsp:useBean>

<%

//Caso 1: No esta logado o si lo esta si es admin
if (userBean == null || userBean.getCorreo().isEmpty() || userBean.getAdmin() == true) {
	
%>
	<jsp:forward page="../../index.jsp" />
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
				<title>Pedir fechas maxima y minima de las reservas del usuario</title>
		</head>
		<body>
				<p id="message"><%= mensajeNextPage %></p>
						<legend> Escriba la fecha de inicio y de fin</legend>
						<form id="formReservasUsuario" method="post" action="/WebProyectoPW/ConsultarReservas">
								<div>
										<p>
												<label for="fechaInicio">Fecha Inicio: </label>
												<input type="date" name="fechaInicio" id="fechaInicio" placeholder="dd/mm/yyyy" required>
										</p>
										<p>
												<label for="fechaFin">Fecha Fin: </label>
												<input type="date" name="fechaFin" id="fechaFin" placeholder="dd/mm/yyyy" required>
										</p>
										
								</div>	
								<input type="submit" value="Aceptar">
						</form>
						<%-- <script type="text/javascript" src="/WebProyectoPW/js/ModifyValidation.js"></script> --%>
						<form id="volver" method="post" action="/WebProyectoPW">
								<input type="submit" value="Volver">
						</form>
		</body>
</html>

<%

}
%>