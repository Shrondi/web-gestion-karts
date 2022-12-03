<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  
<jsp:useBean id="userBean" class="display.javabean.userBean" scope = "session"/>
<!DOCTYPE html>

<% 
String message = "";
String nextPage = "../../index.jsp";

if(userBean!=null || userBean.getCorreo()!=""){
	userBean.setCorreo("");
	userBean = null;
	message = "Se ha cerrado sesi&oacute;n";
}else{
	message = "No se cumplian los requisitos para realizar el cierre de sesi&oacute;n";
}
%>
<jsp:forward page="<%=nextPage%>">
	<jsp:param value="<%=message%>" name="message"/>
</jsp:forward>