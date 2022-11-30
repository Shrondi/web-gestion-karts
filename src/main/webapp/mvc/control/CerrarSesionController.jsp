<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page errorPage="include/errorPage.jsp" %>
<jsp:useBean  id="userBean" scope="session" class="display.javabean.userBean"></jsp:useBean>

<%
request.getSession().removeAttribute("userBean");
%>

<jsp:forward page="../../index.jsp">
		<jsp:param value="Sesion cerrada con exito" name="message"/>
</jsp:forward>