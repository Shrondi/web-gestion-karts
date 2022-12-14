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
		<head>
				<meta charset="UTF-8">
				<title>Crear Pista</title>
		</head>
		<body>
			<form id="formCrearKart" method="post" action="/WebProyectoPW/CrearPista">
				<div>
						<p id="message"><%= mensajeNextPage %></p>
						<p> Escriba los siguientes datos para crear una pista: </p>
						<p>
								<label for="nombrePista">Nombre: </label>
								<input type="text" name="nombrePista" id="nombrePista" placeholder="Pista 1" required="required">
						
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
				</div>	
				<input type="submit" value="Crear pista">
			</form>
			
			<form id="volver" method="post" action="/WebProyectoPW">
					<input type="submit" value="Volver">
			</form>
		</body>
</html>

<%
}
%>