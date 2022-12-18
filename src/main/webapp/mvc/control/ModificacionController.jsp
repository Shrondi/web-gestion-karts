<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="business.usuario.UsuarioDTO, data.DAO.UsuarioDAO" %>
<jsp:useBean  id="userBean" scope="session" class="display.javabean.userBean"></jsp:useBean>

<%

//Caso 1, por defecto usuario no logado -> Se redirige al index.jsp	
String nextPage = "../../index.jsp";
String mensajeNextPage = "Error. Para modificar sus datos, primero debe iniciar sesi&oacute;n.";


//Caso 2: Usuario logueado
if (userBean != null || !userBean.getCorreo().equals("")){
	
		//Se recoge info del usuario desde formulario
		String nombre = request.getParameter("nombre");
		String apellidos = request.getParameter("apellidos");
		String password = request.getParameter("passWord");
		
		//Caso 2.a: Hay parámetros en el request -> Se actualizan datos -> Procede a index.jsp
		if (nombre != null) {
			
			//Se obtienen los parámetros de inicializacion del fichero web.xml
			String sqlProperties= application.getInitParameter("sqlproperties"); 
			java.io.InputStream myIO = application.getResourceAsStream(sqlProperties);
			java.util.Properties prop = new java.util.Properties();
			prop.load(myIO);

			UsuarioDAO usuarioDAO = new UsuarioDAO(prop);
			UsuarioDTO usuarioDTO = new UsuarioDTO(userBean.getCorreo(), nombre, apellidos, userBean.getFechaNacimiento(), password, userBean.getAdmin());

			usuarioDAO.modificarUsuario(usuarioDTO);
			
			nextPage = "../../index.jsp";
			mensajeNextPage = "La informaci&oacute;n ha sido actualizada de manera exitosa";	
		
%>	
			
			<jsp:setProperty property="nombre" value="<%=usuarioDTO.getNombre()%>" name="userBean"/>
			<jsp:setProperty property="apellidos" value="<%=usuarioDTO.getApellidos()%>" name="userBean"/>
			<jsp:setProperty property="passWord" value="<%=usuarioDTO.getPassWord()%>" name="userBean"/>
			
<% 
		//Caso 2b: No hay parametros en el request -> Se debe ir a la vista para modificar los datos
		}else{
			nextPage = "../view/ModificacionDisplay.jsp";
		}
}
%>

<jsp:forward page="<%=nextPage%>">
		<jsp:param value="<%=mensajeNextPage%>" name="message"/>
</jsp:forward>
