<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="business.pista.Dificultad, java.util.List, java.util.ArrayList"%>
<%@ page errorPage="include/errorPage.jsp" %>
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
				<title>Consultar Pistas</title>
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/comun.css">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/footer_header.css">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/aceptar_boton.css">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/fieldset_funciones.css">				
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/reservas.css">				

		</head>
		<body>
			<h2>Consultar Pistas Disponibles</h2>
				<p class="mensaje" id="message"><%= mensajeNextPage %></p>
					<fieldset>
						<legend>Rellene los siguientes campos</legend>	
						<form id="formConsultTipo" method="post" action="/WebProyectoPW/ConsultarPistas">
					
							
							<p>Elija el filtro de su búsqueda, puede seleccionar ambos:</p>
							<select id="filtro" name="filtro" multiple="multiple" required onChange="mostrar(this.value);">
								<option value="tipo"> Tipo de pista </option>
								<option value="minKarts">Número mínimo de karts</option>										
							</select>
							<div class="filtro"></div>		
							
							<div id="tipo" class="tipo">
						    	<p>Seleccione el tipo de pista:</p>
								<label for="tipo"> Tipo:  </label>
											<select name="tipo" id="tipo">
												<option value="">...</option>
												<option value="<%= Dificultad.INFANTIL%>">Infantil</option>
												<option value="<%= Dificultad.FAMILIAR%>">Familiar</option>
												<option value="<%= Dificultad.ADULTOS%>">Adultos</option>
											</select>
							        <div class="aceptar">
										<input type="submit" value="Continuar">			
							        </div>
							</div>
							
							<div id="min_karts" class="min_karts">
						    	<p>Indique el mínimo número de karts:</p>
									<label for="numeroNinios"> Mínimo de karts infantiles:  </label>
									<input type="number" name="min_karts_inf" id="min_karts_inf" min="0" max="20" step="1" required value="0">
									<p> </p>
									<label for="numeroAdultos">Mínimo de karts adultos: </label>
									<input type="number" name="min_karts_adult" id="min_karts_adult" min="0" max="20" step="1" required value="0">
							        <p> </p>
							        
							        <div class="aceptar">
										<input type="submit" value="Continuar">			
							        </div>
							</div>			
					</form>
				</fieldset>
	<jsp:include page="/include/volver_usuario.jsp" />
	<jsp:include page="/include/footer.jsp" />
	
	</body>
</html>

<%

}
%>