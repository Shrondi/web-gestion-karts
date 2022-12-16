<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="java.util.Date, business.reserva.AbstractReservaDTO, data.DAO.reserva.ReservaDAO" %>
<jsp:useBean  id="userBean" scope="session" class="display.javabean.userBean"></jsp:useBean>

<%

if(userBean == null || userBean.getCorreo().equals("") || userBean.getAdmin() == true){
	
	%>
	<jsp:forward page="../../index.jsp"/>
	<%	
}else{
	
	//Se obtienen los parámetros de inicializacion del fichero web.xml
	String sqlProperties= application.getInitParameter("sqlproperties"); 
	java.io.InputStream myIO = application.getResourceAsStream(sqlProperties);
	java.util.Properties prop = new java.util.Properties();
	prop.load(myIO);

	//Obtenemos la fecha de su proxima reserva
	ReservaDAO reserva = new ReservaDAO(prop);
	
	request.setAttribute("ProximaReserva", reserva.obtenerProximaReserva(userBean.getCorreo()));

	
%>
	<jsp:include page="/mvc/view/user/PaginaPrincipalUsuarioDisplay.jsp"/>
<%
}
%>


