<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="java.util.List, java.util.Date, java.time.*, business.usuario.UsuarioDTO, data.DAO.UsuarioDAO" %>
<jsp:useBean  id="userBean" scope="session" class="display.javabean.userBean"></jsp:useBean>

<%

//Caso 1: Usuario logueado y es admin
if (userBean.equals(null) || !userBean.getCorreo().isEmpty() || userBean.getAdmin() == true){
	
	//Se obtienen los parámetros de inicializacion del fichero web.xml
	String sqlProperties= application.getInitParameter("sqlproperties"); 
	java.io.InputStream myIO = application.getResourceAsStream(sqlProperties);
	java.util.Properties prop = new java.util.Properties();
	prop.load(myIO);

	UsuarioDAO usuarioDAO = new UsuarioDAO(prop);
	
	List<UsuarioDTO> usuarios = usuarioDAO.obtenerUsuarios();

	Date currDate = new Date();
	for (UsuarioDTO usuario : usuarios){
	
		long months = 0;
		
		Date fechaInscripcion = usuario.getFechaInscripcionDate();
		
		long diff = currDate.getTime() - fechaInscripcion.getTime();
		long d = ((1000l*60*60*24*365)/12);
		months = Math.round(diff/d);
		
		usuario.setAntiguedad((int)months);
		
	}
	
	request.setAttribute("usuarios", usuarios);
%>
	<jsp:include page="/mvc/view/admin/ListadoUsuariosDisplay.jsp"/>
<%
}
%>


