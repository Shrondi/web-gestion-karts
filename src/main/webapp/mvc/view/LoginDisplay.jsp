<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page errorPage="include/errorPage.jsp" %>
<jsp:useBean id="userBean" scope="session" class="display.javabean.userBean"></jsp:useBean>

<!DOCTYPE html>
<html>
		<head>
				<meta charset="UTF-8">
				<title>Login</title>
		</head>
		<body>

<%

String mensajeNextPage = request.getParameter("message");

if(mensajeNextPage == null) mensajeNextPage = "";

if(userBean != null){
	%>
	<jsp:forward page="../control/LoginController.jsp"/>
	<%	
}
else{
	%>
		<p id="message"><%= mensajeNextPage %></p>
		<fieldset>
			<legend>Login</legend>
			<form id="formLoginUsuario" method="post" action="./WebProyectoPW/mvc/control/LoginController.jsp">
				<div>
					<p>
						<label for="email">Email: </label>
						<input type="text" id="email" name="email" value="<%= userBean.getCorreo() %>">
					</p>
					<p>
						<label for="password">Password: </label>
						<input type="text" id="password" name="password" value="<%= userBean.getPassWord() %>">
					</p>
				</div>	
				<input type="submit" value="Acceder">
			</form>
			<form id="volver" method="post" action="../../index.jsp">
				<input type="submit" value="Volver">
			</form>
		</fieldset>
		<%
}
%>
	</body>
</html>