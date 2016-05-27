<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="model" class="presentation.web.model.ViewSaleModel"
	scope="request" />
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
				<td>${model.customer}</td>
				<td>${model.discount}</td>
				<td>${model.value}</td>
				<td>${model.date}</td>
			</tr>
		</table>


	</div>

</body>
</html>