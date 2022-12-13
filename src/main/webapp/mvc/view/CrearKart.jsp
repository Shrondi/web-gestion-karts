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
	
	KartDTO tipo = (KartDTO)request.getAttribute("tipo");
	KartDTO estado = (KartDTO)request.getAttribute("estado");
	
	//Caso 3: Si el usuario accede a traves de la url
	if (tipo == null && estado == null){ %>
		<jsp:forward page="../../../index.jsp" />
		
<%	}else{ %> 

<!DOCTYPE html>
<html>
		<head>
				<meta charset="UTF-8">
				<title>Creación de kart</title>
		</head>
		<body>
			<form id="formCrearKart" method="post" action="/CrearKart">
				<div>
						<p>
								<label for="tipoKart">Tipo: </label>
								<input type="text" name="tipoKart" id="tipoKart" value="Tipo" required="required">
						</p>
						<p>
								<label for="estadoKart">Estado: </label>
								<input type="text" name="estadoKart" id="estadoKart" value="Estado" required="required">
						</p>
				</div>	
				<input type="submit" value="Crear kart">
			</form>
			
			<form id="volver" method="post" action="/CrearKart">
					<input type="submit" value="Volver">
			</form>
		</body>
</html>

<%
	}
}
%>