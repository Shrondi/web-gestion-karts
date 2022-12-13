<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="business.reserva.*, java.util.List, java.util.ArrayList"%>
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
				<title>Consultar Pistas por Tipo</title>
		</head>
		<body>
				<p id="message"><%= mensajeNextPage %></p>
						<p> Escriba el tipo de la pista</p>
						<form id="formConsultaTipoUsuario" method="post" action="/WebProyectoPW/ConsultarPistas">
								<div>
										<p>
												<label for="tipo">Tipo: </label>
												<input type="text" name="tipo" id="tipo" value= <%=request.getAttribute("tipo")%>required>
										</p>									
								</div>	
								<input type="submit" value="Aceptar">
						</form>
						
						<form id="volver" method="post" action="/WebProyectoPW">
								<input type="submit" value="Volver">
						</form>
		</body>
</html>

<%

}
%>