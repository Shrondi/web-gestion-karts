<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page errorPage="include/errorPage.jsp" %>
<jsp:useBean id="userBean" scope="session" class="display.javabean.userBean"></jsp:useBean>

<%
if(userBean == null || userBean.getCorreo().equals("")){
	
	request.setAttribute("message", "Debe acceder o registrarse");
	%>
	<jsp:forward page="../../index.jsp"/>
	<%	
}

%>
<!DOCTYPE html>
<html>
		<head>
				<meta charset="UTF-8">
				<title>Vista Cliente</title>
		</head>
		<body>
			<%
				String message = (String)request.getAttribute("message");
				if(message == null) message = "";
			%>
			<%=message%>
			
			<fieldset>
				<legend>Vista Cliente</legend>
				
				<p>
					¡Bienvenido <%=userBean.getNombre()%>!<br>
					Son las <%= Utils.dateToString(new java.util.Date()) %><br>
					Se registro <%= userBean.getFechaInscripcion()%>
					Lleva con nosotros  <%= %>
					La fecha de su proxima reserva es <%= %>
				</p>
				
				<ul>
					<li>
						<form method="post" action="/WebProyectoPW-main/CrearReservaIndividual">
							<input type="submit" name="option" class="option" value="Hacer reserva Individual">
						</form>
					</li>
				
					<li>
						<form method="post" action="/WebProyectoPW-main/CrearReservaBono">
							<input type="submit" name="option" class="option" value="Hacer reserva Bono">
						</form>
					</li>
					
					<li>
						<form method="get" action="/WebProyectoPW-main/ListadoReservasFuturas">
							<input type="submit" name="option" class="option" value="Listado de reservas futuras">
						</form>
					</li>
					
					<li>
						<form method="get" action="/WebProyectoPW-main/ListadoResrvasFechaPista">
							<input type="submit" name="option" class="option" value="Listado de reservas segun fecha y pista">
						</form>
					</li>		
					
					
					<li>
						<form method="post" action="/WebProyectoPW-main/ModificarReservaIndividual">
							<input type="submit" name="option" class="option" value="Modificar reserva Individual">
						</form>
					</li>						
											
					<li>
						<form method="post" action="/WebProyectoPW-main/ModificarReservaBono">
							<input type="submit" name="option" class="option" value="Modificar reserva Bono">
						</form>
					</li>									
							
					<li>
						<form method="post" action="/WebProyectoPW-main/EliminarReservaIndividual">
							<input type="submit" name="option" class="option" value="Cancelar reserva Individual">
						</form>
					</li>	

					<li>
						<form method="post" action="/WebProyectoPW-main/EliminarReservaBono">
							<input type="submit" name="option" class="option" value="Cancelar reserva Bono">
						</form>
					</li>	
										
				</ul>
				
				<form method="post" action="../mvc/control/ModificacionController.jsp">
					<input type="submit" value="Modificar datos">
				</form>
				
				<form method="post" action="../mvc/control/LogoutController.jsp">
					<input type="submit" value="Desconectar">
				</form>
				
			</fieldset>
		</body>
</html>