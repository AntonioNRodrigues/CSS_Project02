<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="model" class="presentation.web.model.ViewCustomersModel" scope="request"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Lista de clientes</title>
</head>
<body>
<h2>Lista de clientes</h2>
<table>
	<c:forEach var="customer" items="${model.customers}">
		<tr>
			<td>Nome:</td>
			<td>
				<a href="visualizarPerfil?vat=${customer.vatNumber}">${customer.designation}</a>
			</td>
		</tr>
	</c:forEach>
</table>
</body>
</html>