<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="model" class="presentation.web.model.ViewSaleModel"
	scope="request"></jsp:useBean>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Sale Details</title>
</head>
<body>

	<div>

		<h2>Sale: ${model.id}</h2>
		<h3>Cliente: ${model.customer.designation}</h3>
		<hr/>
		
		<h4>Lista de Productos</h4>
		<table>
			<c:forEach var="sp" items="${model.saleProducts}">
					<tr>
						<td>Prod Code: ${sp.product.prodCod}</td>
						<td>Descricao: ${sp.product.description}</td>
						<td>Preço Unitario: ${sp.product.faceValue}</td>
						<td>Quantidade: ${sp.qty}</td>
						<td>subTotal: ${sp.subTotal}</td>
						
					</tr>

				</c:forEach>
		</table>
		
		<hr/>
		<p><b>Sub Total:&nbsp;</b>${model.total}</p>
		<p><b>Desconto:&nbsp;</b>${model.discountValue}</p>
		<hr/>
		<p><b>Total:&nbsp;</b>${model.finalValue}</p>

	</div>

</body>
</html>