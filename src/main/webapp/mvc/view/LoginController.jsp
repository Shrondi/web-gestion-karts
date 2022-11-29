<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="data.DAO.UsuarioDAO,business.usuario.UsuarioDTO" %>
<jsp:useBean  id="userBean" scope="session" class="display.javabean.userBean"></jsp:useBean>

<%

String correo = request.getParameter("correo");
String passWord = request.getParameter("passWord");
Boolean admin = false;

//Obtenemos el valor del parametro sqlproperties, es decir, la ruta relativa al fichero sql.properties
String sqlproperties = application.getInitParameter("sqlproperties");

//Obtenemos el recurso
java.io.InputStream myIO = application.getResourceAsStream(sqlproperties);

//Creamos un objeto properties y lo cargamos con el fichero
java.util.Properties prop = new java.util.Properties();
prop.load(myIO);

//Caso 1, por defecto: userBean está logado -> Se redirige al index.jsp	
	nextPage = "../../index.jsp";
	mensajeNextPage = "";

/*
Caso 2: userBean no está logado
	a) Hay parámetros en el request  -> procede de la vista 
	b) No hay parámetros en el request -> procede de otra funcionalidad o index.jsp
*/	
if(userBean == null || userBean.getCorreo().equals("")){
	
	String emailUsuario = request.getParameter("email");
	String passwordUsuario = request.getParameter("password");

	//Caso 2.a: Hay parámetros -> procede de la VISTA
	if (emailUsuario != null) {
		
		//Se accede a la base de datos para obtener el correo del usuario
		UsuarioDAO usuarioDAO = new UsuarioDAO(prop);
		UsuarioDTO usuario = usuarioDAO.queryByEmail(emailUsuario);

		//Comprobación de usuario
		if (usuario == null || !usuario.getCorreo().equals(emailUsuario) || !usuario.getPassWord().equals(passwordUsuario)) {
			
			nextPage = "../view/LoginDisplay.jsp";
			mensajeNextPage = "Usuario no existe o no es v&aacute;lido";
			//Hay que dar opcion a volverlo a intentar
		
		//Usuario válido
		} else {
			
			%>
			<jsp:setProperty property="email" value="<%=usuario.getCorreo()%>" name="userBean"/>
			<jsp:setProperty property="nombre" value="<%=usuario.getNombre()%>" name="userBean"/>
			<jsp:setProperty property="apellidos" value="<%=usuario.getApellidos()%>" name="userBean"/>
			<jsp:setProperty property="fecha_nac" value="<%=usuario.getFechaNacimiento()%>" name="userBean"/>
			<jsp:setProperty property="fecha_insc" value="<%=usuario.getFechaInscripcion()%>" name="userBean"/>
			<jsp:setProperty property="password" value="<%=usuario.getPassWord()%>" name="userBean"/>
			<jsp:setProperty property="admin" value="<%=usuario.getAdmin()%>" name="userBean"/>
			<%
			
			//Si el usuario es administrador -> Se redirige a la vista del administrador
			if(usuario.getAdmin()){
				nextPage = "../view/LoginAdminDisplay.jsp";
			}
			//Si el usuario es cliente -> Se redirige a la vista del cliente	
			else{
				nextPage = "../view/LoginClientDisplay.jsp";
			}
		}
		
	//Caso 2.b: No hay parámetros en el request -> ir a la vista por primera vez
	} else {
		nextPage = "../view/LoginDisplay.jsp";		
	}
}

%>

<jsp:forward page="<%=nextPage%>">
	<jsp:param value="<%=mensajeNextPage%>" name="message"/>
</jsp:forward>