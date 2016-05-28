package controller.web.inputController.actions;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import business.persistence.entities.Product;
import facade.handlers.IGetProductHandlerRemote;
import presentation.web.model.AddProductToSaleModel;

@Stateless
public class AddProductToSaleAction extends Action{

	@EJB private IGetProductHandlerRemote getProductsHandler;
	
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			
			// get sale id
			int saleId = Integer.parseInt(request.getParameter("sale"));
			
			
			// get all products
			List<Product> products = getProductsHandler.getAvailableProducts();
			
			
			// set model
			AddProductToSaleModel model = new AddProductToSaleModel();
			model.setProducts(products);
			model.setSaleId(saleId);
			
			
			// make it available to template
			request.setAttribute("model", model);
			
			
			// pass control to dispatcher
			request.getRequestDispatcher("/addProduct/addProduct.jsp").forward(request, response);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Erro ao obter products", e);
		}
		
	}

	
	
}
