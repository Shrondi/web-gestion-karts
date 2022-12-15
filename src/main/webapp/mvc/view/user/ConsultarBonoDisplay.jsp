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
	
%>

<!DOCTYPE html>
<html>
		<head>
				<meta charset="UTF-8">
				<title>Crear Bono</title>
		</head>
		<body>
						<form id="formBono" method="post" action="/WebProyectoPW/CrearBono">
								<div>
								
										<p> Seleccione el tipo de bono que desea: </p>
										<p> Tipo de bono: </p>
										<input type="radio" name="tipoBono" id="infantil" value="INFANTIL" required>
										<label for="infantil">Bono Infantil</label> <br>
										
										<input type="radio" name="tipoBono" id="familiar" value="FAMILIAR" required>
										<label for="familiar">Bono Familiar</label> <br>
										
										<input type="radio" name="tipoBono" id="adultos" value="ADULTOS" required>
										<label for="adultos">Bono Adulto</label> <br>
										
								</div>	
						
								<input type="submit" value="Crear Bono">
						</form>
						<form id="volver" method="post" action="/WebProyectoPW">
								<input type="submit" value="Volver">
						</form>
		</body>
</html>


<%

}
%>