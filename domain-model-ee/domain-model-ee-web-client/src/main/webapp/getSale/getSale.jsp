<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="model" class="presentation.web.model.ViewSaleModel"
	scope="request"></jsp:useBean>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>New Sale</title>
</head>
<body>

	<div>

		<h2>Sale: ${model.id}</h2>

		<table>
			<tr>
				<td>Cliente: </td>
				<td>Total: ${model.total}</td>
				<td>Desconto: ${model.discountValue}</td>
				<td>Lista de Produtos</td>
				<c:forEach var="product" items="${model.saleProducts}">
					<tr>
						<td>Produt: ${product.description}</td>
						<td>Quantidade: ${product.qty}</td>
						<td>FaceValue: ${product.faceValue}</td>
						<td></td>
					</tr>

				</c:forEach>

			</tr>
		</table>


	</div>

</body>
</html>