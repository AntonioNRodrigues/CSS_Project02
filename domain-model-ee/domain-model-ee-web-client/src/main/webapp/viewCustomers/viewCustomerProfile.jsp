<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="model" class="presentation.web.model.ViewCustomerProfileModel" scope="request"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Perfil de ${model.customer.designation}</title>
</head>
<body>
<h2>Cliente: ${model.customer.designation}</h2>
<ul>
	<li>
		${model.customer.designation}<br>
		${model.customer.vatNumber}
		<ul>
			<li><a href="/actions/clientes/visualizarVendas?vat=${model.customer.vatNumber}">Vendas</a></li>
			<li><a href="/actions/clientes/transacoes?vat=${model.customer.vatNumber}">Lista de Transações</a></li>
		</ul>
	</li>
</ul>
</body>
</html>