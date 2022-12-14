<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="business.kart.*"%>
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
		<head>
				<meta charset="UTF-8">
				<title>Creación de Karts</title>
		</head>
		<body>
			<form id="formCrearKart" method="post" action="/WebProyectoPW/CrearKart">
				<div>
						<p id="message"><%= mensajeNextPage %></p>
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
				</div>	
				<input type="submit" value="Crear kart">
			</form>
			
			<form id="volver" method="post" action="/WebProyectoPW">
					<input type="submit" value="Volver">
			</form>
		</body>
</html>

<%
}
%>