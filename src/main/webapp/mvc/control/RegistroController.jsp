<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="data.DAO.UsuarioDAO,business.usuario.UsuarioDTO" %>
<jsp:useBean  id="userBean" scope="session" class="display.javabean.userBean"></jsp:useBean>

<%

//Caso 1: Por defecto ell usuario esta logueado y no puede registrar si no es admin
String nextPage = "../../index.jsp";
String mensajeNextPage = "";

//Caso 2: Se permite el registro a cualquier usuario no logueado y a los admin logueados
if (userBean == null || userBean.getCorreo().isEmpty() || (userBean != null && userBean.getAdmin() == true)) {
	
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
		
		//Comprobamos si el usuario ya existe (crear DAO)
		if (usuario.usuarioExiste(correo)){
			nextPage = "../view/RegistroDisplay.jsp";
			mensajeNextPage = "Ya existe un usuario registrado con ese email";
			
		//Creamos el usuario
		}else{
			UsuarioDTO nuevoUsuario = new UsuarioDTO(correo, nombre, apellidos, fechaNacimiento, passWord, admin);
			
			usuario.altaUsuario(nuevoUsuario);
			
			//Volvemos al index para que el usuario se loguee
			nextPage = "../../index.jsp";
			mensajeNextPage = "Se ha creado el usuario correctamente";
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