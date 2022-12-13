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
	
	PistaDTO nombre_pista = (PistaDTO)request.getAttribute("nombre_pista");
	PistaDTO estado = (PistaDTO)request.getAttribute("estado");
	PistaDTO dificultad = (PistaDTO)request.getAttribute("dificultad");
	PistaDTO max_karts = (PistaDTO)request.getAttribute("max_karts");

	//Caso 3: Si el usuario accede a traves de la url
	if (nombre_pista == null && estado == null && dificultad == null && max_karts == null){ %>
		<jsp:forward page="../../../index.jsp" />
		
<%	}else{ %> 

<!DOCTYPE html>
<html>
		<head>
				<meta charset="UTF-8">
				<title>Creación de pista</title>
		</head>
		<body>
			<form id="formCrearKart" method="post" action="/CrearPista">
				<div>
						<p>
								<label for="nombrePista">Nombre: </label>
								<input type="text" name="nombrePista" id="nombrePista" value="Nombre" required="required">
						</p>
						<p>
								<label for="estadoPista">Estado: </label>
								<input type="text" name="estadoPista" id="estadoPista" value="Estado" required="required">
						</p>
						<p>
								<label for="dificultad">Dificultad: </label>
								<input type="text" name="dificultad" id="dificultad" value="Dificultad" required="required">
						</p>
						<p>
								<label for="max_karts">Número máximo de karts: </label>
								<input type="number" name="max_karts" id="max_karts" value="Max de karts" required="required">
						</p>											
				</div>	
				<input type="submit" value="Crear pista">
			</form>
			
			<form id="volver" method="post" action="/CrearPista">
					<input type="submit" value="Volver">
			</form>
		</body>
</html>

<%
	}
}
%>