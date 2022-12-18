<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page errorPage="include/errorPage.jsp" %>
<%@ page import ="java.util.Date, business.reserva.AbstractReservaDTO" %>
<jsp:useBean id="userBean" scope="session" class="display.javabean.userBean"></jsp:useBean>

<%
if(userBean == null || userBean.getCorreo().equals("") || userBean.getAdmin() == false){
	
	%>
	<jsp:forward page="../../index.jsp"/>
	<%	
}
%>
<!DOCTYPE html>
<html>

	<p><strong>¡Bienvenido Administrador <%=userBean.getNombre()%> <%=userBean.getApellidos() %>!</strong></p>
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
			
			<p> Se ha completado hasta la fecha de hoy <%= request.getAttribute("numeroReservas")%> reservas</p>
			
			<form action="/WebProyectoPW/mvc/view/admin/FuncionesAdministradorDisplay.jsp" method="post">
				<p><input type="submit" value="Acceso a operaciones"></p>
			</form>			
			
			<jsp:include page="/mvc/view/admin/ListadoUsuariosDisplay.jsp"/>				
	</body>
</html>