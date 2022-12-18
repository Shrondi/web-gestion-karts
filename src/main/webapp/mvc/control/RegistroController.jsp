<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="data.DAO.UsuarioDAO,business.usuario.UsuarioDTO" %>
<%@page import="display.javabean.userBean"%>
<jsp:useBean  id="userBean" scope="session" class="display.javabean.userBean"></jsp:useBean>

<%

//Caso 1: Por defecto ell usuario esta logueado y no puede registrar si no es admin
String nextPage = "../../index.jsp";
String mensajeNextPage = "";

//Caso 2: Se permite el registro a cualquier usuario no logueado y a los admin logueados
if (userBean == null || userBean.getCorreo().isEmpty() || userBean.getAdmin() == true) {
	
	String nombre = request.getParameter("nombre");
	String apellidos = request.getParameter("apellidos");
	String correo = request.getParameter("correo");
	String fechaNacimiento = request.getParameter("fechaNacimiento");
	String passWord = request.getParameter("passWord");
	Boolean admin = false;

	//Cuando haya un admin logueado podra registrar usuarios administradores
	if (userBean.getAdmin() == true){
		admin = true;
	}

	//Caso 2a: Hay parametros en el request
	if (correo != null){
		
		//Obtenemos el valor del parametro sqlproperties, es decir, la ruta relativa al fichero sql.properties
		String sqlproperties = application.getInitParameter("sqlproperties");

		//Obtenemos el recurso
		java.io.InputStream myIO = application.getResourceAsStream(sqlproperties);

		//Creamos un objeto properties y lo cargamos con el fichero
		java.util.Properties prop = new java.util.Properties();
		prop.load(myIO);
		
		UsuarioDAO usuario = new UsuarioDAO(prop);
		
		//Comprobamos si el usuario ya existe
		if (usuario.usuarioExiste(correo)){
			nextPage = "../view/RegistroDisplay.jsp";
			mensajeNextPage = "Ya existe un usuario registrado con ese correo";
			
		//Creamos el usuario
		}else{
			UsuarioDTO nuevoUsuario = new UsuarioDTO(correo, nombre, apellidos, fechaNacimiento, passWord, admin);
			
			usuario.altaUsuario(nuevoUsuario);
			
			//Como un admin puede registrar otros admin solo se hace el login automatico hacia la nueva cuenta creada si es un usuario normal
			if (userBean.getAdmin() == false){
			%>
			<jsp:setProperty property="correo" value="<%=nuevoUsuario.getCorreo()%>" name="userBean"/>
			<jsp:setProperty property="nombre" value="<%=nuevoUsuario.getNombre()%>" name="userBean"/>
			<jsp:setProperty property="apellidos" value="<%=nuevoUsuario.getApellidos()%>" name="userBean"/>
			<jsp:setProperty property="fechaNacimiento" value="<%=nuevoUsuario.getFechaNacimiento()%>" name="userBean"/>
			<jsp:setProperty property="passWord" value="<%=nuevoUsuario.getPassWord()%>" name="userBean"/>
			<jsp:setProperty property="admin" value="<%=nuevoUsuario.getAdmin()%>" name="userBean"/>	
		
			<%
			}
			
			nextPage = "../../index.jsp";
			
			if (userBean.getAdmin()){
				mensajeNextPage = "Se ha creado el usuario administrador " + correo + " correctamente";
			}else{
				mensajeNextPage = "Se ha iniciado sesi&oacute;n correctamente";
			}
			
		}
		
	//Caso 2b: No hay parametros en el request -> Se debe ir a la vista para registrarse
	}else{
		nextPage = "../view/RegistroDisplay.jsp";
	}
}
%>

<jsp:forward page="<%=nextPage%>">
	<jsp:param value="<%=mensajeNextPage%>" name="message"/>
</jsp:forward>