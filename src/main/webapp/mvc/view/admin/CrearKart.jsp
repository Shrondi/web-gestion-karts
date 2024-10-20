<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="business.kart.*"%>
<%@ page errorPage="include/errorPage.jsp" %>
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
				<title>Crear Karts</title>
				<link rel="stylesheet" type="text/css" href="/web-gestion-karts/css/comun.css">
				<link rel="stylesheet" type="text/css" href="/web-gestion-karts/css/fieldset.css">
				<link rel="stylesheet" type="text/css" href="/web-gestion-karts/css/footer_header.css">
				<link rel="stylesheet" type="text/css" href="/web-gestion-karts/css/aceptar_boton.css">
		</head>
		<body>
			<h2> Crear Kart</h2>
			<p class="mensaje" id="message"><%= mensajeNextPage %></p>
			<fieldset>
				<legend> Rellene los siguientes datos </legend>
				<form id="formCrearKart" method="post" action="/web-gestion-karts/CrearKart">
					<p> 
						<label for="estadoKart"> Estado:  </label>
						<select name="estadoKart" id="estadoKart" required>
							<option value="">...</option>
							<option value="<%= Estado.DISPONIBLE%>">Disponible</option>
							<option value="<%= Estado.RESERVADO%>">Reservado</option>
							<option value="<%= Estado.MANTENIMIENTO%>">Mantenimiento</option>
						</select>
					</p>
					<p> 
						<label for="tipoKart"> Tipo:  </label>
						<select name="tipoKart" id="tipoKart" required>
							<option value="">...</option>
							<option value="true">Infantil</option>
							<option value="false">Adulto</option>
						</select>
					</p>
					
					<div class="aceptar">
						<input type="submit" value="Crear kart">
					</div>
				</form>
			</fieldset>
			
	<jsp:include page="/include/volver_admin.jsp" />
	<jsp:include page="/include/footer.jsp" />
</body>
</html>

<%
}
%>