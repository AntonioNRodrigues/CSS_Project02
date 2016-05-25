package controller.web.inputController.actions;

import java.io.IOException;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import business.Customer;
import facade.handlers.IAddSaleHandlerRemote;
import facade.handlers.IGetCustomersHandlerRemote;
import presentation.web.model.NewSaleModel;

@Stateless
public class CreateSaleAction extends Action{

	@EJB private IAddSaleHandlerRemote addSaleHandler;
	
	@EJB private IGetCustomersHandlerRemote getCustomersHandler;
	
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			// receive customer ID
			int customerVAT = Integer.parseInt(request.getParameter("customerVAT"));
			
			// add sale
			addSaleHandler.addSale(customerVAT);
			
			NewSaleModel model = new NewSaleModel();
			model.setCustomers(getCustomersHandler.getCustomers());
			model.addMessage("Venda criar com successo");
			request.setAttribute("model", model);
			request.getRequestDispatcher("/newSale/newSale.jsp").forward(request, response);
			
		} catch (Exception e) {
			
		}
		
	}

}
