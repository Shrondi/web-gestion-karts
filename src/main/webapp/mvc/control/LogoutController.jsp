<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  
<jsp:useBean id="userBean" scope = "session" class="display.javabean.userBean" />

<% 
String message = "";
String nextPage = "../../index.jsp";

if (userBean == null || userBean.getCorreo() == "") {
	message = "No se cumplen los requisitos para realizar el cierre de sesi&oacute;n";
	
}else{ 
	request.getSession().removeAttribute("userBean");
	userBean = null;
	
	message = "Sesi&oacute;n cerrada";
}

%>
<jsp:forward page="<%=nextPage%>">
	<jsp:param value="<%=message%>" name="message"/>
</jsp:forward>