<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
				<title>Reserva Individual</title>
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/comun.css">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/footer_header.css">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/aceptar_boton.css">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/fieldset_funciones.css">				
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/validacion.css">	
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/reservas.css">												
		</head>
		<body>
			<h2>Hacer Reserva</h2>
				<p class="mensaje" id="message"><%= mensajeNextPage %></p>
					<fieldset>
						<legend>Rellene los siguientes campos</legend>	
						<form id="formReserva" method="post" action="/WebProyectoPW/ReservaIndividual">
							
							<label for="fecha">Fecha reserva: </label>
							<input id="fecha" type="datetime-local" name="fecha" id="fecha" required>
							<div class="validar" id="textErrorFecha"></div>
							
							<p> Tipo reserva: </p>
							<div class="tipo_reserva">
								<input type="radio" name="tipoReserva" id="infantil" value="INFANTIL" required>
								<label for="infantil">Reserva Infantil</label> <br>
								
								<input type="radio" name="tipoReserva" id="familiar" value="FAMILIAR" required>
								<label for="familiar">Reserva Familiar</label> <br>
								
								<input type="radio" name="tipoReserva" id="adultos" value="ADULTOS" required>
								<label for="adultos">Reserva Adulto</label> <br>
							</div>
							<p></p>
							<div class="integrantes">
								<label id="labelninios" for="numeroNinios"> Número de participantes infantiles:  </label>
								<input type="number" name="numeroNinios" id="numeroNinios" min="0" max="20" step="1" placeholder="1" required>
								<p> </p>
								<label id ="labeladultos" for="numeroAdultos">Número de participantes adultos: </label>
								<input type="number" name="numeroAdultos" id="numeroAdultos" min="0" max="20" step="1" placeholder="1" required>
							</div>
							
							<p> </p>
							<label for="duracion"> Duración:  </label>
							<select name="duracion" id="duracion" required>
							  <option value="">...</option>
							  <option value="60">60 minutos</option>
							  <option value="90">90 minutos</option>
							  <option value="120">120 minutos</option>
							</select>
											
							<div class="aceptar">	
								<input type="submit" value="Continuar">
							</div>
						</form>
					</fieldset>
					
					<script type="text/javascript" src="/WebProyectoPW/js/ReservaIndividualValidation.js"></script>
	<jsp:include page="/include/volver_usuario.jsp" />
	<jsp:include page="/include/footer.jsp" />
	
	</body>
</html>
<%

}
%>