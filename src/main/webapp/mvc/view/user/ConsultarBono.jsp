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
	
	String mensaje = "";
	if (request.getSession().getAttribute("fecha") != null){
		mensaje = "Se ha recuperado la informacion de una reserva sin realizar";
	}

%>

<!DOCTYPE html>
<html>
		<head>
				<meta charset="UTF-8">
				<title>Crear Bono</title>
		</head>
		<body>
				<p id="message"><%= mensajeNextPage %></p>
				<p id="mensaje"><%= mensaje %></p>
						<form id="formBono" method="post" action="/WebProyectoPW/ReservaIndividual">
								<div>
								
										
										<p> Tipo de bono: </p>
										<input type="radio" name="tipoBono" id="infantil" value="INFANTIL" required>
										<label for="infantil">Bono Infantil</label> <br>
										
										<input type="radio" name="tipoBono" id="familiar" value="FAMILIAR" required>
										<label for="familiar">Bono Familiar</label> <br>
										
										<input type="radio" name="tipoBono" id="adultos" value="ADULTOS" required>
										<label for="adultos">Bono Adulto</label> <br>
										
								</div>	
						
								<input type="submit" value="Continuar">
						</form>
						<form id="volver" method="post" action="/WebProyectoPW">
								<input type="submit" value="Volver">
						</form>
		</body>
</html>


<%

}
%>