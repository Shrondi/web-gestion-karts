<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="business.pista.Dificultad, java.util.List, java.util.ArrayList"%>
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
		<head>
				<meta charset="UTF-8">
				<title>Consultar Pistas</title>
		</head>
		<body>
			<p id="message"><%= mensajeNextPage %></p>
			
					<p>
						Elija el filtro de su consulta:
					</p>
					
			<form id="formConsultTipo" method="post" action="/WebProyectoPW/ConsultarPistas">
			
					<select id="filtro" name="filtro" multiple required onChange="mostrar(this.value);">
						<option value="tipo"> Tipo de pista </option>
						<option value="minKarts">Número mínimo de karts</option>										
					</select>
					<div class="filtro"></div>		
				
				
				<div id="tipo" class="tipo">
			    	<h2>Escriba el tipo de la pista</h2>
					<label for="tipo"> Dificultad:  </label>
								<select name="tipo" id="tipo">
									<option value="">...</option>
									<option value="<%= Dificultad.INFANTIL%>">Infantil</option>
									<option value="<%= Dificultad.FAMILIAR%>">Familiar</option>
									<option value="<%= Dificultad.ADULTOS%>">Adultos</option>
								</select>
					<input type="submit" value="Continuar">	
				</div>
				
				<div id="min_karts" class="min_karts">
			    	<h2>Escriba el mínimo de karts infantiles</h2>
						<label for="numeroNinios"> Mínimo de karts infantiles:  </label>
						<input type="number" name="min_karts_inf" id="min_karts_inf" min="0" max="20" step="1" required value="0">
						<p> </p>
						<label for="numeroAdultos">Mínimo de karts adultos: </label>
						<input type="number" name="min_karts_adult" id="min_karts_adult" min="0" max="20" step="1" required value="0">
				        <p> </p>
						<input type="submit" value="Continuar">			
				</div>			

			</form>
			
				<form id="volver" method="post" action="/WebProyectoPW">
					<input type="submit" value="Volver">
				</form>
		</body>
</html>

<%

}
%>