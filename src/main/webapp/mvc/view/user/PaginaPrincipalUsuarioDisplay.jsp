<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page errorPage="include/errorPage.jsp" %>
<%@ page import ="java.util.Date, business.reserva.AbstractReservaDTO" %>
<jsp:useBean id="userBean" scope="session" class="display.javabean.userBean"></jsp:useBean>

<%
if(userBean == null || userBean.getCorreo().equals("") || userBean.getAdmin() == true){
	
	%>
	<jsp:forward page="../../index.jsp"/>
	<%	
}
%>
<!DOCTYPE html>
<html>

	<p><strong>¡Bienvenido usuario <%=userBean.getNombre()%> <%=userBean.getApellidos() %>!</strong></p>
			<p>Hoy es <%= new java.util.Date() %></p>
			
			<%if (userBean.getAntiguedad() <= 0){ %>
				<p>Se registró hoy</p>
			<%}else{ %>
				<p>Se registró <%= userBean.getFechaInscripcion()%></p>
			<%} %>
			
			<%if (userBean.getAntiguedad() <= 0){ %>
				<p>Lleva con nosotros menos de un mes</p>
			<%}else{ %>
				<p>Lleva con nosotros <%= userBean.getAntiguedad()%> meses</p>
			<%} %>
			<%AbstractReservaDTO abstractReservaDTO = (AbstractReservaDTO)request.getAttribute("ProximaReserva"); %>
			<%if (abstractReservaDTO.getIdPista() != null){ %>
				<p>Su próxima reserva es el <%= abstractReservaDTO.getFecha()%> en la pista <%= abstractReservaDTO.getIdPista() %></p>
			<%}else{ %>
				<p> No tiene reservas futuras </p>
			<%} %>
			
		<form action="/WebProyectoPW/FuncionesUsuario" method="post">
			<p><input type="submit" value="Acceso a operaciones"></p>
		</form>									
	</body>
</html>