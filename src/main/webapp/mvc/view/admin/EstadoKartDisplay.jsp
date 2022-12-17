<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="business.kart.*, java.util.List, java.util.ArrayList"%>
<jsp:useBean id="userBean" scope="session" class="display.javabean.userBean"></jsp:useBean>

<!DOCTYPE html>
<html>
		<head>
				<meta charset="UTF-8">
				<title>Modificar Estado Kart</title>
		</head>
		<body>
						<p> Elija el estado al que quiere que pase el kart</p>
						<form id="formModificarEstado" method="post" action="/WebProyectoPW/ModificarEstadoKart">
								<div>
								
								<select name="estado">
									<option value="DISPONIBLE">DISPONIBLE</option>
									<option value="RESERVADO">RESERVADO</option>
									<option value="MANTENIMIENTO">MANTENIMIENTO</option>									
								</select>
										
								</div>	
								<input type="submit" value="Aceptar">
						</form>
						
						<form id="volver" method="post" action="/WebProyectoPW">
								<input type="submit" value="Volver">
						</form>
		</body>
</html>
