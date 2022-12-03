<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="data.DAO.UsuarioDAO,business.usuario.UsuarioDTO" %>
<jsp:useBean  id="userBean" scope="session" class="display.javabean.userBean"></jsp:useBean>

<%

//Caso 1, por defecto: userBean está logado -> Se redirige al index.jsp	
	String nextPage = "../../index.jsp";
	String mensajeNextPage = "";

/*
Caso 2: userBean no está logado
	a) Hay parámetros en el request  -> Comprobamos existencia usuario -> Comprobamos datos introducidos -> Procede a la pagina principal
	b) No hay parámetros en el request -> Procede a index.jsp para loguearse
*/	
if(userBean == null || userBean.getCorreo().equals("")){
	
	String emailUsuario = request.getParameter("correo");
	String passwordUsuario = request.getParameter("passWord");

	//Caso 2.a: Hay parámetros -> Comprobamos si el usuario existe -> procede a la vista
	if (!emailUsuario.isEmpty() || !passwordUsuario.isEmpty()) {
		
		String sqlproperties = application.getInitParameter("sqlproperties");
		java.io.InputStream myIO = application.getResourceAsStream(sqlproperties);
		java.util.Properties prop = new java.util.Properties();
		prop.load(myIO);
		
		UsuarioDAO usuarioDAO = new UsuarioDAO(prop);
		
		//Comprobamos si el usuario existe
		if ( !usuarioDAO.usuarioExiste(emailUsuario)){ //No existe
			nextPage = "../../index.jsp";
			mensajeNextPage = "Usuario no existe";
			
		}else{ //Existe
			
			UsuarioDTO usuario = usuarioDAO.queryByEmail(emailUsuario);

			//Comprobamos si los datos introducidos por el usuario son correctos para el login
			if (!usuario.getCorreo().contentEquals(emailUsuario) || !usuario.getPassWord().contentEquals(passwordUsuario)) { //Datos incorrectos
				
				nextPage = "../../index.jsp";
				mensajeNextPage = "El usuario o la contrase&ntildea no son v&aacute;lidos";
				
			}else{ //Datos correctos, se procede con el login
				
				%>
				<jsp:setProperty property="correo" value="<%=usuario.getCorreo()%>" name="userBean"/>
				<jsp:setProperty property="nombre" value="<%=usuario.getNombre()%>" name="userBean"/>
				<jsp:setProperty property="apellidos" value="<%=usuario.getApellidos()%>" name="userBean"/>
				<jsp:setProperty property="fechaNacimiento" value="<%=usuario.getFechaNacimiento()%>" name="userBean"/>
				<jsp:setProperty property="fechaInscripcion" value="<%=usuario.getFechaInscripcion()%>" name="userBean"/>
				<jsp:setProperty property="passWord" value="<%=usuario.getPassWord()%>" name="userBean"/>
				<jsp:setProperty property="admin" value="<%=usuario.getAdmin()%>" name="userBean"/>
				<%
				
				nextPage = "../../index.jsp";
			}
		}
		
	//Caso 2.b: No hay parámetros en el request -> ir a la vista por primera vez
	} else {
		nextPage = "../../index.jsp";
		mensajeNextPage = "Rellene los campos para loguearse";
	}
}

%>

<jsp:forward page="<%=nextPage%>">
	<jsp:param value="<%=mensajeNextPage%>" name="message"/>
</jsp:forward>