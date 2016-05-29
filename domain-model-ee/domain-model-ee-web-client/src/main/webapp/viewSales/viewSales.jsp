<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="model" class="presentation.web.model.ViewCustomerSalesModel" scope="request"></jsp:useBean>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Visualizar vendas de cliente</title>
</head>
<body>

	<h2>Cliente: ${model.customerDesignation}</h2>
	
	<table>
		<c:forEach var="sale" items="${model.sales}">
			<tr>
				<td>ID: ${sale.id}</td>
				<c:if test="${sale.open}">
					<td>
						<form method="post" action="../vendas/fecharVenda">
							<input type="hidden" name="sale" value="${sale.id}" />
							<input type="hidden" name="customerVAT" value="${model.customerVAT}" />
							<input type="submit" value="Fechar venda" />
						</form>
					</td>
				</c:if>
				<c:if test="${sale.closed}">
					<td>
						<form method="post" action="../vendas/pagarVenda">
							<input type="hidden" name="sale" value="${sale.id}" />
							<input type="hidden" name="customerVAT" value="${model.customerVAT}" />
							<input type="submit" value="Pagar venda" />
						</form>
					</td>
				</c:if>
				<c:if test="${sale.payed}">
					<td>
						PAYED!
					</td>
				</c:if>
				
				<td>
					<c:if test="${sale.open}">
						<a href="../vendas/addProducts?sale=${sale.id}">Add Products</a>
					</c:if>
						<a href="../vendas/visualizarVenda?sale=${sale.id}">View Sale Details</a>
					
				</td>
			</tr>
			
		</c:forEach>
	</table>
	
	
	<c:if test="${model.hasMessages}">
	<p>Mensagens</p>
	<ul>
	<c:forEach var="mensagem" items="${model.messages}">
		<li>${mensagem} 
	</c:forEach>
	</ul>
</c:if>
</body>
</html>