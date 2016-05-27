<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="model" class="presentation.web.model.NewSaleModel" scope="request"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>New Sale</title>
</head>
<body>

	<div>
		
		<h2>Sale ${model.getId}</h2>
		
		<form action="criarVenda" method="post">
			<table>
				<tr>
					<td class="mandatoryField">Selecionar cliente</td>
					<td>
						<select name="customerVAT">
							<c:forEach var="customer" items="${model.customers}">
								<option value="${customer.vatNumber}">${customer.designation}</option>
							</c:forEach>
						</select>								
					</td>
					<td>
						<input type="submit" value="Criar venda"/>
					</td>
				</tr>
			</table>	
		</form>
		 
		
	</div>
	
</body>
</html>