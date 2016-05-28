package controller.web.inputController.actions;

import java.io.IOException;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import business.persistence.entities.Product;
import facade.handlers.IGetProductHandlerRemote;
import facade.handlers.IInsertSaleProductHandlerRemote;
import presentation.web.model.AddProductToSaleModel;

@Stateless
public class InsertProductSaleAction extends Action{

	@EJB private IInsertSaleProductHandlerRemote insertSaleProductHandler;
	
	@EJB private IGetProductHandlerRemote getProductsHandler;
	
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		AddProductToSaleModel model = new AddProductToSaleModel();
		
		
		try {
			
			// get sale and product id
			int saleId = Integer.parseInt(request.getParameter("saleID"));
			int productCod = Integer.parseInt(request.getParameter("productID"));
			
			// add product to sale
			insertSaleProductHandler.insertSaleProduct(saleId, productCod);
			
			// prepare model message
			model.setSaleId(saleId);
			model.setProducts(getProductsHandler.getAvailableProducts());
			model.addMessage("Product added!");
			
		} catch (Exception e) {
			
			model.setSaleId(0);
			model.setProducts(new ArrayList<Product>());
			model.addMessage("Error adding product to sale");
			e.printStackTrace();
		}
		
		request.setAttribute("model", model);
		request.getRequestDispatcher("/addProduct/addProduct.jsp").forward(request, response);
		
	}

}
