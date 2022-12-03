<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  
<jsp:useBean id="userBean" class="display.javabean.userBean" scope = "session"/>
<!DOCTYPE html>

<% 
String message = "No se cumplen los requisitos para realizar el cierre de sesi&oacuten";
String nextPage = "../../index.jsp";

if(userBean !=null && userBean.getCorreo() != ""){
	request.getSession().removeAttribute("userBean");
	userBean = null;
	message = "Se ha cerrado sesi&oacuten";
}
%>
<jsp:forward page="<%=nextPage%>">
	<jsp:param value="<%=message%>" name="message"/>
</jsp:forward>