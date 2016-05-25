<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Perfil de ${customer.designation}</title>
</head>
<body>
<h2>Cliente: ${customer.designation}</h2>
<ul>
	<li>
		${customer.designation}
		<ul>
			<li><a href="/actions/clientes/visualizarVendas?vat=${customer.vatNumber}">Vendas</a></li>
		</ul>
	</li>
</ul>
</body>
</html>