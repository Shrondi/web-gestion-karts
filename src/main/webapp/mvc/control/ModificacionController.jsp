<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="business.usuario.UsuarioDTO, data.DAO.UsuarioDAO" %>
<jsp:useBean  id="userBean" scope="session" class="display.javabean.userBean"></jsp:useBean>
<%

String nextPage = "";
String mensajeNextPage = "";

//Se recoge info del usuario desde formulario
String nombre = request.getParameter("nombre");
String apellidos = request.getParameter("apellidos");
String password = request.getParameter("password");



//Se obtienen los parámetros de inicializacion del fichero web.xml

String sqlProperties= application.getInitParameter("sqlproperties"); 

java.io.InputStream myIO = application.getResourceAsStream(sqlProperties);


java.util.Properties prop = new java.util.Properties();

prop.load(myIO);

UsuarioDAO usuarioDAO = new UsuarioDAO(prop);

UsuarioDTO usuarioAux = null;

//Primera situacion: Si el usuario NO esta registrado


//Si no existe el bean de usuario o su correo esta vacio
if(   (userBean == null) || (userBean.getCorreo().equals(""))   ){
	
	nextPage = "../../index.jsp";
	mensajeNextPage = "Error. Para modificar sus datos, primero debe registrarse.";
}


//Segunda situacion: Si el usuario accede por primera vez al controlador y NO hay ningun dato


else if(nombre == null){
	
	nextPage = "../view/vistaModificacion.jsp";
	mensajeNextPage = "Rellene los campos con su informacion";
}


//Tercera situacion: Si el usuario ha introducido datos. En este caso, se modifica la informacion

else{
	
	if( nombre.equals("") ){ //Si esta vacio, se obtiene el nombre del usuario
		
		nombre = userBean.getNombre();
	}
	
	if( apellidos.equals("") ){ //SI esta vacio, se obtienen los apellidos del usuario
		
		apellidos = userBean.getApellidos();
	}
	
	if( password.equals("") ){ //SI esta vacio, se obtiene la contraseña del usuario
		
		if(usuarioDAO.usuarioExiste(userBean.getCorreo())){
			
			usuarioAux = usuarioDAO.queryByEmail(userBean.getCorreo());
			
		}
	
		password = usuarioAux.getPassWord();
	}
	
	UsuarioDTO usuarioDTO = new UsuarioDTO(userBean.getCorreo(), nombre, apellidos, userBean.getFechaNacimiento(), password, userBean.getAdmin());

	usuarioDAO.modificarUsuario(usuarioDTO);
	
%>	
	
	<jsp:setProperty property="nombre" value="<%=usuarioDTO.getNombre()%>" name="userBean"/>
	<jsp:setProperty property="apellidos" value="<%=usuarioDTO.getApellidos()%>" name="userBean"/>
	
<%
	
	nextPage = "../view/vistaModificacion.jsp";
	mensajeNextPage = "La informacion ha sido actualizada de manera exitosa";
	
}

%>

<jsp:forward page="<%=nextPage%>">
		<jsp:param value="<%=mensajeNextPage%>" name="message"/>
</jsp:forward>