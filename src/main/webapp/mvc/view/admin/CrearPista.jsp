<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="business.pista.*"%>
<jsp:useBean id="userBean" scope="session" class="display.javabean.userBean"></jsp:useBean>

<%

//Caso 1: No esta logado o si lo esta y no es admin
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
%>

<!DOCTYPE html>
<html>
	<jsp:include page="/include/encabezado.jsp" />

		<head>
				<meta charset="UTF-8">
				<title>Crear Pista</title>
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/comun.css">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/fieldset.css">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/footer_header.css">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/aceptar_boton.css">

		</head>
		<body>
			<h2> Crear Pista</h2>
			<p class="mensaje" id="message"><%= mensajeNextPage %></p>
			<fieldset>
				<legend> Rellene los siguientes datos </legend>
				<form id="formCrearPista" method="post" action="/WebProyectoPW/CrearPista">
					<p>
						<label for="nombrePista">Nombre: </label>
						<input type="text" name="nombrePista" id="nombrePista" placeholder="Pista 1" required="required">
						<div id="nameErrorText"></div>
					</p>
					<p> 
						<label for="estado"> Estado:  </label>
						<select name="estado" id="estado" required>
							<option value="">...</option>
							<option value="true">Disponible</option>
							<option value="false">No Disponible</option>
						</select>
					</p>
					<p> 
						<label for="dificultad"> Dificultad:  </label>
						<select name="dificultad" id="dificultad" required>
							<option value="">...</option>
							<option value="<%= Dificultad.INFANTIL%>">Infantil</option>
							<option value="<%= Dificultad.FAMILIAR%>">Familiar</option>
							<option value="<%= Dificultad.ADULTOS%>">Adultos</option>
						</select>
					</p>
					<p>
						<label for="max_karts">Número máximo de karts: </label>
						<input type="number" name="max_karts" id="max_karts" min="5" max="20" placeholder="5" required="required">
					</p>
					
					<div class="aceptar">
						<input type="submit" value="Crear pista">
					</div>											
				</form>
			</fieldset>
			
	<script type="text/javascript" src="/WebProyectoPW/js/PistaCreateValidation.js"></script>
	
	<jsp:include page="/include/volver_admin.jsp" />
	<jsp:include page="/include/footer.jsp" />

</body>
</html>

<%
}
%>