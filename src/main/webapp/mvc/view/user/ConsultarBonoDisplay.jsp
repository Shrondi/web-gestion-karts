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
	<jsp:include page="/include/encabezado.jsp" />

		<head>
				<meta charset="UTF-8">
				<title>Crear Bono</title>
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/comun.css">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/footer_header.css">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/aceptar_boton.css">
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/fieldset_funciones.css">				
				<link rel="stylesheet" type="text/css" href="/WebProyectoPW/css/reservas.css">												

		</head>
		<body>
			<h2>Crear Bono</h2>
					<fieldset>
						<legend>Seleccione el campo</legend>	
						<form id="formBono" method="post" action="/WebProyectoPW/CrearBono">
							<p> Tipo de bono: </p>
							<input type="radio" name="tipoBono" id="infantil" value="INFANTIL" required>
							<label for="infantil">Bono Infantil</label> <br>
							
							<input type="radio" name="tipoBono" id="familiar" value="FAMILIAR" required>
							<label for="familiar">Bono Familiar</label> <br>
							
							<input type="radio" name="tipoBono" id="adultos" value="ADULTOS" required>
							<label for="adultos">Bono Adulto</label> <br>

							<div class="aceptar">	
								<input type="submit" value="Crear Bono">
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