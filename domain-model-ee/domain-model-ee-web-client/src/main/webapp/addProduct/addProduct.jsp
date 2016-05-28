<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="model" class="presentation.web.model.AddProductToSaleModel" scope="request"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
Add products to sale
${model.saleId}

	<table>
		<c:forEach var="product" items="${model.products}">
			<tr>
				<td>CODE: ${product.prodCod}</td>
				<td>Designation: ${product.description}</td>
				<td>Face Value: ${product.faceValue}</td>
				<td>
					<form method="post" action="insertProduct">
						<input type="hidden" value="${product.prodCod}" name="productID"/>
						<input type="hidden" value="${model.saleId}" name="saleID"/>
						<input type="submit" value="[+]"/>
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>

</body>
</html>